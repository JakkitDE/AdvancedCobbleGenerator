package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.plugincommands;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions.Configurator;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions.GeneratorMap;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class CommandReload {
    public CommandReload(CommandSender sender){
        try {
            Main.configurator = new Configurator();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GeneratorMap genMap = new GeneratorMap(Main.configurator.getGeneratorConfiguration());
        Main.generatorMap = genMap.getGeneratorMap();

        Main.settings = Main.configurator.getSettingsConfiguration();

        Main.defaultBiome = genMap.getDefaultBiome();

        Main.worldList = Main.configurator.getWorlds();

        Main.iridiumHook = Main.configurator.getIridiumHook();

        Main.playerdata = Main.configurator.loadPlayerData();

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lGolden&3&lSky &8x &aDie Konfigurationsdateien wurden neu geladen."));

    }
}
