package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class InventoryClick implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getClickedInventory() != null){
            if(Main.upgradeInventoryList.contains(event.getClickedInventory())){
                event.setCancelled(true);
                if(event.getSlot() == 4){
                    if(event.getWhoClicked() instanceof Player){
                        doUpgrade(((Player) event.getWhoClicked()).getPlayer());
                    }
                }
            }else{
                if(event.getWhoClicked().getOpenInventory() != null){
                    if(Main.upgradeInventoryList.contains(event.getWhoClicked().getOpenInventory())){
                        event.setCancelled(true);
                    }
                }
            }
        }

    }
    private void doUpgrade(Player player){
        if(isMaximumLevel(player)){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&lGolden&3&lSky &8x &cDu hast bereits das maximale Level erreicht."));
        }else{
            Double playerBalance = Main.econ.getBalance(player);
            Double upgradePrice = getUpgradePrice(player);
            if(playerBalance >= upgradePrice){
                Main.econ.withdrawPlayer(player, upgradePrice);
                Integer newLevel = getCurrentCobblerLevel(player)+1;
                Main.playerdata.put(player.getUniqueId(), newLevel);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lGolden&3&lSky &8x &aDu hast deinen Cobbler aufgelevelt."));
                player.closeInventory();
            }else{
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lGolden&3&lSky &8x &cDu hast nicht genug Geld."));
            }
        }
    }
    private double getUpgradePrice(Player player){
        Integer nextCobblerLevel = getCurrentCobblerLevel(player)+1;
        return Main.configurator.getUpgradesConfiguration().getDouble(nextCobblerLevel+".price");
    }
    private Integer getCurrentCobblerLevel(Player player){
        return Main.playerdata.get(player.getUniqueId());
    }
    private Boolean isMaximumLevel(Player player){
        Integer nextCobblerLevel = getCurrentCobblerLevel(player)+1;
        if(Main.configurator.getUpgradesConfiguration().contains(nextCobblerLevel.toString())){
            return false;
        }else{
            return true;
        }
    }
}
