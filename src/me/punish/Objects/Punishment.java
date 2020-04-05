package me.punish.Objects;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import me.punish.Punish;
import me.punish.Database.Database;
import me.punish.Utils.Type;

public class Punishment {
	
	private int id;
	private UUID player;
	private String punisher;
	private String reason;
	private long expire;
	private long time;
	private Type type;
	private Type category;
	private int severity;
	private String date;
	private int offence;
	private boolean active;
	
	public Punishment() {
	}
	
	public Punishment(UUID player) {
		this.player = player;
	}

	public Punishment(int id, UUID player, String punisher, String reason, long expire, long time, Type type, Type category, int severity, String date, int offence, boolean active) {
		this.id = id;
		this.player = player;
		this.punisher = punisher;
		this.reason = reason;
		this.expire = expire;
		this.time = time;
		this.type = type;
		this.category = category;
		this.severity = severity;
		this.date = date;
		this.offence = offence;
		this.active = active;
	}
	
	public Punishment(UUID player, String punisher, String reason, long expire, long time, Type type, Type category, int severity, String date, int offence, boolean active) {
		this.player = player;
		this.punisher = punisher;
		this.reason = reason;
		this.expire = expire;
		this.time = time;
		this.type = type;
		this.category = category;
		this.severity = severity;
		this.date = date;
		this.offence = offence;
		this.active = active;
	}
	
	public Punishment(UUID player, @Nullable String punisher, @Nullable String reason, long expire, Type type, Type category, int severity, String date, int offence) {
		this.player = player;
		this.punisher = punisher;
		this.reason = reason;
		this.expire = expire;
		this.type = type;
		this.category = category;
		this.severity = severity;
		this.date = date;
		this.offence = offence;
	}
	
	public Punishment(UUID player, @Nullable String punisher, @Nullable String reason, long expire, Type type, Type category, int severity, String date, int offence, boolean active) {
		this.player = player;
		this.punisher = punisher;
		this.reason = reason;
		this.expire = expire;
		this.type = type;
		this.category = category;
		this.severity = severity;
		this.date = date;
		this.offence = offence;
		this.active = active;
	}
	
	public Punishment(UUID player, Type type, boolean t) {
		this.player = player;
		this.type = type;
		this.active = false;
	}
	
