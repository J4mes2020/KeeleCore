package dev.joey.keelecore.auth.listeners;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.auth.StudentGUI;
import dev.joey.keelecore.util.ConfigFileHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class StudentGUIListener implements Listener {

    ConfigFileHandler configFileHandler;
    StudentGUI GUI = new StudentGUI(null, 9, Component.text("Select User").color(TextColor.color(168, 0, 82)));
    KeeleCore keeleCore;

    public StudentGUIListener(KeeleCore keeleCore) {
        this.keeleCore = keeleCore;
        configFileHandler = new ConfigFileHandler(keeleCore);
        keeleCore.getServer().getPluginManager().registerEvents(this, keeleCore);



    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (KeeleCore.keeleStudent.contains(player.getUniqueId().toString())) {
            player.sendMessage("Your a keele student");
            return;
        }

        if (KeeleCore.nonStudent.contains(player.getUniqueId().toString())) {
            player.sendMessage("Your not a keele student");
            return;
        }
        GUI.openGUI(player);


    }

    @EventHandler
    public void GUIListener(InventoryClickEvent event) {

        if (event.getClickedInventory() == GUI.getGUI()) {
            if (event.getCurrentItem() != null) {

                Player player = (Player) event.getWhoClicked();
                ItemStack itemStack = event.getCurrentItem();
                if (itemStack.isSimilar(GUI.keeleStudent)) {
                    KeeleCore.keeleStudent.add(player.getUniqueId().toString());
                    configFileHandler.getPlayerFile().set("players.students", KeeleCore.keeleStudent);
                    event.setCancelled(true);
                    GUI.closeGUI(player);
                    return;
                }

                if (itemStack.isSimilar(GUI.nonStudent)) {
                    KeeleCore.nonStudent.add(player.getUniqueId().toString());
                    configFileHandler.getPlayerFile().set("players.guests", KeeleCore.nonStudent);
                    event.setCancelled(true);
                    GUI.closeGUI(player);
                }


            }

        }

    }

    @EventHandler
    public void GUIClosing(InventoryCloseEvent event) {

        Player player = (Player) event.getPlayer();
        if (!(KeeleCore.keeleStudent.contains(player.getUniqueId().toString()) || KeeleCore.nonStudent.contains(player.getUniqueId().toString()))) {

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(keeleCore, () -> {
                if (event.getInventory() == GUI.getGUI()) {
                    GUI.openGUI(player);
                }
            }, 1);

        }
    }
}
