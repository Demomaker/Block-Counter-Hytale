package net.demomaker.debug;

import com.hypixel.hytale.server.core.command.system.CommandContext;
import net.demomaker.message.MessageSender;

public class Debugger {
    private final CommandContext debugCommandContext;
    private static boolean isDebugActive = false;

    public Debugger(CommandContext debugCommandContext) {
        this.debugCommandContext = debugCommandContext;
    }

    public Debugger log(String message) {
        if(!canDebug()) {
            return this;
        }
        MessageSender.sendMessageWithoutWrapper(debugCommandContext, message);
        return this;
    }

    public static boolean canDebug() {
        return isDebugActive;
    }

    public static void toggleDebug() {
        isDebugActive = !isDebugActive;
    }
}
