package com.webcheckers.ui.route;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.model.game.AbstractGame;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.board.BoardView;
import com.webcheckers.util.TemplateMap;
import com.webcheckers.util.ViewMode;

import spark.Response;
import spark.TemplateEngine;

import com.google.gson.Gson;

public abstract class GameGetRoute extends CheckersGetRoute {
	private static final Logger LOG = Logger.getLogger(GameGetRoute.class.getName());
	
	private final Gson gson;
	private final ViewMode viewMode;
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{GET} HTTP requests that
	 * render the game view.
	 *
	 * @param viewMode       game mode that this route renders to.
	 * @param playerLobby    the application-tier player manager
	 * @param templateEngine the HTML template rendering engine
	 * @param gson           The Google JSON parser object used to render Ajax responses.   
	 */
	protected GameGetRoute(ViewMode viewMode, PlayerLobby playerLobby, TemplateEngine templateEngine, Gson gson) {
		super(WebServer.GAME_VIEW, playerLobby, templateEngine);
		Objects.requireNonNull(viewMode, "ViewMode cannot be null");
		Objects.requireNonNull(gson, "Gson cannot be null");
		this.viewMode = viewMode;
		this.gson = gson;
	}
	
	@Override
	protected TemplateMap get(Player player, Response response) {
		LOG.finer("GameGetRoute is invoked.");
		
		// get current game
		AbstractGame game = getPlayerLobby().getCurrentGame(player);
		
		// if player is not logged in, not in a game, or in a game of the wrong type, then redirect to home screen
		if(game == null || game.getViewMode(player) != viewMode)
			return redirect(response, WebServer.HOME_URL);
		
		TemplateMap map = new TemplateMap();
		
		map.put("currentUser", player);
		map.put("redPlayer", game.getRedPlayer());
		map.put("whitePlayer", game.getWhitePlayer());
		
		map.put("activeColor", game.getActiveColor());
		
		map.put("viewMode", viewMode);
		
		// pass board array from model to BoardView constructor
		map.put("board", new BoardView(game.flushBoard(player), game.isPlayer1(player)));
		
		map.put("modeOptionsAsJSON", gson.toJson(getModeOptions(game)));

		return map;
	}
	
	protected abstract Map<String, Object> getModeOptions(AbstractGame game);
}
