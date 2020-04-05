package me.punish.GUI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.punish.Objects.Punishment;
import me.punish.Utils.Type;

public class HistoryPage {
	
	public static Inventory openHistory(Player p, String target, String reason, List<Punishment> puns) {
		UUID uid = UUID.fromString(target);
	    OfflinePlayer t = Bukkit.getOfflinePlayer(uid);
	
	    Inventory inv = Bukkit.createInventory(p, 54, "All punishments");
	    
	    ItemStack title = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
	    SkullMeta titleMeta = (SkullMeta)title.getItemMeta();
	    titleMeta.setOwner(t.getName());
	    titleMeta.setDisplayName(ChatColor.WHITE + t.getName());
	    ArrayList<String> titleLore = new ArrayList<String>();
	    titleLore.add(ChatColor.GRAY + reason);
	    titleMeta.setLore(titleLore);
	    title.setItemMeta(titleMeta);
	    inv.setItem(4, title);
	  
	    ItemStack history = new ItemStack(Material.BED);
	    ItemMeta historyim = history.getItemMeta();
	    historyim.setDisplayName(ChatColor.RED + "Go Back");
	    history.setItemMeta(historyim);
	    inv.setItem(45, history);
	   
	    int slot = 9;
	    int amount = 1;
	    for (Punishment pun : puns) {
	    	if (slot > 44) {
	    		break;
	    	}
	    
	    	ItemStack userhistory = new ItemStack(Material.EMPTY_MAP, amount);
	    	ItemMeta userHistoryim = userhistory.getItemMeta();
	    	userHistoryim.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + pun.getCategory().value());
	    	ArrayList<String> userhistoryar = new ArrayList<String>();
		    userhistoryar.add(ChatColor.GRAY + pun.getReason());
		    userhistoryar.add(" ");
		    if (pun.getType() == Type.BAN || pun.getType() == Type.MUTE)
		    	userhistoryar.add(ChatColor.WHITE + "Severity: " + ChatColor.WHITE + "" + ChatColor.BOLD + pun.getSeverity() + ChatColor.WHITE + " (" + ChatColor.RED + "" + ChatColor.BOLD + pun.getOffence() + ChatColor.WHITE +")");
		    userhistoryar.add(ChatColor.WHITE + "Date: " + ChatColor.GRAY + pun.getDate());
		    userhistoryar.add(ChatColor.WHITE + "Staff: " + ChatColor.GRAY + Bukkit.getOfflinePlayer(UUID.fromString(pun.getPunisher())).getName());
		    userhistoryar.add(" ");
		    userhistoryar.add(ChatColor.WHITE + "ID: " + ChatColor.RED + pun.getID());
		    userHistoryim.setLore(userhistoryar);
		    userhistory.setItemMeta(userHistoryim);
	    
		    inv.setItem(slot, userhistory);
		    slot++;
		    amount++;
	    }
	    return inv;
	}
	
}