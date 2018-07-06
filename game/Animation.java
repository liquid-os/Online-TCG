package org.author.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Animation {
	private int maxTime = 1000;
	private long start = System.currentTimeMillis();
	int width = 1, height = 1, tag = 0;
	int posX, posY;
	private Random rand = new Random();
	private boolean remove = false;
	static final int TAG_PARTICLE = 0, TAG_CANNONBALL = 1, TAG_UNITMOVE = 2, TAG_TEXT = 3, TAG_CARD = 4, TAG_SPEECH = 5, TAG_KHOLOS = 6, TAG_VOLLEY = 7, TAG_DRAW = 8, TAG_ABILITY = 9, TAG_MYSTERYCARD = 10;
	private int data = 0, data1 = -1;
	private String txt = null;
	private Point origin = null;
	private CardShell cs = null;
	private Color c = null;
	
	public Animation(int x, int y, int max, int tag){
		this.posX = x;
		this.posY = y;
		this.maxTime = max;
		this.tag = tag;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}
	
	public Animation setOrigin(int x, int y){
		this.origin = new Point(x,y);
		return this;
	}
	
	public static void createStaticAnimation(Unit u, String set, float scale, int time){
		int w = (int) (Grid.tileSize * scale);
		AnimatedParticle part = new AnimatedParticle(Util.boardOffsetX + Grid.tileSize * u.posX + Grid.tileSize/2 - w/2, Util.boardOffsetY + Grid.tileSize * u.posY + Grid.tileSize/2 - w/2, w, time).addFrameSet(set);
		Display.currentScreen.particles.add(part);
	}
	
	public static void createStaticAnimation(int x, int y, String set, float scale, int time){
		int w = (int) (Grid.tileSize * scale);
		AnimatedParticle part = new AnimatedParticle(x - w/2, y - w/2, w, time).addFrameSet(set);
		Display.currentScreen.particles.add(part);
	}
	
	public void draw(Graphics g, int cx, int cy) {
		if(tag == TAG_CANNONBALL){
			int x = Util.pct((int) (System.currentTimeMillis() - start), posX, maxTime);
			int y = Util.pct((int) (System.currentTimeMillis() - start), posY, maxTime);
			g.drawImage(Bank.ball, x, y, Grid.tileSize, Grid.tileSize, null);
			AnimatedParticle p = new AnimatedParticle(x, y, 16+rand.nextInt(17), 400+rand.nextInt(200)).addFrameSet("FIRE");
			Display.currentScreen.particles.add(p);
		}
		if(tag == TAG_VOLLEY){
			int x = Util.pct((int) (System.currentTimeMillis() - start), posX, maxTime);
			int y = Util.pct((int) (System.currentTimeMillis() - start), posY, maxTime);
			g.drawImage(Bank.volleyarrow, x, y, Grid.tileSize, Grid.tileSize, null);
			AnimatedParticle p = new AnimatedParticle(x, y, 10+rand.nextInt(8), 400+rand.nextInt(200)).addFrameSet("FIRE");
			Display.currentScreen.particles.add(p);
		}
		if(tag == TAG_KHOLOS){
			int x = Util.pct((int) (System.currentTimeMillis() - start), posX, maxTime);
			int y = Util.pct((int) (System.currentTimeMillis() - start), posY, maxTime);
			//g.drawImage(Bank.particleSkull_3, x, y, Grid.tileSize, Grid.tileSize, null);
			AnimatedParticle p = new AnimatedParticle(x, y, Grid.tileSize*2, 700).addFrameSet("VOIDCAST");
			p.setShrinks(true);
			Display.currentScreen.particles.add(p);
		}
		if(tag == TAG_UNITMOVE){
			if(getCs()==null)
			setCs(new CardShell(Card.all[Display.currentScreen.getUnitFromGUID(getData(), -1).getTemplate().getId()]));
			int x = origin.x - Util.pct((int) (System.currentTimeMillis() - start), (origin.x - posX), maxTime);
			int y = origin.y - Util.pct((int) (System.currentTimeMillis() - start), (origin.y - posY), maxTime);
			g.drawImage(getCs().getImg(), x, y, Grid.tileSize, Grid.tileSize, null);
		}
		if(tag == TAG_CARD){
			int w = this.width / 3 * 2 + Util.pct((int) (System.currentTimeMillis() - start), (this.width/3), maxTime);
			int h = (int) (w * 1.5);
			if(getCs()==null)
			setCs(new CardShell(Card.all[data]));
			Card.all[data].draw(getCs().getImg(), g, posX - w / 2, posY - h / 2 - w / 6, w, h, false);
		}
		if(tag == TAG_MYSTERYCARD){
			int w = this.width / 3 * 2 + Util.pct((int) (System.currentTimeMillis() - start), (this.width/3), maxTime);
			int h = (int) (w * 1.5);
			int x = posX, y = posY;
			int borderCount = 5;
			int borderCountH = (int) (5 * 1.5);
			int borderW = w / borderCount, borderH = borderW / 3 * 2;
			int borderWY = h / borderCountH, borderHY = borderWY / 3 * 2;
			g.drawImage(Bank.planks, x, y+h/5+borderH/2, w, w, null);
			g.setColor(Color.GRAY);
			g.fillRect(x, y+h/3*2, w, h/3);
			g.fillRect(x, y, w, h/5);
			for(int i = 0; i < borderCount; ++i){
				g.drawImage(Bank.cardBorderTop, x + i * borderW, y, borderW, borderH, null);
				g.drawImage(Bank.cardBorderBottom, x + i * borderW, y+h-borderH, borderW, borderH, null);
				
				g.drawImage(Bank.cardBorderTop, x + i * borderW, y+h/3*2, borderW, borderH / 3 * 2, null);
				g.drawImage(Bank.cardBorderBottom, x + i * borderW, y+h/5, borderW, borderH / 3 * 2, null);
			}
			for(int i = 0; i < borderCountH; ++i){
				g.drawImage(Bank.cardBorderLeft, x, y+i*borderWY, borderHY / 3 * 2, borderWY, null);
				g.drawImage(Bank.cardBorderRight, x+w-(borderHY / 3 * 2), y+i*borderWY, borderHY / 3 * 2, borderWY, null);
			}
			g.drawImage(Bank.cornerTopleft, x, y, borderW, borderW, null);
			g.drawImage(Bank.cornerTopright, x+w-borderW, y, borderW, borderW, null);
			g.drawImage(Bank.cornerBottomleft, x, y+h-borderW, borderW, borderW, null);
			g.drawImage(Bank.cornerBottomright, x+w-borderW, y+h-borderW, borderW, borderW, null);
			int bannerW = w - w / 4;
			int bannerH = (int) (bannerW * 0.25);
			Image bannerImg = null;
			if(c == null){
				int temp = data;
				this.data = data1;
				this.data1 = temp;
				c = Color.WHITE;
			}
			bannerImg = (data == 0 ? Bank.bannerCommon : data == 1 ? Bank.bannerUncommon
					: data == 2 ? Bank.bannerMythic : data == 3 ? Bank.bannerRegal
							: data ==  4 ? Bank.bannerSpell : data == 5 ? Bank.bannerEquipment
									: data == 6 ? Bank.bannerStructure : data == 7 ? Bank.bannerBoardstate : null);
			int cost = data1;
			g.drawImage(bannerImg, x + w / 2 - bannerW / 2, y + (h / 3 * 2) - bannerH / 4, bannerW, bannerH, null);
			
			g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, w/10));
			int cw = w/3;
			g.drawImage(Bank.starFull, x+w/2-cw/2, y-cw/4*3, cw, cw, null);
			
			txt = "Unknown Unit";
			if(data == 4)txt = "Unknown Spell";
			if(data == 5)txt = "Unknown Equipment";
			if(data == 6)txt = "Unknown Structure";
			if(data == 7)txt = "Unknown Board Status";
			
			g.setColor(Color.BLACK);
			g.drawString(txt, x+w/2-g.getFontMetrics().stringWidth(txt)/2-1, y+h/7+4-1);
			g.setColor(Color.WHITE);
			g.drawString(txt, x+w/2-g.getFontMetrics().stringWidth(txt)/2, y+h/7+4);

			g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, w/5));
			g.setColor(Color.BLACK);
			g.drawString(cost+"", x+w/2-g.getFontMetrics().stringWidth(cost+"")/2-2, y-2);
			g.setColor(Color.WHITE);
			g.drawString(cost+"", x+w/2-g.getFontMetrics().stringWidth(cost+"")/2, y);
		}
		if(tag == TAG_DRAW){
			int showtime = 1000;
			int x = posX, y = posY - Util.pct((int)(System.currentTimeMillis() - start), Properties.height / 15, showtime);
			if(this.getElapsed() > showtime){
				x = Properties.width - (Util.pct((int) (System.currentTimeMillis() - start), (posX - PanelBoard.hand.size() * PanelBoard.dcw), maxTime - showtime));
				y = posY - Properties.height / 20 + Util.pct((int)(System.currentTimeMillis() - start), Properties.height / 15, maxTime - showtime);
			}
			if(getCs()==null)
			setCs(new CardShell(Card.all[data]));
			Card.all[data].draw(getCs().getImg(), g, x-width/2, y, width, height, false);
		}
		if(tag == TAG_ABILITY){
			CardAbility ca = CardAbility.all[data];
			Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, Grid.tileSize / 4);
			int w = g.getFontMetrics().stringWidth(ca.getName()) + Grid.tileSize / 2 + 10;
			int h = Grid.tileSize / 2;
			int elr = Util.pct((int)(System.currentTimeMillis() - start), Properties.height / 20, maxTime);
			//if(getCs() != null){
				//getCs().getCard().draw(getCs().getImg(), g, posX, posY - ch - 20, w, ch, false);
			//}
			int rdy = posY - elr - Grid.tileSize / 4;
			int posX =  this.posX - w / 2;
			g.drawImage(Bank.button, posX, rdy, w, h, null);
			g.drawImage(ca.getImg(), posX, rdy, h, h, null);
			Bank.drawOutline(g, posX, rdy, w, h, Color.BLACK, 3);
			Bank.drawOutline(g, posX, rdy, h, h, Color.BLACK, 3);
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.drawString(ca.getName(), posX+h+5, rdy + h / 3 * 2);
			if(c != null){
				int x = origin.x;
				int y = origin.y;
				g.setColor(getC());
				g.drawLine(posX+w/2, posY+h, x+Grid.tileSize/2, y+Grid.tileSize/2);
				g.drawImage(Bank.target, x, y, Grid.tileSize, Grid.tileSize, null);
			}
			//g.drawImage(ca.getImg(), posX + (Util.pct((int)(System.currentTimeMillis() - start), (x - posX), maxTime)) + Grid.tileSize / 4, posY  + (Util.pct((int)(System.currentTimeMillis() - start), (y - posY), maxTime)) + Grid.tileSize / 4, Grid.tileSize/2, Grid.tileSize/2, null);
		}
		if(tag == TAG_TEXT){
			g.setFont(Util.cooldownFont);
			Color c2 = Color.WHITE;
			if(getTxt().equalsIgnoreCase("chilled"))c2 = Color.cyan;
			if(Integer.parseInt(getTxt()) < 0)c2 = Color.RED;
			if(Integer.parseInt(getTxt()) > 0)c2 = Color.GREEN;
			int x = posX;
			int y = posY - Util.pct((int) (System.currentTimeMillis() - start), (posY - Properties.height/5), maxTime);
			int alpha = 300 - (Util.pct((int) (System.currentTimeMillis() - start), (255), maxTime));
			int a = alpha;
			if(a > 255)a=255;
			if(a < 0)a=0;
			g.setColor(new Color(0, 0, 0, a));
			g.drawString(getTxt(), x - g.getFontMetrics().stringWidth(getTxt())/2 -1, y-1);
			g.setColor(new Color (c2.getRed(), c2.getGreen(), c2.getBlue(), a));
			g.drawString(getTxt(), x - g.getFontMetrics().stringWidth(getTxt())/2, y);
		}
		if(tag == TAG_SPEECH && txt!=null){
			int posY = this.posY - Grid.tileSize/2;
			String[] lines = (txt.contains("&") ? txt.split("&") : new String[]{txt});
			int w = 10, h = 8+lines.length*16;
			g.setFont(Util.descTitleFont);
			for(String s : lines){
				if(g.getFontMetrics().stringWidth(s) > w)w = g.getFontMetrics().stringWidth(s) + 24;
			}
			int top = 16;
			g.drawImage(Bank.speechTop, posX, posY-h-top, w, top, null);
			g.drawImage(Bank.speech, posX, posY-h, w, h, null);
			g.drawImage(Bank.speechBot, posX, posY, w, top, null);
			for(int i = 0; i < lines.length; ++i){
				g.setColor(Color.BLACK);
				g.drawString(lines[i], posX+w/2-g.getFontMetrics().stringWidth(lines[i])/2, top/2+posY-h+i*26);
			}
		}
	}
	
	public long getElapsed(){
		return (int)(System.currentTimeMillis()-start);
	}
	
	public void updateBase(){
		if((System.currentTimeMillis() - start) > getMaxTime())this.kill();
	}
	
	public void kill(){this.setRemove(true);};

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public Random getRand() {
		return rand;
	}

	public void setRand(Random rand) {
		this.rand = rand;
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	public Animation setData(int i) {
		this.data = i;
		return this;
	}

	public String getTxt() {
		return txt;
	}

	public Animation setText(String txt) {
		this.txt = txt;
		if(tag == TAG_SPEECH && txt!=null){
			maxTime = 1200 + txt.length()*75;
		}
		return this;
	}

	public int getData() {
		return data;
	}

	public CardShell getCs() {
		return cs;
	}

	public void setCs(CardShell cs) {
		this.cs = cs;
	}

	public int getData1() {
		return data1;
	}

	public void setData1(int data1) {
		this.data1 = data1;
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}
}
