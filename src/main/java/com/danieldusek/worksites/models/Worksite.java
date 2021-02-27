package com.danieldusek.worksites.models;

import com.danieldusek.worksites.constants.C;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Worksite {
    private Location location;
    private String playerName;
    private String worksiteName;
    private float lastUsed = 0; // System.currentTimeMillis()
    private boolean isPublic;
    private String permission;

    public Worksite(Player player) {
        this.location = player.getLocation();
        this.playerName = player.getName();
        this.worksiteName = C.PLUGIN.DEFAULT_WORKSITE_NAME;
        this.lastUsed = 0;
        this.isPublic = false;
        this.permission = "";
    }

    public Worksite(Player player, String worksiteName) {
        this.location = player.getLocation();
        this.playerName = player.getName();
        this.worksiteName = worksiteName;
        this.lastUsed = 0;
        this.isPublic = false;
        this.permission = "";
    }

    public Worksite(Player player, String worksiteName, boolean isPublic) {
        this.location = player.getLocation();
        this.playerName = player.getName();
        this.worksiteName = worksiteName;
        this.lastUsed = 0;
        this.isPublic = isPublic;
        this.permission = "";
    }

    public Worksite(Player player, String worksiteName, String permission) {
        this.location = player.getLocation();
        this.playerName = player.getName();
        this.worksiteName = worksiteName;
        this.lastUsed = 0;
        this.isPublic = false;
        this.permission = permission;
    }

    public Worksite(Location location, Player player) {
        this.location = location;
        this.playerName = player.getName();
        this.worksiteName = C.PLUGIN.DEFAULT_WORKSITE_NAME;
        this.lastUsed = 0;
        this.isPublic = false;
        this.permission = "";
    }

    public Worksite(String playerName, String worksiteName, Location location, float lastUsed, boolean isPublic, String permission) {
        this.playerName = playerName;
        this.worksiteName = worksiteName;
        this.location = location;
        this.lastUsed = lastUsed;
        this.isPublic = isPublic;
        this.permission = permission;
    }


    public void setWorksiteName(String worksiteName) {
        this.worksiteName = worksiteName;
    }

    public String getWorksiteName() {
        return worksiteName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public float getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(float lastUsed) {
        this.lastUsed = lastUsed;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
