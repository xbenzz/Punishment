package me.punish.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.punish.Objects.IPBan;

public class IPBanPage {

	public static Inventory openHistory(Player p, String ip, List<IPBan> ipb) {
		Inventory inv = Bukkit.createInventory(p, 54, "IP History - " + ip);
		   
		int slot = 0;
		int amount = 1;
		for (IPBan pun : ipb) {
			if (slot > 44) {
				break;
			}
			  
			ItemStack userhistory = new ItemStack(Material.REDSTONE_BLOCK, amount);
			ItemMeta userHistoryim = userhistory.getItemMeta();
			userHistoryim.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "IP-BAN");
			ArrayList<String> userhistoryar = new ArrayList<String>();
			userhistoryar.add(ChatColor.GRAY + pun.getReason());
			userhistoryar.add(" ");
			userhistoryar.add(ChatColor.WHITE + "Date: " + ChatColor.GRAY + pun.getDate());
			
			String staff;
			if (pun.getPunisher().equalsIgnoreCase("CONSOLE")) {
				staff = "CONSOLE";
			} else {
				OfflinePlayer of = Bukkit.getOfflinePlayer(UUID.fromString(pun.getPunisher()));
				staff = of.getName();
			}
			
			userhistoryar.add(ChatColor.WHITE + "Staff: " + ChatColor.GRAY + staff);
			userhistoryar.add(" ");
			userhistoryar.add(ChatColor.WHITE + "ID: " + ChatColor.RED + pun.getID());
			if (!pun.isActive()) {
				userhistoryar.add(" ");
				userhistoryar.add(ChatColor.WHITE + "Removed: " + ChatColor.GRAY + pun.getRemoveDate());
				  
				String removedby;
				if (pun.getRemovedBy().equalsIgnoreCase("CONSOLE")) {
					removedby = "CONSOLE";
				} else {
					OfflinePlayer of = Bukkit.getOfflinePlayer(UUID.fromString(pun.getRemovedBy())); 
					removedby = of.getName();
				}
					
				userhistoryar.add(ChatColor.WHITE + "Removed By: " + ChatColor.GRAY + removedby);
			}
			userHistoryim.setLore(userhistoryar);
			userhistory.setItemMeta(userHistoryim);
		    
			inv.setItem(slot, userhistory);
			slot++;
			amount++;
		} 
		return inv;
	 }
}
