package com.alliware.ksi;

import com.alliware.debug.DebugMessenger;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class DeathListener implements Listener {
    private KeepSelectiveInventory ksiPlugin;

    DeathListener(KeepSelectiveInventory ksiPlugin) {
        this.ksiPlugin = ksiPlugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        DebugMessenger.debugMessage("A player has died! Starting event", ksiPlugin.debug);
        Player player = event.getEntity();
        World world = player.getWorld();

        // Do not run this event handler if the world we are in is not in the config file.
        if (!ksiPlugin.config.getStringList("enabledWorlds").contains(world.getName())) {
            DebugMessenger.debugMessage("This is not an enabled world! (" + world.getName() + ")", ksiPlugin.debug);
            return;
        }

        int experience = player.getTotalExperience();

        // TODO: Add configurable.
        player.setTotalExperience(experience / 2);

        // TODO: Add configurable.
        // Reduce the player's level by 8.
        player.setLevel(player.getLevel() >= 8 ? player.getLevel() - 8 : 0);
        player.setExp(0);

        Inventory playerInventory = player.getInventory();

        DebugMessenger.debugMessage("Dropping all internal items.", ksiPlugin.debug);
        // TODO: Add configurable.
        // Drop all the items in the player's internal inventory.
        for (int i = 9; i <= 35; i++) {
            ItemStack item = playerInventory.getItem(i);
            if (item != null) {
                world.dropItem(player.getLocation(), item);
                playerInventory.clear(i);
                DebugMessenger.debugMessage("Dropped ItemStack " + item.getType().name(), ksiPlugin.debug);
            }
        }

        DebugMessenger.debugMessage("Damaging all equipped armor.", ksiPlugin.debug);
        // TODO: Add configurable.
        // Damage all equipped armor by a set amount in config file.
        for (int i = 36; i <= 39; i++) {
            ItemStack equipment = playerInventory.getItem(i);

            // Make sure we're wearing something!
            if (equipment == null) continue;

            // Check if we can damage this item, otherwise continue.
            ItemMeta meta = equipment.getItemMeta();
            if (!(meta instanceof Damageable)) continue;

            int value = ksiPlugin.config.getInt("armorDurabilityDamage");
            if (value == 0) continue;
            else {
                value = 100 / value;
            }

            int currentDamage = ((Damageable) meta).getDamage();
            int damageModifier = equipment.getType().getMaxDurability() / value;

            ((Damageable) meta).setDamage(currentDamage + damageModifier);
            equipment.setItemMeta(meta);

            DebugMessenger.debugMessage(
                    "Damaged Armor " + equipment.getType().name() + " by " + value + " percent",
                    ksiPlugin.debug);
        }
    }
}
