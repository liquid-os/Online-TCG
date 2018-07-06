package org.author.game;

import java.awt.Image;
import java.util.ArrayList;

public class CardSet {
	
	private String name = null;
	private Image icon = null;
	private int version = 0;
	
	static CardSet[] all = new CardSet[20];
	
	public CardSet(String name, Image icon, int ver){
		this.setName(name);
		this.setIcon(icon);
		this.setVersion(ver);
		//all[version == -1 ? all.length : version] = this;
	}

	public static final CardSet SET_CLASSIC = new CardSet("Classic", Bank.setClassic, -1);
	public static final CardSet SET_WAR = new CardSet("War Effort", Bank.setWar, 0);
	public static final CardSet SET_SEA = new CardSet("Dark Tides", Bank.setSea, 1);
	public static final CardSet SET_JUNGLE = new CardSet("Jungle Juice", Bank.setJungle, 2);
	public static final CardSet SET_TRIBELANDS = new CardSet("Trouble in the Tribelands", Bank.setOrklad, 3);
	public static final CardSet SET_NIGHT = new CardSet("Midnight Madness", Bank.setWerewolf, 4);
	public static final CardSet SET_IVORGLEN = new CardSet("Terrors of Ivorglen", Bank.setUndead, 5);
	public static final CardSet SET_EXPEDITION = new CardSet("The Grand Expedition", Bank.setExplorer, 6);
	public static final CardSet SET_ANURIM = new CardSet("The Southern Sands", Bank.setDesert, 7);
	
	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
