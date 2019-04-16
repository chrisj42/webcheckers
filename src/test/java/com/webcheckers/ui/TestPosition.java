package com.webcheckers.ui;


import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Position;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class TestPosition {

  private Position CELLONE = new Position(5,1);


  @Test
  public void testGetRow(){

    //Check to see if the returned row is correct
    assertEquals(CELLONE.getRow(),5);

    //Check to see if incorrect rows are caught
    assertNotEquals(CELLONE.getRow(),2);
  }


}
