package net.demomaker.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import net.demomaker.message.MessageSender;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.concurrent.CompletableFuture;

public abstract class BlockCounterCommand extends AbstractCommand {
    private String helperInfo;

    public BlockCounterCommand(String name, String description) {
        super(name, description);
    }

    public BlockCounterCommand setHelperInfo(String helperInfo) {
        this.helperInfo = helperInfo;
        return this;
    }

    public String getHelperInfo() {
        return this.helperInfo;
    }

    @NullableDecl
    @Override
    protected CompletableFuture<Void> execute(@NonNullDecl CommandContext commandContext) {
        MessageSender.sendMessage(commandContext, "BlockCounter command " + this.getName() + " executed !");
        return CompletableFuture.completedFuture(null);
    }
}
