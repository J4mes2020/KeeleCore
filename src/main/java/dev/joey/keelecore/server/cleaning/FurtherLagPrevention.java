package dev.joey.keelecore.server.cleaning;

import com.destroystokyo.paper.event.block.TNTPrimeEvent;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class FurtherLagPrevention extends ClearLag implements Listener {

    public FurtherLagPrevention() {
        keeleCore.getServer().getPluginManager().registerEvents(this, keeleCore);

    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {

        if (isEnabled()) {
            event.setCancelled(true);
            UtilClass.sendPlayerMessage(event.getPlayer(), "(!) Sorry we're currently clearing some lag" +
                    " and don't want your items deleted, Please try again in a minute", UtilClass.error);
        }
    }

    @EventHandler
    public void onPrimed(TNTPrimeEvent event) {

        if (isEnabled()) {
            event.setCancelled(true);
            if (event.getPrimerEntity() instanceof Player) {
                UtilClass.sendPlayerMessage((Player) event.getPrimerEntity(),
                        "(!) Sorry we're currently clearing some lag, Please try to ignite again in a minute",
                        UtilClass.error);
            }

        }
    }

    @EventHandler
    public void onFlow(BlockFromToEvent event) {

        if (isEnabled()) {
            if (!event.getBlock().isLiquid()) {
                return;
            }
            event.setCancelled(true);

        }
    }

    @EventHandler
    public void onGrow(BlockGrowEvent event) {

        if (isEnabled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDecay(LeavesDecayEvent event) {

        if (isEnabled()) {
            event.setCancelled(true);
        }
    }

}
