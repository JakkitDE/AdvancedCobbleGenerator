package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.functions;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Configurator {
    File generatorFile;
    FileConfiguration generatorConfiguration;

    File settingsFile;
    FileConfiguration settingsConfiguration;

    File upgradesFile;
    FileConfiguration upgradesConfiguration;

    File playerdataFile;
    FileConfiguration playerdataConfiguration;

    File languageFile;
    FileConfiguration languageConfiguration;


    public Configurator() throws IOException {
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

        this.upgradesFile = new File(Main.plugin.getDataFolder(), "upgrades.yml");
        if(!(upgradesFileExists())){
            this.upgradesConfiguration = new YamlConfiguration();
            setupConfiguration(this.upgradesConfiguration, "upgrades");
        }else{
            this.upgradesConfiguration = YamlConfiguration.loadConfiguration(upgradesFile);
        }

        this.playerdataFile = new File(Main.plugin.getDataFolder(), "playerdata.yml");
        if(!(playerdataFileExists())){
            this.playerdataConfiguration = new YamlConfiguration();
            setupConfiguration(this.playerdataConfiguration, "playerdata");
        }else{
            this.playerdataConfiguration = YamlConfiguration.loadConfiguration(playerdataFile);
        }

        this.languageFile = new File(Main.plugin.getDataFolder(), "language.yml");
        if(!(languageFileExists())){
            this.languageConfiguration = new YamlConfiguration();
            setupConfiguration(this.languageConfiguration, "language");
        }else{
            this.languageConfiguration = YamlConfiguration.loadConfiguration(languageFile);
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
    public Boolean getJetsMinionsHook(){
        return Boolean.valueOf(this.settingsConfiguration.getString("hook_jetsminions"));
    }
    private boolean upgradesFileExists(){
        if(this.upgradesFile.exists()){
            return true;
        }else{
            return false;
        }
    }

    private boolean playerdataFileExists(){
        if(this.playerdataFile.exists()){
            return true;
        }else{
            return false;
        }
    }
    public void saveGeneratorSettings() throws IOException {
        this.getGeneratorConfiguration().save(this.generatorFile);
    }
    private boolean languageFileExists(){
        if(this.languageFile.exists()){
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
    public FileConfiguration getUpgradesConfiguration(){
        return this.upgradesConfiguration;
    }
    public FileConfiguration getPlayerdataConfiguration(){
        return this.playerdataConfiguration;
    }
    public List<World> getWorlds(){
        List<World> worldList = new ArrayList<World>();
        List<String> worldListStrings = (List<String>) this.settingsConfiguration.getList("worlds");
        for(String worldString : worldListStrings){
            try{
                worldList.add(Main.plugin.getServer().getWorld(worldString));
                Main.plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aACG &e-> &fWorld &5"+worldString+" &f successfully loaded."));
            }catch(Exception e){
                Main.plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aACG &e-> &fWorld &5"+worldString+" &f failed loading due to not existing."));
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
            List<String> comments = new ArrayList<String>();
            comments.add("In which worlds you want to enable the plugin?");
            configuration.setComments("",comments);
            listOfWorldNames.add("world");
            configuration.set("worlds", listOfWorldNames);

            comments.clear();
            comments.add("Every xx ticks to spawn a new block in the cobble generator..");
            comments.add("20 ticks = 1 second.");
            configuration.setComments("",comments);
            configuration.set("ticksPerBlockSet", 10);


            configuration.set("hook_iridiumskyblock", false);
            configuration.set("hook_jetsminions", false);
            comments.clear();
            comments.add("For cobble_generator_sound and cobble_generator_effect");
            comments.add("you can set NONE to remove sound and/or effect. Use upper case only.");
            configuration.setComments("",comments);
            configuration.set("cobble_generator_sound", "BLOCK_LAVA_EXTINGUISH");
            configuration.set("cobble_generator_effect", "SMOKE");

            comments.clear();
            comments.add("Do not use AIR as placeholder, otherwise the inventory works like a one-way disposal.");
            configuration.setComments("",comments);
            configuration.set("cobble_generator_upgrade_material", "GRASS_BLOCK");
            configuration.set("cobble_generator_upgrade_placeholder_material", "GRAY_STAINED_GLASS_PANE");

            configuration.save(this.settingsFile);
        } else if (configCategory.equalsIgnoreCase("upgrades")) {
            configuration.set("2.display_item","DIRT");
            List<String> lore = new ArrayList<String>();
            lore.add("&aPrice: &6&l500 &e&l$");
            configuration.set("2.lore",lore);
            configuration.set("2.price",500.0);
            configuration.save(this.upgradesFile);
        } else if (configCategory.equalsIgnoreCase("playerdata")) {
            configuration.save(this.playerdataFile);
        } else if (configCategory.equalsIgnoreCase("language")) {
            configuration.set("prefix", "&f[&e&lACG&f] &7> ");
            configuration.set("no_permissions_usage", "&cSorry, but you do not have permissions to use this command.");
            configuration.set("no_permissions_admin", "&cSorry, but you do not have permissions to administrate that.");
            configuration.set("no_permissions_upgrade", "&cSorry, but you do not have permissions to upgrade.");
            List<String> helpListPlayer = new ArrayList<String>();
            helpListPlayer.add("&f---------------");
            helpListPlayer.add("&3&lAdvanced Cobble Generator");
            helpListPlayer.add("");
            helpListPlayer.add("&e/acg upgrade &7to upgrade your cobble generator.");
            helpListPlayer.add("Plugin made by Tom | Kadnick");
            helpListPlayer.add("&f---------------");
            configuration.set("help_menu_players", helpListPlayer);

            List<String> helpListAdmins = new ArrayList<String>();
            helpListAdmins.add("&f---------------");
            helpListAdmins.add("&3&lAdvanced Cobble Generator");
            helpListAdmins.add("");
            helpListAdmins.add("&e/acg upgrade &7to upgrade your cobble generator.");
            helpListAdmins.add("&e/acg reload &7to reload acg.");
            helpListAdmins.add("Plugin made by Tom | Kadnick");
            helpListAdmins.add("&f---------------");
            configuration.set("help_menu_admins", helpListAdmins);

            configuration.set("wrong_usage","&cWrong usage. Please type &e/cobble help &cfor more informations.");
            configuration.set("reload_successfull", "&aSuccessfully reloaded &eACG&a.");
            configuration.set("upgraded_but_iridium_hook", "&cPlease use &e/is upgrade &cto upgrade you cobble generator.");
            configuration.set("upgraded_but_superior_hook", "&cPlease use &e/is upgrade &cto upgrade you cobble generator.");

            configuration.set("upgrade_inventory_title", "&5Upgrade Panel");
            configuration.set("command_executor_must_be_player", "&cOnly players can run that command.");

            configuration.set("upgrade_item_title", "&e&lInfo");

            List<String> upgradeItemLoreUpgradable = new ArrayList<String>();
            upgradeItemLoreUpgradable.add("&7Current level: &a%current_cobbler_level%");
            upgradeItemLoreUpgradable.add("&7Upgrade Price: &a%upgrade_price% &2$");
            configuration.set("upgrade_item_lore_upgradable", upgradeItemLoreUpgradable);

            List<String> upgradeItemLoreMaxed = new ArrayList<String>();
            upgradeItemLoreMaxed.add("&7Current level: &a%current_cobbler_level%");
            upgradeItemLoreMaxed.add("&7You already have reached the maximum level.");
            configuration.set("upgrade_item_lore_maxed", upgradeItemLoreMaxed);

            configuration.set("maximum_level_already_reached", "&cYou already have reached the maximum level.");
            configuration.set("not_enough_money_for_upgrade", "&cYou can't afford that.");
            configuration.set("upgrade_successfull", "&aYou successfully upgraded your cobble generator.");

            List<String> addBiomeItemLore = new ArrayList<String>();
            addBiomeItemLore.add("&7Click to add");
            addBiomeItemLore.add("&7a new biome.");
            configuration.set("editor_add_biome_lore", addBiomeItemLore);
            configuration.set("editor_add_biome_title", "&aAdd biome");

            List<String> addLevelItemLore = new ArrayList<String>();
            addLevelItemLore.add("&7Click to add");
            addLevelItemLore.add("&7a new level.");
            configuration.set("editor_add_level_lore", addLevelItemLore);
            configuration.set("editor_add_level_title", "&aAdd level");

            List<String> editor_edit = new ArrayList<String>();
            editor_edit.add("&7Leftclick to edit.");
            editor_edit.add("&7Q/Drop to delete.");
            configuration.set("editor_edit_lore", editor_edit);

            configuration.set("editor_item_edit_lore_weight", "&eWeight:&a");

            List<String> editor_item_edit = new ArrayList<String>();
            editor_item_edit.add("&7Left-Click to decrease weight.");
            editor_item_edit.add("&7SHIFT+Left-Click to decrease weight in 10s.");
            editor_item_edit.add("&7Right-Click to increase weight.");
            editor_item_edit.add("&7SHIFT+Right-Click to increase weight in 10s.");
            editor_item_edit.add("&7Q/DROP to delete.");
            configuration.set("editor_item_edit_lore", editor_item_edit);

            configuration.save(this.languageFile);
        }
    }
    public FileConfiguration getLanguageConfiguration(){
        return this.languageConfiguration;
    }
    public Boolean getIridiumHook(){
        return this.settingsConfiguration.getBoolean("hook_iridiumskyblock");
    }
    public Boolean getSuperiorSkyblockHook(){
        return Boolean.valueOf(this.settingsConfiguration.getString("hook_superiorskyblock"));
    }
    public HashMap<UUID, Integer> loadPlayerData(){
        HashMap<UUID, Integer> playerdata = new HashMap<UUID, Integer>();
        for(String uuidString : this.playerdataConfiguration.getConfigurationSection("").getKeys(false)){
            playerdata.put(UUID.fromString(uuidString), this.playerdataConfiguration.getInt(uuidString));
        }
        return playerdata;
    }
    public void savePlayerData(HashMap<UUID, Integer> playerdata) throws IOException {
        FileConfiguration playerdataConfig = new YamlConfiguration();
        for(UUID uuid : playerdata.keySet()){
            playerdataConfig.set(uuid.toString(), playerdata.get(uuid));
        }
        playerdataConfig.save(playerdataFile);
    }
    public void checkUpdateVariables () throws IOException {
        if(this.settingsConfiguration.getString("hook_jetsminions", "") == ""){
            this.settingsConfiguration.set("hook_jetsminions", "false");
        }
        if(this.settingsConfiguration.getString("hook_superiorskyblock", "") == ""){
            this.settingsConfiguration.set("hook_superiorskyblock", "false");
        }
        this.settingsConfiguration.save(this.settingsFile);
    }
}
