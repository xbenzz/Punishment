package me.punish.Database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.bukkit.configuration.file.FileConfiguration;
import me.punish.Punish;

public class Database {
	
	private static Connection connection;
	private static FileConfiguration config = Punish.getInstance().getConfig();
  	public static boolean usingLite = Punish.getInstance().getConfig().getBoolean("SQLite");
  
  	public static void openDatabaseConnection() {
  		try {
  			if (usingLite) {
  				try {
  					Class.forName("org.sqlite.JDBC");
  				} catch (ClassNotFoundException localClassNotFoundException) {
  					localClassNotFoundException.printStackTrace();
  				}
  				File localFile = new File(Punish.getInstance().getDataFolder().toString() + File.separator + "data.db");
  				if (!localFile.exists()) {
  					try {
  						localFile.createNewFile();
  					} catch (IOException localIOException) {
  						localIOException.printStackTrace();
  					}
  				}
  				connection = DriverManager.getConnection("jdbc:sqlite:" + localFile.getAbsolutePath());
  			} else {
  				connection = DriverManager.getConnection("jdbc:mysql://" + config.getString("sql.host") + ":" + config.getString("sql.port") + "/" + config.getString("sql.database") + "?autoReconnect=true", config.getString("sql.user"), config.getString("sql.pass"));
  			}
  			DataHandler.generateTables();
  		} catch (SQLException localSQLException) {
  			localSQLException.printStackTrace();
  		}
  	}
  
  	public static void closeConnection() {
  		try {
  			connection.close();
  		} catch (SQLException localSQLException) {
  			localSQLException.printStackTrace();
  		}
  	}
  
  	public static Connection getConnection() {
	  try {
		  if ((connection == null) || (connection.isClosed())) {
			  openDatabaseConnection();
		  }
	  } catch (SQLException localSQLException) {}
	  return connection;
  	}
  
}

