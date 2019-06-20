package com.alliware.ksi;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class KeepSelectiveInventory extends JavaPlugin {
    final FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // TODO: Check for worlds enabled and set /gamerule keepInventory to true.

        getServer().getPluginManager().registerEvents(new DeathListener(this), this);
        getLogger().info("Enabled KSI Plugin.");
    }
    @Override
    public void onDisable() {
        getLogger().info("Disabled KSI Plugin.");
    }
}
