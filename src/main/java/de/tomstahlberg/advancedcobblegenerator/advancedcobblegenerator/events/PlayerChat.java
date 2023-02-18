package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        /* BIOM ADDING MODE */
        if(Main.editorBiomeAddMode.contains(player)){
            event.setCancelled(true);
            try{
                if(Biome.valueOf(event.getMessage().toUpperCase()) != null){
                    Main.editorBiomeAddMode.remove(player);
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&e--------------------"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&aBiome added successfully."));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&e--------------------"));
                }
            }catch(Exception e){
                if(event.getMessage().equalsIgnoreCase("exit")){
                    Main.editorBiomeAddMode.remove(player);
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&e--------------------"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cAborted."));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&e--------------------"));
                }else{
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage("");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&e--------------------"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cBiome is not valid! Try"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cagain or type &5exit &cto abort."));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&e--------------------"));
                }
            }



        }
    }
}
