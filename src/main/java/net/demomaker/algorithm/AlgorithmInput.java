package net.demomaker.algorithm;

import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.universe.world.World;

public class AlgorithmInput {
    public World world;
    public Vector3i first_position;
    public Vector3i second_position;

    public AlgorithmInput(World world, Vector3i first_position, Vector3i second_position) {
        this.world = world;
        this.first_position = first_position;
        this.second_position = second_position;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("World : ").append(world.toString());
        stringBuilder.append("first_position : ").append(first_position.toString());
        stringBuilder.append("second_position : ").append(second_position.toString());
        return stringBuilder.toString();
    }
}
