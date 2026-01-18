package net.demomaker.algorithm;

import com.hypixel.hytale.math.shape.Box;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.command.system.CommandContext;

import net.demomaker.message.MessageSender;
import net.demomaker.shared.BuildInfo;

import java.util.HashSet;
import java.util.Set;

public class Algorithm {
    public CommandContext debugCommandContext;
    public AlgorithmOutput count(AlgorithmInput algorithmInput) {
        AlgorithmOutput algorithmOutput = new AlgorithmOutput();

        Vector3i start = generateSmallestVector(algorithmInput.first_position, algorithmInput.second_position);
        Vector3i end = generateBiggestVector(algorithmInput.first_position, algorithmInput.second_position);

        Set<Vector3i> countedPositions = new HashSet<>();
        int loops = 0;

        for (int x = start.x; x <= end.x; x++) {
            for (int y = start.y; y <= end.y; y++) {
                for (int z = start.z; z <= end.z; z++) {
                    Vector3i currentPos = new Vector3i(x, y, z);
                    loops++;

                    if ((loops >= BuildInfo.debugBlockCountLimit && BuildInfo.isDebug)
                            || (loops >= BuildInfo.customBlockCountLimit && BuildInfo.customBlockCountLimit > 0)) {
                        return algorithmOutput;
                    }

                    BlockType blockType = algorithmInput.world.getBlockType(currentPos);

                    String blockTypeId = (blockType == null || blockType.getId() == null) ? "Empty" : blockType.getId();

                    /*if (blockType != null && isMultiBlockChild(blockType)) {
                        continue;
                    }*/

                    if (!canCountBlock(blockType, currentPos, start, end, countedPositions)) {
                        //countedPositions.add(currentPos);
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

        MessageSender.sendDebugMessage(debugCommandContext, "boundingBox : " + boundingBox.toString());

        Vector3i minBound = generateSmallestVector(counter.subtract(boundingBox.getMin().toVector3i()), countStartBound);
        MessageSender.sendDebugMessage(debugCommandContext, "minBound : " + minBound.toString());
        Vector3i maxBound = generateSmallestVector(counter.add(boundingBox.getMax().toVector3i()), countEndBound);
        MessageSender.sendDebugMessage(debugCommandContext, "maxBound : " + maxBound.toString());

        int localLoops = 0;

        // Correctly nested loops with initialization
        for (int x = minBound.x; x <= maxBound.x; x++) {
            for (int y = minBound.y; y <= maxBound.y; y++) {
                for (int z = minBound.z; z <= maxBound.z; z++) {
                    localLoops++;

                    if (localLoops >= BuildInfo.debugBlockCountLimit && BuildInfo.isDebug) {
                        return false;
                    }

                    // Check if this specific coordinate has already been counted
                    Vector3i checkPos = new Vector3i(x, y, z);
                    if (countedPositions.contains(checkPos)) {
                        return false;
                    }
                }
            }
        }

        return true;
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
