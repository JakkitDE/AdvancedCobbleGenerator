package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GeneratorMap {
    private HashMap<Biome, HashMap<Integer, List<Material>>> generatorMap;

    public GeneratorMap(FileConfiguration config){
        generatorMap = new HashMap<Biome, HashMap<Integer, List<Material>>>();
        HashMap<Integer, List<Material>> levelList = new HashMap<Integer, List<Material>>();
        for(String biomeString : config.getConfigurationSection("").getKeys(false)){
            Biome biome = Biome.valueOf(biomeString);
            for(String levelString : config.getConfigurationSection(biomeString).getKeys(false)){
                List<Material> itemsList = new ArrayList<Material>();
                for(String materialString : config.getConfigurationSection(biomeString+"."+levelString).getKeys(false)){
                    Material material = Material.valueOf(materialString);
                    Integer amount = config.getInt(biomeString+"."+levelString+"."+materialString);

                    for(int i = 0;i<amount;i++){
                        itemsList.add(material);
                    }
                }
                levelList.put(Integer.valueOf(levelString), itemsList);
            }
            this.generatorMap.put(biome, levelList);
        }
    }

    public HashMap<Biome, HashMap<Integer, List<Material>>> getGeneratorMap(){
        return this.generatorMap;
    }
}
