package org.author.game;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Packet04ChangeTurn extends Packet{
	
	int setTurn = 0, turn = 0;
	
	public Packet04ChangeTurn(byte[] data) {
		super(04);
		setTurn = Integer.parseInt(read(data)[1]);
		turn = Integer.parseInt(read(data)[2]);
	}
	
	public Packet04ChangeTurn(int setTurn, int turn) {
		super(04);
		this.setTurn = setTurn;
		this.turn = turn;
	}
	
	public byte[] getData() {
		return ("04#"+setTurn+"#"+turn).getBytes();
	}
}
