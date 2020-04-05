package me.punish.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.punish.Punish;
import me.punish.Utils.Type;
import net.md_5.bungee.api.ChatColor;

public class DataHandler {
	
	public static void generateTables() {
	    Connection con = Database.getConnection();
	    try {
		    if (Database.usingLite) {
		    	PreparedStatement stat = con.prepareStatement("CREATE TABLE IF NOT EXISTS punishments (ID INTEGER PRIMARY KEY, player VARCHAR(36), punisher VARCHAR(36), reason LONGTEXT, expire LONGTEXT, time LONGTEXT, type VARCHAR(16), category VARCHAR(16), severity INT(16), offence INT(16), date VARCHAR(36), active INT(16));");
		    	stat.execute();
		    	PreparedStatement stat2 = con.prepareStatement("CREATE TABLE IF NOT EXISTS ipbans (ID INTEGER PRIMARY KEY, punisher VARCHAR(36), reason LONGTEXT, ip VARCHAR(45), date VARCHAR(36), active INT(16), removeby VARCHAR(36), removedate VARCHAR(36));");
		    	stat2.execute();
		    	PreparedStatement stat3 = con.prepareStatement("CREATE TABLE IF NOT EXISTS users (ID INTEGER PRIMARY KEY, player VARCHAR(36), ip VARCHAR(45), date VARCHAR(36));");
		    	stat3.execute();
		    } else {
		    	PreparedStatement stat = con.prepareStatement("CREATE TABLE IF NOT EXISTS punishments (ID INT(255) NOT NULL AUTO_INCREMENT PRIMARY KEY, player VARCHAR(36), punisher VARCHAR(36), reason LONGTEXT, expire LONGTEXT, time LONGTEXT, type VARCHAR(16), category VARCHAR(16), severity INT(16), offence INT(16), date VARCHAR(36), active INT(16));");
		    	stat.execute();
		    	PreparedStatement stat2 = con.prepareStatement("CREATE TABLE IF NOT EXISTS ipbans (ID INT(255) NOT NULL AUTO_INCREMENT PRIMARY KEY, punisher VARCHAR(36), reason LONGTEXT, ip VARCHAR(45), date VARCHAR(36), active INT(16), removeby VARCHAR(36), removedate VARCHAR(36));");
		    	stat2.execute();
		    	PreparedStatement stat3 = con.prepareStatement("CREATE TABLE IF NOT EXISTS users (ID INT(255) NOT NULL AUTO_INCREMENT PRIMARY KEY, player VARCHAR(36), ip VARCHAR(45), date VARCHAR(36));");
		    	stat3.execute();
		    }
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	}
	  
    public static void kickPlayer(String player, String reason) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("KickPlayer");
        out.writeUTF(player);
        out.writeUTF(reason);
        
        Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        p.sendPluginMessage(Punish.getInstance(), "BungeeCord", out.toByteArray());
    }

	public static String timeFormat(long startTime) {
		String mins;
		String hours;
		String days;
		String seconds;
		
		startTime = startTime / 1000;
		
		long d = startTime / 86400;
		days = "&6" + String.valueOf(d) + " &eDays&f, ";
		startTime = startTime % 86400;
		
		long h = startTime / 3600;
		hours = "&6" + String.valueOf(h) + " &eHours&f, ";
		startTime = startTime % 3600;
		
		long m = startTime / 60;
		mins = "&6" + String.valueOf(m) + " &eMinutes&f, ";
		startTime = startTime % 60;
		
		long s = startTime;
		seconds = "&6" + String.valueOf(s) + " &eSeconds";
		
		String total = "";
		if (d != 0) {
			total += days;
		} 
		if (h != 0) {
			total += hours;
		}
		if (m != 0) {
			total += mins;
		}
		if (s != 0) {
			total += seconds;
		}
		return ChatColor.translateAlternateColorCodes('&', total);
	}
	  
	public static int getDuration(Type category, int severity, int pastOffences) {
	    if (category == Type.CHAT) {
	    	int hours = 0;
	    	if (severity == 1) {
	    		if (pastOffences == 0) {
	    			return 1800;
	    		} else if(pastOffences == 1) {
	    			return 43200;
	    		} else if(pastOffences == 2) {
	    			return 86400;
	    		} else if(pastOffences == 3) {
	    			return 172800;
	    		} else if(pastOffences >= 4) {
	    			return 259200;
	    		}
	        }
	       
	    	if (severity == 2) {
	    		if (pastOffences == 0) {
	    			return 172800;
	    		} else if(pastOffences == 1) {
	    			return 345600;
	    		} else if(pastOffences == 2) {
	    			return 432000;
	    		} else if(pastOffences == 3) {
	    			return 604800;
	    		} else if(pastOffences >= 4) {
	    			return 1209600;
	    		}
	    	}
	      
	      
	    	if (severity == 3) {
	    		if (pastOffences >= 0) {
	    			return 0;
	    		}
	    	}
	      return hours;
	    }
	    
	    if (category == Type.GAMEPLAY) {
	      int hours = 0;
	      if (severity == 1) {
	          if (pastOffences == 0) {
	              return 3600;
	          } else if(pastOffences == 1) {
	              return 7200;
	          } else if(pastOffences == 2) {
	              return 10800;
	          } else if(pastOffences == 3) {
	              return 14400;
	          } else if(pastOffences >= 4) {
	              return 18000;
	          }
	      }
	      
	      if (severity == 2) {
	          if (pastOffences == 0) {
	              return 21600;
	          } else if(pastOffences == 1) {
	              return 43200;
	          } else if(pastOffences == 2) {
	              return 86400;
	          } else if(pastOffences == 3) {
	              return 172800;
	          } else if(pastOffences >= 4) {
	              return 345600;
	          }
	      }
	      
	      if (severity == 3) {
	          if (pastOffences == 0) {
	              return 604800;
	          } else if(pastOffences == 1) {
	              return 1209600;
	          } else if(pastOffences == 2) {
	              return 1814400;
	          } else if(pastOffences == 3) {
	              return 2419200;
	          } else if(pastOffences >= 4) {
	              return 0;
	          }
	      }
	      return hours;
	    }
	    
	    if (category == Type.HACKING) {
	      int hours = 0;
	      if (severity == 1) {
	          if (pastOffences == 0) {
	              return 172800;
	          } else if(pastOffences == 1) {
	              return 345600;
	          } else if(pastOffences == 2) {
	              return 518400;
	          } else if(pastOffences == 3) {
	              return 691200;
	          } else if(pastOffences >= 4) {
	              return 864000;
	          }
	      }
	      
	      if (severity == 2) {
	          if (pastOffences == 0) {
	              return 604800;
	          } else if(pastOffences == 1) {
	              return 1209600;
	          } else if(pastOffences == 2) {
	              return 1814400;
	          } else if(pastOffences == 3) {
	              return 2419200;
	          } else if(pastOffences >= 4) {
	              return 0;
	          }
	      }
	      
	      if (severity >= 3) {
	          if (pastOffences == 0) {
	              return 2592000;
	          } else if(pastOffences == 1) {
	              return 5184000;
	          } else if(pastOffences >= 2) {
	              return 0;
	          } 
	        
	      }
	      return hours;
	    }
	    return 0;
	  }
	  
}
