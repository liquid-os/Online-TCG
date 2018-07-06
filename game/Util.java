package org.author.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

public class Util {
	
	public static final int maxTurnTime = 120000;
	static int latestGUID = 0;
	static int deckSize = 40, maxUnits = 14;
	static int boardWidth = 14, boardHeight = 7, drawn = 0;
	static int clientID = -1;
	static Color[] playerColors = new Color[]{Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.CYAN, Color.MAGENTA};
	static ArrayList<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();
	static int turn = -1, gold = 1, maxGold = 1, maxTurns = 10, goldPerTurn = 0, goldRed = 1, goldGreen = 1, goldBlue = 1;
	static int COMMANDER_PLACE_DIST = 2, CARD_TYPE_LIMIT = 3, SPELL_TYPE_LIMIT = 2, COMM_ZONE = 5;
	static long turnStart = -1;
	static int victor = -1;
	static CardBoardstate boardState = null;
	static int boardStateTimer = 0;
	static ArrayList<CardShell> picks = new ArrayList<CardShell>();
	static boolean displayPicks = false;
	static String RESOURCE_NAME = "mana";
	static ArrayList<Card> castSpells = new ArrayList<Card>();
	static int version = 7, revision = 1;
	static String SITE_LINK = "http://www.talesofterroth.com/";
	static String MANUAL_LINK = "https://docs.google.com/document/d/1nvarbTrR7U7fzdeQMu4AYS9GZUtOOeQS2kkT5nrLeSU/";
	static int roomID = -1;
	static ArrayList<PersistentValue> vars = new ArrayList<PersistentValue>();
	static int manaMode = 0;

	static int DUST_REGAL = 250, DUST_MYTHIC = 75, DUST_UNCOMMON = 30, DUST_COMMON = 10;
	
	static byte[] collection = new byte[Card.all.length];
	
	static String deck = null;
	static String deckName = "No Deck Selected";
	static Image heroImg = null;
	static String username = null;
	static int hero = 0;
	
	static boolean isGameOver(int roomID){
		if(Bank.isServer()){
			if(Bank.server.getRoom(roomID) != null){
				return Bank.server.getRoom(roomID).isGameOver();
			}else{
				return true;
			}
		}
		return false;
	}
	
	public static byte[] flushCollection(byte[] collection){
		byte[] newcollection = Arrays.copyOf(collection, collection.length);
		for(int i = 0; i < collection.length; ++i){
			if(collection[i] > 1){
				Card card = Card.all[i];
				if(card != null){
					int dif = collection[i] - 1;
					int dustValue = Util.getDustValue(card) * dif;
					System.out.println("AUTO-DUSTING: "+card.getName()+" x "+dif+" ("+dustValue+")");
					coins+=dustValue;
					newcollection[i] = 1;
				}
			}
		}
		return newcollection;
	}
	
	public byte[] readCollection(String c){
		String[] s = c.split("%");
		byte[] ret = new byte[Card.all.length];
		for(String str : s){
			ret[Integer.parseInt(str.trim())]++;
		}
		return ret;
	}
	
	public static void resetClient() {
		Util.connectedPlayers.clear();
		Util.queue.clear();
		Util.roomID = -1;
		Util.picks.clear();
		Util.clientID = -1;
		Util.boardState = null;
		Util.gold = 1;
		Util.goldPerTurn = 0;
		Util.maxGold = 1;
		Util.victor = -1;
		Util.turn = -1;
		Util.turnStart = -1;
		Util.boardStateTimer = 0;
		PanelBoard.hand.clear();
		PanelBoard.handqueue.clear();
	}
	
	static ArrayList<Unit> queue = new ArrayList<Unit>();
	
	static ClipShell music = null;
	
	static int boardOffsetX = 20, boardOffsetY = 20;
	
