package org.author.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PanelPackOpen extends PanelBase {
	
	boolean sel = false;
	private GUIButton open, shop;
	
	private long packOpenStart = -1;
	
	private Rectangle openArea = null;
	
	private CardShell[] revealed = new CardShell[5];
	private boolean[] revealed_clicked = new boolean[5];
	private int[] revealed_dupes = new int[5];

	private long cardRevealTime = -1;
	
	private int set = 0;
	
	private CardSet[] sets = new CardSet[]{
			CardSet.SET_CLASSIC,	
			CardSet.SET_WAR,	
			CardSet.SET_SEA,	
			CardSet.SET_JUNGLE,	
			CardSet.SET_TRIBELANDS,	
			CardSet.SET_NIGHT,	
			CardSet.SET_IVORGLEN,	
			CardSet.SET_EXPEDITION,	
			CardSet.SET_ANURIM,	
	};
	
	public PanelPackOpen(int packs){
		int boxicosize = 85;
		for(int i = 0; i < revealed_clicked.length; ++i){
			revealed_clicked[i] = false;
		} 
		openArea = new Rectangle(Properties.width / 2 - Properties.width / 6, Properties.height / 2 - Properties.width / 6, Properties.width / 3, Properties.width / 3);
		guis.add(new GUIButton(Bank.site, Bank.siteHover, 6, 15, 15, boxicosize, boxicosize).setText(""));
		guis.add(new GUIButton(Bank.book, Bank.bookHover, 5, 15 + 15 + boxicosize, 15, boxicosize, boxicosize).setText(""));
		guis.add(new GUIButton(Bank.settings, Bank.settingsHover, 7, 15 + 15 + 15 + boxicosize + boxicosize, 15, boxicosize, boxicosize).setText(""));
		guis.add(new GUIButton(Bank.serverico, Bank.servericoHover, 8, 15 + 15 + 15 + 15 + boxicosize + boxicosize + boxicosize, 15, boxicosize, boxicosize).setText(""));
		int bw = Properties.width / 3;
		int bh = bw / 10;
		open = new GUIButton(Bank.button, Bank.buttonHover, "Open Pack", 1, Properties.width / 2 - bw / 2, Properties.height - Properties.height / 3, bw, bh);
		//guis.add(open);
		this.renderObj = false;
		//Util.packs = packs;
		shop = new GUIButton(Bank.goldbag, Bank.goldbagHover, 9, 15 + 15 + 15 + 15 + 15 + boxicosize + boxicosize + boxicosize + boxicosize, 15, boxicosize, boxicosize).setText("");
		guis.add(shop);
	}
	
	public void buttonReact(int id){
		super.buttonReact(id);
		if(id == 5){
			try {
				java.awt.Desktop.getDesktop().browse(new URI(Util.MANUAL_LINK));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
		if(id == 6){
			try {
				java.awt.Desktop.getDesktop().browse(new URI(Util.SITE_LINK));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
		if(id == 1){
			if(Util.packs > 0){
				startOpening();
			}
		}
		if(id==8){
			//Util.music.stop();
			Bank.server = new MainServer();
			Bank.server.start();
			Display.currentScreen = new PanelServerconsole();
			Display.frame.setSize(800, 600);
			Display.cam.refocus();
		}
		if(id == 9){
			System.out.println("going to shop");
			Display.currentScreen = new PanelShop();
			Display.cam.refocus();
		}
	}
	@Override
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			Display.currentScreen = new PanelMainmenu();
			Display.cam.refocus();
		}
	}
	
	public void startOpening(){
		Packet22CardPack pkt = new Packet22CardPack(-1,-1,-1,-1,-1);
		pkt.write(Bank.client);
		sel = false;
		--Util.packs;
		packOpenStart = System.currentTimeMillis();
	}
	
	public void click(boolean b){
		super.click(b);
		if(b){
			if(sel && openArea.contains(this.getMousePoint()) && Util.packs > 0){
				startOpening();
			}
			if(this.cardRevealTime > -1){
				boolean hit = false;
				int w = Properties.width / 7;
				int h = (int) (w * 1.5f);
				int buffer = Properties.width / 20;
				for(int i = 0; i < 5; ++i){
					Rectangle rect1 = new Rectangle(Properties.width / 2 - ((w + buffer) * 5) / 2 + (w + buffer) * i + w / 5, Properties.height / 2 - h / 2, w, h);
					if(rect1.contains(mousePoint))hit = true;
				}
				if(!hit){
					for(int i = 0; i < 5; ++i){
						if(!revealed_clicked[i]){
							Rectangle rect1 = new Rectangle(Properties.width / 2 - ((w + buffer) * 5) / 2 + (w + buffer) * i + w / 5, Properties.height / 2 - h / 2, w, h);
							revealed_clicked[i] = true;
							AnimatedParticle part = new AnimatedParticle(rect1.x - rect1.width / 4, rect1.y - rect1.height/4, rect1.width, 1200).addFrameSet("NOVA");
							part.width = (int) (rect1.width * 1.5);
							part.height = (int) (rect1.height * 1.5);
							Display.currentScreen.particles.add(part);
						}
					}
					this.cardRevealTime = System.currentTimeMillis() - 180000 + 3000;
				}
			}
		}else{
			sel = false;
		}
	}
	
	public void onUpdate() {
		if(clicking){
		}
	}
	
	public int getPacks(){
		return Util.packs;
	}
	
	private CardShell focus = null;

	public void drawScreen(Graphics g) {
		int s = Properties.width / 10;
		for(int i = 0; i < Properties.width/s + 1; ++i){
			for(int j = 0; j < Properties.height/s + 1; ++j){
				g.drawImage(Bank.planks, i*s, j*s, s, s, null);
			}
		}
		//Bank.drawOvalOutlineOut(g, sel.x, sel.y, sel.w, sel.h, Color.GREEN, 10);	
		g.drawImage(Bank.borderScreen, 0, 0, Properties.width, Properties.height, null);
		g.drawImage(Bank.packOpener, openArea.x, openArea.y, openArea.width, openArea.height, null);

		int logow = Properties.width / 5 + Properties.width / 25;
		int logoh = logow / 4 * 3;
		g.drawImage(Bank.logo, Properties.width / 2 - logow / 2, (int) (Properties.height / 5 - logoh / 1.8), logow, logoh, null);
		//g.setFont(Util.hugeTitleFont);
		g.setColor(Color.BLACK);
		
		for(int i = 0; i < particles.size(); ++i){
			if(i < particles.size()){
				Animation unit = particles.get(i);
				if(unit!=null)
				unit.draw(g, unit.posX, unit.posY);
			}
		}
		
		//g.drawString(s1, Properties.width/2 - g.getFontMetrics().stringWidth(s1) / 2, Properties.height / 5);
		g.setFont(Util.spellNameFont);
		Rectangle rect = new Rectangle(20, Properties.height / 10, Properties.width / 10, Properties.width / 15);
		g.drawImage(Bank.packClosed, rect.x, rect.y, rect.width, rect.height, null);
		g.setColor(Color.BLACK);
		g.drawString(Util.packs+"", rect.x + rect.width - 12, rect.y + rect.height - 12);
		g.setColor(Color.WHITE);
		g.drawString(Util.packs+"", rect.x + rect.width - 10, rect.y + rect.height - 10);
		
		
		if(clicking && rect.contains(mousePoint)){
			sel = true;
		}
		
		if(packOpenStart > -1){
			long currentTime = System.currentTimeMillis() - packOpenStart;
			int w = Properties.width / 4;
			int h = w / 4 * 3;
			Rectangle rect1 = new Rectangle(Properties.width / 2 - w / 2, Properties.height / 2 - h / 2, w, h);
			if(currentTime <= 1000){
				g.drawImage(Bank.packClosed, Properties.width / 2 - w / 2, Properties.height / 2 - h / 2, w, h, null);
			}
			if(currentTime > 1000 && currentTime <= 2500){
				g.drawImage(Bank.packClosed, Properties.width / 2 - w / 2 + Util.generateRange(8), Properties.height / 2 - h / 2 + Util.generateRange(8), w, h, null);
			}
			if(currentTime > 2500 && currentTime <= 3500){
				AnimatedParticle p = new AnimatedParticle(rect1.x + rect1.width / 2, rect1.y + rect1.height / 2, (int) (10 + currentTime / 200), 350 + Util.rand.nextInt(450));
				p.addFrameSet("FIRE");
				p.phys.motionCap = -1;
				p.phys.motionY += Util.generateRange(10 + (int) (currentTime / 100) * 2);
				p.phys.motionX += Util.generateRange(10 + (int) (currentTime / 100) * 3);
				this.particles.add(p);
				g.drawImage(Bank.packClosed, Properties.width / 2 - w / 2 + Util.generateRange(12), Properties.height / 2 - h / 2 + Util.generateRange(12), w, h, null);	
			}
			if(currentTime > 3500 && currentTime <= 3900){
				AnimatedParticle p = new AnimatedParticle(rect1.x + rect1.width / 2, rect1.y + rect1.height / 2, 32, 400 + Util.rand.nextInt(500));
				p.addFrameSet("FIRE");
				p.phys.motionCap = -1;
				p.phys.motionY += rand.nextInt(60);
				p.phys.motionX += (rand.nextInt(120) - 150);
				this.particles.add(p);
				g.drawImage(Bank.packOpen1, Properties.width / 2 - w / 2 + Util.generateRange(16), Properties.height / 2 - h / 2 + Util.generateRange(16), w, h, null);	
			}
			if(currentTime > 3900 && currentTime <= 4000){
				g.drawImage(Bank.packOpen0, Properties.width / 2 - w / 2, Properties.height / 2 - h / 2, w, h, null);	
			}
			if(currentTime > 4000 && currentTime <= 10000){
				g.drawImage(Bank.packOpen0, Properties.width / 2 - w / 2, (int) (Properties.height / 2 - h / 2 + (currentTime - 4000)), w, h, null);	
			}
			if(currentTime > 10000)packOpenStart = -1;
		}
		
		if(sel){
			g.drawImage(Bank.packClosed, mousePoint.x - rect.width / 2, mousePoint.y - rect.height / 2, rect.width, rect.height, null);
			AnimatedParticle p = new AnimatedParticle(mousePoint.x, mousePoint.y, 12, 300 + Util.rand.nextInt(400));
			p.addFrameSet("FIRE");
			p.phys.motionCap = -1;
			p.phys.motionY += Util.generateRange(50) + 10;
			p.phys.motionX += Util.generateRange(80);
			this.particles.add(p);
		}
		
		if(this.cardRevealTime > -1){
			long currentTime = System.currentTimeMillis() - packOpenStart;
			if(currentTime >= 4000){
				int w = Properties.width / 7;
				int h = (int) (w * 1.5f);
				int buffer = Properties.width / 20;
				boolean hit = false;
				for(int i = 0; i < revealed.length; ++i){
					CardShell c = revealed[i];
					Rectangle rect1 = new Rectangle(Properties.width / 2 - ((w + buffer) * 5) / 2 + (w + buffer) * i + w / 5, Properties.height / 2 - h / 2, w, h);
					if(clicking && rect1.contains(mousePoint) &! revealed_clicked[i]){
						revealed_clicked[i] = true;
						AnimatedParticle part = new AnimatedParticle(rect1.x - rect1.width / 4, rect1.y - rect1.height/4, rect1.width, 1200).addFrameSet("NOVA");
						part.width = (int) (rect1.width * 1.5);
						part.height = (int) (rect1.height * 1.5);
						Display.currentScreen.particles.add(part);
					}
					if(revealed_clicked[i]){
						c.getCard().draw(c.getImg(), g, Properties.width / 2 - ((w + buffer) * 5) / 2 + (w + buffer) * i + w / 5, Properties.height / 2 - h / 2, w, h, false);
						if(rect1.contains(mousePoint)){
							hit = true;
							if(rightClicking)
							focus = c;
						}
						if(revealed_dupes[i] > 0){
							int cw = w / 3 * 2;
							if(revealed_dupes[i] == 1){
								revealed_dupes[i] = 2;
								Animation.createStaticAnimation(Properties.width / 2 - ((w + buffer) * 5) / 2 + (w + buffer) * i + w / 5 + w, Properties.height / 2  - w / 2, "fx3_fireBall", 10, 1400);
							}
							g.drawImage(Bank.coin, Properties.width / 2 - ((w + buffer) * 5) / 2 + (w + buffer) * i + w / 5 + w / 2 - cw / 2, Properties.height / 2 - cw / 2, cw, cw, null);
							g.setFont(Util.hugeTitleFont);
							String dvs = ""+Util.getDustValue(c.getCard());
							g.setColor(Color.BLACK);
							g.drawString(dvs, Properties.width / 2 - ((w + buffer) * 5) / 2 + (w + buffer) * i + w / 5 + w / 2 - g.getFontMetrics().stringWidth(dvs) / 2 - 2, Properties.height / 2 - 2);
							g.setColor(Color.WHITE);
							g.drawString(dvs, Properties.width / 2 - ((w + buffer) * 5) / 2 + (w + buffer) * i + w / 5 + w / 2 - g.getFontMetrics().stringWidth(dvs) / 2, Properties.height / 2);
						}
					}else{
						g.drawImage(Bank.cardback, rect1.x, rect1.y, rect1.width, rect1.height, null);
					}
					if(!hit&&(clicking||rightClicking))focus = null;
					if(c.getCard().isUnit()){
						UnitTemplate unit = ((UnitTemplate)c.getCard());
						if(unit.getRank() == UnitTemplate.RANK_MYTHIC){
							AnimatedParticle p = new AnimatedParticle(Properties.width / 2 - ((w + buffer) * 5) / 2 + (w + buffer) * i + w / 2 + w / 5, Properties.height / 2, 24, 340 + Util.rand.nextInt(480));
							p.addFrameSet("SWIRL");
							p.phys.motionCap = -1;
							p.phys.motionY += Util.generateRange(100);
							p.phys.motionX += Util.generateRange(90);
							this.particles.add(p);
						}
						if(unit.getRank() == UnitTemplate.RANK_UNCOMMON){
							AnimatedParticle p = new AnimatedParticle(Properties.width / 2 - ((w + buffer) * 5) / 2 + (w + buffer) * i + w / 2 + w / 5, Properties.height / 2, 24, 340 + Util.rand.nextInt(480));
							p.addFrameSet("SHATTER");
							p.phys.motionCap = -1;
							p.phys.motionY += Util.generateRange(85);
							p.phys.motionX += Util.generateRange(75);
							this.particles.add(p);
						}
						if(unit.getRank() == UnitTemplate.RANK_REGAL){
							for(int j = 0; j < 2; ++j){
								AnimatedParticle p = new AnimatedParticle(Properties.width / 2 - ((w + buffer) * 5) / 2 + (w + buffer) * i + w / 2 + w / 5, Properties.height / 2, 24, 340 + Util.rand.nextInt(480));
								p.addFrameSet("FIRE");
								p.phys.motionCap = -1;
								p.phys.motionY += Util.generateRange(180);
								p.phys.motionX += Util.generateRange(120);
								this.particles.add(p);
							}
						}
					}else{
						if(c.getCard().isEquipment() || c.getCard().isBoardState()){
							AnimatedParticle p = new AnimatedParticle(Properties.width / 2 - ((w + buffer) * 5) / 2 + (w + buffer) * i + w / 2 + w / 5, Properties.height / 2, 24, 340 + Util.rand.nextInt(480));
							p.addFrameSet("SHATTER");
							p.phys.motionCap = -1;
							p.phys.motionY += Util.generateRange(85);
							p.phys.motionX += Util.generateRange(75);
							this.particles.add(p);
						}
					}
				}
			}
			if(focus != null){
				g.setColor(Util.transparent_dark);
				g.fillRect(0, 0, Properties.width, Properties.height);
				CardShell c = focus;
				int fw = Properties.width / 4;
				int fh = (int) (fw * 1.5);
				c.getCard().draw(c.getImg(), g, Properties.width / 2 - fw / 2, Properties.height / 2 - fh / 2 - 20, fw, fh, true);
			}
			if(currentTime >= 180000){
				//cardRevealTime = -1;
			}
		}
		if(cardRevealTime > -1){
			boolean allShown = true;
			for(int i = 0; i < 5; ++i){
				if(revealed_clicked[i] == false)allShown = false;
			}
			int w = Properties.width / 3;
			if(allShown){
				g.setFont(Util.cooldownBold);
				Rectangle rect1 = new Rectangle(Properties.width / 2 - w / 2, Properties.height / 4 * 3, w, w / 3);
				g.setColor(rect1.contains(mousePoint) ? Color.GREEN : Color.WHITE);
				g.drawString("Click To Continue", rect1.x + rect1.width / 2 - g.getFontMetrics().stringWidth("Click To Continue") / 2, rect1.y + rect1.height / 2);
				if(rect1.contains(mousePoint) && clicking)this.cardRevealTime = -1;
			}
		}
		int setIcoW = (int) (60 * 1.3d);
		int setIcoH = (int) (40 * 1.3d);
		for(int i = 0; i < sets.length; ++i){
			g.setColor(Color.WHITE);
			Rectangle setrect = new Rectangle(5 + (setIcoW + 5) * i, Properties.height - 50 - setIcoH, setIcoW, setIcoH);
			CardSet set = sets[i];
			g.fillRect(setrect.x - 5, setrect.y - 10, setrect.width + 10, setrect.width);
			if(clicking && setrect.contains(mousePoint))this.set = i;
			g.drawImage(set.getIcon(), setrect.x, setrect.y, setrect.width, setrect.height, null);
			g.setColor(Color.BLACK);
			if(i == this.set)g.drawOval(setrect.x + setrect.width / 2 - setrect.height / 2 - 5, setrect.y - 5, setrect.height + 10, setrect.height + 10);
		}
	}

	public void revealCards(int c1, int c2, int c3, int c4, int c5) {
		for(int i = 0; i < revealed_clicked.length; ++i){
			revealed_clicked[i] = false;
			revealed_dupes[i] = 0;
		}
		if(Util.collection[c1] > 1)revealed_dupes[0] = 1;
		if(Util.collection[c2] > 1)revealed_dupes[1] = 1;
		if(Util.collection[c3] > 1)revealed_dupes[2] = 1;
		if(Util.collection[c4] > 1)revealed_dupes[3] = 1;
		if(Util.collection[c5] > 1)revealed_dupes[4] = 1;
		revealed[0] = new CardShell(Card.all[c1]);
		revealed[1] = new CardShell(Card.all[c2]);
		revealed[2] = new CardShell(Card.all[c3]);
		revealed[3] = new CardShell(Card.all[c4]);
		revealed[4] = new CardShell(Card.all[c5]);
		Util.collection = Util.flushCollection(Util.collection);
		this.cardRevealTime = System.currentTimeMillis();
		System.out.println("CLIENT got cards from server. ["+Card.all[c1].getName()+"], ["+Card.all[c2].getName()+"], ["+Card.all[c3].getName()+"], ["+Card.all[c4].getName()+"], ["+Card.all[c5].getName()+"], ");
	}
}
