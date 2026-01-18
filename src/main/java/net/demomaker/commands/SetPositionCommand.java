package net.demomaker.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.demomaker.algorithm.Algorithm;
import net.demomaker.algorithm.AlgorithmInput;
import net.demomaker.algorithm.AlgorithmOutput;
import net.demomaker.debug.Debugger;
import net.demomaker.message.MessageSender;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class SetPositionCommand extends BlockCounterCommand {
    private static Vector3i stored_position = null;

    public SetPositionCommand(String name, String description) {
        super(name, description);
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        super.execute(commandContext, store, ref, playerRef, world);

        Vector3i position = playerRef.getTransform().getPosition().toVector3i();
        if(stored_position == null) {
            stored_position = position;
            MessageSender.sendMessage(commandContext, createPositionSetMessage(position, "first"));
            return;
        }
        Vector3i temp_stored_position = new Vector3i(stored_position);
        stored_position = null;

        MessageSender.sendMessage(commandContext, createPositionSetMessage(position, "second"));
        Algorithm algorithm = new Algorithm();
        algorithm.debugger = new Debugger(commandContext);

        AlgorithmInput algorithmInput = new AlgorithmInput(world, temp_stored_position, position);
        AlgorithmOutput algorithmOutput = algorithm.count(algorithmInput);
        MessageSender.sendMessage(commandContext, algorithmOutput.toString());
    }

    public String createPositionSetMessage(Vector3i position, String first_or_second) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("\n").append(first_or_second).append(" position has been set : ");
        messageBuilder.append("\nx : ").append(position.x);
        messageBuilder.append(", y : ").append(position.y);
        messageBuilder.append(", z : ").append(position.z);
        return messageBuilder.toString();
    }
}
