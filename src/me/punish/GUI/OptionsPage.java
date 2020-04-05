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

public class OptionsPage {
	
	 public static Inventory openMorePage(Player p, String target, String reason)  {
		UUID uid = UUID.fromString(target);
		OfflinePlayer t = Bukkit.getOfflinePlayer(uid);
	    
	    Inventory inv = Bukkit.createInventory(p, 27, "More Options");
	    
	    ItemStack history = new ItemStack(Material.BED);
	    ItemMeta historyim = history.getItemMeta();
	    historyim.setDisplayName(ChatColor.RED + "Go Back");
	    history.setItemMeta(historyim);
	    inv.setItem(18, history);
	    
	    ItemStack title = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
	    SkullMeta titleMeta = (SkullMeta)title.getItemMeta();
	    titleMeta.setOwner(t.getName());
	    titleMeta.setDisplayName(ChatColor.WHITE + t.getName());
	    ArrayList<String> titleLore = new ArrayList<String>();
	    titleLore.add(ChatColor.GRAY + reason);
	    titleMeta.setLore(titleLore);
	    title.setItemMeta(titleMeta);
	    inv.setItem(4, title);

	    ItemStack tpaban = new ItemStack(Material.ANVIL);
	    ItemMeta tpabanim = tpaban.getItemMeta();
	    tpabanim.setDisplayName(ChatColor.WHITE + "2 Day TPA Tempban");
	    tpaban.setItemMeta(tpabanim);
	    
	    ItemStack sellban = new ItemStack(Material.ANVIL);
	    ItemMeta sellbanim = sellban.getItemMeta();
	    sellbanim.setDisplayName(ChatColor.WHITE + "Permanent Revoke Sell");
	    sellban.setItemMeta(sellbanim);
	    
	    ItemStack clear = new ItemStack(Material.ANVIL);
	    ItemMeta clearim = clear.getItemMeta();
	    clearim.setDisplayName(ChatColor.WHITE + "Clear All Punishments");
	    clear.setItemMeta(clearim);
	    
	    ItemStack removetpa = new ItemStack(Material.INK_SACK, 1, (short)1);
	    ItemMeta removetpaim = removetpa.getItemMeta();
	    removetpaim.setDisplayName(ChatColor.WHITE + "Remove TPA Tempban");
	    removetpa.setItemMeta(removetpaim);
	    
	    ItemStack removesell = new ItemStack(Material.INK_SACK, 1, (short)1);
	    ItemMeta removesellim = removesell.getItemMeta();
	    removesellim.setDisplayName(ChatColor.WHITE + "Remove Revoke Sell");
	    removesell.setItemMeta(removesellim);
	    
	    ItemStack unmute = new ItemStack(Material.INK_SACK, 1, (short)1);
	    ItemMeta unmuteim = unmute.getItemMeta();
	    unmuteim.setDisplayName(ChatColor.WHITE + "Unmute");
	    unmute.setItemMeta(unmuteim);
	    
	    ItemStack unban = new ItemStack(Material.INK_SACK, 1, (short)1);
	    ItemMeta unbanim = unban.getItemMeta();
	    unbanim.setDisplayName(ChatColor.WHITE + "Unban");
	    unban.setItemMeta(unbanim);
	    
	    inv.setItem(4, title);
	    inv.setItem(12, tpaban);
	    inv.setItem(13, clear);
	    inv.setItem(14, sellban);
	    
	    inv.setItem(20, unmute);
	    inv.setItem(21, unban);
	    
	    inv.setItem(23, removetpa);
	    inv.setItem(24, removesell);
	    
		return inv;
	    }

}