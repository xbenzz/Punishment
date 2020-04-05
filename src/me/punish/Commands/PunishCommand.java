package me.punish.Commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import me.punish.Punish;
import me.punish.GUI.PunishPage;
import me.punish.Objects.Punishment;

public class PunishCommand implements CommandExecutor {

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("You must be a player to excute this command");
      return false;
    }
    
    Player player = (Player)sender;
    if (!player.hasPermission("Punish.Use")) {
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to perform this action!"));
      return false;
    }
    
    if (args.length < 2) {
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cIncorrect Arguments, /punish <player> <reason>"));
      return false;
    }
    
    String same = args[0];
    if (same.equalsIgnoreCase(player.getName())) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou cannot punish yourself!"));
		return false;
    }
    
    String reason = "";
    for (int i = 1; i < args.length; i++) {
      reason = reason + args[i] + " ";
    }
    reason = reason.trim();
    
    final String r = reason;
    
    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
	Punishment p = new Punishment(target.getUniqueId());
	
    Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
		List<Punishment> puns = p.getHistory();
		boolean isStaff = Punish.getPermissions().playerHas(null, target, "Punish.Use");
		
		Bukkit.getScheduler().runTask(Punish.getInstance(), () -> {
	    	if (target.isOnline()) {
	    		if (target.getPlayer().hasPermission("Punish.Use") && (!player.hasPermission("Punish.Staff"))) {
	    			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou may not punish other staff members!"));
	    		} else {
	    		    player.openInventory(PunishPage.openPunish(player, target.getUniqueId().toString(), r, puns));
	    		}
	    	} else {
	    		if (isStaff && (!player.hasPermission("Punish.Staff"))) {
	    			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou may not punish other staff members!"));
	    		}  else {
	    			player.openInventory(PunishPage.openPunish(player, target.getUniqueId().toString(), r, puns));
	    		}
	    	}
		});
    });
    return true;
  }
  
}