	public static Color transparent_faded = new Color(0F,0F,0F,0.25F);
	public static Color transparent = new Color(0F,0F,0F,0.45F);
	public static Color transparent_dark = new Color(0F,0F,0F,0.85F);
	public static Color transparent_white = new Color(1F,1F,1F,0.45F);
	public static Color transparent_buttonwhite = new Color(1F,1F,1F,0.15F);
	public static Color transparent_red = new Color(255,0,0,90);
	public static Color SEMIDARK_GRAY = new Color(Color.GRAY.getRed() + 25, Color.GRAY.getGreen() + 25, Color.gray.getBlue() + 25, Color.gray.getAlpha());
	public static Color waterBlue = new Color(0,147,255,101);
	public static Color blood = new Color(255,0,0,100);
	public static Color orange = new Color(200,180,0,255);
	public static Color yellow = new Color(190,240,0,255);
	public static Color purple = new Color(160,0,220,255);
	public static Color lavender = new Color(230,70,255,255);
	public static Color critGreen = new Color(0,255,100,255);
	public static Color bgoverlay = new Color(100,175,230,185);
	public static Font cooldownFont = new Font(Font.MONOSPACED, Font.PLAIN, 34);
	public static Font dialogFont = new Font(Font.MONOSPACED, Font.PLAIN, 28);
	public static Font dialogBold = new Font(Font.MONOSPACED, Font.BOLD, 28);
	public static Font cooldownBold = new Font(Font.MONOSPACED, Font.BOLD, 34);
	public static Font descFont = new Font(Font.MONOSPACED, Font.PLAIN, 14);
	public static Font upgradeFont = new Font(Font.MONOSPACED, Font.PLAIN, 18);
	public static Font smallDescFont = new Font(Font.MONOSPACED, Font.PLAIN, 12);
	public static Font descTitleFont = new Font(Font.MONOSPACED, Font.BOLD, 16);
	public static Font largeNameFont = new Font(Font.MONOSPACED, Font.BOLD, 54);
	public static Font hugeTitleFont = new Font(Font.MONOSPACED, Font.BOLD, 75);
	public static Font spellNameFont = new Font(Font.MONOSPACED, Font.BOLD, 34);
	public static Font spellDesc = new Font(Font.MONOSPACED, Font.PLAIN, 22);
	public static Font goldFont = new Font(Font.MONOSPACED, Font.BOLD, 22);
	public static Color transparent_green = new Color(0,255,0,80);
	public static Random rand = new Random();
	
	public static ArrayList<GUI> persistentGuis = new ArrayList<GUI>();
	public static Color timebar_color = new Color(0,255,0,120);
	public static int breather = 0;
	public static int packs = 0;
	public static int coins = 0;
	public static int COST_PACK = 150;
	
	public static Unit getNearestUnit(Unit focus, ArrayList<Unit> list){
		Unit u = null;
		list.remove(focus);
		for(Unit unit : list){
			if(u != null){
				if(u.getHealth() > 0 &! u.flagRemove && Util.distance(unit.getTilePosition(), focus.getTilePosition()) < (Util.distance(u.getTilePosition(), focus.getTilePosition()))){
					u = unit;
				}
			}else{
				u = unit;
			}
		}
		return u;
	}
	
	public static void readPatches(File server){
		File[] patches = server.listFiles();
		if(patches != null){
			if(patches.length > 0)
			for(File f : patches){
				String patch = Bank.readAll(f);
				readPatch(patch);
			}
		}
	}
	
	public static void clearPatches(File server){
		File[] patches = server.listFiles();
		if(patches != null){
			if(patches.length > 0)
			for(File f : patches){
				f.delete();
			}
		}
	}
	
	public static void distributePatches(File server, PlayerMP client){
		File[] patches = server.listFiles();
		if(patches != null){
			if(patches.length > 0)
			for(File f : patches){
				String patch = Bank.readAll(f);
				Packet20Patch pkt = new Packet20Patch(patch);
				Bank.server.send(pkt.getData(), client.ip, client.port);
			}
		}
	}
	
