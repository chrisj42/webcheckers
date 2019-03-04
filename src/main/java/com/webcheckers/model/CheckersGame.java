package com.webcheckers.model;

import com.webcheckers.ui.BoardView;

// the server-side model of a game of checkers, including the players involved, which player started the game (i.e. which player is "player 1" i.e. the red player), and the 2D array of Piece objects.
public class CheckersGame {
	
	private Player player1;
	private Player player2;
	
	private Piece[][] board;
	
	public CheckersGame(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		
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
	
	public Player getOpponent(Player player) {
		if(player == player1)
			return player2;
		else if(player == player2)
			return player1;
		else
			return null;
	}
	
	public boolean isPlayer1(Player player) { return player == player1; }
	
	public Piece[][] getBoard() {
		return board;
		
		/*Piece[][] flip = new Piece[BoardView.SIZE][BoardView.SIZE];
		for(int y = 0; y < flip.length; y++)
			for(int x = 0; x < flip[y].length; x++)
				flip[BoardView.SIZE-y-1][BoardView.SIZE-x-1] = board[y][x];
			
		return flip;*/
	}
}
