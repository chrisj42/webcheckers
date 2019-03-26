package com.webcheckers.model;

import java.util.LinkedList;

import com.webcheckers.ui.board.BoardView;
import com.webcheckers.util.Message;

// the server-side model of a game of checkers, including the players involved, which player started the game (i.e. which player is "player 1" i.e. the red player), and the 2D array of Piece objects.
public class CheckersGame {
	
	private final Player redPlayer;
	private final Player whitePlayer;
	
	private final Piece[][] board;
	private final Piece[][] activeBoard;
	
	private Player activePlayer;
	private final LinkedList<Move> cachedMoves = new LinkedList<>();
	
	public CheckersGame(Player redPlayer, Player whitePlayer) {
		this.redPlayer = redPlayer;
		this.whitePlayer = whitePlayer;
		activePlayer = redPlayer;
		
		board = new Piece[BoardView.SIZE][BoardView.SIZE];
		activeBoard = new Piece[BoardView.SIZE][BoardView.SIZE];
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
			System.arraycopy(board[y], 0, activeBoard[y], 0, board.length);
		}
	}
	
	public Piece getPiece(Position pos) {
		return board[pos.getRow()][pos.getCell()];
	}
	
	public Message validateMove(Move move, Player player) {
		if(getPiece(move.getEnd()) != null)
			return Message.error("space is occupied");
		
		int disty = move.getStart().getRow() - move.getEnd().getRow();
		int distx = move.getStart().getCell() - move.getEnd().getCell();
		
		// Piece piece = getPiece(move.getStart());
		
		// the white player must move down, the red player must move up
		int dir = player == whitePlayer ? 1 : -1;
		
		if(Math.abs(distx) != 1 || disty != dir)
			return Message.error("move is invalid");
		
		/*for(Move prev: cachedMoves) {
			if(prev.getStart().equals(move.getEnd()))
				return Message.error("place is taken")
		}*/
		
		cachedMoves.add(move);
		return Message.info("Move validated.");
	}
	
	public Message backupMove() {
		return Message.error("Move backup not implemented.");
	}
	
	public Message submitTurn() {
		return Message.error("Move submission not implemented.");
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
	
	public Piece[][] getBoard(Player player) {
		return player == activePlayer ? board : board;
	}
}
