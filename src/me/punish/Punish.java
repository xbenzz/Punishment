package me.punish;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.punish.Cache.CacheManager;
import me.punish.Commands.CheckIPCommand;
import me.punish.Commands.ClearIPCommand;
import me.punish.Commands.ClearPunishCommand;
import me.punish.Commands.IPCommand;
import me.punish.Commands.PunishCommand;
import me.punish.Commands.PunishIPCommand;
import me.punish.Commands.UnpunishIPCommand;
import me.punish.Database.Database;
import me.punish.Manager.PunishGUIEvents;
import me.punish.Manager.TempGUIEvents;
import me.punish.Manager.HistoryGUIEvents;
import me.punish.Manager.OptionsGUIEvents;
import me.punish.Manager.PlayerEvents;
import net.milkbowl.vault.permission.Permission;

public class Punish extends JavaPlugin {
	
	public static Punish instance;
	public static Permission perms;
	public static CacheManager manager;
	
	public void onEnable() {
		instance = this;
		manager = new CacheManager();
	    loadConfig();
	    Database.openDatabaseConnection();
	    registerCommands();
	    registerEvents();
	    setupPermissions();
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	}
	
	public void onDisable() {
		Bukkit.getScheduler().cancelAllTasks();
		Bukkit.getScheduler().getPendingTasks().clear();
	    Database.closeConnection();
	}
	
	private void registerCommands() {
	     getCommand("punish").setExecutor(new PunishCommand());
	     getCommand("puniship").setExecutor(new PunishIPCommand());
	     getCommand("unpuniship").setExecutor(new UnpunishIPCommand());
	     getCommand("iplookup").setExecutor(new IPCommand());
	     getCommand("checkip").setExecutor(new CheckIPCommand());
	     getCommand("clearpunish").setExecutor(new ClearPunishCommand());
	     getCommand("clearip").setExecutor(new ClearIPCommand());
	}
	   
	private void registerEvents() {
	    PluginManager manager = Bukkit.getPluginManager();
	    manager.registerEvents(new PlayerEvents(), this);
	    manager.registerEvents(new PunishGUIEvents(), this);
	    manager.registerEvents(new OptionsGUIEvents(), this);
	    manager.registerEvents(new TempGUIEvents(), this);
	    manager.registerEvents(new HistoryGUIEvents(), this);
	}
	
	private void loadConfig() {
		FileConfiguration cfg = getConfig();
		cfg.options().copyDefaults(true);
		saveDefaultConfig();
	}
	  
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
	
    public static Permission getPermissions() {
        return perms;
    }
	
	public static Punish getInstance() {
		return instance;
	}
	
	public static CacheManager getCache() {
		return manager;
	}
	

}
