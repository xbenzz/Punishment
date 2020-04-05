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
import me.punish.Database.DataHandler;
import me.punish.GUI.HistoryPage;
import me.punish.GUI.OptionsPage;
import me.punish.GUI.TempPage;
import me.punish.Objects.Punishment;
import me.punish.Utils.Messages;
import me.punish.Utils.Titles;
import me.punish.Utils.Type;

public class PunishGUIEvents implements Listener {
	
	  @EventHandler
	  public void clickPunishGUI(InventoryClickEvent event) {
		  if (!event.getInventory().getName().equals("Punishment Menu")) {
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
		  
		  Messages msg = new Messages();
		  
		  
		  /**
		   * More Options
		   * (GUI)
		   */
		  if (event.getSlot() == 35) { 
			  player.openInventory(OptionsPage.openMorePage(player, uuid.toString(), reason));
		   }
		  
		  
		  /**
		   * Temp Ban
		   * (GUI)
		   */
		  if ((event.getSlot() == 18) && player.hasPermission("Punish.TempBan")) { 
			  player.openInventory(TempPage.openTemp(player, uuid.toString(), reason));
			  TempPage.inTemp.add(player.getUniqueId());
		  }
		  
		  
		  /**
		   * All Offenses
		   * (GUI)
		   */
		  if (event.getSlot() == 53) {
			   Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
				   List<Punishment> puns = p.getHistory();
				   Bukkit.getScheduler().runTask(Punish.getInstance(), () -> {
					   player.openInventory(HistoryPage.openHistory(player, uuid.toString(), reason, puns));
				   });
			   });
		   }
		  
