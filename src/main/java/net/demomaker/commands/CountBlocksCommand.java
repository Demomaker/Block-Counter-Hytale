package net.demomaker.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.demomaker.algorithm.Algorithm;
import net.demomaker.algorithm.AlgorithmInput;
import net.demomaker.algorithm.AlgorithmOutput;
import net.demomaker.debug.Debugger;
import net.demomaker.message.MessageSender;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;


public class CountBlocksCommand extends BlockCounterCommand {

    protected CountBlocksCommand(String name, String description) {
        super(name, description);
    }

    RequiredArg<Vector3i> first_position = this.withRequiredArg("first_position", "set the first position of the counting", ArgTypes.VECTOR3I);
    RequiredArg<Vector3i> second_position = this.withRequiredArg("second_position", "set the second position of the counting", ArgTypes.VECTOR3I);

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        super.execute(commandContext, store, ref, playerRef, world);
        Algorithm algorithm = new Algorithm();
        algorithm.debugger = new Debugger(commandContext);

        AlgorithmInput algorithmInput = new AlgorithmInput(world, first_position.get(commandContext), second_position.get(commandContext));
        AlgorithmOutput algorithmOutput = algorithm.count(algorithmInput);
        String output = algorithmOutput.toString();
        MessageSender.sendMessage(commandContext, output);
        MessageSender.copyToClipboard(commandContext, output);
    }
}
