package org.author.game;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Packet06AttackUnit extends Packet{
	
	int guid, targetguid;
	
	public Packet06AttackUnit(byte[] data) {
		super(06);
		guid = Integer.parseInt(read(data)[1]);
		targetguid = Integer.parseInt(read(data)[2]);
	}
	
	public Packet06AttackUnit(int guid, int target) {
		super(06);
		this.guid = guid;
		this.targetguid = target;
	}
	
	public byte[] getData() {
		return ("06#"+guid+"#"+targetguid).getBytes();
	}
}
