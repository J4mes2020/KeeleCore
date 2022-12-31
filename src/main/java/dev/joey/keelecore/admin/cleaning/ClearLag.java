package dev.joey.keelecore.admin.cleaning;

import dev.joey.keelecore.managers.supers.SuperCommand;
import dev.joey.keelecore.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

import static dev.joey.keelecore.util.UtilClass.keeleCore;

public class ClearLag extends SuperCommand {

    int clearTimer = 18000;
    int ticksToRemoval = 3000;
    ArrayList<Entity> entities = new ArrayList<>();
    int entityRemovalCount;
    boolean enabled;
    int timeRemaining = clearTimer / 20;

    BukkitTask bukkitTaskCounter;

    protected void startClearLag() {
        Bukkit.getScheduler().runTaskTimer(keeleCore, () -> {

            warningClear();
            Bukkit.getScheduler().runTaskLater(keeleCore, () -> {
                clearLagNow(null);

                if (bukkitTaskCounter != null) {
                    bukkitTaskCounter.cancel();
                }

                timeRemaining = clearTimer / 20;
                bukkitTaskCounter = Bukkit.getScheduler().runTaskTimerAsynchronously(keeleCore, () -> timeRemaining--, 0, 20);


            }, 1200);
        }, 0, clearTimer);
    }


    protected void clearLagNow(String time) {

        entityRemovalCount = 0;

        for (World world : Bukkit.getWorlds()) {
            entities.addAll(world.getEntities());
        }

        for (Entity entity : entities) {

            if (entity instanceof LivingEntity) {

                LivingEntity livingEntity = (LivingEntity) entity;

                if (livingEntity.customName() == null
                        && livingEntity.getTicksLived() >= ticksToRemoval
                        && livingEntity.getChunk().getEntities().length > 2
                        && !livingEntity.isLeashed()) {

                    if (!(livingEntity instanceof Player)
                            || livingEntity instanceof NPC
                            || livingEntity instanceof Illager
                            || livingEntity instanceof Boss
                            || livingEntity instanceof Warden) {

                        livingEntity.remove();
                        entityRemovalCount++;
                    }

                }

            } else {

                if (time == null) {
                    if (entity.customName() == null
                            && entity.getTicksLived() >= ticksToRemoval
                            && entity.getChunk().getEntities().length > 2
                            && !(entity instanceof Minecart)) {

                        entity.remove();
                        entityRemovalCount++;

                    }
                } else {
                    if (entity.customName() == null
                            && entity.getTicksLived() >= Integer.parseInt(time)
                            && entity.getChunk().getEntities().length > 2
                            && !(entity instanceof Minecart)) {

                        entity.remove();
                        entityRemovalCount++;

                    }
                }

            }
        }

        if (entityRemovalCount > 1 || entityRemovalCount == 0) {
            Bukkit.getServer().sendMessage(Component.text().content("**************************************************************")
                    .decorate(TextDecoration.BOLD)
                    .append(Component.text("\n\n"))
                    .append(Component.text().content("               Cleared " + entityRemovalCount + " Entities"))
                    .append(Component.text("\n\n"))
                    .append(Component.text().content("**************************************************************")
                            .decorate(TextDecoration.BOLD)).color(TextColor.color(UtilClass.error)).build());
        } else {
            Bukkit.getServer().sendMessage(Component.text().content("**************************************************************")
                    .decorate(TextDecoration.BOLD)
                    .append(Component.text("\n\n"))
                    .append(Component.text().content("               Cleared " + entityRemovalCount + " Entity"))
                    .append(Component.text("\n\n"))
                    .append(Component.text().content("**************************************************************")
                            .decorate(TextDecoration.BOLD)).color(TextColor.color(UtilClass.error)).build());
        }

    }

    private void warningClear() {
        Bukkit.getServer().sendMessage(Component.text().content("**************************************************************")
                .decorate(TextDecoration.BOLD)
                .append(Component.text("\n\n"))
                .append(Component.text().content("               Items will be cleared in 1 minute"))
                .append(Component.text("\n\n"))
                .append(Component.text().content("**************************************************************")
                        .decorate(TextDecoration.BOLD)).color(TextColor.color(UtilClass.error)).build());
    }


    public boolean isEnabled() {
        return enabled;
    }

}
