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
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lGolden&3&lSky &8x &cBitte verwende &e/is upgrade&c."));
            }else{
                Player player = (Player) sender;
                Inventory inventory = Bukkit.createInventory(player, 9, ChatColor.translateAlternateColorCodes('&',"&5&lUpgrades"));
                fillInventory(inventory, player);
                player.openInventory(inventory);
                Main.upgradeInventoryList.add(inventory);
            }

        }else{
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lGolden&3&lSky &8x &cDer Upgrade Befehl kann nur von einem Spieler ausgeführt werden."));
        }

    }
    private Inventory fillInventory(Inventory inventory, Player player){
        ItemStack itemStack = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&e&lInfo"));
        List<String> lore = new ArrayList<String>();
        if(isMaximumLevel(player)){
            lore.add(ChatColor.translateAlternateColorCodes('&',"&7Aktuelles Level: &a%current_cobbler_level%"));
            lore.add(ChatColor.translateAlternateColorCodes('&',"&6&lMaximales Level erreicht."));
            lore = replaceLorePlaceholder(lore, player);
        }else{
            lore.add(ChatColor.translateAlternateColorCodes('&',"&7Aktuelles Level: &a%current_cobbler_level%"));
            lore.add(ChatColor.translateAlternateColorCodes('&',"&7Upgrade Preis: &a%upgrade_price%"));
            lore = replaceLorePlaceholder(lore, player);
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(4, itemStack);
        return inventory;
    }

    private Integer getCurrentCobblerLevel(Player player){
        return Main.playerdata.get(player.getUniqueId());
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
        player.sendMessage(newLore.toString());
        return newLore;
    }
}
