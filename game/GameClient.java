package org.author.game;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.author.game.Packet.PacketType;

public class GameClient extends Thread{
	
	InetAddress ip;
	DatagramSocket socket;
	
	public GameClient(String address){
		System.out.println("Client connecting to IP address "+address+"...");
		try {
			socket = new DatagramSocket();
			ip = InetAddress.getByName(address);
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(true){
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}			
			parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}
		
	private void parsePacket(byte[] data, InetAddress address, int port) {
		String d = new String(data).trim();
		String[] packetData = d.split("#");
		PacketType type = Packet.lookup(Integer.parseInt(packetData[0]));
		String name = null;
		PanelBase panel = Display.currentScreen;
		//name = packetData[1];
		Packet packet = null;
		int roomID = -1;
		switch(type){
		case INVALID:
			System.out.println("Invalid packet.");
		break;
		case JOINGAME:
			System.out.println("CLIENT has received confirmation from the server. Details were received. Game beginning.");
			int playerID = Integer.parseInt(packetData[3]);
			if(Util.clientID == -1){
				Util.clientID = playerID;
			}
			boolean hasPlayer = false;
			for(PlayerMP pl : Util.connectedPlayers){
				if(pl.name.equals(packetData[1]))hasPlayer=true;
			}
			if(!hasPlayer){
				PlayerMP player = new PlayerMP(Integer.parseInt(packetData[2]), packetData[1], address, port);
				player.color = Util.playerColors[playerID];
				player.playerID = playerID;
				Util.connectedPlayers.add(player);
				if(Display.currentScreen instanceof PanelMainmenu){
					Display.currentScreen = new PanelBoard();
					Display.cam.refocus();
				}	
			}
			System.out.println("Game has begun.");
		break;
		case CARDPACK:
			if(Display.currentScreen instanceof PanelPackOpen){
				PanelPackOpen p = ((PanelPackOpen)Display.currentScreen);
				int c1 = Integer.parseInt(packetData[1]);
				int c2 = Integer.parseInt(packetData[2]);
				int c3= Integer.parseInt(packetData[3]);
				int c4 = Integer.parseInt(packetData[4]);
				int c5 = Integer.parseInt(packetData[5]);
				Util.collection[c1]++;
				Util.collection[c2]++;
				Util.collection[c3]++;
				Util.collection[c4]++;
				Util.collection[c5]++;
				p.revealCards(c1, c2, c3, c4, c5);
			}
		break;
		case MISC:
			new Packet23Misc(Integer.parseInt(packetData[1]), packetData[2]).resolve();
		break;
		case LOGIN:
			int ver = Integer.parseInt(packetData[4]);
			Util.version = ver;
			Util.username = packetData[1];
			CardList.initLists();
			CardList.flushCards(ver);
			Display.currentScreen = new PanelMainmenu();
			Display.cam.refocus();
			File sFolder = new File(Bank.path+address.getHostAddress());
			sFolder.mkdirs();
		break;
		case PATCH:
			String pname = "patch_"+System.currentTimeMillis();
			File dir = new File(Bank.path+address.getHostAddress());
			File patch = new File(dir+"/"+pname+".PATCH");
			try {
				patch.createNewFile();
				Bank.setContentsRawdir(patch.getPath(), packetData[1]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Util.readPatches(dir);
		break;
		case INVITE:
			System.out.println("CLIENT has been invited to game. Now sending details...");
			Util.roomID = Integer.parseInt(packetData[1]);
			Packet00Login pkt = new Packet00Login(Util.username, Util.hero, 0, Util.deck, Util.roomID);
			pkt.write(this);
			System.out.println("Details sent.");
		break;
		case NOTIFY:
			int imgtype = Integer.parseInt(packetData[3]);
			Image img = (imgtype == 0 ? Bank.iconInfo : imgtype == 1 ? Bank.iconAlert : imgtype == 2 ? Bank.iconMail : Bank.ball);
			GUINotify notif = new GUINotify(img, packetData[1], packetData[2], 0, 0);
			Display.currentScreen.guis.add(notif);
			notif.maxTime = 10000;
			if(imgtype == 1 && Display.currentScreen instanceof PanelLogin){
				//((PanelLogin)Display.currentScreen).accname.text = "";
				((PanelLogin)Display.currentScreen).accpword.text = "";
			}
		break;
		case CHAT:
			Unit comm = Display.currentScreen.getCommanderForPlayer(Integer.parseInt(packetData[2]), roomID);
			Animation bubble = new Animation(Util.boardOffsetX+comm.posX*Grid.tileSize+Grid.tileSize, Util.boardOffsetY+comm.posY*Grid.tileSize+Grid.tileSize/4, 1000, Animation.TAG_SPEECH);
			bubble.setMaxTime(bubble.getMaxTime() * 2);
			bubble.setText(packetData[1]);
			Display.currentScreen.particles.add(bubble);
			if(Util.clientID!=Integer.parseInt(packetData[2]))
			Util.persistentGuis.add(new GUINotify(Bank.voidgift, "Message From "+Util.connectedPlayers.get(Integer.parseInt(packetData[2])).name, packetData[1], 0, 0));
		break;
		case PLAYCARD:
			int cardID = Integer.parseInt(packetData[2]);
			int PLAYCARDownerID = Integer.parseInt(packetData[1]);
			int PLAYCARDx = Integer.parseInt(packetData[3]);
			int PLAYCARDy = Integer.parseInt(packetData[4]);
			int PLAYCARDguid = Integer.parseInt(packetData[5]);
			Card.all[cardID].onPlayed(1, PLAYCARDx, PLAYCARDy, PLAYCARDguid, PLAYCARDownerID, -1);
			if(PLAYCARDownerID == Util.clientID)
			PanelBoard.removeCardFromHand(Card.all[cardID]);
		break;
		case CHANGETILE:
			Display.currentScreen.getGrid().setTileID(Integer.parseInt(packetData[2]), Integer.parseInt(packetData[3]), Integer.parseInt(packetData[1]));
		break;
		case SHOWPICKS:
			int owner = Integer.parseInt(packetData[1]);
			if(owner == Util.clientID){
				int src = Integer.parseInt(packetData[2]);
				int srcType = Integer.parseInt(packetData[3]);
				String msg = packetData[4];
				String[] parts = msg.split("x");
				Util.picks.clear();
				for(String s : parts){
					Util.picks.add(new CardShell(Card.all[Integer.parseInt(s)]));
				}
				Object source = null;
				if(srcType == 0)source = ((Display.currentScreen.getUnitFromGUID(src, roomID)));
				if(srcType == 1)source = ((PanelBoard)Display.currentScreen).hand.get(src);
				GUIPick gui = new GUIPick(source);
				Display.currentScreen.guis.add(gui);
			}
		break;
		case SELECTPICK:
			/*int SELECTPICKcardID = Integer.parseInt(packetData[1]);
			int SELECTPICKsource = Integer.parseInt(packetData[2]);
			int SELECTPICKsourceType = Integer.parseInt(packetData[3]);
			Unit src = (SELECTPICKsourceType == 0 ? Display.currentScreen.getUnitFromGUID(SELECTPICKsource, roomID) : null);
			for(Unit unit : Display.currentScreen.objects){
				for(CardAbility a : unit.getAbilities()){
					a.onCardConjured(unit, src, SELECTPICKcardID > 0 ? Card.all[SELECTPICKcardID] : null, -1);
				}
			}*/
		break;
		case MYSTERYCARD:
			int cost = Integer.parseInt(packetData[1]);
			int type1 = Integer.parseInt(packetData[2]);
			int cw = Properties.width / 4;
			int ch = (int)(cw*1.5f);
			Animation anim1 = new Animation((int) (Properties.width - (cw * 1.5f)), Properties.height / 10, 5000, Animation.TAG_MYSTERYCARD);
			anim1.setData(cost);
			anim1.setData1(type1);
			anim1.width = cw;
			anim1.height = ch;
			Display.currentScreen.particles.add(anim1);
		break;
		case MOVEUNIT:
			int MOVEUNITdx = Integer.parseInt(packetData[3]);
			int MOVEUNITdy = Integer.parseInt(packetData[4]);
			panel.getUnitFromGUID(Integer.parseInt(packetData[2]), roomID).hasMoved = true;
			Unit u = Display.currentScreen.getUnitFromGUID(Integer.parseInt(packetData[2]), roomID);
			u.setMoveAnim(new Animation(MOVEUNITdx * Grid.tileSize + Util.boardOffsetX, MOVEUNITdy * Grid.tileSize + Util.boardOffsetY, 500, Animation.TAG_UNITMOVE).setOrigin(u.posX * Grid.tileSize + Util.boardOffsetX, u.posY * Grid.tileSize + Util.boardOffsetY).setData(u.GUID));
			int spaces = 0;
			ArrayList<Point> tiles = null;
			if(u.getTilePosition() != null && MOVEUNITdx != 0 && MOVEUNITdy != 0){
				spaces = Util.distance(u.getTilePosition(), new Point(MOVEUNITdx, MOVEUNITdy));	
				tiles = u.generatePath(MOVEUNITdx, MOVEUNITdy);
			}
			panel.getUnitFromGUID(Integer.parseInt(packetData[2]), roomID).posX = MOVEUNITdx;
			panel.getUnitFromGUID(Integer.parseInt(packetData[2]), roomID).posY = MOVEUNITdy;
			u.setEnergy1(u.getEnergy() - 1);
			
			for(Unit t : Display.currentScreen.objects){
				for(CardAbility a : t.getAbilities()){
					a.onUnitMoved(t, u, spaces, tiles, -1);
				}
			}
			Util.resolveUnits(-1);
		break;
		case DRAWCARD:
			int card = Integer.parseInt(packetData[1]);
			int count = Integer.parseInt(packetData[2]);
			for(int i = 0; i < count; ++i)
			PanelBoard.handqueue.add(new CardShell(Card.all[card]));
		break;
		case TURNCHANGE:
			int turnChange = Integer.parseInt(packetData[1]);
			changeTurn(turnChange, Integer.parseInt(packetData[2]));
		break;
		case ATTACKUNIT:
			Unit u1 = Display.currentScreen.getUnitFromGUID(Integer.parseInt(packetData[1]), roomID);
			Unit u2 = Display.currentScreen.getUnitFromGUID(Integer.parseInt(packetData[2]), roomID);
			int w = Grid.tileSize;
			Util.drawParticleLine(new Point(Util.boardOffsetX+u1.posX*w+w/2, Util.boardOffsetY+u1.posY*w+w/2), new Point(Util.boardOffsetX+u2.posX*w+w/2, Util.boardOffsetY+u2.posY*w+w/2), 
					30, true, u1.getTemplate().getParticleType());
			u1.attack(1, u2);
			u1.setEnergy1(u1.getEnergy() - 1);
		break;
		case CARDABILITY:
			CardAbility ability = CardAbility.all[Integer.parseInt(packetData[3])];
			Unit caster = Display.currentScreen.getUnitFromGUID(Integer.parseInt(packetData[1]), roomID);
			Unit target = null;
			if(ability.getTargetType() == CardAbility.TARGET_UNIT)target = Display.currentScreen.getUnitFromGUID(Integer.parseInt(packetData[2]), roomID);
			if(ability.getTargetType() == CardAbility.TARGET_SELF)target = Display.currentScreen.getUnitFromGUID(Integer.parseInt(packetData[1]), roomID);
			int x = Integer.parseInt(packetData[4]);
			int y = Integer.parseInt(packetData[5]);
			byte costType = ability.getCostType();
			if(costType == CardAbility.COST_TYPE_ENERGY){
				caster.setEnergy1(caster.getEnergy() - ability.getCost());
			}
			if(costType == CardAbility.COST_TYPE_MANA){
				if(Util.clientID == caster.ownerID){
					Util.gold -= ability.getCost();
				}
			}
			if(costType == CardAbility.COST_TYPE_SPECIAL){
				Util.connectedPlayers.get(caster.ownerID).special -= ability.getCost();
			}
			if(costType == CardAbility.COST_TYPE_RESOURCE){
				Util.connectedPlayers.get(caster.ownerID).resource -= ability.getCost();
			}
			caster.addUsedAbility(ability);
			ability.onCast(caster, target, x, y, -1);
		break;
		case DISCONNECT:
			Display.currentScreen = new PanelLogin();
			Display.cam.refocus();
			Util.persistentGuis.add(new GUINotify(Bank.flamehelm, "Disconnected.", "You have been disconnected from the server.", 0, 0));
		break;
		case ENDGAME:
			Display.currentScreen.shake(3000, 7);
			Util.persistentGuis.add(new GUINotify(Bank.starFull, "Game Over!", Util.connectedPlayers.get(Integer.parseInt(packetData[1])).name+" is victorious!", 0, 0));
			Util.victor = Integer.parseInt(packetData[1]);
			((PanelBoard)Display.currentScreen).gameEndStart = System.currentTimeMillis();
			//Animation anim = new Animation(Properties.width / 2, Properties.height / 2, 8000, Animation.TAG_CARD);
			//anim.setData((Display.currentScreen.getCommanderForPlayer(Util.clientID, roomID) == null ? Card.apprentice.getId() : Display.currentScreen.getCommanderForPlayer(Util.clientID, roomID).getTemplate().getId()));
			//Display.currentScreen.particles.add(anim);
			Util.resetClient();
		break;
		case COMM:
			String rec = packetData[1];
			int commtype = Integer.parseInt(rec.split("%")[0]);
			int commdata = Integer.parseInt(rec.split("%")[1]);
			if(commtype == 1){ //Card Ability
				CardAbility c = CardAbility.all[commdata];
				Unit t = null;
				if(Integer.parseInt(rec.split("%")[3]) != -1){
					t = Display.currentScreen.getUnitFromGUID(Integer.parseInt(rec.split("%")[3]), roomID);
				}
				c.parseComm(rec.split("%")[6], Display.currentScreen.getUnitFromGUID(Integer.parseInt(rec.split("%")[2]), roomID), t, Integer.parseInt(rec.split("%")[4]), Integer.parseInt(rec.split("%")[5]), -1);
			}
			if(commtype == 2){ //Card
				Card c = Card.all[commdata];
				c.parseComm(rec.split("%")[6], 0, 0, Integer.parseInt(rec.split("%")[4]), Integer.parseInt(rec.split("%")[5]), roomID);
			}
		break;
		}
	}
	
	public static void changeTurn(int turnChange, int turn){
		if(Display.currentScreen instanceof PanelBoard){
			for(PersistentValue v : PersistentValue.getVars(-1)){
				if(!v.eternal)v.flagRemove = true;
			}
			boolean first = false;
			if(Util.turn == -1){
				first = true;
				Util.persistentGuis.add(new GUINotify(Bank.iconHealth, "Game started!", "It is now "+Util.connectedPlayers.get(turnChange).name+"'s turn.", 0, 0));
			}else
			Util.persistentGuis.add(new GUINotify(Bank.iconSpeed, "Turn changed", "It is now "+Util.connectedPlayers.get(turnChange).name+"'s turn.", 0, 0));
			Util.turn = turnChange;
			Util.turnStart = System.currentTimeMillis();
			for(int i = 0; i < Display.currentScreen.objects.size(); ++i){
				Unit u = Display.currentScreen.objects.get(i);
				if(u.getHealth() <= 0 && u.getHealthMax() > 0)u.flagRemove = true;
				if(!u.flagRemove){
					u.hasMoved = false;
					u.hasAttacked = false;
					u.addEnergy1(1);
					u.getUsedAbilities().clear();
					if(u.getAbilities().contains(CardAbility.eldritchPower)){
						u.getAbilities().remove(CardAbility.eldritchPower);
					}
					for(CardAbility ab : u.getAbilities()){
						ab.onTurnChange(turnChange, u, -1);
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
			PanelBoard panel = (PanelBoard) Display.currentScreen;
			if(panel!=null){
				ArrayList<CardShell> newHand = (ArrayList<CardShell>) panel.hand.clone();
				for(CardShell c : panel.hand){
					if(c.getCard() instanceof UnitTemplate){
						UnitTemplate ut = ((UnitTemplate)c.getCard());
						for(CardAbility ca : ut.getAbilities()){
							ca.onTurnChangeInHand(turnChange, turn, newHand, -1);
						}
					}
					if(c.getCard().getId() == Card.goldShard.getId()){
						newHand.remove(c);
					}
				}
				panel.hand = (ArrayList<CardShell>) newHand.clone();
			}
			
			/*for(int i = 0; i < Display.currentScreen.objects.size(); ++i){
				Unit u = Display.currentScreen.objects.get(i);
				if(u!=null){
					if(u.flagRemove)Display.currentScreen.objects.remove(i);
				}
			}*/
			if(Util.boardState == null){
				Util.boardState = (CardBoardstate) Card.stateNormal;
			}else{
				Util.boardState.onEndTurn(Display.currentScreen.objects, -1);
				Util.boardStateTimer--;
				if(Util.boardStateTimer <= 0){
					Util.boardState = (CardBoardstate) Card.stateNormal;
					Util.boardStateTimer = 0;
				}
			}
			for(int i = 0; i < Util.boardWidth; ++i){
				for(int j = 0; j < Util.boardHeight; ++j){
					GridBlock g = GridBlock.all[Display.currentScreen.getGrid().getTileID(i, j)];
					g.onTurnChange(i, j, -1);
				}
			}
			if(Util.manaMode == 0){
				if(turnChange == 1){
					if(Util.maxGold < Util.maxTurns)
						Util.maxGold++;
				}
				Util.gold = Util.maxGold;
			}else if(Util.manaMode == 1){
				if(turnChange == 1){
					Util.goldPerTurn++;
					Util.gold += Util.goldPerTurn;
					Util.goldRed++;
					Util.goldGreen++;
					Util.goldBlue++;
				}
			}
			Util.resolveUnits(-1);
		}
	}

	public void send(byte[] data){
		DatagramPacket packet = new DatagramPacket(data, data.length, ip, Properties.port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
