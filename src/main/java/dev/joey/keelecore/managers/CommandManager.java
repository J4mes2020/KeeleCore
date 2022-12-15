package dev.joey.keelecore.managers;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.admin.commands.*;

public class CommandManager {

    public CommandManager(KeeleCore keeleCore) {

        keeleCore.getCommand("gamemode").setExecutor(new GameModeCommand());
        keeleCore.getCommand("time").setExecutor(new TimeSettingsCommand());
        keeleCore.getCommand("teleport").setExecutor(new TeleportCommand());
        keeleCore.getCommand("vanish").setExecutor(new VanishCommand(keeleCore));
        keeleCore.getCommand("whois").setExecutor(new WhoISCommand());
        keeleCore.getCommand("god").setExecutor(new GodCommand());
        keeleCore.getCommand("feed").setExecutor(new FeedCommand());
        keeleCore.getCommand("heal").setExecutor(new HealCommand());


    }
}
