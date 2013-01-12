package com.vokal.infinichest;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.block.Block;

import com.vokal.infinichest.event.*;

public final class InfiniChest extends JavaPlugin {

    public String getBlockIdentifier(Block aBlock) {
        StringBuilder result = new StringBuilder("x");
        result.append(Integer.toString(aBlock.getX()));
        result.append("y");
        result.append(Integer.toString(aBlock.getY()));
        result.append("z");
        result.append(Integer.toString(aBlock.getZ()));

        return result.toString();
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new SignChangedListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockDispenseListener(this), this);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }
}
