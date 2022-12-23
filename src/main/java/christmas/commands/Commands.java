package christmas.commands;

import christmas.Main;
import christmas.utils.Block;
import christmas.utils.Utils;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Random;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if (command.getName().equals("stopmusic")) {
            Utils.stopMusic(player);
        } else if (command.getName().equals("addblocks")) {
            Location pos1 = Utils.getPos1(player);
            Location pos2 = Utils.getPos2(player);
            
            for (int x = (int) Math.min(pos1.getX(), pos2.getX()); x <= Math.max(pos1.getX(), pos2.getX()); ++x) {
                for (int y = (int) Math.min(pos1.getY(), pos2.getY()); y <= Math.max(pos1.getY(), pos2.getY()); ++y) {
                    for (int z = (int) Math.min(pos1.getZ(), pos2.getZ()); z <= Math.max(pos1.getZ(), pos2.getZ()); ++z) {
                        org.bukkit.block.Block block = pos1.getWorld().getBlockAt(x, y, z);
                        if (block.getType().equals(Material.AIR)) continue;
                        Block myBlock = new Block(block.getBlockData(), 20, 0, block.getLocation());
                        myBlock.saveToConfig();
                    }
                }   
            }
        } else if (command.getName().equals("moneyeffects")) {
            Entity entity = player.getWorld().spawnEntity(player.getLocation(), EntityType.CHICKEN);
            player.addPassenger(entity);
            for (int i = 0; i < 5; ++i) {
                Location location = player.getLocation().clone().add(new Random().nextDouble(1 + 1) - 1, -1, new Random().nextDouble(1 + 1) - 1);
                ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location.clone().subtract(0, 10, 0), EntityType.ARMOR_STAND);
                armorStand.setCustomNameVisible(true);
                armorStand.setCustomName("§c- " + new Random().nextInt(9) + 1 + "$");
                armorStand.setVisible(false);
                new BukkitRunnable() {
                    public void run() {
                        armorStand.teleport(location);
                        new BukkitRunnable() {
                            int i = 0;
                            public void run() {
                                if (i >= 40) {
                                    armorStand.remove();
                                    this.cancel();
                                    return;
                                }
                                player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location.add(new Random().nextInt(2 + 2) - 2, new Random().nextInt(3 + 3) - 3, new Random().nextInt(2 + 2) - 2), 5);
                                armorStand.teleport(armorStand.getLocation().add(0, 0.1, 0));
                                ++i;
                            }
                        }.runTaskTimer(Main.getInstance(), 0, 1);
                    }
                }.runTaskLater(Main.getInstance(), 2);
            }
        }
        return false;
    }

}
