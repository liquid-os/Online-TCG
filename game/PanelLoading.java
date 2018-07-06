package org.author.game;

import java.awt.Color;
import java.awt.Graphics;

public class PanelLoading extends PanelBase{
	
	boolean showWorryText = false;
	long start = System.currentTimeMillis();
	boolean connected = false;
	
	public PanelLoading(){
		guis.add(new GUIButton(Bank.button, Bank.buttonHover, 0, Properties.width/2-80, Properties.height-140, 160, 80).setText("Cancel"));
		guis.add(new GUIButton(Bank.button, Bank.buttonHover, 1, -300, Properties.height-140, 160, 80).setText("Cancel"));

	}

	public void onUpdate() {
		if(!showWorryText && System.currentTimeMillis()-start >= 7500){
			showWorryText = true;
		}
		if(connected){
			Display.currentScreen = new PanelBoard();
			Display.cam.refocus();
		}
	}
	
	public void buttonReact(int id){
		if(id == 0){
			Display.currentScreen = new PanelMainmenu();
			Display.cam.refocus();
		}
	}

	public void drawScreen(Graphics g) {
		/*int w = Properties.width/30;
		for(int i = 0; i < 30; ++i){
			for(int j = 0; j < 30; ++j){
				g.drawImage(Bank.brick, i*w, j*w, w, w, null);
			}
		}*/
		g.drawImage(Bank.mapOriginal, 0, 0, Properties.width, Properties.height, null);
		g.setColor(new Color(255,255,255,220));
		int lw = Properties.width/3;
		g.fillRect(Properties.width/2-lw/4, 0, lw/2, Properties.height);
		g.setColor(Color.BLACK);
		g.setFont(Util.cooldownBold);
		if(!connected)
		g.drawString("Connecting...", Properties.width/2-g.getFontMetrics().stringWidth("Connecting...")/2, Properties.height/2-120);
		else g.drawString("Connected!", Properties.width/2-g.getFontMetrics().stringWidth("Connected!")/2, Properties.height/2-120);
		g.drawImage(Bank.loading, Properties.width/2-lw/2, Properties.height/2-lw/2, lw, lw, null);
		if(showWorryText){
			g.setFont(Util.smallDescFont);
			g.drawString("Connection taking unusually long.", Properties.width/2-g.getFontMetrics().stringWidth("Connection taking unusually long.")/2, Properties.height/2+lw/2);
		}
	}

	public void setConnected(boolean b) {	
		this.connected = true;
	}
}
