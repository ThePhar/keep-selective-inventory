package com.alliware.debug;

import org.bukkit.Bukkit;

public class DebugMessenger {
    public static void debugMessage(String message, boolean debug) {
        if (!debug) return;

        Bukkit.getServer().broadcastMessage("[DEBUG]: " + message);
    }
}
