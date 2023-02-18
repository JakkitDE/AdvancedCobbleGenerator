package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.plugincommands;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions.Configurator;
import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class CommandReload {
    public CommandReload(CommandSender sender) throws IOException {
        Main.configurator.savePlayerData(Main.playerdata);
        try {
            Main.configurator = new Configurator();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Main.settings = Main.configurator.getSettingsConfiguration();

        Main.defaultBiome = Biome.valueOf(Main.configurator.getGeneratorConfiguration().getString("default"));

        Main.worldList = Main.configurator.getWorlds();

        Main.iridiumHook = Main.configurator.getIridiumHook();

        Main.playerdata = Main.configurator.loadPlayerData();

        Main.language = Main.configurator.getLanguageConfiguration();

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.language.getString("prefix")+Main.language.getString("reload_successfull")));

    }
}
