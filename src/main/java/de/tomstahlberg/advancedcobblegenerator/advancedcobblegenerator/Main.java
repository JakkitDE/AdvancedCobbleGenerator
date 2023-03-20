package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblock;
import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events.*;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions.Configurator;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.plugincommands.commands;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
    //public static HashMap<Biome, HashMap<Integer, List<Material>>> generatorMap = new HashMap<Biome, HashMap<Integer, List<Material>>>();
    public static List<World> worldList;

    public static HashMap<Location, Player> cobblerBlocksBroken = new HashMap<Location, Player>();
    public static List<Inventory> upgradeInventoryList = new ArrayList<Inventory>();
    public static List<Inventory> editorInventoryBiomesList = new ArrayList<Inventory>();
    public static List<Inventory> editorInventoryLevelsList = new ArrayList<Inventory>();
    public static List<Inventory> editorInventoryContentsList = new ArrayList<Inventory>();
    public static List<Player> editorBiomeAddMode = new ArrayList<Player>();
    public static Boolean iridiumHook;

    public static Boolean superiorSkyblock2Hook;

    public static Economy econ = null;

    public static FileConfiguration language;

    public static Boolean debugMode;
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        debugMode = false;


        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        getServer().getPluginManager().registerEvents(new UpgradeInventoryClick(), this);
        getServer().getPluginManager().registerEvents(new EditorIntentoryBiomesClick(), this);
        getServer().getPluginManager().registerEvents(new PlayerChatBiomeEditor(), this);
        getServer().getPluginManager().registerEvents(new EditorInventoryLevelClick(), this);
        getServer().getPluginManager().registerEvents(new EditorInventoryContentsClick(), this);
        getServer().getPluginCommand("advancedcobblegenerator").setExecutor(new commands());
        getServer().getPluginCommand("advancedcobblegenerator").setTabCompleter(new commands());
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&aACG &e-> &fPlugin is starting v.1.2.2."));
        try {
            configurator = new Configurator();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //generatorMap = genMap.getGeneratorMap();

        settings = configurator.getSettingsConfiguration();
        worldList = configurator.getWorlds();
        iridiumHook = configurator.getIridiumHook();
        superiorSkyblock2Hook = configurator.getSuperiorSkyblockHook();
        playerdata = configurator.loadPlayerData();
        try {
            configurator.checkUpdateVariables();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!setupEconomy() ) {
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&aACG &e-> &cStopped, due to not having an economy plugin installed."));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        language = configurator.getLanguageConfiguration();
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&aACG &e-> &fRegistered Vault successfully."));
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&aACG &e-> &fSuccessfully started."));

        if(iridiumHook == true){
            if(getServer().getPluginManager().getPlugin("IridiumSkyblock") != null){
                iridiumSkyblockAPI = IridiumSkyblockAPI.getInstance();
                getServer().getPluginManager().registerEvents(new BlockFromToIridiumSkyblock(), this);
                getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&aACG &e-> &aSuccessfully hooked into &fIridiumSkyblock&a."));
            }else{
                getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&aACG &e-> &cYou enabled IridiumHook in settings, but IridiumSkyblock is missing. Plugin is disabling. Install IridiumSkyblock or disable the hook in settings.yml."));
                getServer().getPluginManager().disablePlugin(this);
            }

        }else if(superiorSkyblock2Hook == true){
            if(getServer().getPluginManager().getPlugin("SuperiorSkyblock2") != null){
                getServer().getPluginManager().registerEvents(new BlockFromToSuperiorSkyblock(), this);
                getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&aACG &e-> &aSuccessfully hooked into &fSuperiorSkyblock2&a."));
            }else{
                getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&aACG &e-> &cYou enabled SuperiorSkyblock2Hook in settings, but SuperiorSkyblock2 is missing. Plugin is disabling. Install SuperiorSkyblock2 or disable the hook in settings.yml."));
                getServer().getPluginManager().disablePlugin(this);
            }

        }else{
            getServer().getPluginManager().registerEvents(new BlockFromToSingle(), this);
        }
        if(configurator.getJetsMinionsHook()){
            if(getServer().getPluginManager().getPlugin("JetsMinions") != null){
                getServer().getPluginManager().registerEvents(new BlockBreakByMinion(), this);
                getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&aACG &e-> &aSuccessfully hooked into &fJetsMinions&a."));
            }else{
                getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&aACG &e-> &cYou enabled JetsMinionsHook in settings, but JetsMinions is missing. Plugin is disabling. Install JetsMinions or disable the hook in settings.yml."));
                getServer().getPluginManager().disablePlugin(this);
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            configurator.savePlayerData(playerdata);
            configurator.saveGeneratorSettings();
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
