package com.alliware.ksi;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class KeepSelectiveInventory extends JavaPlugin {
    final FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        initializeConfig();

        getServer().getPluginManager().registerEvents(new DeathListener(this), this);
    }
    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }

    private void initializeConfig() {
        config.addDefault("armorDurabilityDamage", 10);
        config.options().copyDefaults(true);
        saveConfig();

        if (config.getInt("armorDurabilityDamage") < 0 || config.getInt("armorDurabilityDamage") > 100) {
            getLogger().info("armorDurabilityDamage must be between 0 and 100 inclusive!");
            getServer().shutdown();
        }
    }
}
