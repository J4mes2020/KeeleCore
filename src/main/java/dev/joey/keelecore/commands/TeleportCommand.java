package dev.joey.keelecore.commands;

import dev.joey.keelecore.util.Colours;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
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

        if (player.hasPermission("kc.admin") || player.hasPermission("kc.teleport")) {

            if (args.length >= 2) {

                if (args.length == 2) {

                    Player playerTeleported = Bukkit.getPlayer(args[0]);
                    Player playerTeleportedTo = Bukkit.getPlayer(args[1]);

                    if (playerTeleported != null && playerTeleportedTo != null) {
                        playerTeleported.teleport(playerTeleportedTo);
                        player.sendMessage(Component.text()
                                .content("Teleported to  " + playerTeleportedTo.getName()).color(TextColor.color(Colours.success)).build());
                        getLocationInfo(playerTeleported);
                        return true;
                    }

                }

            }

        }
        return false;
    }

    protected void getLocationInfo(Player player) {

        player.sendMessage(Component.text()
                .content("Current Biome " + player.getWorld().getBiome(player.getLocation())
                        + "\n Coordinates: X: " + Math.round(player.getLocation().getX())
                        + " Y: " + Math.round(player.getLocation().getY())
                        + " z: " + Math.round(player.getLocation().getX()))
                .color(TextColor.color(Colours.success)).build());


    }
}
