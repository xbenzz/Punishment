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
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.punish.Punish;
import me.punish.Database.DataHandler;
import me.punish.GUI.TempPage;
import me.punish.Objects.IPBan;
import me.punish.Objects.Punishment;
import me.punish.Objects.User;
import me.punish.Utils.Messages;
import me.punish.Utils.Type;

public class PlayerEvents implements Listener {
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player player = (Player) e.getPlayer();
		if (e.getInventory().getName().contains("Punishment Time")) {
			if (TempPage.inTemp.contains(player.getUniqueId())) {
				TempPage.inTemp.remove(player.getUniqueId());
		        TempPage.weeks = 0;
		        TempPage.days = 0;
		        TempPage.hour = 1;
		        TempPage.minutes = 0;
			}
		}
	}

	@EventHandler(priority=EventPriority.LOW)
	public void onJoin(AsyncPlayerPreLoginEvent e) {
		Date d = new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (e.getLoginResult() == Result.KICK_BANNED || e.getLoginResult() == Result.KICK_FULL) {
			return;
		}
		User user = new User(e.getUniqueId(), e.getAddress().getHostAddress(), date.format(d));
		if (!user.exists()) {
			user.create();
			user.load();
		} else {
			user.load();
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		Player p = event.getPlayer();
        Punish.getCache().savePunishments(p.getUniqueId());
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent event) {
		event.setLeaveMessage(null);
		Player p = event.getPlayer();
		Punish.getCache().savePunishments(p.getUniqueId());
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		Punishment punish = Punish.getCache().getMute(player.getUniqueId());
		Messages msg = new Messages();
		if (punish.isActive()) {
			String staffmember;
			if (!punish.getPunisher().equalsIgnoreCase("CONSOLE")) {
				OfflinePlayer staff = Bukkit.getOfflinePlayer(UUID.fromString(punish.getPunisher()));
				staffmember = staff.getName();
			} else {
				staffmember = "CONSOLE";
			}
			if (punish.isPermanent()) {
				event.setCancelled(true);
				player.sendMessage(msg.muteMessage(punish.getReason(), msg.permanent(), staffmember));
				return;
			} else {
				if (Long.valueOf(punish.getExpire()) - System.currentTimeMillis() <= 0L) {
					punish.remove();
					event.setCancelled(false);
					return;
				}
				Long end = punish.getExpire() - System.currentTimeMillis();
				String format = DataHandler.timeFormat(end.longValue());
				event.setCancelled(true);
				player.sendMessage(msg.muteMessage(punish.getReason(), format, staffmember));
			}
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
	    String message = event.getMessage().toLowerCase();
	    List<String> cmds = Punish.getInstance().getConfig().getStringList("Blocked-Commands");
		Punishment punish = Punish.getCache().getMute(player.getUniqueId());
		Messages msg = new Messages();
	    for (String blacklist : cmds) {
	    	if ((message.startsWith("/" + blacklist.toLowerCase() + " ")) || (message.equals("/" + blacklist.toLowerCase()))) {
	    		if (punish.isActive()) {
	    			String staffmember;
	    			if (!punish.getPunisher().equalsIgnoreCase("CONSOLE")) {
	    				OfflinePlayer staff = Bukkit.getOfflinePlayer(UUID.fromString(punish.getPunisher()));
	    				staffmember = staff.getName();
	    			} else {
	    				staffmember = "CONSOLE";
	    			}
	    			if (punish.isPermanent()) {
	    				event.setCancelled(true);
	    				player.sendMessage(msg.muteMessage(punish.getReason(), msg.permanent(), staffmember));
	    				return;
	    			} else {
	    				if (Long.valueOf(punish.getExpire()) - System.currentTimeMillis() <= 0L) {
	    					punish.remove();
	    					event.setCancelled(false);
	    					return;
	    				}
	    				Long end = punish.getExpire() - System.currentTimeMillis();
	    				String format = DataHandler.timeFormat(end.longValue());
	    				event.setCancelled(true);
	    				player.sendMessage(msg.muteMessage(punish.getReason(), format, staffmember));
	    				return;
	    			}
	    		}
			} 
	    } 
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void TpaBanEvent(PlayerCommandPreprocessEvent event) {
	    Player player = event.getPlayer();
	    Punishment punish = Punish.getCache().getTpa(player.getUniqueId());
		if ((event.getMessage().startsWith("/tpa")) || (event.getMessage().startsWith("/tpahere")) || (event.getMessage().startsWith("/etpa")) || (event.getMessage().startsWith("/etpahere"))) {
    		if (punish.isActive()) {
    				if (Long.valueOf(punish.getExpire()) - System.currentTimeMillis() <= 0L) {
    					punish.remove();
    					event.setCancelled(false);
    					return;
    				}
    				
    				Long end = punish.getExpire() - System.currentTimeMillis();
    				String format = DataHandler.timeFormat(end.longValue());
    				event.setCancelled(true);
    				player.sendMessage(" ");
    				player.sendMessage(ChatColor.RED + "You have been removed access to this command.");
    				player.sendMessage(ChatColor.RED + "You shall regain access in: " + ChatColor.YELLOW + format);
    				player.sendMessage(" ");
    				return;
    		}
		} 
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void SellBanEvent(PlayerCommandPreprocessEvent event) {
	    Player player = event.getPlayer();
	    Punishment punish = Punish.getCache().getSell(player.getUniqueId());
		if ((event.getMessage().startsWith("/sell")) || (event.getMessage().startsWith("/esell")) || (event.getMessage().startsWith("/sellall")) || (event.getMessage().startsWith("/esellall"))) {
    		if (punish.isActive()) {
    			if (punish.isPermanent()) {
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED + "You have been removed access to this command permanently!");
					return;
				} 
    		}
		} 
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
		UUID player = event.getUniqueId();
		Punishment punish = new Punishment(player, Type.BAN);
		Messages msg = new Messages();
		
		if (punish.isActive() && punish.getID() != 0) {
			String staffmember;
			if (!punish.getPunisher().equalsIgnoreCase("CONSOLE")) {
				OfflinePlayer staff = Bukkit.getOfflinePlayer(UUID.fromString(punish.getPunisher()));
				staffmember = staff.getName();
			} else {
				staffmember = "CONSOLE";
			}
			if (punish.isPermanent()) {
				event.disallow(Result.KICK_BANNED, msg.banMessage(punish.getReason(), punish.getID(), msg.permanent(), staffmember));
			} else {
				if (Long.valueOf(punish.getExpire()) - System.currentTimeMillis() <= 0L) {
					punish.remove();
					event.allow();
					return;
				}
				Long end = punish.getExpire() - System.currentTimeMillis();
				String format = DataHandler.timeFormat(end.longValue());
				event.disallow(Result.KICK_BANNED, msg.banMessage(punish.getReason(), punish.getID(), format, staffmember));
				return;
			}
		}
	} 
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onIPBanLogin(AsyncPlayerPreLoginEvent event) {
		String ip = event.getAddress().getHostAddress();
		IPBan punish = new IPBan(ip);
		Messages msg = new Messages();
		if (punish.isActive() && punish.getID() != 0) {
			String staffmember;
			if (!punish.getPunisher().equalsIgnoreCase("CONSOLE")) {
				OfflinePlayer staff = Bukkit.getOfflinePlayer(UUID.fromString(punish.getPunisher()));
				staffmember = staff.getName();
			} else {
				staffmember = "CONSOLE";
			}
			event.disallow(Result.KICK_BANNED, msg.ipbanMessage(punish.getReason(), staffmember));
		}
	}
	
}
	
