package me.punish.Commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.punish.Punish;
import me.punish.GUI.IPBanPage;
import me.punish.Objects.IPBan;
import net.md_5.bungee.api.ChatColor;

public class CheckIPCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
		   sender.sendMessage("You must be a player to excute this command");
		   return false;
		}
		
		Player player = (Player) sender;
	    if (!player.hasPermission("Punish.CheckIP")) {
	    	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to perform this action!"));
	    	return false; 
	    }    
	     
	    if (args.length == 0) {
	    	player.sendMessage(ChatColor.RED + "Incorrect Arguments, /checkip <ip>");
	    	return false;
	    }
	      
	    String ip = args[0];
	    if (ip.contains(".")) {
			Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
				IPBan p = new IPBan();
				List<IPBan> puns = p.getHistory(ip);
				Bukkit.getScheduler().runTask(Punish.getInstance(), () -> {
					player.openInventory(IPBanPage.openHistory(player, ip, puns));
				});
			});
	    } else {
			player.sendMessage(ChatColor.RED + "Error: Invalid IP Address");
	    }
	    return false;
	}

}
