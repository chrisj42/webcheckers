package com.webcheckers.ui.route.home;

import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayArchive;
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
	
	private final ReplayArchive replayArchive;
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{GET /} HTTP requests.
	 *
	 * @param playerLobby    the application-tier player manager
	 * @param replayArchive  the application-tier container for finished, replayable games   
	 * @param templateEngine the HTML template rendering engine
	 */
	public HomeGetRoute(PlayerLobby playerLobby, ReplayArchive replayArchive, TemplateEngine templateEngine) {
		super(WebServer.HOME_VIEW, playerLobby, templateEngine);
		Objects.requireNonNull(replayArchive, "replayArchive must not be null");
		this.replayArchive = replayArchive;
	}
	
	@Override
	protected TemplateMap get(Player player, Response response) {
		LOG.finer("HomeGetRoute is invoked.");
		
		TemplateMap map = new TemplateMap();
		
		if(player != null) {
			// check if player is already in a game (ask PlayerLobby); if so, redirect to /game and return, else continue
			AbstractGame game = getPlayerLobby().getCurrentGame(player);
			
			if(game != null && game.isGameOver()) {
				getPlayerLobby().endGame(player, ViewMode.PLAY);
				game = getPlayerLobby().getCurrentGame(player); // refresh
			}
			
			if(game != null) {
				ViewMode viewMode = game.getViewMode(player);
				return redirect(response, WebServer.getGamePath(viewMode));
			}
			
			// add user object
			map.put(WebServer.USER_KEY, player);
		}
		
		// display a user message in the Home page
		map.put(WebServer.MESSAGE_KEY, WELCOME_MSG);
		map.put(WebServer.PLAYER_LOBBY_KEY, getPlayerLobby());
		map.put(WebServer.REPLAY_ARCHIVE_KEY, replayArchive);
		
		return map;
	}
}
