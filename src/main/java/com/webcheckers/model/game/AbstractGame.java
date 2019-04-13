package com.webcheckers.model.game;

import com.webcheckers.model.*;
import com.webcheckers.util.ViewMode;

public abstract class AbstractGame {
	
	/**
	 * The length of one side of a checkers board, in number of squares.
	 */
	public static final int BOARD_SIZE = 8;
	
	
	/**
	 * The red player (player one).
	 */
	protected final Player redPlayer;
	
	/**
	 * The white player (player two).
	 */
	protected final Player whitePlayer;
	
	/**
	 * The player whose turn it currently is will be stored here, and toggled each turn.
	 */
	protected Player activePlayer;
	
	/**
	 * The main game board, which is modified whenever a turn is submitted.
	 * The non-active player sees this board.
	 */
	protected final Piece[][] board;
	
	protected AbstractGame(Player redPlayer, Player whitePlayer) {
		this.redPlayer = redPlayer;
		this.whitePlayer = whitePlayer;
		
		activePlayer = redPlayer;
		
		board = new Piece[BOARD_SIZE][BOARD_SIZE];
		SetDefaultBoard();
	}

	protected AbstractGame(Player redPlayer, Player whitePlayer, TestMode mode) {
		this.redPlayer = redPlayer;
		this.whitePlayer = whitePlayer;

		activePlayer = redPlayer;

		board = new Piece[BOARD_SIZE][BOARD_SIZE];
		if(mode == TestMode.ENDGAME) {
			SetEndgameTestBoard();
		}
		else if(mode == TestMode.MULTJUMP) {
			SetMultJumpTestBoard();
		}
	}
	
	/**
	 * A simple utility method to set a position of a board to a piece.
	 *
	 * @param pos   the position to set
	 * @param board the board to modify
	 * @param piece the piece to put
	 * @return the piece that was replaced   
	 */
	protected static Piece setCell(Position pos, Piece[][] board, Piece piece) {
		Piece prev = getCell(pos, board);
		board[pos.getRow()][pos.getCell()] = piece;
		return prev;
	}
	
	/**
	 * A simple utility method to fetch the Piece at a position on a board.
	 *
	 * @param pos   the position to fetch at
	 * @param board the board to fetch from
	 * @return the piece at the position on the board
	 */
	protected static Piece getCell(Position pos, Piece[][] board) {
		return board[pos.getRow()][pos.getCell()];
	}
	
	/**
	 * Checks if a piece belongs to a player.
	 *
	 * @param piece  the piece
	 * @param player the player
	 * @return true if the piece color matches the player color, false otherwise
	 */
	protected boolean matchesPlayer(Piece piece, Player player) {
		// piece must be red to match red player
		if(player == redPlayer && piece.getColor() == Color.RED)
			return true;
		// piece must be white to match white player
		if(player == whitePlayer && piece.getColor() == Color.WHITE)
			return true;
		
		return false;
	}
	
	/**
	 * Copies the pieces from one board to another board.
	 * Used to submit a move, in which case the activeBoard is copied to the main board;
	 * also used to reset a move in progress, in which case the main board is copied to the activeBoard.
	 *
	 * Because this method is private, it assumes that the boards will both
	 * be @code{BOARD_SIZE} x @code{BOARD_SIZE} in dimensions.
	 *
	 * @param source board to copy from
	 * @param dest   board to copy to
	 */
	protected static void copyBoard(Piece[][] source, Piece[][] dest) {
		for(int r = 0; r < BOARD_SIZE; r++)
			System.arraycopy(source[r], 0, dest[r], 0, BOARD_SIZE);
	}
	
	/**
	 * Determines the color associated with the active player.
	 *
	 * @return the color of the active player
	 */
	public Color getActiveColor() {
		return activePlayer == redPlayer ? Color.RED : Color.WHITE;
	}
	
	/**
	 * boolean for determining if the game is over or not
	 * @return boolean for whether or not the game is over
	 *
	 */
	public abstract boolean isGameOver();
	
	public abstract String getGameOverMessage();
	
	/**
	 * Fetches the Player object for the red player (player one).
	 *
	 * @return the red player
	 */
	public Player getRedPlayer() {
		return redPlayer;
	}
	
	/**
	 * Fetches the Player object for the white player (player two).
	 *
	 * @return the white player
	 */
	public Player getWhitePlayer() {
		return whitePlayer;
	}
	
	public boolean isPlayer1(Player player) {
		return player == redPlayer;
	}
	
	/*
	 * Fetches the Player object representing the opponent of the given player in this game.
	 * If passed the red player, the white player is returned.
	 * If passed the white player, the red player is returned.
	 * If passed any other player, null is returned.
	 *
	 * @param player the player to find the opponent of
	 * @return the opponent of the given player, or null if this player is not part of this game
	 */
	public Player getOpponent(Player player) {
		if(player == redPlayer)
			return whitePlayer;
		else if(player == whitePlayer)
			return redPlayer;
		else
			return null;
	}

	/**
	 * Adds pieces to board for a normal Checkers game
	 */
	protected void SetDefaultBoard() {
		for(int row = 0; row < board.length; row++) {
			if(row < 3) {
				for(int col = 0; col < board[row].length; col++)
					if((col+row) % 2 == 1) // maths to find black tiles
						board[row][col] = new Piece(Type.SINGLE, Color.WHITE);
			}
			if(row >= BOARD_SIZE - 3) {
				for(int col = 0; col < board[row].length; col++)
					if((col+row) % 2 == 1) // maths to find black tiles
						board[row][col] = new Piece(Type.SINGLE, Color.RED);
			}
		}
	}

	protected void SetMultJumpTestBoard() {
		board[5][4] = new Piece(Type.KING, Color.RED);
		board[7][2] = new Piece(Type.KING, Color.RED);
		board[2][3] = new Piece(Type.KING, Color.WHITE);
		board[2][5] = new Piece(Type.KING, Color.WHITE);
		board[4][5] = new Piece(Type.KING, Color.WHITE);
	}

	protected void SetEndgameTestBoard() {
		board[3][4] = new Piece(Type.KING, Color.WHITE);
		board[4][3] = new Piece(Type.KING, Color.RED);
	}
	
	public abstract ViewMode getViewMode(Player player);
	
	public abstract Piece[][] flushBoard(Player player);
}
