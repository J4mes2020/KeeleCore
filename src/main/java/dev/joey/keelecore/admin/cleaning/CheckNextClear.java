package dev.joey.keelecore.admin.cleaning;

import dev.joey.keelecore.util.UtilClass;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CheckNextClear extends ClearLag implements CommandExecutor {

    public CheckNextClear() {
        startClearLag();
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSenderCheck(commandSender)) return true;

        Player player = (Player) commandSender;

        int sec = timeRemaining % 60;
        int min = (timeRemaining / 60)%60;
        int hours = (timeRemaining/60)/60;

        String timeString = "The next clear lag is in " + hours + " Hours, " + min + " Minutes, " + sec +" Seconds";

        UtilClass.sendPlayerMessage(player, timeString, UtilClass.information);

        return false;
    }
}
