package dev.joey.keelecore.commands;

import dev.joey.keelecore.util.Colours;
import dev.joey.keelecore.util.TimesTickFormat;
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

        if (!(sender instanceof Player)) {
            sender.sendMessage("Sorry only a player can run this command");
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("kc.admin") || player.hasPermission("kc.time")) {

            if (args.length != 2) {
                player.sendMessage(Component.text().content("Invalid Syntax").color(TextColor.color(Colours.error)));
                player.sendMessage(Component.text().content("/time <set|add> [time]").color(TextColor.color(Colours.error)));
                return true;
            }

            World world = player.getWorld();

            if (args[1].matches("^[0-9]*$") && Long.parseLong(args[1]) <= 24000) {
                if (args[0].equalsIgnoreCase("set")) {
                    world.setTime(Long.parseLong(args[1]));
                    player.sendMessage(Component.text().content("Time changed to " + args[1]).color(TextColor.color(Colours.success)));
                    return true;
                }
                if (args[0].equalsIgnoreCase("add")) {
                    world.setTime(world.getTime() + Long.parseLong(args[1]));
                    player.sendMessage(Component.text().content("The time is now " + (world.getTime() + Long.parseLong(args[1]))).color(TextColor.color(Colours.success)));
                    return true;
                }
            }

            if (TimesTickFormat.nameToTicks.containsKey(args[1])) {
                if (args[0].equalsIgnoreCase("set")) {
                    world.setTime(TimesTickFormat.nameToTicks.get(args[1]));
                    player.sendMessage(Component.text().content("Time changed to " + args[1]).color(TextColor.color(Colours.success)));
                    return true;
                }
                if (args[0].equalsIgnoreCase("add")) {
                    world.setTime(world.getTime() + TimesTickFormat.nameToTicks.get(args[1]));
                    player.sendMessage(Component.text().content("The time is now " + (world.getTime() + Long.parseLong(args[1]))).color(TextColor.color(Colours.success)));
                    return true;
                }
            }
            player.sendMessage(Component.text().content(args[1] + " is not a valid time of day").color(TextColor.color(Colours.error)));
            player.sendMessage(Component.text().content("Try one of these - " + new ArrayList<>(TimesTickFormat.nameToTicks.keySet())).color(TextColor.color(Colours.error)));

        }
        return false;
    }
}