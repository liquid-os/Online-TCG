package org.author.game;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Packet15SelectPick extends Packet{
	
	int card = 0;
	int source = -1;
	int sourceType = 0;
	
	public Packet15SelectPick(byte[] data) {
		super(15);
		card = Integer.parseInt(read(data)[1]);
		source = Integer.parseInt(read(data)[2]);
		sourceType = Integer.parseInt(read(data)[3]);
	}
	
	public Packet15SelectPick(int c, int src, int srcType) {
		super(15);
		this.card = c;
		this.source = src;
		this.sourceType = srcType;
	}
	
	public byte[] getData() {
		return ("15#"+card+"#"+source+"#"+sourceType).getBytes();
	}
}
