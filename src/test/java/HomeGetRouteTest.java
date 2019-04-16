import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayArchive;
import com.webcheckers.ui.TemplateEngineTester;
import com.webcheckers.ui.route.home.HomeGetRoute;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-Tier")
public class HomeGetRouteTest {
  /**
   * The component-under-test (CuT)
   *
   */
  private HomeGetRoute CuT;

  private PlayerLobby playerLobby;
  // mock objects
  private Request request;
  private Session session;
  private TemplateEngine engine;
  private Response response;

  private static final String HOME_VIEW = "home.ftl";




  @BeforeEach
  public void setup(){
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    response = mock(Response.class);
    engine = mock(TemplateEngine.class);

    // create a unique CuT for each test
    // the GameCenter is friendly but the engine mock will need configuration
    ReplayArchive archive = new ReplayArchive();
    playerLobby = new PlayerLobby(archive, false);
    CuT = new HomeGetRoute(playerLobby, archive, engine);
  }

  @Test
  public void new_session(){

    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    CuT.handle(request,response);

    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();
    //    * model contains all necessary View-Model data
    //testHelper.assertViewModelAttribute(WebServer.MESSAGE_KEY,WebServer.);


  }



}
