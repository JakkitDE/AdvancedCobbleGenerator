package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.plugincommands;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions.Configurator;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions.GeneratorMap;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.plugincommands.CommandReload;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.plugincommands.CommandUpgrade;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class commands implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission("advancedcobblegenerator.use") || sender.isOp()){
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("reload")){
                    if(sender.hasPermission("advancedcobblegenerator.admin") || sender.isOp()){
                        try {
                            new CommandReload(sender);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }else{
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.language.getString("prefix") + Main.language.getString("no_permissions_admin")));
                    }
                }else if (args[0].equalsIgnoreCase("upgrade")){
                    if(sender.hasPermission("advancedcobblegenerator.upgrade") || sender.isOp()){
                        new CommandUpgrade(sender);
                    }else{
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.language.getString("prefix") + Main.language.getString("no_permissions_upgrade")));
                    }
                }else if (args[0].equalsIgnoreCase("help")){
                    if(sender.hasPermission("advancedcobblegenerator.admin") || sender.isOp()){
                        List<String> help_menu = Main.language.getStringList("help_menu_admins");
                        for(String line : help_menu){
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',line));
                        }
                    }else{
                        List<String> help_menu = Main.language.getStringList("help_menu_players");
                        for(String line : help_menu){
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',line));
                        }
                    }
                }else{
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.language.getString("prefix") + Main.language.getString("wrong_usage")));
                }
            }else{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.language.getString("prefix") + Main.language.getString("wrong_usage")));
            }
        }else{
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.language.getString("prefix") + Main.language.getString("no_permissions_usage")));

        }

        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> arguments = new ArrayList<String>();
        if(args.length == 1){
            if(sender.hasPermission("advancedcobblegenerator.upgrade") || sender.isOp()){
                arguments.add("upgrade");
                arguments.add("help");
            }
            if(sender.hasPermission("advancedcobblegenerator.admin") || sender.isOp()){
                arguments.add("reload");
            }
        }
        return arguments;
    }
}
