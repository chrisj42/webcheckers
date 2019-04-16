package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import com.webcheckers.model.Color;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Type;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(" Model-Tier")
public class TestPiece {

  private Type type;
  private static String PLAYER1 = "P1";
  private static String PLAYER2 = "P2";

  @Test
  public void testGetType(){
    Piece singleChecker = new Piece(Type.SINGLE, Color.RED);
    Piece kingChecker = new Piece(Type.SINGLE,Color.RED);

    //Checks to see if the correct color is returned.
    assertEquals(singleChecker.getType(),Type.SINGLE);
    assertNotEquals(singleChecker.getType(),Type.KING);
  }

  @Test
  public void testInvalidGetType(){
    Piece singleChecker = new Piece(Type.SINGLE,Color.RED);
    Piece kingChecker = new Piece(Type.KING, Color.RED);

    assertNotEquals(kingChecker.getType(),Type.SINGLE);
    assertNotEquals(singleChecker.getType(),Type.KING);
  }

  @Test
  public void testgetColor(){
    Piece redChecker = new Piece(Type.SINGLE,Color.RED);
    Piece whiteChecker = new Piece(Type.SINGLE,Color.WHITE);

    //Checks to see if the correct
    assertEquals(redChecker.getColor(),Color.RED);
    assertEquals(whiteChecker.getColor(),Color.WHITE);
  }

  @Test
  public void testInvalidGetColor() {
    Piece redChecker = new Piece(Type.SINGLE, Color.RED);
    Piece whiteChecker = new Piece(Type.SINGLE, Color.WHITE);

    // Checks that the correct colors are returned
    assertNotEquals(redChecker.getColor(), Color.WHITE);
    assertNotEquals(whiteChecker.getColor(), Color.RED);

  }
}
