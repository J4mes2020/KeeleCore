package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HealCommand extends SuperCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;

        Player player = (Player) sender;

        if (!(player.hasPermission("kc.admin") || player.hasPermission("kc.heal"))) {
            UtilClass.sendPlayerMessage(player, "Invalid Rank", UtilClass.error);
            return true;
        }
        if (args.length == 0) {
            player.setHealth(20);
            UtilClass.sendPlayerMessage(player, "You healed yourself", UtilClass.success);
            return true;
        }

        if (args.length == 1) {

            Player victim = Bukkit.getPlayer(args[0]);
            if (victim != null) {
                victim.setHealth(20);
                UtilClass.sendPlayerMessage(player, "Healed " + victim.getName(), UtilClass.success);
                return true;
            }
        }

        return false;
    }
}
