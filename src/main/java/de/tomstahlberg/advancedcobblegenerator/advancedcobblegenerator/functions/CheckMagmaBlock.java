package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions;

import org.bukkit.Location;
import org.bukkit.Material;

public class CheckMagmaBlock {
    private Location loc ;
    public CheckMagmaBlock(Location loc){
        this.loc = loc;
    }

    public Boolean isCobblerBlock(){
        Location plusX = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ());
        Location minusX = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ());
        Location plusZ = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()+1);
        Location minusZ = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()-1);

        if(plusX.getBlock().getType() == Material.MAGMA_BLOCK){
            return true;
        }else if(minusX.getBlock().getType() == Material.MAGMA_BLOCK){
            return true;
        }else if(plusZ.getBlock().getType() == Material.MAGMA_BLOCK){
            return true;
        }else if(minusZ.getBlock().getType() == Material.MAGMA_BLOCK){
            return true;
        }else{
            return false;
        }
    }
}
