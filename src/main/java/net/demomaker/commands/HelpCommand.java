package net.demomaker.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.demomaker.message.MessageSender;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class HelpCommand extends BlockCounterCommand {
    protected HelpCommand(String name, String description) {
        super(name, description);
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        super.execute(commandContext, store, ref, playerRef, world);
        MessageSender.sendMessage(commandContext, createMessage());
    }

    private String createMessage() {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Help for the BlockCounter commands : ")
                .append("\nHere are the commands :\n");

        Command.getCommands().forEach(command -> {
            messageBuilder.append("\n---\n").append("/").append(command.getName())
                    .append("\n").append(command.getDescription())
                    .append("\n").append(command.getHelperInfo())
                    .append("\n");
        });

        return messageBuilder.toString();
    }
}
