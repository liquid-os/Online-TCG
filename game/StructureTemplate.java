package org.author.game;

public class StructureTemplate extends UnitTemplate {

	public StructureTemplate(int id, String name, String art, int cost,
			int rank, int hp, int atk, int spd) {
		super(id, name, art, cost, rank, hp, atk, spd);
	}
	
	public StructureTemplate(int id, String name, String art, int cost,
			int rank, int hp, int atk, int spd, CardSet set) {
		super(id, name, art, cost, rank, hp, atk, spd, set);
	}

}
