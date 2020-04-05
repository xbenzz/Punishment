package me.punish.Manager;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.punish.Punish;
import me.punish.GUI.PunishPage;
import me.punish.Objects.Punishment;

public class HistoryGUIEvents implements Listener {
	
	  @EventHandler
	  public void clickHistoryGUI(InventoryClickEvent event) {
		  if (!event.getInventory().getName().contains("All punishments")) {
			  return;
		  }
		  event.setCancelled(true);
		  Player player = (Player)event.getWhoClicked();
		  
		  String name = ChatColor.stripColor(event.getInventory().getItem(4).getItemMeta().getDisplayName());
		  UUID uuid = Bukkit.getOfflinePlayer(name).getUniqueId();
		  String reason = ChatColor.stripColor(event.getInventory().getItem(4).getItemMeta().getLore().get(0));
		  
		  /** 
		   * Go Back
		   */
		  if (event.getSlot() == 45) { 
			   Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
				   Punishment p = new Punishment(uuid);
				   List<Punishment> puns = p.getHistory();
				   Bukkit.getScheduler().runTask(Punish.getInstance(), () -> {
					   player.openInventory(PunishPage.openPunish(player, uuid.toString(), reason, puns));
				   });
			   });
		  }
	 }
	  
	 @EventHandler
	 public void clickIPBanHistory(InventoryClickEvent event) {
		if (!event.getInventory().getName().contains("IP History - ")) {
			  return;
		}
		event.setCancelled(true);
	}
}
