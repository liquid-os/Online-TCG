package org.author.game;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Packet03MoveUnit extends Packet{
	
	int guid, playerid, x, y;
	
	public Packet03MoveUnit(byte[] data) {
		super(03);
		playerid = Integer.parseInt(read(data)[1]);
		guid = Integer.parseInt(read(data)[2]);
		x = Integer.parseInt(read(data)[3]);
		y = Integer.parseInt(read(data)[4]);
	}
	
	public Packet03MoveUnit(int playerid, int guid, int x, int y) {
		super(03);
		this.playerid = playerid;
		this.guid = guid;
		this.x = x;
		this.y = y;
	}
	
	public byte[] getData() {
		return ("03#"+playerid+"#"+guid+"#"+x+"#"+y).getBytes();
	}
}
