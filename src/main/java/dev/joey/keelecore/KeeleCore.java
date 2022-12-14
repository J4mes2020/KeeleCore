package dev.joey.keelecore;

import dev.joey.keelecore.managers.ListenerManager;
import dev.joey.keelecore.managers.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class KeeleCore extends JavaPlugin {

    @Override
    public void onEnable() {
        new CommandManager(this);
        new ListenerManager(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
