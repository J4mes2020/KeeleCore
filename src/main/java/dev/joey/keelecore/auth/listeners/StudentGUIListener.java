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

public class StudentGUIListener implements Listener {

    ConfigFileHandler configFileHandler;
    StudentGUI GUI = new StudentGUI(null, 9, Component.text("Select User").color(TextColor.color(168, 0, 82)));
    KeeleCore keeleCore;
    LuckPerms luckPerms;

    TabFormatting tabFormatting = new TabFormatting();

    public StudentGUIListener(KeeleCore keeleCore) {
        this.keeleCore = keeleCore;
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

        tabFormatting.assignTeam(player);


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
                User user = luckPerms.getUserManager().getUser(player.getUniqueId());
                ItemStack itemStack = event.getCurrentItem();
                if (itemStack.isSimilar(GUI.keeleStudent)) {
                    KeeleCore.keeleStudent.add(player.getUniqueId().toString());
                    configFileHandler.getPlayerFile().set("players.students", KeeleCore.keeleStudent);
                    InheritanceNode node = InheritanceNode.builder("keelestudent").value(true).build();
                    user.data().add(node);
                    luckPerms.getUserManager().saveUser(user);

                    player.sendMessage(Component.text("You are now known as a Keele Student").color(TextColor.color(UtilClass.brightSuccess)));
                    event.setCancelled(true);
                    GUI.closeGUI(player);
                    return;
                }

                if (itemStack.isSimilar(GUI.nonStudent)) {
                    KeeleCore.nonStudent.add(player.getUniqueId().toString());
                    configFileHandler.getPlayerFile().set("players.guests", KeeleCore.nonStudent);
                    player.sendMessage(Component.text("You are now known as a guest").color(TextColor.color(UtilClass.brightSuccess)));

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
