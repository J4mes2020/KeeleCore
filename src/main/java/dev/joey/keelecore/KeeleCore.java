package dev.joey.keelecore;

import dev.joey.keelecore.commands.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class KeeleCore extends JavaPlugin {

    @Override
    public void onEnable() {
        new CommandManager(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
