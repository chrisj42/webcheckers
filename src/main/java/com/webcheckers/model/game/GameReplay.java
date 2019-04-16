package com.webcheckers.model.game;

import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.model.TurnReplay;
import com.webcheckers.util.Message;
import com.webcheckers.util.ViewMode;

public class GameReplay extends AbstractGame {
	
	private final TurnReplay[] moves;
	private int curMove;
	
	private final String gameOverMessage;
	
	public GameReplay(GameReplayData replayData) {
		super(replayData.getRedPlayer(), replayData.getWhitePlayer());
		
		this.moves = replayData.getMoveReplays();
		curMove = 0;
		this.gameOverMessage = replayData.getGameOverMessage();
	}
	
	public boolean hasNextMove() {
		return curMove < moves.length;
	}
	
	public Message playNextMove() {
		if(!hasNextMove())
			return Message.error("No next move.");
		
		moves[curMove].applyTurn(board);
		curMove++;
		
		return Message.info("true");
	}
	
	public boolean hasPreviousMove() {
		return curMove > 0;
	}
	
	public Message undoMove() {
		if(!hasPreviousMove())
			return Message.error("No previous move.");
		
		curMove--;
		moves[curMove].undoTurn(board);
		
		return Message.info("true");
	}
	
	/**
	 * @inheritDoc
	 * @return true; the game is always already over when a replay exists.
	 */
	@Override
	public boolean isGameOver() {
		return true;
	}
	
	/**
	 * @inheritDoc
	 */
	@Override
	public String getGameOverMessage() {
		return gameOverMessage;
	}
	
	/**
	 * Returns the ViewMode that the given player must have; GameReplays can only be viewed in REPLAY mode.
	 * @param player viewing player
	 * @return ViewMode.REPLAY
	 */
	@Override
	public ViewMode getViewMode(Player player) {
		return ViewMode.REPLAY;
	}
	
	@Override
	public Piece[][] flushBoard(Player player) {
		return board;
	}
}
