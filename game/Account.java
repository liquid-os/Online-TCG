package org.author.game;

import java.util.ArrayList;

public class Account {
	
	private String username, password;
	private int accountID;
	private ArrayList<CardShell> collection = new ArrayList<CardShell>();
	
	public Account(int ID, String user, String pass){
		this.username = user;
		this.password = pass;
		this.accountID = ID;
		System.out.println("Loaded account "+user+".");
	}

}
