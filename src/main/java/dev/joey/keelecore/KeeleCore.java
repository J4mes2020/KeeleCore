package dev.joey.keelecore;

import dev.joey.keelecore.admin.vanish.VanishCommand;
import dev.joey.keelecore.managers.CommandManager;
import dev.joey.keelecore.managers.ListenerManager;
import dev.joey.keelecore.util.ConfigFileHandler;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import java.util.HashSet;
import java.util.Set;

public final class KeeleCore extends JavaPlugin {

    ConfigFileHandler configFileHandler = new ConfigFileHandler(this);

    public static Set<String> keeleStudent = new HashSet<>();
    public static Set<String> nonStudent = new HashSet<>();

    @Override
    public void onEnable() {
        UtilClass.keeleCore = this;
        configFileHandler.createPlayerFile();
        if (!Bukkit.getScoreboardManager().getMainScoreboard().getTeams().isEmpty()) {
            Bukkit.getScoreboardManager().getMainScoreboard().getTeams().forEach(Team::unregister);
        }

        keeleStudent.addAll(configFileHandler.getPlayerFile().getStringList("players.students"));
        nonStudent.addAll(configFileHandler.getPlayerFile().getStringList("players.guests"));
        new CommandManager();
        new ListenerManager();

    }

    @Override
    public void onDisable() {
        configFileHandler.getPlayerFile().set("vanished", VanishCommand.getVanishedPlayers());
        configFileHandler.saveFile();
    }
}
