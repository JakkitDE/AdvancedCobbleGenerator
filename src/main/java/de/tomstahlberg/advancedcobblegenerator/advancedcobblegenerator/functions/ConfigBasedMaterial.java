package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ConfigBasedMaterial {
    //private HashMap<Biome, HashMap<Integer, List<Material>>> generatorMap;
    private Material material;
    public ConfigBasedMaterial(Location loc, Integer upgradeLevel){
        List<Material> materialList = new ArrayList<Material>();
        String biomeString = "";
        if(Main.configurator.getGeneratorConfiguration().contains("biomes."+loc.getBlock().getBiome())){
            //use blocks biome
            biomeString = loc.getBlock().getBiome().name();
        }else{
            //use default biome
            biomeString = Main.configurator.getGeneratorConfiguration().getString("default");
        }

        for(String materialString : Main.configurator.getGeneratorConfiguration().getConfigurationSection(
                "biomes."+biomeString+"."+upgradeLevel
        ).getKeys(false)){
            try{
                materialList.add(Material.valueOf(materialString));
            }catch (Exception e){
                Main.plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&aACG &e-> &c"+biomeString+"."+upgradeLevel+"."+materialString+" is no material."));
            }
        }
        Integer pick = getRandomInteger(0, materialList.size() - 1);
        this.material = materialList.get(pick);

    }

    public Material getMaterial (){
        return this.material;
    }

    private Integer getRandomInteger(int min, int max){
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }
}
