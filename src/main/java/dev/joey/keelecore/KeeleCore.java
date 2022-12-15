package dev.joey.keelecore;

import dev.joey.keelecore.managers.ListenerManager;
import dev.joey.keelecore.managers.CommandManager;
import dev.joey.keelecore.util.ConfigFileHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class KeeleCore extends JavaPlugin {

    ConfigFileHandler configFileHandler = new ConfigFileHandler(this);

    public static List<String> keeleStudent;
    public static List<String> nonStudent;

    @Override
    public void onEnable() {
        configFileHandler.createPlayerFile();
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
