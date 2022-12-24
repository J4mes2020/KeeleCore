package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class VanishCommand extends SuperCommand implements CommandExecutor {

    static ArrayList<UUID> vanishedPlayers = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (commandSenderCheck(sender)) return true;
        Player player = (Player) sender;
        if (noPermission(player, "kc.admin", "kc.vanish")) return true;

        if (args.length == 1) {
            player = Bukkit.getPlayer(args[0]);
        }

        playerNullCheck(player, player);
        if (vanishedPlayers.contains(player.getUniqueId())) { //UNVANISH
            vanishedPlayers.remove(player.getUniqueId());
            for (Player playersOnServer : Bukkit.getOnlinePlayers()) {
                playersOnServer.showPlayer(keeleCore, player);
            }
            player.getInventory().setHelmet(null);
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            UtilClass.sendPlayerMessage(player, "You are now visible", UtilClass.success);

        } else { //VANISH
            vanishedPlayers.add(player.getUniqueId());
            for (Player playersOnServer : Bukkit.getOnlinePlayers()) {
                if (!(hasPermissionToSee(playersOnServer))) {
                    playersOnServer.hidePlayer(keeleCore, player);
                }
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000000, 1, false, false, false));
            applyHead(player);
            UtilClass.sendPlayerMessage(player, "You are now vanished", UtilClass.success);
        }
        return false;
    }

    private boolean hasPermissionToSee(Player player) {
        return player.isOp() || player.hasPermission("kc.see");
    }

    private void applyHead(Player player) {
        if (player.getInventory().getHelmet() == null) {
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            meta.setOwningPlayer(player);
            head.setItemMeta(meta);
            player.getInventory().setHelmet(head);
        }
    }

    public static ArrayList<UUID> getVanishedPlayers() {
        return vanishedPlayers;
    }


    //TODO: Add listener for joining and leaving

}
