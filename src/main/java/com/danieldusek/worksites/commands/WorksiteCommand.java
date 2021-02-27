package com.danieldusek.worksites.commands;

import com.danieldusek.worksites.Worksites;
import com.danieldusek.worksites.constants.C;
import com.danieldusek.worksites.models.Worksite;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class WorksiteCommand implements CommandExecutor {
    private Worksites i;
    public WorksiteCommand(Worksites instance) {
        this.i = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command cannot be used from the console.");
            return true;
        }
        Player p = (Player) sender;

        // Parse command, cover all use case basis
        // /worksite (case 0)
        // /worksite name - case 1
        //  - can be either their own
        //  - or the public one
        //  - or the permissions based one
        // /worksite PlayerName:WorksiteName
        // /worksite name public - case 2
        // /worksite name permission.something


        switch (args.length) {
            case 0:
                if (p.hasPermission(C.PERMISSIONS.USE_WORKSITE)) {
                    // Take location from player data cache
                    Location target = (Location) this.i.state.playerCache.get(p).getData().get("sites." + C.PLUGIN.DEFAULT_WORKSITE_NAME + ".location");
                    if (target == null) {
                        p.sendMessage(C.PLUGIN.MARKER + "You don't have any worksites.");
                        return true;
                    }
                    p.teleport(target);
                    // Port the player
                }
                break;
            case 1:
                String[] parts = args[0].split(":");
                if (parts.length == 1) {
                    // Its just worksite name
                    if (this.i.state.playerCache.containsKey(p)) {
                        if(this.i.state.playerCache.get(p).getWorksites().containsKey(parts[0])) {
                            Location target = this.i.state.playerCache
                                    .get(p)
                                    .getWorksites()
                                    .get(parts[0])
                                    .getLocation();

                            p.teleport(target);
                            return true;
                        }
                    }

                    // Maybe it was public worksite?
                    if (this.i.state.publicWorksites.containsKey(parts[0])) {
                        p.sendMessage(C.PLUGIN.MARKER + ChatColor.GREEN + "Teleporting you to public worksite.");
                        p.teleport(this.i.state.publicWorksites.get(parts[0]).getLocation());
                        return true;
                    }

                    // Or maybe it is permission based worksite
                    if (this.i.state.permissionBasedWorksites.containsKey(parts[0])) {
                        Worksite target = this.i.state.permissionBasedWorksites.get(parts[0]);
                        if (!p.hasPermission(target.getPermission())) {
                            p.sendMessage(C.PLUGIN.MARKER + ChatColor.RED + "You do not have the permission required to access this worksite.");
                            return true;
                        }
                        p.teleport(target.getLocation());
                    }

                } else {
                   p.sendMessage(C.PLUGIN.MARKER + ChatColor.RED + "This is not supported in the current version.");
                   return true;
                }
                break;
            case 2:
                // There is currently no version of command with syntax: /worksite name something
                break;
        }

        return true;
    }
}
