package me.goodbee.touchgrassreminderplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class testcommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Only players");
            return true;
        }

        Toast.displayTo((Player) sender, Material.TALL_GRASS.toString().toLowerCase(), ChatColor.YELLOW + "You've been playing for more than 4 hours", Toast.Style.GOAL);

        Bukkit.getScheduler().runTaskLater(TouchGrassReminder.getPlugin(), new Runnable() {
            @Override
            public void run() {
                Toast.displayTo((Player) sender, Material.TALL_GRASS.toString().toLowerCase(), "Excessive gaming may \n interfere with daily life", Toast.Style.GOAL);
            }
        }, 20);

        return false;
    }
}
