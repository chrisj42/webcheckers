package com.webcheckers.ui.route;

import com.webcheckers.TemplateEngineTester;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayArchive;
import com.webcheckers.model.Player;
import com.webcheckers.model.game.CheckersGame;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.ViewMode;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
	
	@BeforeEach
	public void setup() {
		
		request = mock(Request.class);
		session = mock(Session.class);
		when(request.session()).thenReturn(session);
		response = mock(Response.class);
		engine = mock(TemplateEngine.class);
		
		gson = new Gson();
		ReplayArchive archive = new ReplayArchive();
		playerLobby = new PlayerLobby(archive, false);
		
		
		playerLobby.logoutPlayer("Player1");
		playerLobby.logoutPlayer("Player2");
		
		CG = mock(CheckersGame.class);
		
		//Create a unique CuT for each test.
		CuT = new LiveGameGetRoute(ViewMode.PLAY, playerLobby, engine, gson);
	}
	
	@Test
	public void newGameTest() {
		
		final Player P1 = playerLobby.tryLoginPlayer("Player1");
		final Player P2 = playerLobby.tryLoginPlayer("Player2");
		
		//final Player play = playerLobby.iterator().next();
		
		//final CheckersGame CG = playerLobby.getCurrentGame(P1);
		when(session.attribute(WebServer.PLAYER_LOBBY_KEY)).thenReturn(playerLobby);
		
		
		final TemplateEngineTester testHelper = new TemplateEngineTester();
		when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
		
		when(request.session()).thenReturn(session);
		when(request.session().attribute(WebServer.PLAYER_ATTR)).thenReturn(P1);
		// Player player = playerLobby.tryLoginPlayer("Player2");
		playerLobby.tryStartGame(P1,P2.getName());
		
		CuT.handle(request,response);
		
		testHelper.assertViewModelExists();
		testHelper.assertViewModelIsaMap();
		
		testHelper.assertViewName(WebServer.GAME_VIEW);
	}
	
	@Test
	void testOne() {
		
		final Player P1 = playerLobby.tryLoginPlayer("Player1");
		final Player P2 = playerLobby.tryLoginPlayer("Player2");
		
		//final Player play = playerLobby.iterator().next();
		
		//final CheckersGame CG = playerLobby.getCurrentGame(P1);
		when(session.attribute(WebServer.PLAYER_LOBBY_KEY)).thenReturn(playerLobby);
		
		
		final TemplateEngineTester testHelper = new TemplateEngineTester();
		when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
		
		when(request.session()).thenReturn(session);
		when(request.session().attribute(WebServer.PLAYER_ATTR)).thenReturn(P1);
		
		playerLobby.tryStartGame(P1,P2.getName());
		CuT.handle(request,response);
		
		//testHelper.assertViewModelAttributeIsAbsent(gson.toJson());
		
	}
}
