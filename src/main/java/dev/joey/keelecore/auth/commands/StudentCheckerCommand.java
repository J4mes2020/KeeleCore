package dev.joey.keelecore.auth.commands;

import dev.joey.keelecore.auth.StudentGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StudentCheckerCommand implements CommandExecutor {

    StudentGUI GUI = new StudentGUI();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Sorry only a player can run this command");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 1 && args[0].equalsIgnoreCase("open")) {
            GUI.createGUI(null, 9, Component.text("Select User").color(TextColor.color(229, 180, 17)));
            GUI.openGUI(player);
            return true;
        }
        return false;

    }
}
