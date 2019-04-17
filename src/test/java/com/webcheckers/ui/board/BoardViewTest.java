package com.webcheckers.ui.board;

import com.webcheckers.model.Player;
import com.webcheckers.model.game.AbstractGame;
import com.webcheckers.model.game.CheckersGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class BoardViewTest {

	private BoardView CuT;
	private AbstractGame CG;
	private Player P1;
	private Player P2;

	@BeforeEach
	void Setup(){
		P1 = new Player("P1");
		CG = new CheckersGame(P1,P2,null);
		CuT = new BoardView(CG.flushBoard(P1),true);
	}

	@Test
	void iterator() {
		assertNotNull(CuT.iterator());
		assertEquals(Row.class,CuT.iterator().next().getClass());

	}
}
