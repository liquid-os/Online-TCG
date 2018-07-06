package org.author.game;

import java.awt.Point;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Packet23Misc extends Packet{
	
	private int type;
	private String dat;
	
	public Packet23Misc(byte[] data) {
		super(23);
		type = Integer.parseInt(read(data)[1]);
		dat = read(data)[2];
	}
	
	public Packet23Misc(int c1, String d) {
		super(23);
		this.type = c1;
		this.dat = d;
	}
	
	public byte[] getData() {
		return ("23#"+type+"#"+dat).getBytes();
	}
	
	public void resolve(){
		if(type == 0){ //sending collection
			if(Bank.isClient()){
				String[] collectionParts = dat.split("%");
				for(String s : collectionParts){
					Util.collection[Integer.parseInt(s.trim())]++;
					System.out.println("CLIENT got confirmation of unlocking for: "+Card.all[Integer.parseInt(s.trim())].getName());
				}
			}
		}
		if(type == 1){ //add card pack
			if(Bank.isClient()){
				int amt = Integer.parseInt(dat);
				Util.packs += amt;
			}
		}
		if(type == 2){ //SET pack count
			if(Bank.isClient()){
				int amt = Integer.parseInt(dat);
				Util.packs = amt;
			}
		}
		if(type == 3){ //add gold
			if(Bank.isClient()){
				int amt = Integer.parseInt(dat);
				Util.coins += amt;
			}
		}
		if(type == 4){ //SET pack & gold count
			if(Bank.isClient()){
				String[] split = dat.split("%");
				int amt = Integer.parseInt(split[0]);
				int amt1 = Integer.parseInt(split[1]);
				Util.packs = amt;
				Util.coins = amt1;
			}
		}
		if(type == 7){
			Util.coins -= Util.COST_PACK;
			Util.packs++;
			if(Display.currentScreen instanceof PanelShop){
				PanelShop panel = (PanelShop)Display.currentScreen;
				panel.buyPack.active = true;
				panel.buyPack.setIcon(Bank.coin);
				Point chestPoint = new Point(panel.rect.x + panel.rect.width / 2, panel.rect.y + panel.rect.height / 2);
				Util.drawParticleLine(panel.getMousePoint(), chestPoint, 25, false, "SHINE");
				Animation.createStaticAnimation(panel.rect.x + panel.rect.width / 2, panel.rect.y + panel.rect.height / 2, "fx3_fireBall", 3, 800);
			}
		}
	}
	
	public void resolve(PlayerMP sender){
		if(type == 5){ //CRAFT CARD
			if(Bank.isServer()){
				int c = Integer.parseInt(dat);
				if(c > 0){
					Card card = Card.all[c];
					int cost = (int)(Util.getDustValue(card) * Card.CRAFT_MULTI);
					if(sender.getCoins() > cost){
						sender.setCoins(sender.getCoins() - cost);
						sender.addToCollection(card, 1);
						Bank.getServer().modifyAccountValue(sender.name, 5, ""+sender.getCoins());
					}
				}
			}
		}
		if(type == 6){ //CRAFT CARD
			if(Bank.isServer()){
				int c = Integer.parseInt(dat);
				if(c > 0){
					Card card = Card.all[c];
					int value = (int)(Util.getDustValue(card));
					if(sender.getCollection()[card.getId()] > 0){
						sender.setCoins(sender.getCoins() + value);
						sender.getCollection()[card.getId()] = 0;
						sender.saveCollection();
						Bank.getServer().modifyAccountValue(sender.name, 5, ""+sender.getCoins());
					}
				}
			}
		}
		if(type == 7){ //buy pack
			int cost = Util.COST_PACK;
			if(sender.getCoins() > cost){
				sender.setCoins(sender.getCoins() - cost);
				Bank.getServer().modifyAccountValue(sender.name, 5, ""+sender.getCoins());
				Bank.getServer().send(getData(), sender.ip, sender.port);
				Bank.getServer().modifyAccountValue(sender.name, 4, ""+(Integer.parseInt(Bank.getServer().getAccountInfo(sender.name)[4]) + 1));
				Bank.getServer().getLog().add("Player "+sender.name+" purchased a pack with gold.");
			}
		}
	}
}
