package org.author.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

public class Unit{

	private boolean useGravity = false;
	private int health, healthMax = 0, attack = 0, energy = 0, roomID = -1;
	private Image img = null;
	private CardEquipment equipment = null;
	private Image equipmentArt = null;
	private byte equipmentDurability = 1;
	private Unit protector = null;
	
	public CardEquipment getEquipment() {
		return equipment;
	}

	public void setEquipment(CardEquipment equipment) {
		this.equipment = equipment;
	}

	public int getAttack() {
		int atk = this.attack;
		if(this.abilities.contains(CardAbility.charger))return (int) this.getSpeed();
		atk+=getTotalEffectValue(EffectType.attack);
		if(this.abilities.contains(CardAbility.wintersembrace))
			atk+=getTotalEffectValue(EffectType.chill)*2;
		if(this.abilities.contains(CardAbility.aquastrike) && this.getTileAtPosition(posX, posY) == GridBlock.water)
			atk*=2;
		return atk;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getEnergy() {
		return energy;
	}
	
	public int getResourceForCostType(Byte costType) {
		if(Bank.isClient()){
			return (costType == CardAbility.COST_TYPE_MANA ? Util.connectedPlayers.get(this.ownerID).gold : costType == CardAbility.COST_TYPE_RESOURCE ? Util.connectedPlayers.get(this.ownerID).resource : costType == CardAbility.COST_TYPE_MANA ? Util.connectedPlayers.get(this.ownerID).special : this.getEnergy());	
		}
		return (costType == CardAbility.COST_TYPE_MANA ? Bank.server.getClients(roomID).get(this.ownerID).gold : costType == CardAbility.COST_TYPE_RESOURCE ? Bank.server.getClients(roomID).get(this.ownerID).resource : costType == CardAbility.COST_TYPE_MANA ? Bank.server.getClients(roomID).get(this.ownerID).special : this.getEnergy());
	}

	public void setEnergy1(int energy) {
		this.energy = energy;
	}

	private float speed = 3f;
	private boolean solid = true, canHarm = true;
	private UnitTemplate template = null;
	boolean flagRemove = false;
	protected byte lastDir = 0;
	protected int posX = 0, posY = 0;
	protected int GUID = 0, ownerID = -1;
	private ArrayList<Effect> effects = new ArrayList<Effect>();
	private ArrayList<CardAbility> abilities = new ArrayList<CardAbility>();
	private Byte[] costTypeOverride = new Byte[]{};
	private ArrayList<CardAbility> usedAbilities = new ArrayList<CardAbility>();
	private Animation moveAnim = null;
	
	private Image interactIcon = null;
	private String interactText = null;
	
	boolean hasMoved = false, hasAttacked = false;
	private ArrayList<CardAbility> abilityQueue = new ArrayList<CardAbility>();
	
	public Unit(UnitTemplate t, int x, int y, int roomID){
		this.setTemplate(t);
		this.health = t.getHealth();
		this.healthMax = health;
		this.speed = t.getSpeed();
		this.attack = t.getAttack();
		this.setPosX(x);
		this.setPosY(y);
		this.setAbilities((ArrayList<CardAbility>) t.getAbilities().clone());
		setCostTypeOverride(new Byte[t.getAbilities().size() + 20]);
		for(int i = 0; i < 20; ++i){
			costTypeOverride[i] = 0;
		}
		for(CardAbility ca : abilities){
			getCostTypeOverride()[abilities.indexOf(ca)] = ca.getCostType();
		}
		this.setEnergy1(0);
		this.img = Filestream.getCardImage(t.getArt());
		this.roomID = roomID;
	}
	
	public Unit setCostTypeOverride(CardAbility ab, Byte override){
		costTypeOverride[this.abilities.indexOf(ab)] = override;
		return this;
	}
	
	public Unit setCostTypeOverride(Integer abilityIndex, Byte override){
		costTypeOverride[abilityIndex] = override;
		return this;
	}
	
	public Unit setAllCostTypeOverrides(Byte override){
		for (int i = 0; i < getCostTypeOverride().length; ++i){
			getCostTypeOverride()[i] = override;
		}
		return this;
	}
	
	public void say(String txt){
		Animation a = new Animation(Util.boardOffsetX+Grid.tileSize*posX+Grid.tileSize/2, Util.boardOffsetY+Grid.tileSize*posY+Grid.tileSize/2, 1200, Animation.TAG_SPEECH);
		a.setText(txt);
		Display.currentScreen.particles.add(a);
	}
	
	public int getTotalEffectValue(EffectType t){
		int ret = 0;
		for(Effect e : effects){
			if(e.getEffectType() == t)ret+=e.getVal();
		}
		if(equipment!=null){
			for(Effect e : equipment.getEffects()){
				if(e.getEffectType() == t)ret+=e.getVal();
			}
		}
		return ret;
	}
	
	public void initHealth(int hp){
		this.healthMax = hp;
		this.health = hp;
	}
	
	public void setHealth(int hp){
		if(hp < this.getHealthMax())
		this.health = hp;
		else this.health = healthMax;
	}
	
	public void kill() {
		this.hurt(this.getHealth() * 10, this);
		//this.flagRemove = true;
	}
	
	public final void updateUnit(){
		if(this.getHealth()<=0){
			//this.onKilled(null);
		}
	}
	

	public void callMethod(String mn){
		Method method;
		try {
		  method = this.getClass().getMethod(mn);
		  method.invoke(this);
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
		} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}
	
	public boolean isRemoving() {
		return flagRemove;
	}
	
	public Unit getProtector(){
		return protector;
	}

	public void hurt(int d, Unit hitter) {
		if(!Util.isGameOver(roomID) &! this.flagRemove){
			if(this.getProtector() != null && (this.getProtector() != this && this.getProtector().getHealth() > 0 && Util.distance(getProtector().getTilePosition(), this.getTilePosition()) <= 2)){
				Animation.createStaticAnimation(this, "SHIELD", 2, 500);
				getProtector().hurt(d, hitter);
				/*if(getProtector().getHealth() <= 0 && this.getAbilities().contains(CardAbility.legacy)){
					this.addOrUpdateEffect(new Effect(EffectType.cowardice, 1, 999));
					this.setTemplate((UnitTemplate) UnitTemplate.pendleton1);
					this.resummon();
					this.say("What fate is this?");
				}*/
			}else{
				int dmg = d;
				dmg += PersistentValue.getPersistentValueByTag("GAME_DMGINCR", roomID).nVal;
				for(PersistentValue v : PersistentValue.getVars(roomID)){
					if(v.tag.equalsIgnoreCase("GAME_DMGINCR") &! v.eternal){
						v.flagRemove = true;
					}
				}
				if(this.getAbilities().contains(CardAbility.warding)){
					dmg/=2;
					if(!Bank.isServer())
					Animation.createStaticAnimation(this, "SHIELD", 2, 900);
				}
				if(this.getAbilities().contains(CardAbility.evasive) && this.getTileAtPosition(posX, posY) == GridBlock.water){
					dmg/=2;
					if(!Bank.isServer()){
						Animation.createStaticAnimation(this, "SHIELD", 2, 900);
						Animation.createStaticAnimation(this, "SPLASH", 2, 600);
					}
				}
				if(hitter!=null && hitter != this){
					if(hitter.getAbilities().contains(CardAbility.assassination) &! this.isStructure() &! this.getIsCommander()){
						if(dmg < this.health)dmg = this.health;
						if(!Bank.isServer()){
							Util.drawParticleLine(hitter.getPoint(), this.getPoint(), 10, true, "BURST");
							Animation.createStaticAnimation(this, "BURST", 2, 800);
							Animation.createStaticAnimation(this, "SLASH", 2, 800);
						}
					}
					ArrayList<Unit> units = null;
					if(Bank.isServer())units = Bank.server.getUnits(roomID); else units = ((PanelBoard)Display.currentScreen).objects;
					for(Unit u : units){
						if(u.ownerID == hitter.ownerID && u.getAbilities().contains(CardAbility.flames)){
							dmg++;
							if(!Bank.isServer()){
								Util.drawParticleLine(u.getPoint(), hitter.getPoint(), 20, true, "FIRE");
								Util.drawParticleLine(hitter.getPoint(), this.getPoint(), 20, true, "FIRE");
							}
						}
					}
				}
				Display.currentScreen.particles.add(new Animation(this.getPoint().x, this.getPoint().y, 2000, Animation.TAG_TEXT).setText((-dmg)+""));
				this.health-=dmg;
				if(this.getHealth()<=0)this.onKilled(hitter);
				if(this.getAbilities().contains(CardAbility.goldMine) && hitter != this){
					if(Bank.isServer()){
						if(hitter != null){
							if(hitter.ownerID <= Bank.server.getClients(roomID).size()){
								if(Util.distance(hitter.getTilePosition(), this.getTilePosition()) <= 2)
								Bank.server.drawCard(Bank.server.getClients(roomID).get(hitter.ownerID), Card.goldShard.getId(), 1);
							}
						}
					}else{
						Animation.createStaticAnimation(this, "FLASH", 2, 800);
					}
				}
				if(this.getAbilities().contains(CardAbility.tree) && hitter != this){
					if(Bank.isServer()){
						if(hitter != null){
							if(hitter.ownerID <= Bank.server.getClients(roomID).size()){
								if(Util.distance(hitter.getTilePosition(), this.getTilePosition()) <= 2)
								Bank.server.drawCard(Bank.server.getClients(roomID).get(hitter.ownerID), Card.pileWood.getId(), 1);
							}
						}
					}else{
						Animation.createStaticAnimation(this, "LEAVES", 2, 1200);
					}
				}
				if(hitter != null){
					if(hitter.getAbilities().contains(CardAbility.meatsaw)){
						if(Bank.isServer())
							Bank.server.drawCard(Bank.server.getClients(roomID).get(hitter.ownerID), Card.fleshpile.getId(), 1);
						else 
							Animation.createStaticAnimation(hitter, "BLOOD", 2.5f, 750);
					}
				}
				if(equipment!=null){
					this.equipmentDurability--;
					if(this.equipmentDurability<=0){
						this.equipment.equipUnit(this, false);
					}
				}
				Animation.createStaticAnimation(this, "BLOOD", 2, 500);
			}
		}
	}
	
	public void onKilled(Unit killer){
		if(!this.flagRemove){
			onkilledByUnit(killer);
			this.flagRemove = true;
			PersistentValue.addPersistentValue("TURN_DIED", 1, roomID);
			if(Bank.server!=null){
				if(killer!=null){
					if(this.getAbilities().contains(CardAbility.lootfilled)){
						int r = Util.rand.nextInt(5);
						Card c = (r == 0 ? Card.healthpotion : r == 1 ? Card.energypotion : r == 2 ? Card.weapon : r == 3 ? Card.bluepotion : Card.mapTreasure);
						if(killer.ownerID <= Bank.server.getClients(roomID).size())
						Bank.server.drawCard(Bank.server.getClients(roomID).get(killer.ownerID), c.getId(), 1);
					}
				}
				for(int i = 0; i < Bank.server.getUnits(roomID).size(); ++i){
					Unit u = Bank.server.getUnits(roomID).get(i);
					if(u!=null){
						for(CardAbility ab : u.getAbilities()){
							if(!u.flagRemove || u == this)
							ab.onUnitDied(u, this, roomID);
						}
						if(u.getProtector() == this)u.protector = null;
					}
				}
			}
			if(Bank.isClient()){
				Animation a = new Animation(Util.boardOffsetX+Grid.tileSize*posX+Grid.tileSize/2, Util.boardOffsetY+Grid.tileSize*posY+Grid.tileSize/2, 1200, Animation.TAG_SPEECH);
				a.setText(((UnitTemplate)this.getTemplate()).getTxtDeath());
				Display.currentScreen.particles.add(a);
				for(int i = 0; i < Display.currentScreen.objects.size(); ++i){
					Unit u = Display.currentScreen.objects.get(i);
					for(CardAbility ab : u.getAbilities()){
						ab.onUnitDied(u, this, roomID);
					}
					if(u.getProtector() == this)u.protector = null;
				}
			}
			if(Display.currentScreen instanceof PanelBoard){
				if(((PanelBoard)Display.currentScreen).unitClickedGUID == GUID){
					((PanelBoard)Display.currentScreen).unitClickedGUID = -1;
				}
			}
			
		}
		if((this.getIsCommander() || this.isBase()) && Bank.isServer()){
			Bank.server.getRoom(roomID).endGame((ownerID == 0 ? 1 : 0));
		}
		//Util.resolveUnits();
	}
	
	public boolean isBase(){
		return template == Card.tent || template == Card.outpost || template == Card.stronghold;
	}
	
	public boolean canEnd(){
		return (Display.currentScreen.getCommanderForPlayer(ownerID, roomID) == null || Display.currentScreen.getBaseForPlayer(ownerID, roomID) == null);
	}
	
	public void onkilledByUnit(Unit u){
		if(u!=null)
		if(u.getAbilities().contains(CardAbility.murkblade)){
			if(Bank.isServer()){
				Bank.server.addUnit(Card.tree.getUnit(), posX, posY, 999, roomID);
			}else{
				Animation.createStaticAnimation(u, "BLOOM", 2, 650);
			}
		}
	}

	public boolean useGravity() {
		return useGravity;
	}

	public void setUseGravity(boolean useGravity) {
		this.useGravity = useGravity;
	}

	public boolean isCanHarm() {
		return canHarm;
	}

	public void setCanHarm(boolean canHarm) {
		this.canHarm = canHarm;
	}

	public int getHealthMax() {
		int add = 0;
		add+=getTotalEffectValue(EffectType.health);
		if(this.abilities.contains(CardAbility.wintersembrace))
		add+=getTotalEffectValue(EffectType.chill)*2;
		return healthMax+add;	
	}

	public void setHealthMax(int healthMax) {
		this.healthMax = healthMax;
	}

	public int getHealth() {
		int add = 0;
		add+=getTotalEffectValue(EffectType.health);
		if(this.abilities.contains(CardAbility.wintersembrace))
		add+=getTotalEffectValue(EffectType.chill)*2;
		return health+add;	
	}
	
	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	public void originDraw(Graphics g, int x, int y){
		if(!this.isRemoving()){
			draw(g,x,y);
			if(new Rectangle(posX-Display.cam.x, posY-Display.cam.y, Grid.tileSize, Grid.tileSize).contains(Display.currentScreen.mousePoint)){
				g.setColor(Color.WHITE);
				g.setFont(Util.spellDesc);
				if(this.getInteractIcon()!=null){
					g.drawImage(getInteractIcon(), Display.currentScreen.mousePoint.x+16, Display.currentScreen.mousePoint.y-42, 32, 32, null);
					if(this.getInteractText()!=null){
						g.drawString(this.getInteractText(), Display.currentScreen.mousePoint.x+54, Display.currentScreen.mousePoint.y-20);
					}
				}else{
					if(this.interactText!=null)
					g.drawString(this.getInteractText(), Display.currentScreen.mousePoint.x+20, Display.currentScreen.mousePoint.y-20);
				}
			}
		}
	}
	
	public ArrayList<Point> getPath(int x, int y){
		ArrayList<Point> tiles = new ArrayList<Point>();
		
		return tiles;
	}

	public void draw(Graphics g, int x, int y) {
		if(getMoveAnim()!=null){
			getMoveAnim().draw(g, 0, 0);
			moveAnim.updateBase();
			if(getMoveAnim().isRemove())setMoveAnim(null);
		}else{
			int s = Grid.tileSize;
			if(!this.isStructure() && this.isPlayerUnit())
			g.drawImage(img, x, y - Util.breather, Grid.tileSize, Grid.tileSize + Util.breather, null);
			else g.drawImage(img, x, y, Grid.tileSize, Grid.tileSize, null);
			int ballsize = s/8*3;
			g.setColor(Color.BLACK);
			g.drawImage(Bank.iconAttack, x, y+s-ballsize, ballsize, ballsize, null);
			g.drawOval(x, y+s-ballsize, ballsize, ballsize);
			g.drawImage(Bank.iconHealth, x+s-ballsize, y+s-ballsize, ballsize, ballsize, null);
			g.drawOval(x+s-ballsize, y+s-ballsize, ballsize, ballsize);
			
			g.setFont(new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, ballsize/3*2));
			g.setColor(this.getHealth() < this.getHealthMax() ? Color.RED : (this.getHealth() > template.getHealth() ? Color.GREEN : Color.BLACK));
			g.drawString(""+this.getHealth(), x+s-ballsize/2-g.getFontMetrics().stringWidth(""+health)/2, y+s-ballsize/4);
			g.setColor(this.getAttack() > template.getAttack() ? Color.GREEN : (Color.BLACK));
			g.drawString(""+this.getAttack(), x+ballsize/2-g.getFontMetrics().stringWidth(""+attack)/2, y+s-ballsize/4);
			
			if(getTemplate().getRank() == UnitTemplate.RANK_COMMANDER){
				g.drawImage(Bank.flamehelm, x+Grid.tileSize/4, y-Grid.tileSize/4, Grid.tileSize/2, Grid.tileSize/2, null);
			}
			
			if(this.ownerID!=Util.clientID && ownerID <= 10 && ownerID >= 0){
				g.setColor(Util.transparent_red);
				g.fillRect(x, y, Grid.tileSize, Grid.tileSize);		
			}else{
				if(Util.turn == Util.clientID){
					if(!this.hasAttacked && energy >= 1 &! this.isStructure()){
						g.drawImage(Bank.flamering, x, y+s-ballsize, ballsize, ballsize, null);
					}
					if(!this.hasMoved && energy >= 1 &! this.isStructure()){
						int rs = (int) (Grid.tileSize * 1.5);
						g.drawImage(Bank.holynova, x+Grid.tileSize/2-rs/2, y+Grid.tileSize/2-rs/2, rs, rs, null);
					}
				}
			}
			if(hasEffect(EffectType.chill)){
				g.setColor(new Color(0, 80, 255, 100));
				g.fillRect(x, y, Grid.tileSize, Grid.tileSize);
			}
			if(this.ownerID == Util.clientID && Util.turn != Util.clientID){
				g.setColor(Util.transparent);
				g.fillRect(x, y, Grid.tileSize, Grid.tileSize);		
			}
			if(getProtector() != null && Util.distance(getProtector().getTilePosition(), this.getTilePosition()) <= 2){
				g.drawImage(Bank.protection, x, y, Grid.tileSize, Grid.tileSize, null);
				g.setColor(Color.YELLOW);
				g.drawLine(getProtector().getPoint().x, getProtector().getPoint().y, this.getPoint().x, this.getPoint().y);
				g.drawRect(x - 1, y - 1, Grid.tileSize + 2, Grid.tileSize + 2);
				g.drawRect(getProtector().getPoint().x - 1 - Grid.tileSize / 2, getProtector().getPoint().y - 1 - Grid.tileSize / 2, Grid.tileSize + 2, Grid.tileSize + 2);
			}
			if(this.isStunned())
			g.drawImage(Bank.stun, x, y - Grid.tileSize / 4, Grid.tileSize, Grid.tileSize / 2, null);
		}
	}
	
