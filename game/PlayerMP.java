package org.author.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;

public class PlayerMP{
	
	InetAddress ip;
	int port = -1;
	int commanderID = 0, playerID = 0, gold = 1, goldRed = 0, goldBlue = 0, goldGreen = 0, baseGold = 1, special = 0, resource = 0;
	String name;
	int currentRoom = -1;
	private int coins = 0;
	AIController controller = null;
	
	private byte[] collection = new byte[Card.all.length]; 
	
	ArrayList<CardShell> hand = new ArrayList<CardShell>();
	ArrayList<CardShell> deck = new ArrayList<CardShell>();
	Color color = Color.RED;

	public PlayerMP(int comm, String user, InetAddress ip, int port) {
		this.commanderID = comm;
		this.name = user;
		this.ip = ip;
		this.port = port;
	}
	
	public Image getUserIcon(){
		return Bank.icon;
	}
	
	public Unit getHero(){
		return Bank.server.getCommander(this);
	}
	
	public ArrayList<Unit> getGameUnits(){
		return Bank.server.getRoom(currentRoom).units;
	}
	
	public ArrayList<Unit> getPlayerUnits(){
		ArrayList<Unit> old = Bank.server.getRoom(currentRoom).units;
		ArrayList<Unit> units = Bank.server.getRoom(currentRoom).units;
		for(Unit u : old){
			if(u.ownerID != playerID){
				units.remove(u);
			}
		}
		return units;
	}
	
	public void readCollection(){
		collection = new byte[Card.all.length]; 
		File f = new File(Bank.accFile+"/"+name+"_collection.userdata");
		String s = Bank.readAll(f);
		Bank.getServer().getLog().add("Loaded collection for "+name+": "+s);
		if(!s.isEmpty()){
			String[] bits = s.split("#");
			if(bits.length > 0)
			for(String str : bits){
				collection[Integer.parseInt(str.trim())]++;
			}
		}
	}
	
	public byte[] getCollection(){
		return collection;
	}
	
	public byte[] addToCollection(Card c, int count){
		if(collection[c.getId()] + count > 10){
			count = 10 - collection[c.getId()];
		}
		collection[c.getId()] += count;
		this.saveCollection();
		return collection;
	}
	
	public void saveCollection(){
		String finalData = "";
		for(int i = 0; i < collection.length; ++i){
			if(collection[i] > 0){
				finalData+=(i+"#");
			}
		}
		Bank.setContentsRawdir(Bank.accFile+"/"+name+"_collection.userdata", finalData);
	}
	
	public byte[] addToCollection(int c, int count){
		if(collection[c] + count > 10){
			count = 10 - collection[c];
		}
		collection[c] += count;
		this.saveCollection();
		return collection;
	}

	public byte[] addToCollectionRaw(int c, int count) {
		if(collection[c] + count > 10){
			count = 10 - collection[c];
		}
		collection[c] += count;
		return collection;
	}

	public void flushCollection() {
		byte[] newcollection = Arrays.copyOf(collection, collection.length);
		for(int i = 0; i < collection.length; ++i){
			if(collection[i] > 1){
				Card card = Card.all[i];
				if(card != null){
					int dif = collection[i] - 1;
					int dustValue = Util.getDustValue(card) * dif;
					setCoins(getCoins() + dustValue);
					Bank.getServer().getLog().add("AUTO-DUSTING: "+card.getName()+" x "+dif+" ("+dustValue+")");
					newcollection[i] = 1;
				}
			}
		}
		Bank.getServer().modifyAccountValue(name, 5, getCoins()+"");
		this.collection = newcollection;
		this.saveCollection();
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public boolean canAfford(int cardID) {
		Card card = Card.all[cardID];
		boolean ret = true;
		if(this.goldRed < card.getRedCost())ret = false;
		if(this.goldGreen < card.getGreenCost())ret = false;
		if(this.goldBlue < card.getBlueCost())ret = false;
		if(this.gold < card.getCost())ret = false;
		return ret;
	}
	
	public void spend(int cardID){
		Card card = Card.all[cardID];
		this.goldRed-=card.getRedCost();
		this.goldBlue-=card.getBlueCost();
		this.goldGreen-=card.getGreenCost();
		this.gold-=card.getCost();
	}
}
