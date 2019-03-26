package com.webcheckers.ui.home;

import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.ui.CheckersGetRoute;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Message;
import com.webcheckers.util.TemplateMap;
import spark.Response;
import spark.TemplateEngine;

/**
 * The UI Controller for the Home page, containing both GET and POST behavior.
 *
 * @author Christopher Johns
 */
public class HomeGetRoute extends CheckersGetRoute {
	private static final Logger LOG = Logger.getLogger(HomeGetRoute.class.getName());
	
	private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{GET /} HTTP requests.
	 *
	 * @param viewName       name of the ftl view that this route renders
	 * @param playerLobby    the application-tier player manager
	 * @param templateEngine the HTML template rendering engine
	 */
	public HomeGetRoute(String viewName, PlayerLobby playerLobby, TemplateEngine templateEngine) {
		super(viewName, playerLobby, templateEngine);
	}
	
	@Override
	protected TemplateMap get(Player player, Response response) {
		// LOG.finer("GetHomeRoute is invoked.");
		TemplateMap map = new TemplateMap();
		
		if(player != null) {
			// check if player is already in a game (ask PlayerLobby); if so, redirect to /game and return, else continue
			if(getPlayerLobby().hasGame(player))
				return redirect(response, WebServer.GAME_URL);
			
			// add user object
			map.put(WebServer.USER_KEY, player);
		}
		
		// display a user message in the Home page
		map.put(WebServer.MESSAGE_KEY, WELCOME_MSG);
		map.put(WebServer.PLAYER_LOBBY_KEY, getPlayerLobby());
		
		return map;
	}
}
