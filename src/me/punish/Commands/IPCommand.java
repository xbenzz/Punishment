package me.punish.Commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.punish.Punish;
import me.punish.Objects.IPBan;
import me.punish.Objects.Punishment;
import me.punish.Objects.User;
import me.punish.Utils.Type;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class IPCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to excute this command");
			return false;
		}
		
		Player player = (Player) sender;
	    if (!sender.hasPermission("Punish.IPLookup")) {
	        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to perform this action!"));
	        return false; 
	    }    
	      
	    if (args.length == 0) {
	    	player.sendMessage(ChatColor.RED + "Incorrect Arguments! /iplookup <ip/player>");
	        return false;
	    }
	      
	    String input = args[0];
		  
	    Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {  
		    if (input.contains(".")) {
			    User user = new User(input);
			      
			    if (user.getPlayers().isEmpty()) {
			    	player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo accounts found!"));
			    } else {
			    	player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAccounts of &e" + input));
			    	for (User ip : user.getPlayers()) {
			    		ChatColor c = getColor(ip.getPlayer());
			    		String msg = ChatColor.DARK_GRAY + "* " + c + Bukkit.getOfflinePlayer(ip.getPlayer()).getName() + ChatColor.GRAY + " - " + ChatColor.AQUA + ip.getDate();
				        TextComponent message = new TextComponent(msg);
				        message.setClickEvent(new ClickEvent( ClickEvent.Action.OPEN_URL, "https://namemc.com/profile/" + ip.getPlayer()));
				        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to visit their namemc page").create()));
				        player.spigot().sendMessage(message);
			    	}  
			    }
		    } else {
		    	OfflinePlayer target = Bukkit.getOfflinePlayer(input);
			    User pla = new User(target.getUniqueId());
			     
			    if (pla.getIPs().isEmpty()) {
			    	player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo IP's found!"));
			    } else {
			    	player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aIP's of &e" + target.getName()));
			    	for (User ps : pla.getIPs()) {
			    		 ChatColor c = getColor(ps.getIP());
			    		 String msg = ChatColor.DARK_GRAY + "* " + c + ps.getIP() + ChatColor.GRAY + " - " + ChatColor.AQUA + ps.getDate();
				         player.sendMessage(msg);
			    	}
			    }
		    }
	    });
		return false;
	}
	
	public static ChatColor getColor(UUID uuid) {
		Punishment p = new Punishment(uuid);
		if (p.isPunished(Type.MUTE) && p.isPunished(Type.BAN)) {
			return ChatColor.LIGHT_PURPLE;
		} else if (p.isPunished(Type.BAN)) {
			return ChatColor.RED;
		} else if (p.isPunished(Type.MUTE)) {
			return ChatColor.YELLOW;
		} else {
			return ChatColor.GREEN;
		}
	}
	
	public static ChatColor getColor(String ip) {
		IPBan ban = new IPBan(ip);
		if (ban.isActive() && ban.getID() != 0) {
			return ChatColor.RED;
		} else {
			return ChatColor.GREEN;
		}
	}

}
