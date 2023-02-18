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
import java.util.List;

public class EditorIntentoryBiomesClick implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getClickedInventory() != null){
            if(Main.editorInventoryBiomesList.contains(event.getInventory())){
                event.setCancelled(true);
                if(checkIfClickedAddItem(event.getClickedInventory().getItem(event.getSlot()), (Player) event.getWhoClicked())){
                    //clicked on add biome
                    event.getWhoClicked().closeInventory();
                    Main.editorInventoryBiomesList.remove(event.getInventory());
                    Main.editorBiomeAddMode.add((Player) event.getWhoClicked());
                }else{
                    if(event.getInventory().getItem(event.getSlot()) != null){
                        //clicked on a biome
                        if(event.getClick() == ClickType.LEFT){
                            String biomeString = ChatColor.stripColor(event.getInventory().getItem(event.getSlot()).getItemMeta().getDisplayName());

                            Inventory inventory = prepareLevelInventory((Player) event.getWhoClicked(), biomeString);
                            event.getWhoClicked().openInventory(inventory);
                            Main.editorInventoryBiomesList.remove(event.getInventory());
                            Main.editorInventoryLevelsList.add(inventory);
                        }else if (event.getClick() == ClickType.DROP){
                            String biomeString = ChatColor.stripColor(event.getInventory().getItem(event.getSlot()).getItemMeta().getDisplayName());
                            Main.configurator.getGeneratorConfiguration().set("biomes."+biomeString, null);
                            event.getInventory().setItem(event.getSlot(), null);
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        if(event.getInventory() != null && Main.editorInventoryBiomesList.contains(event.getInventory())){
            Main.editorInventoryBiomesList.remove(event.getInventory());
        }
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
                for(String loreLine : Main.configurator.getGeneratorConfiguration().getStringList("editor_edit_lore")){
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
        for(String loreLine : Main.configurator.getGeneratorConfiguration().getStringList("editor_add_level_lore")){
            lore.add(ChatColor.translateAlternateColorCodes('&',loreLine));
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(53, itemStack);
    }

    private Boolean checkIfClickedAddItem(ItemStack itemStack, Player player){
        if(itemStack != null && itemStack.hasItemMeta()){
            if(itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.configurator.getLanguageConfiguration().getString("editor_add_biome_title")))){
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
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&aEnter the &5biome &ainto the"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&achat or type &5exit &ato abort."));
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
