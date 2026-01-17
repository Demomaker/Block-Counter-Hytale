package net.demomaker.events;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import net.demomaker.message.MessageSender;

public class BlockCounterEvent {

    public static void onPlayerReady(PlayerReadyEvent event) {
        Player player = event.getPlayer();
        MessageSender.sendMessage(player,"The BlockCounter mod is installed on this server. Use /blockcounter-help for more details");
    }

}