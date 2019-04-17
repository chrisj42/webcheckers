package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Tier")
class PositionTest {
	
	private Position CELLONE;
	
	@BeforeEach
	void setUp() {
		CELLONE = new Position(5,1);
	}
	
	@Test
	void getRow() {
		//Check to see if the returned row is correct
		assertEquals(CELLONE.getRow(),5);
		
		//Check to see if incorrect rows are caught
		assertNotEquals(CELLONE.getRow(),2);
	}
	
	@Test
	void getCell() {
		//Check to see if the returned cell is correct
		assertEquals(CELLONE.getCell(),1);
		
		//Check to see if incorrect cells are caught
		assertNotEquals(CELLONE.getCell(),5);
	}
}
