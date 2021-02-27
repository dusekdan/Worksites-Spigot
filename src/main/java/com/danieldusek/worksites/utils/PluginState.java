package com.danieldusek.worksites.utils;

import com.danieldusek.worksites.Worksites;
import com.danieldusek.worksites.models.PlayerData;
import com.danieldusek.worksites.models.Worksite;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * In-memory storage of plugin's current state that might be immediately
 * needed. The idea is to minimize read-write operations on the disk.
 *
 */
public class PluginState {
    public HashMap<Player, PlayerData> playerCache = new HashMap<>();

    // TODO: Don't forget to update these (and write through) when creating worksites
    public HashMap<String, Worksite> publicWorksites = new HashMap<>();
    public HashMap<String, Worksite> permissionBasedWorksites = new HashMap<>();

    public PluginState(Worksites instance) {
        // Sweep through all the player worksite files and identify permission based & public worksites
        File f = new File(instance.getDataFolder() + File.separator + instance.WS_BASE_DIR);
        String names[] = f.list();

        if (names != null) {
            for (String fileName : names) {
                String filePathBase = instance.getDataFolder() + File.separator + instance.WS_BASE_DIR;
                File tmpFile = new File(filePathBase, fileName);
                FileConfiguration tmpData = new YamlConfiguration();
                try {
                    tmpData.load(tmpFile);
                    for (String workSite : tmpData.getConfigurationSection("sites").getKeys(false)) {
                        // Iterating over worksite records for given player
                        if (!tmpData.getString("sites." + workSite + ".permission").isEmpty()) {
                            // Not empty -> meaning we have permission based one (Add to collection)
                            this.permissionBasedWorksites.put(workSite,
                                    new Worksite(
                                            tmpData.getString("name"),
                                            workSite,
                                            (Location) tmpData.get("sites." + workSite + ".location"),
                                            (float) tmpData.getDouble("sites." + workSite + ".last_used"),
                                            false,
                                            tmpData.getString("sites." + workSite + ".permission")
                                    )
                            );
                        }

                        if (tmpData.getBoolean("sites." + workSite + ".is_public")) {
                            // Got a public one, store in public collection
                            this.publicWorksites.put(workSite,
                                    new Worksite(
                                            tmpData.getString("name"),
                                            workSite,
                                            (Location) tmpData.get("sites." + workSite + ".location"),
                                            (float) tmpData.getDouble("sites." + workSite + ".last_used"),
                                            true,
                                            ""
                                    )
                            );
                        }
                    }
                } catch (IOException | InvalidConfigurationException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
