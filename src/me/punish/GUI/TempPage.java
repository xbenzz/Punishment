package me.punish.GUI;

import java.util.ArrayList;
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

public class TempPage {
	
	public static int weeks = 0;
	public static int days = 0;
	public static int hour = 1;
	public static int minutes = 0;
	public static ArrayList<UUID> inTemp = new ArrayList<UUID>();
	
	public static Inventory openTemp(Player player, String target, String reason) {
		UUID uid = UUID.fromString(target);
		OfflinePlayer t = Bukkit.getOfflinePlayer(uid);
    
		Inventory inv = Bukkit.createInventory(player, 45, "Punishment Time");
    
		  ItemStack title = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
		  SkullMeta titleMeta = (SkullMeta)title.getItemMeta();
		  titleMeta.setOwner(t.getName());
		  titleMeta.setDisplayName(ChatColor.WHITE + t.getName());
		  ArrayList<String> titleLore = new ArrayList<String>();
		  titleLore.add(ChatColor.GRAY + reason);
		  titleMeta.setLore(titleLore);
		  title.setItemMeta(titleMeta);
	    	
		  ItemStack history = new ItemStack(Material.ARROW);
		  ItemMeta historyim = history.getItemMeta();
		  historyim.setDisplayName(ChatColor.RED + "Go Back");
		  history.setItemMeta(historyim);

	    //---------------------------------------------------------
	    
	    ItemStack weekup = new ItemStack(Material.INK_SACK, 1, (short)10);
	    ItemMeta weekupim = weekup.getItemMeta();
	    weekupim.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "UP");
	    ArrayList<String> weekupar = new ArrayList<String>();
	    weekupar.add(ChatColor.RED + "+ 1");
	    weekupim.setLore(weekupar);
	    weekup.setItemMeta(weekupim);
	    
	    ItemStack week = new ItemStack(Material.STAINED_GLASS, 1, (short)14);
	    ItemMeta weekim = week.getItemMeta();
	    weekim.setDisplayName(ChatColor.WHITE + "Weeks: " + weeks);
	    week.setItemMeta(weekim);
	    
	    ItemStack weekdown = new ItemStack(Material.INK_SACK, 1, (short)9);
	    ItemMeta weekdownim = weekdown.getItemMeta();
	    weekdownim.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "DOWN");
	    ArrayList<String> weekdownar = new ArrayList<String>();
	    weekdownar.add(ChatColor.RED + "- 1");
	    weekdownim.setLore(weekdownar);
	    weekdown.setItemMeta(weekdownim);
	    
	    //---------------------------------------------------------
	    
	    ItemStack dayup = new ItemStack(Material.INK_SACK, 1, (short)10);
	    ItemMeta dayupim = dayup.getItemMeta();
	    dayupim.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "UP");
	    ArrayList<String> dayupar = new ArrayList<String>();
	    dayupar.add(ChatColor.AQUA + "+ 1");
	    dayupim.setLore(dayupar);
	    dayup.setItemMeta(dayupim);
	    
	    ItemStack day = new ItemStack(Material.STAINED_GLASS, 1, (short)3);
	    ItemMeta dayim = day.getItemMeta();
	    dayim.setDisplayName(ChatColor.WHITE + "Days: " + days);
	    day.setItemMeta(dayim);
	    
	    ItemStack daydown = new ItemStack(Material.INK_SACK, 1, (short)9);
	    ItemMeta daydownim = daydown.getItemMeta();
	    daydownim.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "DOWN");
	    ArrayList<String> daydownar = new ArrayList<String>();
	    daydownar.add(ChatColor.AQUA + "- 1");
	    daydownim.setLore(daydownar);
	    daydown.setItemMeta(daydownim);
	    
	    //---------------------------------------------------------
	    
	    ItemStack hoursup = new ItemStack(Material.INK_SACK, 1, (short)10);
	    ItemMeta hoursupim = hoursup.getItemMeta();
	    hoursupim.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "UP");
	    ArrayList<String> hoursupar = new ArrayList<String>();
	    hoursupar.add(ChatColor.GREEN + "+ 1");
	    hoursupim.setLore(hoursupar);
	    hoursup.setItemMeta(hoursupim);
	    
	    ItemStack hours = new ItemStack(Material.STAINED_GLASS, 1, (short)5);
	    ItemMeta hoursim = hours.getItemMeta();
	    hoursim.setDisplayName(ChatColor.WHITE + "Hours: " + hour);
	    hours.setItemMeta(hoursim);
	    
	    ItemStack hoursdown = new ItemStack(Material.INK_SACK, 1, (short)9);
	    ItemMeta hoursdownim = hoursdown.getItemMeta();
	    hoursdownim.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "DOWN");
	    ArrayList<String> hoursdownar = new ArrayList<String>();
	    hoursdownar.add(ChatColor.GREEN + "- 1");
	    hoursdownim.setLore(hoursdownar);
	    hoursdown.setItemMeta(hoursdownim);
	    
	    //---------------------------------------------------------
	    
	    ItemStack minup = new ItemStack(Material.INK_SACK, 1, (short)10);
	    ItemMeta minupim = minup.getItemMeta();
	    minupim.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "UP");
	    ArrayList<String> minupar = new ArrayList<String>();
	    minupar.add(ChatColor.YELLOW + "+ 10");
	    minupim.setLore(minupar);
	    minup.setItemMeta(minupim);
	    
	    ItemStack min = new ItemStack(Material.STAINED_GLASS, 1, (short)4);
	    ItemMeta minim = min.getItemMeta();
	    minim.setDisplayName(ChatColor.WHITE + "Minutes: " + minutes);
	    min.setItemMeta(minim);
	    
	    ItemStack mindown = new ItemStack(Material.INK_SACK, 1, (short)9);
	    ItemMeta mindownim = mindown.getItemMeta();
	    mindownim.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "DOWN");
	    ArrayList<String> mindownar = new ArrayList<String>();
	    mindownar.add(ChatColor.YELLOW + "- 10");
	    mindownim.setLore(mindownar);
	    mindown.setItemMeta(mindownim);
	    
	    //---------------------------------------------------------
	    
	    ItemStack diamond = new ItemStack(Material.DIAMOND);
	    ItemMeta diamondim = diamond.getItemMeta();
	    diamondim.setDisplayName(ChatColor.RED + "Temp Ban");
	    ArrayList<String> diamondar = new ArrayList<String>();
	    diamondar.add(ChatColor.WHITE + "Weeks: " + weeks);
	    diamondar.add(ChatColor.WHITE + "Days: " + days);
	    diamondar.add(ChatColor.WHITE + "Hours: " + hour);
	    diamondar.add(ChatColor.WHITE + "Minutes: " + minutes);
	    diamondim.setLore(diamondar);
	    diamond.setItemMeta(diamondim);
	    
	    inv.setItem(4, title);
	    inv.setItem(36, history);
	    
	    inv.setItem(10, weekup);
	    inv.setItem(19, week);
	    inv.setItem(28, weekdown);
	    
	    inv.setItem(12, dayup);
	    inv.setItem(21, day);
	    inv.setItem(30, daydown);
	    
	    inv.setItem(14, hoursup);
	    inv.setItem(23, hours);
	    inv.setItem(32, hoursdown);
	    
	    inv.setItem(16, minup);
	    inv.setItem(25, min);
	    inv.setItem(34, mindown);
	    
	    inv.setItem(44, diamond);
	    
		return inv;
	}
}
  