	public static void readPatch(String patch){
		String[] clauses = patch.split(";");
		for(String s : clauses){
			String[] subclauses = s.split(",");
			int type = (subclauses[0].equalsIgnoreCase("C") ? 0 : subclauses[0].equalsIgnoreCase("A") ? 1 : -1);
			for(int i = 1; i < subclauses.length; ++i){
				String sub = subclauses[i];
				int id = Integer.parseInt(sub.split("%")[0]);
				String target = sub.split("%")[1].split("=")[0];
				String assignment = sub.split("%")[1].split("=")[1];
				if(type == 0){
					Card card = Card.all[id];
					if(card!=null){
						if(target.equalsIgnoreCase("atk")){
							((UnitTemplate)card).setAttack(Integer.parseInt(assignment));
						}
						if(target.equalsIgnoreCase("hp")){
							((UnitTemplate)card).setHealth(Integer.parseInt(assignment));
						}
						if(target.equalsIgnoreCase("spd")){
							((UnitTemplate)card).setSpeed(Integer.parseInt(assignment));
						}
						if(target.equalsIgnoreCase("rnk")){
							((UnitTemplate)card).setRank((byte) Integer.parseInt(assignment));
						}
						if(target.equalsIgnoreCase("uls")){
							card.setListable(Integer.parseInt(assignment) == 0);
						}
						if(target.equalsIgnoreCase("del")){
							Card.all[card.getId()] = null;
						}
						if(target.equalsIgnoreCase("dur")){
							((CardEquipment)card).setDurability((byte) Integer.parseInt(assignment));
						}
						if(target.equalsIgnoreCase("rab")){
							((UnitTemplate)card).getAbilities().remove(CardAbility.all[Integer.parseInt(assignment)]);
						}
						if(target.equalsIgnoreCase("aab")){
							((UnitTemplate)card).getAbilities().add(CardAbility.all[Integer.parseInt(assignment)]);
						}
						if(target.equalsIgnoreCase("cst")){
							card.setCost(Integer.parseInt(assignment));
						}
						if(target.equalsIgnoreCase("art")){
							card.setArt(assignment);
						}
					}
				}
				if(type == 1){
					CardAbility a = CardAbility.all[id];
					if(a!=null){
						if(target.equalsIgnoreCase("rng")){
							a.setRange(Integer.parseInt(assignment));
						}
						if(target.equalsIgnoreCase("cst")){
							a.setCost(Integer.parseInt(assignment));
						}
						if(target.equalsIgnoreCase("ttp")){
							a.setTargetType(Integer.parseInt(assignment));
						}
						if(target.equalsIgnoreCase("bnd")){
							a.setUnbound(Integer.parseInt(assignment) == 1);
						}
						if(target.equalsIgnoreCase("ctp")){
							a.setCostType((byte) Integer.parseInt(assignment));
						}
					}
				}
			}
		}
	}
	
	public static void resolveUnits(int roomID){
		if(!Util.isGameOver(roomID)){
			ArrayList<Unit> units = null;
			if(Bank.isServer()){
				units = Bank.server.getRoom(roomID).units;
			}else{
				units = Display.currentScreen.objects;
			}
			for(int i = 0; i < units.size(); ++i){
				if(i < units.size() && units.get(i)!=null){
					if(!units.get(i).isRemoving()){
						units.get(i).updateUnit();
					}else{
						units.remove(i);
					}
				}
			}
			
			ArrayList<PersistentValue> varlist = PersistentValue.getVars(roomID);
			ArrayList<PersistentValue> newvarlist = (ArrayList<PersistentValue>) varlist.clone();
			for(int i = 0; i < varlist.size(); ++i){
				PersistentValue v = varlist.get(i);
				if(v.flagRemove)newvarlist.remove(v);
			}
			PersistentValue.setVars(newvarlist, roomID);
		}
	}
	
