package dev.joey.keelecore.util;

import dev.joey.keelecore.KeeleCore;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.InventoryHolder;

public interface PlayerGUI {

    void createGUI(InventoryHolder owner, int size, Component title);

    void closeGUI();

    void openGUI(Player player);

}
