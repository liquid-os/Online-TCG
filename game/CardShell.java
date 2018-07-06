package org.author.game;

import java.awt.Image;

public class CardShell {
	
	private Card card = null;
	private Image img = null;
	
	public CardShell(Card c){
		card = c;
	}
	
	public int getID(){
		return getCard().getId();
	}
	
	public Card getCard(){
		return (card == null ? Card.filler : card);
	}

	public Image getImg() {
		if(this.img == null)setImg(Filestream.getCardImage(getCard().getArt()));
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public int getCost() {
		return getCard().getCost();
	}
}
