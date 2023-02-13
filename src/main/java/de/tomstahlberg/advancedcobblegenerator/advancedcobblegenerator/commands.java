package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class commands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission("advancedcobblegenerator.use") || sender.isOp()){
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("reload")){
                    //reload
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
