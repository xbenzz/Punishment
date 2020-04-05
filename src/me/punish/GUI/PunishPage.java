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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.punish.Objects.Punishment;
import me.punish.Utils.Type;

public class PunishPage {
	
	public static Inventory openPunish(Player player, String target, String reason, List<Punishment> puns) {
		UUID uid = UUID.fromString(target);
	    OfflinePlayer t = Bukkit.getOfflinePlayer(uid);
	    
	    Inventory inv = Bukkit.createInventory(player, 54, "Punishment Menu");
	    Punishment punish = new Punishment(uid);
	    
	    int warns = punish.getPastOffense(1, Type.WARN, puns);
	    
	    int chat1 = punish.getPastOffense(1, Type.CHAT, puns);
	    int chat2 = punish.getPastOffense(2, Type.CHAT, puns);
	    int chat3 = punish.getPastOffense(3, Type.CHAT, puns);
	    
	    int game1 = punish.getPastOffense(1, Type.GAMEPLAY, puns);
	    int game2 = punish.getPastOffense(2, Type.GAMEPLAY, puns);
	    int game3 = punish.getPastOffense(3, Type.GAMEPLAY, puns);
	    
	    int hack1 = punish.getPastOffense(1, Type.HACKING, puns);
	    int hack2 = punish.getPastOffense(2, Type.HACKING, puns);
	    int hack3 = punish.getPastOffense(3, Type.HACKING, puns);
	    
	    int totalChat = chat1 + chat2 + chat3;
	    int totalGame = game1 + game2 + game3;
	    int totalHack = hack1 + hack2 + hack3;
	    
	    ItemStack title = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
	    SkullMeta titleMeta = (SkullMeta)title.getItemMeta();
	    titleMeta.setOwner(t.getName());
	    titleMeta.setDisplayName(ChatColor.WHITE + t.getName());
	    ArrayList<String> titleLore = new ArrayList<String>();
	    titleLore.add(ChatColor.GRAY + reason);
	    titleMeta.setLore(titleLore);
	    title.setItemMeta(titleMeta);
	    
	    ItemStack history = new ItemStack(Material.BOOK);
	    ItemMeta historyim = history.getItemMeta();
	    historyim.setDisplayName(ChatColor.WHITE + "Show ALL Offences");
	    history.setItemMeta(historyim);
	
	    ItemStack chatOffense = new ItemStack(Material.IRON_SWORD);
	    ItemMeta chatOffenseim = chatOffense.getItemMeta();
	    chatOffenseim.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Chat Offences");
	    ArrayList<String> chatOffensear = new ArrayList<String>();
	    chatOffensear.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Punishment Type: Mute");
	    chatOffensear.add(ChatColor.GRAY + "" + ChatColor.ITALIC + " ");
	    chatOffensear.add(ChatColor.YELLOW + "Total Chat punishments: " + ChatColor.GOLD + totalChat);
	    chatOffenseim.setLore(chatOffensear);
	    chatOffenseim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    chatOffenseim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    chatOffense.setItemMeta(chatOffenseim);
	    
	    ItemStack generalOffense = new ItemStack(Material.GOLD_SWORD);
	    ItemMeta generalOffenseim = generalOffense.getItemMeta();
	    generalOffenseim.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Gameplay Offences");
	    ArrayList<String> generalOffensear = new ArrayList<String>();
	    generalOffensear.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Punishment Type: Ban");
	    generalOffensear.add(ChatColor.GRAY + "" + ChatColor.ITALIC + " ");
	    generalOffensear.add(ChatColor.YELLOW + "Total Gameplay punishments: " + ChatColor.GOLD + totalGame);
	    generalOffenseim.setLore(generalOffensear);
	    generalOffenseim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    generalOffenseim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    generalOffense.setItemMeta(generalOffenseim);
	    
	    ItemStack clientMod = new ItemStack(Material.IRON_SWORD);
	    ItemMeta clientModim = clientMod.getItemMeta();
	    clientModim.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Client Modifications");
	    ArrayList<String> clientModar = new ArrayList<String>();
	    clientModar.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Punishment Type: Ban");
	    clientModar.add(ChatColor.GRAY + "" + ChatColor.ITALIC + " ");
	    clientModar.add(ChatColor.YELLOW + "Total Hack punishments: " + ChatColor.GOLD + totalHack);
	    clientModim.setLore(clientModar);
	    clientModim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    clientModim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    clientMod.setItemMeta(clientModim);
	    
	    ItemStack warning = new ItemStack(Material.PAPER);
	    ItemMeta warningim = warning.getItemMeta();
	    warningim.setDisplayName(ChatColor.RED + "Warning");
	    ArrayList<String> warningar = new ArrayList<String>();
	    warningar.add(ChatColor.GRAY + " ");
	    warningar.add(ChatColor.GRAY + "Total Warnings: " + ChatColor.YELLOW + warns);
	    warningim.setLore(warningar);
	    warning.setItemMeta(warningim);
	    
	    ItemStack permban = new ItemStack(Material.REDSTONE_BLOCK);
	    ItemMeta permbanim = permban.getItemMeta();
	    permbanim.setDisplayName(ChatColor.RED + "Perm Ban");
	    permban.setItemMeta(permbanim);
	    
	    ItemStack permmute = new ItemStack(Material.REDSTONE);
	    ItemMeta permmuteim = permmute.getItemMeta();
	    permmuteim.setDisplayName(ChatColor.RED + "Temp Ban");
	    permmute.setItemMeta(permmuteim);
	    
	    ItemStack kick = new ItemStack(Material.LEATHER_BOOTS);
	    ItemMeta kickim = kick.getItemMeta();
	    kickim.setDisplayName(ChatColor.RED + "Kick");
	    kick.setItemMeta(kickim);
	    
	    ItemStack more = new ItemStack(Material.COMMAND);
	    ItemMeta moreim = more.getItemMeta();
	    moreim.setDisplayName(ChatColor.RED + "More Options");
	    more.setItemMeta(moreim);
	    
	    ItemStack chatSev1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
	    ItemMeta chatSev1im = chatSev1.getItemMeta();
	    chatSev1im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aSeverity &a&l1"));
	    ArrayList<String> chatSev1ar = new ArrayList<String>();
	    chatSev1ar.add("");
	    chatSev1ar.add(ChatColor.WHITE + "1st Offense: 30 Minutes");
	    chatSev1ar.add(ChatColor.WHITE + "2nd Offense: 12 Hours");
	    chatSev1ar.add(ChatColor.WHITE + "3rd Offense: 1 Day");
	    chatSev1ar.add(ChatColor.WHITE + "4th Offense: 2 Days");
	    chatSev1ar.add(ChatColor.WHITE + "5th+ Offense: 3 Days");
	    chatSev1ar.add("");
	    chatSev1ar.add(ChatColor.GRAY + "Total Sev.1 punishments: " + ChatColor.YELLOW + chat1);
	    chatSev1im.setLore(chatSev1ar);
	    chatSev1.setItemMeta(chatSev1im);
	    
	    //----------------------------------------
	    
	    ItemStack generalSev1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
	    ItemMeta generalSev1im = generalSev1.getItemMeta();
	    generalSev1im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aSeverity &a&l1"));
	    ArrayList<String> generalSev1ar = new ArrayList<String>();
	    generalSev1ar.add("");
	    generalSev1ar.add(ChatColor.WHITE + "1st Offense: 1 Hour");
	    generalSev1ar.add(ChatColor.WHITE + "2nd Offense: 2 Hours");
	    generalSev1ar.add(ChatColor.WHITE + "3rd Offense: 3 Hours");
	    generalSev1ar.add(ChatColor.WHITE + "4th Offense: 4 Hours");
	    generalSev1ar.add(ChatColor.WHITE + "5th+ Offense: 5 Hours");
	    generalSev1ar.add("");
	    generalSev1ar.add(ChatColor.GRAY + "Total Sev.1 punishments: " + ChatColor.YELLOW + game1);
	    generalSev1im.setLore(generalSev1ar);
	    generalSev1.setItemMeta(generalSev1im);
	    
	    //----------------------------------------
	    
	    ItemStack clientModSev1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
	    ItemMeta clientModSev1im = clientModSev1.getItemMeta();
	    clientModSev1im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aSeverity &a&l1"));
	    ArrayList<String> clientModSev1ar = new ArrayList<String>();
	    clientModSev1ar.add("");
	    clientModSev1ar.add(ChatColor.WHITE + "1st Offense: 2 Days");
	    clientModSev1ar.add(ChatColor.WHITE + "2nd Offense: 4 Days");
	    clientModSev1ar.add(ChatColor.WHITE + "3rd Offense: 6 Days");
	    clientModSev1ar.add(ChatColor.WHITE + "4th Offense: 8 Days");
	    clientModSev1ar.add(ChatColor.WHITE + "5th+ Offense: 10 Days");
	    clientModSev1ar.add("");
	    clientModSev1ar.add(ChatColor.GRAY + "Total Sev.1 punishments: " + ChatColor.YELLOW +  hack1);
	    clientModSev1im.setLore(clientModSev1ar);
	    clientModSev1.setItemMeta(clientModSev1im);
	    
	    //----------------------------------------
	    
	    ItemStack chatSev2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)4);
	    ItemMeta chatSev2im = chatSev2.getItemMeta();
	    chatSev2im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eSeverity &e&l2"));
	    ArrayList<String> chatSev2ar = new ArrayList<String>();
	    chatSev2ar.add("");
	    chatSev2ar.add(ChatColor.WHITE + "1st Offense: 2 Days");
	    chatSev2ar.add(ChatColor.WHITE + "2nd Offense: 4 Days");
	    chatSev2ar.add(ChatColor.WHITE + "3rd Offense: 5 Days");
	    chatSev2ar.add(ChatColor.WHITE + "4th Offense: 7 Days");
	    chatSev2ar.add(ChatColor.WHITE + "5th+ Offense: 14 Days");
	    chatSev2ar.add("");
	    chatSev2ar.add(ChatColor.GRAY + "Total Sev.2 punishments: " + ChatColor.YELLOW + chat2);
	    chatSev2im.setLore(chatSev2ar);
	    chatSev2.setItemMeta(chatSev2im);
	    
	    //----------------------------------------
	    
	    ItemStack generalSev2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)4);
	    ItemMeta generalSev2im = generalSev2.getItemMeta();
	    generalSev2im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eSeverity &e&l2"));
	    ArrayList<String> generalSev2ar = new ArrayList<String>();
	    generalSev2ar.add("");
	    generalSev2ar.add(ChatColor.WHITE + "1st Offense: 6 Hours");
	    generalSev2ar.add(ChatColor.WHITE + "2nd Offense: 12 Hours");
	    generalSev2ar.add(ChatColor.WHITE + "3rd Offense: 24 Hours");
	    generalSev2ar.add(ChatColor.WHITE + "4th Offense: 48 Hours");
	    generalSev2ar.add(ChatColor.WHITE + "5th+ Offense: 96 Hours");
	    generalSev2ar.add("");
	    generalSev2ar.add(ChatColor.GRAY + "Total Sev.2 punishments: " + ChatColor.YELLOW + game2);
	    generalSev2im.setLore(generalSev2ar);
	    generalSev2.setItemMeta(generalSev2im);
	    
	    //----------------------------------------
	    
	    ItemStack clientModSev2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)4);
	    ItemMeta clientModSev2im = clientModSev2.getItemMeta();
	    clientModSev2im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eSeverity &e&l2"));
	    ArrayList<String> clientModSev2ar = new ArrayList<String>();
	    clientModSev2ar.add("");
	    clientModSev2ar.add(ChatColor.WHITE + "1st Offense: 7 Days");
	    clientModSev2ar.add(ChatColor.WHITE + "2nd Offense: 14 Days");
	    clientModSev2ar.add(ChatColor.WHITE + "3rd Offense: 21 Days");
	    clientModSev2ar.add(ChatColor.WHITE + "4th Offense: 28 Days");
	    clientModSev2ar.add(ChatColor.WHITE + "5th+ Offense: Permanent");
	    clientModSev2ar.add("");
	    clientModSev2ar.add(ChatColor.GRAY + "Total Sev.2 punishments: " + ChatColor.YELLOW + hack2);
	    clientModSev2im.setLore(clientModSev2ar);
	    clientModSev2.setItemMeta(clientModSev2im);
	    
	    //----------------------------------------
	    
	    ItemStack chatSev3 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
	    ItemMeta chatSev3im = chatSev2.getItemMeta();
	    chatSev3im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cSeverity &c&l3"));
	    ArrayList<String> chatSev3ar = new ArrayList<String>();
	    chatSev3ar.add("");
	    chatSev3ar.add(ChatColor.WHITE + "1st Offense: Permanent");
	    chatSev3ar.add("");
	    chatSev3ar.add(ChatColor.GRAY + "Total Sev.3 punishments: " + ChatColor.YELLOW + chat3);
	    chatSev3im.setLore(chatSev3ar);
	    chatSev3.setItemMeta(chatSev3im);
	    
	    //----------------------------------------
	    
	    ItemStack generalSev3 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
	    ItemMeta generalSev3im = chatSev3.getItemMeta();
	    generalSev3im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cSeverity &c&l3"));
	    ArrayList<String> generalSev3ar = new ArrayList<String>();
	    generalSev3ar.add("");
	    generalSev3ar.add(ChatColor.WHITE + "1st Offense: 7 Days");
	    generalSev3ar.add(ChatColor.WHITE + "2nd Offense: 14 Days");
	    generalSev3ar.add(ChatColor.WHITE + "3rd Offense: 21 Days");
	    generalSev3ar.add(ChatColor.WHITE + "4th Offense: 28 Days");
	    generalSev3ar.add(ChatColor.WHITE + "5th+ Offense: Permanent");
	    generalSev3ar.add("");
	    generalSev3ar.add(ChatColor.GRAY + "Total Sev.3 punishments: " + ChatColor.YELLOW + game3);
	    generalSev3im.setLore(generalSev3ar);
	    generalSev3.setItemMeta(generalSev3im);
	    
	    //----------------------------------------
	    
	    ItemStack clientModSev3 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
	    ItemMeta clientModSev3im = clientModSev3.getItemMeta();
	    clientModSev3im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cSeverity &c&l3"));
	    ArrayList<String> clientModSev3ar = new ArrayList<String>();
	    clientModSev3ar.add("");
	    clientModSev3ar.add(ChatColor.WHITE + "1st Offense: 30 Days");
	    clientModSev3ar.add(ChatColor.WHITE + "2nd Offense: 60 Days");
	    clientModSev3ar.add(ChatColor.WHITE + "3rd+ Offense: Permanent");
	    clientModSev3ar.add("");
	    clientModSev3ar.add(ChatColor.GRAY + "Total Sev.3 punishments: " + ChatColor.YELLOW + hack3);
	    clientModSev3im.setLore(clientModSev3ar);
	    clientModSev3.setItemMeta(clientModSev3im);
    
	    int slot = 45;
	    int amount = 1;
	    for (Punishment pun : puns) {
	    	if (slot > 52) {
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
    
    
    	inv.setItem(4, title);
    	inv.setItem(9, permban);
    	inv.setItem(11, chatOffense);
    	inv.setItem(13, generalOffense);
    	inv.setItem(15, clientMod);
    	inv.setItem(17, warning);
    	inv.setItem(18, permmute);
    	inv.setItem(20, chatSev1);
    	inv.setItem(22, generalSev1); 
    	inv.setItem(24, clientModSev1);
    	inv.setItem(29, chatSev2);
    	inv.setItem(31, generalSev2);
    	inv.setItem(33, clientModSev2);
    	inv.setItem(35, more);
    	inv.setItem(36, kick);
    	inv.setItem(38, chatSev3);
    	inv.setItem(40, generalSev3);
    	inv.setItem(42, clientModSev3);
    	inv.setItem(53, history);
	    
    	return inv;
  	}
	
}