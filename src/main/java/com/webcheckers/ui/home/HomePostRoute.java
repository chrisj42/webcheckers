package com.webcheckers.ui.home;

import java.util.logging.Logger;

import com.webcheckers.model.Player;
import com.webcheckers.ui.CheckersPostWebRoute;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;

/**
 * The UI Controller for the Home page, containing both GET and POST behavior.
 *
 * @author Christopher Johns
 */
public class HomePostRoute extends CheckersPostWebRoute {
	private static final Logger LOG = Logger.getLogger(HomePostRoute.class.getName());
	
	// query parameters (matches name attribute of input elements inside a form element in ftl files)
	private static final String OPPONENT_PARAM = "opponent";
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST /} HTTP requests,
	 * which are used to start a game with another player.
	 * 
	 * @param homeGetRoute the GET route to render upon erroneous input
	 */
	public HomePostRoute(HomeGetRoute homeGetRoute) {
		super(homeGetRoute);
	}
	
	@Override
	public Object handle(Request request, Response response) {
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
