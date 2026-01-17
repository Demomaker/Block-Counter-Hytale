package net.demomaker.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.concurrent.CompletableFuture;

public class CountBlocksCommand extends BlockCounterCommand {

    protected CountBlocksCommand(String name, String description) {
        super(name, description);
    }

    @NullableDecl
    @Override
    protected CompletableFuture<Void> execute(@NonNullDecl CommandContext commandContext) {
        super.execute(commandContext);
        return CompletableFuture.completedFuture(null);
    }
}
