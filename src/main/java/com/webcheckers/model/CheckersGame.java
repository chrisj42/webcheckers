package com.webcheckers.model;

import java.util.LinkedList;

import com.webcheckers.model.game.AbstractGame;
import com.webcheckers.util.Message;
import com.webcheckers.util.ViewMode;

// the server-side model of a game of checkers, including the players involved, which player started the game (i.e. which player is "player 1" i.e. the red player), and the 2D array of Piece objects.
public class CheckersGame extends AbstractGame {
	
	/**
	 * The "active board;" this board acts as a temporary playing field for
	 * the active player while they are planning out their move, or making
	 * their move, before it gets submitted. On submission, this board is
	 * copied to the main board iteratively.
	 * 
	 * This board is reset when the server receives a get request from
	 * the active player, because they have reset their local movement tracking.
	 */
	private final Piece[][] activeBoard;
	
	/**
	 * This list stores the sequence of moves that the active player has made;
	 * these moves have not yet been submitted, and only show on the active board.
	 * 
	 * This list is reset when the server receives a get request from
	 * the active player, because they have reset their local movement tracking.
	 */
	private final LinkedList<Move> cachedMoves = new LinkedList<>();
	
	private boolean isGameOver;
	
	private String gameOverMessage = null;
	
	/**
	 * CheckersGame constructor. Creates the board objects and initializes them
	 * with the starting layout for a checkers game.
	 * 
	 * @param redPlayer   the player controlling the red checkers (player one)
	 * @param whitePlayer the player controlling the white checkers (player two)
	 */
	public CheckersGame(Player redPlayer, Player whitePlayer) {
		super(redPlayer, whitePlayer);
		isGameOver = false;
		
		activeBoard = new Piece[BOARD_SIZE][BOARD_SIZE];
		copyBoard(board, activeBoard);
	}

	public CheckersGame(Player redPlayer, Player whitePlayer, TestMode mode) {
		super(redPlayer, whitePlayer, mode);
		isGameOver = false;

		activeBoard = new Piece[BOARD_SIZE][BOARD_SIZE];
		copyBoard(board, activeBoard);
	}
	
	/**
	 * Determines if the given player can make a jump move.
	 * 
	 * @param player the player making the move.
	 * @return if the player can 
	 */
	private boolean canMakeJump(Player player) {
		return canMakeMove(player, false);
	}
	
	private boolean canMakeMove(Player player) {
		return canMakeMove(player, true);
	}
	
	private boolean canMakeMove(Player player, boolean checkSingle) {
		// check if single moves are possible
		for(int r = 0; r < activeBoard.length; r++) {
			for(int c = 0; c < activeBoard[r].length; c++) {
				Piece piece = activeBoard[r][c];
				if(piece == null) continue;
				if(matchesPlayer(piece, player) && canMakeMove(piece, r, c, checkSingle, false))
					return true;
			}
		}
		
		return false;
	}
	
	private boolean canMakeMove(Piece piece, int row, int col, boolean checkSingle, boolean kingValidation) {
		// determine primary direction
		int dir = piece.getColor() == Color.RED ? -1 : 1;
		if(kingValidation) dir *= -1;
		
		// check king
		if(piece.getType() == Type.KING && !kingValidation)
			return canMakeMove(piece, row, col, checkSingle, true);
		
		// ensure movement dir is valid
		if(row+dir < 0 || row+dir >= BOARD_SIZE)
			return false;
		
		// check forward moves
		if(checkSingle) {
			// check non-jump
			if(col > 0 && activeBoard[row+dir][col-1] == null)
				return true;
			if(col < BOARD_SIZE-1 && activeBoard[row+dir][col+1] == null)
				return true;
		}
		
		// check jumps
		if(checkJump(piece, row, col, dir, -1))
			return true;
		if(checkJump(piece, row, col, dir, 1))
			return true;
		
		return false;
	}
	
	private boolean checkJump(Piece piece, int row, int col, int rdir, int cdir) {
		// ensure move is on board
		if(col+cdir*2 < 0 || col+cdir*2 >= BOARD_SIZE)
			return false;
		if(row+rdir*2 < 0 || row+rdir*2 >= BOARD_SIZE)
			return false;
		
		// ensure end spot is open
		if(activeBoard[row+rdir*2][col+cdir*2] != null)
			return false;
		
		// ensure jumped color is different
		Piece jumped = activeBoard[row+rdir][col+cdir];
		if(jumped == null || jumped.getColor() == piece.getColor())
			return false;
		
		return true;
	}
	
	/**
	 * Checks if the player has run out of pieces or cannot move.
	 * If so, gameOver is set to true and the game over message is
	 * set to say the other player has won.
	 */
	private void checkGameOver(Player player) {
		boolean hasPieces = false;
		for(int r = 0; r < activeBoard.length; r++) {
			for(int c = 0; c < activeBoard[r].length; c++) {
				if(activeBoard[r][c] != null && matchesPlayer(activeBoard[r][c], player)) {
					hasPieces = true;
					break;
				}
			}
			if(hasPieces) break;
		}
		
		if(!hasPieces) {
			gameOverMessage = (player == redPlayer ? whitePlayer : redPlayer).getName()+" has captured all the pieces.";
			isGameOver = true;
			return;
		}
		
		// see if the player has run out of valid moves
		if(!canMakeMove(player)) {
			gameOverMessage = player.getName()+" has run out of valid moves.";
			isGameOver = true;
		}
	}
	
