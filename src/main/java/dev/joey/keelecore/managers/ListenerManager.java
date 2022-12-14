package dev.joey.keelecore.managers;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.admin.listeners.BlockPluginListener;

public class ListenerManager {

    KeeleCore keeleCore;

    public ListenerManager(KeeleCore keeleCore) {

        this.keeleCore = keeleCore;
        new BlockPluginListener(keeleCore);

    }
}
