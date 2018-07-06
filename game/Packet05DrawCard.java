package org.author.game;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Packet05DrawCard extends Packet{
	
	int cardsToDraw = 0, card = 0;
	
	public Packet05DrawCard(byte[] data) {
		super(05);
		card = Integer.parseInt(read(data)[1]);
		cardsToDraw = Integer.parseInt(read(data)[2]);
	}
	
	public Packet05DrawCard(int c, int count) {
		super(05);
		this.card = c;
		this.cardsToDraw = count;
	}
	
	public byte[] getData() {
		return ("05#"+card+"#"+cardsToDraw).getBytes();
	}
}
