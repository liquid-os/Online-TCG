package org.author.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

public class PanelDeckbuilder extends PanelBase{
	
	private int page = 0, mode = 0;
	private ArrayList<Card> available = new ArrayList<Card>();
	private Card hero = null;
	private CardShell[] cards = new CardShell[40];
	private GUIButton nextPage = new GUIButton(Bank.arrowRight, Bank.arrowRight, 1, Properties.width-80, Properties.height/2-32, 64, 64);
	private GUIButton prevPage = new GUIButton(Bank.arrowLeft, Bank.arrowLeft, 2, Properties.width/10, Properties.height/2-32, 64, 64);
	private GUIButton confirm = new GUIButton(Bank.button, Bank.buttonHover, 3, Properties.width/2-80, Properties.height-90, 160, 45).setText("Confirm");
	private GUIButton exit = new GUIButton(Bank.button, Bank.buttonHover, 4, Properties.width/10+20, Properties.height-90, 160, 45).setText("Exit");
	private int cardsPerPage = 10;
	private CardShell inspection = null;
	private long lastCardAdd = System.currentTimeMillis();
	private File basefile = null;
	private ArrayList<CardShell> pagelist = new ArrayList<CardShell>();
	private GUITextBox search;
	private boolean toggleLocked = true;
	
	public PanelDeckbuilder(Card hero){
		if(hero==null)
			guis.add(new GUISelectHero());
		else {
			this.hero = hero;
			this.addCardList();
			guis.add(nextPage);
			if(hero == Card.filler){
				prevPage.x = 0;
				exit.x = 0;
			}
			guis.add(prevPage);
			guis.add(confirm);
			guis.add(exit);
			search = new GUITextBox(Bank.searchbar, Bank.searchbar, Properties.width / 10, Properties.height - 90, Properties.width / 3, 50);
			guis.add(search);
			initPage(available);
		}
	}
	
