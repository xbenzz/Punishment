package me.punish.Cache;

import java.util.HashMap;
import java.util.UUID;
import me.punish.Objects.Punishment;
import me.punish.Utils.Type;

public class CacheManager {
	
	public HashMap<UUID, Punishment> sell = new HashMap<>();
	public HashMap<UUID, Punishment> tpa = new HashMap<>();
    public HashMap<UUID, Punishment> mutes = new HashMap<>();
    
    public HashMap<UUID, Punishment> getMutes() {
    	return mutes;
    }
    
    public HashMap<UUID, Punishment> getSells() {
    	return sell;
    }
    
    public HashMap<UUID, Punishment> getTpas() {
    	return tpa;
    }
    
    public Punishment getMute(UUID uuid) {
    	return mutes.get(uuid);
    }
    
    public Punishment getSell(UUID uuid) {
    	return sell.get(uuid);
    }
    
    public Punishment getTpa(UUID uuid) {
    	return tpa.get(uuid);
    }
    
    public void loadPunishments(UUID id) {
    	retrievePunishments(id);
    }
    
    public void savePunishments(UUID uuid) {
    	if (mutes.containsKey(uuid)) {
    		mutes.remove(uuid);
    	}
    	if (sell.containsKey(uuid)) {
    		sell.remove(uuid);
    	}
    	if (tpa.containsKey(uuid)) {
    		tpa.remove(uuid);
    	}
    }
    
    private void retrievePunishments(UUID uuid) {
    	Punishment m = new Punishment(uuid, Type.MUTE);
    	Punishment s = new Punishment(uuid, Type.SELL);
    	Punishment t = new Punishment(uuid, Type.TPA);
    	mutes.put(uuid, m);
    	sell.put(uuid, s);
    	tpa.put(uuid, t);
    }

}
