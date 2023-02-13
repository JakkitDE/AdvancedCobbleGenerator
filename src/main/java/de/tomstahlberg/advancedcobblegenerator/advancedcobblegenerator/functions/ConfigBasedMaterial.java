package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ConfigBasedMaterial {
    private HashMap<Biome, HashMap<Integer, List<Material>>> generatorMap;
    private Material material;
    public ConfigBasedMaterial(Location loc, Integer upgradeLevel){
        this.generatorMap = Main.generatorMap;
        Biome biome = loc.getBlock().getBiome();
        if(generatorMap.containsKey(biome)){
            HashMap<Integer, List<Material>> levelsList = this.generatorMap.get(biome);
            List<Material> materialList = levelsList.get(upgradeLevel);
            Integer pick = getRandomInteger(0, materialList.size() - 1);
            this.material = materialList.get(pick);
        }else{
            biome = Main.defaultBiome;
            HashMap<Integer, List<Material>> levelsList = this.generatorMap.get(biome);
            List<Material> materialList = levelsList.get(upgradeLevel);
            Integer pick = getRandomInteger(0, materialList.size() - 1);
            this.material = materialList.get(pick);
        }

    }

    public Material getMaterial (){
        return this.material;
    }

    private Integer getRandomInteger(int min, int max){
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }
}