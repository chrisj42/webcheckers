package com.webcheckers.ui.route.home;

import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.model.game.AbstractGame;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.route.CheckersGetRoute;
import com.webcheckers.util.Message;
import com.webcheckers.util.TemplateMap;
import com.webcheckers.util.ViewMode;

import spark.Response;
import spark.TemplateEngine;

/**
 * The UI Controller for the Home page, containing GET behavior.
 */
public class HomeGetRoute extends CheckersGetRoute {
	private static final Logger LOG = Logger.getLogger(HomeGetRoute.class.getName());
	
	private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{GET /} HTTP requests.
	 *
	 * @param playerLobby    the application-tier player manager
	 * @param templateEngine the HTML template rendering engine
	 */
	public HomeGetRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
		super(WebServer.HOME_VIEW, playerLobby, templateEngine);
	}
	
	@Override
	protected TemplateMap get(Player player, Response response) {
		LOG.finer("HomeGetRoute is invoked.");
		
		TemplateMap map = new TemplateMap();
		
		if(player != null) {
			// check if player is already in a game (ask PlayerLobby); if so, redirect to /game and return, else continue
			AbstractGame game = getPlayerLobby().getCurrentGame(player);
			if(game != null) {
				if(game.isGameOver())
					getPlayerLobby().endGame(player);
				else {
					ViewMode viewMode = game.getViewMode(player);
					return redirect(response, WebServer.getGamePath(viewMode));
				}
			}
			
			// add user object
			map.put(WebServer.USER_KEY, player);
		}
		
		// display a user message in the Home page
		map.put(WebServer.MESSAGE_KEY, WELCOME_MSG);
		map.put(WebServer.PLAYER_LOBBY_KEY, getPlayerLobby());
		
		return map;
	}
}
