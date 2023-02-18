package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class EditorIntentoryClick implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getClickedInventory() != null){
            if(Main.editorInventoryList.contains(event.getInventory())){
                event.setCancelled(true);
                if(checkIfClickedAddItem(event.getClickedInventory().getItem(event.getSlot()), (Player) event.getWhoClicked())){
                    event.getWhoClicked().closeInventory();
                    Main.editorInventoryList.remove(event.getInventory());
                }
            }
        }
    }
    private Boolean checkIfClickedAddItem(ItemStack itemStack, Player player){
        if(itemStack != null && itemStack.hasItemMeta()){
            if(itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&aAdd Biome"))){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&e--------------------"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&aEnter the &5biome &ain the chat or type &5exit &ato abort."));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&e--------------------"));
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
