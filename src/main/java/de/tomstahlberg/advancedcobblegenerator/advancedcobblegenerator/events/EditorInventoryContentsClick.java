package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import org.apache.commons.lang.StringUtils;
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
import java.util.List;

public class EditorInventoryContentsClick implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getInventory() != null && Main.editorInventoryContentsList.contains(event.getInventory())){
            event.setCancelled(true);
            if(event.getClickedInventory() == event.getWhoClicked().getInventory()){
                //add clicked item to inventory
                ItemStack itemStack = parseItem(event.getClickedInventory().getItem(event.getSlot()));
                //if inventory doesnt includes the item to add
                if(!(event.getInventory().contains(itemStack.getType()))){
                    for(int i = 0;i<54;i++){
                        if(event.getInventory().getItem(i) == null){
                            event.getInventory().setItem(i, itemStack);
                            break;
                        }
                    }
                }
            }else{
                if(event.getSlot() < 54 && event.getSlot() >= 0 && event.getInventory().getItem(event.getSlot()) != null){
                    if(event.getClick() == ClickType.LEFT){
                        //decrease weight
                        ItemStack itemStack = event.getInventory().getItem(event.getSlot());
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        List<String> lore = itemMeta.getLore();
                        String weight[] = lore.get(0).split(" ", 2);
                        Integer weightValue = Integer.valueOf(ChatColor.stripColor(weight[1]));
                        updateWeight(weightValue-1, itemStack);
                        event.getInventory().setItem(event.getSlot(), itemStack);
                    }else if(event.getClick() == ClickType.RIGHT){
                        //increase weight
                        ItemStack itemStack = event.getInventory().getItem(event.getSlot());
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        List<String> lore = itemMeta.getLore();
                        String weight[] = lore.get(0).split(" ", 2);
                        Integer weightValue = Integer.valueOf(ChatColor.stripColor(weight[1]));
                        updateWeight(weightValue+1, itemStack);
                        event.getInventory().setItem(event.getSlot(), itemStack);
                    }else if(event.getClick() == ClickType.SHIFT_LEFT){
                        //increase weight
                        ItemStack itemStack = event.getInventory().getItem(event.getSlot());
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        List<String> lore = itemMeta.getLore();
                        String weight[] = lore.get(0).split(" ", 2);
                        Integer weightValue = Integer.valueOf(ChatColor.stripColor(weight[1]));
                        updateWeight(weightValue-10, itemStack);
                        event.getInventory().setItem(event.getSlot(), itemStack);
                    }else if(event.getClick() == ClickType.SHIFT_RIGHT){
                        //increase weight
                        ItemStack itemStack = event.getInventory().getItem(event.getSlot());
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        List<String> lore = itemMeta.getLore();
                        String weight[] = lore.get(0).split(" ", 2);
                        Integer weightValue = Integer.valueOf(ChatColor.stripColor(weight[1]));
                        updateWeight(weightValue+10, itemStack);
                        event.getInventory().setItem(event.getSlot(), itemStack);
                    }else if(event.getClick() == ClickType.DROP){
                        //increase weight
                        event.getInventory().setItem(event.getSlot(), null);
                    }
                }
            }
        }
    }
    private ItemStack parseItem(ItemStack itemStackToParse){
        ItemStack itemStack = new ItemStack(itemStackToParse.getType());
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.translateAlternateColorCodes('&',Main.configurator.getLanguageConfiguration().getString("editor_item_edit_lore_weight")+" 10"));
        for(String loreLine : Main.configurator.getLanguageConfiguration().getStringList("editor_item_edit_lore")){
            lore.add(ChatColor.translateAlternateColorCodes('&',loreLine));
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    private void updateWeight(Integer weight, ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        lore.set(0, ChatColor.translateAlternateColorCodes('&',Main.configurator.getLanguageConfiguration().getString("editor_item_edit_lore_weight")+" "+weight));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        if(Main.editorInventoryContentsList.contains(event.getInventory())){
            String inventoryName[] = event.getView().getTitle().split(" ", 2);
            String biomeName = ChatColor.stripColor(inventoryName[0]);
            Integer level = Integer.valueOf(StringUtils.substringBetween(inventoryName[1], "{", "}"));
            Main.configurator.getGeneratorConfiguration().set("biomes."+biomeName+"."+level, null);
            for(ItemStack itemStack : event.getInventory().getContents()){
                if(itemStack != null){
                    String material = itemStack.getType().toString();
                    Integer weight = getWeight(itemStack);
                    Main.configurator.getGeneratorConfiguration().set("biomes."+biomeName+"."+level+"."+material, weight);
                }
            }
            Inventory inventory = prepareLevelInventory((Player) event.getPlayer(), biomeName);
            Main.editorInventoryContentsList.remove(event.getInventory());
            Main.editorInventoryLevelsList.add(inventory);
            Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    event.getPlayer().openInventory(inventory);
                }
            },10);
        }
    }
    private Integer getWeight(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        String weight[] = lore.get(0).split(" ", 2);
        return Integer.valueOf(ChatColor.stripColor(weight[1]));
    }
    private Inventory prepareLevelInventory(Player player, String biome){
        Inventory inventory = Bukkit.createInventory(player, 54, ChatColor.translateAlternateColorCodes('&',"&e"+biome));
        Integer i = 0;
        for(String level : Main.configurator.getGeneratorConfiguration().getConfigurationSection("biomes."+ChatColor.stripColor(biome)).getKeys(false)){
            if(i<45){
                ItemStack itemStack = new ItemStack(Material.MAP);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&e"+level));
                List<String> lore = new ArrayList<String>();
                for(String loreLine : Main.configurator.getLanguageConfiguration().getStringList("editor_edit_lore")){
                    lore.add(ChatColor.translateAlternateColorCodes('&',loreLine));
                }
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(i, itemStack);
                i++;
            }else{
                break;
            }
        }
        setAddLevelItem(inventory);
        return inventory;
    }

    private void setAddLevelItem(Inventory inventory){
        ItemStack itemStack = new ItemStack(Material.GREEN_BANNER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',Main.configurator.getLanguageConfiguration().getString("editor_add_level_title")));
        List<String> lore = new ArrayList<String>();
        for(String loreLine : Main.configurator.getLanguageConfiguration().getStringList("editor_add_level_lore")){
            lore.add(ChatColor.translateAlternateColorCodes('&',loreLine));
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(53, itemStack);
    }
}
