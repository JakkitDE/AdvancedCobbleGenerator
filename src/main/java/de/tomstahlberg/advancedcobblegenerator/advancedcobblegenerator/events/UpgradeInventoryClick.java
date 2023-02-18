package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class UpgradeInventoryClick implements Listener {
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
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.language.getString("prefix")+Main.language.getString("maximum_level_already_reached")));
        }else{
            Double playerBalance = Main.econ.getBalance(player);
            Double upgradePrice = getUpgradePrice(player);
            if(playerBalance >= upgradePrice){
                Main.econ.withdrawPlayer(player, upgradePrice);
                Integer newLevel = getCurrentCobblerLevel(player)+1;
                Main.playerdata.put(player.getUniqueId(), newLevel);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.language.getString("prefix")+Main.language.getString("upgrade_successfull")));
                player.closeInventory();
            }else{
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.language.getString("prefix")+Main.language.getString("not_enough_money_for_upgrade")));
                player.closeInventory();
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
