package org.author.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class PanelBoard extends GridPanel {
	
	int cardsInDeck = 40;
	int unitClickedGUID = -1;
	static ArrayList<CardShell> hand = new ArrayList<CardShell>();
	static ArrayList<CardShell> handqueue = new ArrayList<CardShell>();
	int selCard = -1;
	long lastAbilityClick = System.currentTimeMillis();
	int abilityClickInterval = 400;
	CardAbility selAbility = null;
	long gameEndStart = -1;
	static int dcw = Properties.width/ 12;
	int dch = (int) (dcw * 1.5);
	private Animation drawanim = null;
	private int roomID = -1;
	
	public PanelBoard() {
		super(new Grid(Util.boardWidth, Util.boardHeight, null));
		this.renderGuis = false;
		for(int i = 0; i < grid.getCore()[0].length; ++i){
			for(int j = 0; j < grid.getCore()[1].length; ++j){
				grid.setTileID(i, j, GridBlock.ground.getID());
				//if(rand.nextInt(20)==0)placeDirt(i, j);
			}
		}
		for(int i = 0; i < grid.getCore()[0].length; ++i){
			for(int j = 0; j < grid.getCore()[1].length; ++j){
				grid.setTileID(0, j, GridBlock.dirtLeft.getID());
				grid.setTileID(grid.getCore()[0].length - 1, j, GridBlock.dirtRight.getID());
				grid.setTileID(i, 0, GridBlock.dirtTop.getID());
				grid.setTileID(i, grid.getCore()[1].length - 1, GridBlock.dirtBot.getID());
			}
		}
		
		grid.setTileID(Util.boardWidth / 2 - 1, 0, GridBlock.boulder.getID());
		grid.setTileID(Util.boardWidth / 2 - 1, 1, GridBlock.boulder.getID());
		grid.setTileID(Util.boardWidth / 2 - 1, 2, GridBlock.boulder.getID());
		grid.setTileID(Util.boardWidth / 2, 2, GridBlock.boulder.getID());

		grid.setTileID(Util.boardWidth / 2 - 1, 4, GridBlock.boulder.getID());
		grid.setTileID(Util.boardWidth / 2, 4, GridBlock.boulder.getID());
		grid.setTileID(Util.boardWidth / 2, 5, GridBlock.boulder.getID());
		grid.setTileID(Util.boardWidth / 2, 6, GridBlock.boulder.getID());

		grid.setTileID(0, 0, GridBlock.dirtTopLeft.getID());
		grid.setTileID(grid.getCore()[0].length - 1, grid.getCore()[1].length - 1, GridBlock.dirtBotRight.getID());
		grid.setTileID(grid.getCore()[0].length - 1, 0, GridBlock.dirtTopRight.getID());
		grid.setTileID(0, grid.getCore()[1].length - 1, GridBlock.dirtBotLeft.getID());
		int relTS = 48;
		GUIButton b = new GUIButton(Bank.button, Bank.buttonHover, 1, Util.boardOffsetX+Grid.tileSize*Util.boardWidth-200+3, Util.boardOffsetY+Grid.tileSize*Util.boardHeight+3, 200, 60).setText("End Turn");
		b.turnButton = true;
		guis.add(b);
		
	}
	
	public void placeDirt(int x, int y){
		grid.setTileID(x, y, GridBlock.ground.getID());
		for(int i = 0; i < 3; ++i){
			for(int j = 0; j < 3; ++j){
				GridBlock b = GridBlock.dirtTopLeft;
				if((i == 1 && j == 0))b = GridBlock.dirtTop;
				if((i == 2 && j == 0))b = GridBlock.dirtTopRight;
				if((i == 0 && j == 1))b = GridBlock.dirtLeft;
				if((i == 2 && j == 1))b = GridBlock.dirtRight;
				if((i == 0 && j == 2))b = GridBlock.dirtBotLeft;
				if((i == 1 && j == 2))b = GridBlock.dirtBot;
				if((i == 2 && j == 2))b = GridBlock.dirtBotRight;
				if(grid.getTileID(x - 1 + i, y - 1 + j) == GridBlock.grass.getID()){
					grid.setTileID(x - 1 + i, y - 1 + j, b.getID());
				}
			}
		}
	}

	public void buttonReact(int id){
		if(id == 1){
			Packet04ChangeTurn pkt = new Packet04ChangeTurn(-2, 0);
			pkt.write(Bank.client);
		}
	}
	

	public void onUpdate() {
		if(Util.turn != Util.clientID){
			this.selCard = -1;
		}
		if(this.unitClickedGUID!=-1){
			if(this.getUnitFromGUID(unitClickedGUID, roomID)==null) this.unitClickedGUID = -1;
			if(rightClicking || (clicking &! new Rectangle(0, Util.boardOffsetY, Properties.width, Grid.tileSize*Util.boardHeight).contains(mousePoint))){
				this.unitClickedGUID = -1;
			}
		}
		if(unitClickedGUID == -1){
			selAbility = null;
		}
		if(!Util.queue.isEmpty()){
			for(Unit u : Util.queue){
				objects.add(u);
			}
			Util.queue.clear();
		}
		if(!handqueue.isEmpty() && drawanim  == null){
			for(int i = 0; i < handqueue.size(); ++i){
				CardShell c = handqueue.get(i);
				if(c!=null && c.getCard()!=null){
					drawanim = new Animation(Properties.width - dch, Properties.height - dch / 4 * 3, 2000, Animation.TAG_DRAW);
					drawanim.width = dcw;
					drawanim.height = dch;
					drawanim.setData(c.getCard().getId());
					particles.add(drawanim);
					handqueue.remove(i);
					Animation.createStaticAnimation(Properties.width - dch, Properties.height - dch/2, "BLUELIGHT", 5, 800);
				}
			}
		}
		if(drawanim!=null){
			if(!particles.contains(drawanim)){
				hand.add(new CardShell(Card.all[drawanim.getData()]));
				drawanim = null;
			}
		}
		if(Util.boardState != null){
			Util.boardState.update(this);
		}
	}
	
	public static void removeCardFromHand(Card c){
		for(int i = 0; i < hand.size(); ++i){
			if(hand.get(i).getCard() == c){
				hand.remove(i);
				break;
			}
		}
	}
	
	public void keyPressed(KeyEvent e){
		if((e.getKeyChar()+"").matches("^-?\\d+$")){
			int key = Integer.parseInt(e.getKeyChar()+"");
			if(this.getUnitFromGUID(this.unitClickedGUID, roomID) !=null){
				if(this.getUnitFromGUID(unitClickedGUID, roomID).getAbilities().get(key-1)!=null){
					this.selAbility = getUnitFromGUID(unitClickedGUID, roomID).getAbilities().get(key-1);
				}	
			}else{
				int index = key-1;
				while(objects.get(index) == null && index < objects.size()+1)++index;
				if(this.getUnitFromGUID(index, roomID) != null)this.unitClickedGUID = index;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_TAB){
			if(this.getUnitFromGUID(this.objects.get(unitClickedGUID+1).GUID, roomID) != null)this.unitClickedGUID = unitClickedGUID+1;
			else this.unitClickedGUID = rand.nextInt(objects.size());
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			GUIChat chat = new GUIChat();
			guis.add(chat);
			selGui = guis.indexOf(chat);
		}
	}
	
	public void releaseClick(boolean b) {
		if(guis.size() > 0){
			for(int i = 0; i < guis.size(); ++i){
				if(i < guis.size()){
					if(guis.get(i).getRect().contains(Display.currentScreen.getMousePoint()) && guis.get(i).solid){
						guis.get(i).releaseClick(b);
					}
				}
			}
		}			
		boolean hit = false, justCast = false;
		if(b){
			if(unitClickedGUID==-1){
				for(int i = 0; i < objects.size(); ++i){
					Unit u = objects.get(i);
					if(u.getHitbox().contains(mousePoint)){
						hit = true;
						unitClickedGUID = u.GUID;
					}
				}
			}else{
				Unit u = getUnitFromGUID(this.unitClickedGUID, roomID);
				int gridX = (mousePoint.x - Util.boardOffsetX) / Grid.tileSize;
				int gridY = (mousePoint.y - Util.boardOffsetY) / Grid.tileSize;
				boolean canDo = true;
				if(u.posX == gridX && u.posY == gridY)canDo = false;
				if(selAbility == CardAbility.move || selAbility == CardAbility.attack){
					if(canDo){
						if(this.selAbility == CardAbility.move){
							if(getUnitOnTile(gridX, gridY, roomID) == null){
								if(Util.distance(new Point(u.posX, u.posY), new Point(gridX, gridY)) <= u.getSpeed() && u.canUnitMoveOverTile(gridX, gridY))
								moveUnit(u, gridX, gridY);
							}
						}
						if(this.selAbility == CardAbility.attack){
							Packet06AttackUnit pkt = new Packet06AttackUnit(this.unitClickedGUID, getUnitOnTile(gridX, gridY, roomID).GUID);
							pkt.write(Bank.client);
						}
						selAbility = null;
						justCast = true;
					}
				}else{
					if(selAbility!=null){
						if(selAbility.getTargetType() == CardAbility.TARGET_UNIT && getUnitOnTile(gridX, gridY, roomID)!=null && isOnBoard(mousePoint)){
							Packet07CardAbility pkt = new Packet07CardAbility(this.unitClickedGUID, getUnitOnTile(gridX, gridY, roomID).GUID, this.selAbility.getId(), gridX, gridY);
							pkt.write(Bank.client);
							selAbility = null;
							justCast = true;
						}
						if(selAbility.getTargetType() == CardAbility.TARGET_AREA && isOnBoard(mousePoint)){
							Packet07CardAbility pkt = new Packet07CardAbility(this.unitClickedGUID, -1, this.selAbility.getId(), gridX, gridY);
							pkt.write(Bank.client);
							selAbility = null;
							justCast = true;
						}
					}
				}
			}
			if(!hit && selAbility == null &! justCast){
				for(int i = 0; i < objects.size(); ++i){
					Unit u = objects.get(i);
					if(u.getHitbox().contains(mousePoint)){
						hit = true;
						unitClickedGUID = u.GUID;
					}
				}
			}
		}
		if(!b)unitClickedGUID = -1;
	}
	
	public boolean isOnBoard(Point p){
		return (p.x > Util.boardOffsetX && p.y > Util.boardOffsetY && p.x < (Util.boardOffsetX + Util.boardWidth * Grid.tileSize) && p.y < (Util.boardOffsetY + Util.boardHeight * Grid.tileSize));
	}
	
	public Unit getUnitOnTile(int x, int y, int roomID){
		for(int i = 0; i < objects.size(); ++i){
			if(objects.get(i).posX == x && objects.get(i).posY == y)return objects.get(i);
		}
		return null;
	}
	
	public void moveUnit(Unit u, int x, int y){
		if(Util.boardState == Card.stateMalestrom){
			u.addEnergy1(1);
			u.hurt(1, u);
			Animation.createStaticAnimation(u, "THUNDER", 3, 1500);
			Animation.createStaticAnimation(u, "POOL", 3, 900);
		}
		u.moveOnList(u.generatePath(x, y));
	}
	
	public boolean isValidTile(int x, int y){
		return grid.getTileID(x, y) > 0 && grid.getTileID(x, y) != GridBlock.water.getID();
	}

	public void click(boolean b) {
		if(guis.size() > 0){
			for(int i = 0; i < guis.size(); ++i){
				if(i < guis.size()){
					if(guis.get(i).getRect().contains(Display.currentScreen.getMousePoint()) && guis.get(i).solid){
						guis.get(i).click(b);
					}
				}
			}
		}
		if(selCard > -1 && Util.turn == Util.clientID){
			if(mousePoint.x > Util.boardOffsetX && mousePoint.y > Util.boardOffsetY && mousePoint.x < Util.boardOffsetX + Util.boardWidth*Grid.tileSize && mousePoint.y < Util.boardOffsetY + Util.boardHeight*Grid.tileSize){
				int gridX = (mousePoint.x - Util.boardOffsetX) / Grid.tileSize;
				int gridY = (mousePoint.y - Util.boardOffsetY) / Grid.tileSize;
				Packet02PlayCard pkt = new Packet02PlayCard(Util.clientID, hand.get(selCard).getID(), gridX, gridY, 0);
				pkt.write(Bank.client);
				selCard = -1;
			}
		}
	}

	public void renderScene(Graphics g) {
		/*int r = -2;
		for(int i = 0; i < cardsInDeck; ++i){
			g.drawImage(Bank.cardback, Properties.width-280+i*r, 100+i*3, 220, 360, null);
		}*/
		int count = 2;
		int w = Properties.width/count;
		for(int i = 0; i < count; ++i){
			for(int j = 0; j < count; ++j){
				g.drawImage(Bank.texture_stardust, i*w, j*w, w, w, null);
			}
		}
	  }
	
	public void cast(Unit u, Unit t, int x, int y, CardAbility c){
		if(System.currentTimeMillis() - lastAbilityClick >= abilityClickInterval){
			this.lastAbilityClick = System.currentTimeMillis();
			if(c.getTargetType() == CardAbility.TARGET_SELF){
				Packet07CardAbility pkt = new Packet07CardAbility(u.GUID, (t == null ? 0 : t.GUID), c.getId(), x, y);
				pkt.write(Bank.client);
			}
			if(c.getTargetType() == CardAbility.TARGET_AREA || c.getTargetType() == CardAbility.TARGET_UNIT){
				this.selAbility = c;
			}
		}
	}

	public void renderForeground(Graphics g){
		int x = Grid.tileSize * Util.boardWidth + Util.boardOffsetX;
		g.drawImage(Bank.texture_tweed, x+20, Util.boardOffsetY, Properties.width/5, Properties.height/2, null);
		g.drawImage(Bank.texture_paper, x+20, Util.boardOffsetY+Properties.height/2+20, Properties.width/5, Properties.height/4, null);
		Bank.drawOutlineOut(g, x+20, Util.boardOffsetY, Properties.width/5, Properties.height/2, Color.BLACK, 3);
		Bank.drawOutlineOut(g, x+20, Util.boardOffsetY+Properties.height/2+20, Properties.width/5, Properties.height/4, Color.BLACK, 3);
		for(int i = 0; i < Util.connectedPlayers.size(); ++i){
	        g.setFont(Util.cooldownBold);
	        g.setColor(Color.BLACK);
	        g.drawString("Player "+Util.connectedPlayers.get(i).playerID+": "+Util.connectedPlayers.get(i).name, x+25-2, Util.boardOffsetY+Properties.height/2+20+i*30+30-1);
	        g.setColor(Util.connectedPlayers.get(i).color);
	        g.drawString("Player "+Util.connectedPlayers.get(i).playerID+": "+Util.connectedPlayers.get(i).name, x+25, Util.boardOffsetY+Properties.height/2+20+i*30+30);
		}
		if(unitClickedGUID!=-1){
			Unit u = this.getUnitFromGUID(unitClickedGUID, roomID);
			if(u!=null){
				g.setFont(Util.cooldownBold);
				g.setColor(Color.BLACK);
				g.drawString(u.getTemplate().getName(), x+20+10-2, Util.boardOffsetY+50-2);
				g.setColor(u.ownerID == Util.clientID ? Color.green : Color.RED);
				g.drawString(u.getTemplate().getName(), x+20+10, Util.boardOffsetY+50);
				g.setColor(Color.WHITE);
				g.setFont(Util.spellDesc);
				g.drawString("Right click to deselect.", x+30, Util.boardOffsetY+Properties.height/2-20);
				int energyW = Properties.width/5 / 10;
				for(int i = 0; i < 10; ++i){
					g.drawImage(Bank.energyEmpty, x+20+i*energyW, Util.boardOffsetY+70, energyW, energyW, null);
				}
				for(int i = 0; i < u.getEnergy(); ++i){
					g.drawImage(Bank.energyFull, x+20+i*energyW, Util.boardOffsetY+70, energyW, energyW, null);
				}
				if(u.getAbilities().size() > 0){
					for(int i = 0; i < u.getAbilities().size(); ++i){
						CardAbility ab = u.getAbilities().get(i);
						Rectangle button = new Rectangle(x+20, Util.boardOffsetY+80+i*50+3+energyW, Properties.width/5, 40);
						g.drawImage((button.contains(mousePoint) && u.getEnergy() >= ab.getCost()) ? Bank.buttonHover : Bank.button, button.x, button.y, button.width, button.height, null);
						g.drawImage(ab.getImg(), button.x, button.y, button.height, button.height, null);
						g.setFont(Util.spellDesc);
						g.setColor(Color.WHITE);
						int sw = g.getFontMetrics().stringWidth(ab.getName());
						for(int j = 0; j < ab.getCost(); ++j){
							Image costImg = Bank.energyFull;
							if(ab.getCostType() == CardAbility.COST_TYPE_MANA){costImg = Bank.starFull;}
							if(ab.getCostType() == CardAbility.COST_TYPE_RESOURCE){costImg = Bank.ball;}
							if(ab.getCostType() == CardAbility.COST_TYPE_SPECIAL){costImg = Bank.holynova;}
							g.drawImage(costImg, button.height+button.x+sw+(j)*energyW/2+20, button.y+button.height/2-energyW/2, energyW, energyW, null);
						}
						g.drawString(ab.getName(), button.x+button.height, button.y+button.height/3*2);
						if((Util.turn != Util.clientID) || (u.getUsedAbilities().contains(ab) &! ab.isUnbound()) || (Util.gold < ab.getCost() && ab.getCostType() == CardAbility.COST_TYPE_MANA) || (u.getEnergy() < ab.getCost() && ab.getCostType() == CardAbility.COST_TYPE_ENERGY) || u.ownerID != Util.clientID || ab.getTargetType() == CardAbility.TARGET_PASSIVE || (ab == CardAbility.attack && u.hasAttacked) || (ab == CardAbility.move && u.hasMoved)){
							g.setColor(Util.transparent_dark);
							g.fillRect(button.x, button.y, button.width, button.height);
						}
						Bank.drawOutlineOut(g, button.x, button.y, button.width, button.height, (ab.getTargetType() == CardAbility.TARGET_PASSIVE ? Color.YELLOW : (selAbility == ab ? Color.GREEN : Color.BLACK)), 3);
						if(clicking && button.contains(mousePoint)){
							this.cast(this.getUnitFromGUID(unitClickedGUID, roomID), null, 0, 0, ab);
						}
						if(button.contains(mousePoint)){
							ab.drawTooltip(g, 0, x, mousePoint.y);
						}
					}
				}
				int destX = mousePoint.x - ((mousePoint.x-Util.boardOffsetX)%Grid.tileSize) + Grid.tileSize/2;
				int destY = mousePoint.y - ((mousePoint.y-Util.boardOffsetY)%Grid.tileSize) + Grid.tileSize/2;
				int gridX = (mousePoint.x - Util.boardOffsetX) / Grid.tileSize;
				int gridY = (mousePoint.y - Util.boardOffsetY) / Grid.tileSize;
				if(this.selAbility == CardAbility.move){
					if(!u.hasMoved){
						ArrayList<Point> path = u.generatePath(gridX, gridY);
						for(Point p : path){
							g.setColor(Color.GREEN);
							g.drawRect(p.x*Grid.tileSize+Util.boardOffsetX, p.y*Grid.tileSize+Util.boardOffsetY, Grid.tileSize, Grid.tileSize);
							g.setColor(Util.transparent_green);
							g.fillRect(p.x*Grid.tileSize+Util.boardOffsetX, p.y*Grid.tileSize+Util.boardOffsetY, Grid.tileSize, Grid.tileSize);
							g.drawImage(Bank.slot_foot, p.x*Grid.tileSize+Util.boardOffsetX-20 + Grid.tileSize / 2, p.y*Grid.tileSize+Util.boardOffsetY-20 + Grid.tileSize / 2, 40, 40, null);
						}
					}
					
					/*
					if(Util.distance(new Point(u.posX, u.posY), new Point(gridX, gridY)) <= u.getSpeed() && u.canMoveTo(gridX, gridY))
					g.setColor(Color.GREEN);
					else g.setColor(Color.RED);
					if(!u.hasMoved){
						for(int j = 0; j < 3; ++j){
							g.drawLine(Util.boardOffsetX+u.posX*Grid.tileSize+Grid.tileSize/2, Util.boardOffsetY+u.posY*Grid.tileSize+Grid.tileSize/2, destX, Util.boardOffsetY+u.posY*Grid.tileSize+Grid.tileSize/2);
							g.drawLine(destX, Util.boardOffsetY+u.posY*Grid.tileSize+Grid.tileSize/2, destX, destY);
						}
						g.drawRect(gridX*Grid.tileSize+Util.boardOffsetX, gridY*Grid.tileSize+Util.boardOffsetY, Grid.tileSize, Grid.tileSize);
						g.drawImage(Bank.slot_foot, destX-20, destY-20, 40, 40, null);
					}*/
				}
				else if(this.selAbility == CardAbility.attack){
					if(Util.distance(new Point(u.posX, u.posY), new Point(gridX, gridY)) <= 2)
					g.setColor(Color.GREEN);
					else g.setColor(Color.RED);
					if(!u.hasAttacked){
						g.drawLine(Util.boardOffsetX+u.posX*Grid.tileSize+Grid.tileSize/2, Util.boardOffsetY+u.posY*Grid.tileSize+Grid.tileSize/2, destX, destY);
						g.drawImage(Bank.slot_weapon, destX-20, destY-20, 40, 40, null);
					}
				}else{
					if(selAbility!=null){
						if(Util.distance(new Point(u.posX, u.posY), new Point(gridX, gridY)) <= selAbility.getRange())
							g.setColor(Color.GREEN);
						else 
							g.setColor(Color.RED);
						g.drawLine(Util.boardOffsetX+u.posX*Grid.tileSize+Grid.tileSize/2, Util.boardOffsetY+u.posY*Grid.tileSize+Grid.tileSize/2, destX, destY);
						g.drawImage(selAbility.getImg(), destX-20, destY-20, 40, 40, null);
						Util.drawPolyZone(g, u.getPoint().x, u.getPoint().y, (g.getColor()), Grid.tileSize*selAbility.getRange());
					}
				}
			}
		}
		int relTS = 48;
		int stateBoxWidth = 200;
		if(Util.boardState != null){
			g.setFont(Util.spellDesc);
			Rectangle stateboxrect = new Rectangle(Util.boardOffsetX, Util.boardOffsetY + Util.boardHeight * Grid.tileSize+3, stateBoxWidth, 60-3);
			g.drawImage(Bank.texture_tweed, Util.boardOffsetX, Util.boardOffsetY + Util.boardHeight * Grid.tileSize+3, stateBoxWidth, 60-3, null);
			Bank.drawOutlineOut(g, Util.boardOffsetX, Util.boardOffsetY + Util.boardHeight * Grid.tileSize+3, stateBoxWidth, 60-2, Color.BLACK, 3);
			g.setColor(Color.WHITE);
			g.drawString("Board Status:", Util.boardOffsetX + 5, Util.boardOffsetY + Util.boardHeight * Grid.tileSize + 20);
			g.drawString(Util.boardState.getName().replace("Status: ", ""), Util.boardOffsetX + 5, Util.boardOffsetY + Util.boardHeight * Grid.tileSize + 40);
			g.setFont(Util.smallDescFont);
			g.setColor(Color.YELLOW);
			g.drawString((Util.boardStateTimer > 0 ? Util.boardStateTimer : "Infinite") + " Turns Remaining", Util.boardOffsetX + 5, Util.boardOffsetY + Util.boardHeight * Grid.tileSize + 55);
			if(stateboxrect.contains(getMousePoint())){
				Util.boardState.drawTooltip(g, 2, Util.boardOffsetX, Util.boardOffsetY + Util.boardHeight * Grid.tileSize - 110);
			}
		}
		g.setColor(Color.BLACK);
		g.drawImage(Bank.timebar, Util.boardOffsetX + stateBoxWidth, Util.boardOffsetY + Util.boardHeight * Grid.tileSize+3, Grid.tileSize * Util.boardWidth - 200 - stateBoxWidth, 60-3, null);
		Bank.drawOutlineOut(g, Util.boardOffsetX + stateBoxWidth, Util.boardOffsetY + Util.boardHeight * Grid.tileSize+3, Grid.tileSize * Util.boardWidth - 200 - stateBoxWidth, 60-2, Color.BLACK, 3);
		float perc = (float)(System.currentTimeMillis() - Util.turnStart) / ((float)Util.maxTurnTime);
		int cbw = ((int) (perc * (float) (Grid.tileSize * Util.boardWidth - 200 - stateBoxWidth)));
		int maxcbw = Grid.tileSize * Util.boardWidth - 200 - stateBoxWidth;
		g.setColor((Util.clientID == Util.turn ? Util.timebar_color : Util.transparent_red));
		g.fillRect(Util.boardOffsetX + cbw + stateBoxWidth, Util.boardOffsetY + Util.boardHeight * Grid.tileSize+3, maxcbw - cbw, 60-3);
		int cw = Properties.width/16, ch = (int) (cw*1.5);
		int gW = relTS/2;
		
		g.setColor(Color.BLACK);
		g.fillRect(Util.boardOffsetX, Util.boardOffsetY+Util.boardHeight*Grid.tileSize + 60, gW * 10, gW);
		if(Util.manaMode == 1){
			g.drawImage(Util.gold > 0 ? Bank.starFull : Bank.starEmpty, Util.boardOffsetX+5, Util.boardOffsetY+Util.boardHeight*Grid.tileSize + 60, gW, gW, null);
			g.drawImage(Util.goldRed > 0 ? Bank.starRed : Bank.starEmpty, Util.boardOffsetX+5 + gW * 2, Util.boardOffsetY+Util.boardHeight*Grid.tileSize + 60, gW, gW, null);
			g.drawImage(Util.goldBlue > 0 ? Bank.starBlue : Bank.starEmpty, Util.boardOffsetX+5 + gW * 4 + 5, Util.boardOffsetY+Util.boardHeight*Grid.tileSize + 60, gW, gW, null);
			g.drawImage(Util.goldGreen > 0 ? Bank.starGreen : Bank.starEmpty, Util.boardOffsetX+5 + gW * 6 + 10, Util.boardOffsetY+Util.boardHeight*Grid.tileSize + 60, gW, gW, null);
			g.setFont(Util.spellDesc);
			g.setColor(Color.WHITE);
			g.drawString(Util.gold+"", Util.boardOffsetX + 5 + gW, Util.boardOffsetY+Util.boardHeight*Grid.tileSize + 60 + gW / 2 + 7);
			g.setColor(Color.RED);
			g.drawString(Util.goldRed+"", Util.boardOffsetX + 5 + gW + gW * 2, Util.boardOffsetY+Util.boardHeight*Grid.tileSize + 60 + gW / 2 + 7);
			g.setColor(Color.GREEN);
			g.drawString(Util.goldGreen+"", Util.boardOffsetX + 5 + gW + gW * 4 + 5, Util.boardOffsetY+Util.boardHeight*Grid.tileSize + 60 + gW / 2 + 7);
			g.setColor(Color.BLUE);
			g.drawString(Util.goldBlue+"", Util.boardOffsetX + 5 + gW + gW * 6 + 10, Util.boardOffsetY+Util.boardHeight*Grid.tileSize + 60 + gW / 2 + 7);
		}
		int golds = 10;

		if(Util.manaMode == 0){
			g.fillRect(Util.boardOffsetX+golds*gW, Util.boardOffsetY+Util.boardHeight*Grid.tileSize + 60, 60, gW);
			g.setFont(Util.descTitleFont);
			g.setColor(Color.CYAN);
			g.drawString(Util.gold+"/"+golds, Util.boardOffsetX+golds*gW + 10, Util.boardOffsetY+Util.boardHeight*Grid.tileSize + 60 + gW / 2 + 5);
		}
		if(Util.manaMode == 0){
			for(int i = 0; i < golds; ++i){
				g.setColor(Color.BLACK);
				g.fillRect(Util.boardOffsetX+i*gW, Util.boardOffsetY+Util.boardHeight*Grid.tileSize + 60, gW, gW);
				g.drawImage(Bank.starFull, Util.boardOffsetX+i*gW, Util.boardOffsetY+Util.boardHeight*Grid.tileSize + 60, gW, gW, null);
				if(i+1 > Util.gold){
					g.drawImage(Bank.starEmpty, Util.boardOffsetX+i*gW, Util.boardOffsetY+Util.boardHeight*Grid.tileSize + 60, gW, gW, null);
				}
			}
		}
		if(hand.size() > 0){
			for(int i = 0; i < hand.size(); ++i){
				CardShell shell = hand.get(i);
				Card card = hand.get(i).getCard();
				int spacing = cw / 3 * 2;
				Rectangle crect = new Rectangle(Util.boardOffsetX+i*cw,  Properties.height-ch/5*4, cw, ch);
				if(crect.contains(mousePoint)){
					if(selCard!=i)
					card.draw(shell.getImg(), g, Properties.width/2-crect.width, Properties.height/2-crect.height, crect.width*3, crect.height*3, true);
					if(selCard == -1 && clicking)selCard = i;
				}else{
					if(selCard!=i)
					card.draw(shell.getImg(), g, crect.x, crect.y, crect.width, crect.height, false);
				}
			}
		}
		
		if(selCard != -1 && Util.turn == Util.clientID){
			if(hand.size()>selCard)
			hand.get(selCard).getCard().draw(hand.get(selCard).getImg(), g, mousePoint.x+cw, mousePoint.y-ch, cw, ch, false);
			int size = 12+rand.nextInt(12);
			int particleRange = 60;
			AnimatedParticle particle = new AnimatedParticle(mousePoint.x+cw+cw/2, mousePoint.y-ch+ch/2, size, 400+rand.nextInt(200)).addFrameSet("fire").setMotions(rand.nextInt(particleRange)-rand.nextInt(particleRange), rand.nextInt(particleRange)-rand.nextInt(particleRange));
			this.particles.add(particle);
			int destX = mousePoint.x - ((mousePoint.x-Util.boardOffsetX)%Grid.tileSize) + Grid.tileSize/2;
			int destY = mousePoint.y - ((mousePoint.y-Util.boardOffsetY)%Grid.tileSize) + Grid.tileSize/2;
			int gridX = (mousePoint.x - Util.boardOffsetX) / Grid.tileSize;
			int gridY = (mousePoint.y - Util.boardOffsetY) / Grid.tileSize;
			if(mousePoint.x > Util.boardOffsetX && mousePoint.y > Util.boardOffsetY && mousePoint.x < Util.boardOffsetX + Util.boardWidth*Grid.tileSize && mousePoint.y < Util.boardOffsetY + Util.boardHeight*Grid.tileSize){
				g.setColor(Color.RED);
				g.drawLine(mousePoint.x+ch, mousePoint.y-ch/2, destX, destY);
			}
			if(hand.get(selCard).getCard() instanceof UnitTemplate){
				UnitTemplate card = ((UnitTemplate)hand.get(selCard).getCard());
				if(card.getRank() == Card.RANK_COMMANDER){
					Util.drawZone(g, Util.boardOffsetX + (Util.clientID == 1 ? (Util.boardWidth * Grid.tileSize - (Grid.tileSize * Util.COMM_ZONE)) : 0), Util.boardOffsetY, Color.GREEN, Grid.tileSize * Util.COMM_ZONE, Grid.tileSize * Util.boardHeight);
				}else{
					Unit comm = null;
					for(int i = 0; i < objects.size(); ++i){
						Unit cu = objects.get(i);
						if(cu!=null){
							if(cu.ownerID == Util.clientID && cu.getIsCommander()){
								comm = cu;
							}
						}
					}
					if(comm!=null){
						Color color = Color.GREEN;
						if(Util.distance(comm.getPoint(), getMousePoint()) > (Util.COMMANDER_PLACE_DIST * Grid.tileSize + Grid.tileSize / 2))color = Color.RED;
						Util.drawPolyZone(g, comm.getPoint().x, comm.getPoint().y, color, Util.COMMANDER_PLACE_DIST * Grid.tileSize);
					}
				}
			}
		}
		if(selCard>-1){
			if(this.rightClicking){
				selCard = -1;
			}
		}else{
			Point p = Util.getGRPoint(getMousePoint());
			Unit u = this.getUnitOnTile(p.x, p.y, roomID);
			int w = Properties.width/6;
			if(u != null){
				u.drawTooltip(g, mousePoint.x+30, mousePoint.y-100, w, (int) (w*1.5));
			}
		}
		this.renderGuis(g);
		
		for(int i = 0; i < Util.deckSize - Util.drawn; ++i){
			g.drawImage(Bank.cardbackClosed, Properties.width - (int)(dcw*1.5) - i * 2, Properties.height - dch / 4 * 3 - i - dch / 6, dcw, dch, null);
		}
		/*if(Util.turn!=Util.clientID && Util.turn!=-1){
			g.setColor(Util.transparent_dark);
			g.fillRect(0, Properties.height/2-Properties.height/10, Properties.width, Properties.height/5);
			g.setColor(Color.WHITE);
			g.setFont(Util.cooldownBold);
			g.drawString(objects.size()+"", Properties.width/2-g.getFontMetrics().stringWidth("Player "+Util.turn+"'s turn.")/2, Properties.height/2);
		}*/
		if(Util.turn == -1){
			g.setColor(Util.transparent_dark);
			g.fillRect(0, 0, Properties.width, Properties.height);
			g.setColor(Color.WHITE);
			g.setFont(Util.cooldownBold);
			g.drawString("Waiting for players...", Properties.width/2-g.getFontMetrics().stringWidth("Waiting for players...")/2, Properties.height/2);
		}
		if(gameEndStart != -1){
			int a = (int) ((double)((double)(System.currentTimeMillis() - gameEndStart) / (double)8000) * (double)255);
			if(a < 0)a = 0; if(a > 255)a = 255;
			g.setColor(new Color(0,0,0,a));
			g.fillRect(0, 0, Properties.width, Properties.height);
			int bannerW = (int) ((double)((double)(System.currentTimeMillis() - gameEndStart) / (double)8000) * (double)Properties.width / 2);
			int bannerH = (int) (bannerW * 0.4);
			g.drawImage(Util.victor == Util.clientID ? Bank.bannerVictory : Bank.bannerDefeat, Properties.width / 2 - bannerW / 2, Properties.height/2 - bannerH / 2, bannerW, bannerH, null);
			if(System.currentTimeMillis() - gameEndStart >= 8000){
				Display.currentScreen = new PanelMainmenu();
				Display.cam.refocus();
			} 
		}
	}

	public void doSpendAnimation(int cost) {
		for(int i = 0; i < 20; ++i){
			AnimatedParticle p = new AnimatedParticle(mousePoint.x, mousePoint.y, 20+rand.nextInt(10), 300+rand.nextInt(200)).setMotions(rand.nextInt(50)-rand.nextInt(50), rand.nextInt(50)-rand.nextInt(50)).addFrameSet("shatter");
			Display.currentScreen.particles.add(p);
		}
	}
}
