package me.goodbee.touchgrassreminderplugin;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class TouchGrassReminder extends JavaPlugin {

    private static TouchGrassReminder instance;

    public static FileConfiguration config;
    TimeTracking timeTracking = new TimeTracking();
    @Override
    public void onEnable() {

        instance = this;

        if(instance == null) {
            getLogger().severe("idk how this happened but instance is null right after I assigned it... so like plugin will probably not load or smth (probably)");
        }

        this.saveResource("config.yml", false);

        config = this.getConfig();

        getCommand("touchgrass").setExecutor(new testcommand());
        timeTracking.setup();
        getServer().getPluginManager().registerEvents(timeTracking, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static TouchGrassReminder getPlugin() {
        return instance;
    }

    public static FileConfiguration getPlConfig() {
        return config;
    }
}
