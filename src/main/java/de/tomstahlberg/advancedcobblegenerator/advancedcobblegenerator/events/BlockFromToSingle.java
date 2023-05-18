package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import com.iridium.iridiumskyblock.database.Island;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions.ConfigBasedMaterial;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.Optional;

public class BlockFromToSingle implements Listener {
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
    private int getCobblerLevel(Player player){
        return Main.playerdata.get(player.getUniqueId());
    }


    private void doDelayedBlockSet(Location loc){
        if(Main.cobblerBlocksBroken.containsKey(loc)){
            Player player = Main.cobblerBlocksBroken.get(loc);
            Material material = new ConfigBasedMaterial(loc, getCobblerLevel(player)).getMaterial();
            Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {
                public void run(){
                    loc.getBlock().setType(material);
                    if(!(Main.settings.getString("cobble_generator_sound").equalsIgnoreCase("none"))){
                        loc.getWorld().playSound(loc, Sound.valueOf(Main.settings.getString("cobble_generator_sound")), 0.2F, 1.0F);
                    }
                    if(!(Main.settings.getString("cobble_generator_effect").equalsIgnoreCase("none"))){
                        loc.getWorld().playEffect(loc, Effect.valueOf(Main.settings.getString("cobble_generator_effect")), 1);
                    }
                }
            }, Main.settings.getInt("ticksPerBlockSet"));
        }else{
            loc.getBlock().setType(new ConfigBasedMaterial(loc, 1).getMaterial());
            if(!(Main.settings.getString("cobble_generator_sound").equalsIgnoreCase("none"))){
                loc.getWorld().playSound(loc, Sound.valueOf(Main.settings.getString("cobble_generator_sound")), 0.2F, 1.0F);
            }
            if(!(Main.settings.getString("cobble_generator_effect").equalsIgnoreCase("none"))){
                loc.getWorld().playEffect(loc, Effect.valueOf(Main.settings.getString("cobble_generator_effect")), 1);
            }
        }

    }
}
