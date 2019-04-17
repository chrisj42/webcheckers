package com.webcheckers.model.game;

import com.webcheckers.model.Color;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.model.Type;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("Model-Tier")
class AbstractGameTest {

	private AbstractGame CuT;

	private Player redPlayer;
	private Player whitePlayer;
	private Position posRed;
	private Position posWhite;
	private Piece pieceRed;
	private Piece pieceWhite;


	@BeforeEach
	void setUp() {

		redPlayer = new Player("P1");
		whitePlayer = new Player("P2");

		pieceRed = new Piece(Type.SINGLE,Color.RED);
		pieceWhite = new Piece(Type.SINGLE,Color.WHITE);

		posRed = new Position(7,0);
		posWhite = new Position(0,1);


		CuT = new CheckersGame(redPlayer,whitePlayer, null);


	}
	
	@AfterEach
	void tearDown() {


	}
	
	@Test
	void setCell() {
		assertNotNull(CuT.setCell(posRed,CuT.board,pieceRed));


	}
	
	@Test
	void getCell() {

		assertNotNull(CuT.getCell(posRed,CuT.board));


	}
	
	@Test
	void matchesPlayer() {

		assertTrue(CuT.matchesPlayer(pieceRed,redPlayer));
		assertTrue(CuT.matchesPlayer(pieceWhite,whitePlayer));
		assertFalse(CuT.matchesPlayer(pieceRed,whitePlayer));
		assertFalse(CuT.matchesPlayer(pieceWhite,redPlayer));

	}
	
	@Test
	void copyBoard() {
		//Piece[][] dest = new Piece[][];
		assertEquals(CuT.board,CuT.flushBoard(redPlayer));

	}
	
	@Test
	void getActiveColor() {
		assertEquals(redPlayer,CuT.activePlayer);
		assertNotEquals(whitePlayer,CuT.activePlayer);

	}
	
	@Test
	void getRedPlayer() {
		assertEquals(redPlayer,CuT.getRedPlayer());
		assertNotEquals(whitePlayer,CuT.getRedPlayer());
	}
	
	@Test
	void getWhitePlayer() {
		assertEquals(whitePlayer,CuT.getWhitePlayer());
		assertNotEquals(redPlayer,CuT.getWhitePlayer());
	}
	
	@Test
	void isPlayer1() {
		assertTrue(CuT.isPlayer1(redPlayer));
		assertFalse(CuT.isPlayer1(whitePlayer));

	}
	
	@Test
	void getOpponent() {
		assertEquals(whitePlayer,CuT.getOpponent(redPlayer));
		assertEquals(redPlayer,CuT.getOpponent(whitePlayer));
		assertEquals(null,CuT.getOpponent(new Player("P1")));

	}


}
