package dev.joey.keelecore.auth.listeners;

import dev.joey.keelecore.KeeleCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class StudentGUIListener implements Listener {

    public StudentGUIListener(KeeleCore keeleCore) {
        keeleCore.getServer().getPluginManager().registerEvents(this, keeleCore);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

    }

}
