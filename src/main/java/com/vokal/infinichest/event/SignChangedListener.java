package com.vokal.infinichest.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.block.Block;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.Material;

import com.vokal.infinichest.*;

public class SignChangedListener implements Listener {
    private InfiniChest mPlugin; // pointer to your main class, unrequired if you don't need methods from the main class

    public SignChangedListener(InfiniChest aPlugin) {
        mPlugin = aPlugin;
    }

    @EventHandler
    public void onSignChanged(SignChangeEvent aEvent) {
        String[] lines = aEvent.getLines();

        // Verify the sign is for an InfiniChest
        if (!lines[0].equals("[InfiniChest]")) {
            mPlugin.getLogger().info("Authorized user didn't place an InfiniChest sign");
            return;
        }

        // Check user authorization
        if (!aEvent.getPlayer().hasPermission("infinichest.moderator")) {
            aEvent.getPlayer().sendMessage(ChatColor.RED + "Not authorized to use InfiniChest");
            return;
        }

        // Find the chest (block below and in front)
        int x = aEvent.getBlock().getX();
        int y = aEvent.getBlock().getY();
        int z = aEvent.getBlock().getZ();
        World world = aEvent.getBlock().getWorld();

        Block block = world.getBlockAt(x, y - 1, z);

        // Verify the block below is a chest
        if (!(block.getType() == Material.CHEST) && !(block.getType() == Material.DISPENSER)) {
            aEvent.getBlock().breakNaturally();
            aEvent.getPlayer().sendMessage(ChatColor.RED + "Chest must be placed underneath before placing the sign");
            return;
        }

        String blockId = mPlugin.getBlockIdentifier(block);
        mPlugin.getLogger().info("Writing block " + blockId + " to config");

        mPlugin.getConfig().set(blockId + ".x", x);
        mPlugin.getConfig().set(blockId + ".y", y);
        mPlugin.getConfig().set(blockId + ".z", z);

        mPlugin.saveConfig();

        aEvent.getPlayer().sendMessage(ChatColor.GREEN 
                + "Sign successfully placed for chest at [" 
                + Integer.toString(x) + ", "
                + Integer.toString(y - 1) + ", "
                + Integer.toString(z) + "!");
    }
}
