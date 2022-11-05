package dev.joey.keelecore.events;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.util.FileModifications;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinAndRegListeners implements Listener {

    public JoinAndRegListeners(KeeleCore keeleCore) {

        keeleCore.getServer().getPluginManager().registerEvents(this, keeleCore);

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        if (!FileModifications.isInFile(player.getUniqueId().toString(), FileModifications.getPlayerFile())) { //If Player doesn't exist in file

            player.sendMessage(
                    Component
                            .text().content("INPUT STUDENT TYPE \n\n\n\n\n")
                            .color(TextColor.color(61, 158, 191))
                            .decoration(TextDecoration.BOLD, true).build());

            player.sendMessage(Component
                    .text().content("[NON STUDENT] ")
                    .color(TextColor.color(137, 79, 169))
                    .clickEvent(ClickEvent.runCommand("/non-student"))
                    .decoration(TextDecoration.BOLD, true).build()


                    .append(Component.text().content(" [KEELE STUDENT]")
                    .color(TextColor.color(199, 44, 1)).clickEvent(ClickEvent.runCommand("/student"))
                    .decoration(TextDecoration.BOLD, true).build()));

        }


    }


}
