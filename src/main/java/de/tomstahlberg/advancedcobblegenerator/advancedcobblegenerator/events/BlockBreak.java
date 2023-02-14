package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions.CheckMagmaBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(Main.worldList.contains(event.getBlock().getLocation().getWorld())){
            Player player = event.getPlayer();
            if(new CheckMagmaBlock(event.getBlock().getLocation()).isCobblerBlock()){
                Main.cobblerBlocksBroken.put(event.getBlock().getLocation(), event.getPlayer());
            }
        }

    }
}