	public Punishment(UUID player, Type type) {
		this.player = player;
		this.type = type;
		try {
			PreparedStatement stat = Database.getConnection().prepareStatement("SELECT * FROM punishments WHERE player = ? AND type = ? AND active = '1'");
			stat.setString(1, player.toString());
			stat.setString(2, type.toString());
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				id = rs.getInt("ID");
				punisher = rs.getString("punisher");
				reason = rs.getString("reason");
				category = Type.valueOf(rs.getString("category"));
				expire = rs.getLong("expire");
				time = rs.getLong("time");
				severity = rs.getInt("severity");
				date = rs.getString("date");
				offence = rs.getInt("offence");
				active = rs.getBoolean("active");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Punishment(int ID) {
		this.id = ID;
		try {
			PreparedStatement stat = Database.getConnection().prepareStatement("SELECT * FROM punishments WHERE ID = ?");
			stat.setString(1, player.toString());
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				player = UUID.fromString(rs.getString("player"));
				punisher = rs.getString("punisher");
				reason = rs.getString("reason");
				expire = rs.getLong("expire");
				time = rs.getLong("time");
				type = Type.valueOf(rs.getString("type"));
				category = Type.valueOf(rs.getString("category"));
				severity = rs.getInt("severity");
				date = rs.getString("date");
				offence = rs.getInt("offence");
				active = rs.getBoolean("active");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void execute() {
		if (player == null) {
			return;
		}
		final String punishee = (punisher == null) || (punisher.equalsIgnoreCase("")) ? "Console" : punisher;
		final String banreason = (reason == null) || (reason.equalsIgnoreCase("")) ? "N/A" : reason;
		final long bantime = System.currentTimeMillis();
	    long duration = 0;
		if (expire == 0) {
			duration = 0;
		} else {
			long millis = expire * 1000L;
			duration = System.currentTimeMillis() + millis;
		}
		final long end = duration;
		
		if (type == Type.MUTE || type == Type.SELL || type == Type.TPA) {
			OfflinePlayer off = Bukkit.getOfflinePlayer(player);
			if (off.isOnline()) {
				if (type == Type.MUTE) {
					Punish.getCache().getMutes().replace(player, new Punishment(player, punishee, banreason, end, bantime, type, category, severity, date, offence, true));
				} else if (type == Type.SELL) {
					Punish.getCache().getSells().replace(player, new Punishment(player, punishee, banreason, end, bantime, type, category, severity, date, offence, true));
				} else if (type == Type.TPA) {
					Punish.getCache().getTpas().replace(player, new Punishment(player, punishee, banreason, end, bantime, type, category, severity, date, offence, true));
				}
			}
		}
		

			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("INSERT INTO punishments(player, punisher, reason, expire, time, type, category, severity, offence, date, active) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
				ps.setString(1, player.toString());
				ps.setString(2, punishee);
				ps.setString(3, banreason);				
				ps.setLong(4, end);
				ps.setLong(5, bantime);
				ps.setString(6, type.toString());
				ps.setString(7, category.toString());
				ps.setInt(8, severity);
				ps.setInt(9, offence);
				ps.setString(10, date);
				ps.setBoolean(11, true);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	
	public List<Punishment> getHistory() {
		List<Punishment> punish = new ArrayList<Punishment>();
		try {
			PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM punishments WHERE player = ?");
			ps.setString(1, player.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				punish.add(new Punishment(rs.getInt("ID"), UUID.fromString(rs.getString("player")), rs.getString("punisher"), rs.getString("reason"), Long.valueOf(rs.getLong("expire")), Long.valueOf(rs.getLong("time")), Type.valueOf(rs.getString("type")), Type.valueOf(rs.getString("category")), rs.getInt("severity"), rs.getString("date"), rs.getInt("offence"), rs.getBoolean("active")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return punish;
	}
	
	public void remove() {
		Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE punishments SET active = '0' WHERE player = ? AND active = '1' AND type = ?");
				ps.setString(1, player.toString());
				ps.setString(2, type.toString());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		if (type == Type.MUTE || type == Type.SELL || type == Type.TPA) {
			OfflinePlayer off = Bukkit.getOfflinePlayer(player);
			if (off.isOnline()) {
				if (type == Type.MUTE) 
					Punish.getCache().getMutes().replace(player, new Punishment(player, Type.MUTE, false));
				else if (type == Type.SELL)
					Punish.getCache().getSells().replace(player, new Punishment(player, Type.SELL, false));
				else if (type == Type.TPA)
					Punish.getCache().getTpas().replace(player, new Punishment(player, Type.TPA, false));;
			}
		}
	}
	
	public int getPastOffense(int sev, Type category, List<Punishment> list) {
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getSeverity() == sev && list.get(i).getCategory() == category) {
				count++;
			}
		}
		return count;
    }
	
	public int getPastOffense(int sev, Type category) {
		int rowCount = 0;
		try {
		    PreparedStatement ps = Database.getConnection().prepareStatement("SELECT COUNT( * ) as 'Numbers of Rows' FROM punishments WHERE player = ? AND severity = ? AND category = ?");
		    ps.setString(1, player.toString());
		    ps.setInt(2, sev);
		    ps.setString(3, category.toString());
		    ResultSet rs = ps.executeQuery();
		    while (rs.next())
		    	rowCount = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowCount;
    }
	
	public void clearAll() {
		Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("DELETE FROM punishments WHERE player = ?");
                ps.setString(1, player.toString());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void clear(int ID) {
		Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("DELETE FROM punishments WHERE ID = ?");
                ps.setInt(1, ID);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public boolean isPunished(Type type) {
		try {
		    PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM punishments WHERE player = ? AND type = ? AND active = '1'");
		    ps.setString(1, player.toString());
		    ps.setString(2, type.toString());
		    ResultSet rs = ps.executeQuery();
		    return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/* Getters */
	public String toString() {
		return "Punishment(id=" + getID() + ", player=" + getPlayer() + ", punisher=" + getPunisher() + ", reason=" + getReason() + ", expire=" + getExpire() + ", duration=" + getDuration() + ", type=" + getType() + ", severity=" + getSeverity() + ", date=" + getDate() + ", offence =" + getOffence() + ", active=" + isActive() + ")";
	}

	public int getID() {
		return id;
	}

	public UUID getPlayer() {
		return player;
	}

	public String getPunisher() {
		return punisher;
	}

	public String getReason() {
		return reason;
	}

	public long getExpire() {
		return expire;
	}

	public long getDuration() {
		return time;
	}

	public Type getType() {
		return type;
	}
	
	public Type getCategory() {
		return category;
	}
	
	public int getSeverity() {
		return severity;
	}
	
	public String getDate() {
		return date;
	}
	
	public int getOffence() {
		return offence;
	}

	public boolean isPermanent() {
		if (expire == 0L)
			return true;
		else
			return false;
	}

	public boolean isActive() {
		return active;
	}

}
