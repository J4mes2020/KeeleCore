package dev.joey.keelecore.commands;

import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class VanishCommand implements CommandExecutor, Listener {

    ArrayList<Player> vanishedPlayers = new ArrayList<>();
    KeeleCore keeleCore;

    public VanishCommand(KeeleCore keeleCore) {
        this.keeleCore = keeleCore;
        keeleCore.getServer().getPluginManager().registerEvents(this, keeleCore);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if (!(sender instanceof Player)) {
            sender.sendMessage("Sorry only a player can run this command");
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("kc.admin") || player.hasPermission("kc.vanish") || player.isOp()) {

            if (args.length == 1 || args.length == 0) {

                if (args.length == 1) {
                    player = Bukkit.getPlayer(args[0]);
                }

                if (player != null) {
                    if (vanishedPlayers.contains(player)) { //UNVANISH
                        vanishedPlayers.remove(player);
                        for (Player playersOnServer : Bukkit.getOnlinePlayers()) {
                            playersOnServer.showPlayer(keeleCore, player);
                        }
                        player.getInventory().setHelmet(null);
                        player.removePotionEffect(PotionEffectType.INVISIBILITY);
                        player.sendMessage(Component.text().content("You are now visible").color(TextColor.color(UtilClass.brightSuccess)));


                    } else { //VANISH
                        vanishedPlayers.add(player);
                        for (Player playersOnServer : Bukkit.getOnlinePlayers()) {
                            if (!(hasPermissionToSee(playersOnServer))) {
                                playersOnServer.hidePlayer(keeleCore, player);
                            }
                        }
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000000, 1, false, false, false));
                        if (player.getInventory().getHelmet() == null) {
                            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                            SkullMeta meta = (SkullMeta) head.getItemMeta();
                            meta.setOwningPlayer(player);
                            head.setItemMeta(meta);
                            player.getInventory().setHelmet(head);
                        }

                        player.sendMessage(Component.text().content("You are now vanished").color(TextColor.color(UtilClass.brightSuccess)));
                    }

                }
                else {
                    sender.sendMessage(Component.text().content(args[0] + " isn't a valid player").color(TextColor.color(UtilClass.error)));
                }
            }else {
                player.sendMessage(Component.text().content("Invalid Syntax \n/vanish [Player]").color(TextColor.color(UtilClass.error)));
            }

        } else {
            player.sendMessage(Component.text().content("You need to be a server operator to do this command").color(TextColor.color(UtilClass.error)));
        }
        return false;
    }

    private boolean hasPermissionToSee(Player player) {
        return player.isOp() || player.hasPermission("kc.see");
    }


    //TODO: Add listener for joining and leaving

}
