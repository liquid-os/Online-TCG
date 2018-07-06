package org.author.game;

import java.util.ArrayList;

public class CardEquipment extends Card {
	
	private ArrayList<CardAbility> abilities = new ArrayList<CardAbility>();
	private ArrayList<Effect> effects = new ArrayList<Effect>();
	private byte durability = 1;

	public byte getDurability() {
		return durability;
	}

	public void setDurability(byte durability) {
		this.durability = durability;
	}

	public CardEquipment(int id, String name, int cost, int dura, String art) {
		super(id, name, cost, art);
		this.durability = (byte) dura;
		getAbilities().add(CardAbility.unequip);
	}
	
	public CardEquipment(int id, String name, int cost, int dura, String art, CardSet set) {
		super(id, name, cost, art, set);
		this.durability = (byte) dura;
		getAbilities().add(CardAbility.unequip);
	}

	public void equipUnit(Unit u, boolean on){
		u.setEquipment(this);
		u.setEquipmentDurability(durability);
		u.setEquipmentArt(Filestream.getCardImage(this.getArt()));
		
		//UNEQUIP THE UNIT
		if(!on){
			u.setEquipmentDurability((byte) 0);
			u.setEquipment(null);
			u.setEquipmentArt(null);
		}
	}
	
	public CardEquipment addAbility(CardAbility ca){
		this.getAbilities().add(ca);
		return this;
	}
	
	public CardEquipment addEffect(Effect e){
		this.effects.add(e);
		return this;
	}

	public ArrayList<Effect> getEffects() {
		return effects;
	}

	public void setEffects(ArrayList<Effect> effects) {
		this.effects = effects;
	}

	public ArrayList<CardAbility> getAbilities() {
		return abilities;
	}

	public void setAbilities(ArrayList<CardAbility> abilities) {
		this.abilities = abilities;
	}
}
