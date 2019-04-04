package com.webcheckers.ui.route;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.board.BoardView;
import com.webcheckers.util.TemplateMap;
import com.webcheckers.util.ViewMode;

import spark.Response;
import spark.TemplateEngine;

import com.google.gson.Gson;

public class GameGetRoute extends CheckersGetRoute {
	private static final Logger LOG = Logger.getLogger(GameGetRoute.class.getName());
	
	private final Gson gson;
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{GET /game} HTTP requests.
	 *
	 * @param viewName       name of the ftl view that this route renders
	 * @param playerLobby    the application-tier player manager
	 * @param templateEngine the HTML template rendering engine
	 * @param gson           The Google JSON parser object used to render Ajax responses.   
	 */
	public GameGetRoute(String viewName, PlayerLobby playerLobby, TemplateEngine templateEngine, Gson gson) {
		super(viewName, playerLobby, templateEngine);
		Objects.requireNonNull(gson, "Gson cannot be null");
		this.gson = gson;
	}
	
	@Override
	protected TemplateMap get(Player player, Response response) {
		LOG.finer("GetGameRoute is invoked.");
		
		// if player is not logged in, or not in a game, then redirect to home screen
		if(player == null || !getPlayerLobby().hasGame(player))
			return redirect(response, WebServer.HOME_URL);
		
		// get current game
		CheckersGame game = getPlayerLobby().getCurrentGame(player);
		
		TemplateMap map = new TemplateMap();
		
		map.put("currentUser", player);
		map.put("redPlayer", game.getRedPlayer());
		map.put("whitePlayer", game.getWhitePlayer());
		
		map.put("activeColor", game.getActiveColor());
		
		map.put("viewMode", ViewMode.PLAY); // replace with actual view mode
		
		// pass board array from model to BoardView constructor
		map.put("board", new BoardView(game.flushBoard(player), player == game.getRedPlayer()));
		
		final Map<String, Object> modeOptions = new HashMap<>(2);
		modeOptions.put("isGameOver", game.isGameOver());
		modeOptions.put("gameOverMessage", game.getGameOverMessage() /* get end of game message */);
		map.put("modeOptionsAsJSON", gson.toJson(modeOptions));

		return map;
	}
}
