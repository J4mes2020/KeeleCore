package dev.joey.keelecore.auth.listeners;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.auth.StudentGUI;
import dev.joey.keelecore.util.ConfigFileHandler;
import dev.joey.keelecore.util.UtilClass;
import dev.joey.keelecore.util.formatting.TabFormatting;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

import static dev.joey.keelecore.KeeleCore.keeleStudent;
import static dev.joey.keelecore.KeeleCore.nonStudent;
import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class GUIPlayerListener implements Listener {

    ConfigFileHandler configFileHandler;
    StudentGUI GUI = new StudentGUI(null, 9, Component.text("Select User").color(TextColor.color(168, 0, 82)));
    LuckPerms luckPerms;

    TabFormatting tabFormatting = new TabFormatting();

    public GUIPlayerListener() {
        configFileHandler = new ConfigFileHandler(keeleCore);
        keeleCore.getServer().getPluginManager().registerEvents(this, keeleCore);
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.sendMessage(Component.text().append(Component.text("Welcome ").color(TextColor.color(UtilClass.information))).build()
                .append(Component.text(player.getName()).color(TextColor.color(UtilClass.success))).toBuilder().build()
                .append(Component.text(" to the keele minecraft server").color(TextColor.color(UtilClass.information))).toBuilder().build());

        if (!(keeleStudent.contains(player.getUniqueId().toString()))
                && nonStudent.contains(player.getUniqueId().toString())) {
            GUI.openGUI(player);
        }


    }

    @EventHandler
    public void GUIListener(InventoryClickEvent event) {

        if (event.getClickedInventory() == GUI.getGUI() && event.getCurrentItem() != null) {
            Player player = (Player) event.getWhoClicked();
            User user = luckPerms.getUserManager().getUser(player.getUniqueId());
            ItemStack itemStack = event.getCurrentItem();
            if (itemStack.isSimilar(GUI.keeleStudent)) {

                keeleStudent.add(player.getUniqueId().toString());
                configFileHandler.getPlayerFile().set("players.students", keeleStudent);
                InheritanceNode node = InheritanceNode.builder("keelestudent").value(true).build();
                user.data().add(node);
                luckPerms.getUserManager().saveUser(user);

                UtilClass.sendPlayerMessage(player, "You are now registered as a Keele Student", UtilClass.success);
                event.setCancelled(true);
                GUI.closeGUI(player);
            }

            if (itemStack.isSimilar(GUI.nonStudent)) {
                nonStudent.add(player.getUniqueId().toString());
                configFileHandler.getPlayerFile().set("players.guests", nonStudent);
                UtilClass.sendPlayerMessage(player, "You are now registered as a Guest", UtilClass.success);
                event.setCancelled(true);
                GUI.closeGUI(player);
            }

            tabFormatting.assignTeam(player);


        }

    }

    @EventHandler
    public void GUIClosing(InventoryCloseEvent event) {

        Player player = (Player) event.getPlayer();
        if (!(keeleStudent.contains(player.getUniqueId().toString()) || nonStudent.contains(player.getUniqueId().toString()))) {

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(keeleCore, () -> {
                if (event.getInventory() == GUI.getGUI()) {
                    GUI.openGUI(player);
                }
            }, 1);

        }
    }
}
