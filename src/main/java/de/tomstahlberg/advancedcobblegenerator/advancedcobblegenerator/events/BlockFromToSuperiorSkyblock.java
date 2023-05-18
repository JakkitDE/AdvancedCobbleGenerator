package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblock;
import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.config.SettingsManager;
import com.bgsoftware.superiorskyblock.api.handlers.*;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.scripts.IScriptEngine;
import com.bgsoftware.superiorskyblock.api.world.event.WorldEventsManager;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions.ConfigBasedMaterial;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class BlockFromToSuperiorSkyblock implements Listener {
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
        if(SuperiorSkyblockAPI.getIslandAt(loc) != null){
            return true;
        }else{
            return false;
        }
    }

    private Island getIsland (Location loc){
        return SuperiorSkyblockAPI.getIslandAt(loc);
    }

    private int getCobblerLevel (Island island){
        if(island.getUpgrades().get("generator-rates") != null){
            return island.getUpgrades().get("generator-rates");
        }else{
            return 1;
        }
    }
    private void doDelayedBlockSet(Location loc){
        if(isOnIsland(loc) == true){
            Island island = getIsland(loc);
            Material material = new ConfigBasedMaterial(loc, getCobblerLevel(island)).getMaterial();
            Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {
                public void run(){
                    loc.getBlock().setType(material);
                    if(Main.debugMode == true){
                        island.getOwner().asPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lACG &f-> &aBlock set."));
                    }
                    if(!(Main.settings.getString("cobble_generator_sound").equalsIgnoreCase("none"))){
                        loc.getWorld().playSound(loc, Sound.valueOf(Main.settings.getString("cobble_generator_sound")), 0.2F, 1.0F);
                    }
                    if(!(Main.settings.getString("cobble_generator_effect").equalsIgnoreCase("none"))){
                        loc.getWorld().playEffect(loc, Effect.valueOf(Main.settings.getString("cobble_generator_effect")), 1);
                    }
                }
            }, Main.settings.getInt("ticksPerBlockSet"));
        }
    }
}
