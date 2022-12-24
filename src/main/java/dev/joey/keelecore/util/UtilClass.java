package dev.joey.keelecore.util;

import dev.joey.keelecore.KeeleCore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

public class UtilClass {


    public static KeeleCore keeleCore;

    static Logger log = Logger.getLogger("Minecraft");
    public static int success = new Color(0, 255, 8).getRGB();
    public static int error = new Color(202, 28, 26).getRGB();
    public static int information = new Color(255, 221, 0).getRGB();


    public static void sendPlayerMessage(Player player, String message, int colour) {
        player.sendMessage(Component.text().content(message).color(TextColor.color(colour)));
    }

    public static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public static class TimesTickFormat {
        public static Map<String, Long> nameToTicks = new LinkedHashMap<>();

        static {

            nameToTicks.put("sunrise", 23000L);
            nameToTicks.put("dawn", 23000L);
            nameToTicks.put("daystart", 0L);
            nameToTicks.put("day", 0L);
            nameToTicks.put("morning", 1000L);
            nameToTicks.put("midday", 6000L);
            nameToTicks.put("noon", 6000L);
            nameToTicks.put("afternoon", 9000L);
            nameToTicks.put("sunset", 12000L);
            nameToTicks.put("dusk", 12000L);
            nameToTicks.put("sundown", 12000L);
            nameToTicks.put("nightfall", 12000L);
            nameToTicks.put("nightstart", 14000L);
            nameToTicks.put("night", 14000L);
            nameToTicks.put("midnight", 18000L);

        }

    }

    public static ItemStack createGUIItem(Material material, Component displayName) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(displayName);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);

        return item;
    }
}
