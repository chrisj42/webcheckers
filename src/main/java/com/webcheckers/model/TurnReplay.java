package com.webcheckers.model;

import java.util.List;

public class TurnReplay {
	
	private final Player player;
	private final MoveReplay[] moves;
	
	public TurnReplay(Player player, List<MoveReplay> moves) {
		this.player = player;
		this.moves = moves.toArray(new MoveReplay[0]);
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void applyTurn(Piece[][] board) {
		for(MoveReplay move: moves)
			move.applyMove(board);
	}
	
	public void undoTurn(Piece[][] board) {
		for(int i = moves.length-1; i >= 0; i--)
			moves[i].undoMove(board);
	}
}
