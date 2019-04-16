package com.webcheckers.ui;


import static org.junit.jupiter.api.Assertions.*;


import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-Tier")
public class TestPlayer {

  private String NAME = "PlayerOne";
  private String INVALIDNAME = "FakeName";

  @Test
  public void testGetName(){
    Player P1 = new Player(NAME);
    //Check to see if the name returned is correct
    assertEquals(NAME,P1.getName());

    //Check to see if invalid names are caught.
    assertNotEquals(INVALIDNAME,P1.getName());
  }

}
