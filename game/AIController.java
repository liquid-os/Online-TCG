package org.author.game;

public abstract class AIController {
	
	public abstract void onGameStart(PlayerMP player);

	public abstract void onTurnChange(PlayerMP player, boolean isPlayerTurn);
}
