package christmas.utils;

import christmas.Main;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.block.data.BlockData;

import java.util.HashMap;
import java.util.Map;

public class Block {

    private BlockData blockData;
    private int spawnAfter;
    private int fallHeight;
    private Location location;

    public Block(BlockData blockData, int spawnAfter, int fallHeight, Location location) {
        this.blockData = blockData;
        this.spawnAfter = spawnAfter;
        this.fallHeight = fallHeight;
        this.location = location;
    }

    public Block(org.bukkit.block.Block block, int spawnAfter, int fallHeight) {
        new Block(block.getBlockData(), spawnAfter, fallHeight, block.getLocation());
    }

    public BlockData getBlockData() {
        return blockData;
    }

    public int getSpawnAfter() {
        return spawnAfter;
    }

    public int getFallHeight() {
        return fallHeight;
    }

    public Location getLocation() {
        return location;
    }

    public void saveToConfig() {
        int id = christmas.data.BlockData.getId();
        christmas.data.BlockData.blockData.set(id + ".blockdata", this.blockData.getAsString());
        christmas.data.BlockData.blockData.set(id + ".location", Utils.locationToString(this.location));
        christmas.data.BlockData.blockData.set(id + ".spawnafter", this.spawnAfter);
        christmas.data.BlockData.blockData.set(id + ".fallheight", this.fallHeight);
        christmas.data.BlockData.saveBlockData();
    }

    public static Map<Location, Block> getBlocks() {
        Map<Location, Block> map = new HashMap<>();
        for (String id : christmas.data.BlockData.blockData.getKeys(false)) {
            map.put(Utils.getLocationFromString(christmas.data.BlockData.blockData.getString(id + ".location")), new Block(Main.getInstance().getServer().createBlockData(christmas.data.BlockData.blockData.getString(id + ".blockdata")),
                    christmas.data.BlockData.blockData.getInt(id + ".spanwafter"),
                    christmas.data.BlockData.blockData.getInt(id + ".fallheight"),
                    Utils.getLocationFromString(christmas.data.BlockData.blockData.getString(id + ".location"))));
        }
        return map;
    }

}