	public boolean isStunned(){
		return this.getTotalEffectValue(EffectType.stunned) > 0;
	}
	
	public void drawTooltip(Graphics g, int bx, int by, int w, int h) {
		int x = bx, y = by;
		int borderCount = 5;
		int borderCountH = (int) (5 * 1.5);
		int borderW = w / borderCount, borderH = borderW / 3 * 2;
		int borderWY = h / borderCountH, borderHY = borderWY / 3 * 2;
		String name = getTemplate().getName();
		
		g.drawImage(img, x, y+h/5+borderH/2, w, w, null);
		g.drawImage(Bank.timebar, x, y+h/3*2, w, h/3, null);
		g.drawImage(Bank.button, x, y, w, h/5, null);
		for(int i = 0; i < borderCount; ++i){
			g.drawImage(Bank.cardBorderTop, x + i * borderW, y, borderW, borderH, null);
			g.drawImage(Bank.cardBorderBottom, x + i * borderW, y+h-borderH, borderW, borderH, null);
			
			g.drawImage(Bank.cardBorderTop, x + i * borderW, y+h/3*2, borderW, borderH / 3 * 2, null);
			g.drawImage(Bank.cardBorderBottom, x + i * borderW, y+h/5, borderW, borderH / 3 * 2, null);
		}
		for(int i = 0; i < borderCountH; ++i){
			g.drawImage(Bank.cardBorderLeft, x, y+i*borderWY, borderHY / 3 * 2, borderWY, null);
			g.drawImage(Bank.cardBorderRight, x+w-(borderHY / 3 * 2), y+i*borderWY, borderHY / 3 * 2, borderWY, null);
		}
		g.drawImage(Bank.cornerTopleft, x, y, borderW, borderW, null);
		g.drawImage(Bank.cornerTopright, x+w-borderW, y, borderW, borderW, null);
		g.drawImage(Bank.cornerBottomleft, x, y+h-borderW, borderW, borderW, null);
		g.drawImage(Bank.cornerBottomright, x+w-borderW, y+h-borderW, borderW, borderW, null);
		
		g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, w/10));
		
		g.setColor(Color.BLACK);
		g.drawString(name, x+w/2-g.getFontMetrics().stringWidth(name)/2-1, y+h/7+4-1);
		g.setColor(this.ownerID == Util.clientID ? Color.WHITE : Color.RED);
		g.drawString(name, x+w/2-g.getFontMetrics().stringWidth(name)/2, y+h/7+4);
		
		g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, w/5));
		g.setColor(Color.BLACK);
			int iconW = w / 4;
			g.drawImage(Bank.iconHealth, x-iconW/3, y+h-iconW+iconW/4, iconW, iconW, null);
			g.drawImage(Bank.iconSpeed, x-iconW/3, y+h-iconW+iconW/4-iconW, iconW, iconW, null);
			g.drawImage(Bank.iconAttack, x+w-iconW/3*2, y+h-iconW+iconW/4, iconW, iconW, null);
			g.drawImage(Bank.slot_head, x-iconW/3, y+h-iconW+iconW/4, iconW, iconW, null);
			g.drawImage(Bank.slot_foot, x-iconW/3, y+h-iconW+iconW/4-iconW, iconW, iconW, null);
			g.drawImage(Bank.slot_weapon, x+w-iconW/3*2, y+h-iconW+iconW/4, iconW, iconW, null);
			Bank.drawOvalOutlineOut(g, x-iconW/3, y+h-iconW+iconW/4, iconW, iconW, Color.BLACK, 2);
			Bank.drawOvalOutlineOut(g, x+w-iconW/3*2, y+h-iconW+iconW/4, iconW, iconW, Color.BLACK, 2);
			int health = this.getHealth();
			int attack = this.getAttack();
			int speed = (int) this.getSpeed();
			g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, w/4));
			g.setColor(Color.BLACK);
			g.drawString(health+"", x-iconW/3+iconW/2-g.getFontMetrics().stringWidth(health+"")/2-3, y+h-2);
			g.setColor(Color.WHITE);
			g.drawString(health+"", x-iconW/3+iconW/2-g.getFontMetrics().stringWidth(health+"")/2, y+h);
			
			g.setColor(Color.BLACK);
			g.drawString(attack+"", x+w-iconW/3*2+iconW/2-g.getFontMetrics().stringWidth(attack+"")/2-3, y+h-2);
			g.setColor(Color.WHITE);
			g.drawString(attack+"", x+w-iconW/3*2+iconW/2-g.getFontMetrics().stringWidth(attack+"")/2, y+h);
			
			g.setColor(Color.BLACK);
			g.drawString((int)speed+"", x-iconW/3+iconW/2-g.getFontMetrics().stringWidth((int)speed+"")/2-3, y+h-2-iconW);
			g.setColor(Color.WHITE);
			g.drawString((int)speed+"", x-iconW/3+iconW/2-g.getFontMetrics().stringWidth((int)speed+"")/2, y+h-iconW);
			
			if(equipment!=null){
				int eqw = w / 3 * 2, eqh = h / 3 * 2;
				int eqx = bx + w / 2 - eqw / 2, eqy = by + ((by + h / 2) > Properties.height / 2 ? (-eqh) : h	 + 10);
				iconW = iconW / 3 * 2;
				int dura = this.equipmentDurability;
				equipment.draw(equipmentArt, g, eqx, eqy, eqw, eqh, true);
				g.drawImage(Bank.iconDurability, eqx-iconW/3, eqy+eqh-iconW+iconW/4, iconW, iconW, null);
				g.drawImage(Bank.slot_head, eqx-iconW/3, eqy+eqh-iconW+iconW/4, iconW, iconW, null);
				g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, w/5));
				g.setColor(Color.BLACK);
				g.drawString(dura+"", eqx-iconW/3+iconW/2-g.getFontMetrics().stringWidth(dura+"")/2-3, eqy+eqh-2);
				g.setColor(Color.WHITE);
				g.drawString(dura+"", eqx-iconW/3+iconW/2-g.getFontMetrics().stringWidth(dura+"")/2, eqy+eqh);
				//g.drawImage(Bank.slot_head, bx + bx / 2 - duraIconSize / 2, eqy + eqh / 3 * 2, iconW, iconW, null);
				//g.drawString(this.equipmentDurability+"/"+this.equipment.getDurability(), bx + bx / 2 - duraIconSize / 2, eqy + eqh / 3 * 2);
			}

	}
	
	public void attack(int type, Unit u){
		//0 = SERVER, 1 = CLIENT
		if(!this.hasAttacked &! (this.getTemplate() instanceof StructureTemplate)){
			this.hurt(u.getAttack(), u);
			u.hurt(this.getAttack(), this);
			this.hasAttacked =  true;
			//if(this.getTemplate() == UnitTemplate.commHera)new ClipShell("axeSlash.wav").start();
			for(CardAbility ab : getAbilities()){
				ab.onAttack(this, u, roomID);
			}
			Animation a = new Animation(Util.boardOffsetX+Grid.tileSize*posX+Grid.tileSize/2, Util.boardOffsetY+Grid.tileSize*posY+Grid.tileSize/2, 1200, Animation.TAG_SPEECH);
			a.setText(((UnitTemplate)this.getTemplate()).getTxtAtk());
			Display.currentScreen.particles.add(a);
			Animation.createStaticAnimation(u, "SLASH", 2, 550);
		}
		Util.resolveUnits(roomID);
	}
	
	public UnitTemplate getTemplate(){
		return template;
	}
	
	public Family getFamily(){
		if(equipment == Card.equipmentScrollDemon){
			return Family.demon;
		}
		return template.getFamily();
	}

	public void moveLeft(int i) {posX-=i;}
	public void moveRight(int i) {posX+=i;}
	public void moveUp(int i) {posY-=i;}
	public void moveDown(int i) {posY+=i;}

	public float getSpeed() {
		if(getTemplate() instanceof StructureTemplate)return 0;
		if(hasEffect(EffectType.chill) &! this.abilities.contains(CardAbility.wintersembrace))return 1;
		int add = 0;
		add+=getTotalEffectValue(EffectType.speed);
		if(this.abilities.contains(CardAbility.wintersembrace))
		add+=getTotalEffectValue(EffectType.chill)*2;
		return speed+add;	
	}
	
	public boolean hasEffect(EffectType e){
		for(Effect eff : effects){
			if(eff.getEffectType() == e)return true;
		}
		if(equipment!=null){
			for(Effect eff : equipment.getEffects()){
				if(eff.getEffectType() == e)return true;
			}
		}
		return false;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Image getInteractIcon() {
		return interactIcon;
	}

	public void setInteractIcon(Image interactIcon) {
		this.interactIcon = interactIcon;
	}

	public String getInteractText() {
		return interactText;
	}

	public void setInteractText(String interactText) {
		this.interactText = interactText;
	}

	public void heal(int val) {
		int v1 = val;
		if(Bank.isServer()){
			for(int i = 0; i < Bank.server.getUnits(roomID).size(); ++i){
				Unit t = Bank.server.getUnits(roomID).get(i);
				if(t!=null){
					if(t.getAbilities().contains(CardAbility.grove)){
						v1*=2;
					}
					if(t.getAbilities().contains(CardAbility.corruptHealing)){
						v1*=-1;
					}
				}
			}
		}else{
			for(int i = 0; i < Display.currentScreen.objects.size(); ++i){
				Unit t = Display.currentScreen.objects.get(i);
				if(t!=null){
					if(t.getAbilities().contains(CardAbility.grove)){
						v1*=2;
					}
					if(t.getAbilities().contains(CardAbility.corruptHealing)){
						v1*=-1;
						Util.drawParticleLine(t.getPoint(), this.getPoint(), 9, true, "SKULLGAS");
					}
				}
			}
		}
		if(v1 >= 0){
			Display.currentScreen.particles.add(new Animation(this.getPoint().x, this.getPoint().y, 2000, Animation.TAG_TEXT).setText(v1+""));
			this.health+=v1;
		}else{
			this.hurt(Math.abs(v1), this);
		}
		if(this.health>this.getHealthMax())this.health = this.getHealthMax();
	}
	
	public GridBlock getTileAtPosition(int x, int y){
		if(Bank.isServer()){
			return GridBlock.all[Bank.server.getGrid(roomID).getTileID(x, y)];
		}else{
			return GridBlock.all[((PanelBoard)Display.currentScreen).getGrid().getTileID(x, y)];
		}
	}
	
	public boolean canUnitMoveOverTile(int x, int y){
		GridBlock tile = this.getTileAtPosition(x, y);	
		Unit unitOnTile = null;
		if(Bank.isServer()){
			unitOnTile = Bank.server.getUnitOnTile(x, y, roomID);
		}else{
			unitOnTile = Display.currentScreen.getUnitOnTile(x, y, roomID);
		}
		if(tile == GridBlock.water){
			return (this.getAbilities().contains(CardAbility.amphibious) || this.getAbilities().contains(CardAbility.aquatic) || this.getAbilities().contains(CardAbility.hovering));
		}else{
			if(this.getAbilities().contains(CardAbility.aquatic))return false;
		}
		if(tile == GridBlock.air){
			return this.getAbilities().contains(CardAbility.hovering);
		}
		if(tile == GridBlock.boulder){
			return this.getAbilities().contains(CardAbility.hovering) || this.getAbilities().contains(CardAbility.burrowing);
		}
		if(unitOnTile!=null){
			if(unitOnTile.getAbilities().contains(CardAbility.wall) && unitOnTile.ownerID != this.ownerID || unitOnTile.ownerID != this.ownerID){
				return false;
			}
		}
		return true; 
	}
	
	public ArrayList<Point> generatePath(int destX, int destY){
		int usedMoves = (int) this.getSpeed();
		ArrayList<Point> list = new ArrayList<Point>();
		ArrayList<Point> moveBuffer = new ArrayList<Point>();		
		if(usedMoves > 0){
			if(destY > posY){
				for(int i = posY+1; i <= destY; ++i){
					moveBuffer.add(new Point(posX, i));
				}
			}
			if(destY < posY){
				for(int i = posY-1; i >= destY; --i){
					moveBuffer.add(new Point(posX, i));
				}
			}
			if(destX > posX){
				for(int i = posX+1; i <= destX; ++i){
					moveBuffer.add(new Point(i, destY));
				}
			}
			if(destX < posX){
				for(int i = posX-1; i >= destX; --i){
					moveBuffer.add(new Point(i, destY));
				}
			}
		}
		for(Point p : moveBuffer){
			if(this.canUnitMoveOverTile(p.x, p.y) && usedMoves > 0){
				list.add(p);
				--usedMoves;
			}else{
				return list;
			}
		}
		return list;
	}
	
	public void moveOnList(ArrayList<Point> points){
		if(!this.hasMoved){
			for(Point p : points){
				if(points.indexOf(p) >= points.size()-1)
				this.moveRaw(p.x, p.y);
				if(!Bank.isServer())
				Animation.createStaticAnimation(p.x*Grid.tileSize+Util.boardOffsetX + Grid.tileSize / 2, p.y*Grid.tileSize+Util.boardOffsetY + Grid.tileSize / 2, "EARTH", 0.8f, 900);
			}
			this.hasMoved = true;
		}
	}

	public Rectangle getHitbox() {
		return new Rectangle(posX*Grid.tileSize+Util.boardOffsetX, posY*Grid.tileSize+Util.boardOffsetY, Grid.tileSize, Grid.tileSize);
	}

	public void addEnergy1(int i){
		energy+=i;
		if(energy > 10) energy = 10;
	}

	public boolean getIsCommander() {
		return getTemplate().getRank() == UnitTemplate.RANK_COMMANDER;
	}

	public ArrayList<CardAbility> getAbilities() {
		if(equipment != null){
			ArrayList<CardAbility> newAbs = (ArrayList<CardAbility>) abilities.clone();
			for(CardAbility ca : equipment.getAbilities()){
				newAbs.add(ca);
			}
			return newAbs;
		}
		return abilities;
	}

	public void setAbilities(ArrayList<CardAbility> abilities) {
		this.abilities = abilities;
	}

	public ArrayList<Effect> getEffects() {
		return effects;
	}

	public void setEffects(ArrayList<Effect> effects) {
		this.effects = effects;
	}

	public void removeEffect(Effect e) {
		this.effects.remove(e);
	}
	
	public void addEffect(Effect e) {
		this.effects.add(e);
	}
	
	public void addOrUpdateEffect(Effect e) {
		boolean update = false;
		for(int i = 0; i < effects.size(); ++i){
			Effect eff = effects.get(i);
			if(eff!=null){
				if(eff.getEffectType() == e.getEffectType()){
					update = true;
					eff.setVal(eff.getVal() + e.getVal());
				}
			}
		}
		if(!update)
		this.effects.add(e);
	}

	public void addUsedAbility(CardAbility ability) {
		getUsedAbilities().add(ability);
	}

	public ArrayList<CardAbility> getUsedAbilities() {
		return usedAbilities;
	}

	public void setUsedAbilities(ArrayList<CardAbility> usedAbilities) {
		this.usedAbilities = usedAbilities;
	}

	public Point getPoint() {
		return new Point(Util.boardOffsetX+Grid.tileSize*posX+Grid.tileSize/2, Util.boardOffsetY+Grid.tileSize*posY+Grid.tileSize/2);
	}
	
	public Point getPosition() {
		return new Point(Util.boardOffsetX+Grid.tileSize*posX+Grid.tileSize/2, Util.boardOffsetY+Grid.tileSize*posY+Grid.tileSize/2);
	}
	
	public Point getTilePosition() {
		return new Point(posX, posY);
	}

	public Animation getMoveAnim() {
		return moveAnim;
	}

	public void setMoveAnim(Animation moveAnim) {
		this.moveAnim = moveAnim;
	}

	public Unit setOwnerID(int ownerID2) {
		this.ownerID = ownerID2;
		return this;
	}

	public void setTemplate(UnitTemplate template) {
		this.template = template;
	}

	public void resummon() {
		UnitTemplate t = template;
		this.setEnergy1(0);
		this.health = t.getHealth();
		this.healthMax = health;
		this.speed = t.getSpeed();
		this.attack = t.getAttack();
		this.setAbilities((ArrayList<CardAbility>) t.getAbilities().clone());
		this.img = Filestream.getCardImage(t.getArt());
	}
	
	public boolean canMoveTo(int x, int y){
		boolean canMove = true;
		ArrayList<Point> crossed = new ArrayList<Point>();
		for(int i = 0; i < Util.dif(posX, x); ++i){
			if(x > posX){
				crossed.add(new Point(posX + i, posY));
			}
			if(x < posX){
				crossed.add(new Point(posX - i, posY));
			}
		}
		for(int i = 0; i < Util.dif(posY, y); ++i){
			if(y > posY){
				crossed.add(new Point(x, posY + i));
			}
			if(y < posY){
				crossed.add(new Point(x, posY - i));
			}
		}
		for(Point p : crossed){
			Unit u = Display.currentScreen.getUnitOnTile(p.x, p.y, roomID);
			if(u!=null){
				if(u.getAbilities().contains(CardAbility.wall) && u.ownerID != this.ownerID){
					canMove = false;
				}
			}
		}
		return canMove;
	}

	public void move(int x, int y) {
		boolean canMove = canMoveTo(x,y);
		if(!hasMoved && canMove){
			Packet03MoveUnit pkt = new Packet03MoveUnit(Util.clientID, GUID, x, y);
			pkt.write(Bank.client);
			hasMoved = true;
		}		
	}
	
	public void moveRaw(int x, int y) {
		Packet03MoveUnit pkt = new Packet03MoveUnit(Util.clientID, GUID, x, y);
		pkt.write(Bank.client);
	}

	public Byte[] getCostTypeOverride() {
		return costTypeOverride;
	}

	public void setCostTypeOverride(Byte[] costTypeOverride) {
		this.costTypeOverride = costTypeOverride;
	}

	public boolean isStructure() {
		return this.getAbilities().contains(CardAbility.wall) || this.getTemplate() instanceof StructureTemplate;
	}

	public byte getEquipmentDurability() {
		return equipmentDurability;
	}

	public void setEquipmentDurability(byte equipmentDurability) {
		this.equipmentDurability = equipmentDurability;
	}

	public Image getEquipmentArt() {
		return equipmentArt;
	}

	public void setEquipmentArt(Image equipmentArt) {
		this.equipmentArt = equipmentArt;
	}

	public void Protect(Unit u, int i) {
		this.protector = u;
		this.effects.add(new Effect(EffectType.protection, i, i));
	}

	public boolean isPlayerUnit() {
		return this.ownerID >= 0 && this.ownerID < 10;
	}

	public void destroy() {
		this.setHealth(0);
		this.hurt(this.getHealthMax(), this);
	}

	/*public ArrayList<CardAbility> getAbilityQueue() {
		return abilityQueue;
	}

	public void queueAbilityLearn(CardAbility a) {
		this.abilityQueue.add(a);
	}

	public void doLearnQueue() {
		Unit u = this;
		for(CardAbility ca : u.getAbilityQueue()){
			u.getAbilities().add(ca);
			ca.onPlayed(u);
		}
		u.getAbilityQueue().clear();
	}*/
}