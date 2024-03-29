package dev.joey.keelecore.util.formatting;

import dev.joey.keelecore.KeeleCore;
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

import java.util.UUID;

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
                    if (group.getName().equals("student")) {
                        if (!KeeleCore.keeleStudent.isEmpty()) {
                            KeeleCore.keeleStudent.forEach(student -> {
                                if (student != null) {
                                    mainBoard.getTeam("student").addPlayer(Bukkit.getOfflinePlayer(UUID.fromString(student)));
                                }

                            });
                        }
                    }


                    if (group.getName().equals("default")) {
                        if (!KeeleCore.nonStudent.isEmpty()) {
                            KeeleCore.nonStudent.forEach(student -> {
                                if (student != null) {
                                    mainBoard.getTeam("default").addPlayer(Bukkit.getOfflinePlayer(UUID.fromString(student)));
                                }
                            });
                        }
                    }
                }
            }
        }
    }


    public void assignTeam(Player player) {

        User user = luckPerms.getUserManager().getUser(player.getUniqueId());

        for (Team team : mainBoard.getTeams()) {
            if (user != null) {
                if (!team.getEntries().contains(player.getName())) {
                    if (user.getPrimaryGroup().equals(team.getName())) {
                        team.addPlayer(player);
                    }
                }
            }

        }


    }

}