	public void keyPressed(KeyEvent e){
		search.keyPressed(e);
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			ArrayList<String> conditions = new ArrayList<String>();
			if(search.text.contains("=")){
				if(search.text.contains(",")){
					String[] split = search.text.split(",");
					for(String s : split){
						if(s.contains("=")){
							conditions.add(s);
							search.text.replace(s, "");
						}
					}
					search.text.replace(",", "");
				}else{
					search.text.replace(" ", "");
					conditions.add(search.text);
				}
			}
			ArrayList<Card> found = new ArrayList<Card>();
			for(int i = 0; i < Card.all.length; ++i){
				if(Card.all[i] != null){
					Card card = Card.all[i];
					if(canShowCard(card) &! found.contains(card)){
						if(card.getName().equalsIgnoreCase(search.text))found.add(card);
					}
				}
			}
			for(int i = 0; i < Card.all.length; ++i){
				if(Card.all[i] != null){
					Card card = Card.all[i];
					if(canShowCard(card) &! found.contains(card)){
						if(card.isUnit()){
							if(((UnitTemplate)card).getFamily().getName().equalsIgnoreCase(search.text))
							found.add(card);
						}
					}
				}
			}
			if(search.text.equalsIgnoreCase("board status")){
				for(int i = 0; i < Card.all.length; ++i){
					if(Card.all[i] != null){
						Card card = Card.all[i];
						if(canShowCard(card) &! found.contains(card)){
							if(card.isBoardState())found.add(card);
						}
					}
				}
			}
			if(search.text.equalsIgnoreCase("equipment")){
				for(int i = 0; i < Card.all.length; ++i){
					if(Card.all[i] != null){
						Card card = Card.all[i];
						if(canShowCard(card) &! found.contains(card)){
							if(card.isEquipment())found.add(card);
						}
					}
				}
			}
			if(search.text.equalsIgnoreCase("spell")){
				for(int i = 0; i < Card.all.length; ++i){
					if(Card.all[i] != null){
						Card card = Card.all[i];
						if(canShowCard(card) &! found.contains(card)){
							if(card.isSpell())found.add(card);
						}
					}
				}
			}
			if(search.text.equalsIgnoreCase("unit")){
				for(int i = 0; i < Card.all.length; ++i){
					if(Card.all[i] != null){
						Card card = Card.all[i];
						if(canShowCard(card) &! found.contains(card)){
							if(card.isUnit())found.add(card);
						}
					}
				}
			}
			if(search.text.equalsIgnoreCase("commander")){
				for(int i = 0; i < Card.all.length; ++i){
					if(Card.all[i] != null){
						Card card = Card.all[i];
						if(canShowCard(card) &! found.contains(card)){
							if(card.isUnit()){
								if(((UnitTemplate)card).getRank() == UnitTemplate.RANK_COMMANDER)
								found.add(card);
							}
						}
					}
				}
			}
			if(search.text.equalsIgnoreCase("common")){
				for(int i = 0; i < Card.all.length; ++i){
					if(Card.all[i] != null){
						Card card = Card.all[i];
						if(canShowCard(card) &! found.contains(card)){
							if(card.isUnit()){
								if(((UnitTemplate)card).getRank() == UnitTemplate.RANK_COMMON)
								found.add(card);
							}
						}
					}
				}
			}
			if(search.text.equalsIgnoreCase("uncommon")){
				for(int i = 0; i < Card.all.length; ++i){
					if(Card.all[i] != null){
						Card card = Card.all[i];
						if(canShowCard(card) &! found.contains(card)){
							if(card.isUnit()){
								if(((UnitTemplate)card).getRank() == UnitTemplate.RANK_UNCOMMON)
								found.add(card);
							}
						}
					}
				}
			}
			if(search.text.equalsIgnoreCase("regal")){
				for(int i = 0; i < Card.all.length; ++i){
					if(Card.all[i] != null){
						Card card = Card.all[i];
						if(canShowCard(card) &! found.contains(card)){
							if(card.isUnit()){
								if(((UnitTemplate)card).getRank() == UnitTemplate.RANK_REGAL)
								found.add(card);
							}
						}
					}
				}
			}
			if(search.text.equalsIgnoreCase("mythic")){
				for(int i = 0; i < Card.all.length; ++i){
					if(Card.all[i] != null){
						Card card = Card.all[i];
						if(canShowCard(card) &! found.contains(card)){
							if(card.isUnit()){
								if(((UnitTemplate)card).getRank() == UnitTemplate.RANK_MYTHIC)
								found.add(card);
							}
						}
					}
				}
			}
			for(int i = 0; i < Card.all.length; ++i){
				if(Card.all[i] != null){
					Card card = Card.all[i];
					if(canShowCard(card) &! found.contains(card)){
						if(card.getName().toLowerCase().contains(search.text.toLowerCase()))found.add(card);
					}
				}
			}
			for(int i = 0; i < Card.all.length; ++i){
				if(Card.all[i] != null){
					Card card = Card.all[i];
					if(canShowCard(card) &! found.contains(card)){
						ArrayList<CardAbility> abs = new ArrayList<CardAbility>();
						if(card instanceof UnitTemplate){
							abs = ((UnitTemplate)card).getAbilities();
						}
						if(card instanceof CardEquipment){
							abs = ((CardEquipment)card).getAbilities();
						}
						for(CardAbility ca : abs){
							if(!found.contains(card) && ca.getName().toLowerCase().contains(search.text.toLowerCase()))found.add(card);
						}
					}
				}
			}
			for(int i = 0; i < Card.all.length; ++i){
				if(Card.all[i] != null){
					Card card = Card.all[i];
					if(canShowCard(card) &! found.contains(card)){
						if(card.getText() != null){
							if(card.getText().toLowerCase().contains(search.text.toLowerCase()))found.add(card);
						}
					}
				}
			}
			for(int i = 0; i < Card.all.length; ++i){
				if(Card.all[i] != null){
					Card card = Card.all[i];
					if(canShowCard(card) &! found.contains(card)){
						ArrayList<CardAbility> abs = new ArrayList<CardAbility>();
						if(card instanceof UnitTemplate){
							abs = ((UnitTemplate)card).getAbilities();
						}
						if(card instanceof CardEquipment){
							abs = ((CardEquipment)card).getAbilities();
						}
						for(CardAbility ca : abs){
							if(!found.contains(card) && ca.getDesc().toLowerCase().contains(search.text.toLowerCase()))found.add(card);
						}
					}
				}
			}
			for(int i = 0; i < Card.all.length; ++i){
				if(Card.all[i] != null){
					Card card = Card.all[i];
					if(canShowCard(card) &! found.contains(card)){
						if(CardList.getListForCard(card).getName().equalsIgnoreCase(search.text) || CardList.getListForCard(card).getName().replace("The ","").equalsIgnoreCase(search.text))
						found.add(card);
					}
				}
			}
			if(conditions.size() > 0){
				for(String s : conditions){
					String type = s.split("=")[0];
					String value = s.split("=")[1];
					if(type.equalsIgnoreCase("cost")){
						int intval = Integer.parseInt(value);
						ArrayList<Card> newlist = (ArrayList<Card>) found.clone();
						for(Card c : found){
							if(c.getCost() != intval)newlist.remove(c);
						}
						found = (ArrayList<Card>) newlist.clone();
					}
					if(type.equalsIgnoreCase("attack") || type.equalsIgnoreCase("atk")){
						int intval = Integer.parseInt(value);
						ArrayList<Card> newlist = (ArrayList<Card>) found.clone();
						for(Card c : found){
							if(c instanceof UnitTemplate){
								if(((UnitTemplate)c).getAttack() != intval)newlist.remove(c);
							}
						}
						found = newlist;
					}
					if(type.equalsIgnoreCase("health") || type.equalsIgnoreCase("hp")){
						int intval = Integer.parseInt(value);
						
						ArrayList<Card> newlist = (ArrayList<Card>) found.clone();
						for(Card c : found){
							if(c instanceof UnitTemplate){
								if(((UnitTemplate)c).getHealth() != intval)newlist.remove(c);
							}
						}
						found = newlist;
					}
					if(type.equalsIgnoreCase("speed") || type.equalsIgnoreCase("spd")){
						int intval = Integer.parseInt(value);
						ArrayList<Card> newlist = (ArrayList<Card>) found.clone();
						for(Card c : found){
							if(c instanceof UnitTemplate){
								if(((UnitTemplate)c).getSpeed() != intval)newlist.remove(c);
							}
						}
						found = newlist;
					}
					if(type.equalsIgnoreCase("tribe")){
						ArrayList<Card> newlist = (ArrayList<Card>) found.clone();
						for(Card c : found){
							if(c instanceof UnitTemplate){
								if(!((UnitTemplate)c).getFamily().getName().equalsIgnoreCase(value))newlist.remove(c);
							}
						}
						found = newlist;
					}
					if(type.equalsIgnoreCase("spell") && value.equalsIgnoreCase("true")){
						ArrayList<Card> newlist = (ArrayList<Card>) found.clone();
						for(Card c : found){
							if(c instanceof UnitTemplate){
								if(!((UnitTemplate)c).isSpell())newlist.remove(c);
							}
						}
						found = newlist;
					}
					if(type.equalsIgnoreCase("unit") && value.equalsIgnoreCase("true")){
						ArrayList<Card> newlist = (ArrayList<Card>) found.clone();
						for(Card c : found){
							if(c instanceof UnitTemplate){
								if(!((UnitTemplate)c).isUnit())newlist.remove(c);
							}
						}
						found = newlist;
					}
					if(type.equalsIgnoreCase("equipment") && value.equalsIgnoreCase("true")){
						ArrayList<Card> newlist = (ArrayList<Card>) found.clone();
						for(Card c : found){
							if(c instanceof UnitTemplate){
								if(!((UnitTemplate)c).isEquipment())newlist.remove(c);
							}
						}
						found = newlist;
					}
					if(type.equalsIgnoreCase("rarity")){
						int intval = Integer.parseInt(value);
						ArrayList<Card> newlist = (ArrayList<Card>) found.clone();
						for(Card c : found){
							if(c instanceof UnitTemplate){
								if(((UnitTemplate)c).getRank() != intval)newlist.remove(c);
							}
						}
						found = newlist;
					}
				}
			}
			if(search.text.equalsIgnoreCase("") || search.text.equalsIgnoreCase("null")){
				page = 0;
				this.addCardList();
				initPage(found);
			}else{
				page = 0;
				available = found;
				initPage(found);
			}
		}
	}
	
	public boolean canShowCard(Card c){
		boolean ret = false;
		if(this.hero == Card.filler){
			return true;
		}else{
			ret = (CardList.getListForCard(hero) == CardList.getListForCard(c) || CardList.getListForCard(c) == CardList.common) && c.isListable();
		}
		return ret;
	}
	
	public void initPage(ArrayList<Card> avail){
		pagelist = new ArrayList<CardShell>();
		if(avail.size() > 0)
		for(int i = 0; i < cardsPerPage; ++i){
			if(avail.get(page + i) != null){
				pagelist.add(new CardShell(avail.get(page + i)));
			}
		}
	}
	
	class CostSorter implements Comparator<Card> {
		public int compare(Card c1, Card c2) {
			return Integer.compare(c1.getCost(), c2.getCost());
		}
	}
	
	void addCard(Card c){
		if(this.countCard(c) < c.getDeckLimit() && this.getCurrentCardCount() < Util.deckSize){
			cards[getCurrentCardCount()] = new CardShell(c);
			AnimatedParticle part = new AnimatedParticle(Properties.width/20 - (Properties.width/10)/2, this.getCurrentCardCount()*(Properties.height/45 + 1) + 10 - (Properties.height/15)/4*3, 100, 1200).addFrameSet("manaburn");
			part.width = Properties.width/10;
			part.height = Properties.height/15;
			Display.currentScreen.particles.add(part);
		}else{
			Util.persistentGuis.add(new GUINotify(Bank.flamehelm, "Failed to add card!", "You can't have any more of this card in your deck.", 0, 0));
		}
	}
	
	public int getCurrentCardCount(){
		int ret = 0;
		for(int i = 0; i < cards.length; ++i){
			if(cards[i]!=null)++ret;
		}
		return ret;
	}
	
	public void saveDeck(String n){
		String name = n;
		File file = (this.basefile == null ? new File(Bank.path+"decks/"+name+".DECK") : this.basefile);
		if(basefile!=null){
			name = basefile.getName().replace(".DECK", "");
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String cont = "";
		for(int i = 0; i < cards.length; ++i){
			cont += cards[i].getCard().getId()+"%";
		}
		cont+="&"+hero.getId()+"&"+name;
		Bank.setContentsRawdir(file.getPath(), cont);
		/*File file = new File("C:\\"+name+".DECK");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String cont = "";
		for(int i = 0; i < cards.length; ++i){
			cont += cards[i].getId()+"%";
		}
		cont+="&"+hero.getId()+"&"+name;
		Bank.setContentsRawdir("C:\\"+name+".DECK", cont);
		JOptionPane.showMessageDialog(null, "Your deck will be in your C:/ drive.");*/
	}
	
	public void addCardList(){
		for(int i = 0; i < Card.all.length; ++i){
			Card c = Card.all[i];
			if(c!=null){
				if((c.isListable() && c.availableFor(hero) && (Util.collection[c.getId()] > 0 || toggleLocked)) || hero == Card.filler){
					available.add(c);
				}
			}
		}
		Collections.sort(available, new CostSorter());
	}
	
	public void buttonReact(int i){
		if(i == 1){
			page+=cardsPerPage;
			//AnimatedParticle part = new AnimatedParticle(mousePoint.x-50, mousePoint.y-50, 100, 1000).addFrameSet("HEAL");
			//particles.add(part);
			initPage(available);
		}
		if(i == 2){
			if(page-cardsPerPage>=0)page-=cardsPerPage;
			//AnimatedParticle part = new AnimatedParticle(mousePoint.x-50, mousePoint.y-50, 100, 1000).addFrameSet("HEAL");
			//particles.add(part);
			initPage(available);
		}
		if(i == 3){
			if(this.getCurrentCardCount() >= Util.deckSize){
				saveDeck((basefile == null ? JOptionPane.showInputDialog("Name your deck...") : null));
				Display.currentScreen = new PanelMainmenu();
				Display.cam.refocus();
			}else{
				Util.persistentGuis.add(new GUINotify(Bank.flamehelm, "Error!", "Your deck is not complete.", 0, 0));
			}
		}
		if(i == 4){
			Display.currentScreen = new PanelMainmenu();
			Display.cam.refocus();
		}
	}
	
	public int countCard(Card c){
		int count = 0;
		for(CardShell card : cards){
			if(card!=null)
			if(card.getCard() == c)++count;
		}
		return count;
	}

	public void onUpdate() {
	}
	

	public void drawScreen(Graphics g) {
		int cih = Properties.height / 5 * 3;
		int ciw = (int) (cih * 0.75);
		int cbw = Properties.width / 10 * 3, spacer = 25;
		int cbh = cbw / 6;
		Rectangle craftButton = new Rectangle(Properties.width/2-ciw/2 - cbw - spacer, Properties.height / 2 - cbh / 2, cbw, cbh);
		Rectangle decraftButton = new Rectangle(Properties.width/2-ciw/2 + ciw + spacer, Properties.height / 2 - cbh / 2, cbw, cbh);

		if((clicking || rightClicking) &! (craftButton.contains(mousePoint) || decraftButton.contains(mousePoint))){
			this.inspection = null;
		}
		int s = Properties.width / 10;
		for(int i = 0; i < Properties.width/s + 1; ++i){
			for(int j = 0; j < Properties.height/s + 1; ++j){
				g.drawImage(Bank.planks, i*s, j*s, s, s, null);
			}
		}
		g.drawImage(Bank.borderScreen, 0, 0, Properties.width, Properties.height, null);
		/*int count = 10;
		int w = Properties.width/count;
		for(int i = 0; i < count; ++i){
			for(int j = 0; j < count; ++j){
				g.drawImage(Bank.texture_diamond, i*w, j*w, w, w, null);
			}
		}*/
		for(int i = 0; i < cardsPerPage; ++i){
			int index = i;
			int ch = Properties.height / 4 * 3 / 2;
			int cw = Properties.width / 8;
			int spacing = 64, ySpacing = 60;
			if(index < pagelist.size()){
				CardShell cs = pagelist.get(index);
				Card c = pagelist.get(index).getCard();
				if(c!=null){
					Rectangle rect = new Rectangle(cw / 2 + Properties.width / 10 + spacing/2 + (i >= 5 ? (i - 5) : i) * (cw + spacing) - (mode == 0 ? 0 : Properties.width/20), spacing + (i / 5 * (ch + ySpacing)), cw, ch);
					if(inspection == null || inspection.getCard() == c)
					c.draw(cs.getImg(), g, rect.x, rect.y, rect.width, rect.height, false);
					else g.drawImage(Bank.cardback, rect.x, rect.y, rect.width, rect.height, null);
					if(rightClicking && rect.contains(getMousePoint())){
						this.inspection = new CardShell(pagelist.get(index).getCard());
						if(inspection.getImg() == null)
						this.inspection.setImg(Filestream.getImageFromPath(pagelist.get(index).getCard().getArt()));
					}
					if(inspection == null && clicking && rect.contains(mousePoint) && (System.currentTimeMillis() - lastCardAdd >= 200) && this.mode == 0){
						this.addCard(c);
						lastCardAdd = System.currentTimeMillis();
					}
				}
			}
		}
		for(int i = 0; i < cardsPerPage; ++i){
			int index = i;
			int ch = Properties.height / 4 * 3 / 2;
			int cw = Properties.width / 8;
			int spacing = 64, ySpacing = 60;
			if(index < pagelist.size()){
				CardShell cs = pagelist.get(index);
				Card c = pagelist.get(index).getCard();				
				if(c!=null){
					Rectangle rect = new Rectangle(cw / 2 + Properties.width / 10 + spacing/2 + (i >= 5 ? (i - 5) : i) * (cw + spacing) - (mode == 0 ? 0 : Properties.width/20), spacing + (i / 5 * (ch + ySpacing)), cw, ch);
					if(rect.contains(getMousePoint())){
						c.draw(cs.getImg(), g, rect.x, rect.y, rect.width, rect.height, true);
						g.setFont(Util.dialogFont);
						g.setColor(Util.transparent_dark);
						g.fillRect(mousePoint.x+20, mousePoint.y-10, g.getFontMetrics().stringWidth("Right click to inspect...")+20, 40);
						g.setColor(Color.WHITE);
						g.drawString("Right click to inspect...", mousePoint.x+30, mousePoint.y+20);
					}
				}
			}
		}
		if(mode == 0){
			for(int i = 0; i < cards.length; ++i){
				Rectangle cb = new Rectangle(0, 0+i*(Properties.height/45 + 1) + 10, Properties.width/10, Properties.height/45+1);
				CardShell cs = cards[i];
				Card c = (cards[i] != null ? cards[i].getCard() : null);
				g.drawImage(c == null ? Bank.texture_tweed : cs.getImg(), cb.x, cb.y, cb.width, cb.height, null);
				if(c!=null){
					g.setColor(Util.transparent);
					g.fillRect(cb.x, cb.y, cb.width, cb.height);
				}
				Bank.drawOutline(g, cb.x, cb.y, cb.width, cb.height, Color.BLACK, 1);
				if(cb.contains(mousePoint) && c!=null){
					Bank.drawOutlineOut(g, cb.x, cb.y, cb.width, cb.height, Color.YELLOW, 3);
					g.setColor(Util.transparent);
					g.fillRect(0, 0, Properties.width, Properties.height);
					int cih1 = Properties.height / 5 * 3;
					int ciw1 = (int) (cih1 * 0.75);
					c.draw(cs.getImg(), g, Properties.width/2-ciw1/2, Properties.height/2-cih1/2, ciw1, cih1, true);			
					if(rightClicking && (System.currentTimeMillis() - lastCardAdd) >= 200){
						cards[i] = null;
						AnimatedParticle part = new AnimatedParticle(0, (i)*Properties.height/45-20, 100, 900).addFrameSet("EXPLOSION");
						part.width = Properties.width/10;
						part.height = Properties.width/20;
						Display.currentScreen.particles.add(part);
						lastCardAdd = System.currentTimeMillis();
						for(int j = 0; j < cards.length; ++j){
							if(cards[j] == null && cards[j+1]!=null){
								cards[j] = cards[j+1];
								cards[j+1] = null;
							}
						}
					}
				}
			}
		}
		g.setColor(Color.WHITE);
		g.setFont(Util.cooldownFont);
		g.drawString("Page: "+ (page/cardsPerPage+1), 10, 30);
		if(this.inspection != null){
			g.setColor(Util.transparent_dark);
			g.fillRect(0, 0, Properties.width, Properties.height);
			inspection.getCard().draw(inspection.getImg(), g, Properties.width/2-ciw/2, Properties.height/2-cih/2, ciw, cih, true);
			if(inspection.getCard().isCraftable()){
				int coinSize = 32;
				if(Util.collection[inspection.getCard().getId()] <= 0){
					g.setColor(Color.WHITE);
					g.drawImage(craftButton.contains(getMousePoint()) ? Bank.buttonHover : Bank.button, craftButton.x, craftButton.y, craftButton.width, craftButton.height, null);
					g.setFont(Util.cooldownBold);
					g.drawString("Create '"+inspection.getCard().getName()+"'", craftButton.x + craftButton.width / 2 - g.getFontMetrics().stringWidth("Create '"+inspection.getCard().getName()+"'") / 2, craftButton.y + craftButton.height / 4);
					g.setColor(Color.GREEN);
					g.drawString(""+(int)(Util.getDustValue(inspection.getCard()) * Card.CRAFT_MULTI), craftButton.x + craftButton.width / 2 - g.getFontMetrics().stringWidth(""+(Util.getDustValue(inspection.getCard()) * Card.CRAFT_MULTI)) / 2 - coinSize / 2, craftButton.y + craftButton.height - craftButton.height / 5);
					g.drawImage(Bank.coin, craftButton.x + craftButton.width / 2 + g.getFontMetrics().stringWidth(""+(Util.getDustValue(inspection.getCard()) * Card.CRAFT_MULTI)) / 2 + coinSize / 4, craftButton.y + craftButton.height - coinSize - 5 - craftButton.height / 10, coinSize, coinSize, null);
				
					if(clicking && craftButton.contains(mousePoint)){
						int cost = (int)(Util.getDustValue(inspection.getCard()) * Card.CRAFT_MULTI);
						if(Util.coins >= cost){
							Animation.createStaticAnimation(this.getMousePoint().x, this.getMousePoint().y, "fx7_energyBall", 5, 1000);
							Util.coins-=cost;
							Util.collection[inspection.getID()]++;
							Packet23Misc pkt = new Packet23Misc(5, ""+inspection.getID());
							pkt.write(Bank.client);
							this.inspection = null;
						}
					}
				}else{
					g.setColor(Color.WHITE);
					g.drawImage(decraftButton.contains(getMousePoint()) ? Bank.buttonHover : Bank.button, decraftButton.x, decraftButton.y, decraftButton.width, decraftButton.height, null);
					g.setFont(Util.cooldownBold);
					g.drawString("Salvage '"+inspection.getCard().getName()+"'", decraftButton.x + decraftButton.width / 2 - g.getFontMetrics().stringWidth("Salvage '"+inspection.getCard().getName()+"'") / 2, decraftButton.y + decraftButton.height / 4);
					g.setColor(Color.GREEN);
					g.drawString(""+(int)(Util.getDustValue(inspection.getCard())), decraftButton.x + decraftButton.width / 2 - g.getFontMetrics().stringWidth(""+(Util.getDustValue(inspection.getCard()) * Card.CRAFT_MULTI)) / 2 - coinSize / 2, decraftButton.y + decraftButton.height - decraftButton.height / 5);
					g.drawImage(Bank.coin, decraftButton.x + decraftButton.width / 2 + g.getFontMetrics().stringWidth(""+(Util.getDustValue(inspection.getCard()) * Card.CRAFT_MULTI)) / 2 + coinSize / 4, decraftButton.y + decraftButton.height - coinSize - 5 - decraftButton.height / 10, coinSize, coinSize, null);
					
					if(clicking && decraftButton.contains(mousePoint)){
						int value = (int)(Util.getDustValue(inspection.getCard()));
						if(Util.collection[inspection.getID()] > 0){
							Animation.createStaticAnimation(this.getMousePoint().x, this.getMousePoint().y, "fx7_energyBall", 5, 1000);
							Util.coins+=value;
							Util.collection[inspection.getID()] = 0;
							Packet23Misc pkt = new Packet23Misc(6, ""+inspection.getID());
							pkt.write(Bank.client);
							this.inspection = null;
						}
					}
				}
			}
		}
		g.drawImage(Bank.coin, Properties.width - 80, 0, 64, 64, null);
		g.setFont(Util.cooldownBold);
		g.drawString(""+Util.coins, Properties.width - 85 - g.getFontMetrics().stringWidth(Util.coins+""), 40);
	}

	public void load(File f, String deck) {
		this.basefile = f;
		String[] cards = deck.split("&")[0].split("%");
		for(String s : cards){
			this.addCard(Card.all[Integer.parseInt(s)]);
		}
	}

	public void setMode(int i) {
		this.mode = i;
	}
}
