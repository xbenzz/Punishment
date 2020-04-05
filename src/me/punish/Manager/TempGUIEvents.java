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
import me.punish.GUI.PunishPage;
import me.punish.GUI.TempPage;
import me.punish.Objects.Punishment;
import me.punish.Utils.Messages;
import me.punish.Utils.Type;

public class TempGUIEvents implements Listener {
	
	  @EventHandler
	  public void clickTempGUI(InventoryClickEvent event) {
		  if (!event.getInventory().getName().contains("Punishment Time")) {
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
		   * Go back
		   */
	      if (event.getSlot() == 36) {
			   Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
				   List<Punishment> puns = p.getHistory();
				   Bukkit.getScheduler().runTask(Punish.getInstance(), () -> {
					   player.openInventory(PunishPage.openPunish(player, uuid.toString(), reason, puns));
				   });
			   });
	    	  TempPage.weeks = 0;
	    	  TempPage.days = 0;
	          TempPage.hour = 1;
	          TempPage.minutes = 0;
	      }
	      
	      
	      /**
	       * Week (Increase)
	       */
	      if (event.getSlot() == 10) {
	    	  TempPage.weeks = new Integer(TempPage.weeks + 1);
	    	  player.openInventory(TempPage.openTemp(player, uuid.toString(), reason));
	      }
	      
	      
	      /**
	       * Day (Increase)
	       */
	      if (event.getSlot() == 12){
	    	  TempPage.days = new Integer(TempPage.days + 1);
	    	  player.openInventory(TempPage.openTemp(player, uuid.toString(), reason));
	      }

	      
	      /**
	       * Hour (Increase)
	       */
	      if (event.getSlot() == 14) {
	    	  TempPage.hour = new Integer(TempPage.hour + 1);
	    	  player.openInventory(TempPage.openTemp(player, uuid.toString(), reason));
	      }
	      
	      
	      /**
	       * Minutes (Increase)
	       */
	      if (event.getSlot() == 16) {
	    	  TempPage.minutes = new Integer(TempPage.minutes + 10);
	    	  player.openInventory(TempPage.openTemp(player, uuid.toString(), reason));
	      }
	      
	      
	      /**
	       * Weeks (Decrease)
	       */
	      if (event.getSlot() == 28) {
	          int weeks = TempPage.weeks - 1;
	          if (weeks < 0) {
	        	 weeks = 0;
	          }
	          TempPage.weeks = weeks;
	    	  player.openInventory(TempPage.openTemp(player, uuid.toString(), reason));
	      }
	      
	      
	      /**
	       * Days (Decrease)
	       */
	      if (event.getSlot() == 30) {
	          int days = TempPage.days - 1;
	          if (days < 0) {
	        	 days = 0;
	          }
	          TempPage.days = days;
	    	  player.openInventory(TempPage.openTemp(player, uuid.toString(), reason));
	      }
	      
	      
	      /**
	       * Hours (Decrease)
	       */
	      if (event.getSlot() == 32) {
	          int hour = TempPage.hour - 1;
	          if (hour < 0) {
	        	 hour = 0;
	          }
	          TempPage.hour = hour;
	    	  player.openInventory(TempPage.openTemp(player, uuid.toString(), reason));
	      }
	      
	      
	      /**
	       * Minutes (Decrease)
	       */
	      if (event.getSlot() == 34) {
	          int minutes = TempPage.minutes - 10;
	          if (minutes < 0) {
	        	 minutes = 0;
	          }
	          TempPage.minutes = minutes;
	    	  player.openInventory(TempPage.openTemp(player, uuid.toString(), reason));
	      }
	      
	      
	      /**
	       * TempBan
	       * Issue a temp-ban
	       */
	      if (event.getSlot() == 44) {
			  Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {	  
				  if (p.isPunished(Type.BAN)) {
				      player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer is already banned!"));
				      player.closeInventory();
				      return;
				   }
		    
		          int weeks = TempPage.weeks * 604800;
		          int days = TempPage.days * 86400;
		          int hours = TempPage.hour * 3600;
		          int mins = TempPage.minutes * 60;
		          int total = weeks + days + hours + mins;
		          if (total == 0) {
		        	  player.sendMessage(ChatColor.RED + "You must select a proper duration!");
		        	  player.closeInventory();
		        	  return;
		          }
		          
		          TempPage.inTemp.remove(player.getUniqueId());
		          Punishment punish = new Punishment(uuid, player.getUniqueId().toString(), reason, total, Type.BAN, Type.TEMP_BAN, 1, date.format(d), 1);
				  punish.execute();
		          
		          Bukkit.getOnlinePlayers().stream().filter(s -> s.hasPermission("Punish.Alert")).forEach(s -> { 
		        	  s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " has temp-banned &c" + target.getName() + " for " + reason));
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
			          TempPage.weeks = 0;
			          TempPage.days = 0;
			          TempPage.hour = 1;
			          TempPage.minutes = 0;
				  });
			  });
	      }
		  
	      
	}
}
