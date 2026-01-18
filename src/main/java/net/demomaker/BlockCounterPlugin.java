package net.demomaker;

import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import net.demomaker.commands.Command;
import net.demomaker.commands.DebugCommand;
import net.demomaker.events.BlockCounterEvent;

import javax.annotation.Nonnull;

public class BlockCounterPlugin extends JavaPlugin {

    public BlockCounterPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        this.getCommandRegistry().registerCommand(new DebugCommand("blockcounter-debug", "debug blockcounter"));

        try {
            Command.getCommands().forEach(command -> {
                this.getCommandRegistry().registerCommand(command.getCommandDefinition());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, BlockCounterEvent::onPlayerReady);
    }
}