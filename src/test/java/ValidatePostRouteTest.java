import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayArchive;
import com.webcheckers.model.Player;
import com.webcheckers.model.game.CheckersGame;
import com.webcheckers.ui.TemplateEngineTester;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.route.game.ValidatePostRoute;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

@Tag("game-tier")
public class ValidatePostRouteTest {

  private ValidatePostRoute CuT;

  private WebServer WS;

  // friendly objects
  private PlayerLobby playerLobby;
  private CheckersGame checkersGame;

  // attributes holding mock objects
  private Request request;
  private Session session;
  private Response response;
  private TemplateEngine engine;

  private String Player1 = "P1";
  private String Player2 = "P2";


  private String VALID_MOVE;

  /**
   * Setup new mock objects for each test.
   */
  @BeforeEach
  void setUp() {

    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    engine = mock(TemplateEngine.class);
    response = mock(Response.class);

    //build the sercie and model objects
    //the GameCenter and GuessingGame are friendly
    ReplayArchive archive = new ReplayArchive();
    playerLobby = new PlayerLobby(archive, false);
    PlayerLobby playerLobby= mock(PlayerLobby.class);
    Player P1 = new Player(Player1);
    Player P2 = new Player(Player2);
    checkersGame = new CheckersGame(P1,P2, null);

    final Gson gson = new Gson();
    WS = new WebServer(playerLobby,archive,engine,gson);


    when(playerLobby.getCurrentGame(P1)).thenReturn(checkersGame);
    // store in the session
    when(session.attribute(WebServer.PLAYER_LOBBY_KEY)).thenReturn(playerLobby);

    //create a unique CuT for each test
    CuT = new ValidatePostRoute(playerLobby,gson);

  }


  @Test
  public void validMove(){

    // Arrange test scenario: Valid move made.
    when(request.queryParams(eq("actionData"))).thenReturn(VALID_MOVE);

    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    //Invoke the test
    CuT.handle(checkersGame.getRedPlayer(),checkersGame,request,response);

    //Analyze the results:
    //   * model is a non-null Map
    testHelper.assertViewModelExists();

    testHelper.assertViewModelIsaMap();

    //   * model contains all necessary View-Model data
    testHelper.assertViewModelAttribute(WebServer.PLAYER_ATTR, Boolean.FALSE);

    //testHelper.assertViewModelAttribute(WebServer.MESSAGE_KEY,WebServer.);


  }

  @Test
  void handle1() {


  }
}
