package com.webcheckers.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
	
	private String NAME = "PlayerOne";
	private String INVALIDNAME = "FakeName";
	
	@Test
	void getName() {
		Player P1 = new Player(NAME);
		//Check to see if the name returned is correct
		assertEquals(NAME,P1.getName());
		
		//Check to see if invalid names are caught.
		assertNotEquals(INVALIDNAME,P1.getName());
	}
}
