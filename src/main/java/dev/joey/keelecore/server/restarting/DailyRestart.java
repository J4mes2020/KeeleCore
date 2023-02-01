package dev.joey.keelecore.server.restarting;

import org.bukkit.Bukkit;
import org.bukkit.World;

import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class DailyRestart {

    public DailyRestart() {

        Bukkit.getScheduler().runTaskLater(keeleCore, () -> {
            keeleCore.getServer().getWorlds().forEach(World::save);
            keeleCore.getServer().savePlayers();
            keeleCore.getServer().spigot().restart();

        }, 1728000);
    }

}
