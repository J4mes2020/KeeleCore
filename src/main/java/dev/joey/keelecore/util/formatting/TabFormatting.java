package dev.joey.keelecore.util.formatting;

import net.kyori.adventure.text.Component;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class TabFormatting {

    LuckPerms luckPerms;
    User user;

    RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
    ScoreboardManager manager = Bukkit.getScoreboardManager();
    Scoreboard mainBoard = manager.getMainScoreboard();

    public TabFormatting() {

        if (provider != null) {
            luckPerms = provider.getProvider();
            for (Group group : luckPerms.getGroupManager().getLoadedGroups()) {
                if (group.getCachedData().getMetaData().getPrefix() != null) {
                        mainBoard.registerNewTeam(group.getName()).prefix(Component.text(ChatFormatting.colorize(group.getCachedData().getMetaData().getPrefix())));
                }
            }

        }

    }

    public void assignTeam(Player player) {

        User user = luckPerms.getUserManager().getUser(player.getUniqueId());

        for (Team team : mainBoard.getTeams()) {
            if (user != null) {
                if (user.getPrimaryGroup().equals(team.getName())) {
                    team.addPlayer(player);
                }
            }

        }


    }


}
