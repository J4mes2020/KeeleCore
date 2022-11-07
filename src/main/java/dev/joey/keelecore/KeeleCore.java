package dev.joey.keelecore;

import dev.joey.keelecore.auth.AuthCommands;
import dev.joey.keelecore.auth.JoinAndRegListeners;
import dev.joey.keelecore.events.PlayerEvents;
import dev.joey.keelecore.util.FileModifications;
import org.bukkit.plugin.java.JavaPlugin;

public final class KeeleCore extends JavaPlugin {

    @Override
    public void onEnable() {
        new PlayerEvents(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
