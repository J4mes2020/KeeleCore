package dev.joey.keelecore.managers;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.admin.listeners.BlockDefaultThings;
import dev.joey.keelecore.auth.listeners.StudentGUIListener;
import dev.joey.keelecore.util.formatting.ChatFormatting;

public class ListenerManager {


    public ListenerManager(KeeleCore keeleCore) {
        new BlockDefaultThings(keeleCore);
        new StudentGUIListener(keeleCore);
        new ChatFormatting(keeleCore);

    }
}
