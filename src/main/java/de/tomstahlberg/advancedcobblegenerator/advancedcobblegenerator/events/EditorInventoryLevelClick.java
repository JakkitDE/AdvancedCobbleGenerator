package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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
        if(event.getClickedInventory() != null && Main.editorInventoryLevelsList.contains(event.getInventory())){
            event.setCancelled(true);
            biome = ChatColor.stripColor(event.getView().getTitle());
            if(event.getClick() == ClickType.LEFT){
                //content
                //if clicked add item
                if(event.getInventory().getItem(event.getSlot()) != null){
                    ItemStack itemStack = event.getInventory().getItem(event.getSlot());
                    if(itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',"&aAdd level"))){
                        Integer newLevel = getMaximumLevel(biome)+1;
                        Main.configurator.getGeneratorConfiguration().set("biomes."+biome+"."+newLevel+".STONE", 100);
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
                        Main.editorInventoryLevelsList.remove(event.getInventory());
                        Integer level = Integer.valueOf(ChatColor.stripColor(itemStack.getItemMeta().getDisplayName()));
                        event.getWhoClicked().openInventory(prepareContentsInventory((Player) event.getWhoClicked(), biome, level));
                    }
                }
            }
        }
    }
    public Inventory prepareContentsInventory(Player player, String biome, Integer level){
        Inventory inventory = Bukkit.createInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&e"+biome+" {"+level+"}"));
        int i = 0;
        for(String materialString : Main.configurator.getGeneratorConfiguration().getConfigurationSection("biomes."+biome+"."+level).getKeys(false)){
            if(i<53){
                Integer chance = Main.configurator.getGeneratorConfiguration().getInt("biomes."+biome+"."+level+"."+materialString);
                ItemStack itemStack = new ItemStack(Material.valueOf(materialString));
                ItemMeta itemMeta = itemStack.getItemMeta();
                List<String> lore = new ArrayList<String>();
                lore.add(ChatColor.translateAlternateColorCodes('&',"&eWeight: &a"+chance));
                lore.add(ChatColor.translateAlternateColorCodes('&',"&2Left-Click to decrease weight."));
                lore.add(ChatColor.translateAlternateColorCodes('&',"&2Right-Click to increase weight."));
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(i, itemStack);
                i++;
            }else{
                break;
            }
        }
        return inventory;
    }
    private Integer getMaximumLevel(String biome){
        List<Integer> levelList = new ArrayList<Integer>();
        for(String level : Main.configurator.getGeneratorConfiguration().getConfigurationSection("biomes."+biome).getKeys(false)){
            levelList.add(Integer.valueOf(level));
        }
        return Collections.max(levelList);
    }
}
