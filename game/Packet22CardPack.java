package org.author.game;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Packet22CardPack extends Packet{
	
	int c1, c2, c3, c4, c5;
	
	public Packet22CardPack(byte[] data) {
		super(22);
		c1 = Integer.parseInt(read(data)[1]);
		c2 = Integer.parseInt(read(data)[2]);
		c3 = Integer.parseInt(read(data)[3]);
		c4 = Integer.parseInt(read(data)[4]);
		c5 = Integer.parseInt(read(data)[5]);
	}
	
	public Packet22CardPack(int c1, int c2, int c3, int c4, int c5) {
		super(22);
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
		this.c4 = c4;
		this.c5 = c5;
	}
	
	public byte[] getData() {
		return ("22#"+c1+"#"+c2+"#"+c3+"#"+c4+"#"+c5).getBytes();
	}
}
