package dev.joey.keelecore;

import dev.joey.keelecore.events.JoinAndRegListeners;
import dev.joey.keelecore.util.FileModifications;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class KeeleCore extends JavaPlugin {

    FileModifications fileModifications = new FileModifications(this);

    @Override
    public void onEnable() {
        fileModifications.createCustomConfig(FileModifications.getPlayerFile());
        new JoinAndRegListeners(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
