package net.demomaker.algorithm;

import com.hypixel.hytale.math.shape.Box;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;

import com.hypixel.hytale.server.core.universe.world.World;
import net.demomaker.debug.Debugger;
import net.demomaker.shared.BuildInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Algorithm {
    public Debugger debugger;
    private World world;
    public AlgorithmOutput count(AlgorithmInput algorithmInput) {
        AlgorithmOutput algorithmOutput = new AlgorithmOutput();
        this.world = algorithmInput.world;

        Vector3i start = generateSmallestVector(algorithmInput.first_position, algorithmInput.second_position);
        Vector3i end = generateBiggestVector(algorithmInput.first_position, algorithmInput.second_position);

        Set<String> occupiedSpaces = new HashSet<>();

        int loops = 0;

        for (int x = start.x; x <= end.x; x++) {
            for (int y = start.y; y <= end.y; y++) {
                for (int z = start.z; z <= end.z; z++) {
                    loops++;

                    if ((loops >= BuildInfo.debugBlockCountLimit && Debugger.canDebug())
                            || (loops >= BuildInfo.customBlockCountLimit && BuildInfo.customBlockCountLimit > 0)) {
                        return algorithmOutput;
                    }

                    Vector3i currentPos = new Vector3i(x, y, z);

                    if (occupiedSpaces.contains(currentPos.toString())) {
                        continue;
                    }

                    BlockType blockType = this.world.getBlockType(currentPos);

                    String blockTypeId = "Empty";

                    if(blockType != null && blockType.getId() != null) {
                        blockTypeId = blockType.getId();
                        if(blockTypeId.equals("Empty")) {
                            blockTypeId = getFluidTypeName(x, y, z);
                        }

                        Box box = getBoundingBox(blockType);

                        if(!box.isUnitBox()) {
                            addMultiblockToSet(new Vector3i(currentPos), box, occupiedSpaces);
                        } else {
                            occupiedSpaces.add(new Vector3i(currentPos).toString());
                        }
                    }

                    // Update the count map
                    algorithmOutput.counts.put(blockTypeId, algorithmOutput.counts.getOrDefault(blockTypeId, 0) + 1);
                    algorithmOutput.total += 1;
                }
            }
        }
        return algorithmOutput;
    }

    private String getFluidTypeName(int x, int y, int z) {
        int fluidId = this.world.getFluidId(x, y, z);
        debugger.log(String.valueOf(fluidId));
        String fluidName = this.getFluidNames().get(fluidId);
        if(fluidId > 0 && fluidName == null) {
            fluidName = "Unknown Fluid";
        }
        return fluidName;
    }

    private void addMultiblockToSet(Vector3i origin, Box box, Set<String> set) {
        Vector3i min = new Vector3i(origin);
        Vector3i diffBetweenMinAndMaxBox = new Vector3d(box.

                getMax()).add(-0.01, -0.01, -0.01).floor().subtract(new Vector3d(box.getMin().floor())).toVector3i();
        Vector3i max = new Vector3i(origin).add(diffBetweenMinAndMaxBox);

        debugger.log("min : " + min.toString());
        debugger.log("max : " + max.toString());

        for (int x = min.x; x <= max.x; x++) {
            for (int y = min.y; y <= max.y; y++) {
                for (int z = min.z; z <= max.z; z++) {
                    Vector3i multiblock = new Vector3i(x, y, z);
                    debugger.log("multiblock : " + multiblock.toString());
                    set.add(multiblock.toString());
                }
            }
        }
    }

    private Vector3i generateSmallestVector(Vector3i first_vector, Vector3i second_vector) {
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

    private Vector3i generateBiggestVector(Vector3i first_vector, Vector3i second_vector) {
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

    private Box getBoundingBox(BlockType blockType) {
        BlockBoundingBoxes blockBoundingBoxes = BlockBoundingBoxes.getAssetMap().getAsset(blockType.getHitboxType());
        try {
            return blockBoundingBoxes.get(0).getBoundingBox();
        } catch (Exception e) {
            return Box.UNIT;
        }
    }

    private Map<Integer, String> getFluidNames() {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "Empty");
        map.put(1, "Mud");
        map.put(2, "Red Slime");
        map.put(3, "Tar");
        map.put(4, "Poison");
        map.put(5, "Slime");
        map.put(6, "Lava");
        map.put(7, "Water");
        map.put(8, "Flowing Water");
        map.put(9, "Flowing Red Slime");
        map.put(10, "Flowing Tar");
        map.put(11, "Flowing Lava");
        map.put(12, "Flowing Mud");
        map.put(13, "Flowing Poison");
        map.put(14, "Flowing Slime");
        return map;
    }
}
