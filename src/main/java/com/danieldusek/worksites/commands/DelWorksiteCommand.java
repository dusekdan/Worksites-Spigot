package com.danieldusek.worksites.commands;

import com.danieldusek.worksites.Worksites;
import com.danieldusek.worksites.constants.C;
import com.danieldusek.worksites.models.Worksite;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelWorksiteCommand implements CommandExecutor {
    private Worksites i;

    public DelWorksiteCommand(Worksites instance) {
        this.i = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            i.getLogger().warning("You cannot set worksite from console.");
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission(C.PERMISSIONS.DELETE_WORKSITE)) {
            player.sendMessage(C.PLUGIN.MARKER + ChatColor.RED + "You don't have permission to delete worksites.");
            return true;
        }

        // args[0] == worksite to delete name
        if (args.length == 1) {
            // Delete named worksite
            if (this.i.state.playerCache.get(player).getWorksites().containsKey(args[0])) {

                // Detect whether worksite was public || permission based and update state accordingly
                Worksite workSiteToDelete = this.i.state.playerCache.get(player).getWorksites().get(args[0]);
                if (!workSiteToDelete.getPermission().equalsIgnoreCase("")) {
                    this.i.state.permissionBasedWorksites.remove(args[0]);
                }

                if(workSiteToDelete.isPublic()) {
                    this.i.state.publicWorksites.remove(args[0]);
                }

                this.i.state.playerCache.get(player).deleteWorksite(args[0]);
                player.sendMessage(C.PLUGIN.MARKER + ChatColor.GREEN + "Worksite " + args[0] + " was deleted.");

            } else {
                player.sendMessage(C.PLUGIN.MARKER + ChatColor.RED + "Worksite " + args[0] + " does not exist, so it was not deleted.");
            }
        } else {
            // Delete default worksite
            if (this.i.state.playerCache.get(player).getWorksites().containsKey(C.PLUGIN.DEFAULT_WORKSITE_NAME)) {
                this.i.state.playerCache.get(player).deleteWorksite(C.PLUGIN.DEFAULT_WORKSITE_NAME);
                player.sendMessage(C.PLUGIN.MARKER + ChatColor.GREEN + "Default worksite deleted.");
            } else {
                player.sendMessage(C.PLUGIN.MARKER + ChatColor.RED + "You have not set your default worksite, it can't be deleted.");
            }
        }

        return true;
    }
}
