package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Configuration {
    File file;
    FileConfiguration configuration;
    public Configuration() throws IOException {
        this.file = new File(Main.plugin.getDataFolder(), "generator.yml");
        if(!(configurationFileExists())){
            this.configuration = new YamlConfiguration();
            setupConfiguration(this.configuration);
        }else{
            this.configuration = YamlConfiguration.loadConfiguration(file);
        }

    }

    private boolean configurationFileExists(){
        if(this.file.exists()){
            return true;
        }else{
            return false;
        }
    }
    public FileConfiguration getConfiguration(){
        return this.configuration;
    }
    private void setupConfiguration(FileConfiguration configuration) throws IOException {
        configuration.set("FOREST.1.STONE", 31);
        configuration.set("FOREST.1.COBBLESTONE", 31);
        configuration.set("FOREST.1.COAL_ORE", 17);
        configuration.set("FOREST.1.IRON_ORE", 12);
        configuration.set("FOREST.1.GOLD_ORE", 7);
        configuration.set("FOREST.1.DIAMOND_ORE", 2);
        configuration.save(this.file);
    }
}
