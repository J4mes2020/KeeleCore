package dev.joey.keelecore.commands;

import dev.joey.keelecore.KeeleCore;

public class CommandManager {

    public CommandManager(KeeleCore keeleCore) {

        keeleCore.getCommand("gamemode").setExecutor(new GameModeCommand());
        keeleCore.getCommand("time").setExecutor(new TimeSettingsCommand());
        keeleCore.getCommand("teleport").setExecutor(new TeleportCommand());
        keeleCore.getCommand("vanish").setExecutor(new VanishCommand(keeleCore));
        keeleCore.getCommand("whois").setExecutor(new WhoISCommand());


    }
}
