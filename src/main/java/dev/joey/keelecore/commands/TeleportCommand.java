package dev.joey.keelecore.commands;

import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if (!(sender instanceof Player)) {
            sender.sendMessage("Sorry only a player can run this command");
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("kc.admin") || player.hasPermission("kc.teleport") || player.isOp()) {

            if (args.length == 1) {

                Player playerTeleportedTo = Bukkit.getPlayer(args[0]);
                if (playerTeleportedTo != null) {
                    player.teleport(playerTeleportedTo.getLocation());
                    player.sendMessage(Component.text()
                            .content("Teleported to " + playerTeleportedTo.getName()).color(TextColor.color(UtilClass.success)).build());
                    return true;
                }

            }

            if (args.length == 2) {

                Player playerTeleported = Bukkit.getPlayer(args[0]);
                Player playerTeleportedTo = Bukkit.getPlayer(args[1]);

                if (playerTeleported != null && playerTeleportedTo != null) {
                    playerTeleported.teleport(playerTeleportedTo);
                    player.sendMessage(Component.text()
                            .content("Teleported " + playerTeleported.getName() + " to " + playerTeleportedTo.getName()).color(TextColor.color(UtilClass.success)).build());
                    getLocationInfo(playerTeleported);
                    return true;
                }

            }

            Player teleporter = null;
            int x = 0, y = 0, z = 0;

            if (args.length == 4) {
                teleporter = Bukkit.getPlayer(args[0]);
                x = Integer.parseInt(args[1]);
                y = Integer.parseInt(args[2]);
                z = Integer.parseInt(args[3]);
            }

            if (args.length == 3) {
                teleporter = player;
                x = Integer.parseInt(args[0]);
                y = Integer.parseInt(args[1]);
                z = Integer.parseInt(args[2]);

            }

            Location teleportLocation = new Location(teleporter.getWorld(), x, y, z);
            teleporter.teleport(teleportLocation);


        } else {
            player.sendMessage(Component.text().content("You need to be a server operator to do this command").color(TextColor.color(UtilClass.error)));
        }
        return false;
    }

    protected void getLocationInfo(Player player) {

        player.sendMessage(Component.text()
                .content("Current Biome " + player.getWorld().getBiome(player.getLocation())
                        + "\n Coordinates: X: " + Math.round(player.getLocation().getX())
                        + " Y: " + Math.round(player.getLocation().getY())
                        + " z: " + Math.round(player.getLocation().getX()))
                .color(TextColor.color(UtilClass.success)).build());


    }
}
