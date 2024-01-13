package me.goodbee.touchgrassreminderplugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class TouchGrassReminder extends JavaPlugin {

    private static TouchGrassReminder instance;

    public static FileConfiguration config;
    TimeTracking timeTracking = new TimeTracking();
    @Override
    public void onEnable() {

        instance = this;

        this.saveResource("config.yml", false);

        config = this.getConfig();
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
