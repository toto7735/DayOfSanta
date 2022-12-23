package christmas.data;

import christmas.utils.Block;
import christmas.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BlockData {

    public static File file;
    public static FileConfiguration blockData;
    private static Map<Location, Block> map = new HashMap<>();

    public static void setupBlockData() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("ChristmasPlugin").getDataFolder(), "blockdata.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
            }
        }
        blockData = YamlConfiguration.loadConfiguration(file);

        map = Block.getBlocks();
    }

    public static void saveBlockData() {
        try {
            blockData.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reloadBlockData() {
        blockData = YamlConfiguration.loadConfiguration(file);
    }

    public static int getId() {
        int i = -1;
        for (String key : blockData.getKeys(false)) {
            if (Integer.parseInt(key) > i) i = Integer.parseInt(key);
        }
        return i + 1;
    }

    public static Map<Location, Block> getBlockMap() {
        return map;
    }

}
