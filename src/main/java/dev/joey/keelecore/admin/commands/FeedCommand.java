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

public class FeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Sorry only a player can run this command");
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("kc.admin") || player.hasPermission("kc.feed") || player.isOp()) {

            if (args.length == 0) {
                player.setFoodLevel(40);
                player.sendMessage(Component.text().content("Fed " + player.getName()).color(TextColor.color(UtilClass.brightSuccess)));
                return true;
            }

            if (args.length == 1) {

                Player victim = Bukkit.getPlayer(args[0]);
                if (victim != null) {
                    victim.setFoodLevel(40);
                    player.sendMessage(Component.text().content("Fed " + victim.getName()).color(TextColor.color(UtilClass.brightSuccess)));
                    return true;
                }
            }

        } else {
            player.sendMessage(Component.text().content("You need to be a server operator to do this command").color(TextColor.color(UtilClass.error)));
        }
        return false;
    }
}
