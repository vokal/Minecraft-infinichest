package com.vokal.infinichest.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import com.vokal.infinichest.*;

public class BlockDispenseListener implements Listener {
    private InfiniChest mPlugin; // pointer to your main class, unrequired if you don't need methods from the main class

    public BlockDispenseListener(InfiniChest aPlugin) {
        mPlugin = aPlugin;
    }

    @EventHandler
    public void onBlockDispense(BlockDispenseEvent aEvent) {
        String blockId = mPlugin.getBlockIdentifier(aEvent.getBlock());

        if (!mPlugin.getConfig().contains(blockId)) {
            return;
        }

        Dispenser dispenser = (Dispenser) aEvent.getBlock().getState();
        Inventory inv = dispenser.getInventory();
        inv.addItem(aEvent.getItem());
    }
}
