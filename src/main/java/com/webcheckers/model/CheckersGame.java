package com.webcheckers.model;

import com.webcheckers.ui.board.BoardView;

// the server-side model of a game of checkers, including the players involved, which player started the game (i.e. which player is "player 1" i.e. the red player), and the 2D array of Piece objects.
public class CheckersGame {
	
	private final Player redPlayer;
	private final Player whitePlayer;
	
	private final Piece[][] board;
	
	public CheckersGame(Player redPlayer, Player whitePlayer) {
		this.redPlayer = redPlayer;
		this.whitePlayer = whitePlayer;
		
		board = new Piece[BoardView.SIZE][BoardView.SIZE];
		for(int y = 0; y < board.length; y++) {
			if(y < 3) {
				for(int x = 0; x < board[y].length; x++)
					if((x+y) % 2 == 1)
						board[y][x] = new Piece(Type.SINGLE, Color.WHITE);
			}
			if(y >= BoardView.SIZE - 3) {
				for(int x = 0; x < board[y].length; x++)
					if((x+y) % 2 == 1)
						board[y][x] = new Piece(Type.SINGLE, Color.RED);
			}
		}
	}
	
	public Player getRedPlayer() {
		return redPlayer;
	}
	
	public Player getWhitePlayer() {
		return whitePlayer;
	}
	
	public Player getOpponent(Player player) {
		if(player == redPlayer)
			return whitePlayer;
		else if(player == whitePlayer)
			return redPlayer;
		else
			return null;
	}
	
	public boolean isPlayer1(Player player) { return player == redPlayer; }
	
	public Piece[][] getBoard() {
		return board;
	}
}
