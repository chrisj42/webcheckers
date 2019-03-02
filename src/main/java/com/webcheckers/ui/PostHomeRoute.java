package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

/**
 * The UI Controller to POST to Home page i.e. request a game to start.
 *
 * 
 */
public class PostHomeRoute implements Route {
	private static final Logger LOG = Logger.getLogger(PostHomeRoute.class.getName());
	
	private static final String OPPONENT_PARAM = "opponent";
	
	private final PlayerLobby playerLobby;
	private final TemplateEngine templateEngine;
	
	/**
	 * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
	 *
	 * @param templateEngine
	 *   the HTML template rendering engine
	 */
	public PostHomeRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
		this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");;
		this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
		//
		LOG.config("PostHomeRoute is initialized.");
	}
	
	/**
	 * 
	 *
	 * @param request
	 *   the HTTP request
	 * @param response
	 *   the HTTP response
	 *
	 * @return
	 *   the rendered HTML for the Home page
	 */
	@Override
	public Object handle(Request request, Response response) {
		LOG.finer("PostHomeRoute is invoked.");
		//
		//Map<String, Object> vm = new HashMap<>();
		
		Session session = request.session();
		
		// check if the user is signed in
		Player p = session.attribute(WebServer.PLAYER_ATTR);
		if(p == null) {
			// cannot start game if player isn't signed in
			response.redirect(WebServer.HOME_URL);
			Spark.halt();
			return null;
		}
		
		// get name of player that we want to start a game with
		String opponent = request.queryParams(OPPONENT_PARAM);
		
		// make PlayerLobby calls to determine if the given opponent can play with the current player. Do management stuff and make return a boolean.
		if(playerLobby.startGame(p.getName(), opponent)) {
			response.redirect(WebServer.GAME_URL);
			Spark.halt();
			return null;
		}
		
		// game could not start, other player has game in progress
		Map<String, Object> vm = new HashMap<>();
		vm.put(WebServer.USER_KEY, p);
		vm.put(WebServer.MESSAGE_KEY, Message.error("Player is already in a game."));
		vm.put(WebServer.PLAYER_LOBBY_KEY, playerLobby);
		// response.redirect(WebServer.HOME_URL);
		// Spark.halt();
		// return null;
		return templateEngine.render(new ModelAndView(vm, GetHomeRoute.VIEW_NAME));
	}
}
