package dev.joey.keelecore.server.cleaning;

import dev.joey.keelecore.util.UtilClass;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClearLagCommand extends ClearLag implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSenderCheck(commandSender)) return true;

        Player player = (Player) commandSender;

        if (!(player.hasPermission("kc.admin") || player.hasPermission("kc.clearlag"))) {
            UtilClass.sendPlayerMessage(player, "Invalid Rank", UtilClass.error);
            return true;
        }
        if (strings.length >= 1) {
            if (strings[0].matches("[0-9]+")) {
                clearLagNow(strings[0]);
                UtilClass.sendPlayerMessage(player, "The server will clear lag in " + strings[0] + " ticks", UtilClass.success);
                return true;

            }
        } else {
            clearLagNow(String.valueOf(0));
            return true;
        }


        return false;
    }
}
