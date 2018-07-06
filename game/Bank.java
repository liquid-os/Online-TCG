package org.author.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Bank {
	
	//static MainServer mainserver = null;
	static MainServer server = null;
	static GameClient client = null;
	
	static Image flamehelm = Filestream.loadGif("item_flamehelm.gif");
	static Image icon = Filestream.getImageFromPath("icon.png");
	static Image protection = Filestream.loadGif("protection.gif");
	static BufferedImage blank = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	static BufferedImage cursor = (BufferedImage) Filestream.getImageFromPath("cursor.png");
	static BufferedImage grass = (BufferedImage) Filestream.getImageFromPath("grass.png");
	static Image water = Filestream.loadGif("water.gif");
	static BufferedImage brick = (BufferedImage) Filestream.getImageFromPath("bricks.png");
	static BufferedImage bouldertile = (BufferedImage) Filestream.getImageFromPath("boulder.png");
	static BufferedImage sand = (BufferedImage) Filestream.getImageFromPath("sand.png");
	static BufferedImage scorched = (BufferedImage) Filestream.getImageFromPath("scorched.png");
	static BufferedImage dirt = (BufferedImage) Filestream.getImageFromPath("dirt.png");
	static BufferedImage dirtTopLeft = (BufferedImage) Filestream.getImageFromPath("dirtTopLeft.png");
	static BufferedImage dirtTopRight = (BufferedImage) Filestream.getImageFromPath("dirtTopRight.png");
	static BufferedImage dirtTop = (BufferedImage) Filestream.getImageFromPath("dirtTop.png");
	static BufferedImage dirtLeft = (BufferedImage) Filestream.getImageFromPath("dirtLeft.png");
	static BufferedImage dirtRight = (BufferedImage) Filestream.getImageFromPath("dirtRight.png");
	static BufferedImage dirtBotLeft = (BufferedImage) Filestream.getImageFromPath("dirtBotLeft.png");
	static BufferedImage dirtBotRight = (BufferedImage) Filestream.getImageFromPath("dirtBotRight.png");
	static BufferedImage dirtBot = (BufferedImage) Filestream.getImageFromPath("dirtBot.png");
	static BufferedImage lawn = (BufferedImage) Filestream.getImageFromPath("lawn.png");
	static BufferedImage stone = (BufferedImage) Filestream.getImageFromPath("stone.png");
	static BufferedImage rocks = (BufferedImage) Filestream.getImageFromPath("rocks.png");
	static BufferedImage grassyrock = (BufferedImage) Filestream.getImageFromPath("grassyrock.png");
	static BufferedImage spell_woodwhirl = (BufferedImage) Filestream.getImageFromPath("spell_woodwhirl.png");
	static BufferedImage ball = (BufferedImage) Filestream.getImageFromPath("ball.png");
	static BufferedImage button = (BufferedImage) Filestream.getImageFromPath("button.png");
	static BufferedImage buttonHover = (BufferedImage) Filestream.getImageFromPath("buttonHover.png");
	static BufferedImage timebar = (BufferedImage) Filestream.getImageFromPath("timebar.png");
	static BufferedImage gradient = (BufferedImage) Filestream.getImageFromPath("gradient.png");
	static BufferedImage iconServer = (BufferedImage) Filestream.getImageFromPath("iconServer.png");
	static BufferedImage volleyarrow = (BufferedImage) Filestream.getImageFromPath("volleyarrow.png");
	static BufferedImage planks = (BufferedImage) Filestream.getImageFromPath("planks.png");
	static BufferedImage logo = (BufferedImage) Filestream.getImageFromPath("newlogosmall.png");
	static BufferedImage torch = (BufferedImage) Filestream.getImageFromPath("torch.png");
	static BufferedImage z = (BufferedImage) Filestream.getImageFromPath("z_0.png");
	static BufferedImage iconMail = (BufferedImage) Filestream.getImageFromPath("iconMail.png");
	static BufferedImage iconAlert = (BufferedImage) Filestream.getImageFromPath("iconAlert.png");
	static BufferedImage iconInfo = (BufferedImage) Filestream.getImageFromPath("iconInfo.png");
	static BufferedImage coinstack = (BufferedImage) Filestream.getImageFromPath("coinstack.png");
	static Image coin = Filestream.loadGif("coin.gif");
	static Image stun = Filestream.loadGif("stun.gif");
	static Image voidtile = Filestream.loadGif("voidtile.gif");
	static BufferedImage goldbag = (BufferedImage) Filestream.getImageFromPath("goldbag.png");
	static BufferedImage goldbagHover = (BufferedImage) Filestream.getImageFromPath("goldbagHover.png");
	static BufferedImage packicon = (BufferedImage) Filestream.getImageFromPath("packicon.png");
	
	static BufferedImage starRed = (BufferedImage) Filestream.getImageFromPath("starRed.png");
	static BufferedImage starGreen = (BufferedImage) Filestream.getImageFromPath("starGreen.png");
	static BufferedImage starBlue = (BufferedImage) Filestream.getImageFromPath("starBlue.png");

	static BufferedImage packClosed = (BufferedImage) Filestream.getImageFromPath("packClosed.png");
	static BufferedImage packOpen = (BufferedImage) Filestream.getImageFromPath("packOpen.png");
	static BufferedImage packOpen0 = (BufferedImage) Filestream.getImageFromPath("packOpen0.png");
	static BufferedImage packOpen1 = (BufferedImage) Filestream.getImageFromPath("packOpen1.png");
	static BufferedImage packOpener = (BufferedImage) Filestream.getImageFromPath("cardopener.png");

	static BufferedImage texture_paper = (BufferedImage) Filestream.getImageFromPath("texture_paper.png");
	static BufferedImage texture_diamond = (BufferedImage) Filestream.getImageFromPath("texture_diamond.png");
	static BufferedImage texture_china = (BufferedImage) Filestream.getImageFromPath("texture_china.png");
	static BufferedImage texture_stardust = (BufferedImage) Filestream.getImageFromPath("texture_stardust.png");
	static BufferedImage texture_tweed = (BufferedImage) Filestream.getImageFromPath("texture_tweed.png");
	static BufferedImage texture_bark = (BufferedImage) Filestream.getImageFromPath("texture_bark.png");
	static BufferedImage texture_brickwall = (BufferedImage) Filestream.getImageFromPath("texture_brickwall.png");
	static BufferedImage paper = (BufferedImage) Filestream.getImageFromPath("paper.png");
	static BufferedImage searchbar = (BufferedImage) Filestream.getImageFromPath("searchbar.png");
	static BufferedImage borderScreen = (BufferedImage) Filestream.getImageFromPath("border.png");
	static BufferedImage crest = (BufferedImage) Filestream.getImageFromPath("crest.png");
	static BufferedImage book = (BufferedImage) Filestream.getImageFromPath("book.png");
	static BufferedImage bookHover = (BufferedImage) Filestream.getImageFromPath("bookHover.png");
	static BufferedImage site = (BufferedImage) Filestream.getImageFromPath("site.png");
	static BufferedImage siteHover = (BufferedImage) Filestream.getImageFromPath("siteHover.png");
	static BufferedImage settings = (BufferedImage) Filestream.getImageFromPath("settings.png");
	static BufferedImage settingsHover = (BufferedImage) Filestream.getImageFromPath("settingsHover.png");
	static BufferedImage serverico = (BufferedImage) Filestream.getImageFromPath("server.png");
	static BufferedImage servericoHover = (BufferedImage) Filestream.getImageFromPath("serverHover.png");
	static BufferedImage runeRed = (BufferedImage) Filestream.getImageFromPath("runeRed.png");
	static BufferedImage runeBlue = (BufferedImage) Filestream.getImageFromPath("runeBlue.png");
	static BufferedImage runeGreen = (BufferedImage) Filestream.getImageFromPath("runeGreen.png");
	
	static BufferedImage setbg = (BufferedImage) Filestream.getImageFromPath("cardtextbg.png");
	static BufferedImage setClassic = (BufferedImage) Filestream.getImageFromPath("setClassic.png");
	static BufferedImage setWar = (BufferedImage) Filestream.getImageFromPath("setWar.png");
	static BufferedImage setSea = (BufferedImage) Filestream.getImageFromPath("setSea.png");
	static BufferedImage setDesert = (BufferedImage) Filestream.getImageFromPath("setDesert.png");
	static BufferedImage setOrklad = (BufferedImage) Filestream.getImageFromPath("setOrklad.png");
	static BufferedImage setUndead = (BufferedImage) Filestream.getImageFromPath("setUndead.png");
	static BufferedImage setJungle = (BufferedImage) Filestream.getImageFromPath("setJungle.png");
	static BufferedImage setExplorer = (BufferedImage) Filestream.getImageFromPath("setExplorer.png");
	static BufferedImage setWerewolf = (BufferedImage) Filestream.getImageFromPath("setWerewolf.png");

	static BufferedImage bannerBoardstate = (BufferedImage) Filestream.getImageFromPath("bannerBoardstatus.png");
	static BufferedImage bannerCommon = (BufferedImage) Filestream.getImageFromPath("bannerCommon.png");
	static BufferedImage bannerUncommon = (BufferedImage) Filestream.getImageFromPath("bannerUncommon.png");
	static BufferedImage bannerMythic = (BufferedImage) Filestream.getImageFromPath("bannerMythic.png");
	static BufferedImage bannerRegal = (BufferedImage) Filestream.getImageFromPath("bannerRegal.png");
	static BufferedImage bannerVictory = (BufferedImage) Filestream.getImageFromPath("bannerVictory.png");
	static BufferedImage bannerDefeat = (BufferedImage) Filestream.getImageFromPath("bannerDefeat.png");
	static BufferedImage bannerSpell = (BufferedImage) Filestream.getImageFromPath("bannerSpell.png");
	static BufferedImage bannerEquipment = (BufferedImage) Filestream.getImageFromPath("bannerEquipment.png");
	static BufferedImage bannerStructure = (BufferedImage) Filestream.getImageFromPath("bannerStructure.png");
	
	static BufferedImage holystrike = (BufferedImage) Filestream.getImageFromPath("holystrike.png");
	static BufferedImage bloodbeam = (BufferedImage) Filestream.getImageFromPath("spell_deathbeam.png");
	static BufferedImage voidgift = (BufferedImage) Filestream.getImageFromPath("spell_curse.png");
	static BufferedImage siphon = (BufferedImage) Filestream.getImageFromPath("spell_brainfry.png");
	static BufferedImage infinityburst = (BufferedImage) Filestream.getImageFromPath("spell_planetshoot.png");
	static BufferedImage minion = (BufferedImage) Filestream.getImageFromPath("spell_ichor.png");
	static BufferedImage cleave = (BufferedImage) Filestream.getImageFromPath("spell_whirl.png");
	static BufferedImage freezespell = (BufferedImage) Filestream.getImageFromPath("spell_flashfreeze.png");
	static BufferedImage web = (BufferedImage) Filestream.getImageFromPath("spell_web.png");
	static BufferedImage bash = (BufferedImage) Filestream.getImageFromPath("spell_smash.png");
	static BufferedImage consume = (BufferedImage) Filestream.getImageFromPath("spell_banshee.png");
	static BufferedImage meditate = (BufferedImage) Filestream.getImageFromPath("spell_earthtotem.png");
	static BufferedImage dive = (BufferedImage) Filestream.getImageFromPath("spell_chislice.png");
	static BufferedImage defend = (BufferedImage) Filestream.getImageFromPath("spell_defend.png");
	static BufferedImage fist = (BufferedImage) Filestream.getImageFromPath("spell_oakfist.png");
	static BufferedImage target = (BufferedImage) Filestream.getImageFromPath("target.png");
	
	static BufferedImage speech = (BufferedImage) Filestream.getImageFromPath("speech.png");
	static BufferedImage speechTop = (BufferedImage) Filestream.getImageFromPath("speechTop.png");
	static BufferedImage speechBot = (BufferedImage) Filestream.getImageFromPath("speechBot.png");

	static BufferedImage starEmpty = (BufferedImage) Filestream.getImageFromPath("starEmpty.png");
	static BufferedImage starFull = (BufferedImage) Filestream.getImageFromPath("starFull.png");
	
	static BufferedImage energyEmpty = (BufferedImage) Filestream.getImageFromPath("energyEmpty.png");
	static BufferedImage energyFull = (BufferedImage) Filestream.getImageFromPath("energyFull.png");

	static BufferedImage logoOrklad = (BufferedImage) Filestream.getImageFromPath("logoOrklad.png");
	static BufferedImage logoNorthgarde = (BufferedImage) Filestream.getImageFromPath("logoNorthgarde.png");
	static BufferedImage logoEboncreed = (BufferedImage) Filestream.getImageFromPath("logoEboncreed.png");
	static BufferedImage logoSteamguild = (BufferedImage) Filestream.getImageFromPath("logoSteamguild.png");
	static BufferedImage logoLegion = (BufferedImage) Filestream.getImageFromPath("logoAhnet.png");

	static BufferedImage iconLock = (BufferedImage) Filestream.getImageFromPath("iconLock.png");
	static BufferedImage iconDurability = (BufferedImage) Filestream.getImageFromPath("iconDurability.png");
	static BufferedImage iconAttack = (BufferedImage) Filestream.getImageFromPath("iconAttack.png");
	static BufferedImage iconHealth = (BufferedImage) Filestream.getImageFromPath("iconHealth.png");
	static BufferedImage iconSpeed = (BufferedImage) Filestream.getImageFromPath("iconSpeed.png");
	static BufferedImage arrowRight = (BufferedImage) Filestream.getImageFromPath("arrow.png");
	static BufferedImage arrowLeft = flip(arrowRight);

	static BufferedImage slot_head = (BufferedImage) Filestream.getImageFromPath("slot_head.png");
	static BufferedImage slot_foot = (BufferedImage) Filestream.getImageFromPath("slot_foot.png");
	static BufferedImage slot_weapon = (BufferedImage) Filestream.getImageFromPath("slotWeapon.png");
	static BufferedImage slot_ring = (BufferedImage) Filestream.getImageFromPath("slot_ring.png");
	static BufferedImage slot_shoulder = (BufferedImage) Filestream.getImageFromPath("slot_shoulder.png");

	static Image loading = Filestream.loadGif("loading.gif");
	static Image holynova = Filestream.loadGif("holyNova.gif");
	static Image flamering = Filestream.loadGif("flamering.gif");

	static BufferedImage bg_map = (BufferedImage) Filestream.getImageFromPath("map.png");
	static BufferedImage mapOriginal = (BufferedImage) Filestream.getImageFromPath("mapOriginal.png");
	
	static BufferedImage cardback = (BufferedImage) Filestream.getImageFromPath("cardback.png");
	static BufferedImage cardbackClosed = (BufferedImage) Filestream.getImageFromPath("cardback_closed.png");
	
static BufferedImage cardBorderTop = (BufferedImage) Filestream.getImageFromPath("borderTop.png");
	
	static BufferedImage cardBorderRight = Bank.rotate(cardBorderTop, 90);
	static BufferedImage cardBorderBottom = Bank.rotate(cardBorderTop, 180);
	static BufferedImage cardBorderLeft = Bank.rotate(cardBorderTop, 270);
	
	static BufferedImage corner = (BufferedImage) Filestream.getImageFromPath("borderCorner.png");
	
	static BufferedImage cornerTopleft = Bank.rotate(corner, 90);
	static BufferedImage cornerBottomright = Bank.rotate(cornerTopleft, 180);
	static BufferedImage cornerBottomleft = Bank.flip(cornerBottomright);
	static BufferedImage cornerTopright = Bank.flip(cornerTopleft);
	
static BufferedImage cardBorderTop1 = (BufferedImage) Filestream.getImageFromPath("borderTop1.png");
	
	static BufferedImage cardBorderRight1 = Bank.rotate(cardBorderTop1, 90);
	static BufferedImage cardBorderBottom1 = Bank.rotate(cardBorderTop1, 180);
	static BufferedImage cardBorderLeft1 = Bank.rotate(cardBorderTop1, 270);
	
	static BufferedImage corner1 = (BufferedImage) Filestream.getImageFromPath("borderCorner1.png");
	
	static BufferedImage cornerTopleft1 = Bank.rotate(corner1, 90);
	static BufferedImage cornerBottomright1 = Bank.rotate(cornerTopleft1, 180);
	static BufferedImage cornerBottomleft1 = Bank.flip(cornerBottomright1);
	static BufferedImage cornerTopright1 = Bank.flip(cornerTopleft1);
	
	static BufferedImage cardBorderTop2 = (BufferedImage) Filestream.getImageFromPath("borderTop2.png");
	
	static BufferedImage cardBorderRight2 = Bank.rotate(cardBorderTop2, 90);
	static BufferedImage cardBorderBottom2 = Bank.rotate(cardBorderTop2, 180);
	static BufferedImage cardBorderLeft2 = Bank.rotate(cardBorderTop2, 270);
	
	static BufferedImage corner2 = (BufferedImage) Filestream.getImageFromPath("borderCorner2.png");
	
	static BufferedImage cornerTopleft2 = Bank.rotate(corner2, 90);
	static BufferedImage cornerBottomright2 = Bank.rotate(cornerTopleft2, 180);
	static BufferedImage cornerBottomleft2 = Bank.flip(cornerBottomright2);
	static BufferedImage cornerTopright2 = Bank.flip(cornerTopleft2);
	
	
	static BufferedImage particleFire_0 = (BufferedImage) Filestream.getImageFromPath("particles/fire_0.png");
	static BufferedImage particleFire_1 = (BufferedImage) Filestream.getImageFromPath("particles/fire_1.png");
	static BufferedImage particleFire_2 = (BufferedImage) Filestream.getImageFromPath("particles/fire_2.png");
	static BufferedImage particleFire_3 = (BufferedImage) Filestream.getImageFromPath("particles/fire_3.png");
	static BufferedImage particleFire_4 = (BufferedImage) Filestream.getImageFromPath("particles/fire_4.png");
	static BufferedImage particleFire_5 = (BufferedImage) Filestream.getImageFromPath("particles/fire_5.png");
	static BufferedImage particleFire_6 = (BufferedImage) Filestream.getImageFromPath("particles/fire_6.png");
	static BufferedImage particleFire_7 = (BufferedImage) Filestream.getImageFromPath("particles/fire_7.png");
	
	static BufferedImage particleSkull_0 = (BufferedImage) Filestream.getImageFromPath("particles/skull_0.png");
	static BufferedImage particleSkull_1 = (BufferedImage) Filestream.getImageFromPath("particles/skull_1.png");
	static BufferedImage particleSkull_2 = (BufferedImage) Filestream.getImageFromPath("particles/skull_2.png");
	static BufferedImage particleSkull_3 = (BufferedImage) Filestream.getImageFromPath("particles/skull_3.png");
	static BufferedImage particleSkull_4 = (BufferedImage) Filestream.getImageFromPath("particles/skull_4.png");
	static BufferedImage particleSkull_5 = (BufferedImage) Filestream.getImageFromPath("particles/skull_5.png");
	static BufferedImage particleSkull_6 = (BufferedImage) Filestream.getImageFromPath("particles/skull_6.png");
	
	static BufferedImage particleIron_0 = (BufferedImage) Filestream.getImageFromPath("particles/iron_0.png");
	static BufferedImage particleIron_1 = (BufferedImage) Filestream.getImageFromPath("particles/iron_1.png");
	static BufferedImage particleIron_2 = (BufferedImage) Filestream.getImageFromPath("particles/iron_2.png");
	static BufferedImage particleIron_3 = (BufferedImage) Filestream.getImageFromPath("particles/iron_3.png");
	static BufferedImage particleIron_4 = (BufferedImage) Filestream.getImageFromPath("particles/iron_4.png");
	static BufferedImage particleIron_5 = (BufferedImage) Filestream.getImageFromPath("particles/iron_5.png");
	static BufferedImage particleIron_6 = (BufferedImage) Filestream.getImageFromPath("particles/iron_6.png");
	static BufferedImage particleIron_7 = (BufferedImage) Filestream.getImageFromPath("particles/iron_7.png");
	static BufferedImage particleIron_8 = (BufferedImage) Filestream.getImageFromPath("particles/iron_8.png");
	static BufferedImage particleIron_9 = (BufferedImage) Filestream.getImageFromPath("particles/iron_9.png");
	static BufferedImage particleIron_10 = (BufferedImage) Filestream.getImageFromPath("particles/iron_10.png");
	static BufferedImage particleIron_11 = (BufferedImage) Filestream.getImageFromPath("particles/iron_11.png");
	static BufferedImage particleIron_12 = (BufferedImage) Filestream.getImageFromPath("particles/iron_12.png");
	static BufferedImage particleIron_13 = (BufferedImage) Filestream.getImageFromPath("particles/iron_13.png");
	static BufferedImage particleIron_14 = (BufferedImage) Filestream.getImageFromPath("particles/iron_14.png");
	
	static BufferedImage particleSwirl_0 = (BufferedImage) Filestream.getImageFromPath("particles/swirl_0.png");
	static BufferedImage particleSwirl_1 = (BufferedImage) Filestream.getImageFromPath("particles/swirl_1.png");
	static BufferedImage particleSwirl_2 = (BufferedImage) Filestream.getImageFromPath("particles/swirl_2.png");
	static BufferedImage particleSwirl_3 = (BufferedImage) Filestream.getImageFromPath("particles/swirl_3.png");
	static BufferedImage particleSwirl_4 = (BufferedImage) Filestream.getImageFromPath("particles/swirl_4.png");
	static BufferedImage particleSwirl_5 = (BufferedImage) Filestream.getImageFromPath("particles/swirl_5.png");
	
	static BufferedImage particleBloom_0 = (BufferedImage) Filestream.getImageFromPath("particles/bloom_0.png");
	static BufferedImage particleBloom_1 = (BufferedImage) Filestream.getImageFromPath("particles/bloom_1.png");
	static BufferedImage particleBloom_2 = (BufferedImage) Filestream.getImageFromPath("particles/bloom_2.png");
	static BufferedImage particleBloom_3 = (BufferedImage) Filestream.getImageFromPath("particles/bloom_3.png");
	static BufferedImage particleBloom_4 = (BufferedImage) Filestream.getImageFromPath("particles/bloom_4.png");
	static BufferedImage particleBloom_5 = (BufferedImage) Filestream.getImageFromPath("particles/bloom_5.png");
	static BufferedImage particleBloom_6 = (BufferedImage) Filestream.getImageFromPath("particles/bloom_6.png");
	static BufferedImage particleBloom_7 = (BufferedImage) Filestream.getImageFromPath("particles/bloom_7.png");
	
	static BufferedImage particleShatter_0 = (BufferedImage) Filestream.getImageFromPath("particles/shatter_0.png");
	static BufferedImage particleShatter_1 = (BufferedImage) Filestream.getImageFromPath("particles/shatter_1.png");
	static BufferedImage particleShatter_2 = (BufferedImage) Filestream.getImageFromPath("particles/shatter_2.png");
	static BufferedImage particleShatter_3 = (BufferedImage) Filestream.getImageFromPath("particles/shatter_3.png");
	static BufferedImage particleShatter_4 = (BufferedImage) Filestream.getImageFromPath("particles/shatter_4.png");
	static BufferedImage particleShatter_5 = (BufferedImage) Filestream.getImageFromPath("particles/shatter_5.png");
	static BufferedImage particleShatter_6 = (BufferedImage) Filestream.getImageFromPath("particles/shatter_6.png");
	static BufferedImage particleShatter_7 = (BufferedImage) Filestream.getImageFromPath("particles/shatter_7.png");
	
	static BufferedImage particleBubble_0 = (BufferedImage) Filestream.getImageFromPath("particles/bubble_0.png");
	static BufferedImage particleBubble_1 = (BufferedImage) Filestream.getImageFromPath("particles/bubble_1.png");
	static BufferedImage particleBubble_2 = (BufferedImage) Filestream.getImageFromPath("particles/bubble_2.png");
	static BufferedImage particleBubble_3 = (BufferedImage) Filestream.getImageFromPath("particles/bubble_3.png");
	
	public static String path = getPath();
	public static File accFile = new File(Bank.path+"serverData/accounts");
	
	public static String getPath(){
		if(PanelMainmenu.autoServer){
			try {
				return Bank.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().replaceAll("cardgameserver.jar", "")+"/"+Properties.gameName+"/";
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} 
		}else{
			return System.getenv("APPDATA")+"/"+Properties.gameName+"/";
		}
		return System.getenv("APPDATA")+"/"+Properties.gameName+"/";
	}
	
	static MainServer getServer(){
		return server;
	}
	
	public static void drawOutline(Graphics g, int x, int y, int w, int h, Color c, int size){
		g.setColor(c);
		g.fillRect(x, y, w, size);
		g.fillRect(x, y, size, h);
		g.fillRect(x+w-size, y, size, h);
		g.fillRect(x, y+h-size, w, size);
	}
	
	public static void drawOutlineMid(Graphics g, int x, int y, int w, int h, Color c, int size){
		g.setColor(c);
		g.fillRect(x-size/2, y, w, size);
		g.fillRect(x-size/2, y, size, h);
		g.fillRect(x+w-size/2, y, size, h);
		g.fillRect(x, y+h-size/2, w, size);
	}
	
	public static void drawOutlineOut(Graphics g, int x, int y, int w, int h, Color c, int size){
		g.setColor(c);
		g.fillRect(x-size, y-size, w+size, size);
		g.fillRect(x-size, y-size, size, h+size);
		g.fillRect(x+w, y-size, size, h+size);
		g.fillRect(x-size, y+h, w+size*2, size);
	}
	
	public static void drawOvalOutlineOut(Graphics g, int x, int y, int w, int h, Color c, int size){
		g.setColor(c);
		for(int i = 0; i < size; ++i){
			g.drawOval(x-i, y-i, w+i*2, h+i*2);
		}
	}
	
	public static BufferedImage[] getSpriteSheet(String sheet, int size){
		if(!Bank.isServer()){
			BufferedImage bi = (BufferedImage) Filestream.getImageFromPath(sheet);
			int x = bi.getWidth() / size, y = bi.getHeight() / size;
			BufferedImage[] imgs = new BufferedImage[x * y];
			for (int i = 0; i < y; i++){
			    for (int j = 0; j < x; j++){
			    	imgs[(i * x) + j] = (bi).getSubimage(j*size,i*size,size,size);
			    }
			}
			return imgs;
		}
		return null;
	}
	
	static BufferedImage cropImage(BufferedImage src, Rectangle rect) {
	      BufferedImage dest = src.getSubimage(rect.x, rect.y, rect.width, rect.height);
	      return dest; 
	}
	
	public static String getLevelData(String lvl){
		InputStream is = Bank.class.getResourceAsStream("levels/"+lvl+Properties.fileExt); 
		return convertStreamToString(is);
	}
	
	public static String[] getBio(String str){
		InputStream is = Bank.class.getResourceAsStream(str); 
		String[] s = convertStreamToStringArray(is);
		return s;
	}
	
	public static String getRawLevelData(String lvl){
		return Bank.getRawdirDataLine(lvl);
	}
	
	static String convertStreamToString(java.io.InputStream is) {
	    Scanner s = new Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
	/**
	 * Reads an IS to a 1D string array with 30 or less pieces
	 * @param is
	 * @return
	 */
	static String[] convertStreamToStringArray(java.io.InputStream is) {
	    Scanner s = new Scanner(is).useDelimiter("\\A");
	    int index = 0;
	    String[] ret = new String[30];
	    while(s.hasNextLine()){
	    	ret[index] = s.nextLine();
	    	++index;
	    }
	    return ret;
	}
	
	public static boolean hasLockVal(String f, int index){
		return getRawdirDataLine(path+"data/"+f).split("0")[index].contains("1");
	}
	
	public static boolean[] produceLockvalArray(String f){
		String[] sa = getRawdirDataLine(path+"data/"+f).split("0");
		boolean[] ba = new boolean[sa.length];
		for(int i = 0; i < sa.length; ++i){
			if(sa[i].contains("1"))ba[i]=true;
		}
		return ba;
	}
	
	public static boolean setLockVal(String p, int index, boolean state){
		boolean ret = false;
		StringBuilder sb = new StringBuilder();
		String s = getRawdirDataLine(Bank.path+"data/"+p);
		String[] ns = s.split("0");
		if(ns[index].equals("1"))ret=true;
		if(state){
			ns[index] = "1";
		}else{
			ns[index] = (""+(2+Util.rand.nextInt(8)));
		}
		for(int i = 0; i < ns.length; ++i){
			sb.append(ns[i]+"0");
		}
		setContentsRawdir(Bank.path+"data/"+p, sb.toString());
		return ret;
	}
	
	public static void createLockFile(String s1){
		String s = "";
		for(int i = 0; i < 255; ++i){
			s+=((2+Util.rand.nextInt(8))+"0");
		}
		Bank.setContentsRawdir(Bank.path+s1, s);
	}
	
	public static void init(){
		//CardList.initLists();
		//CardList.flushCards();
		int count = 0;
		//Util.initResourcepack();
		new File(path).mkdirs();
		File sFolder = new File(path+"serverData");
		File dFolder = new File(path+"data");
		File tFolder = new File(path+"plugins");
		File rFolder = new File(path+"resourcepacks");
		File deckFolder = new File(path+"decks");
		File serverFolder = new File(path+"server_patches");
		File serverPropertiesFile = new File(path+"server.properties");
		File coreSetFile = new File(path+"data/coreset"+Properties.fileExt);
		sFolder.mkdirs();
		dFolder.mkdirs();
		tFolder.mkdirs();
		rFolder.mkdirs();
		serverFolder.mkdirs();
		deckFolder.mkdirs();
		accFile.mkdirs();
		File core = new File(path+"data/Core"+Properties.fileExt);
		File keybinds = new File(path+"data/keybinds"+Properties.fileExt);
		File settings = new File(path+"data/settings"+Properties.fileExt);
		File realmlist = new File(path+"data/realmlist"+Properties.fileExt);
		try {
			if(keybinds.createNewFile()){}
			if(core.createNewFile()){}
			if(settings.createNewFile()){}
			if(realmlist.createNewFile()){
				Bank.setContentsRawdir(realmlist.getPath(), "logon.talesofterroth.com");
			}
			if(coreSetFile.createNewFile()){
				Bank.fillCoreFile(coreSetFile);
			}
			if(serverPropertiesFile.createNewFile()){
				Analysis.setKey(serverPropertiesFile, "ip","0.0.0.0");
				Analysis.setKey(serverPropertiesFile, "min_players","2");
				Analysis.setKey(serverPropertiesFile, "max_players","2");
				Analysis.setKey(serverPropertiesFile, "maxTurnTime","120000");
				Analysis.setKey(serverPropertiesFile, "maxUnits","9");
				Analysis.setKey(serverPropertiesFile, "autostartPlayers","2");
				Analysis.setKey(serverPropertiesFile, "localMapID","-1");
				Analysis.setKey(serverPropertiesFile, "gameEvents","1");
				Analysis.setKey(serverPropertiesFile, "maxRooms","10");
				Analysis.setKey(serverPropertiesFile, "gameVersion","0");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static BufferedImage rotate(BufferedImage img, double angle)
	{
		if(Bank.isServer() || PanelMainmenu.autoServer){
			return null;
		}
	    double sin = Math.abs(Math.sin(Math.toRadians(angle))),
	           cos = Math.abs(Math.cos(Math.toRadians(angle)));

	    int w = img.getWidth(null), h = img.getHeight(null);

	    int neww = (int) Math.floor(w*cos + h*sin),
	        newh = (int) Math.floor(h*cos + w*sin);

	    BufferedImage bimg = new BufferedImage(neww, newh, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = bimg.createGraphics();

	    g.translate((neww-w)/2, (newh-h)/2);
	    g.rotate(Math.toRadians(angle), w/2, h/2);
	    g.drawRenderedImage(img, null);
	    g.dispose();

	    return bimg;
	}
	
	 public static BufferedImage flip(BufferedImage bi) {
		 if(Bank.isServer() || PanelMainmenu.autoServer){
				return null;
			}
	        BufferedImage flipped = new BufferedImage(
	                bi.getWidth(),
	                bi.getHeight(),
	                BufferedImage.TYPE_INT_ARGB);
	        AffineTransform tran = AffineTransform.getTranslateInstance(bi.getWidth(), 0);
	        AffineTransform flip = AffineTransform.getScaleInstance(-1d, 1d);
	        tran.concatenate(flip);

	        Graphics2D g = flipped.createGraphics();
	        g.setTransform(tran);
	        g.drawImage(bi, 0, 0, null);
	        g.dispose();
	       return flipped;
	   }
	 
		public static String getRawdirDataLine(String f){
			File file = new File(f);
			try {
				file.createNewFile();
				FileReader w = new FileReader(file);
				BufferedReader bw = new BufferedReader(w);
				return bw.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public static String readAll(File file){
			String str = "";
			try {
				file.createNewFile();
				FileReader w = new FileReader(file);
				BufferedReader br = new BufferedReader(w);
				String line = br.readLine();
			    while (line != null) {
			        str+=line;
			        line = br.readLine();
			    }

			    br.close();			
			    } catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
		
		public static void setContents(String file, String n){
			File f = new File(Bank.getPath()+file);
			try {
				f.createNewFile();
				FileWriter w = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(w);
				bw.flush();
				bw.write(n);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public static void setContentsRawdir(String file, String n){
			File f = new File(file);
			try {
				f.createNewFile();
				FileWriter w = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(w);
				bw.flush();
				bw.write(n);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public static boolean isServer() {
			return server!=null;
		}

		public static boolean isClient() {
			return client!=null;
		}

		public static void fillCoreFile(File set) {
			Bank.setContentsRawdir(set.getPath(), "20%130%46%177%75%24%900%91%109%150%137%216%142%545%209%99%164%340%267%368%25%365%207%29%215%49%43%925%78%113%161%21%28%904%45%157%151%144%61%141%190%115%67%149%146%377%27%364%70%166%100%259%103%532%386%543%549%552%554%555%372%409%48%308%37");
		}
}
