package me.punish.Objects;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.scheduler.BukkitRunnable;

import me.punish.Punish;
import me.punish.Database.Database;

public class IPBan {
	
	private int id;
	private String ip;
	private String punisher;
	private String reason;
	private String date;
	private boolean active;
	private String removeBy;
	private String removeDate;

	public IPBan(int id, String ip, String punisher, String reason, String date, boolean active, String removeBy, String removeDate) {
		this.id = id;
		this.ip = ip;
		this.punisher = punisher;
		this.reason = reason;
		this.date = date;
		this.active = active;
		this.removeBy = removeBy;
		this.removeDate = removeDate;
	}
	
	public IPBan(String ip, String punisher, String reason, String date) {
		this.ip = ip;
		this.punisher = punisher;
		this.reason = reason;
		this.date = date;
	}
	
	public IPBan(String ip) {
		this.ip = ip;
		try {
			PreparedStatement stat = Database.getConnection().prepareStatement("SELECT * FROM ipbans WHERE ip = ? AND active = '1'");
			stat.setString(1, ip);
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				id = rs.getInt("ID");
				punisher = rs.getString("punisher");
				reason = rs.getString("reason");
				date = rs.getString("date");
				active = rs.getBoolean("active");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public IPBan() {
	}
	
	public void execute() {
		if (ip == null) {
			return;
		}
		final String punishee = (punisher == null) || (punisher.equalsIgnoreCase("")) ? "Console" : punisher;
		final String banreason = (reason == null) || (reason.equalsIgnoreCase("")) ? "N/A" : reason;
		new BukkitRunnable() {
			public void run() {
				try {
					PreparedStatement ps = Database.getConnection().prepareStatement("INSERT INTO ipbans(punisher, reason, ip, date, active, removeby, removedate) VALUES (?,?,?,?,?,?,?)");
					ps.setString(1, punishee);
					ps.setString(2, banreason);
					ps.setString(3, ip);
					ps.setString(4, date);
					ps.setBoolean(5, true);
					ps.setString(6, null);
					ps.setString(7, null);
					ps.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(Punish.getInstance());
	}
	
	public void remove(String by) {
		Date d = new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		new BukkitRunnable() {
			public void run() {
				try {
					PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE ipbans SET active = '0', removeby = ?, removedate = ? WHERE ip = ?");
					ps.setString(1, by);
					ps.setString(2, date.format(d));
					ps.setString(3, ip);
					ps.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(Punish.getInstance());
	}
	
	
	public void clear(int ID) {
		new BukkitRunnable() {
			public void run() {
				try {
					PreparedStatement ps = Database.getConnection().prepareStatement("DELETE FROM ipbans WHERE ID = ?");
					ps.setInt(1, ID);
					ps.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(Punish.getInstance());
	}
	
	public ArrayList<IPBan> getHistory(String ip) {
		ArrayList<IPBan> list = new ArrayList<IPBan>();
		try {
			PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM ipbans WHERE ip = ?");
			ps.setString(1, ip);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new IPBan(rs.getInt("ID"), rs.getString("ip"), rs.getString("punisher"), rs.getString("reason"), rs.getString("date"), rs.getBoolean("active"), rs.getString("removeby"), rs.getString("removedate")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/* Getters */
	@Override
	public String toString() {
		return "IPBan(id=" + getID() + ", ip=" + getIP() + ", punisher=" + getPunisher() + ", reason=" + getReason() + ", date=" + getDate() + ", active=" + isActive() + ")";
	}

	public int getID() {
		return id;
	}
	
	public String getIP() {
		return ip;
	}

	public String getPunisher() {
		return punisher;
	}

	public String getReason() {
		return reason;
	}
	
	public String getDate() {
		return date;
	}

	public boolean isActive() {
		return active;
	}
		
	public String getRemovedBy() {
		return removeBy;
	}
	
	public String getRemoveDate() {
		return removeDate;
	}
}