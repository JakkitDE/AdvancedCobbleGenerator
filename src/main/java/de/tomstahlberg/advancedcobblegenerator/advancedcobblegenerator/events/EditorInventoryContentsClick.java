package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import org.apache.commons.lang.StringUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EditorInventoryContentsClick implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getInventory() != null && Main.editorInventoryContentsList.contains(event.getInventory())){
            event.setCancelled(true);
            String inventoryName[] = event.getView().getTitle().split(" ", 2);
            String biomeName = inventoryName[0];
            Integer level = Integer.valueOf(StringUtils.substringBetween(inventoryName[1], "{", "}"));
            event.getWhoClicked().sendMessage(biomeName + " und " + level);
        }
    }
}
