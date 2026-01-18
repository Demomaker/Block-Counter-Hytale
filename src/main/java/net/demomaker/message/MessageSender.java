package net.demomaker.message;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import net.demomaker.shared.BuildInfo;

public class MessageSender {
    public static void sendMessage(CommandContext commandContext, String message) {
        commandContext.sendMessage(Message.raw(wrapMessage(message)));
    }

    public static void sendDebugMessage(CommandContext commandContext, String message) {
        if (BuildInfo.isDebug) {
            commandContext.sendMessage(Message.raw(message));
        }
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage(Message.raw(wrapMessage(message)));
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
        return "=======================";
    }
}
