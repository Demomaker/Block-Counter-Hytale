package net.demomaker.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.demomaker.shared.BuildInfo;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class SetBlockLimitCommand extends BlockCounterCommand {
    public SetBlockLimitCommand(String name, String description) {
        super(name, description);
    }

    RequiredArg<Integer> limitValueArg = this.withRequiredArg("limit", "limit of blocks to count, if algorithm reaches this limit, it stops counting", ArgTypes.INTEGER);

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        super.execute(commandContext, store, ref, playerRef, world);
        BuildInfo.customBlockCountLimit = limitValueArg.get(commandContext);
    }
}