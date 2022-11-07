package dev.joey.keelecore.auth;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.util.FileModifications;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AuthCommands implements CommandExecutor {

    FileModifications fileModifications;

    public AuthCommands(KeeleCore keeleCore) {

        fileModifications = new FileModifications(keeleCore);
        keeleCore.getCommand("keeleauth").setExecutor(this);


    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        if (args.length == 1 && args[0].equalsIgnoreCase("student")) {
            if (!FileModifications.isInFile(player.getUniqueId().toString(), fileModifications.getPlayerFile())) {


                fileModifications.getCustomPlayerConfig().set("Students.", player.getUniqueId());


            }
            else {
                sender.sendMessage("Your already registered as a student");
            }


            return true;
        }

        return false;
    }
}
