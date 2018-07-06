package org.author.game;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Packet02PlayCard extends Packet{
	
	int card, playerid, x, y, guid;
	String notes = "";
	
	public Packet02PlayCard(byte[] data) {
		super(02);
		playerid = Integer.parseInt(read(data)[1]);
		card = Integer.parseInt(read(data)[2]);
		x = Integer.parseInt(read(data)[3]);
		y = Integer.parseInt(read(data)[4]);
		guid = Integer.parseInt(read(data)[5]);
	}
	
	public Packet02PlayCard(int playerid, int card, int x, int y, int guid) {
		super(02);
		this.playerid = playerid;
		this.card = card;
		this.x = x;
		this.y = y;
		this.guid = guid;
	}
	
	public Packet addNote(String n){
		notes+=("#"+n);
		return this;
	}
	
	
	public byte[] getData() {
		return ("02#"+playerid+"#"+card+"#"+x+"#"+y+"#"+guid+notes).getBytes();
	}
}
