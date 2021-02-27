package com.danieldusek.worksites;

import com.danieldusek.worksites.commands.*;
import com.danieldusek.worksites.listeners.PlayerJoinLeaveListener;
import com.danieldusek.worksites.utils.PluginState;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Worksites extends JavaPlugin {
    public PluginOptions config;
    final public String WS_BASE_DIR = "Worksites";

    public PluginState state;

    @Override
    public void onEnable() {
        getLogger().info("Started loading...");
        configSetup();
        if (!prepareDirectoryStructure())
            return;

        this.state = new PluginState(this);

        // TODO: As player cache is populated on player joins/leaves, THERE SHOULD BE CUSTOM RELOAD LOGIC
        // THAT WILL POPULATE THE CACHE BASED ON CURRENT PLAYERS ONLINE.

        // Register event listeners (Join|Leave for cache updates)
        getServer().getPluginManager().registerEvents(new PlayerJoinLeaveListener(this), this);

        // Register user commands
        getCommand("setworksite").setExecutor(new SetWorksiteCommand(this));
        getCommand("delworksite").setExecutor(new DelWorksiteCommand(this));
        getCommand("delworksite").setTabCompleter(new WorksiteTabCompleter(this));
        getCommand("worksite").setExecutor(new WorksiteCommand(this));
        getCommand("worksite").setTabCompleter(new WorksiteTabCompleter(this));
        getCommand("worksites").setExecutor(new WorksitesCommand(this));

        getLogger().info("Loaded.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin shutdown logic started...");
    }


    private boolean prepareDirectoryStructure() {
        File dataFolder = new File(getDataFolder(), WS_BASE_DIR);
        if (!dataFolder.exists()) {
            if (!dataFolder.mkdir()) {
                getLogger().severe("Could not create workspaces directory. Workspaces plugin can not operate without it.");
                return false;
            }
        }

        return true;
    }


    private void configSetup() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        this.config = new PluginOptions(this);
    }
}
