package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions.Configuration;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions.GeneratorMap;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class commands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission("advancedcobblegenerator.use") || sender.isOp()){
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("reload")){
                    //reload
                    try {
                        Main.configurator = new Configuration();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    GeneratorMap genMap = new GeneratorMap(Main.configurator.getGeneratorConfiguration());
                    Main.generatorMap = genMap.getGeneratorMap();
                    Main.plugin.getServer().getConsoleSender().sendMessage(Main.generatorMap.toString());

                    Main.settings = Main.configurator.getSettingsConfiguration();

                    Main.defaultBiome = genMap.getDefaultBiome();

                    Main.worldList = Main.configurator.getWorlds();

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lGolden&3&lSky &8x &aDie Konfigurationsdateien wurden neu geladen."));

                }else{
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lGolden&3&lSky &8x &cBenutze /acg reload."));
                }
            }else{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lGolden&3&lSky &8x &cBenutze /acg reload."));
            }
        }else{
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lGolden&3&lSky &8x &cKeine Rechte."));

        }

        return false;
    }
}