		  /** Warning
		   * Paper
		   */
		  if (event.getSlot() == 17) { 
			  Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
				  Punishment punish = new Punishment(uuid, player.getUniqueId().toString(), reason, 0L, Type.WARN, Type.WARN, 1, date.format(d), 1);
				  punish.execute();
			  });
		        
			  Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert"))
			  .forEach(s -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " warned &c" + target.getName() + " for " + reason)));

			  if (target.isOnline()) {
				  target.getPlayer().sendMessage(msg.warnMessage(reason));
			   	  Titles.sendFullTitle(target.getPlayer(), 20, 100, 20, ChatColor.RED + "" + ChatColor.BOLD + "Warning!", ChatColor.YELLOW + "Received warning from " + player.getName());
			  }
		      player.closeInventory();
		   }
		  
		  /**
		   * Kick
		   * Leather Boots
		   */
		  if (event.getSlot() == 36) {
		      Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert"))
		      .forEach(s -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " kicked &c" + target.getName() + " for " + reason)));
		      
		      if (target.isOnline()) {
		    	  target.getPlayer().kickPlayer(msg.kickMessage(reason));
		      } else {
		          DataHandler.kickPlayer(target.getName(), msg.kickMessage(reason));
		      }
		      player.closeInventory();
		   }
		  
		  /**
		   * Perm Ban
		   * Redstone Block
		   */
		   if ((event.getSlot() == 9) && (player.hasPermission("Punish.PermBan"))) { 
			   Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
				   if (p.isPunished(Type.BAN)) {
					   player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer is already banned!"));
					   player.closeInventory();
					   return;
			       } else {
			    	   Punishment punish = new Punishment(uuid, player.getUniqueId().toString(), reason, 0L, Type.BAN, Type.PERM_BAN, 1, date.format(d), 1);
					   punish.execute();
					   
					   Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert")).forEach(s -> { 
					    	s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " has perm-banned &c" + target.getName() + " for " + reason));
					   });
				        
					   Punishment check = new Punishment(uuid, Type.BAN);
					   Bukkit.getScheduler().runTask(Punish.getInstance(), () -> {
					       if (target.isOnline()) {
					  		  	target.getPlayer().kickPlayer(msg.banMessage(reason, check.getID(), msg.permanent(), player.getName()));
					       } else {
					    	   DataHandler.kickPlayer(target.getName(), msg.banMessage(reason, check.getID(), msg.permanent(), player.getName()));
					       }
					       player.closeInventory();
					   });
			       }
			   });
		   }
	    
		  /**
		   * Severity 1 
		   * Chat Offense (Mute)
		   */
		  if (event.getSlot() == 20) { 
			   Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
				 if (p.isPunished(Type.MUTE)) {
			          player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer is already muted!"));
			      	  player.closeInventory();
			      	  return;
				  }
				  
				  long time = DataHandler.getDuration(Type.CHAT, 1, p.getPastOffense(1, Type.CHAT));
				  Punishment punish = new Punishment(uuid, player.getUniqueId().toString(), reason, time, Type.MUTE, Type.CHAT, 1, date.format(d), p.getPastOffense(1, Type.CHAT) + 1);
				  punish.execute();
			        
			      Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert")).forEach(s -> { 
				      int offenses = p.getPastOffense(1, Type.CHAT);
				      
				      if (offenses >= 5)
				    	  offenses = 5;
			    	  s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " punished &c" + target.getName() + " for Chat Offence - Sev 1 (" + offenses + " offences)"));
			      });
			      
			      if (target.isOnline()) {
			    	  Long end = time * 1000L;
					  String format = DataHandler.timeFormat(end.longValue());
					  target.getPlayer().sendMessage(msg.muteMessage(reason, format, player.getName()));
			      } 
			      player.closeInventory();
			 });
		 }
		  
		 /**
		  * Severity 2
		  * Chat Offense (Mute)
		  */
		  if (event.getSlot() == 29) { 
			  Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
				 if (p.isPunished(Type.MUTE)) {
			          player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer is already muted!"));
			      	  player.closeInventory();
			      	  return;
				  }
				  
				  long time = DataHandler.getDuration(Type.CHAT, 2, p.getPastOffense(2, Type.CHAT));
				  Punishment punish = new Punishment(uuid, player.getUniqueId().toString(), reason, time, Type.MUTE, Type.CHAT, 2, date.format(d), p.getPastOffense(2, Type.CHAT) + 1);
				  punish.execute();
			        
			      Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert")).forEach(s -> { 
				      int offenses = p.getPastOffense(2, Type.CHAT);
				      if (offenses >= 5)
				    	  offenses = 5;
			    	  s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " punished &c" + target.getName() + " for Chat Offence - Sev 2 (" + offenses + " offences)"));
			      });
			      
			      if (target.isOnline()) {
			    	  Long end = time * 1000L;
					  String format = DataHandler.timeFormat(end.longValue());
					  target.getPlayer().sendMessage(msg.muteMessage(reason, format, player.getName()));
			      } 
			      player.closeInventory();
			  });
		 }
		  
		 /** 
		  * Severity 3
		  * Chat Offense (Mute)
		  */
		  if (event.getSlot() == 38) { 
			  Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {	  
				 if (p.isPunished(Type.MUTE)) {
			          player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer is already muted!"));
			      	  player.closeInventory();
			      	  return;
				  }
				  
				  Punishment punish = new Punishment(uuid, player.getUniqueId().toString(), reason, 0L, Type.MUTE, Type.CHAT, 3, date.format(d), p.getPastOffense(3, Type.CHAT) + 1);
				  punish.execute();
			        
			      Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert")).forEach(s -> { 
			    	  s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " punished &c" + target.getName() + " for Chat Offence - Sev 3 (1 offences)"));
			      });
			      
			      if (target.isOnline()) {
					  target.getPlayer().sendMessage(msg.muteMessage(reason, msg.permanent(), player.getName()));
			      } 
			      player.closeInventory();
			 });
		 }
		  
		 /**
		  * Severity 1
		  * Gameplay Offense (Ban)
		  */
		  if ((event.getSlot() == 22) && (player.hasPermission("Punish.Ban"))) { 
			  Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
				 if (p.isPunished(Type.BAN)) {
			          player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer is already banned!"));
			      	  player.closeInventory();
			      	  return;
				  }
				  
				  long time = DataHandler.getDuration(Type.GAMEPLAY, 1, p.getPastOffense(1, Type.GAMEPLAY));
				  Punishment punish = new Punishment(uuid, player.getUniqueId().toString(), reason, time, Type.BAN, Type.GAMEPLAY, 1, date.format(d), p.getPastOffense(1, Type.GAMEPLAY) + 1);
				  punish.execute();
			        
			      Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert")).forEach(s -> { 
				      int offenses = p.getPastOffense(1, Type.GAMEPLAY);
				      if (offenses >= 5)
				    	  offenses = 5;
			    	  s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " punished &c" + target.getName() + " for Gameplay Offence - Sev 1 (" + offenses + " offences)"));
			      });
			      
				  Punishment check = new Punishment(uuid, Type.BAN);
		          Long end = check.getExpire() - System.currentTimeMillis();
				  String format = DataHandler.timeFormat(end.longValue());
				  Bukkit.getScheduler().runTask(Punish.getInstance(), () -> {
				      if (target.isOnline()) {		    	 
				  		  target.getPlayer().kickPlayer(msg.banMessage(reason, check.getID(), format, player.getName()));
				      } else {
				          DataHandler.kickPlayer(target.getName(), msg.banMessage(reason, check.getID(), format, player.getName()));
				      }
				      player.closeInventory();
				  });
			  });
		 }
		  
		 /** 
		  * Severity 2
		  * Gameplay Offense (Ban)
		  */
		  if ((event.getSlot() == 31) && (player.hasPermission("Punish.Ban"))) { 
			  Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
				 if (p.isPunished(Type.BAN)) {
			          player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer is already banned!"));
			      	  player.closeInventory();
			      	  return;
				  }
				  
				  long time = DataHandler.getDuration(Type.GAMEPLAY, 2, p.getPastOffense(2, Type.GAMEPLAY));
				  Punishment punish = new Punishment(uuid, player.getUniqueId().toString(), reason, time, Type.BAN, Type.GAMEPLAY, 2, date.format(d), p.getPastOffense(2, Type.GAMEPLAY) + 1);
				  punish.execute();
			        
			      Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert")).forEach(s -> { 
				      int offenses = p.getPastOffense(2, Type.GAMEPLAY);
				      if (offenses >= 5)
				    	  offenses = 5;
			    	  s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " punished &c" + target.getName() + " for Gameplay Offence - Sev 2 (" + offenses + " offences)"));
			      });
			      
				  Punishment check = new Punishment(uuid, Type.BAN);
		          Long end = check.getExpire() - System.currentTimeMillis();
				  String format = DataHandler.timeFormat(end.longValue());
				  Bukkit.getScheduler().runTask(Punish.getInstance(), () -> {
				      if (target.isOnline()) {		    	 
				  		  target.getPlayer().kickPlayer(msg.banMessage(reason, check.getID(), format, player.getName()));
				      } else {
				          DataHandler.kickPlayer(target.getName(), msg.banMessage(reason, check.getID(), format, player.getName()));
				      }
				      player.closeInventory();
			  	});      
			});
		 }
		  
		 /**
		  * Severity 3 
		  * Gameplay Offense (Ban)
		  */
		  if ((event.getSlot() == 40) && (player.hasPermission("Punish.Ban"))) { 
			  Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
				 if (p.isPunished(Type.BAN)) {
			          player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer is already banned!"));
			      	  player.closeInventory();
			      	  return;
				  }
				  
				  long time = DataHandler.getDuration(Type.GAMEPLAY, 3, p.getPastOffense(3, Type.GAMEPLAY));
				  Punishment punish = new Punishment(uuid, player.getUniqueId().toString(), reason, time, Type.BAN, Type.GAMEPLAY, 3, date.format(d), p.getPastOffense(3, Type.GAMEPLAY) + 1);
				  punish.execute();
			        
			      Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert")).forEach(s -> { 
				      int offenses = p.getPastOffense(3, Type.GAMEPLAY);
				      if (offenses >= 5)
				    	  offenses = 5;
			    	  s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " punished &c" + target.getName() + " for Gameplay Offence - Sev 3 (" + offenses + " offences)"));
			      });
			      
				  Punishment check = new Punishment(uuid, Type.BAN);
			      String format;
			      if (time == Long.valueOf(0)) {
			    	  format = msg.permanent();
			      } else {
			    	  Long end = check.getExpire() - System.currentTimeMillis();
			          format = DataHandler.timeFormat(end.longValue());
			      }
				  Bukkit.getScheduler().runTask(Punish.getInstance(), () -> {
				      if (target.isOnline()) {		    	 
				  		  target.getPlayer().kickPlayer(msg.banMessage(reason, check.getID(), format, player.getName()));
				      } else {
				          DataHandler.kickPlayer(target.getName(), msg.banMessage(reason, check.getID(), format, player.getName()));
				      }
				      player.closeInventory();
				  });
			  });
		 }
		  
		 
		 /** 
		  * Severity 1
		  * Client Mod (Ban)
		  */
		  if ((event.getSlot() == 24) && (player.hasPermission("Punish.Ban"))) { 
			  Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
				 if (p.isPunished(Type.BAN)) {
			          player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer is already banned!"));
			      	  player.closeInventory();
			      	  return;
				  }
				  
				  long time = DataHandler.getDuration(Type.HACKING, 1, p.getPastOffense(1, Type.HACKING));
				  Punishment punish = new Punishment(uuid, player.getUniqueId().toString(), reason, time, Type.BAN, Type.HACKING, 1, date.format(d), p.getPastOffense(1, Type.HACKING) + 1);
				  punish.execute();
			        
			      Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert")).forEach(s -> { 
				      int offenses = p.getPastOffense(1, Type.HACKING);
				      if (offenses >= 5)
				    	  offenses = 5;
			    	  s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " punished &c" + target.getName() + " for Client Modifications - Sev 1 (" + offenses + " offences)"));
			      });
			      
				  Punishment check = new Punishment(uuid, Type.BAN);
			      Long end = check.getExpire() - System.currentTimeMillis();
				  String format = DataHandler.timeFormat(end.longValue());
				  Bukkit.getScheduler().runTask(Punish.getInstance(), () -> {
				      if (target.isOnline()) {		    	 
				  		  target.getPlayer().kickPlayer(msg.banMessage(reason, check.getID(), format, player.getName()));
				      } else {
				          DataHandler.kickPlayer(target.getName(), msg.banMessage(reason, check.getID(), format, player.getName()));
				      }
				      player.closeInventory();
				  });			      
			  });
		 }
		  
		 
		 /**
		  * Severity 2
		  * Client Mod (Ban)
		  */
		  if ((event.getSlot() == 33) && (player.hasPermission("Punish.Ban"))) { 
			  Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
				 if (p.isPunished(Type.BAN)) {
			          player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer is already banned!"));
			      	  player.closeInventory();
			      	  return;
				  }
				  
				  long time = DataHandler.getDuration(Type.HACKING, 2, p.getPastOffense(2, Type.HACKING));
				  Punishment punish = new Punishment(uuid, player.getUniqueId().toString(), reason, time, Type.BAN, Type.HACKING, 2, date.format(d), p.getPastOffense(2, Type.HACKING) + 1);
				  punish.execute();
			        
			      Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert")).forEach(s -> { 
				      int offenses = p.getPastOffense(2, Type.HACKING);
				      if (offenses >= 5)
				    	  offenses = 5;
			    	  s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " punished &c" + target.getName() + " for Client Modifications - Sev 2 (" + offenses + " offences)"));
			      });
			      
				  Punishment check = new Punishment(uuid, Type.BAN);
			      String format;
			      if (time == Long.valueOf(0)) {
			    	  format = msg.permanent();
			      } else {
			    	  Long end = check.getExpire() - System.currentTimeMillis();
			          format = DataHandler.timeFormat(end.longValue());
			      }
				  Bukkit.getScheduler().runTask(Punish.getInstance(), () -> {
				      if (target.isOnline()) {		    	 
				  		  target.getPlayer().kickPlayer(msg.banMessage(reason, check.getID(), format, player.getName()));
				      } else {
				          DataHandler.kickPlayer(target.getName(), msg.banMessage(reason, check.getID(), format, player.getName()));
				      }
				      player.closeInventory();
				  });
			  });
		 }
		  
		 
		 /**
		  * Severity 3
		  * Client Mod (Ban)
		  */
		  if ((event.getSlot() == 42) && (player.hasPermission("Punish.Ban"))) { 
			  Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
				 if (p.isPunished(Type.BAN)) {
			          player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer is already banned!"));
			      	  player.closeInventory();
			      	  return;
				  }
				  
				  long time = DataHandler.getDuration(Type.HACKING, 3, p.getPastOffense(3, Type.HACKING));
				  Punishment punish = new Punishment(uuid, player.getUniqueId().toString(), reason, time, Type.BAN, Type.HACKING, 3, date.format(d), p.getPastOffense(3, Type.HACKING) + 1);
				  punish.execute();
			        
			      Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert")).forEach(s -> { 
				      int offenses = p.getPastOffense(3, Type.HACKING);
				      if (offenses >= 3)
				    	  offenses = 3;
			    	  s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " punished &c" + target.getName() + " for Client Modifications - Sev 3 (" + offenses + " offences)"));
			      });
			      
				  Punishment check = new Punishment(uuid, Type.BAN);
			      String format;
			      if (time == Long.valueOf(0)) {
			    	  format = msg.permanent();
			      } else {
			    	  Long end = check.getExpire() - System.currentTimeMillis();
			          format = DataHandler.timeFormat(end.longValue());
			      }
				  Bukkit.getScheduler().runTask(Punish.getInstance(), () -> {
				      if (target.isOnline()) {		    	 
				  		  target.getPlayer().kickPlayer(msg.banMessage(reason, check.getID(), format, player.getName()));
				      } else {
				          DataHandler.kickPlayer(target.getName(), msg.banMessage(reason, check.getID(), format, player.getName()));
				      }
				      player.closeInventory();
				  });
			  });
		 }
		  
	}
}
