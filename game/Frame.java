package org.author.game;

import java.awt.*;

import javax.swing.*;


public class Frame extends JFrame{
	public Display p;
	
	public Frame()
	{
		p = new Display(this);
		this.setLayout(new GridLayout(1, 1, 0, 0));
		this.setTitle(Properties.gameName+" "+Properties.gameTitle+" v"+Properties.ver);
		this.setSize(Properties.width, Properties.height);
		this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setVisible(true);
		add(p);
		this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(Bank.blank, new Point(0,0), "BLANK_CURSOR"));
	}
}
