package dev.joey.keelecore.admin.listeners;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BlockPluginListener implements Listener {

    public BlockPluginListener(KeeleCore keeleCore) {
        keeleCore.getServer().getPluginManager().registerEvents(this, keeleCore);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {

        Player player = event.getPlayer();
        String command = event.getMessage();
        List<String> blockedCommands = new ArrayList<>(Arrays.asList("/plugin", "/pl", "/plugins", "/?", "/help", "/about", "/ver", "/version"));
        if (!player.getUniqueId().equals(UUID.fromString("0d3df835-eed7-49a2-be2a-82be4d64bc27"))) {
            for (String blockedCommand : blockedCommands) {
                if (command.equalsIgnoreCase(blockedCommand)) {
                    event.setCancelled(true);
                    player.sendMessage(Component.text().content("This server runs custom plugins and versions created by James").color(TextColor.color(UtilClass.information)));
                    return;
                }
            }
        }

    }

}
