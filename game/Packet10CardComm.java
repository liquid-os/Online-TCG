package org.author.game;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Packet10CardComm extends Packet{
	
	private String msg;
	
	public Packet10CardComm(byte[] data) {
		super(10);
		msg = read(data)[1];
	}
	
	public Packet10CardComm(String msg) {
		super(10);
		this.msg = msg;
	}
	
	public byte[] getData() {
		return ("10#"+msg).getBytes();
	}
}
