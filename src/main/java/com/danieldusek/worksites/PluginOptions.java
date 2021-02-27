package com.danieldusek.worksites;

import org.bukkit.plugin.Plugin;

/**
 * TODO: Look into reimplementing this to be actually a configuration file like any other.
 * It will provide support for easy serialization and deserialization
 *
 * Source: https://www.spigotmc.org/wiki/config-files/
 */
public class PluginOptions {
    final private Plugin instance;

    public PluginOptions(Plugin instance) {
        this.instance = instance;
        this.load();
    }

    public void load() {
        this.instance.reloadConfig();
    }

    public void reload() {
        this.load();
    }

    /*
     * Define your config fields here e.g.:
     *
     * Simple type properties: public [String | int | double]
     * public String key1() {
     *     return this.instance.getConfig().getString("key1");
     * }
     *
     * List type properties:
     * public List<String> keyList() {
     *     return this.instance.getConfig().getStringList("key-list");
     * }
     */
}
