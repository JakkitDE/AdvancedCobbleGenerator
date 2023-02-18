package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import com.iridium.iridiumskyblock.database.Island;
import com.iridium.iridiumskyblock.database.IslandUpgrade;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions.ConfigBasedMaterial;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BlockFromTo implements Listener {
    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event){
        if (event.getBlock().getType() == Material.WATER){
            if(Main.worldList.contains(event.getBlock().getLocation().getWorld())){

                Location locPlusZ = new Location(event.getToBlock().getWorld(), event.getToBlock().getX(), event.getToBlock().getY(), event.getToBlock().getZ());
                locPlusZ.setZ(locPlusZ.getZ()+1.0);
                if(locPlusZ.getBlock().getType() == Material.MAGMA_BLOCK){
                    locPlusZ.setZ(locPlusZ.getZ()-1.0);
                    event.setCancelled(true);
                    doDelayedBlockSet(locPlusZ);
                }

                Location locMinusZ = new Location(event.getToBlock().getWorld(), event.getToBlock().getX(), event.getToBlock().getY(), event.getToBlock().getZ());
                locMinusZ.setZ(locMinusZ.getZ()-1.0);
                if(locMinusZ.getBlock().getType() == Material.MAGMA_BLOCK){
                    locMinusZ.setZ(locMinusZ.getZ()+1.0);
                    event.setCancelled(true);
                    doDelayedBlockSet(locMinusZ);
                }

                Location locPlusX = new Location(event.getToBlock().getWorld(), event.getToBlock().getX(), event.getToBlock().getY(), event.getToBlock().getZ());
                locPlusX.setX(locPlusX.getX()+1.0);
                if(locPlusX.getBlock().getType() == Material.MAGMA_BLOCK){
                    locPlusX.setX(locPlusX.getX()-1.0);
                    event.setCancelled(true);
                    doDelayedBlockSet(locPlusX);
                }

                Location locMinusX = new Location(event.getToBlock().getWorld(), event.getToBlock().getX(), event.getToBlock().getY(), event.getToBlock().getZ());
                locMinusX.setX(locMinusX.getX()-1.0);
                if(locMinusX.getBlock().getType() == Material.MAGMA_BLOCK){
                    locMinusX.setX(locMinusX.getX()+1.0);
                    event.setCancelled(true);
                    doDelayedBlockSet(locMinusX);
                }
            }
        }
    }

    private Boolean isOnIsland(Location loc){
        IridiumSkyblockAPI api = Main.iridiumSkyblockAPI;
        Optional<Island> island = api.getIslandViaLocation(loc);
        if(island == null || !(island.isPresent())){
            return false;
        }else{
            return true;
        }
    }

    private Island getIsland (Location loc){
        IridiumSkyblockAPI api = Main.iridiumSkyblockAPI;
        Optional<Island> island = api.getIslandViaLocation(loc);
        return island.get();
    }

    private int getCobblerLevel (Island island){
        return Main.iridiumSkyblockAPI.getIslandUpgrade(island, "generator").getLevel();
    }
    private void doDelayedBlockSet(Location loc){
        if(isOnIsland(loc) == true){
            Island island = getIsland(loc);
            Material material = new ConfigBasedMaterial(loc, getCobblerLevel(island)).getMaterial();
            Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {
                public void run(){
                    loc.getBlock().setType(material);
                    if(Main.debugMode == true){
                        island.getOwner().getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lACG &f-> &aBlock set."));
                    }
                    if(!(Main.settings.getString("cobble_generator_sound").equalsIgnoreCase("none"))){
                        loc.getWorld().playSound(loc, Sound.valueOf(Main.settings.getString("cobble_generator_sound")), 1, 1);
                    }
                    if(!(Main.settings.getString("cobble_generator_effect").equalsIgnoreCase("none"))){
                        loc.getWorld().playEffect(loc, Effect.valueOf(Main.settings.getString("cobble_generator_effect")), 1);
                    }


                }
            }, Main.settings.getInt("ticksPerBlockSet"));
        }
    }
}
