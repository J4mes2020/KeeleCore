package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GameModeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Sorry only a player can run this command");
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("kc.admin") || player.hasPermission("kc.gamemode") || player.isOp()) {

            if (args.length == 0 || args.length > 2) {
                player.sendMessage(Component.text().content("Invalid Syntax").color(TextColor.color(UtilClass.error)));
                player.sendMessage(Component.text().content("/gamemode <type> [player]").color(TextColor.color(UtilClass.error)));
                return true;
            }

            if (args.length == 2) {
                player = Bukkit.getPlayer(args[1]);
            }

            changeGameMode(player, args);

            return true;
        }
        else {
            player.sendMessage(Component.text().content("You need to be a server operator to do this command").color(TextColor.color(UtilClass.error)));
        }
        return false;
    }

    private void changeGameMode(Player player, String[] args) {

        switch (args[0]) {
            case "0", "survival" -> {
                player.setGameMode(org.bukkit.GameMode.SURVIVAL);
                player.sendMessage(Component.text()
                        .content("Gamemode changed to " + player.getGameMode()).color(TextColor.color(UtilClass.success)).build());
                return;
            }
            case "1", "creative" -> {
                player.setGameMode(org.bukkit.GameMode.CREATIVE);
                player.sendMessage(Component.text()
                        .content("Gamemode changed to " + player.getGameMode()).color(TextColor.color(UtilClass.success)).build());
                return;
            }
            case "2", "adventure" -> {
                player.setGameMode(org.bukkit.GameMode.ADVENTURE);
                player.sendMessage(Component.text()
                        .content("Gamemode changed to " + player.getGameMode()).color(TextColor.color(UtilClass.success)).build());
                return;
            }
            case "3", "spectator" -> {
                player.setGameMode(org.bukkit.GameMode.SPECTATOR);
                player.sendMessage(Component.text()
                        .content("Gamemode changed to " + player.getGameMode()).color(TextColor.color(UtilClass.success)).build());
                return;
            }
        }
        player.sendMessage(Component.text().content("Sorry thats not a valid gamemode").color(TextColor.color(UtilClass.success)));


    }
}
