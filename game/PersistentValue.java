package org.author.game;

import java.util.ArrayList;

public class PersistentValue {
	
	String tag, sVal;
	int nVal;
	boolean eternal = false;
	public boolean flagRemove = false;
	
	//TURN_DIED, GAME_DIED, GAME_DMGINCR
	
	public PersistentValue(String tag, int nVal, String sVal){
		this.tag = tag;
		this.nVal = nVal;
		this.sVal = sVal;
	}
	
	public static ArrayList<PersistentValue> getVars(int roomID){
		return Bank.isServer() ? Bank.getServer().getRoom(roomID).vars : Util.vars;
	}
	
	public static void modNumbervalByTag(String tag, int mod, int roomID){
		for(PersistentValue v : getVars(roomID)){
			if(v.tag.equalsIgnoreCase(tag)){
				v.nVal += mod;
			}
		}
	}
	
	public PersistentValue setLifetime(int l){
		this.eternal = (l == 1);
		return this;
	}
	
	public static PersistentValue addPersistentValue(String t, int nVal, int roomID){
		PersistentValue p = new PersistentValue(t, nVal, null);
		boolean canAdd = true;
		for(PersistentValue v : getVars(roomID)){
			if(v.tag.equalsIgnoreCase(p.tag)){
				canAdd = false;
			}
		}
		if(canAdd){
			getVars(roomID).add(p);
		}else{
			modNumbervalByTag(p.tag, p.nVal, roomID);
		}
		return p;
	}
	
	public static PersistentValue addPersistentValue(String t, String sVal, int roomID){
		PersistentValue p = new PersistentValue(t, 0, sVal);
		boolean canAdd = true;
		for(PersistentValue v : getVars(roomID)){
			if(v.tag.equalsIgnoreCase(p.tag)){
				canAdd = false;
			}
		}
		if(canAdd){
			getVars(roomID).add(p);
		}else{
			modNumbervalByTag(p.tag, p.nVal, roomID);
		}
		return p;
	}
	
	public static PersistentValue addUniquePersistentValue(String t, int nVal, int roomID){
		PersistentValue p = new PersistentValue(t, nVal, null);
		getVars(roomID).add(p);
		return p;
	}
	
	public static PersistentValue getPersistentValueByTag(String tag, int roomID){
		for(PersistentValue v : getVars(roomID)){
			if(v.tag.equalsIgnoreCase(tag) &! v.flagRemove){
				return v;
			}
		}
		return new PersistentValue(tag,0,"");
	}

	public static void setVars(ArrayList<PersistentValue> newvarlist, int roomID) {
		if(Bank.isServer()){
			Bank.getServer().getRoom(roomID).vars = (ArrayList<PersistentValue>) newvarlist.clone();
		}else{
			Util.vars = (ArrayList<PersistentValue>) newvarlist.clone();
		}
	}
}
