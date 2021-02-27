package com.danieldusek.worksites.models;

import com.danieldusek.worksites.Worksites;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerData {
    private Worksites i;
    private Player player;
    private HashMap<String, Worksite> worksites = new HashMap<>();

    private File dataFile;
    private FileConfiguration data;

    public PlayerData(Worksites instance, Player player) {
        this.i = instance;
        this.player = player;

        bootstrapPlayerConfigFile(player);

        if (worksites.isEmpty()) {
            populateWorksites();
        }
    }


    public FileConfiguration getData() {
        if (data == null || dataFile == null) {
            bootstrapPlayerConfigFile(this.player);

            data = new YamlConfiguration();
            try {
                data.load(dataFile);
            } catch (IOException | InvalidConfigurationException e) {
                this.i.getLogger().severe("Failed to bootstrap player data. This player won't be able to use the plugin.");
                e.printStackTrace();
            }
        }

        return data;
    }

    public void addWorksite(Worksite worksite) {
        // This method will add worksite to the players worksites property (list of worksites for this player)
        this.worksites.put(worksite.getWorksiteName(), worksite);

        // And also to the file (writing it in)
        getData().set("sites." + worksite.getWorksiteName() + ".location", worksite.getLocation());
        getData().set("sites." + worksite.getWorksiteName() + ".last_used", worksite.getLastUsed());
        getData().set("sites." + worksite.getWorksiteName() + ".is_public", worksite.isPublic());
        getData().set("sites." + worksite.getWorksiteName() + ".permission", worksite.getPermission());
        saveChanges();
    }

    public void deleteWorksite(String worksiteName) {
        this.worksites.remove(worksiteName);

        // Remove the record from the file
        getData().set("sites." + worksiteName, null);
        saveChanges();
    }

    private void saveChanges() {
        try {
            getData().save(dataFile);
        } catch (IOException e) {
            this.i.getLogger().warning("Failed to save changes into player file.");
            e.printStackTrace();
        }
    }

    public void reloadData() {
        bootstrapPlayerConfigFile(this.player);
        data = new YamlConfiguration();
        try {
            data.load(dataFile);
        } catch (IOException | InvalidConfigurationException e) {
            this.i.getLogger().severe("Failed to reload player data. This player won't be able to use the plugin.");
            e.printStackTrace();
        }

        populateWorksites();
    }

    private void populateWorksites() {
        worksites.clear();

        if (getData().getConfigurationSection("sites") != null) {
            for (String worksiteItem : getData().getConfigurationSection("sites").getKeys(false)) {
                worksites.put(worksiteItem, new Worksite(
                        getData().getString("name"),
                        worksiteItem,
                        (Location) getData().get("sites." + worksiteItem + ".location"),
                        (float) getData().getDouble("sites." + worksiteItem + ".last_used"),
                        getData().getBoolean("sites." + worksiteItem + ".is_public"),
                        getData().getString("sites." + worksiteItem + ".permission")
                ));
            }
        }

    }

    public HashMap<String, Worksite> getWorksites() {
        return worksites;
    }

    private void bootstrapPlayerConfigFile(Player player) {
        if (dataFile == null) {
            // Note that the prefix "ws_" is necessary to address issues with prohibited
            // filenames. Such as COM2.[any] and such.
            String filePathBase = this.i.getDataFolder() + File.separator + this.i.WS_BASE_DIR;
            String playerFileName = "ws_" + this.player.getName() + ".yml";
            dataFile = new File(filePathBase, playerFileName);

            if (!dataFile.exists()) {
                dataFile.getParentFile().mkdirs();
                //this.i.saveResource(playerFileName, false); // this will go...

                // Create new yml file for the player and store it in the proper location
                try (FileWriter writer = new FileWriter(dataFile)) {
                    // Sensible defaults
                    writer.write("name: " + player.getName() + "\n");
                    writer.write("sites: null");
                } catch (IOException e) {
                    this.i.getLogger().severe("Unable to write player worksites file for the first time. The player won't be able to use the plugin.");
                    e.printStackTrace();
                }

            }
        }
    }
}
