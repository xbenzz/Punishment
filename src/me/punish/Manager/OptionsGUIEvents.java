package me.punish.Manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.punish.Punish;
import me.punish.GUI.PunishPage;
import me.punish.Objects.Punishment;
import me.punish.Utils.Type;

public class OptionsGUIEvents implements Listener {
	
	@EventHandler
	public void clickOptionsGUI(InventoryClickEvent event) {
		  if (!event.getInventory().getName().contains("More Options")) {
			  return;
		  }
		  event.setCancelled(true);

		  Player player = (Player)event.getWhoClicked();
		  String name = ChatColor.stripColor(event.getInventory().getItem(4).getItemMeta().getDisplayName());
		  UUID uuid = Bukkit.getOfflinePlayer(name).getUniqueId();
		  String reason = ChatColor.stripColor(event.getInventory().getItem(4).getItemMeta().getLore().get(0));
		  OfflinePlayer target = Bukkit.getOfflinePlayer(uuid);
	    
		  Date d = new Date();
		  SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Punishment p = new Punishment(uuid);
		  
		  
		  /** 
		   * Go Back
		   */
		  if (event.getSlot() == 18) { 
			   Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
				   List<Punishment> puns = p.getHistory();
				   Bukkit.getScheduler().runTask(Punish.getInstance(), () -> {
					   player.openInventory(PunishPage.openPunish(player, uuid.toString(), reason, puns));
				   });
			   });
		   }
		  
		  
		  /** 
		   * Clear All Punishments
		   */
		  if ((event.getSlot() == 13) && (player.hasPermission("Punish.Clear"))) { 
			  p.clearAll();
		        
			  Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert"))
			  .forEach(s -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " has cleared &c" + target.getName() + " punishments")));
			  
		      player.closeInventory();
		   }
		  
		  
		  /** 
		   * Tpa Ban (2 Days)
		   */
		  if ((event.getSlot() == 12) && (player.hasPermission("Punish.TpaBan"))) { 
			  Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {	  
				   if (p.isPunished(Type.TPA)) {
					   player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer is already tpa banned!"));
					   player.closeInventory();
					   return;
			       }
				   
				   Punishment punish = new Punishment(uuid, player.getUniqueId().toString(), reason, 172800L, Type.TPA, Type.TPA, 1, date.format(d), 1);
				   punish.execute();
			  
			        
				   Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert")).forEach(s -> 
				   s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " has revoked tpa to &c" + target.getName() + " for " + reason)));
			  });
	          
		      player.closeInventory();
		   }
		  
		  
		  /** 
		   * Tpa Ban (2 Days)
		   */
		  if ((event.getSlot() == 14) && (player.hasPermission("Punish.RevokeSell"))) { 
			  Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {	  
				  if (p.isPunished(Type.SELL)) {
					   player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer is already sell banned!"));
					   player.closeInventory();
					   return;
			       }
				   
				   Punishment punish = new Punishment(uuid, player.getUniqueId().toString(), reason, 0L, Type.SELL, Type.SELL, 1, date.format(d), 1);
				   punish.execute();
			 
				   Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert"))
				   .forEach(s -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " has revoked sell to &c" + target.getName() + " for " + reason)));
			  });
		        
		      player.closeInventory();
		   }
		  
		  
		  /** 
		   * Unmute
		   */
		  if ((event.getSlot() == 20) && (player.hasPermission("Punish.Unmute"))) { 
			  Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {	   
				  Punishment punish = new Punishment(uuid, Type.MUTE);
				  if (punish.getID() != 0 && punish.getPunisher() != player.getUniqueId().toString() && !player.hasPermission("Punish.Bypass")) {
					  player.sendMessage(ChatColor.RED + "Only " + Bukkit.getOfflinePlayer(UUID.fromString(punish.getPunisher())).getName() + " can unmute this player!");
					  player.closeInventory();
					  return;
				  }
				  punish.remove();
			  
		        
				  Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert"))
				  .forEach(s -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " unmuted &c" + target.getName())));
			  });
		         
		      player.closeInventory();
		   }
		  
		  
		  /** 
		   * Unban
		   */
		  if ((event.getSlot() == 21) && (player.hasPermission("Punish.Unban"))) { 
			  Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {	    
				  Punishment punish = new Punishment(uuid, Type.BAN);
				  if (punish.getID() != 0 && punish.getPunisher() != player.getUniqueId().toString() && !player.hasPermission("Punish.Bypass")) {
					  player.sendMessage(ChatColor.RED + "Only " + Bukkit.getOfflinePlayer(UUID.fromString(punish.getPunisher())).getName() + " can unban this player!");
					  player.closeInventory();
					  return;
				  }
				  punish.remove();
			  
		        
				  Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert"))
				  .forEach(s -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " unbanned &c" + target.getName())));
			  });
		        
		      player.closeInventory();
		   }
		  
		  
		  /** 
		   * Grant Tpa
		   */
		  if ((event.getSlot() == 23) && (player.hasPermission("Punish.TpaBan"))) { 
			  Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {	   
				  Punishment punish = new Punishment(uuid, Type.TPA);
				  if (punish.getID() != 0 && punish.getPunisher() != player.getUniqueId().toString() && !player.hasPermission("Punish.Bypass")) {
					  player.sendMessage(ChatColor.RED + "Only " + Bukkit.getOfflinePlayer(UUID.fromString(punish.getPunisher())).getName() + " can untpaban this player!");
					  player.closeInventory();
					  return;
				  }
				  punish.remove();
			  
			        
				  Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert"))
				  .forEach(s -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " has granted &c" + target.getName() + " tpa-access")));
			  });
		        
		      player.closeInventory();
		   }
		  
		  
		  /** 
		   * Grant Sell
		   */
		  if ((event.getSlot() == 24) && (player.hasPermission("Punish.RevokeSell"))) { 
			  Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {	  
				  Punishment punish = new Punishment(uuid, Type.SELL);
				  if (punish.getID() != 0 && punish.getPunisher() != player.getUniqueId().toString() && !player.hasPermission("Punish.Bypass")) {
					  player.sendMessage(ChatColor.RED + "Only " + Bukkit.getOfflinePlayer(UUID.fromString(punish.getPunisher())).getName() + " can unsellban this player!");
					  player.closeInventory();
					  return;
				  }
				  punish.remove();
			  
		        
				  Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert"))
				  .forEach(s -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " has granted &c" + target.getName() + " sell-access")));
			  });
		        
		      player.closeInventory();
		   }
		  
		  
		  
	  }

}
