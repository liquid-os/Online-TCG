package org.author.game;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Packet07CardAbility extends Packet{
	
	int guid, targetguid, abilityID, x, y;
	String notes = "";
	
	public Packet07CardAbility(byte[] data) {
		super(07);
		guid = Integer.parseInt(read(data)[1]);
		targetguid = Integer.parseInt(read(data)[2]);
		abilityID = Integer.parseInt(read(data)[3]);
		x = Integer.parseInt(read(data)[4]);
		y = Integer.parseInt(read(data)[5]);
	}
	
	public Packet07CardAbility(int guid, int target, int abilityID, int x, int y) {
		super(07);
		this.guid = guid;
		this.targetguid = target;
		this.abilityID = abilityID;
		this.x = x;
		this.y = y;
	}
	
	public Packet addNote(String n){
		notes+=("#"+n);
		return this;
	}
	
	public byte[] getData() {
		return ("07#"+guid+"#"+targetguid+"#"+abilityID+"#"+x+"#"+y+notes).getBytes();
	}
}
