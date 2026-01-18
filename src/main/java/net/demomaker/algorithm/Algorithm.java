package net.demomaker.algorithm;

import com.hypixel.hytale.math.shape.Box;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;

import com.hypixel.hytale.server.core.universe.world.World;
import net.demomaker.debug.Debugger;
import net.demomaker.shared.BuildInfo;

import java.util.HashSet;
import java.util.Set;

public class Algorithm {
    public Debugger debugger;
    private World world;
    public AlgorithmOutput count(AlgorithmInput algorithmInput) {
        AlgorithmOutput algorithmOutput = new AlgorithmOutput();
        this.world = algorithmInput.world;

        Vector3i start = generateSmallestVector(algorithmInput.first_position, algorithmInput.second_position);
        Vector3i end = generateBiggestVector(algorithmInput.first_position, algorithmInput.second_position);

        Set<Vector3i> countedPositions = new HashSet<>();
        int loops = 0;

        for (int x = start.x; x <= end.x; x++) {
            for (int y = start.y; y <= end.y; y++) {
                for (int z = start.z; z <= end.z; z++) {
                    Vector3i currentPos = new Vector3i(x, y, z);
                    loops++;

                    if ((loops >= BuildInfo.debugBlockCountLimit && Debugger.canDebug())
                            || (loops >= BuildInfo.customBlockCountLimit && BuildInfo.customBlockCountLimit > 0)) {
                        return algorithmOutput;
                    }

                    BlockType blockType = this.world.getBlockType(currentPos);

                    String blockTypeId = (blockType == null || blockType.getId() == null) ? "Empty" : blockType.getId();

                    if (!canCountBlock(blockType, currentPos, start, end, countedPositions)) {
                        countedPositions.add(currentPos);
                        continue;
                    }

                    countedPositions.add(currentPos);

                    // Update the count map
                    algorithmOutput.counts.put(blockTypeId, algorithmOutput.counts.getOrDefault(blockTypeId, 0) + 1);
                    algorithmOutput.total += 1;
                }
            }
        }
        return algorithmOutput;
    }

    public boolean canCountBlock(BlockType blockType, Vector3i counter, Vector3i countStartBound, Vector3i countEndBound, Set<Vector3i> countedPositions) {
        Box boundingBox = getBoundingBox(blockType);
        if (boundingBox.isUnitBox()) {
            return true;
        }

        debugger.log("boundingBox : " + boundingBox.toString());
        Vector3i maxAsVector3i = boundingBox.getMax().ceil().toVector3i();
        Vector3i minAsVector3i = boundingBox.getMin().ceil().toVector3i();
        Vector3i maxWithAllOnesRemoved = maxAsVector3i.subtract(Vector3i.ALL_ONES);
        debugger.log("maxWithAllOnesRemoved : " + maxWithAllOnesRemoved.toString());
        debugger.log("minAsVector3i : " + minAsVector3i.toString());
        // Determine the actual world-space area this block occupies
        Vector3i blockMin = new Vector3i(counter).add(minAsVector3i);
        Vector3i blockMax = new Vector3i(counter).add(maxWithAllOnesRemoved);
        debugger.log("blockMin : " + blockMin.toString());
        debugger.log("blockMax : " + blockMax.toString());

        // Define the counting area boundaries (handling potential min/max flip)
        int rangeMinX = Math.min(countStartBound.x, countEndBound.x);
        int rangeMaxX = Math.max(countStartBound.x, countEndBound.x);
        int rangeMinY = Math.min(countStartBound.y, countEndBound.y);
        int rangeMaxY = Math.max(countStartBound.y, countEndBound.y);
        int rangeMinZ = Math.min(countStartBound.z, countEndBound.z);
        int rangeMaxZ = Math.max(countStartBound.z, countEndBound.z);
        debugger.log("range min Vector : " + new Vector3i(rangeMinX, rangeMinY, rangeMinZ).toString());
        debugger.log("range max Vector : " + new Vector3i(rangeMaxX, rangeMaxY, rangeMaxZ).toString());

        // Intersection: The loop only runs for the part of the block inside the counting range
        int startX = Math.min(Math.max(blockMin.x, rangeMinX), rangeMaxX);
        int endX = Math.max(Math.min(blockMax.x, rangeMaxX), rangeMinX);
        int startY = Math.min(Math.max(blockMin.y, rangeMinY), rangeMaxY);
        int endY = Math.max(Math.min(blockMax.y, rangeMaxY), rangeMinY);
        int startZ = Math.min(Math.max(blockMin.z, rangeMinZ), rangeMaxZ);
        int endZ = Math.max(Math.min(blockMax.z, rangeMaxZ), rangeMinZ);
        debugger.log("start Vector : " + new Vector3i(startX, startY, startZ));
        debugger.log("end Vector : " + new Vector3i(endX, endY, endZ));

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    Vector3i checkPos = new Vector3i(x, y, z);
                    debugger.log("currentCheck : " + checkPos.toString());

                    BlockType currentBlockType = this.world.getBlockType(checkPos);

                    if (currentBlockType.getId().equals(blockType.getId()) && countedPositions.contains(checkPos)) {
                        debugger.log("will not count this block");
                        return false;
                    }

                    debugger.log("will count this block");
                }
            }
        }

        return true;
    }

    public Vector3i removeSameValues(Vector3i originalVector, Vector3i comparison) {
        Vector3i newValue = new Vector3i(originalVector);
        if(newValue.x == comparison.x) {
            newValue.x = 0;
        }
        if(newValue.y == comparison.y) {
            newValue.y = 0;
        }
        if(newValue.z == comparison.z) {
            newValue.z = 0;
        }
        return newValue;
    }

    public Vector3i putToNegative(Vector3i original) {
        Vector3i newValue = new Vector3i(original);
        if(newValue.x > 0) {
            newValue.x = -newValue.x;
        }
        if(newValue.y > 0) {
            newValue.y = -newValue.y;
        }
        if(newValue.z > 0) {
            newValue.z = -newValue.z;
        }

        return newValue;
    }

    public Vector3i generateSmallestVector(Vector3i first_vector, Vector3i second_vector) {
        Vector3i smallestVector = new Vector3i(first_vector);

        if(smallestVector.x > second_vector.x) {
            smallestVector.x = second_vector.x;
        }
        if(smallestVector.y > second_vector.y) {
            smallestVector.y = second_vector.y;
        }
        if(smallestVector.z > second_vector.z) {
            smallestVector.z = second_vector.z;
        }

        return smallestVector;
    }

    public Vector3i generateBiggestVector(Vector3i first_vector, Vector3i second_vector) {
        Vector3i biggestVector = new Vector3i(first_vector);

        if(biggestVector.x < second_vector.x) {
            biggestVector.x = second_vector.x;
        }
        if(biggestVector.y < second_vector.y) {
            biggestVector.y = second_vector.y;
        }
        if(biggestVector.z < second_vector.z) {
            biggestVector.z = second_vector.z;
        }

        return biggestVector;
    }

    public Box getBoundingBox(BlockType blockType) {
        BlockBoundingBoxes blockBoundingBoxes = BlockBoundingBoxes.getAssetMap().getAsset(blockType.getHitboxType());
        try {
            return blockBoundingBoxes.get(0).getBoundingBox();
        } catch (Exception e) {
            return Box.UNIT;
        }
    }

    public boolean isMultiBlockChild(BlockType blockType) {
        return false;
    }
}
