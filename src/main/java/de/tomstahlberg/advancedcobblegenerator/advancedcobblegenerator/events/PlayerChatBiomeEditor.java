package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import net.milkbowl.vault.chat.Chat;
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
                    for(String chatLine : Main.configurator.getLanguageConfiguration().getStringList("biome_added_successfully_chat_message")){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',chatLine));
                    }
                    List<Material> materialList = new ArrayList<Material>();
                    materialList.add(Material.STONE);
                    HashMap<Integer, List<Material>> level = new HashMap<Integer, List<Material>>();
                    level.put(1, materialList);

                    //Main.generatorMap.put(biome, level);
                    Main.configurator.getGeneratorConfiguration().set("biomes."+biome.name()+".1.STONE",1);
                    //reopen Biomes list GUI
                    setupInventory();
                    openInventoryForPlayer(this.player, this.inventory);

                }
            }catch(Exception e){
                if(event.getMessage().equalsIgnoreCase("exit")){
                    Main.editorBiomeAddMode.remove(player);
                    for(String chatLine : Main.configurator.getLanguageConfiguration().getStringList("biome_add_aborted_chat_message")){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',chatLine));
                    }
                }else{
                    for(String chatLine : Main.configurator.getLanguageConfiguration().getStringList("biome_add_invalid_biome_chat_message")){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',chatLine));
                    }
                }
            }
        }
    }
    private void openInventoryForPlayer(Player player, Inventory inventory){
        Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {
            @Override
            public void run() {
                player.openInventory(inventory);
                Main.editorInventoryBiomesList.add(inventory);
            }
        },10);
    }
    private void setupInventory(){
        Inventory inventory;
        inventory = Bukkit.createInventory(this.player, 54, ChatColor.translateAlternateColorCodes('&',"&eBiomes"));

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
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.configurator.getLanguageConfiguration().getString("editor_add_biome_title")));
        List<String> lore = new ArrayList<String>();
        for(String loreLine : Main.configurator.getLanguageConfiguration().getStringList("editor_add_biome_lore")){
            lore.add(ChatColor.translateAlternateColorCodes('&',loreLine));
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private List<ItemStack> getBiomeList(){
        List<ItemStack> biomeList = new ArrayList<ItemStack>();
        for(String biomeString : Main.configurator.getGeneratorConfiguration().getConfigurationSection("biomes").getKeys(false)){
            ItemStack itemStack = new ItemStack(Material.GRASS_BLOCK);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&l"+biomeString));
            List<String> lore = new ArrayList<String>();
            for(String loreLine : Main.configurator.getLanguageConfiguration().getStringList("editor_edit_lore")){
                lore.add(ChatColor.translateAlternateColorCodes('&',loreLine));
            }
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            biomeList.add(itemStack);
        }
        return biomeList;
    }
}
