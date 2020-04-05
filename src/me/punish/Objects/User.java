package me.punish.Objects;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import me.punish.Punish;
import me.punish.Database.Database;

public class User {
	
	private int id;
	private UUID player;
	private String ip;
	private String date;
	
	public User(int id, UUID player, String ip, String date) {
		this.id = id;
		this.player = player;
		this.ip = ip;
		this.date = date;
	}
	
	public User(UUID player, String ip, String date) {
		this.player = player;
		this.ip = ip;
		this.date = date;
	}
	
	public User(UUID player) {
		this.player = player;
	}
	
	public User(String ip) {
		this.ip = ip;
	}
	
	public void create() {
		if (player == null) {
			return;
		}
		Bukkit.getScheduler().runTaskAsynchronously(Punish.getInstance(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("INSERT INTO users(player, ip, date) VALUES (?,?,?)");
				ps.setString(1, player.toString());
				ps.setString(2, ip);
				ps.setString(3, date);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public boolean exists() {
		try {
			PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM users WHERE player = ? AND ip = ?");
			ps.setString(1, player.toString());
			ps.setString(2, ip);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<User> getIPs() {
		List<User> ips = new ArrayList<User>();
		try {
			PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM users WHERE player = ?");
			ps.setString(1, player.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				ips.add(new User(rs.getInt("ID"), UUID.fromString(rs.getString("player")), rs.getString("ip"), rs.getString("date")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ips;
	}
	
	public List<User> getPlayers() {
		List<User> players = new ArrayList<User>();
		try {
			PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM users WHERE ip = ?");
			ps.setString(1, ip);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				players.add(new User(rs.getInt("ID"), UUID.fromString(rs.getString("player")), rs.getString("ip"), rs.getString("date")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return players;
	}
	
	public void load() {
		Punish.getCache().loadPunishments(player);
	}

	public String toString() {
		return "User(id=" + getID() + ", ip=" + getIP() + ", player=" + getPlayer() + ", date=" + getDate() + ")";
	}

	public int getID() {
		return id;
	}
	
	public UUID getPlayer() {
		return player;
	}
	
	public String getIP() {
		return ip;
	}

	public String getDate() {
		return date;
	}

}
