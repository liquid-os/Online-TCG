package org.author.game;

import java.util.ArrayList;

public class GameAI {
	
	public Unit getLargestThreat(){
		return null;
	}
	
	public int getTotalDamagePossible(ArrayList<Unit> units, int ownerID){
		int damage = 0;
		Unit target = null;
		for(Unit unit : units){
			if(unit.ownerID == ownerID){
				int effectiveEnergy = unit.getEnergy();
				int xDif = Math.abs(unit.posX - target.posX);
				int yDif = Math.abs(unit.posY - target.posY);
				int totalDistance = xDif + yDif;
				for(CardAbility a : unit.getAbilities()){
					boolean canCast = false;
					if(a.getRange() > totalDistance){
						if(unit.getSpeed() >= (a.getRange() - totalDistance)){
							if(effectiveEnergy >= 1){
								canCast = true;
							}
						}
					}else{
						canCast = true;
					}
					if(canCast){
						
					}
				}
				if(totalDistance-1 <= unit.getSpeed()){
					if(effectiveEnergy >= 2){
						effectiveEnergy -= 2;
						damage += unit.getAttack();
					}else if(effectiveEnergy >= 1 && totalDistance <= 2){
						effectiveEnergy -= 1;
						damage += unit.getAttack();
					}
					
				}
			}
		}
		return damage;
	}
}
