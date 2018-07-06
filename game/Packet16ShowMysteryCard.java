package org.author.game;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Packet16ShowMysteryCard extends Packet{
	
	int type, cost;
	
	public Packet16ShowMysteryCard(byte[] data) {
		super(16);
		cost = Integer.parseInt(read(data)[1]);
		type = Integer.parseInt(read(data)[2]);
	}
	
	public Packet16ShowMysteryCard(int cost, int type) {
		super(16);
		this.type = type;
		this.cost = cost;
	}

	public byte[] getData() {
		return ("16#"+cost+"#"+type).getBytes();
	}
}
