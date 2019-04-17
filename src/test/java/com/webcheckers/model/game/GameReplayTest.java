package com.webcheckers.model.game;

import java.util.List;

import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.model.TurnReplay;
import com.webcheckers.util.Message.Type;

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
class GameReplayTest {

	private GameReplay CuT;
	private GameReplayData GRD;
	private Player redPlayer;
	private Player whitePlayer;
	private List<TurnReplay> turnReplays;

	private CheckersGame CG;
	private TurnReplay[] moves;
	private int curMove;

	@BeforeEach
	void setUp() {
		redPlayer = new Player("P1");
		whitePlayer = new Player("P2");
		CG = new CheckersGame(redPlayer,whitePlayer,null);

		CG.validateMove(new Move(new Position(5,0),new Position(4,1)),
				CG.activePlayer);
		CG.submitTurn(CG.activePlayer);
		GRD = CG.generateReplayData();
		CuT = new GameReplay(GRD);


	}
	
	@AfterEach
	void tearDown() {

	}
	
	@Test
	void hasNextMove() {
		assertTrue(CuT.hasNextMove());
		CuT.playNextMove();
		assertFalse(CuT.hasNextMove());
	}
	
	@Test
	void playNextMove() {
		assertEquals(Type.INFO,CuT.playNextMove().getType());
		CuT.playNextMove().getType();

		assertEquals(Type.ERROR, CuT.playNextMove().getType());
	}
	
	@Test
	void hasPreviousMove() {
		assertFalse(CuT.hasPreviousMove());
		CuT.playNextMove();
		assertTrue(CuT.hasPreviousMove());

	}
	
	@Test
	void undoMove() {
		assertEquals(Type.ERROR,CuT.undoMove().getType());
		CuT.playNextMove();
		assertEquals(Type.INFO,CuT.undoMove().getType());

	}
	
	@Test
	void isGameOver() {
		assertTrue(CuT.isGameOver());
	}
	
	@Test
	void getGameOverMessage() {
		assertNull(CuT.getGameOverMessage());
	}
	
	@Test
	void getViewMode() {
		assertNotNull(CuT.getViewMode(CG.activePlayer));
	}

	@Test
	void flushBoard() {
		assertNotNull(CuT.flushBoard(CG.activePlayer));
	}
}
