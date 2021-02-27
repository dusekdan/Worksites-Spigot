package com.danieldusek.worksites.commands;

import com.danieldusek.worksites.Worksites;
import com.danieldusek.worksites.constants.C;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorksitesCommand implements CommandExecutor {
    private final Worksites i;

    public WorksitesCommand(Worksites plugin) {
        this.i = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command cannot be issued from server console.");
            return false;
        }
        Player player = (Player) sender;

        StringBuilder message = new StringBuilder();
        for (String worksiteName : this.i.state.playerCache.get(player).getWorksites().keySet()) {

            if (worksiteName.equals(C.PLUGIN.DEFAULT_WORKSITE_NAME))
                continue;

            message.append(worksiteName).append(" ");
        }

        player.sendMessage(C.PLUGIN.MARKER + "You have following worksites set up: ");
        player.sendMessage(message.toString());

        return true;
    }
}
