package org.author.game;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Packet13ChangeTile extends Packet{
	
	private int tileID, x, y;
	
	public Packet13ChangeTile(byte[] data) {
		super(13);
		tileID = Integer.parseInt(read(data)[1]);
		x = Integer.parseInt(read(data)[2]);
		y = Integer.parseInt(read(data)[3]);
	}
	
	public Packet13ChangeTile(int tileID, int x, int y) {
		super(13);
		this.tileID = tileID;
		this.x = x;
		this.y = y;
	}
	
	public byte[] getData() {
		return ("13#"+tileID+"#"+x+"#"+y).getBytes();
	}
}
