package dev.joey.keelecore.player;

import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class DiscordCommand extends SuperCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSenderCheck(commandSender)) return true;

        Player player = (Player) commandSender;

        player.sendMessage(Component.text().content("Join our discord at: " + keeleCore.getConfig().get("discord")).color(TextColor.color(88, 101, 242)).clickEvent(ClickEvent.openUrl(keeleCore.getConfig().getString("discord"))).build());

        return false;
    }
}
