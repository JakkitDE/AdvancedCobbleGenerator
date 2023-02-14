package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions.Configurator;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions.GeneratorMap;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.plugincommands.CommandReload;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.plugincommands.CommandUpgrade;
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
                    if(sender.hasPermission("advancedcobblegenerator.admin") || sender.isOp()){
                        new CommandReload(sender);
                    }else{
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lGolden&3&lSky &8x &cKeine Rechte."));
                    }
                }else if (args[0].equalsIgnoreCase("upgrade")){
                    if(sender.hasPermission("advancedcobblegenerator.upgrade") || sender.isOp()){
                        new CommandUpgrade(sender);
                    }else{
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lGolden&3&lSky &8x &cKeine Rechte."));
                    }
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