	/**
	 * Validates a proposed move before storing it in the active move cache.
	 * It returns an error message if:
	 * 	- the given player is not the active player
	 * 	- the end position is occupied by an existing piece
	 * 	- there is a previous move in the move cache and its end position
	 * 		doesn't match this move's start position (i.e. the player is attempting
	 * 		to move a different checker).
	 * 	- the move is not diagonally forward left or right one space
	 * 
	 * @param move the move object, holding the start and end positions
	 * @param player the player who is requesting the move
	 * @return the status message that the player should see on their screen having attempted to play the move.
	 */
	public Message validateMove(Move move, Player player) {
		if(activePlayer != player)
			// shouldn't happen but we'll put it in just in case.
			return Message.error("It is not your turn!");
		
		// eliminate all possible moves that are never valid
		if(!move.isValid())
			return Message.error("Move is not valid.");
		
		// do checks relating to previously validated moves
		Move prevMove = cachedMoves.peekLast();
		if(prevMove != null) {
			// ensure same checker
			if(!prevMove.getEnd().equals(move.getStart()))
				return Message.error("Only one checker can be moved per turn.");
			
			// only allow extra move if previous move was a jump, and this is a jump also
			if(!prevMove.isJump() || !move.isJump())
				return Message.error("Only jumps can be chained.");
		}
		
		// checks for existing piece
		if(getCell(move.getEnd(), activeBoard) != null)
			return Message.error("space is occupied");
		
		// checks for single-space diagonal movement in the correct direction
		Piece cell = getCell(move.getStart(), activeBoard);
		if(cell.getType() != Type.KING) {
			boolean dirValid;
			int rowDelta = move.getRowDelta();
			// the white player must move down, the red player must move up
			if(player == whitePlayer)
				dirValid = rowDelta > 0; // check white
			else
				dirValid = rowDelta < 0; // check my boi red
			if(!dirValid)
				return Message.error("Normal checkers can only move forward.");
		}
		
		if(move.isJump()) {
			Piece jumped = getCell(move.getJumpPos(), activeBoard);
			if(jumped == null || matchesPlayer(jumped, player))
				return Message.error("Jumps must remove an opponent checker.");
		}
		
		// don't allow simple moves if a jump move is possible
		if(!move.isJump() && canMakeJump(player))
			return Message.error("A jump is possible.");
		
		// move validated
		cachedMoves.add(move);
		Piece temp = setCell(move.getStart(), activeBoard, null);
		setCell(move.getEnd(), activeBoard, temp);
		if(move.isJump())
			setCell(move.getJumpPos(), activeBoard, null);
		
		return Message.info("Move validated.");
	}
	
	/**
	 * Undoes the previously validated move, if one exists.
	 * 
	 * @param player the player making the request
	 * @return a status message reporting whether the request was successful
	 */
	public Message backupMove(Player player) {
		// ensure this is the right player
		if(player != activePlayer)
			return Message.error("It's not your turn!");
		
		// check if there are any moves to undo
		Move move = cachedMoves.pollLast();
		if(move == null)
			return Message.error("You have no moves to undo.");
		
		Piece temp = setCell(move.getEnd(), activeBoard, null);
		setCell(move.getStart(), activeBoard, temp);
		if(move.isJump())
			setCell(move.getJumpPos(), activeBoard, getCell(move.getJumpPos(), board));
		
		return Message.info("Move undone.");
	}
	
	/**
	 * Submits the cache of validated moves and switches to the opponent's turn.
	 * 
	 * @param player the player making the request
	 * @return a status message reporting whether the request was successful
	 */
	public Message submitTurn(Player player) {
		// ensure this is the right player
		if(player != activePlayer)
			return Message.error("It's not your turn!");
		
		if(cachedMoves.size() == 0)
			return Message.error("There are no moves to submit.");
		
		Move move = cachedMoves.getLast();
		Position pos = move.getEnd();
		Piece cell = getCell(pos, activeBoard);
		int row = pos.getRow();
		
		if(move.isJump() && canMakeMove(cell, row, pos.getCell(), false, false))
			return Message.error("Multi-jump moves must be completed.");
		if((cell.getColor() == Color.RED && row == 0) || (cell.getColor() == Color.WHITE && row == 7))
			cell.promote();
		
		copyBoard(activeBoard, board);
		cachedMoves.clear();
		activePlayer = getOpponent(activePlayer);
		
		checkGameOver(activePlayer);
		
		return Message.info("Turn submitted.");
	}
	
	/**
	 * 
	 */
	public Message resignGame(Player player) {
		if(player == activePlayer && cachedMoves.size() > 1)
			return Message.error("you must undo all moves before resigning.");
		
		if(!isGameOver) {
			gameOverMessage = player.getName() + " has resigned.";
			isGameOver = true;
		}
		return Message.info("You have resigned.");
	}
	
	/**
	 * boolean for determining if the game is over or not
	 * @return boolean for whether or not the game is over
	 *
	 */
	@Override
	public boolean isGameOver() {
		return isGameOver;
	}
	
	@Override
	public String getGameOverMessage() {
		return gameOverMessage;
	}
	
	/**
	 * Returns whether it is the given player's turn.
	 * If the game is over, then it always returns true
	 * so that the opponent will refresh the page.
	 * 
	 * @param player the player
	 * @return if it is currently the given player's turn
	 */
	public boolean isPlayerTurn(Player player) {
		return isGameOver || activePlayer == player;
	}
	
	@Override
	public ViewMode getViewMode(Player player) {
		return player == whitePlayer || player == redPlayer ? ViewMode.PLAY : ViewMode.SPECTATOR;
	}
	
	/**
	 * Returns the main board; if the provided player is the active player,
	 * then the activeBoard is reset and the move cache is cleared. This
	 * should only be called in response to a @code{GET /game} request.
	 * 
	 * @param player the player requesting the board
	 * @return the board
	 */
	@Override
	public Piece[][] flushBoard(Player player) {
		if(player == activePlayer) {
			copyBoard(board, activeBoard);
			cachedMoves.clear();
		}
		
		return board;
	}
}
