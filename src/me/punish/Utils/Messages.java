package me.punish.Utils;

import org.bukkit.ChatColor;

import me.punish.Punish;

public class Messages {
	
	public String permanent() {
		return ChatColor.translateAlternateColorCodes('&', "&cPermanent &f(&7Never&f)");
	}
	
	public String ipbanMessage(String reason, String staff) {
	    String kicked = ChatColor.RED + "" + ChatColor.BOLD + "You are IP-Banned!";
	    String kicked2 = ChatColor.WHITE + "Reason: " + ChatColor.GRAY + reason;
	    String kicked2a = ChatColor.WHITE + "Punished By: " + ChatColor.GRAY + staff;
	    String kicked3 = " ";
		String kicked4 = ChatColor.YELLOW + "Believe your punishment was a mistake?";
		String kicked5 = ChatColor.YELLOW + "Post an appeal at: " + ChatColor.GOLD + Punish.getInstance().getConfig().getString("Appeal-Link");
	    String kicked6 = " ";
	    String kicked7 = ChatColor.WHITE + "Expires: " + permanent();
	    String s = kicked + "\n" + kicked2 + "\n" + kicked2a + "\n" + kicked3 + "\n" + kicked4 + "\n" + kicked5 + "\n" + kicked6 + "\n" + kicked7;
	    return s;
	}
	
	public String banMessage(String reason, int id, String time, String staff) {
	    String kicked = ChatColor.RED + "" + ChatColor.BOLD + "You are Banned!";
	    String kicked2 = ChatColor.WHITE + "Reason: " + ChatColor.GRAY + reason;
	    String kicked2a = ChatColor.WHITE + "Punished By: " + ChatColor.GRAY + staff;
	    String kicked3 = ChatColor.WHITE + "Punishment ID: " + ChatColor.RED + "" + ChatColor.BOLD + id;
	    String kicked4 = " ";
	    String kicked5 = ChatColor.YELLOW + "Believe your punishment was a mistake?";
	    String kicked6 = ChatColor.YELLOW + "Post an appeal at: " + ChatColor.GOLD + Punish.getInstance().getConfig().getString("Appeal-Link");
	    String kicked7 = " ";
	    String unfairly = ChatColor.WHITE + "Expires: " + ChatColor.YELLOW + time;
	    String s = kicked + "\n" + kicked2 + "\n" + kicked2a + "\n" + kicked3 + "\n" + kicked4 + "\n" + kicked5 + "\n" + kicked6 + "\n" + kicked7 + "\n" + unfairly;
		return s;
	}
	
	public String kickMessage(String reason) { 
	    String kickeds = ChatColor.RED + "" + ChatColor.BOLD + "You were Kicked!";
	    String kickeds2 = ChatColor.YELLOW + reason;
	    String s = kickeds + "\n" + kickeds2;
		return s;
	}
	
	public String muteMessage(String reason, String time, String staff) {
		  String kick = " ";
		  String kicked = ChatColor.RED + "" + ChatColor.BOLD + "You are Muted!";
		  String kicked2 = ChatColor.WHITE + "Reason: " + ChatColor.GRAY + reason;
		  String kicked2a = ChatColor.WHITE + "Punished By: " + ChatColor.GRAY + staff;
		  String kicked3 = ChatColor.GOLD + "Believe your punishment was a mistake?";
	      String kicked4 = ChatColor.GOLD + "Post an appeal at: " + ChatColor.YELLOW + Punish.getInstance().getConfig().getString("Appeal-Link");
	      String kicked5 = ChatColor.WHITE + "Expires: " + ChatColor.YELLOW + time;
	      String kicked6 = " ";
	      String s = kick + "\n" + kicked + "\n" + kicked2 + "\n" + kicked2a + "\n" + kicked3 + "\n" + kicked4 + "\n" + kicked5 + "\n" + kicked6;
	      return s;
	}
	
	public String warnMessage(String reason) {
		 String kick = " ";
  	     String kick2 = ChatColor.RED + "" + ChatColor.BOLD + "You have been Warned!";
  	     String kick3 = ChatColor.YELLOW + reason;
  	     String kick4 = " ";
	     String s = kick + "\n" + kick + "\n" + kick2 + "\n" + kick3 + "\n" + kick4;
	     return s;
	}
	
	

}
