package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Configuration {
    File generatorFile;
    FileConfiguration generatorConfiguration;

    File settingsFile;
    FileConfiguration settingsConfiguration;
    public Configuration() throws IOException {
        this.generatorFile = new File(Main.plugin.getDataFolder(), "generator.yml");
        if(!(configurationFileExists())){
            this.generatorConfiguration = new YamlConfiguration();
            setupConfiguration(this.generatorConfiguration, "generator");
        }else{
            this.generatorConfiguration = YamlConfiguration.loadConfiguration(generatorFile);
        }

        this.settingsFile = new File(Main.plugin.getDataFolder(), "settings.yml");
        if(!(settingsFileExists())){
            this.settingsConfiguration = new YamlConfiguration();
            setupConfiguration(this.settingsConfiguration, "settings");
        }else{
            this.settingsConfiguration = YamlConfiguration.loadConfiguration(settingsFile);
        }

    }

    private boolean configurationFileExists(){
        if(this.generatorFile.exists()){
            return true;
        }else{
            return false;
        }
    }

    private boolean settingsFileExists(){
        if(this.settingsFile.exists()){
            return true;
        }else{
            return false;
        }
    }
    public FileConfiguration getGeneratorConfiguration(){
        return this.generatorConfiguration;
    }
    public FileConfiguration getSettingsConfiguration(){
        return this.settingsConfiguration;
    }
    public List<World> getWorlds(){
        List<World> worldList = new ArrayList<World>();
        List<String> worldListStrings = (List<String>) this.settingsConfiguration.getList("worlds");
        for(String worldString : worldListStrings){
            try{
                worldList.add(Main.plugin.getServer().getWorld(worldString));
                Main.plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e"+worldString+"&a Welt registriert."));
            }catch(Exception e){
                Main.plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e"+worldString+"&c ist keine gültige Welt."));
            }
        }
        return worldList;
    }
    private void setupConfiguration(FileConfiguration configuration, String configCategory) throws IOException {
        if(configCategory.equalsIgnoreCase("generator")){
            configuration.set("default", "FOREST");

            configuration.set("biomes.FOREST.1.STONE", 31);
            configuration.set("biomes.FOREST.1.COBBLESTONE", 31);
            configuration.set("biomes.FOREST.1.COAL_ORE", 17);
            configuration.set("biomes.FOREST.1.IRON_ORE", 12);
            configuration.set("biomes.FOREST.1.GOLD_ORE", 7);
            configuration.set("biomes.FOREST.1.DIAMOND_ORE", 2);

            configuration.set("biomes.FOREST.2.STONE", 31);
            configuration.set("biomes.FOREST.2.COBBLESTONE", 31);
            configuration.set("biomes.FOREST.2.COAL_ORE", 17);
            configuration.set("biomes.FOREST.2.IRON_ORE", 12);
            configuration.set("biomes.FOREST.2.GOLD_ORE", 7);
            configuration.set("biomes.FOREST.2.DIAMOND_ORE", 2);

            configuration.set("biomes.DESERT.1.SAND", 100);
            configuration.set("biomes.DESERT.2.SAND", 80);
            configuration.set("biomes.DESERT.2.DIRT", 20);
            configuration.save(this.generatorFile);
        } else if (configCategory.equalsIgnoreCase("settings")) {
            List<String> listOfWorldNames = new ArrayList<String>();
            listOfWorldNames.add("world");
            configuration.set("worlds", listOfWorldNames);

            configuration.set("ticksPerBlockSet", 10);

            configuration.save(this.settingsFile);
        }
    }
}
