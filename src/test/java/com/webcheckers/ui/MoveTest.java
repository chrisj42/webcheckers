package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Move;
import com.webcheckers.model.Position;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-teir")
public class MoveTest {

  private static Position redMoveOne = new Position(5,0);

  // Used for normal move
  private static Position redMoveTwo = new Position(4,1);

  // Used for jump move
  private static Position redMoveThree = new Position(3,2);

  private static Position invalidRedOne = new Position(6,1);
  private static Position invalidRedTwo = new Position(5,1);

  @Test
  public void testValidMove(){

    final Move redMoveNormal = new Move(redMoveOne,redMoveTwo);

    //Checks if normal move is valid
    assertTrue(redMoveNormal.isValid());

    final Move redMoveJump = new Move(redMoveOne,redMoveThree);

    //Checks if jump move is valid
    assertTrue(redMoveJump.isValid());
  }

  @Test
  public void testInvalidMove(){
    final Move redMove = new Move(invalidRedOne,invalidRedTwo);

    //Tests to tsee if the moving to an invalid space is caught
    assertFalse(redMove.isValid());
  }

  @Test
  public void testGetStart(){
    final Move redMove = new Move(redMoveOne,redMoveTwo);

    //Tests to see if the starting position is correctly stored and checked.
    assertTrue(redMove.getStart().equals(redMoveOne));
  }

  @Test
  public void testGetEnd(){
    final Move redMove = new Move(redMoveOne,redMoveTwo);

    //Tests to see if the end position is correctly stored and checked
    assertTrue(redMove.getEnd().equals(redMoveTwo));
  }

  @Test
  public void testIsJump(){
    final Move redMoveJump = new Move(redMoveOne,redMoveThree);

    //Checks that jump moves are checked
    assertTrue(redMoveJump.isJump());
  }

  @Test
  public void testInvalidIsJump(){
    final Move redMove = new Move(redMoveOne,redMoveTwo);

    //Checks that none jump moves are caught
    assertFalse(redMove.isJump());
  }

  @Test
  public void testGetJumpPos(){
    final Move redMove = new Move(redMoveOne,redMoveThree);

    //Checks that the position returned by getJumpPos is the correct position.
    assertEquals(redMove.getJumpPos(),redMoveTwo);
  }

  @Test
  public void testInvalidGetJumpPos(){
    final Move redMove = new Move(redMoveOne,redMoveThree);

    //Checks that the position checks for incorrect jump positions.
    assertNotEquals(redMove.getJumpPos(),redMoveThree);
  }


}
