package org.author.game;

import java.util.ArrayList;

public class CardBoardstate extends Card {

	public CardBoardstate(int id, String name, int cost, String art) {
		super(id, name, cost, art);
	}
	
	public void onEndTurn(ArrayList<Unit> units, int roomID){
		if(units != null){
			if(this == Card.stateHellfire){
				if(Bank.isServer()){
					for(Unit t : Bank.server.getUnits(roomID)){
						t.hurt(1, t);
					}
				}else{
					for(int i = 0; i < Display.currentScreen.objects.size(); ++i){
						Unit t = Display.currentScreen.objects.get(i);
						if(t!=null){
							t.hurt(1, t);
							Animation.createStaticAnimation(t, "INCINERATE", 2, 400);
						}
					}
				}
			}
	
			if(this == Card.stateHealingrain){
				if(Bank.isServer()){
					for(Unit t : Bank.server.getUnits(roomID)){
						t.heal(1);
					}
				}else{
					for(int i = 0; i < Display.currentScreen.objects.size(); ++i){
						Unit t = Display.currentScreen.objects.get(i);
						if(t!=null){
							Animation.createStaticAnimation(t, "POOL", 1, 800);
							t.heal(1);
						}
					}
				}
			}
			
			if(this == Card.stateSpirit){
				if(Bank.isServer()){
					for(PlayerMP p : Bank.server.getClients(roomID)){
						Bank.server.drawCard(p, -1, 1);
					}
				}
			}
			
			if(this == Card.stateRevolution){
				if(Bank.isServer()){
					for(Unit t : Bank.server.getUnits(roomID)){
						ArrayList<CardAbility> abs = new ArrayList<CardAbility>();
						for(CardAbility ca : CardAbility.all){
							if(ca!=null)abs.add(ca);
						}
						CardAbility ab = abs.get(Util.rand.nextInt(abs.size()));
						this.sendComm(t.GUID+","+ab.getId(), t.ownerID, t.GUID, t.posX, t.posY, roomID);
					}
					
					for(PlayerMP p : Bank.server.getClients(roomID)){
						Card c = Card.steamgolem;
						ArrayList<Card> avail = new ArrayList<Card>();
						for(Card card : Card.all){
							if(card!=null){
								if(card.getUnit() != null){
									if(card.getUnit().getFamily() == Family.mech)
									avail.add(card);
								}
							}					
						}
						c = avail.get(Util.rand.nextInt(avail.size()));
						Bank.server.drawCard(Bank.server.getClients(roomID).get(Bank.server.getClients(roomID).indexOf(p)), c.getId(), 1);
					}
				}
			}
			
			if(this == Card.stateArcaneSurge){
				if(Bank.isServer()){
					for(Unit t : Bank.server.getUnits(roomID)){
						t.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
						t.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
					}
				}else{
					for(int i = 0; i < Display.currentScreen.objects.size(); ++i){
						Unit t = Display.currentScreen.objects.get(i);
						if(t!=null){
							Animation.createStaticAnimation(t, "MANABURN", 1, 800);
							t.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
							t.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
						}
					}
				}
			}
			
			if(this == Card.stateBlizzard){
				if(Bank.isServer()){
					for(Unit t : Bank.server.getUnits(roomID)){
						t.addOrUpdateEffect(new Effect(EffectType.chill, 1, 1));
					}
				}else{
					for(int i = 0; i < Display.currentScreen.objects.size(); ++i){
						Unit t = Display.currentScreen.objects.get(i);
						if(t!=null){
							Animation.createStaticAnimation(t, "FREEZE", 3, 800);
							t.addOrUpdateEffect(new Effect(EffectType.chill, 1, 1));

						}
					}
				}
			}
		}
	}
	
