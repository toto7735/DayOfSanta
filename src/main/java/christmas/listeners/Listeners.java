package christmas.listeners;

import christmas.Main;
import christmas.utils.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.raidstone.wgevents.WorldGuardEvents;
import net.raidstone.wgevents.events.RegionEnteredEvent;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class Listeners implements Listener {

    @EventHandler
    public void onRegionEntered(RegionEnteredEvent event) {
        if (event.getPlayer() == null) return;
        Player player = event.getPlayer();
        if (event.getRegion().getId().contains("fail")) {
            Utils.stopMusic(player);
            Utils.reset(player);
        }
        switch (event.getRegion().getId()) {
            case "music_start" -> {
                player.playSound(player.getLocation(), "christmas:music", 1, 1);
                break;
            }
            case "round2" -> {
                Utils.setRound(2);
                break;
            }
            case "round3" -> {
                Utils.setRound(3);
                break;
            }
            case "receivesand" -> {
                player.getInventory().setItem(0, new ItemStack(Material.SAND, 2));
                break;
            }
            case "receivebricks" -> {
                player.getInventory().setHeldItemSlot(4);
                player.getInventory().setItem(4, new ItemStack(Material.BRICKS, 1));
                break;
            }
            case "receivebricks2" -> {
                player.getInventory().setHeldItemSlot(0);
                player.getInventory().setItem(0, new ItemStack(Material.BRICKS, 2));
                player.getInventory().setItem(4, new ItemStack(Material.AIR));
                break;
            }
            case "receivepressureplate", "receivepressureplate2" -> {
                player.getInventory().setHeldItemSlot(0);
                player.getInventory().setItem(0, new ItemStack(Material.LIGHT_WEIGHTED_PRESSURE_PLATE, 1));
                break;
            }
        }
    }

    @EventHandler
    public void onScaffold(PlayerMoveEvent event) {
        PlayerInventory playerInventory = event.getPlayer().getInventory();
        if (playerInventory.getItemInMainHand().getType().equals(Material.BRICKS) && playerInventory.getHeldItemSlot() == 4) event.getPlayer().getWorld().getBlockAt(event.getPlayer().getLocation().subtract(0, 1, 0)).setType(Material.BRICKS);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (WorldGuardEvents.getRegionsNames(event.getPlayer().getUniqueId()).contains("round1")) {
            Utils.checkAndPaste(1, event.getTo());
        } else if (WorldGuardEvents.getRegionsNames(event.getPlayer().getUniqueId()).contains("round2")) {
            Utils.checkAndPaste(2, event.getTo());
        } else if (WorldGuardEvents.getRegionsNames(event.getPlayer().getUniqueId()).contains("round3")) {
            Utils.checkAndPaste(3, event.getTo());
        }
    }

    @EventHandler
    public void onClick(BlockBreakEvent event) {
        if (!event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STICK)) return;
        event.setCancelled(true);
        Utils.setPos1(event.getPlayer(), event.getBlock().getLocation());
        event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Pos1 Set!"));
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (!event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STICK) || !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        event.setCancelled(true);
        Utils.setPos2(event.getPlayer(), event.getClickedBlock().getLocation());
        event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Pos2 Set!"));
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        for (int i = 0; i < 3; ++i) event.getBlock().getWorld().spawnParticle(Particle.REDSTONE, event.getBlock().getLocation().add(new Random().nextInt(2 - 1) + 1, new Random().nextInt(2 - 1) + 1, new Random().nextInt(2 - 1) + 1), 1, new Particle.DustOptions(Color.RED, 10));
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Item item = event.getItemDrop();
        if (!item.getItemStack().getType().toString().contains("SHULKER_BOX")) return;
        new BukkitRunnable() {
            public void run() {
                item.remove();
                ArmorStand armorStand = (ArmorStand) item.getWorld().spawnEntity(item.getLocation(), EntityType.ARMOR_STAND);
                armorStand.setInvisible(true);
                armorStand.setHelmet(item.getItemStack());
                new BukkitRunnable() {
                    public void run() {
                        if (armorStand.isOnGround()) {
                            this.cancel();
                            armorStand.remove();
                            armorStand.getLocation().getBlock().setType(item.getItemStack().getType());
                        }
                    }
                }.runTaskTimer(Main.getInstance(), 0, 1);
            }
        }.runTaskLater(Main.getInstance(), 10);
    }

}
