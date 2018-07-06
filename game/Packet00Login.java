package org.author.game;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Packet00Login extends Packet{
	
	public String username, deck;
	int playerID, classid = 0, roomID = -1;
	
	public Packet00Login(byte[] data) {
		super(00);
		username = read(data)[1];
		classid = Integer.parseInt(read(data)[2]);
		playerID = Integer.parseInt(read(data)[3]);
		deck = read(data)[4];
		roomID = Integer.parseInt(read(data)[5]);
	}
	
	public Packet00Login(String user, int heroID, int playerID, String deck, int r) {
		super(00);
		this.playerID = playerID;
		this.username = user;
		this.classid = heroID;
		this.deck = deck;
		this.roomID = r;
	}
	
	public byte[] getData() {
		return ("00#"+username+"#"+classid+"#"+playerID+"#"+deck+"#"+roomID).getBytes();
	}
}
