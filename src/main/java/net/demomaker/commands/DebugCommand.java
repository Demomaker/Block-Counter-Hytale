package net.demomaker.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.demomaker.debug.Debugger;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class DebugCommand extends BlockCounterCommand {
    public DebugCommand(String name, String description) {
        super(name, description);
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        super.execute(commandContext, store, ref, playerRef, world);
        Debugger.toggleDebug();
    }
}