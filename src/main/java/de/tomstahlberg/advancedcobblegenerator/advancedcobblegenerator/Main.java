package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Plugin plugin;
    public static IridiumSkyblockAPI iridiumSkyblockAPI;
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        iridiumSkyblockAPI = IridiumSkyblockAPI.getInstance();
        getServer().getPluginManager().registerEvents(new BlockFromTo(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
