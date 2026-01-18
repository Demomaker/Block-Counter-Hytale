package net.demomaker.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.demomaker.message.MessageSender;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public abstract class BlockCounterCommand extends AbstractPlayerCommand {
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

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        MessageSender.sendMessage(commandContext, "BlockCounter command " + this.getName() + " executed !");
    }
}
