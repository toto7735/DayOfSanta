package christmas;

import christmas.commands.Commands;
import christmas.data.BlockData;
import christmas.listeners.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        this.getCommand("stopmusic").setExecutor(new Commands());
        this.getCommand("addblocks").setExecutor(new Commands());
        this.getCommand("moneyeffects").setExecutor(new Commands());
        BlockData.setupBlockData();
    }

    public static Main getInstance() {
        return instance;
    }

}
