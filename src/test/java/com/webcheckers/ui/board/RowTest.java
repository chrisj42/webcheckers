package com.webcheckers.ui.board;

import com.webcheckers.model.Piece;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RowTest {

	private Row CuT;
	private int idx;
	private Piece[] pieces;


	@BeforeEach
	void setUp(){
		idx = 5;
		pieces = new Piece[8];

		CuT = new Row(idx,pieces,true);

	}

	@Test
	void getIndex() {
		assertEquals(idx, CuT.getIndex());
	}
	
	@Test
	void iterator() {
		assertEquals(Space.class,CuT.iterator().next().getClass());
	}
}
