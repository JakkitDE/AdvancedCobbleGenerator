package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.plugincommands;

import com.iridium.iridiumskyblock.database.Island;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandUpgrade {
    public CommandUpgrade(CommandSender sender){
        if(sender instanceof Player){
            if(Main.iridiumHook == true){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.language.getString("prefix")+Main.language.getString("upgraded_but_iridium_hook")));
            }else{
                Player player = (Player) sender;
                Inventory inventory = Bukkit.createInventory(player, 9, ChatColor.translateAlternateColorCodes('&',Main.language.getString("prefix")+Main.language.getString("upgrade_inventory_title")));
                fillInventory(inventory, player);
                player.openInventory(inventory);
                Main.upgradeInventoryList.add(inventory);
            }

        }else{
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.language.getString("prefix")+Main.language.getString("command_executor_must_be_player")));
        }

    }
    private Inventory fillInventory(Inventory inventory, Player player){
        ItemStack itemStack = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',Main.language.getString("prefix")+Main.language.getString("upgrade_item_title")));
        List<String> lore = new ArrayList<String>();
        if(isMaximumLevel(player)){
            for(String loreLine : Main.language.getStringList("upgrade_item_lore_maxed")){
                lore.add(ChatColor.translateAlternateColorCodes('&',loreLine));
            }
            lore = replaceLorePlaceholder(lore, player);
        }else{
            for(String loreLine : Main.language.getStringList("upgrade_item_lore_upgradable")){
                lore.add(ChatColor.translateAlternateColorCodes('&',loreLine));
            }
            lore = replaceLorePlaceholder(lore, player);
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(4, itemStack);
        setPlaceHolder(inventory, 0);
        setPlaceHolder(inventory, 1);
        setPlaceHolder(inventory, 2);
        setPlaceHolder(inventory, 3);

        setPlaceHolder(inventory, 5);
        setPlaceHolder(inventory, 6);
        setPlaceHolder(inventory, 7);
        setPlaceHolder(inventory, 8);
        return inventory;
    }

    private Integer getCurrentCobblerLevel(Player player){
        return Main.playerdata.get(player.getUniqueId());
    }
    private void setPlaceHolder(Inventory inventory, Integer slot){
        ItemStack itemStack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&'," "));
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(slot, itemStack);
    }
    private Boolean isMaximumLevel(Player player){
        Integer nextCobblerLevel = getCurrentCobblerLevel(player)+1;
        if(Main.configurator.getUpgradesConfiguration().contains(nextCobblerLevel.toString())){
            return false;
        }else{
            return true;
        }
    }
    private Double getCobblerUpgradePrice(Player player){
        int futureCobblerLevel = getCurrentCobblerLevel(player)+1;
        return Main.configurator.getUpgradesConfiguration().getDouble(futureCobblerLevel+".price");
    }

    private List<String> replaceLorePlaceholder(List<String> lore, Player player){
        List<String> newLore = new ArrayList<String>();
        for(String loreLine : lore){
            loreLine = loreLine.replace("%current_cobbler_level%", getCurrentCobblerLevel(player).toString());
            loreLine = loreLine.replace("%upgrade_price%", getCobblerUpgradePrice(player).toString());
            newLore.add(loreLine);
        }
        return newLore;
    }
}
