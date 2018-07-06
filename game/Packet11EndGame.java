package org.author.game;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Packet11EndGame extends Packet{
	
	private int winner;
	
	public Packet11EndGame(byte[] data) {
		super(11);
		winner = Integer.parseInt(read(data)[1]);
	}
	
	public Packet11EndGame(int i) {
		super(11);
		this.winner = i;
	}
	
	public byte[] getData() {
		return ("11#"+winner).getBytes();
	}
}
