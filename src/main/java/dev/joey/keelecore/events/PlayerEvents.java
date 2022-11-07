package dev.joey.keelecore.events;

import dev.joey.keelecore.KeeleCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PlayerEvents implements Listener {
    
    public PlayerEvents(KeeleCore keeleCore) {

        keeleCore.getServer().getPluginManager().registerEvents(this, keeleCore);


    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        if (event.getBlock().getWorld().getName().equals("hub") && event.getPlayer().getWorld().getName().equals("hub")) {

            event.setCancelled(true);

        }

    }
}
