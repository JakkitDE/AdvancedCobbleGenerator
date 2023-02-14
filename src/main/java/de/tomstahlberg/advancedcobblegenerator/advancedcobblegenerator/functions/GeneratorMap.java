package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GeneratorMap {
    private HashMap<Biome, HashMap<Integer, List<Material>>> globalGeneratorMap;
    FileConfiguration config;
    public GeneratorMap(FileConfiguration config){
        this.config = config;
        globalGeneratorMap = new HashMap<Biome, HashMap<Integer, List<Material>>>();

        //für jedes Biom
        for(String biomeString : config.getConfigurationSection("biomes").getKeys(false)){
            Biome biome = Biome.valueOf(biomeString);

            HashMap<Integer, List<Material>> levelList = new HashMap<Integer, List<Material>>();
            //für jedes Level
            for(String levelString : config.getConfigurationSection("biomes."+biomeString).getKeys(false)){
                List<Material> itemsList = new ArrayList<Material>();
                //für jedes Material
                for(String materialString : config.getConfigurationSection("biomes."+biomeString+"."+levelString).getKeys(false)){
                    Material material = Material.valueOf(materialString);
                    Integer amount = config.getInt("biomes."+biomeString+"."+levelString+"."+materialString);

                    for(int i = 0;i<amount;i++){
                        itemsList.add(material);
                    }
                }
                levelList.put(Integer.valueOf(levelString), itemsList);
            }
            globalGeneratorMap.put(biome, levelList);

        }
    }

    public HashMap<Biome, HashMap<Integer, List<Material>>> getGeneratorMap(){
        return this.globalGeneratorMap;
    }
    public Biome getDefaultBiome(){
        String biomeString = this.config.getString("default");
        if(Biome.valueOf(biomeString) != null){
            return Biome.valueOf(biomeString);
        }else{
            return Biome.FOREST;
        }
    }
}
