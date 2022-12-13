package dev.joey.keelecore.Listeners;

import dev.joey.keelecore.KeeleCore;

public class ListenerManager {

    KeeleCore keeleCore;

    public ListenerManager(KeeleCore keeleCore) {

        this.keeleCore = keeleCore;
        new BlockPluginListener(keeleCore);

    }
}
