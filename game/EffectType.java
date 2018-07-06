package org.author.game;

import java.awt.Graphics;
import java.awt.Image;

public class EffectType {
	
	private int id = 0, duration = 0;
	private String name;
	private Image icon = Bank.ball;
	
	static EffectType[] all = new EffectType[100];
	
	public EffectType(int id, String name){
		this.id = id;
		this.name = name;
		all[id] = this;
	}
	
	public static final EffectType attack = new EffectType(0, "Attack Bonus");
	public static final EffectType health = new EffectType(1, "Health Bonus");
	public static final EffectType speed = new EffectType(2, "Speed Bonus");
	public static final EffectType chill = new EffectType(3, "Chilled");
	public static final EffectType camofalgue = new EffectType(4, "Camoflague");
	public static final EffectType atrophy = new EffectType(5, "Atrophy");
	public static final EffectType protection = new EffectType(6, "Protection");
	public static final EffectType build = new EffectType(7, "Building In Progress");
	public static final EffectType cowardice = new EffectType(8, "Cowardice");
	public static final EffectType stunned = new EffectType(9, "Stunned");

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
