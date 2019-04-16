package com.webcheckers.ui.route.home;

import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.model.game.AbstractGame;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.route.ValidationPostRoute;
import com.webcheckers.util.Message;
import com.webcheckers.util.ViewMode;

import spark.Request;
import spark.Response;

/**
 * The UI Controller for the Home page, containing both GET and POST behavior.
 *
 * @author Christopher Johns
 */
public class StartGamePostRoute extends ValidationPostRoute {
	private static final Logger LOG = Logger.getLogger(StartGamePostRoute.class.getName());
	
	// query parameters (matches name attribute of input elements inside a form element in ftl files)
	private static final String MODE = "viewMode"; // replay or play (spectate is enforced if play fails)
	private static final String OPPONENT_PARAM = "opponent";
	private static final String REPLAY_ID_PARAM = "replay";
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST /} HTTP requests,
	 * which are used to start a game with another player.
	 * 
	 * @param homeGetRoute the GET route to render upon erroneous input
	 */
	public StartGamePostRoute(HomeGetRoute homeGetRoute) {
		super(homeGetRoute);
	}
	
	@Override
	public Object handle(Request request, Response response) {
		LOG.finer("StartGamePostRoute is invoked.");
		
		// check if the user is signed in
		Player player = request.session().attribute(WebServer.PLAYER_ATTR);
		
		if(player == null) // cannot start game if player isn't signed in
			return redirect(response, WebServer.HOME_URL);
		
		ViewMode mode = ViewMode.valueOf(request.queryParams(MODE));
		
		String error;
		if(mode == ViewMode.PLAY) {
			// get name of player that we want to start a game with
			String opponent = request.queryParams(OPPONENT_PARAM);
			
			// attempt to start a game with the given player; a String is returned that represents a possible error.
			error = getPlayerLobby().tryStartGame(player, opponent);
		}
		else if(mode == ViewMode.REPLAY) {
			int id = Integer.parseInt(request.queryParams(REPLAY_ID_PARAM));
			
			error = getPlayerLobby().tryStartReplay(player, id);
		}
		else
			return refreshWithMessage(player, response, Message.error("Game mode is not known."));
		
		// if no error...
		if(Objects.equals(error, PlayerLobby.NO_ERROR)) {
			// success
			AbstractGame game = getPlayerLobby().getCurrentGame(player);
			if(game == null) // shouldn't happen
				return redirect(response, WebServer.HOME_URL);
			
			ViewMode viewMode = game.getViewMode(player);
			return redirect(response, WebServer.getGamePath(viewMode));
		}
		
		// failed to join/start game.
		return refreshWithMessage(player, response, Message.error(error));
	}
}
