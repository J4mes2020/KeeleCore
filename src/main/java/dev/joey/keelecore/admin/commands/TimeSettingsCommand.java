package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TimeSettingsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        if (player.hasPermission("kc.admin") || player.hasPermission("kc.time") || player.isOp()) {

            if (args.length != 2) {
                player.sendMessage(Component.text().content("Invalid Syntax").color(TextColor.color(UtilClass.error)));
                player.sendMessage(Component.text().content("/time <set|add> [time]").color(TextColor.color(UtilClass.error)));
                return true;
            }

            World world = player.getWorld();

            if (args[1].matches("^[0-9]*$") && Long.parseLong(args[1]) <= 24000) {
                if (args[0].equalsIgnoreCase("set")) {
                    world.setTime(Long.parseLong(args[1]));
                    player.sendMessage(Component.text().content("Time changed to " + args[1]).color(TextColor.color(UtilClass.success)));
                    return true;
                }
                if (args[0].equalsIgnoreCase("add")) {
                    world.setTime(world.getTime() + Long.parseLong(args[1]));
                    player.sendMessage(Component.text().content("The time is now " + (world.getTime() + Long.parseLong(args[1]))).color(TextColor.color(UtilClass.success)));
                    return true;
                }
            }

            if (UtilClass.TimesTickFormat.nameToTicks.containsKey(args[1])) {
                if (args[0].equalsIgnoreCase("set")) {
                    world.setTime(UtilClass.TimesTickFormat.nameToTicks.get(args[1]));
                    player.sendMessage(Component.text().content("Time changed to " + args[1]).color(TextColor.color(UtilClass.success)));
                    return true;
                }
                if (args[0].equalsIgnoreCase("add")) {
                    world.setTime(world.getTime() + UtilClass.TimesTickFormat.nameToTicks.get(args[1]));
                    player.sendMessage(Component.text().content("The time is now " + (world.getTime() + Long.parseLong(args[1]))).color(TextColor.color(UtilClass.success)));
                    return true;
                }
            }
            player.sendMessage(Component.text().content(args[1] + " is not a valid time of day").color(TextColor.color(UtilClass.error)));
            player.sendMessage(Component.text().content("Try one of these - " + new ArrayList<>(UtilClass.TimesTickFormat.nameToTicks.keySet())).color(TextColor.color(UtilClass.error)));

        }
        else {
            player.sendMessage(Component.text().content("You need to be a server operator to do this command").color(TextColor.color(UtilClass.error)));
        }
        return false;
    }
}