package org.author.game;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.author.game.Packet.PacketType;

public class GameServer {

	ArrayList<PlayerMP> clients = new ArrayList<PlayerMP>();
	ArrayList<Unit> units = new ArrayList<Unit>();
	long turnStart = System.currentTimeMillis();
	int maxTurnTime = Util.maxTurnTime, playerTurn = -1;
	private ArrayList<String> log = new ArrayList<String>();
	int currentGUID = 0;
	int playersToStart = 2;
	int baseGold = 0, turn = 0;
	private ArrayList<PluginEvent> events = new ArrayList<PluginEvent>();
	public Grid grid = new Grid(Util.boardWidth, null);
	private ArrayList<Card> picks = new ArrayList<Card>();
	ArrayList<Card> castSpells = new ArrayList<Card>();
	int roomID = 0;
	boolean gameover = false;
	private int winGold = 100;
	private int goldPerTurn = 0;
	static ArrayList<PersistentValue> vars = new ArrayList<PersistentValue>();
	
	public void setPicks(ArrayList<Card> newpicks){
		picks.clear();
		for(Card c : newpicks){
			picks.add(c);
		}
	}
	
	public MainServer getMainServer(){
		return Bank.server;
	}
	
	public void addPick(Card c){
		picks.add(c);
	}
	
	public int getId(){
		return roomID;
	}
	
