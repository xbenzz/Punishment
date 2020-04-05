package me.punish.Utils;

public enum Type {

	MUTE("Mute"), 
	BAN("Ban"),
	WARN("WARN"), 
	CHAT("CHAT OFFENCE"), 
	GAMEPLAY("GAMEPLAY OFFENCE"), 
	HACKING("CLIENT MODIFICTION"), 
	PERM_BAN("BAN PERM"), 
	TEMP_BAN("TEMP BAN"), 
	SELL("REVOKE SELL"), 
	TPA("TPA SPAM");
	
	private String value;
	
	private Type(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
}
