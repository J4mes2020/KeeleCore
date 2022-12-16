package dev.joey.keelecore.managers;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.admin.listeners.BlockPluginListener;
import dev.joey.keelecore.auth.listeners.StudentGUIListener;
import dev.joey.keelecore.util.ChatFormatting;

public class ListenerManager {


    public ListenerManager(KeeleCore keeleCore) {
        new BlockPluginListener(keeleCore);
        new StudentGUIListener(keeleCore);
        new ChatFormatting(keeleCore);

    }
}
