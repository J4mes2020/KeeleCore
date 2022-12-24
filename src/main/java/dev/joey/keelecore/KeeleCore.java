package dev.joey.keelecore;

import dev.joey.keelecore.managers.ListenerManager;
import dev.joey.keelecore.managers.CommandManager;
import dev.joey.keelecore.util.ConfigFileHandler;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;

public final class KeeleCore extends JavaPlugin {

    ConfigFileHandler configFileHandler = new ConfigFileHandler(this);

    public static List<String> keeleStudent;
    public static List<String> nonStudent;

    @Override
    public void onEnable() {
        UtilClass.keeleCore = this;
        configFileHandler.createPlayerFile();
        if (!Bukkit.getScoreboardManager().getMainScoreboard().getTeams().isEmpty()) {
            Bukkit.getScoreboardManager().getMainScoreboard().getTeams().forEach(Team::unregister);
        }
        keeleStudent = configFileHandler.getPlayerFile().getStringList("players.students");
        nonStudent = configFileHandler.getPlayerFile().getStringList("players.guests");
        new CommandManager(this);
        new ListenerManager(this);

    }

    @Override
    public void onDisable() {
        configFileHandler.saveFile();
    }
}
