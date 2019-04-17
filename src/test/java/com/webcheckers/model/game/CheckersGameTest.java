package com.webcheckers.model.game;

import com.webcheckers.model.*;
import com.webcheckers.util.Message;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("Model-Tier")
class CheckersGameTest {

	private CheckersGame CuT;

	private Player redPlayer;
	private Player whitePlayer;
	private Position posRed;
	private Position posWhite;
	private Position kingPiece;
	private Piece pieceRed;
	private Piece pieceWhite;
	private Move move;
	private Move inValidMove;
	private Position pieceKing;

	@BeforeEach
	void setUp() {
		redPlayer = new Player("P1");
		whitePlayer = new Player("P2");

		pieceRed = new Piece(Type.SINGLE, Color.RED);
		pieceWhite = new Piece(Type.SINGLE,Color.WHITE);



		posRed = new Position(7,0);
		posWhite = new Position(0,1);

		pieceKing = new Position(5,2);

		move = new Move(new Position(5,0),new Position(4,1));
		inValidMove = new Move(new Position(5,0), new Position(5,1));



		CuT = new CheckersGame(redPlayer,whitePlayer, null);
		CuT.board[5][2]= new Piece(Type.KING,Color.RED);
		CuT.board[4][3] = new Piece(Type.SINGLE,Color.WHITE);
		CuT.board[4][1]= new Piece(Type.SINGLE,Color.WHITE);
	}
	
	@AfterEach
	void tearDown() {

		CuT.activePlayer=CuT.redPlayer;

	}
	
	@Test
	void validateMove() {

		assertEquals(Message.Type.ERROR,CuT.validateMove(move,whitePlayer).getType());
		assertEquals(Message.Type.ERROR,CuT.validateMove(inValidMove,CuT.activePlayer).getType());
		assertEquals(Message.Type.INFO,CuT.validateMove(move,CuT.activePlayer).getType());
		CuT.validateMove(move,CuT.activePlayer);
		assertEquals(Message.Type.ERROR,CuT.validateMove(move,CuT.activePlayer).getType());
		assertEquals(Message.Type.ERROR,CuT.validateMove(new Move(new Position(5,0),
				new Position(3,2)),CuT.activePlayer).getType());
		assertEquals(Message.Type.ERROR,CuT.validateMove(new Move(new Position(7,0),
				new Position(6,1)),CuT.activePlayer).getType());
		CuT.flushBoard(CuT.activePlayer);
		CuT.setCell(pieceKing,CuT.board,new Piece(Type.KING,Color.RED));
		assertEquals(Message.Type.INFO,CuT.validateMove(new Move(pieceKing,new Position(3,4)),
				CuT.activePlayer).getType());
		CuT.flushBoard(CuT.activePlayer);
		assertEquals(Message.Type.ERROR,CuT.validateMove(new Move(new Position(4,3),
				new Position(3,2)),CuT.whitePlayer).getType());

		Move invalidBack = new Move(new Position(4,1),new Position(3,0));
		CuT.activePlayer=whitePlayer;
		assertEquals(Message.Type.ERROR,CuT.validateMove(invalidBack,whitePlayer).getType());

	}
	
	@Test
	void backupMove() {

		CuT.validateMove(move,CuT.activePlayer);
		assertEquals(Message.Type.INFO, CuT.backupMove(CuT.activePlayer).getType());
		CuT.flushBoard(CuT.activePlayer);
		assertEquals(Message.Type.ERROR, CuT.backupMove(CuT.activePlayer).getType());
		assertEquals(Message.Type.ERROR,CuT.backupMove(CuT.whitePlayer).getType());

	}
	
	@Test
	void submitTurn() {
		assertEquals(Message.Type.ERROR, CuT.submitTurn(CuT.whitePlayer).getType());
		assertEquals(Message.Type.ERROR,CuT.submitTurn(CuT.activePlayer).getType());
		assertEquals(Message.Type.ERROR,CuT.submitTurn(CuT.activePlayer).getType());

		CuT.validateMove(move,CuT.activePlayer);
		assertEquals(Message.Type.INFO,CuT.submitTurn(CuT.activePlayer).getType());

		CuT.validateMove(inValidMove,CuT.activePlayer);
		assertEquals(Message.Type.ERROR,CuT.submitTurn(CuT.activePlayer).getType());

		CuT.flushBoard(CuT.activePlayer);

	}
	
	@Test
	void resignGame() {
		CuT.activePlayer=redPlayer;
		assertEquals(Message.Type.INFO, CuT.resignGame(CuT.whitePlayer).getType());

	}

	@Test
	void resignGame_1() {
		CuT.activePlayer=redPlayer;
		assertEquals(Message.Type.INFO,CuT.resignGame(CuT.activePlayer).getType());
	}

	@Test
	void isGameOver() {

		assertFalse(CuT.isGameOver());

		CuT.resignGame(CuT.activePlayer);
		assertTrue(CuT.isGameOver());

	}
	
	@Test
	void getGameOverMessage() {
		assertNull(CuT.getGameOverMessage());
	}
	
	@Test
	void generateReplayData() {

		assertEquals(GameReplayData.class, CuT.generateReplayData().getClass());
	}
	
	@Test
	void isPlayerTurn() {
		assertTrue(CuT.isPlayerTurn(CuT.activePlayer));
		assertFalse(CuT.isPlayerTurn(CuT.whitePlayer));

		CuT.resignGame(CuT.activePlayer);
		assertTrue(CuT.isPlayerTurn(CuT.activePlayer));
		assertTrue(CuT.isPlayerTurn(CuT.whitePlayer));

	}
	
	@Test
	void getViewMode() {
		assertNotNull(CuT.getViewMode(CuT.activePlayer));
	}
	
	@Test
	void getLastSubmitTime() {
		assertNotNull(CuT.getLastSubmitTime());
	}
	
	@Test
	void flushBoard() {
		assertNotNull(CuT.flushBoard(CuT.activePlayer));
		assertEquals(CuT.board.getClass(),CuT.flushBoard(CuT.activePlayer).getClass());

	}
}
