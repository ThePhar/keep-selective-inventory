package com.alliware.ksi;

import com.alliware.debug.DebugMessenger;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class KeepSelectiveInventory extends JavaPlugin {
    final FileConfiguration config = getConfig();
    boolean debug = false;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        debug = config.getBoolean("debug");

        enableWorlds();

        getServer().getPluginManager().registerEvents(new DeathListener(this), this);
        getLogger().info("Enabled KSI Plugin.");
    }

    private void enableWorlds() {
        DebugMessenger.debugMessage("Enabling keepInventory on all worlds!", debug);
        List<String> worlds = config.getStringList("enabledWorlds");

        for (String world : worlds) {
            DebugMessenger.debugMessage("Attempting to enabling keepInventory on world '" + world + "'.", debug);
            World this_world = getServer().getWorld(world);

            if (this_world != null) {
                //noinspection ConstantConditions
                getServer().getWorld(world).setGameRule(GameRule.KEEP_INVENTORY, true);
                DebugMessenger.debugMessage("Successfully enabled keepInventory on world '" + world + "'.", debug);
            } else {
                DebugMessenger.debugMessage("World '" + world + "' does not exist!", debug);
            }
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled KSI Plugin.");
    }
}
