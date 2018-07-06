package org.author.game;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.author.game.Packet.PacketType;

public class MainServer extends Thread{


	DatagramSocket socket;
	ArrayList<GameServer> rooms = new ArrayList<GameServer>();
	ArrayList<PlayerMP> connected = new ArrayList<PlayerMP>();
	//GameServer authRoom = new GameServer();
	private ArrayList<String> log = new ArrayList<String>();
	String hostIP = Analysis.getKey(new File(Bank.path+"server.properties"), "ip");
	private ArrayList<PluginEvent> events = new ArrayList<PluginEvent>();
	private ArrayList<PlayerMP> queue = new ArrayList<PlayerMP>();
	static int MAX_GAMEROOMS = Integer.parseInt(Analysis.getKey(new File(Bank.path+"server.properties"), "maxRooms"));
	static int VERSION = Integer.parseInt(Analysis.getKey(new File(Bank.path+"server.properties"), "gameVersion"));
	private File patchDirectory = new File(Bank.path+"server_patches");
	
	private byte ACCOUNT_NAME = 0, ACCOUNT_PASSWORD = 1, ACCOUNT_ID = 2, ACCOUNT_EMAIL = 3, ACCOUNT_PACKS = 4, ACCOUNT_GOLD = 5;
	
	public MainServer(){
		Util.version = VERSION;
		Bank.init();
		CardList.initLists();
		Util.readPatches(patchDirectory);
		CardList.flushCards(VERSION);
		File set = new File(Bank.path+"data/coreset"+Properties.fileExt);
		String cards = Bank.readAll(set);
		String[] blocks = cards.split("&")[0].split("%");
		for(String s : blocks){
			int card = Integer.parseInt(s.trim());
			if(Card.all[card]!=null){
				Card.all[card].setBasic(true);
			}
		}
		log("Server starting on port "+Properties.port);
		log("...");
		try {
			//InetAddress address = InetAddress.getByName(hostIP);
			socket = new DatagramSocket(Properties.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		log("Server started successfully on IP Address "+hostIP+".");
	}
	
	public Card generateCardForPack(CardSet set){
		ArrayList<Card> avail_common = new ArrayList<Card>();
		ArrayList<Card> avail_uncommon = new ArrayList<Card>();
		ArrayList<Card> avail_mythic = new ArrayList<Card>();
		ArrayList<Card> avail_regal = new ArrayList<Card>();
		for(Card card : Card.all){
			if(card != null){
				if(card.getSet() == set || set == null){
					if(card.isListable() &! card.isBasic()){
						if(card.isEquipment() || card.isBoardState()){
							avail_uncommon.add(card);
						}
						if(card.isSpell() || card.isStructure()){
							avail_common.add(card);
						}
						if(card.isUnit()){
							UnitTemplate u = ((UnitTemplate)card);
							if(u.getRank() == UnitTemplate.RANK_COMMON)avail_common.add(card);
							if(u.getRank() == UnitTemplate.RANK_UNCOMMON)avail_uncommon.add(card);
							if(u.getRank() == UnitTemplate.RANK_MYTHIC)avail_mythic.add(card);
							if(u.getRank() == UnitTemplate.RANK_REGAL)avail_regal.add(card);
						}
					}
				}
			}
		}
		int r = Util.rand.nextInt(100);
		if(r <= 1){
			return avail_regal.get(Util.rand.nextInt(avail_regal.size()));
		}
		if(r > 1 && r <= 11){
			return avail_mythic.get(Util.rand.nextInt(avail_mythic.size()));
		}
		if(r > 11 && r <= 41){
			return avail_uncommon.get(Util.rand.nextInt(avail_uncommon.size()));
		}
		return avail_common.get(Util.rand.nextInt(avail_common.size()));
		//return avail_regal.get(Util.rand.nextInt(avail_regal.size()));
	}
	
	public void initMatch(PlayerMP p1, PlayerMP p2){
		if(queue.contains(p1))queue.remove(p1);
		if(queue.contains(p2))queue.remove(p2);
		GameServer room = this.addRoom();
		int id = room.getId();
		room.addPlayer(p1);
		room.addPlayer(p2);
		p1.currentRoom = id;
		p2.currentRoom = id;
	}

	public void run(){
		log("Checking for plugins...");
		File[] plugins = new File(Bank.path+"plugins/").listFiles();
		if(plugins.length > 0){
			for(int i = 0; i < plugins.length; ++i){
				File f = new File(Bank.path+"plugins/"+plugins[i].getName());
				if(f.isFile() && f.exists()){
					this.loadPlugin(f, f.getName());
				}
			}
		}
		while(true){
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parsePacket(packet.getData(), packet.getAddress(), packet.getPort(), getRoomIDForClientIP(packet.getAddress(), packet.getPort()));
		}
	}
	
	public GameServer getRoomForClientIP(InetAddress ip, int port){
		/*for(GameServer s : rooms){
			for(PlayerMP c : s.clients){
				if(c.ip.equals(ip))return s;
			}
		}*/
		PlayerMP pl = this.getSender(ip, port);
		if(pl != null)
			return this.getRoom(pl.currentRoom);
		else return null;
	}
	
	public int getRoomIDForClientIP(InetAddress ip, int port){
		GameServer room = getRoomForClientIP(ip, port);
		if(room == null){
			return -1;
		}else{
			return room.getId();
		}
	}
	
	private void changeTurn(int t, int roomID){
		this.getRoom(roomID).changeTurn(t);
	}
	
	private boolean hasCard(PlayerMP pl, int id){
		//return this.getRoomForClientIP(pl.ip, pl.port).hasCard(pl, id);
		return this.getRoom(pl.currentRoom).hasCard(pl, id);
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
		for(PlayerMP pl : connected){
			if(pl.ip.equals(address) && pl.port == port){
				return pl;
			}
		}
		return null;
	}
	
	public void playCommander(PlayerMP player){
		//this.getRoomForClientIP(player.ip, player.port).playCommander(player);
		this.getRoom(player.currentRoom).playCommander(player);
	}
	
	private void parsePacket(byte[] data, InetAddress address, int port, int room) {
		String d = new String(data).trim();
		String[] packetData = d.split("#");
		PacketType type = Packet.lookup(packetData[0]);
		Packet packet;
		if(room <= -1 || type == Packet.PacketType.INVITE || type == Packet.PacketType.LOGIN || type == Packet.PacketType.JOINGAME || type == Packet.PacketType.CREATEACCOUNT || type == Packet.PacketType.CARDPACK){
			if(type == Packet.PacketType.LOGIN){
				log("Player attempting to connect with username "+packetData[1]);
				String[] accInfo = getAccountInfo(packetData[1]);
				if(accInfo != null){
					log("> Account under username "+packetData[1]+" found...");
					if(accInfo[1].equalsIgnoreCase(packetData[2])){
						log(">> Password is a MATCH for account "+packetData[1]);
						packet = new Packet17MainLogin(packetData[1], packetData[2], Integer.parseInt(packetData[3]), VERSION);
						PlayerMP player = new PlayerMP(-1, packetData[1], address, port);
						System.out.println("Player connected with username "+((Packet17MainLogin)packet).username);
						registerConnection(player, (Packet17MainLogin)packet);
						player.setCoins(Integer.parseInt(accInfo[ACCOUNT_GOLD]));
						String collection = Bank.readAll(new File(Bank.getPath()+"/serverData/accounts/"+packetData[1]+"_collection.userdata"));
						if(!collection.isEmpty()){
							String cur = "";
							int index = 0;
							String[] col = collection.split("#");
							int len = col.length;
							int subindex = 0;
							int max = 30;
							int chunk = 0;
							while(index < len){
								int currentMax = (len - chunk) > max ? max : (len - chunk);
								cur += col[index]+"%";
								player.addToCollectionRaw(Integer.parseInt(col[index]), 1);
								++subindex;
								++index;
								if(subindex >= currentMax){
									chunk+=currentMax;
									this.log.add("Sending collection chunk of size "+currentMax+". Chunking is currently at "+chunk+"/"+len+".");
									send(new Packet23Misc(0, cur).getData(), address, port);
									subindex = 0;
									cur = "";
								}
							}
							player.saveCollection();
						}
						send(packet.getData(), address, port);
						log("> Player "+((Packet17MainLogin)packet).username+" connected to main server.");
						send(new Packet18Notification("Success!", "You are now logged in as "+packetData[1]+".", 0).getData(), address, port);
						send(new Packet23Misc(4, (accInfo[ACCOUNT_PACKS]+"%"+player.getCoins())+"").getData(), address, port);
						
						Util.distributePatches(patchDirectory, player);
					}else{
						send(new Packet18Notification("Error!", "The password you entered does not match the one stored on our system.", 1).getData(), address, port);
						log(">> Password does not match for account "+packetData[1]);
					}
				}else{
					send(new Packet18Notification("Error!", "That account does not exist.", 1).getData(), address, port);
					log("> Account under username "+packetData[1]+" NOT found...");
				}
			}
			if(type == Packet.PacketType.INVITE){
				PlayerMP pl = this.getPlayer(packetData[2]);
				queuePlayer(pl);
			}
			if(type == Packet.PacketType.MISC){
				PlayerMP pl = this.getSender(address, port);
				new Packet23Misc(Integer.parseInt(packetData[1]), packetData[2]).resolve(pl);
			}
			if(type == Packet.PacketType.CARDPACK){
				CardSet set = null;
				int c1 = this.generateCardForPack(set).getId();
				int c2 = this.generateCardForPack(set).getId();
				int c3 = this.generateCardForPack(set).getId();
				int c4 = this.generateCardForPack(set).getId();
				int c5 = this.generateCardForPack(set).getId();
				send(new Packet22CardPack(c1, c2, c3, c4, c5).getData(), address, port);
				PlayerMP pl = this.getSender(address, port);
				pl.addToCollectionRaw(c1, 1);
				pl.addToCollectionRaw(c2, 1);
				pl.addToCollectionRaw(c3, 1);
				pl.addToCollectionRaw(c4, 1);
				pl.addToCollectionRaw(c5, 1);
				pl.saveCollection();
				pl.flushCollection();
				this.modifyAccountValue(pl.name, ACCOUNT_PACKS, ""+(Integer.parseInt(this.getAccountInfo(pl.name)[ACCOUNT_PACKS]) - 1));
			}
			if(type == Packet.PacketType.JOINGAME){
				room = Integer.parseInt(packetData[5]);
				getRoom(room).parsePacket(data, d, packetData, address, port);
				log("User "+packetData[1]+" attempting to join GameRoom ["+room+"]. Redirecting packet.");
			}
			if(type == Packet.PacketType.CREATEACCOUNT){
				String name = packetData[1];
				String pass = packetData[2];
				String email = packetData[3];
				int newid = addAccount(name, pass, email);
				if(newid == -1){
					send(new Packet18Notification("Error!", "That username is already taken, sorry!", 1).getData(), address, port);
				}else{
					send(new Packet18Notification("Success!", "Account '"+name+"' has been created.", 2).getData(), address, port);
				}
			}
		}else{
			if(room > -1)
			getRoom(room).parsePacket(data, d, packetData, address, port);
		}
	}
	
	public void modifyAccountValue(String s, int vIndex, String newval){
		File userdata = new File(Bank.accFile+"/"+s+".userdata");
		String s1 = Bank.readAll(userdata).split(";")[0];
		String[] dat = s1.split("%");
		if(dat[0].toLowerCase().equals(s.toLowerCase())){
			dat[vIndex] = newval;
		}
		String finalData = "";
		for(String d1 : dat)finalData+=(d1+"%");
		Bank.setContentsRawdir(Bank.accFile+"/"+s+".userdata", finalData+";");
	}
	
	public void queuePlayer(PlayerMP pl){
		this.log(pl.name+" is now queued for a match");
		ArrayList<PlayerMP> others = (ArrayList<PlayerMP>) queue.clone();
		queue.add(pl);
		if(!queue.isEmpty()){
			for(PlayerMP player : queue){
				if(player!=null && player != pl){
					if(!player.name.equalsIgnoreCase(pl.name)){
						this.log("> "+pl.name+" has been matched with "+player.name+"!");
						initMatch(pl, player);
						return;
					}
				}
			}
		}
	}
	
	public String[] getAccountInfo(String s){
		File userdata = new File(Bank.accFile+"/"+s+".userdata");
		String s1 = Bank.readAll(userdata).split(";")[0];
		String[] arr = new String[8];
		String[] dat = s1.split("%");
		if(dat[0].toLowerCase().equals(s.toLowerCase())){
			arr[0] = s;
			arr[1] = dat[1];
			arr[2] = dat[2];
			arr[3] = dat[3];
			arr[4] = dat[4];
			arr[5] = dat[5];
		}
		
		if(arr[0]!=null){
			if(arr[0].isEmpty())arr = null;
		}else{
			arr = null;
		}
		return arr;
	}
	
	public int addAccount(String user, String pass, String email){
		int id = Bank.accFile.listFiles().length;
		File userdata = new File(Bank.accFile+"/"+user+".userdata");
		File collectionfile = new File(Bank.accFile+"/"+user+"_collection.userdata");
		try {
			userdata.createNewFile();
			collectionfile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String ln = user+"%"+pass+"%"+id+"%"+email+"%3%0;";
		Bank.setContentsRawdir(userdata.getPath(), ln);
		log("Account '"+user+"' has been created.");
		return id;
	}
	
	public GameServer getRoom(int id){
		for(GameServer s : rooms){
			if(s.getId() == id)return s;
		}
		return null;
	}
	
	private GameServer findRoom(int id){
		for(GameServer s : rooms){
			if(s.getId() == id)return s;
		}
		return null;
	}
	
	private GameServer addRoom(){
		int newID = generateNewRoomID();
		GameServer s = new GameServer(newID);
		s.roomID = newID;
		rooms.add(s);
		return s;
	}
	
	private int generateNewRoomID(){
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for(GameServer s : rooms){
			if(s != null){
				ids.add((int) s.getId());
			}
		}
		for(int i = 1; i < MAX_GAMEROOMS; ++i){
			if(!ids.contains(i))return i;
		}
		return 0;
	}
	
	public Unit getCommander(PlayerMP p){
		//return this.getRoomForClientIP(p.ip, p.port).getCommander(p);
		return this.getRoom(p.currentRoom).getCommander(p);
	}
	
	public void drawCard(PlayerMP pl, int cardID, int cardCount){
		log("Drawing card in room "+pl.currentRoom);
		//this.getRoomForClientIP(pl.ip, pl.port).drawCard(pl, cardID, cardCount);
		this.getRoom(pl.currentRoom).drawCard(pl, cardID, cardCount);
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
		for(PlayerMP p : connected){
			if(name.equalsIgnoreCase(p.name)){
				return connected.indexOf(p);
			}
		}
		return 0;
	}
	
	public Unit getUnitFromGUID(int guid, int roomID){
		return this.getRoom(roomID).getUnitFromGUID(guid);
	}

	private void breakConnection(PlayerMP player, Packet01Disconnect packet) {	
		connected.remove(getPlayerIndex(packet.username));
		//GameServer s = this.getRoomForClientIP(player.ip, player.port);
		GameServer s = this.getRoom(player.currentRoom);
		if(s!=null){
			packet.write(s);
		}else{
			packet.write(this, -1);
		}
	}
	
	/*private void registerConnection(PlayerMP player, Packet17MainLogin packet) {
		boolean c = false;
		for(PlayerMP p : connected){
			if(player.name.equalsIgnoreCase(p.name)){
				if(p.ip == null){
					p.ip = player.ip;
				}
				if(p.port == -1){
					p.port = player.port;
				}
				c = true;
			}
		}
		if(!c){
			PlayerMP newplayer = player;
			newplayer.playerID = -1;
			connected.add(newplayer);
		}
	}*/
	
	
	 private void registerConnection(PlayerMP player, Packet17MainLogin packet) {
		boolean c = false;
		for(PlayerMP p : connected){
			if(player.name.equalsIgnoreCase(p.name)){
				Packet01Disconnect pkt = new Packet01Disconnect(player.name);
				this.send(pkt.getData(), p.ip, p.port);
				connected.remove(p);
				for(GameServer s : rooms){
					s.removePlayer(p);
				}
				break;
			}
		}
		if(!c){
			PlayerMP newplayer = player;
			newplayer.playerID = -1;
			connected.add(newplayer);
		}
	}
	 
	public PlayerMP getPlayer(String user){
		for(int i = 0; i < connected.size(); ++i){
			if(connected.get(i).name.equalsIgnoreCase(user))
			return connected.get(i);
		}
		return null;
	}
	
	public int getPlayerIndex(String user){
		int index = 0;
		for(int i = 0; i < connected.size(); ++i){
			if(connected.get(i).name.equals(user))
				break;
		}
		index++;
		return index;
	}

	public void send(byte[] data, InetAddress ip, int port){
		DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendToAll(byte[] data) {
		for(PlayerMP p : connected){
			send(data, p.ip, p.port);
		}
	}

	public ArrayList<String> getLog() {
		return log;
	}

	public void setLog(ArrayList<String> log) {
		this.log = log;
	}

	public int generateGUID(int roomID) {
		return this.getRoom(roomID).generateGUID();
	}

	public Unit addUnit(UnitTemplate ut, int x, int y, int ownerID, int roomID) {
		return this.getRoom(roomID).addUnit(ut, x, y, ownerID);
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
		log("Loading plugin \""+name+"\"...");
		String all = Bank.readAll(file);
		loadEventsFrom(all.replace(" ", "").replace("	", ""));
		log("Plugin loaded successfully.");
	}
	
	private void log(String s){
		log.add(s);
		System.out.println(s);
	}

	public Unit getUnitOnTile(int i, int j, int roomID) {
		return this.getRoom(roomID).getUnitOnTile(i, j);
	}

	public ArrayList<Unit> getUnits(int roomID) {
		return (this.getRoom(roomID) == null ? new ArrayList<Unit>() : this.getRoom(roomID).units);
	}

	public Grid getGrid(int roomID) {
		return this.getRoom(roomID).grid;
	}
	
	public void setPicks(ArrayList<Card> list, int roomID2) {
		this.getRoom(roomID2).setPicks(list);
	}

	public ArrayList<PlayerMP> getClients(int roomID) {
		return this.getRoom(roomID).clients;
	}

	public void purgeRoom(GameServer room) {
		for(PlayerMP pl : room.clients){
			pl.currentRoom = -1;
		}
		this.rooms.remove(room);
	}
}
