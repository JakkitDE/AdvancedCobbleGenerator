package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EditorInventoryLevelClick implements Listener {

    public EditorInventoryLevelClick(){
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        String biome;
        if(Main.editorInventoryLevelsList.contains(event.getInventory())){
            biome = ChatColor.stripColor(event.getView().getTitle());
            if(event.getClick() == ClickType.LEFT){
                //content
                //if clicked add item
                if(event.getInventory().getItem(event.getSlot()) != null){
                    ItemStack itemStack = event.getInventory().getItem(event.getSlot());
                    if(itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',"&aAdd level"))){
                        Integer newLevel = getMaximumLevel(biome)+1;
                        Main.configurator.getGeneratorConfiguration().set("biomes."+biome, newLevel);
                        //refresh inventory
                        for(int i = 0; i<45; i++){
                            if(event.getInventory().getItem(i) == null){
                                ItemStack itemStackNewLevel = new ItemStack(Material.MAP);
                                ItemMeta itemMetaNewLevel = itemStackNewLevel.getItemMeta();
                                itemMetaNewLevel.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&e"+newLevel));
                                List<String> lore = new ArrayList<String>();
                                lore.add(ChatColor.translateAlternateColorCodes('&',"&2Leftclick to edit items of"));
                                lore.add(ChatColor.translateAlternateColorCodes('&',"&2this level."));
                                itemMetaNewLevel.setLore(lore);
                                itemStackNewLevel.setItemMeta(itemMetaNewLevel);
                                event.getInventory().setItem(i, itemStackNewLevel);
                                break;
                            }
                        }
                    }else{
                        //prepair contents inventory
                    }
                }
            }
            event.setCancelled(true);
        }
    }

    private Integer getMaximumLevel(String biome){
        List<Integer> levelList = new ArrayList<Integer>();
        for(String level : Main.configurator.getGeneratorConfiguration().getConfigurationSection("biomes."+biome).getKeys(false)){
            levelList.add(Integer.valueOf(level));
        }
        return Collections.max(levelList);
    }
}
