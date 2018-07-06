package org.author.game;

import java.awt.Image;

public class Family {
	
	private String name;
	private Image icon;
	private byte id;
	
	public Family(int id, String name, Image icon){
		this.id = (byte)id;
		this.setName(name);
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static final Family none = new Family(0, "None", Bank.ball);
	public static final Family mech = new Family(1, "Mechanical", Bank.ball);
	public static final Family kratt = new Family(2, "Kratt", Bank.ball);
	public static final Family beast = new Family(3, "Beast", Bank.ball);
	public static final Family demon = new Family(4, "Demon", Bank.ball);
	public static final Family insectoid = new Family(5, "Insectoid", Bank.ball);
	public static final Family undead = new Family(6, "Undead", Bank.ball);
	public static final Family werewolf = new Family(7, "Grinn", Bank.ball);
	public static final Family eldritch = new Family(8, "Eldritch", Bank.ball);
	public static final Family elemental = new Family(9, "Elemental", Bank.ball);
}
