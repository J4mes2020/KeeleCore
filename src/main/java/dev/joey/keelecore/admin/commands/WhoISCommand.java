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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class WhoISCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Sorry only a player can run this command");
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("kc.admin") || player.hasPermission("kc.inspect")) {

            if (args.length == 1) {
                Player victim = Bukkit.getPlayer(args[0]);
                if (victim != null) {
                    player.sendMessage(
                            Component.text("IP: " + victim.getAddress().getAddress()
                                            + "\n" + "Last Login: " + getDate(victim.getLastLogin())
                                            + "\n" + "Last Seen: " + getDate(victim.getLastSeen()))
                                    .color(TextColor.color(UtilClass.information)).toBuilder().build());

                } else {
                    player.sendMessage(Component.text().content(args[0] + " isn't a valid player").color(TextColor.color(UtilClass.error)));
                }
            } else {
                player.sendMessage(Component.text().content("Invalid Syntax \n/whois [Player]").color(TextColor.color(UtilClass.error)));
            }

        } else {
            player.sendMessage(Component.text().content("You need to be a server operator to do this command").color(TextColor.color(UtilClass.error)));
        }

        return false;
    }

    private String getDate(long epoc) {

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        return format.format(epoc);
    }
}
