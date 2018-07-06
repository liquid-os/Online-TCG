package org.author.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class GUIPick extends GUI{
	
	private int selection = 0;
	
	Object source;
	//0 = UNIT, 1 = CARDSHELL
	private int sourceType = 1;

	public GUIPick(Object source) {
		super(Properties.width/2-Properties.width/4, Properties.height/2-Properties.height/4, Properties.width/2, Properties.height/2);
		this.showSelection = false;
		this.source = source;
		if(source instanceof Card)sourceType = 1;
		if(source instanceof CardShell)sourceType = 1;
		if(source instanceof Unit)sourceType = 0;
	}

	public void tick(double delta) {
	}

	public void draw(Graphics g, int x, int y) {
		g.setColor(Util.transparent);
		g.fillRect(0, 0, Properties.width, Properties.height);
		g.setColor(Color.WHITE);
		g.setFont(Util.largeNameFont);
		String title = "Select A Card";
		g.drawString(title, Properties.width / 2 - g.getFontMetrics().stringWidth(title)/2, Properties.height / 15 + 20);
		int perLine = 5, dw = Properties.width / 4 * 3 / Util.picks.size(), spacing = 20;
		g.setFont(Util.cooldownBold);
		for(int i = 0; i < Util.picks.size(); ++i){
			CardShell cs = Util.picks.get(i);
			Card c = cs.getCard();
			int ref = i+1;
			int dy = (i/perLine) * (dw + spacing);
			Rectangle rect = new Rectangle(Properties.width/2 - (Util.picks.size() * (dw + spacing)) / 2 + i * (dw + spacing) , Properties.height / 2 - dw / 2, dw, dw);
			boolean b = false;
			if(rect.contains(Display.currentScreen.getMousePoint())){
				b = true;
				if(Display.currentScreen.clicking){
					confirm(c);
				}
			}
			//c.draw(cs.getImg(), g, rect.x, rect.y, rect.width, rect.height, b);
			g.drawImage(cs.getImg(), rect.x, rect.y, rect.width, rect.height, null);
			int iconSize = rect.width / 4;
			Bank.drawOutlineOut(g, rect.x, rect.y, rect.width, rect.height, Color.GREEN, 4);
			//g.drawImage(CardList.getListForCard(c).getIcon(), rect.x + rect.width - iconSize/2, rect.y - iconSize/2, iconSize, iconSize, null);
		}
		for(int i = 0; i < Util.picks.size(); ++i){
			Card c = Util.picks.get(i).getCard();
			int ref = i+1;
			int dy = (i/perLine) * (dw + spacing);
			Rectangle rect = new Rectangle(Properties.width/2 - (Util.picks.size() * (dw + spacing)) / 2 + i * (dw + spacing) , Properties.height / 2 - dw / 2, dw, dw);
			if(rect.contains(Display.currentScreen.mousePoint)){
				int cdh = Properties.height / 2;
				int cdw = (int) (cdh * 0.75);
				c.draw(Util.picks.get(i).getImg(), g, Properties.width/2 + Properties.width/4, Properties.height/2-cdh/2, cdw, cdh, true);
				if(Display.currentScreen.clicking)selection = i;
			}
		}
	}
	
	public void confirm(Card c){
		int src = -1;
		if(sourceType == 0){
			src = ((Unit)source).GUID;
		}
		Packet15SelectPick packet = new Packet15SelectPick(c.getId(), src, sourceType);
		Bank.client.send(packet.getData());
		Display.currentScreen.guis.remove(this);
	}
}
