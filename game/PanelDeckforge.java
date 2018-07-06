package org.author.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;

public class PanelDeckforge extends PanelBase {
	
	int mapScale = 5;
	int scrollX = rand.nextInt(Properties.width*mapScale/2), scrollY = rand.nextInt(Properties.height*mapScale/2);
	int destScrollX = rand.nextInt(Properties.width*mapScale/2), destScrollY = rand.nextInt(Properties.height*mapScale/2);
	long lastRescroll = System.currentTimeMillis();
	int rescrollTime = 3000;
	
	long start = System.currentTimeMillis();
	boolean introFinished = false;
	
	public PanelDeckforge(){
		CardList.initLists();
		//Util.music = new ClipShell("music", "menumusic.wav");
		scrollX = 800;
		int bw = Properties.width/5;
		guis.add(new GUIButton(Bank.button, Bank.buttonHover, 1, Properties.width/2-bw/2, Properties.height/10, bw, bw/3).setText("Create New Deck"));
		guis.add(new GUIButton(Bank.button, Bank.buttonHover, 2, Properties.width/2-bw/2, Properties.height/10+bw/2, bw, bw/3).setText("Edit Deck"));
		guis.add(new GUIButton(Bank.button, Bank.buttonHover, 4, Properties.width/2-bw/2, Properties.height/10+bw, bw, bw/3).setText("Browse Cards"));
		guis.add(new GUIButton(Bank.button, Bank.buttonHover, 3, Properties.width/2-bw/2, Properties.height/10+bw+bw/2, bw, bw/3).setText("Exit"));
	}
	
	public void buttonReact(int id){
		if(id==1){
			PanelDeckbuilder pan = new PanelDeckbuilder(null);
			Display.currentScreen = pan;
			Display.cam.refocus();
		}
		if(id==2){
			String path = Bank.path+"decks/";
			JFileChooser fc = new JFileChooser(path);
			fc.showOpenDialog(null);
			File file = fc.getSelectedFile();
			String deck = Bank.getRawdirDataLine(file.getPath());
			Card hero = Card.all[Integer.parseInt(deck.split("&")[1])];
			PanelDeckbuilder pan = new PanelDeckbuilder(hero);
			pan.load(file, deck);
			Display.currentScreen = pan;
			Display.cam.refocus();	
		}
		if(id==3){
			Display.currentScreen = new PanelMainmenu();
			Display.cam.refocus();
		}
		if(id==4){
			PanelDeckbuilder pan = new PanelDeckbuilder(Card.filler);
			Display.currentScreen = pan;
			pan.setMode(-1);
			Display.cam.refocus();
		}
	}
	


	public void onUpdate() {
		if(System.currentTimeMillis()-lastRescroll >= rescrollTime){
			lastRescroll = System.currentTimeMillis();
			destScrollX = rand.nextInt(Properties.width*mapScale/2);
			destScrollY = rand.nextInt(Properties.height*mapScale/2);
		}
			if(scrollX > destScrollX)--scrollX;
			if(scrollX < destScrollX)++scrollX;
			if(scrollY > destScrollY)--scrollY;
			if(scrollY < destScrollY)++scrollY;
	}
	
	public void renderForeground(Graphics g){
		if(!introFinished){
			int a = 255 - (int) ((double)((double)(System.currentTimeMillis() - start) / (double)4000) * (double)255);
			if(a < 0)a = 0; if(a > 255)a = 255;
			g.setColor(new Color(0,0,0,a));
			g.fillRect(0, 0, Properties.width, Properties.height);
			if(a <= 0)introFinished = true;
		}
	}

	public void drawScreen(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Properties.width, Properties.height);
		g.drawImage(Bank.bg_map, Properties.width/2 - (Properties.width*mapScale)/2+scrollX, Properties.height/2 - (Properties.height*mapScale)/2+scrollY, Properties.width*mapScale, Properties.height*mapScale, null);
		/*g.setColor(Color.LIGHT_GRAY);
		g.fillOval(Properties.width/2 + Properties.width / 6, Properties.height/2-120+300-Properties.height/4, 480, 150);
		g.setColor(Color.BLACK);
		g.drawOval(Properties.width/2 + Properties.width / 6, Properties.height/2-120+300-Properties.height/4, 480, 150);
		for(int i = 0; i < 20; ++i)
		g.drawImage(Bank.cardbackClosed, Properties.width/2 + Properties.width / 4-i*4, Properties.height/2-120+i*2-Properties.height/4+40, 240, (int)(240 * 1.28), null);
		*/
	}
}
