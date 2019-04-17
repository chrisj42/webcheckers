package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("Model-Tier")
class MoveTest {
	
	private static Position redMoveOne = new Position(5,0);
	
	// Used for normal move
	private static Position redMoveTwo = new Position(4,1);
	
	// Used for jump move
	private static Position redMoveThree = new Position(3,2);
	
	private static Position invalidRedOne = new Position(6,1);
	private static Position invalidRedTwo = new Position(5,1);
	
	@Test
	void getStart() {
		final Move redMove = new Move(redMoveOne,redMoveTwo);
		
		//Tests to see if the starting position is correctly stored and checked.
		assertEquals(redMove.getStart(), redMoveOne);
	}
	
	@Test
	void getEnd() {
		final Move redMove = new Move(redMoveOne,redMoveTwo);
		
		//Tests to see if the end position is correctly stored and checked
		assertEquals(redMove.getEnd(), redMoveTwo);
	}
	
	@Test
	void isValid() {
		final Move redMoveNormal = new Move(redMoveOne,redMoveTwo);
		
		//Checks if normal move is valid
		assertTrue(redMoveNormal.isValid());
		
		final Move redMoveJump = new Move(redMoveOne,redMoveThree);
		
		//Checks if jump move is valid
		assertTrue(redMoveJump.isValid());
	}
	
	@Test
	void isJump() {
		final Move redMoveJump = new Move(redMoveOne,redMoveThree);
		
		//Checks that jump moves are checked
		assertTrue(redMoveJump.isJump());
	}
	
	@Test
	void badIsJump() {
		final Move redMove = new Move(redMoveOne,redMoveTwo);
		
		//Checks that none jump moves are caught
		assertFalse(redMove.isJump());
	}
	
	@Test
	void getJumpPos() {
		final Move redMove = new Move(redMoveOne,redMoveThree);
		
		//Checks that the position returned by getJumpPos is the correct position.
		assertEquals(redMove.getJumpPos(),redMoveTwo);
	}
	
	@Test
	void getBadJumpPos() {
		final Move redMove = new Move(redMoveOne,redMoveThree);
		
		//Checks that the position checks for incorrect jump positions.
		assertNotEquals(redMove.getJumpPos(),redMoveThree);
	}
	
	@Test
	void getRowDelta() {
		final Move redMove = new Move(redMoveOne,redMoveTwo);
		
		//Checks that row delta is right
		assertEquals(redMove.getRowDelta(), -1);
	}
	
	@Test
	void getColumnDelta() {
		final Move redMove = new Move(redMoveOne,redMoveTwo);
		
		//Checks that column delta is right
		assertEquals(redMove.getColumnDelta(), 1);
	}
}
