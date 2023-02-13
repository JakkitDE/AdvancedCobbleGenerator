package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import com.iridium.iridiumskyblock.database.Island;
import com.iridium.iridiumskyblock.database.IslandUpgrade;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class BlockFromTo implements Listener {
    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event){
        if (event.getBlock().getType() == Material.WATER){
            if(event.getBlock().getLocation().distance(Main.plugin.getServer().getPlayer("Kadnick").getLocation()) <= 5){
                Player player = Main.plugin.getServer().getPlayer("Kadnick");

                Location locPlusZ = new Location(event.getToBlock().getWorld(), event.getToBlock().getX(), event.getToBlock().getY(), event.getToBlock().getZ());
                locPlusZ.setZ(locPlusZ.getZ()+1.0);
                if(locPlusZ.getBlock().getType() == Material.MAGMA_BLOCK){
                    locPlusZ.setZ(locPlusZ.getZ()-1.0);
                    event.setCancelled(true);
                    //locPlusZ.getBlock().setType(Material.STONE);
                    doDelayedBlockSet(locPlusZ);
                }

                Location locMinusZ = new Location(event.getToBlock().getWorld(), event.getToBlock().getX(), event.getToBlock().getY(), event.getToBlock().getZ());
                locMinusZ.setZ(locMinusZ.getZ()-1.0);
                if(locMinusZ.getBlock().getType() == Material.MAGMA_BLOCK){
                    locMinusZ.setZ(locMinusZ.getZ()+1.0);
                    event.setCancelled(true);
                    //locMinusZ.getBlock().setType(Material.STONE);
                    doDelayedBlockSet(locMinusZ);
                }

                Location locPlusX = new Location(event.getToBlock().getWorld(), event.getToBlock().getX(), event.getToBlock().getY(), event.getToBlock().getZ());
                locPlusX.setX(locPlusX.getX()+1.0);
                if(locPlusX.getBlock().getType() == Material.MAGMA_BLOCK){
                    locPlusX.setX(locPlusX.getX()-1.0);
                    event.setCancelled(true);
                    //locPlusX.getBlock().setType(Material.STONE);
                    doDelayedBlockSet(locPlusX);
                }

                Location locMinusX = new Location(event.getToBlock().getWorld(), event.getToBlock().getX(), event.getToBlock().getY(), event.getToBlock().getZ());
                locMinusX.setX(locMinusX.getX()-1.0);
                if(locMinusX.getBlock().getType() == Material.MAGMA_BLOCK){
                    locMinusX.setX(locMinusX.getX()+1.0);
                    event.setCancelled(true);
                    //locMinusX.getBlock().setType(Material.STONE);
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
        return Main.iridiumSkyblockAPI.getIslandUpgrade(island, "oresUpgrade").getLevel();
    }


    private void doDelayedBlockSet(Location loc){
        if(isOnIsland(loc) == true){
            Island island = getIsland(loc);
            Main.plugin.getServer().broadcastMessage("oresUpgrade Level: "+getCobblerLevel(island));
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run(){
                loc.getBlock().setType(Material.STONE);
            }
        }, 10);
    }
}