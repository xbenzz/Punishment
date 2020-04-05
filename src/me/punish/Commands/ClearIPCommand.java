package me.punish.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.punish.Objects.IPBan;

public class ClearIPCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) { 
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to excute this command");
			return false;
	    }
	    
	    Player player = (Player)sender;
	    if (!player.hasPermission("Punish.IPClear")) {
	    	player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to perform this action!"));
		    return false;
	    }
	    
	    if (args.length < 1) {
	    	player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cIncorrect Arguments, /clearip <ID>"));
	    	return false;
	    }
	    
	    int id = 0;
	    try {
	    	id = Integer.valueOf(args[0]);
	    } catch (NumberFormatException e) {
	    	player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cIncorrect Arguments, /clearip <ID>"));
	    }
	    
		IPBan p = new IPBan();
	    p.clear(id);
	    
	    player.sendMessage(ChatColor.RED + "Cleared IPBan #" + id);
	    return false;
	}
	  
}
