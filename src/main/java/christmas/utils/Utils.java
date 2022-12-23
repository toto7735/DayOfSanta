package christmas.utils;

import christmas.Main;
import christmas.data.BlockData;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockTypes;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.util.WorldEditRegionConverter;
import net.kyori.adventure.bossbar.BossBar;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.ChunkProviderServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.Chunk;
import net.minecraft.world.level.lighting.LightEngine;
import net.raidstone.wgevents.WorldGuardEvents;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import oshi.util.tuples.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Utils {

    private static Map<Player, Pair<Location, Location>> selection = new HashMap<>();
    private static int round = 1;

    public static void checkAndPaste(int round, Location location) {
        if (round == 1) {
            for (Location location1 : BlockData.getBlockMap().keySet()) {
                if (location.distance(location1) > 10) continue;
                christmas.utils.Block block = BlockData.getBlockMap().get(location1);
                if (!block.getLocation().getWorld().getBlockAt(block.getLocation()).getType().equals(Material.AIR)) continue;
                new BukkitRunnable() {
                    public void run() {
                        if (block.getFallHeight() > 0) {
                            FallingBlock fallingBlock = location1.getWorld().spawnFallingBlock(location1.clone().add(0, block.getFallHeight(), 0), block.getBlockData());
                        }
                        location1.getWorld().getBlockAt(location1).setBlockData(block.getBlockData());
                        for (int i = 0; i < 10; ++i) location1.getWorld().spawnParticle(Particle.CLOUD,
                                location1.clone().add(new Random().nextInt(2 - 1) + 1, new Random().nextInt(2 - 1) + 1, new Random().nextInt(2 - 1) + 1), 1, 1,
                                new Random().nextInt(2 - 1) + 1, new Random().nextInt(2 - 1) + 1, new Random().nextInt(2 - 1) + 1);
                    }
                }.runTaskLater(Main.getInstance(), block.getSpawnAfter());
            }
        } else if (round == 2) {
            for (Location location1 : BlockData.getBlockMap().keySet()) {
                if (location.distance(location1) > 10) continue;
                christmas.utils.Block block = BlockData.getBlockMap().get(location1);
                if (!block.getLocation().getWorld().getBlockAt(block.getLocation()).getType().equals(Material.AIR)) continue;
                new BukkitRunnable() {
                    public void run() {
                        if (block.getFallHeight() > 0) {
                            FallingBlock fallingBlock = location1.getWorld().spawnFallingBlock(location1.clone().add(0, block.getFallHeight(), 0), block.getBlockData());
                        }
                        location1.getWorld().getBlockAt(location1).setBlockData(block.getBlockData());
                        for (int i = 0; i < 10; ++i) location1.getWorld().spawnParticle(Particle.REDSTONE,
                                location1.clone().add(new Random().nextInt(2 - 1) + 1, new Random().nextInt(2 - 1) + 1, new Random().nextInt(2 - 1) + 1), 1, 1,
                                new Random().nextInt(2 - 1) + 1, new Random().nextInt(2 - 1) + 1, new Random().nextInt(2 - 1) + 1, new Particle.DustOptions(Color.YELLOW, 5));
                    }
                }.runTaskLater(Main.getInstance(), block.getSpawnAfter());
            }
        } else if (round == 3) {
            for (Location location1 : BlockData.getBlockMap().keySet()) {
                if (location.distance(location1) > 10) continue;
                christmas.utils.Block block = BlockData.getBlockMap().get(location1);
                if (!block.getLocation().getWorld().getBlockAt(block.getLocation()).getType().equals(Material.AIR)) continue;
                new BukkitRunnable() {
                    public void run() {
                        if (block.getFallHeight() > 0) {
                            FallingBlock fallingBlock = location1.getWorld().spawnFallingBlock(location1.clone().add(0, block.getFallHeight(), 0), block.getBlockData());
                        }
                        location1.getWorld().getBlockAt(location1).setBlockData(block.getBlockData());
                        for (int i = 0; i < 10; ++i) location1.getWorld().spawnParticle(Particle.REDSTONE,
                                location1.clone().add(new Random().nextInt(2 - 1) + 1, new Random().nextInt(2 - 1) + 1, new Random().nextInt(2 - 1) + 1), 1, 1,
                                new Random().nextInt(2 - 1) + 1, new Random().nextInt(2 - 1) + 1, new Random().nextInt(2 - 1) + 1, new Particle.DustOptions(Color.PURPLE, 5));
                    }
                }.runTaskLater(Main.getInstance(), block.getSpawnAfter());
            }
        }

    }
    public static void stopMusic(Player player) {
        player.stopSound("christmas:music");
    }

    public static void reset(Player player) {
        switch (Utils.getRound()) {
            case 1:
                clearArea(WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld())).getRegion("round1"), player.getWorld());
                clearArea(WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld())).getRegion("round2"), player.getWorld());
                break;
            case 2:
                clearArea(WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld())).getRegion("round1"), player.getWorld());
                clearArea(WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld())).getRegion("round2"), player.getWorld());
                break;
            case 3:
                clearArea(WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld())).getRegion("round1"), player.getWorld());
                clearArea(WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld())).getRegion("round2"), player.getWorld());
                clearArea(WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld())).getRegion("round3"), player.getWorld());
                break;
        }
        player.getInventory().clear();
        ItemStack present = new ItemStack(Material.WHITE_SHULKER_BOX);
        ItemMeta itemMeta = present.getItemMeta();
        itemMeta.setDisplayName("§f§lPresent");
        present.setItemMeta(itemMeta);
        player.getInventory().setItem(5, present);
        present.setType(Material.YELLOW_SHULKER_BOX);
        itemMeta.setDisplayName("§e§lPresent");
        present.setItemMeta(itemMeta);
        player.getInventory().setItem(6, present);
        present.setType(Material.PINK_SHULKER_BOX);
        itemMeta.setDisplayName("§d§lPresent");
        present.setItemMeta(itemMeta);
        player.getInventory().setItem(7, present);
        present.setType(Material.PURPLE_SHULKER_BOX);
        itemMeta.setDisplayName("§5§lPresent");
        present.setItemMeta(itemMeta);
        player.getInventory().setItem(8, present);
        player.setHealth(player.getMaxHealth());
        player.teleport(new Location(Bukkit.getWorld("world"), 72.5, 81, 74.5, -90, 0)); //todo
        for (Entity entity : player.getWorld().getEntities()) {
            if (!(entity instanceof Item)) continue;
            entity.remove();
        }
        Utils.setRound(1);
    }

    public static int getRound() {
        return round;
    }

    public static void setRound(int r) {
        round = r;
    }

    public static void clearSchematic(File file) {
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            Clipboard clipboard = reader.read();
            for (int x = clipboard.getMinimumPoint().getX(); x <= clipboard.getMaximumPoint().getX(); x++) {
                for (int y = clipboard.getMinimumPoint().getY(); y <= clipboard.getMaximumPoint().getY(); y++) {
                    for (int z = clipboard.getMinimumPoint().getZ(); z <= clipboard.getMaximumPoint().getZ(); z++) {
                        BlockState block = clipboard.getBlock(BlockVector3.at(x, y, z));
                        if (block.getBlockType().getId().equals("minecraft:air")) continue;
                        Bukkit.getWorld("world").getBlockAt(x, y, z).setType(Material.AIR);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void clearArea(ProtectedRegion region, org.bukkit.World world2) {
        World world = BukkitAdapter.adapt(world2);
        EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder().world(world).build();
        editSession.setBlocks(WorldEditRegionConverter.convertToRegion(region), BlockTypes.AIR.getDefaultState());
        editSession.close();
    }

    public static void clearArea(Region region, org.bukkit.World world2) {
        World world = BukkitAdapter.adapt(world2);
        EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder().world(world).build();
        editSession.setBlocks(region, BlockTypes.AIR.getDefaultState());
        editSession.close();
    }

    public static void pasteSchematic(Location location, File file) {
        ClipboardFormat clipboardFormat = ClipboardFormats.findByFile(file);
        Clipboard clipboard;
        BlockVector3 blockVector3 = BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (clipboardFormat != null) {
            try (ClipboardReader clipboardReader = clipboardFormat.getReader(new FileInputStream(file))) {
                if (location.getWorld() == null)
                    throw new NullPointerException("Failed due to world being null");
                World world = BukkitAdapter.adapt(location.getWorld());
                EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder().world(world).build();
                clipboard = clipboardReader.read();
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(blockVector3)
                        .ignoreAirBlocks(false)
                        .build();
                try {
                    Operations.complete(operation);
                    editSession.close();
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static String locationToString(Location location) {
        Bukkit.broadcastMessage(location.getWorld().getName());
        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ();
    }

    public static Location getLocationFromString(String str) {
        return new Location(Bukkit.getWorld(str.split(",")[0]), Double.parseDouble(str.split(",")[1]), Double.parseDouble(str.split(",")[2]), Double.parseDouble(str.split(",")[3]));
    }

    public static Location getPos1(Player player) {
        return selection.get(player).getA();
    }

    public static Location getPos2(Player player) {
        return selection.get(player).getB();
    }

    public static void setPos1(Player player, Location location) {
        if (selection.get(player) != null && selection.get(player).getB() != null) selection.put(player, new Pair<>(location, selection.get(player).getB()));
        else selection.put(player, new Pair<>(location, null));
    }

    public static void setPos2(Player player, Location location) {
        if (selection.get(player) != null && selection.get(player).getA() != null) selection.put(player, new Pair<>(selection.get(player).getA(), location));
        else selection.put(player, new Pair<>(null, location));
    }

    // not my code
    public static void setBlocksNMS(Block b, Material material, byte data, boolean applyLightUpdate) {
        WorldServer nmsWorld = ((CraftWorld) b.getWorld()).getHandle();
        Chunk nmsChunk = nmsWorld.d(b.getX() >> 4, b.getZ() >> 4);
        BlockPosition bp = new BlockPosition(b.getX(), b.getY(), b.getZ());
        IBlockData ibd = CraftMagicNumbers.getBlock(material, data);
        ChunkProviderServer chunkProvider = nmsWorld.k();
        //modify blocks
        nmsChunk.a(bp, ibd, false);
        //update lights
        if (applyLightUpdate) {
            LightEngine engine = chunkProvider.a();
            engine.a(bp);
        }
        // send to client?
        chunkProvider.a(bp);
    }

}
