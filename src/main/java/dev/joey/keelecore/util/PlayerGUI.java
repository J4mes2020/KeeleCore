package dev.joey.keelecore.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public interface PlayerGUI {

    void createGUI(InventoryHolder owner, int size, Component title);

    void closeGUI();

    void openGUI(Player player);

    ItemStack createItem(Material material, Component component);
}
