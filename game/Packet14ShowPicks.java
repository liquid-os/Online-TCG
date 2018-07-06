package org.author.game;

import java.util.ArrayList;

public class Packet14ShowPicks extends Packet{
	
	int owner;
	String msg = "";
	int source = -1;
	int sourceType = -1;
	
	public Packet14ShowPicks(byte[] data) {
		super(14);
		owner = Integer.parseInt(read(data)[1]);
		source = Integer.parseInt(read(data)[2]);
		sourceType = Integer.parseInt(read(data)[3]);
		String msg = read(data)[4];
	}
	
	public Packet14ShowPicks(int o, int source, int sourceType, ArrayList<Card> cards) {
		super(14);
		this.owner = o;
		for(Card c : cards){
			msg = msg + (c.getId()+"x");
		}
		this.source = source;
		this.sourceType = sourceType;
	}

	public byte[] getData() {
		String app = "";
		return ("14#"+owner+"#"+source+"#"+sourceType+"#"+msg).getBytes();
	}
}
