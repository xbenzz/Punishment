package me.punish.Commands;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.punish.Punish;
import me.punish.Objects.IPBan;
import me.punish.Utils.Messages;
import net.md_5.bungee.api.ChatColor;

public class PunishIPCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("Punish.IPBan")) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to perform this action!"));
	        return false; 
	    }    
	      
	     if (args.length < 2) {
	         sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cIncorrect Arguments! /puniship <IP> <reason>"));
	         return false;
	     }

	     String ip = args[0];
	     if (!ip.contains(".")) {
			sender.sendMessage(ChatColor.RED + "Error: Invalid IP  Address");
			return false;
	     }
	     
		 String banReason = "";
		 for (int i = 1; i < args.length; i++) {
			 banReason = banReason + args[i] + " ";
		 }
		 banReason = banReason.trim();
		 final String res = banReason;
		 
		 Date d = new Date();
		 SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Messages msg = new Messages();
		 
		 Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
			 IPBan p = new IPBan(ip);
			 if (p.isActive() && p.getID() != 0) {
		          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e" + ip + " &cis already banned!"));
			 } else {
				 String banner = "";
				 if (sender instanceof Player) {
					 Player t = (Player) sender;
					 banner = t.getUniqueId().toString();
				 } else {
					 banner = "CONSOLE";
				 }
				  
		         IPBan ban = new IPBan(ip, banner, res, date.format(d));
		         ban.execute();
		          
				 Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert") && !s.hasPermission("Punish.ViewIP"))
				 .forEach(s -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + sender.getName() + " has ipbanned a player.")));
				  
				 Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.ViewIP"))
				 .forEach(s -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + sender.getName() + " has ipbanned &c" + ip)));
			      
				 Bukkit.getScheduler().runTask(Punish.getInstance(), () -> {
					 for (Player po13 : Bukkit.getOnlinePlayers()) {
						 if (po13.getAddress().getAddress().getHostAddress().equals(ip)) {
							 po13.kickPlayer(msg.ipbanMessage(res, sender.getName()));
						 }
					 }
				 });
			 }
		 });
	    return true;
	}
}
