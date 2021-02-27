package com.danieldusek.worksites.commands;

import com.danieldusek.worksites.Worksites;
import com.danieldusek.worksites.constants.C;
import com.danieldusek.worksites.models.Worksite;
import com.danieldusek.worksites.utils.PermissionUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWorksiteCommand implements CommandExecutor {
    private Worksites i;
    public SetWorksiteCommand(Worksites instance) {
        this.i = instance;
    }


    // Interesting take on serialization: https://www.spigotmc.org/threads/how-to-serialize-location-object.327007/
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            i.getLogger().warning("You cannot set worksite from console.");
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission(C.PERMISSIONS.SET_WORKSITE)) {
            player.sendMessage(C.PLUGIN.MARKER + ChatColor.RED + "Sorry, you do not have a permission to do that.");
            return true;
        }

        // Figure out what kind of parameters were parsed, possibilities:
        // /setworksite PlayerName:WorksiteName (FUTURE: Implement this when re-wamping the store process)
        Worksite worksite;
        if (args.length == 1) {
            // It's the named worksite - do the validation
            if (!args[0].matches("^[a-zA-Z0-9]*$")) {
                player.sendMessage(C.PLUGIN.MARKER + ChatColor.RED + "Sorry, only alfanumeric characters are allowed in worksite name. Example: siteX");
                return true;
            }

            // Get number of worksites player currently have (n)
            // And check the permission node either for unlimited or for (n+1)
            int currentWorksites = this.i.state.playerCache.get(player).getWorksites().size();
            int allowedMax = PermissionUtils.getNumericPermissionNode(player, C.PERMISSIONS.MULTIPLE_WORKSITES + ".", 1);
            if (!(player.hasPermission(C.PERMISSIONS.UNLIMITED_WORKSITES) || currentWorksites + 1 <= allowedMax)) {
                player.sendMessage(C.PLUGIN.MARKER + ChatColor.RED + "Sorry, you cannot create any more worksites.");
                return true;
            }

            worksite = new Worksite(player, args[0]);
        } else if (args.length == 2) {
            // First validate the name is in correct format
            if (!args[0].matches("^[a-zA-Z0-9]*$")) {
                player.sendMessage(C.PLUGIN.MARKER + ChatColor.RED + "Sorry, only alfanumeric characters are allowed in worksite name. Example: siteX");
                return true;
            }

            // Public or permission based worksite
            if (args[1].equals("public")) {
                if (!player.hasPermission(C.PERMISSIONS.SET_WORKSITE_PUBLIC)) {
                    player.sendMessage(C.PLUGIN.MARKER + ChatColor.RED + "Sorry, you do not have permission to set public worksites.");
                    return true;
                }

                worksite = new Worksite(player, args[0], true);
                this.i.state.publicWorksites.put(worksite.getWorksiteName(), worksite);

            } else {
                // Permission based worksites here
                if (!player.hasPermission(C.PERMISSIONS.SET_WORKSITE_PERMISSION_BASED)) {
                    player.sendMessage(C.PLUGIN.MARKER + ChatColor.RED + "Sorry, you do not have permission to set permission-based worksites.");
                    return true;
                }

                worksite = new Worksite(player, args[0], args[1]);
                this.i.state.permissionBasedWorksites.put(worksite.getWorksiteName(), worksite);
            }
        } else {
            worksite = new Worksite(player); // Sets default work site
        }

        // Writes the new worksites through the cache to player file as well.
        if (this.i.state.playerCache.containsKey(player)) {
            this.i.state.playerCache.get(player).addWorksite(worksite);
        }
        player.sendMessage(C.PLUGIN.MARKER + ChatColor.GREEN + "Worksite set.");

        return true;
    }
}
