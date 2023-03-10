package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.plugincommands;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EditorCommand {
    private Player player;
    private Inventory inventory;
    public EditorCommand(CommandSender sender){
        if(sender instanceof Player){
            this.player = (Player) sender;
            setupInventory();
            openInventoryForPlayer(player, this.inventory);
            Main.editorInventoryBiomesList.add(this.inventory);
        }else{
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.language.getString("prefix")+Main.language.getString("command_executor_must_be_player")));
        }
    }

    private void openInventoryForPlayer(Player player, Inventory inventory){
        this.player.openInventory(this.inventory);
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
                break;
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