	public void update(PanelBase screen){
		if(this != Card.stateNormal){
			String frameset = "FIRE";
			int chance = 100;
			int time = 700 + Util.rand.nextInt(350);
			int size = 32;
			if(this == Card.stateHellfire){
				frameset = "FIRE";
			}
			if(this == Card.stateBlizzard){
				frameset = "FREEZE";
				chance = 80;
			}
			if(this == Card.stateArcaneSurge){
				frameset = "MAGIC";
				if(Util.rand.nextInt(4) == 1)frameset = "RUNE";
			}
			if(this == Card.stateHealingrain){
				frameset = "BUBBLE";
				if(Util.rand.nextInt(4) == 1)frameset = "WATERBIRD";
			}
			if(this == Card.stateKholosian){
				frameset = "VOID";
			}
			if(this == Card.stateMalestrom){
				frameset = "THUNDER";
				if(Util.rand.nextInt(4) == 1)frameset = "POOL";
			}
			int per = 30;
			int dist = (Util.boardWidth * Grid.tileSize) / per;
			for(int i = 0; i < per; ++i){
				if(Util.rand.nextInt(chance) == 0){
					AnimatedParticle particle = new AnimatedParticle(Util.boardOffsetX + i * dist + Util.rand.nextInt(dist / 2) - Util.rand.nextInt(dist / 2), 0 + Util.rand.nextInt(Util.boardHeight), size, time + Util.rand.nextInt(time / 2) - Util.rand.nextInt(time / 2));
					particle.addFrameSet(frameset);
					particle.phys.motionX = (Util.rand.nextInt(20) - Util.rand.nextInt(20));
					particle.phys.motionY = -(Util.rand.nextInt(40));
					Display.currentScreen.particles.add(particle);
				}
			}
			for(int i = 0; i < per; ++i){
				if(Util.rand.nextInt(chance) == 0){
					AnimatedParticle particle = new AnimatedParticle(Util.boardOffsetX + i * dist + Util.rand.nextInt(dist / 2) - Util.rand.nextInt(dist / 2), Util.boardHeight * Grid.tileSize + Util.boardOffsetY + Util.rand.nextInt(Util.boardHeight), size, time + Util.rand.nextInt(time / 2) - Util.rand.nextInt(time / 2));
					particle.addFrameSet(frameset);
					particle.phys.motionX = (Util.rand.nextInt(20) - Util.rand.nextInt(20));
					particle.phys.motionY = (Util.rand.nextInt(40));
					Display.currentScreen.particles.add(particle);
				}
			}
			int perY = 20;
			int distY = (Util.boardHeight * Grid.tileSize) / perY;
			for(int i = 0; i < perY; ++i){
				if(Util.rand.nextInt(chance) == 0){
					AnimatedParticle particle = new AnimatedParticle(Util.rand.nextInt(Util.boardOffsetX), Util.boardOffsetY + i * distY, size, time + Util.rand.nextInt(time / 2) - Util.rand.nextInt(time / 2));
					particle.addFrameSet(frameset);
					particle.phys.motionX = (Util.rand.nextInt(40));
					particle.phys.motionY = (Util.rand.nextInt(20) - Util.rand.nextInt(20));
					Display.currentScreen.particles.add(particle);
				}
			}
			for(int i = 0; i < perY; ++i){
				if(Util.rand.nextInt(chance) == 0){
					AnimatedParticle particle = new AnimatedParticle(Util.rand.nextInt(Util.boardOffsetX) + Util.boardWidth * Grid.tileSize + Util.boardOffsetX, Util.boardOffsetY + i * distY, size, time + Util.rand.nextInt(time / 2) - Util.rand.nextInt(time / 2));
					particle.addFrameSet(frameset);
					particle.phys.motionX = -(Util.rand.nextInt(40));
					particle.phys.motionY = (Util.rand.nextInt(20) - Util.rand.nextInt(20));
					Display.currentScreen.particles.add(particle);
				}
			}
		}
	}
	
	public void onUnitPlayed(Unit unit, int roomID){
		if(this == Card.stateHellfire){
			Animation.createStaticAnimation(unit, "INCINERATE", 2, 1200);
			unit.hurt(4, unit);
		}
		
		if(this == Card.stateKholosian){
			Animation.createStaticAnimation(unit, "VOID", 3, 1200);
			if(unit.getTemplate().getFamily() != Family.demon && unit.getTemplate().getFamily() != Family.undead)
			unit.hurt(10, unit);
		}
	}
}
