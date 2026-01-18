package net.demomaker.message;

import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
import com.hypixel.hytale.builtin.buildertools.commands.CopyCommand;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.demomaker.debug.Debugger;

public class MessageSender {
    private static Store<EntityStore> store;
    private static PlayerRef playerRef;
    public static void init(Store<EntityStore> store, PlayerRef playerRef) {
        MessageSender.store = store;
        MessageSender.playerRef = playerRef;
    }

    public static void sendMessage(CommandContext commandContext, String message) {
        commandContext.sendMessage(Message.raw(wrapMessage(message)));
    }

    public static void sendMessageWithoutWrapper(CommandContext commandContext, String message) {
        commandContext.sendMessage(Message.raw(message));
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage(Message.raw(wrapMessage(message)));
    }

    public static void copyToClipboard(CommandContext commandContext, String message) {
        //TODO: To be implemented in the future (lacking features to do this in Hytale)
        new Debugger(commandContext).log(wrapMessage("could not copy contents to clipboard since Hytale system isnt designed for this usecase"));
    }

    public static String wrapMessage(String message) {
        return new StringBuilder().append(getMessagePreWrapper())
                .append("\n").append(message)
                .append("\n").append(getMessagePostWrapper())
                .toString();
    }

    private static String getMessagePreWrapper() {
        return "====[Block Counter]====";
    }

    private static String getMessagePostWrapper() {
        return "===================";
    }
}