	public static int findMatch(Card c, String keyword){
		int match = 0;
		if(c.getText()!=null)
			if(c.getText().toLowerCase().contains(keyword))++match;
		if(c.isUnit()){
			if(c.getUnit().getFamily().getName().toLowerCase().contains(keyword))++match;
			for(CardAbility a : c.getUnit().getAbilities()){
				if(a.getName().toLowerCase().contains(keyword))++match;
				if(a.getDesc().toLowerCase().contains(keyword))++match;
			}
		}
		if(c.getName().toLowerCase().contains(keyword))++match;
		return match;
	}
	
	public static Unit getFurthestUnit(Unit focus, ArrayList<Unit> list){
		Unit u = null;
		list.remove(focus);
		for(Unit unit : list){
			if(u != null){
				if(u.getHealth() > 0 &! u.flagRemove && Util.distance(unit.getTilePosition(), focus.getTilePosition()) > (Util.distance(u.getTilePosition(), focus.getTilePosition()))){
					u = unit;
				}
			}else{
				u = unit;
			}
		}
		return u;
	}
	
	public static void doTurnChangeEvent(int type, Grid g, ArrayList<Unit> units, int turn){
		if(type == 0){
			if(turn == 1){
				int x = rand.nextInt(Util.boardWidth);
				int y = rand.nextInt(Util.boardHeight);
				
			}
		}else{
			//if(type == 1){
		}
	}
	
	public static int countUnitsForPlayer(ArrayList<Unit> list, int ownerID){
		int count = 0;
		for(Unit u : list){
			if(u.ownerID == ownerID && u.getTemplate().shouldCount() && u.getHealth() > 0 &! u.isRemoving())++count;
		}
		return count;
	}
	
	public static Color getRarityColor(int r){
		return
				r == 0 ? Color.GRAY :
				r == 1 ? Color.WHITE :
				r == 2 ? Color.YELLOW :
				r == 3 ? Color.GREEN :
				r == 4 ? Color.MAGENTA :
				r == 5 ? Color.BLUE : Color.WHITE;
	}
	
	public static void initResourcepack(){
		File file = new File(Bank.path + "resourcepacks/");
		File[] packs = file.listFiles();
		ArrayList<File> folders = new ArrayList<File>();
		for(File f : packs){
			if(f.isDirectory())folders.add(f);
		}
		for(File f : folders){
			loadResourcePack(f);
		}
	}
	
