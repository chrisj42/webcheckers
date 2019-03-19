package com.webcheckers.ui;

import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import com.webcheckers.util.TemplateMap;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

/**
 * The UI Controller for the Home page, containing both GET and POST behavior.
 *
 * @author Christopher Johns
 */
public class HomeRoute extends CheckersGetRoute implements CheckersPostRoute {
	private static final Logger LOG = Logger.getLogger(HomeRoute.class.getName());
	
	private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
	
	// query parameters (matches name attribute of input elements inside a form element in ftl files)
	private static final String OPPONENT_PARAM = "opponent";
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{GET /} HTTP requests.
	 *
	 * @param viewName       name of the ftl view that this route renders
	 * @param playerLobby    the application-tier player manager
	 * @param templateEngine the HTML template rendering engine
	 */
	HomeRoute(String viewName, PlayerLobby playerLobby, TemplateEngine templateEngine) {
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
	
	@Override
	public Object post(Request request, Response response) {
		LOG.finer("PostHomeRoute is invoked.");
		
		// check if the user is signed in
		Player player = request.session().attribute(WebServer.PLAYER_ATTR);
		
		if(player == null) // cannot start game if player isn't signed in
			return redirect(response, WebServer.HOME_URL);
		
		// get name of player that we want to start a game with
		String opponent = request.queryParams(OPPONENT_PARAM);
		
		// make PlayerLobby calls to determine if the given opponent can play with the current player. Do management stuff and make return a boolean.
		if(getPlayerLobby().startGame(player.getName(), opponent))
			return redirect(response, WebServer.GAME_URL);
		
		// failed to join game.
		return refreshWithMessage(player, response, Message.error("Player is already in a game."));
	}
}
