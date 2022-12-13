package dev.joey.keelecore.auth;

import dev.joey.keelecore.util.PlayerGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StudentGUI implements PlayerGUI {

    Inventory GUI;

    @Override
    public void createGUI(InventoryHolder owner, int size, Component title) {

        GUI = Bukkit.createInventory(owner, size, title);

        GUI.setItem(4, createItem(Material.BLUE_WOOL,
                Component.text("Keele Student")
                        .style(Style.style(TextColor.color(61, 158, 191), TextDecoration.BOLD))
                        .toBuilder().build()));

    }

    @Override
    public void closeGUI() {

    }

    @Override
    public void openGUI(Player player) {

        player.openInventory(GUI);

    }

    @Override
    public ItemStack createItem(Material material, Component component) {

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(component);
        item.setItemMeta(meta);
        return item;

    }
}
