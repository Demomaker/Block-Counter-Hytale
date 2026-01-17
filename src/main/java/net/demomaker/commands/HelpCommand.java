package net.demomaker.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import net.demomaker.message.MessageSender;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.concurrent.CompletableFuture;

public class HelpCommand extends BlockCounterCommand {
    protected HelpCommand(String name, String description) {
        super(name, description);
    }

    @NullableDecl
    @Override
    protected CompletableFuture<Void> execute(@NonNullDecl CommandContext commandContext) {
        super.execute(commandContext);
        MessageSender.sendMessage(commandContext, createMessage());
        return CompletableFuture.completedFuture(null);
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
