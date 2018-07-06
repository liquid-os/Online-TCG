package org.author.game;

import java.awt.Graphics;

public class GUIDeckSelect extends GUI{

	public GUIDeckSelect(int x, int y) {
		super(x, y, Properties.width/3*2, Properties.height/3*2);
	}

	public void tick(double delta) {
		if(Display.currentScreen.clicking &! this.getRect().contains(Display.currentScreen.mousePoint)){
			Display.currentScreen.removeGUI(this);
		}
	}

	public void draw(Graphics g, int x, int y) {
		g.setColor(Util.transparent);
		g.fillRect(0, 0, Properties.width, Properties.height);
	}
}
