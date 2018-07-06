package org.author.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class GUISelectHero extends GUI{
	
	private CardShell[] heroes = new CardShell[]{
			new CardShell(Card.commHera), new CardShell(Card.commBashar), new CardShell(Card.commOzaaroth), new CardShell(Card.commRangrim), new CardShell(Card.commTikesh),
			new CardShell(Card.commYorg), new CardShell(Card.commShanzin), new CardShell(Card.commArachna), new CardShell(Card.commChristy),new CardShell(Card.commJaram),
	};
	
	private int selection = 0, actionType = 1;

	public GUISelectHero() {
		super(Properties.width/2-Properties.width/4, Properties.height/2-Properties.height/4, Properties.width/2, Properties.height/2);
		this.showSelection = false;
	}

	public void tick(double delta) {
	}

	public void draw(Graphics g, int x, int y) {
		g.setColor(Util.transparent);
		g.fillRect(0, 0, Properties.width, Properties.height);
		int perLine = 5, dw = Properties.height/5, spacing = 40;
		Rectangle conf = new Rectangle(Properties.width/2-Properties.width/8, (heroes.length / perLine) * (dw + spacing) + 32, Properties.width/4, Properties.width/20);
		g.drawImage(conf.contains(Display.currentScreen.mousePoint) ? Bank.buttonHover : Bank.button, conf.x, conf.y, conf.width, conf.height, null);
		g.setFont(Util.cooldownBold);
		g.setColor(conf.contains(Display.currentScreen.mousePoint) ? Color.BLACK : Color.WHITE);
		if(actionType==0)
		g.drawString("Confirm", Properties.width/2-g.getFontMetrics().stringWidth("Confirm")/2, conf.y+conf.height/5*3);
		if(actionType==1)
		g.drawString("Create New Deck", Properties.width/2-g.getFontMetrics().stringWidth("Create New Deck")/2, conf.y+conf.height/5*3);
		if(Display.currentScreen.clicking && conf.contains(Display.currentScreen.mousePoint)){
			confirm();
		}
		for(int i = 0; i < heroes.length; ++i){
			CardShell cs = heroes[i];
			Card c = heroes[i].getCard();
			int ref = i+1;
			int dy = (i/perLine) * (dw + spacing);
			Rectangle rect = new Rectangle(Properties.width/2 - ((dw + spacing) * perLine)/2 + i * (dw + spacing) - (perLine*((i/perLine)*(dw + spacing))), 32 + dy, dw, dw);
			g.drawImage(cs.getImg(), rect.x, rect.y, rect.width, rect.height, null);
			int iconSize = rect.width / 4;
			Bank.drawOutlineOut(g, rect.x, rect.y, rect.width, rect.height, (selection == i ? Color.YELLOW : Color.BLACK), 4);
			g.drawImage(CardList.getListForCard(c).getIcon(), rect.x + rect.width - iconSize/2, rect.y - iconSize/2, iconSize, iconSize, null);
		}
		for(int i = 0; i < heroes.length; ++i){
			Card c = heroes[i].getCard();
			int ref = i+1;
			int dy = (i/perLine) * (dw + spacing);
			Rectangle rect = new Rectangle(Properties.width/2 - ((dw + spacing) * perLine)/2 + i * (dw + spacing) - (perLine*((i/perLine)*(dw + spacing))), 32 + dy, dw+spacing, dw+spacing);
			if(rect.contains(Display.currentScreen.mousePoint)){
				int cdh = Properties.height / 2;
				int cdw = (int) (cdh * 0.75);
				c.draw(heroes[i].getImg(), g, Properties.width/2 + Properties.width/4, Properties.height/2-cdh/2, cdw, cdh, true);
				if(Display.currentScreen.clicking)selection = i;
			}
		}
	}
	
	public void confirm(){
		Display.currentScreen = new PanelDeckbuilder(heroes[selection].getCard());
		Display.cam.refocus();
	}
}
