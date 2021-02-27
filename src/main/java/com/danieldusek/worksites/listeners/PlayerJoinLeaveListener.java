package com.danieldusek.worksites.listeners;

import com.danieldusek.worksites.Worksites;
import com.danieldusek.worksites.models.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinLeaveListener implements Listener {
    final private Worksites i;
    public PlayerJoinLeaveListener(Worksites instance) {
        this.i = instance;
    }

    @EventHandler
    public void onPlayerConnect(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!this.i.state.playerCache.containsKey(p)) {
            PlayerData playerData = new PlayerData(this.i, p);
            this.i.state.playerCache.put(p, playerData);
            return;
        }
        this.i.getLogger().warning("For some reason, player " + p.getName() + " was already cached. No action taken.");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        this.i.state.playerCache.remove(p);
    }
}
