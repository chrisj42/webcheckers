package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Tier")
class PieceTest {
	
	@Test
	void getType() {
		Piece singleChecker = new Piece(Type.SINGLE, Color.RED);
		Piece kingChecker = new Piece(Type.KING,Color.RED);
		
		//Checks to see if the correct color is returned.
		assertEquals(singleChecker.getType(),Type.SINGLE);
		assertEquals(kingChecker.getType(),Type.KING);
	}
	
	@Test
	void getColor() {
		Piece redChecker = new Piece(Type.SINGLE, Color.RED);
		Piece whiteChecker = new Piece(Type.SINGLE, Color.WHITE);
		
		//Checks to see if the correct color is returned.
		assertEquals(redChecker.getColor(),Color.RED);
		assertEquals(whiteChecker.getColor(),Color.WHITE);
	}
}
