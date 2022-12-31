package dev.joey.keelecore.admin.vanish;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class VanishListener extends Vanish implements Listener {

    Inventory fakeInventory;
    Chest chest;

    public VanishListener() {
        keeleCore.getServer().getPluginManager().registerEvents(this, keeleCore);
        //SilentChestListeners.setupSoundListener();
        //SilentChestListeners.setupAnimationListener();
    }

    @EventHandler
    public void onOpenChest(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (event.getClickedBlock() == null) {
            return;
        }

        if (isVanished(player)
                && event.getAction().isRightClick()
                && event.getClickedBlock().getType() == Material.CHEST) {

            chest = (Chest) event.getClickedBlock().getState();

            fakeInventory = chest.getInventory();
            fakeInventory.setItem(4, new ItemStack(Material.BARRIER));
            player.openInventory(fakeInventory);

            event.setCancelled(true);

        }

    }

    @EventHandler
    public void onPickUp(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (isVanished(player)) {
                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onCloseChest(InventoryCloseEvent event) {

        Player player = (Player) event.getPlayer();

        if (isVanished(player)
                && event.getInventory() == fakeInventory
                && chest != null) {

            chest.getInventory().setContents(fakeInventory.getContents());


        }

    }

    public static void setupAnimationListener() {
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(keeleCore, ListenerPriority.HIGH,
                        PacketType.Play.Server.BLOCK_ACTION) {
                    @Override
                    public void onPacketSending(PacketEvent e) {
                        if (e.getPacketType() == PacketType.Play.Server.BLOCK_ACTION) {
                            Player listener = e.getPlayer();
                            // is the action the chest-opening-action?
                            if (e.getPacket().getIntegers().read(1) != 1)
                                return;
                            BlockPosition position = e.getPacket()
                                    .getBlockPositionModifier().read(0);
                            if (position == null)
                                return;
                            Location loc = position.toVector().toLocation(
                                    listener.getWorld());
                            Block b = listener.getWorld().getBlockAt(loc);
                            if (!(b.getState() instanceof Chest chest)) {
                                return;
                            }
                            Inventory inv = chest.getBlockInventory();
                            List<HumanEntity> humanViewers = inv.getViewers();
                            List<Player> players = new ArrayList<>();
                            for (HumanEntity entity : humanViewers) {
                                if (entity instanceof Player)
                                    players.add((Player) entity);
                            }
                            for (Player p : players) {
                                if (isVanished(p)) {
                                    // cancel it since one of the viewers is
                                    // invisible
                                    e.setCancelled(true);
                                }
                            }
                        }
                    }
                });
    }

    public static void setupSoundListener() {
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(keeleCore, ListenerPriority.HIGH,
                        PacketType.Play.Server.NAMED_SOUND_EFFECT) {
                    @Override
                    public void onPacketSending(PacketEvent e) {
                        if (e.getPacketType() == PacketType.Play.Server.NAMED_SOUND_EFFECT) {
                            Player listener = e.getPlayer();
                            // is the action the chest-opening-sound?
                            if (!(e.getPacket().getStrings().read(0)
                                    .equalsIgnoreCase("random.chestopen") || e
                                    .getPacket().getStrings().read(0)
                                    .equalsIgnoreCase("random.chestclosed")))
                                return;
                            // divide the location by 8, since it's a bit
                            // obfuscated
                            Location loc = new Location(listener.getWorld(), e
                                    .getPacket().getDoubles().read(0) / 8, e
                                    .getPacket().getDoubles().read(1) / 8, e
                                    .getPacket().getDoubles().read(2) / 8);
                            Block b = listener.getWorld().getBlockAt(loc);
                            check:
                            if (!(b.getState() instanceof Chest)) {
                                // search for adjacent blocks, too, since the
                                // position of the animation is not exact
                                List<Location> adjacentBlockLocations = getAdjacentBlockLocations(loc);
                                for (Location otherLocation : adjacentBlockLocations) {
                                    Block otherBlock = listener.getWorld()
                                            .getBlockAt(otherLocation);
                                    if (otherBlock.getState() instanceof Chest) {
                                        b = otherBlock;
                                        loc = otherLocation;
                                        break check;
                                    }
                                }
                                return;
                            }
                            if (e.getPacket().getStrings().read(0)
                                    .equalsIgnoreCase("random.chestclosed")) {
                                for (Player p : listener.getWorld().getPlayers()) {
                                    if (isVanished(p)
                                            && p.getLocation().distance(loc) < 6) {
                                        // cancel it since an invisible player
                                        // is
                                        // nearby
                                        e.setCancelled(true);
                                    }
                                }
                                return;
                            }
                            Chest chest = (Chest) b.getState();
                            Inventory inv = chest.getBlockInventory();
                            List<HumanEntity> humanViewers = inv.getViewers();
                            for (HumanEntity entity : humanViewers) {
                                if (entity instanceof Player player)
                                    if (isVanished(player)) {
                                        // cancel it since one of the viewers is
                                        // invisible
                                        e.setCancelled(true);
                                    }
                            }
                        }
                    }
                });
    }

    private static Location add(Location l, int x, int z) {
        return new Location(l.getWorld(), l.getX() + x, l.getY(), l.getZ() + z);
    }

    private static List<Location> getAdjacentBlockLocations(Location loc) {
        List<Location> adjacentBlockLocations = new ArrayList<>();
        adjacentBlockLocations.add(add(loc, 1, 0));
        adjacentBlockLocations.add(add(loc, -1, 0));
        adjacentBlockLocations.add(add(loc, 0, -1));
        adjacentBlockLocations.add(add(loc, 0, 1));
        adjacentBlockLocations.add(add(loc, 1, 1));
        adjacentBlockLocations.add(add(loc, -1, -1));
        adjacentBlockLocations.add(add(loc, 1, -1));
        adjacentBlockLocations.add(add(loc, -1, 1));
        return adjacentBlockLocations;
    }
}
