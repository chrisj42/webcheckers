import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.ui.TemplateEngineTester;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.route.home.SignInGetRoute;
import org.junit.Before;

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

@Tag("ui-tier")
public class SignInGetRouteTest {

  private SignInGetRoute CuT;

  private Request request;
  private Session session;
  private Response response;
  private TemplateEngine engine;
  private Gson gson;
  private WebServer webServer;
  private PlayerLobby playerLobby;

  @Before
  public void setup(){

    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    response = mock(Response.class);
    engine = mock(TemplateEngine.class);
    playerLobby = mock(PlayerLobby.class);


    CuT = new SignInGetRoute(WebServer.SIGN_IN_VIEW,playerLobby,engine);

  }

  @Test
  public void new_Login(){
    final TemplateEngineTester testHelper = new TemplateEngineTester();


    CuT.handle(request,response);

    testHelper.assertViewModelExists();

  }

}