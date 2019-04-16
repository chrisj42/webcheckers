package com.webcheckers.model.game;

import java.util.Arrays;
import java.util.List;

import com.webcheckers.model.TurnReplay;
import com.webcheckers.model.Player;

public class GameReplayData {
	
	private final Player redPlayer, whitePlayer;
	private final String gameOverMessage;
	private final TurnReplay[] movesMade;
	
	public GameReplayData(Player redPlayer, Player whitePlayer, String gameOverMessage, List<TurnReplay> movesMade) {
		this.redPlayer = redPlayer;
		this.whitePlayer = whitePlayer;
		this.gameOverMessage = gameOverMessage;
		this.movesMade = movesMade.toArray(new TurnReplay[0]);
	}
	
	public Player getRedPlayer() {
		return redPlayer;
	}
	
	public Player getWhitePlayer() {
		return whitePlayer;
	}
	
	public String getGameOverMessage() {
		return gameOverMessage;
	}
	
	public TurnReplay[] getMoveReplays() {
		return Arrays.copyOf(movesMade, movesMade.length);
	}
}
