package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerChatBiomeEditor implements Listener {
    private Inventory inventory;
    private Player player;

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        this.player = player;
        /* BIOM ADDING MODE */
        if(Main.editorBiomeAddMode.contains(player)){
            event.setCancelled(true);
            try{
                if(Biome.valueOf(event.getMessage().toUpperCase()) != null){
                    Biome biome = Biome.valueOf((event.getMessage().toUpperCase()));
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
                    List<Material> materialList = new ArrayList<Material>();
                    materialList.add(Material.STONE);
                    HashMap<Integer, List<Material>> level = new HashMap<Integer, List<Material>>();
                    level.put(1, materialList);

                    Main.generatorMap.put(biome, level);
                    //reopen Biomes list GUI
                    setupInventory();
                    openInventoryForPlayer(this.player, this.inventory);

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
    private void openInventoryForPlayer(Player player, Inventory inventory){
        Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {
            @Override
            public void run() {
                player.openInventory(inventory);
                Main.editorInventoryList.add(inventory);
            }
        },10);
    }
    private void setupInventory(){
        Inventory inventory;
        inventory = Bukkit.createInventory(this.player, 54, ChatColor.translateAlternateColorCodes('&',"&eEditor - Biom"));

        int i = 0;
        for(ItemStack itemStack : getBiomeList()){
            if(i<45){
                inventory.setItem(i, itemStack);
                i++;
            }else{
                return;
            }
        }

        inventory.setItem(53, getAddBiomeItem());
        this.inventory = inventory;
    }
    private ItemStack getAddBiomeItem(){
        ItemStack itemStack = new ItemStack(Material.GREEN_BANNER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aAdd Biome"));
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.translateAlternateColorCodes('&',"&2Click to add a"));
        lore.add(ChatColor.translateAlternateColorCodes('&',"&2new biome."));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private List<ItemStack> getBiomeList(){
        List<ItemStack> biomeList = new ArrayList<ItemStack>();
        for(Biome biome : Main.generatorMap.keySet()){
            ItemStack itemStack = new ItemStack(Material.GRASS_BLOCK);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&l"+biome.name()));
            List<String> lore = new ArrayList<String>();
            lore.add(ChatColor.translateAlternateColorCodes('&',"&2Leftclick, to setup"));
            lore.add(ChatColor.translateAlternateColorCodes('&',"&2this biome."));
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            biomeList.add(itemStack);
        }
        return biomeList;
    }
}
