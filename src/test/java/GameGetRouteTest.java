import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.ui.TemplateEngineTester;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.route.game.GameGetRoute;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;



import spark.HaltException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
import org.junit.jupiter.api.Test;

public class GameGetRouteTest {


  private GameGetRoute CuT;
  private Request request;
  private Session session;
  private Response response;
  private TemplateEngine engine;
  private Gson gson;
  private WebServer webServer;
  private PlayerLobby playerLobby;
  private CheckersGame CG;
  //private Player P1;
  //private Player P2;

  /**
  private Player P1;
  private Player P2;
  private CheckersGame checkersGame;
  private PlayerLobby playerLobby;
  */
  @BeforeEach
  public void setup(){

    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    response = mock(Response.class);
    engine = mock(TemplateEngine.class);

    gson = new Gson();
    playerLobby = new PlayerLobby();


    playerLobby.tryLoginPlayer("Player1");
    playerLobby.tryLoginPlayer("Player2");

    CG = mock(CheckersGame.class);

    //Create a unique CuT for each test.
    CuT = new GameGetRoute(WebServer.GAME_VIEW,playerLobby,engine,gson);
  }

  @Test
  public void new_game(){


    playerLobby.tryLoginPlayer("Player1");
    final Player P1 = playerLobby.iterator().next();
    playerLobby.tryLoginPlayer("Player2");

    final Player P2 = playerLobby.iterator().next();

    //final Player play = playerLobby.iterator().next();

    //final CheckersGame CG = playerLobby.getCurrentGame(P1);
    when(session.attribute(WebServer.PLAYER_LOBBY_KEY)).thenReturn(playerLobby);


    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());


    when(request.session()).thenReturn(session);
    when(request.session().attribute(WebServer.PLAYER_ATTR)).thenReturn(P1);



    Player player;
    player=request.session().attribute(WebServer.PLAYER_ATTR);
    playerLobby.startGame(P1.getName(),P2.getName());

    CuT.handle(request,response);

    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();

    testHelper.assertViewName(WebServer.GAME_VIEW);
  }


  @Test
  void get() {




  }
}