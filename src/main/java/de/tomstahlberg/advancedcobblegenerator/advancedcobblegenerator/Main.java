package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events.BlockBreak;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events.BlockFromTo;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events.PlayerJoin;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions.Configurator;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions.GeneratorMap;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin {
    public static Plugin plugin;
    public static IridiumSkyblockAPI iridiumSkyblockAPI;

    public static Configurator configurator;
    public static FileConfiguration settings;

    public static HashMap<UUID, Integer> playerdata;
    public static Biome defaultBiome;
    public static HashMap<Biome, HashMap<Integer, List<Material>>> generatorMap = new HashMap<Biome, HashMap<Integer, List<Material>>>();
    public static List<World> worldList;

    public static HashMap<Location, Player> cobblerBlocksBroken = new HashMap<Location, Player>();
    public static List<Inventory> upgradeInventoryList = new ArrayList<Inventory>();
    public static Boolean iridiumHook;

    public static Economy econ = null;
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        iridiumSkyblockAPI = IridiumSkyblockAPI.getInstance();
        getServer().getPluginManager().registerEvents(new BlockFromTo(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        getServer().getPluginCommand("advancedcobblegenerator").setExecutor(new commands());
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&aACG &e-> &fPlugin is starting."));
        try {
            configurator = new Configurator();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GeneratorMap genMap = new GeneratorMap(configurator.getGeneratorConfiguration());
        generatorMap = genMap.getGeneratorMap();

        settings = configurator.getSettingsConfiguration();
        worldList = configurator.getWorlds();
        defaultBiome = genMap.getDefaultBiome();
        iridiumHook = configurator.getIridiumHook();
        playerdata = configurator.loadPlayerData();

        if (!setupEconomy() ) {
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&aACG &e-> &cStopped, due to not having an economy plugin installed."));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&aACG &e-> &fRegistered Vault successfully."));
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&aACG &e-> &fSuccessfully started."));
        getServer().getConsoleSender().sendMessage("IridiumHook "+iridiumHook);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            configurator.savePlayerData(playerdata);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aACG &e-> &cSuccesfully stopped."));
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

}