	public static void loadResourcePack(File folder){
		File cards = new File(folder.getPath()+"/cards.RES");
		File abilities = new File(folder.getPath()+"/abilities.RES");
		try {
			abilities.createNewFile();
			cards.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Util.loadResourcePackCards(folder, cards);
		Util.loadResourcePackAbilities(folder, abilities);
		Util.persistentGuis.add(new GUINotify(Bank.flamehelm, "Resource Pack Loaded", folder.getName()+" was loaded successfully.", 0, 0));
	}
	
	public static void loadResourcePackCards(File folder, File cards){
		String cardcontent = Bank.readAll(cards);
		String[] dats = cardcontent.split(";");
		for(int i = 0; i < dats.length; ++i){
			if(dats[i]!=null){
				int id = Integer.parseInt(dats[i].split("=")[0]);
				String data = dats[i].split("=")[1];
				if(Card.all[id]!=null){
					String[] vals = data.split(",");
					for(int j = 0; j < vals.length; ++j){
						if(vals[j]!=null){
							String tag = vals[j].split(":")[0];
							String val = vals[j].split(":")[1];
							if(tag.equalsIgnoreCase("name")){
								Card.all[id].setName(val);
							}
							if(tag.equalsIgnoreCase("desc")){
								Card.all[id].setText(val);
							}
							if(tag.equalsIgnoreCase("img")){
								Card.all[id].setArt((folder.getPath()+"/"+val));
							}
						}
					}
				}
			}
		}
	}
	
	public static void loadResourcePackAbilities(File folder, File cards){
		String cardcontent = Bank.readAll(cards);
		String[] dats = cardcontent.split(";");
		for(int i = 0; i < dats.length; ++i){
			if(dats[i]!=null){
				int id = Integer.parseInt(dats[i].split("=")[0]);
				String data = dats[i].split("=")[1];
				if(CardAbility.all[id]!=null){
					String[] vals = data.split(",");
					for(int j = 0; j < vals.length; ++j){
						if(vals[j]!=null){
							String tag = vals[j].split(":")[0];
							String val = vals[j].split(":")[1];
							if(tag.equalsIgnoreCase("name")){
								CardAbility.all[id].setName(val);
							}
							if(tag.equalsIgnoreCase("desc")){
								CardAbility.all[id].setDesc(val);
							}
							if(tag.equalsIgnoreCase("img")){
								try {
									CardAbility.all[id].setImg(ImageIO.read(new File(folder.getPath()+"/"+val)));
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static Point getGRPoint(Point mousePoint){
		int gridX = (mousePoint.x - Util.boardOffsetX) / Grid.tileSize;
		int gridY = (mousePoint.y - Util.boardOffsetY) / Grid.tileSize;
		return new Point(gridX, gridY);
	}
	
	public static void drawParticleLine(Point p1, Point p2, int max, boolean peak, String set){
		for(int i = 0; i < max; ++i){
			int difX = p1.x-p2.x;
			int difY = p1.y-p2.y;
			double divY = (double)difY / (double)max;
			double divX = (double)difX / (double)max;
			int ym = 0;
			if(peak)ym = i<(max/2)?(i * 2):(max/2)*2-((i-(max/2))*2);
			AnimatedParticle part = new AnimatedParticle((int) (p1.x-(divX*i)), (int) (p1.y-(i*divY))-ym, 16+Util.rand.nextInt(32), 150+(i*10)).setMotions(0, rand.nextInt(6)-rand.nextInt(6));
			part.addFrameSet(set);
			Display.currentScreen.particles.add(part);
		}
	}
	public static void drawParticleLine(Point p1, Point p2, int max, boolean peak, String set, int size, int dur, boolean shrinks){
		for(int i = 0; i < max; ++i){
			int difX = p1.x-p2.x;
			int difY = p1.y-p2.y;
			double divY = (double)difY / (double)max;
			double divX = (double)difX / (double)max;
			int ym = 0;
			if(peak)ym = i<(max/2)?(i * 2):(max/2)*2-((i-(max/2))*2);
			AnimatedParticle part = new AnimatedParticle((int) (p1.x-(divX*i)), (int) (p1.y-(i*divY))-ym, size, dur+(i*10)).setMotions(0, 0);
			part.addFrameSet(set);
			part.setShrinks(shrinks);
			Display.currentScreen.particles.add(part);
		}
	}
	
	public static void drawPolyZone(Graphics g, int x, int y, Color c, int size){
		Polygon poly = new Polygon();
		poly.npoints = 4;
		poly.xpoints = new int[]{x - size, x, x + size, x};
		poly.ypoints = new int[]{y, y - size, y, y + size};
		g.setColor(c);
		g.drawPolygon(poly);
		g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 80));
		g.fillPolygon(poly);
	}
	
	public static void drawZone(Graphics g, int x, int y, Color c, int w, int h){
		g.setColor(c);
		g.drawRect(x, y, w, h);
		g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 80));
		g.fillRect(x, y, w, h);
	}


	public static void createCircle(int x, int y, int w, int h, int res){
		int count = res;
		int quart1 = res/4, quart2 = (res/2), quart3 = res/4*3;
		for(int i = 0; i < res; ++i){
			//int mx = ;
		}
	}
	
	public static ClipShell getMusic(){
		String pth = "ig.wav";
		File[] files = new File(Bank.path+"tracks").listFiles();
		int r = rand.nextInt(files.length+1);
		if(r!=0)pth=files[r-1].getPath();
		return new ClipShell(pth);
	}
	
	public static int distance(Point p1, Point p2){
		int d = p2.x-p1.x;
		int d1 = p2.y-p1.y;
		if(d<0)d=Math.abs(d);
		if(d1<0)d1=Math.abs(d1);
		return d+d1;
	}
	
	public static int xDist(Point p1, Point p2){
		int d = p2.x-p1.x;
		if(d<0)d=Math.abs(d);
		return d;
	}
	
	public static int dif(int n1, int n2){
		int d = n2-n1;
		if(d<0)d=Math.abs(d);
		return d;
	}
	
	public static int yDist(Point p1, Point p2){
		int d = p2.y-p1.y;
		if(d<0)d=Math.abs(d);
		return d;
	}

	public static Color setAlpha(Color c, int a) {
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), a);
	}

	public static int pct(int ret, int i) {
		float f = (float)ret;
		f /= (float)100;
		f *= (float)i;
		return (int)f;
	}
	
	public static int pct(int l, int i, int max) {
		float f = (float)l;
		f /= (float)max;
		f *= (float)i;
		return (int)f;
	}

	public static int generateRange(int i) {
		return (rand.nextInt(i) - rand.nextInt(i));
	}

	public static void doPlayAnim(Card card) {
		int cw = Properties.width/5;
		int ch = (int) (cw * 1.5);
		AnimatedParticle part = new AnimatedParticle(Properties.width/2 + cw - ch/2, Properties.height/2 - ch/2 - cw / 10, ch, 2500).addFrameSet("NOVA");
		Display.currentScreen.particles.add(part);
		Animation anim = new Animation(Properties.width/2 + cw, Properties.height/2, 1500, Animation.TAG_CARD);
		anim.width = cw;
		anim.height = ch;
		anim.setData(card.getId());
		Display.currentScreen.particles.add(anim);
	}

	public static ArrayList<Card> createConjuringList(ArrayList<Card> availold, int max) {
		ArrayList<Card> list = new ArrayList<Card>();
		ArrayList<Card> avail = (ArrayList<Card>) availold.clone();
		for(Card c : availold){
			if(!c.isListable())avail.remove(c);
		}
		for(int i = 0; i < max; ++i){
			if(avail.size() > 0){
				Card candidate = avail.get(Util.rand.nextInt(avail.size()));
				if(!list.contains(candidate)){
					list.add(candidate);
					avail.remove(candidate);
				}
			}else{
				list.add(Card.filler);
			}
		}
		return list;
	}

	public static String getServerIP() {
		return Bank.readAll(new File(Bank.path+"realmlist"+Properties.fileExt)).isEmpty() ? "logon.talesofterroth.com" : Bank.readAll(new File(Bank.path+"realmlist"+Properties.fileExt));
	}

	public static ArrayList<Card> getCastSpells(int id) {
		if(Bank.isServer()){
			return Bank.server.getRoom(id).castSpells;
		}else{
			return Util.castSpells;
		}
	}

	public static int getDustValue(Card card) {
		if(card instanceof UnitTemplate){
			UnitTemplate t = (UnitTemplate)card;
			if(t.getRank() == Card.RANK_COMMANDER ||  t.getRank() == Card.RANK_REGAL){
				return DUST_REGAL;
			}
			if(t.getRank() == Card.RANK_MYTHIC){
				return DUST_MYTHIC;
			}
			if(t.getRank() == Card.RANK_UNCOMMON){
				return DUST_UNCOMMON;
			}
			if(t.getRank() == Card.RANK_COMMON){
				return DUST_COMMON;
			}
		}else{
			if(card.isSpell()){
				return DUST_COMMON;
			}
			if(card.isBoardState()){
				return DUST_UNCOMMON;
			}
			if(card.isStructure()){
				return DUST_COMMON;
			}
			if(card.isEquipment()){
				return DUST_UNCOMMON;
			}
			if(card.isBasic()){
				return 0;
			}
		}
		return 0;
	}
}
