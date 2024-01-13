package me.goodbee.touchgrassreminderplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TimeTracking implements Listener {

    private  long requiredTimeForToastInSeconds;
    private String display;
    private boolean publicShame;
    private Map<String, Long> playtimeMap = new HashMap<String, Long>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        playtimeMap.put(e.getPlayer().getUniqueId().toString(), System.currentTimeMillis());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        playtimeMap.remove(e.getPlayer().getUniqueId().toString());
    }

    public int setup() {
        this.requiredTimeForToastInSeconds = TouchGrassReminder.getPlConfig().getLong("reminderTime");
        this.display = TouchGrassReminder.getPlConfig().getString("display");
        this.publicShame = TouchGrassReminder.getPlConfig().getBoolean("public-shaming");

        int taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(TouchGrassReminder.getPlugin(), new Runnable() {
            @Override
            public void run() {
                long now = System.currentTimeMillis();


                Collection<? extends Player> players = Bukkit.getOnlinePlayers();
                for (Player player : players) {
                    long joinTime = playtimeMap.get(player.getUniqueId().toString());
                    long spentTime = now - joinTime;

                    long spentTimeSeconds = spentTime / 1000;

                    if(spentTimeSeconds >= requiredTimeForToastInSeconds) {
                        Toast.displayTo(player, Material.TALL_GRASS.toString().toLowerCase(), ChatColor.YELLOW + "You've been playing for more than " + display, Toast.Style.GOAL);
                        player.sendMessage(ChatColor.YELLOW + "You've been playing for more than " + display + "." + ChatColor.RED + " Studies have shown the negative impacts of excessive gaming on the brain. " + ChatColor.GREEN + ChatColor.UNDERLINE +  "https://www.health.harvard.edu/blog/the-health-effects-of-too-much-gaming-2020122221645");
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);

                        if(publicShame) {
                            Bukkit.broadcastMessage(ChatColor.RED + player.getName() + " has been playing for more than " + display + ". \n" + ChatColor.YELLOW + "Studies have shown the negative impacts of excessive gaming on the brain.");
                        }

                        Bukkit.getScheduler().runTaskLater(TouchGrassReminder.getPlugin(), new Runnable() {
                            @Override
                            public void run() {
                                Toast.displayTo(player, Material.TALL_GRASS.toString().toLowerCase(), "Excessive gaming may \n interfere with daily life", Toast.Style.GOAL);
                            }
                        }, 20);

                        playtimeMap.replace(player.getUniqueId().toString(), now);
                    }
                }
            }
        }, 20 * 60, 20 * 60);

        Bukkit.getLogger().info("Config: \n reminderTime: " + Long.toString(requiredTimeForToastInSeconds) + "\n display: " + display + "\n publicShame: " + Boolean.toString(publicShame));

        return taskid;
    }
}
