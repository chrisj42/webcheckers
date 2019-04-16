/**package com.webcheckers.ui;


import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.game.CheckersGame;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


/**
 * The unit test suite for the {@link CheckersGame} component/
 *
 *
 */
/**@Tag("Model-tier")
public class CheckTurnTest {

  private static String PLAYER1 = "P1";
  private static String PLAYER2 = "P2";


  @Test
  public void testTurnSubmit(){
    Player P1 = new Player(PLAYER1);
    Player P2 = new Player(PLAYER2);

    final CheckersGame CG = new CheckersGame(P1,P2);
    //Check if its Player1's turn at start
    assertTrue(CG.isPlayerTurn(P1),"It is P1's turn");
    CG.validateMove(new Move(new Position(1,0),new Position(2,1)),P1);
    CG.submitTurn(P1);


    CG.validateMove(new Move(new Position(6,0),new Position(5,1)),P2);
    //CG.isPlayerTurn(P2);
    assertFalse(CG.isPlayerTurn(P1),"Not P1's turn");


  }


}*/