	public GameServer(){
		for(int i = 0; i < grid.getCore()[0].length; ++i){
			for(int j = 0; j < grid.getCore()[1].length; ++j){
				grid.setTileID(i, j, GridBlock.ground.getID());
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
		
		log.add("Server room on port "+Properties.port);
		log.add("...");
	}

	public GameServer(int newID) {
		this();
		this.roomID = newID;
		log.add("Game Room ["+newID+"] has started.");
	}

	public void run(){
		log.add("Checking for plugins...");
		File[] plugins = new File(Bank.path+"plugins/").listFiles();
		if(plugins.length > 0){
			for(int i = 0; i < plugins.length; ++i){
				File f = new File(Bank.path+"plugins/"+plugins[i].getName());
				if(f.isFile() && f.exists()){
					this.loadPlugin(f, f.getName());
				}
			}
		}
		/*while(true){
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}*/
	}
	
	public void startGame(){
		log.add("> Game has begun");
		this.changeTurn(0);
		for(PlayerMP pl : clients){
			playCommander(pl);
			ArrayList<CardShell> newdeck = (ArrayList<CardShell>) pl.deck.clone();
			for(CardShell card : pl.deck){
				if(card!=null){
					if(card.getCard() != null){
						if(card.getCard().isUnit()){
							if(((UnitTemplate)card.getCard()).getAbilities().contains(CardAbility.titanichunger)){
								int count = 0;
								for(CardShell c : pl.deck){
									if(c.getCost() == 4 && count < 10){
										newdeck.remove(c);
										count++;
									}
								}
							}
						}
					}
				}
			}
			pl.deck = newdeck;
		}
		int GUID1 = this.generateGUID();
		int x1 = Util.boardWidth / 2 - 1, y1 = Util.boardHeight - 2;
		Card.goldMine.onPlayed(0, x1, y1, GUID1, -999, roomID);
		Packet02PlayCard packet1 = new Packet02PlayCard(-999, Card.goldMine.getId(), x1, y1, GUID1);
		packet1.write(this);
		
		int GUID2 = this.generateGUID();
		int x2 = Util.boardWidth / 2, y2 = 1;
		Card.goldMine.onPlayed(0, x2, y2, GUID2, -999, roomID);
		Packet02PlayCard packet2 = new Packet02PlayCard(-999, Card.goldMine.getId(), x2, y2, GUID2);
		packet2.write(this);
		
		
		int[][] startingUnits = {
				//{1, 1, Card.goldVein.getId()},
				//{5, 2, Card.goldVein.getId()},
				{Util.boardWidth - 2, Util.boardHeight - 2, Card.goldVein.getId()},
				{Util.boardWidth - 6, Util.boardHeight - 3, Card.goldVein.getId()},
				{0, Util.boardHeight - 1, Card.tree.getId()},
				{2, Util.boardHeight - 1, Card.tree.getId()},
				{4, Util.boardHeight - 1, Card.tree.getId()},
				{Util.boardWidth - 1, 0, Card.tree.getId()},
				{Util.boardWidth - 3, 0, Card.tree.getId()},
				{Util.boardWidth - 5, 0, Card.tree.getId()},

		};
		
		for(int[] arr : startingUnits){
			int GUID = this.generateGUID();
			int card = arr[2];
			int x = arr[0], y = arr[1];
			Card.all[card].onPlayed(0, x, y, GUID, -999, roomID);
			Packet02PlayCard packet = new Packet02PlayCard(-999, card, x, y, GUID);
			packet.write(this);
		}
	}
	
	public void changeTurn(int num){
		this.playerTurn = num;
		turnStart = System.currentTimeMillis();
		/*for(int i = 0; i < units.size(); ++i){
			Unit u = units.get(i);
			if(u!=null){
				if(u.flagRemove || u.getHealth() <= 0)units.remove(i);
			}
		}*/
		for(PersistentValue v : PersistentValue.getVars(roomID)){
			if(!v.eternal)v.flagRemove = true;
		}
		for(int i = 0; i < units.size(); ++i){
			Unit u = units.get(i);
			if(u!=null){
				u.addEnergy1(1);
				u.hasMoved = false;
				u.hasAttacked = false;
				u.getUsedAbilities().clear();
				if(u.getHealth() <= 0)u.flagRemove = true;
				if(!u.flagRemove){
					if(u.getAbilities().contains(CardAbility.eldritchPower)){
						u.getAbilities().remove(CardAbility.eldritchPower);
					}
					for(CardAbility ab : u.getAbilities()){
						ab.onTurnChange(playerTurn, u, this.getId());
					}
					if(u.getEffects().size() > 0){
						for(int j = 0; j < u.getEffects().size(); ++j){
							Effect e = u.getEffects().get(j);
							if(e!=null)
							e.onTurnChange(u, turn);
						}
					}
				}
			}
		}
		for(int i = 0; i < events.size(); ++i){
			if(events.get(i)!=null)
			events.get(i).execute(this);
		}
		if(num == 1 && turn < Util.maxTurns){
			turn++;
		}
		for(PlayerMP player : clients){
			ArrayList<CardShell> newHand = (ArrayList<CardShell>) player.hand.clone();
			if(Util.manaMode == 0){
				if(num == 1){
					if(player.baseGold < Util.maxTurns)
						player.baseGold++;
				}
				player.gold = player.baseGold;
			}
			if(Util.manaMode == 1){
				if(num == 1){
					goldPerTurn++;
					player.gold += goldPerTurn ;
					player.goldRed++;
					player.goldGreen++;
					player.goldBlue++;
				}
			}
			for(CardShell c : player.hand){
				if(c.getCard() instanceof UnitTemplate){
					UnitTemplate ut = ((UnitTemplate)c.getCard());
					for(CardAbility ca : ut.getAbilities()){
						ca.onTurnChangeInHand(player.playerID, turn, newHand, this.getId());
					}
				}
				if(c.getCard().getId() == Card.deathlotus.getId()){
					Unit comm = Bank.server.getCommander(player);
					comm.hurt(2, comm);
					player.deck.add(new CardShell(Card.deathlotus));
					Card.deathlotus.sendComm(comm.GUID+","+2, player.playerID, comm.GUID, comm.posX, comm.posY, this.getId());
				}
				if(c.getCard().getId() == Card.goldShard.getId()){
					newHand.remove(c);
				}
			}
			player.hand = (ArrayList<CardShell>) newHand.clone();
			if(player.playerID == this.playerTurn){
				this.drawCard(player, -1, 1);
			}
		}
		if(Util.boardState == null){
			Util.boardState = (CardBoardstate) Card.stateNormal;
		}else{
			Util.boardState.onEndTurn(units, this.getId());
			Util.boardStateTimer--;
			if(Util.boardStateTimer <= 0){
				Util.boardState = (CardBoardstate) Card.stateNormal;
				Util.boardStateTimer = 0;
			}
		}
		for(int i = 0; i < Util.boardWidth; ++i){
			for(int j = 0; j < Util.boardHeight; ++j){
				GridBlock g = GridBlock.all[grid.getTileID(i, j)];
				g.onTurnChange(i, j, roomID);
			}
		}
		Util.resolveUnits(this.getId());
		Packet04ChangeTurn pkt = new Packet04ChangeTurn(playerTurn, turn);
		pkt.write(this);
	}
	
	boolean hasCard(PlayerMP pl, int id){
		for(int i = 0; i < pl.hand.size(); ++i){
			if(pl.hand.get(i) != null){
				CardShell shell = pl.hand.get(i);
				if(shell.getID()==id)return true;
			}
		}
		return false;
	}
	
	private void removeCard(PlayerMP pl, int id){
		for(int i = 0; i < pl.hand.size(); ++i){
			CardShell shell = pl.hand.get(i);
			if(shell.getID()==id){
				pl.hand.remove(i);
				break;
			}
		}
	}
	
	public PlayerMP getSender(InetAddress address, int port){
		for(PlayerMP pl : clients){
			if(pl.ip.equals(address) && pl.port == port){
				return pl;
			}
		}
		return null;
	}
	
	public void playCommander(PlayerMP player){
		Card card = Card.all[player.commanderID];
		int PLAYCARDguid = this.generateGUID();
		int TENTguid = this.generateGUID();
		player.hand.add(new CardShell(Card.tent));
		if(player.playerID == 0){
			card.onPlayed(0, 1 + 2, Util.boardHeight / 2, PLAYCARDguid, player.playerID, roomID);
			Packet02PlayCard packet = new Packet02PlayCard(player.playerID, player.commanderID, 1 + 2, Util.boardHeight / 2, PLAYCARDguid);
			packet.write(this);
			Card.tent.onPlayed(0, 1, Util.boardHeight / 2, TENTguid, player.playerID, roomID);
			Packet02PlayCard packet1 = new Packet02PlayCard(player.playerID, Card.tent.getId(), 1, Util.boardHeight / 2, TENTguid);
			packet1.write(this);
		}
		if(player.playerID == 1){
			card.onPlayed(0, Util.boardWidth - 4, Util.boardHeight / 2, PLAYCARDguid, player.playerID, roomID);
			Packet02PlayCard packet = new Packet02PlayCard(player.playerID, player.commanderID, Util.boardWidth - 4, Util.boardHeight / 2, PLAYCARDguid);
			packet.write(this);
			Card.tent.onPlayed(0, Util.boardWidth - 2, Util.boardHeight / 2, TENTguid, player.playerID, roomID);
			Packet02PlayCard packet1 = new Packet02PlayCard(player.playerID, Card.tent.getId(), Util.boardWidth - 2, Util.boardHeight / 2, TENTguid);
			packet1.write(this);
		}
		this.removeCard(player, card.getId());
		this.removeCard(player, Card.tent.getId());
	}
	
	private PlayerMP getPlayer(int id){
		return clients.get(id);
	}
	
	public void playCard(int playerID, Card card, int x, int y){
		PlayerMP player = this.getPlayer(playerID);
		int PLAYCARDguid = this.generateGUID();
		card.onPlayed(0, x, y, PLAYCARDguid, player.playerID, roomID);
		Packet02PlayCard packet = new Packet02PlayCard(player.playerID, card.getId(), x, y, PLAYCARDguid);
		packet.write(this);
	}
	
	void parsePacket(byte[] data, String d, String[] packetData, InetAddress address, int port) {
		PacketType type = Packet.lookup(packetData[0]);
		Packet packet;
		PlayerMP player = null;
		int senderID = ((this.getSender(address, port) != null) ? this.clients.indexOf(this.getSender(address, port)) : 0);
		switch(type){
			case INVALID:
			break;
			case JOINGAME:
				log.add("> "+packetData[1]+" joined game room "+roomID+".");
				packet = new Packet00Login(packetData[1], Integer.parseInt(packetData[2]), clients.size(), "", this.getId());
				player = new PlayerMP(Integer.parseInt(packetData[2]), packetData[1], address, port);
				System.out.println("Player connected with username "+((Packet00Login)packet).username);
				player.currentRoom = this.getId();
				this.getMainServer().getPlayer(player.name).currentRoom = player.currentRoom;
				registerConnection(player, (Packet00Login)packet);
				send(packet.getData(), address, port);
				getLog().add("> Player "+((Packet00Login)packet).username+" connected with ID "+(clients.size()-1)+" || "+address.getHostAddress()+":"+port);
				String deck = packetData[4].split("&")[0];
				int hero = Integer.parseInt(packetData[2]);
				player.commanderID = hero;
				player.readCollection();
				byte[] collection = player.getCollection();
				if(deck!=null){
					String[] cards = deck.split("%");
					for(String s : cards){
						int id = Integer.parseInt(s.trim());
						Card card = Card.all[id];
						if(card != null){
							if(collection[id] > 0 || Card.all[id].isBasic()){
								player.deck.add(new CardShell(Card.all[id]));
							}else{
								this.log.add("> Illegal card '"+Card.all[id].getName()+"' found for player "+player.name+"!");
								player.deck.add(new CardShell(Card.filler));
							}
						}else{
							this.log.add("> NULL card '"+Card.all[id].getName()+"' found for player "+player.name+"!");
							player.deck.add(new CardShell(Card.filler));
						}
					}
				}else{
					for(int i = 0; i < 40; ++i){
						player.deck.add(new CardShell(Card.getUsableCard(-1)));
					}
				}
				drawCard(player, player.commanderID, 1);
				drawCard(player, -1, 1);
				drawCard(player, -1, 1);
				drawCard(player, -1, 1);
				if(playerTurn != -1){
					Packet04ChangeTurn pkt = new Packet04ChangeTurn(this.playerTurn, turn);
					this.send(pkt.getData(), address, port);
				}else{
					if(clients.size() >= playersToStart){
						startGame();
					}
				}
				for(PlayerMP pl : clients){
					Packet00Login pkt = new Packet00Login(pl.name, pl.commanderID, pl.playerID, "", this.getId());
					pkt.write(this);
				}
			break;
			case CHAT:
				packet = new Packet12Chat(packetData[1], senderID);
				packet.write(this);
				log.add(clients.get(senderID).name+ " sending message "+packetData[1]);
			break;
			case PLAYCARD:
				int cardID = Integer.parseInt(packetData[2]);
				int PLAYCARDownerID = senderID;
				int PLAYCARDx = Integer.parseInt(packetData[3]);
				int PLAYCARDy = Integer.parseInt(packetData[4]);
				if(playerTurn == senderID){
					if(this.hasCard(clients.get(PLAYCARDownerID), cardID)){
						if(this.getSender(address, port).canAfford(cardID)){
							Card card = Card.all[cardID];
							if(card.getUnit()!=null){
								if(Util.countUnitsForPlayer(units, senderID) <= Util.maxUnits |! card.getUnit().shouldCount()){
									if(card.getUnit().getRank() == UnitTemplate.RANK_COMMANDER){
										boolean canPlace = false;
										if(this.getSender(address, port).playerID == 0){
											if(PLAYCARDx <= Util.COMM_ZONE)canPlace =  true;
										}
										else{
											if(PLAYCARDx >= (Util.boardWidth - Util.COMM_ZONE))canPlace =  true;
										}
										if(canPlace){
											int PLAYCARDguid = this.generateGUID();
											card.onPlayed(0, PLAYCARDx, PLAYCARDy, PLAYCARDguid, PLAYCARDownerID, roomID);
											packet = new Packet02PlayCard(PLAYCARDownerID, cardID, PLAYCARDx, PLAYCARDy, PLAYCARDguid);
											packet.write(this);
											this.getSender(address, port).spend(cardID);
										}
									}else{
										if(this.getCommander(clients.get(PLAYCARDownerID))!=null){
											if(Util.distance(new Point(getCommander(clients.get(PLAYCARDownerID)).posX, getCommander(clients.get(PLAYCARDownerID)).posY), new Point(PLAYCARDx, PLAYCARDy)) <= Util.COMMANDER_PLACE_DIST){
												int PLAYCARDguid = this.generateGUID();
												card.onPlayed(0, PLAYCARDx, PLAYCARDy, PLAYCARDguid, PLAYCARDownerID, roomID);
												packet = new Packet02PlayCard(PLAYCARDownerID, cardID, PLAYCARDx, PLAYCARDy, PLAYCARDguid);
												packet.write(this);
												this.getSender(address, port).spend(cardID);
											}
										}
									}
								}
							}else{
								card.onPlayed(0, PLAYCARDx, PLAYCARDy, -1, PLAYCARDownerID, roomID);
								packet = new Packet02PlayCard(PLAYCARDownerID, cardID, PLAYCARDx, PLAYCARDy, -1);
								packet.write(this);
							}
							this.removeCard(clients.get(senderID), cardID);
							log.add("> Player "+clients.get(PLAYCARDownerID).name+" played card "+card.getName());
						}
					}
				}
			break;
			case MOVEUNIT:
				Unit u = getUnitFromGUID(Integer.parseInt(packetData[2]));
				int MOVEUNITdx = Integer.parseInt(packetData[3]);
				int MOVEUNITdy = Integer.parseInt(packetData[4]);
				int MOVEUNITownerID = senderID;
				int spaces = 0;
				ArrayList<Point> tiles = null;
				if(u!=null){
					if(u.getTilePosition() != null && MOVEUNITdx != 0 && MOVEUNITdy != 0){
						spaces = Util.distance(u.getTilePosition(), new Point(MOVEUNITdx, MOVEUNITdy));
						tiles = u.generatePath(MOVEUNITdx, MOVEUNITdy);
					}
					if(playerTurn == senderID && u.getEnergy() >= CardAbility.move.getCost()){
						u.setEnergy1(u.getEnergy() - 1);
						if(Util.distance(new Point(u.posX, u.posY), new Point(MOVEUNITdx, MOVEUNITdy)) <= u.getSpeed() &! u.hasMoved && u.ownerID == MOVEUNITownerID){
							u.hasMoved = true;
							u.posX = MOVEUNITdx;
							u.posY = MOVEUNITdy;
							packet = new Packet03MoveUnit(MOVEUNITownerID, Integer.parseInt(packetData[2]), MOVEUNITdx, MOVEUNITdy);
							packet.write(this);
							log.add("> Player "+clients.get(MOVEUNITownerID).name+" moved unit #"+u.GUID);
						}else{
							
						}
					}
					for(Unit t : Display.currentScreen.objects){
						for(CardAbility a : t.getAbilities()){
							a.onUnitMoved(t, u, spaces, tiles, this.getId());
						}
					}
				}
				Util.resolveUnits(this.getId());
			break;
			case DISCONNECT:
				packet = new Packet01Disconnect(d);
				log.add(address.getHostAddress()+" left the game.");
				breakConnection(player, (Packet01Disconnect)packet);
			break;
			case TURNCHANGE:
				int turnChange = Integer.parseInt(packetData[1]);
				if(turnChange == -2){
					turnChange = (playerTurn+1 >= clients.size() ? 0 : playerTurn+1);
				}
				if(playerTurn == senderID){
					this.changeTurn(turnChange);
					log.add("> Turn change accepted from sender "+senderID);
				}else{
					log.add("> Turn change denied from sender "+senderID);
				}
			break;
			case DRAWCARD:
				int DRAWCARDcardID = Integer.parseInt(packetData[1]);
				packet = new Packet05DrawCard(DRAWCARDcardID, Integer.parseInt(packetData[2]));
				packet.write(this);
			break;
			case SHOWPICKS:
				int SHOWPICKSownerID = Integer.parseInt(packetData[1]);
				int SHOWPICKSsource = Integer.parseInt(packetData[2]);
				int SHOWPICKSsourcetype = Integer.parseInt(packetData[3]);
				packet = new Packet14ShowPicks(SHOWPICKSownerID, SHOWPICKSsource, SHOWPICKSsourcetype, picks);
				packet.write(this);
			break;
			case SELECTPICK:
				int SELECTPICKcardID = Integer.parseInt(packetData[1]);
				int SELECTPICKsource = Integer.parseInt(packetData[2]);
				int SELECTPICKsourceType = Integer.parseInt(packetData[3]);
				boolean valid = false;
				for(Card c : picks){
					if(c.getId() == SELECTPICKcardID){
						valid = true;
					}
				}
				if(valid && playerTurn == senderID){
					this.drawCard(clients.get(senderID), SELECTPICKcardID, 1);
					Card card = Card.all[SELECTPICKcardID];
					int t = 0;
					if(card.isUnit()){
						t = card.getUnit().getRank();
					}else{
						if(card.isSpell())t = 4;
						if(card.isEquipment())t = 5;
						if(card.isStructure())t = 6;
						if(card.isBoardState())t = 7;
					}
					if(SELECTPICKsourceType == 0){
						Unit src = getUnitFromGUID(SELECTPICKsource);
						for(Unit unit : units){
							for(CardAbility a : unit.getAbilities()){
								a.onCardConjured(unit, src, card, this.getId());
							}
						}
					}
					
					for(PlayerMP pl : clients){
						if(pl.ip ==  address){
							send(data, pl.ip, pl.port);
						}else{
							send(new Packet15SelectPick(-1, SELECTPICKsource, SELECTPICKsourceType).getData(), pl.ip, pl.port);
						}
					}
					
					Packet16ShowMysteryCard showpkt = new Packet16ShowMysteryCard(card.getCost(), t);
					showpkt.write(this);
					this.picks.clear();
				}
			break;
			case ATTACKUNIT:
				if(playerTurn == senderID){
					Unit u1 = getUnitFromGUID(Integer.parseInt(packetData[1]));
					Unit u2 = getUnitFromGUID(Integer.parseInt(packetData[2]));
					if(u1!=null && u2!=null){
						if(Util.distance(new Point(u1.posX, u1.posY), new Point(u2.posX, u2.posY)) <= 2 && u1.getEnergy() >= CardAbility.attack.getCost()){
							u1.setEnergy1(u1.getEnergy() - 1);
							u1.attack(0, u2);
							packet = new Packet06AttackUnit(u1.GUID, u2.GUID);
							packet.write(this);
						}
					}
				}
			break;
			case CARDABILITY:
				if(playerTurn == senderID){
					CardAbility ability = CardAbility.all[Integer.parseInt(packetData[3])];
					Unit caster = getUnitFromGUID(Integer.parseInt(packetData[1]));
					Unit target = null;
					PlayerMP sender = this.getSender(address, port);
					if(ability.getTargetType() == CardAbility.TARGET_UNIT)target = getUnitFromGUID(Integer.parseInt(packetData[2]));
					if(ability.getTargetType() == CardAbility.TARGET_SELF)target = getUnitFromGUID(Integer.parseInt(packetData[1]));
					int x = Integer.parseInt(packetData[4]);
					int y = Integer.parseInt(packetData[5]);
					if(caster!=null){
						//byte costType = caster.getCostTypeOverride()[caster.getAbilities().indexOf(ability)];
						byte costType = ability.getCostType();
						if(caster.getResourceForCostType(costType) >= ability.getCost() && (ability.isUnbound() |! caster.getUsedAbilities().contains(ability))){
							if(ability.getTargetType() == CardAbility.TARGET_UNIT && target != null){
								if(Util.distance(new Point(caster.posX, caster.posY), new Point(target.posX, target.posY)) <= ability.getRange()){
									ability.onCast(caster, target, x, y, this.getId());
									packet = new Packet07CardAbility(caster.GUID, target.GUID, Integer.parseInt(packetData[3]), x, y);
									packet.write(this);
									if(costType == CardAbility.COST_TYPE_ENERGY){
										caster.setEnergy1(caster.getEnergy() - ability.getCost());
									}
									if(costType == CardAbility.COST_TYPE_MANA){
										sender.gold -= ability.getCost();
									}
									if(costType == CardAbility.COST_TYPE_SPECIAL){
										sender.special -= ability.getCost();
									}
									if(costType == CardAbility.COST_TYPE_RESOURCE){
										sender.resource -= ability.getCost();
									}
									caster.addUsedAbility(ability);
								}
							}
							if(ability.getTargetType() == CardAbility.TARGET_SELF){
								ability.onCast(caster, caster, x, y, this.getId());
								packet = new Packet07CardAbility(caster.GUID, caster.GUID, Integer.parseInt(packetData[3]), x, y);
								packet.write(this);
								if(costType == CardAbility.COST_TYPE_ENERGY){
									caster.setEnergy1(caster.getEnergy() - ability.getCost());
								}
								if(costType == CardAbility.COST_TYPE_MANA){
									sender.gold -= ability.getCost();
								}
								if(costType == CardAbility.COST_TYPE_SPECIAL){
									sender.special -= ability.getCost();
								}
								if(costType == CardAbility.COST_TYPE_RESOURCE){
									sender.resource -= ability.getCost();
								}
								caster.addUsedAbility(ability);
							}
							if(ability.getTargetType() == CardAbility.TARGET_AREA){
								if(Util.distance(new Point(caster.posX, caster.posY), new Point(x, y)) <= ability.getRange()){
									ability.onCast(caster, null, x, y, this.getId());
									packet = new Packet07CardAbility(caster.GUID, 0, Integer.parseInt(packetData[3]), x, y);
									packet.write(this);
									if(costType == CardAbility.COST_TYPE_ENERGY){
										caster.setEnergy1(caster.getEnergy() - ability.getCost());
									}
									if(costType == CardAbility.COST_TYPE_MANA){
										sender.gold -= ability.getCost();
									}
									if(costType == CardAbility.COST_TYPE_SPECIAL){
										sender.special -= ability.getCost();
									}
									if(costType == CardAbility.COST_TYPE_RESOURCE){
										sender.resource -= ability.getCost();
									}
									caster.addUsedAbility(ability);
								}
							}
						}
					}
				}
			break;
		}
	}
	
	public Unit getCommander(PlayerMP p){
		int id = p.playerID;
		for(int i = 0; i < units.size(); ++i){
			Unit u = units.get(i);
			if(u.ownerID == id && u.getIsCommander()){
				return u;
			}
		}
		return null;
	}
	
	public void drawCard(PlayerMP pl, int cardID, int cardCount){
		if(cardID == -1){
			for(int i = 0; i < cardCount; ++i){
				int drawnCardID = getCard(pl);
				pl.hand.add(new CardShell(Card.all[drawnCardID]));
				Packet05DrawCard packet = new Packet05DrawCard(drawnCardID, 1);
				this.send(packet.getData(), pl.ip, pl.port);
			}
		}else{
			for(int i = 0; i < cardCount; ++i)
			pl.hand.add(new CardShell(Card.all[cardID]));
			Packet05DrawCard packet = new Packet05DrawCard(cardID, cardCount);
			this.send(packet.getData(), pl.ip, pl.port);
		}
	}
	
	public int getCard(PlayerMP pl){
		if(pl.deck.size() > 0){
			int r = Util.rand.nextInt(pl.deck.size());
			if(pl.deck.get(r).getCard()!=null){
				int ret = pl.deck.get(r).getID();
				pl.deck.remove(r);
				return ret;
			}
		}
		return Card.filler.getId();
	}
	
	public int getIDForPlayer(String name){
		for(PlayerMP p : clients){
			if(name.equalsIgnoreCase(p.name)){
				return clients.indexOf(p);
			}
		}
		return 0;
	}
	
	public Unit getUnitFromGUID(int guid){
		for(int i = 0; i < units.size(); ++i){
			if(units.get(i).GUID == guid)return units.get(i);
		}
		return null;
	}

	private void breakConnection(PlayerMP player, Packet01Disconnect packet) {	
		clients.remove(getPlayerIndex(packet.username));
		packet.write(this);
	}

	private void registerConnection(PlayerMP player, Packet00Login packet) {
		boolean connected = false;
		for(PlayerMP p : clients){
			if(player.name.equalsIgnoreCase(p.name)){
				if(p.ip == null){
					p.ip = player.ip;
				}
				if(p.port == -1){
					p.port = player.port;
				}
				connected = true;
			}else{
				send(packet.getData(), p.ip, p.port);
			}
		}
		if(!connected){
			PlayerMP newplayer = player;
			newplayer.playerID = clients.size();
			clients.add(newplayer);
		}
	}
	
	public PlayerMP getPlayer(String user){
		for(int i = 0; i < clients.size(); ++i){
			return clients.get(i).name == user ? clients.get(i) : null;
		}
		return null;
	}
	
	public int getPlayerIndex(String user){
		int index = 0;
		for(int i = 0; i < clients.size(); ++i){
			if(clients.get(i).name.equals(user))
				break;
		}
		index++;
		return index;
	}

	public void send(byte[] data, InetAddress ip, int port){
		DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
		try {
			this.getMainServer().socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendToAll(byte[] data) {
		for(PlayerMP p : clients){
			send(data, p.ip, p.port);
		}
	}

	public ArrayList<String> getLog() {
		return log;
	}

	public void setLog(ArrayList<String> log) {
		this.log = log;
	}

	public int generateGUID() {
		++currentGUID;
		return currentGUID-1;
	}

	public Unit addUnit(UnitTemplate ut, int x, int y, int ownerID) {
		Unit u = new Unit(ut, x, y, roomID);
		u.GUID = this.generateGUID();
		u.ownerID = ownerID;
		units.add(u);
		u.getTemplate().onPlayed(0, u.posX, u.posY, u.GUID, u.ownerID, roomID);
		Packet02PlayCard packet = new Packet02PlayCard(-1 + (-u.ownerID), u.getTemplate().getId(), u.posX, u.posY, u.GUID);
		packet.write(this);
		return u;
	}

	public void loadEventsFrom(String events) {
		String[] list = events.split("%");
		for(int i = 0; i < list.length; ++i){
			String s = list[i];
			if(s!=null){
				if(!s.isEmpty()){
					this.events.add(PluginEvent.read(s));
				}
			}
		}
	}

	public void loadPlugin(File file, String name){
		getLog().add("Loading plugin \""+name+"\"...");
		String all = Bank.readAll(file);
		loadEventsFrom(all.replace(" ", "").replace("	", ""));
		getLog().add("Plugin loaded successfully.");
	}

	public Unit getUnitOnTile(int i, int j) {
		for(Unit u : units){
			if(u.posX == i && u.posY == j)return u;
		}
		return null;
	}

	public void addPlayer(PlayerMP p1) {
		p1.currentRoom = this.getId();
		Packet19GameInvite pkt = new Packet19GameInvite(this.getId(), p1.name);
		this.send(pkt.getData(), p1.ip, p1.port);
		//clients.add(p1);
		/*if(clients.size() >= this.playersToStart){
			//this.startGame();
		}*/
	}

	public void endGame(int victor) {
		gameover = true;
		System.out.println("Ending game on room "+roomID);
		Packet11EndGame pkt = new Packet11EndGame(victor);
		pkt.write(this);
		Packet23Misc pkt1 = new Packet23Misc(3, winGold +"");
		PlayerMP winner = this.clients.get(victor);
		this.send(pkt1.getData(), winner.ip, winner.port);
		this.getMainServer().modifyAccountValue(winner.name, 5, ""+(Integer.parseInt(this.getMainServer().getAccountInfo(winner.name)[5]) + winGold));
		winner.setCoins(winner.getCoins() + winGold);
		this.getMainServer().purgeRoom(this);
	}

	public void removePlayer(PlayerMP player) {
		for(PlayerMP p : clients){
			if(p.name.equalsIgnoreCase(player.name)){
				p.currentRoom = -1;
				this.endGame((clients.indexOf(p) == 0 ? 1 : 0));
				clients.remove(p);
				break;
			}
		}
	}

	public boolean isGameOver() {
		return gameover;
	}
}
