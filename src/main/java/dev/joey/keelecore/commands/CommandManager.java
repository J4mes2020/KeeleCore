package dev.joey.keelecore.commands;

import dev.joey.keelecore.KeeleCore;

public class CommandManager {

    public CommandManager(KeeleCore keeleCore) {

        keeleCore.getCommand("gamemode").setExecutor(new GameMode());

    }
}
