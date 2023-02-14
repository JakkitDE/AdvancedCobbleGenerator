package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events.BlockFromTo;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions.Configuration;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions.GeneratorMap;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public final class Main extends JavaPlugin {
    public static Plugin plugin;
    public static IridiumSkyblockAPI iridiumSkyblockAPI;

    public static Configuration configurator;
    public static FileConfiguration settings;
    public static Biome defaultBiome;
    public static HashMap<Biome, HashMap<Integer, List<Material>>> generatorMap = new HashMap<Biome, HashMap<Integer, List<Material>>>();
    public static List<World> worldList;
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        iridiumSkyblockAPI = IridiumSkyblockAPI.getInstance();
        getServer().getPluginManager().registerEvents(new BlockFromTo(), this);
        getServer().getPluginCommand("advancedcobblegenerator").setExecutor(new commands());
        getServer().getConsoleSender().sendMessage("§aCobbleGen gestartet.");
        try {
            configurator = new Configuration();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GeneratorMap genMap = new GeneratorMap(configurator.getGeneratorConfiguration());
        generatorMap = genMap.getGeneratorMap();

        settings = configurator.getSettingsConfiguration();
        worldList = configurator.getWorlds();
        defaultBiome = genMap.getDefaultBiome();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
