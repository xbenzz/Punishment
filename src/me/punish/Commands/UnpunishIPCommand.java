package me.punish.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.punish.Punish;
import me.punish.Objects.IPBan;
import net.md_5.bungee.api.ChatColor;

public class UnpunishIPCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
	      if (!sender.hasPermission("Punish.IPBan")) {
	        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to perform this action!"));
	        return false; 
	      }    
	      
	      if (args.length == 0) {
	    	sender.sendMessage(ChatColor.RED + "Incorrect Arguments! /unpuniship <IP>");
	        return false;
	      }
	      
	      String ip = args[0];
		  if (!ip.contains(".")) {
			  sender.sendMessage(ChatColor.RED + "Error: Invalid IP Address");
			  return false;
		  }
			     
	      
	      Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
		      IPBan ban = new IPBan(ip);
		      if (ban.isActive() && ban.getID() != 0) {
		    	  if (sender instanceof Player) {
		    		  Player p = (Player) sender;
		    		  ban.remove(p.getUniqueId().toString());
		    	  } else {
		    		  ban.remove("CONSOLE");
		    	  }
		    	  
				  Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert") && !s.hasPermission("Punish.ViewIP"))
				  .forEach(s -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + sender.getName() + " has unipbanned a player.")));
				  
				  Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.ViewIP"))
				  .forEach(s -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + sender.getName() + " has unipbanned &c" + ip)));
		      } else {
		    	  sender.sendMessage(ChatColor.RED + "That IP Address is not banned.");
		      }
	      });
		  return false;
	}

}