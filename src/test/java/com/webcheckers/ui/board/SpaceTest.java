package com.webcheckers.ui.board;

import com.webcheckers.model.Color;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpaceTest {

	private Space CuT;
	private int row;
	private int col;
	private Piece piece;

	@BeforeEach
	void setUp() {
		row = 4;
		col = 1;
		piece = null;
		CuT = new Space(row,col,piece);
	}
	
	@Test
	void getCellIdx() {
		assertEquals(col,CuT.getCellIdx());
	}
	
	@Test
	void isValid() {
		assertTrue(CuT.isValid());
	}
	@Test
	void getPiece() {
		assertEquals(piece,CuT.getPiece());
	}
}
