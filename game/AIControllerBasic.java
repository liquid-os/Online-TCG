package org.author.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class AIControllerBasic extends AIController{
	
	private Random rand = new Random();

	public void onGameStart(PlayerMP player) {
		player.getHero().say("I will end you!");
	}

	public void onTurnChange(PlayerMP player, boolean isPlayerTurn) {
		if(isPlayerTurn){
			int totalRes = player.gold;
			ArrayList<Card> toPlay = new ArrayList<Card>();
			while(totalRes >= 0){
				Card best = findBestCard(player, totalRes);
				if(best != null){
					toPlay.add(best);
					totalRes -= best.getCost();
				}else{
					totalRes = 0;
				}
			}
			for(Card card : toPlay){
				if(card.isUnit() || card.isStructure()){
					Point p = this.getSuitableUnitPlayLocation(player);
					if(p != null){
						Packet02PlayCard pkt = new Packet02PlayCard(player.playerID, card.getId(), p.x, p.y, 0);
						Bank.server.sendToAll(pkt.getData());
					}
				}else{
					Unit tar = null;
					if(card.isSpell() || card.isBoardState()){
						tar = player.getGameUnits().get(rand.nextInt(player.getGameUnits().size()));
					}
					if(card.isEquipment()){
						tar = player.getPlayerUnits().get(rand.nextInt(player.getGameUnits().size()));
					}
					Packet02PlayCard pkt = new Packet02PlayCard(player.playerID, card.getId(), tar.posX, tar.posY, 0);
					Bank.server.sendToAll(pkt.getData());
				}
			}
		}
	}
	
	public Point getSuitableUnitPlayLocation(PlayerMP player){
		int baseX = player.getHero().posX;
		int baseY = player.getHero().posY;
		for(int i = -1; i < 2; ++i){
			for(int j = -1; j < 2; ++j){
				if(Display.currentScreen.getUnitOnTile(baseX + i, baseY + j, player.currentRoom) == null){
					return new Point(baseX + i, baseY + j);
				}
			}
		}
		return null;
	}
	
	public Card findBestCard(PlayerMP player, int gold){
		CardShell pick =  null;
		for(CardShell c : player.hand){
			if(c.getCost() <= gold){
				if(pick != null){
					if(c.getCost() >= pick.getCost()){
						pick = c;
					}
				}else{
					pick = c;
				}
			}
		}
		if(pick != null)
			return pick.getCard();
		else
			return null;
	}
}
