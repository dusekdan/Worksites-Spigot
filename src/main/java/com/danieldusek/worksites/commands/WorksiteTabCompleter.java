package com.danieldusek.worksites.commands;

import com.danieldusek.worksites.Worksites;
import com.danieldusek.worksites.constants.C;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WorksiteTabCompleter implements TabCompleter {
    private Worksites i;
    public WorksiteTabCompleter(Worksites plugin) {
        this.i = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (command.getName().equalsIgnoreCase("worksite") || command.getName().equalsIgnoreCase("delworksite")){
            if (sender instanceof Player) {
                Player p = (Player) sender;

                ArrayList<String> publicWorksites = new ArrayList<>(this.i.state.publicWorksites.keySet());
                ArrayList<String> autocompleter = new ArrayList<>(this.i.state.playerCache.get(p).getWorksites().keySet());
                autocompleter.addAll(publicWorksites);
                autocompleter.removeIf(key -> key.equalsIgnoreCase(C.PLUGIN.DEFAULT_WORKSITE_NAME));

                return autocompleter;
            }
        }

        return null;
    }
}
