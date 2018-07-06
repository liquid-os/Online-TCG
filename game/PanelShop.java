package org.author.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class PanelShop extends PanelBase{
	
	GUIButton buyPack;
	Rectangle rect = new Rectangle(20, Properties.height / 10, Properties.width / 10, Properties.width / 15);
	
	public PanelShop(){
		int bw = Properties.width / 3;
		int bh = bw / 8;
		buyPack = new GUIButton(Bank.button, Bank.buttonHover, 10, Properties.width / 2 - bw / 2, Properties.height / 2 - bh, bw, bh);
		buyPack.setText("Buy 1 Pack - 100");
		buyPack.setIcon(Bank.coin);
		buyPack.setIconStyle(GUI.ICON_STYLE_TRAIL);
		buyPack.font = Util.cooldownFont;
		buyPack.hoverFont = Util.cooldownBold;
		guis.add(buyPack);
	}
	
	public void buttonReact(int id){
		if(id == 10){
			if(Util.coins > Util.COST_PACK && buyPack.active){
				buyPack.active = false;
				buyPack.setIcon(Bank.loading);
				
				Packet23Misc pkt = new Packet23Misc(7, "1");
				pkt.write(Bank.client);
			}
		}
	}

	public void onUpdate() {
		
	}

	public void drawScreen(Graphics g) {
		int s = Properties.width / 10;
		for(int i = 0; i < Properties.width/s + 1; ++i){
			for(int j = 0; j < Properties.height/s + 1; ++j){
				g.drawImage(Bank.planks, i*s, j*s, s, s, null);
			}
		}
		//Bank.drawOvalOutlineOut(g, sel.x, sel.y, sel.w, sel.h, Color.GREEN, 10);	
		g.drawImage(Bank.borderScreen, 0, 0, Properties.width, Properties.height, null);
		g.setFont(Util.spellNameFont);
		g.drawImage(rect.contains(getMousePoint()) ? Bank.packOpen1 : Bank.packClosed, rect.x, rect.y, rect.width, rect.height, null);
		g.setColor(Color.BLACK);
		g.drawString(Util.packs+"", rect.x + rect.width - 12, rect.y + rect.height - 12);
		g.setColor(Color.WHITE);
		g.drawString(Util.packs+"", rect.x + rect.width - 10, rect.y + rect.height - 10);
		
		g.drawImage(Bank.coin, Properties.width - 80, 0, 64, 64, null);
		g.setFont(Util.cooldownBold);
		g.drawString(""+Util.coins, Properties.width - 85 - g.getFontMetrics().stringWidth(Util.coins+""), 40);
		
		if(clicking && rect.contains(getMousePoint())){
			Display.currentScreen = new PanelPackOpen(0);
			Display.cam.refocus();
		}
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			Display.currentScreen = new PanelMainmenu();
			Display.cam.refocus();
		}
	}
}
