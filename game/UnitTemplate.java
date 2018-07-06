package org.author.game;

import java.awt.Image;
import java.util.ArrayList;

public class UnitTemplate extends Card{
	
	private int health, attack, speed;
	private byte rank = 0;
	private String name, particleType = "fire";
	static final int RANK_COMMON = 0, RANK_UNCOMMON = 1, RANK_MYTHIC = 2, RANK_REGAL = 3, RANK_COMMANDER = 4;
	private ArrayList<CardAbility> abilities = new ArrayList<CardAbility>();
	private Family family = Family.none;
	private String txtPlay = null, txtAtk = null, txtDeath = null;
	
	public UnitTemplate(int id, String name, String art, int cost, int rank, int hp, int atk, int spd){
		super(id, name, (rank == RANK_COMMANDER ? 0 : cost), art);
		this.setHealth(hp);
		this.setAttack(atk);
		this.setSpeed(spd);
		this.setRank((byte) rank);
		this.addAbility(CardAbility.attack);
		this.addAbility(CardAbility.move);
		if(rank == RANK_COMMANDER){
			this.setListable(false);
		}
		//this.generateAssumedCost();
	}
	
	public UnitTemplate(int id, String name, String art, int cost, int rank, int hp, int atk, int spd, CardSet set){
		super(id, name, (rank == RANK_COMMANDER ? 0 : cost), art, set);
		this.setHealth(hp);
		this.setAttack(atk);
		this.setSpeed(spd);
		this.setRank((byte) rank);
		this.addAbility(CardAbility.attack);
		this.addAbility(CardAbility.move);
		if(rank == RANK_COMMANDER){
			this.setListable(false);
		}
		//this.generateAssumedCost();
	}
	
	public void generateAssumedCost(){
		int c = 0;
		c += this.getAttack() / 2.5d;
		c += this.getHealth() / 3;
		c += this.getSpeed() / 4;
		c += (this.getAbilities().size() - 2) * 1.5d;
		c -= Math.abs(this.getHealth() - this.getAttack()) / 2;
		c += (this.getRank() > RANK_UNCOMMON ? this.getRank() - 1 : 0);
		c += (this.getFamily() == Family.none ? 0 : 1);
		this.setCost(c * 2);
	}
	
	public UnitTemplate addAbility(CardAbility c){
		this.getAbilities().add(c);
		return this;
	}
	public UnitTemplate setParticleType(String s){
		this.particleType = s;
		return this;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public byte getRank() {
		return rank;
	}

	public void setRank(byte rank) {
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<CardAbility> getAbilities() {
		return abilities;
	}

	public void setAbilities(ArrayList<CardAbility> abilities) {
		this.abilities = abilities;
	}

	public String getParticleType() {
		return particleType;
	}
	
	public UnitTemplate setFamily(Family f){
		this.family = f;
		return this;
	}

	public boolean shouldCount() {
		if(rank == RANK_COMMANDER || this == tent || this == outpost || this == stronghold || this.abilities.contains(CardAbility.minion))return false;
		return true;
	}

	public Family getFamily() {
		return family;
	}

	public String getTxtPlay() {
		return txtPlay;
	}

	public void setTxtPlay(String txtPlay) {
		this.txtPlay = txtPlay;
	}

	public String getTxtDeath() {
		return txtDeath;
	}

	public void setTxtDeath(String txtDeath) {
		this.txtDeath = txtDeath;
	}

	public String getTxtAtk() {
		return txtAtk;
	}

	public void setTxtAtk(String txtAtk) {
		this.txtAtk = txtAtk;
	}
	
	public UnitTemplate setShouts(String play, String death, String action){
		this.txtPlay = play;
		this.txtDeath = death;
		this.txtAtk = action;
		return this;
	}
}
