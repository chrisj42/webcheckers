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

public class GameLiveGetRoute extends GameGetRoute {
	// private static final Logger LOG = Logger.getLogger(GameLiveGetRoute.class.getName());
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{GET /game} HTTP requests,
	 * and also @code{GET /spectator/game} HTTP requests.
	 *
	 * @param viewMode       game mode that this route renders to.
	 * @param playerLobby    the application-tier player manager
	 * @param templateEngine the HTML template rendering engine
	 * @param gson           The Google JSON parser object used to render Ajax responses.   
	 */
	public GameLiveGetRoute(ViewMode viewMode, PlayerLobby playerLobby, TemplateEngine templateEngine, Gson gson) {
		super(viewMode, playerLobby, templateEngine, gson);
	}
	
	@Override
	protected Map<String, Object> getModeOptions(AbstractGame game) {
		final Map<String, Object> modeOptions = new HashMap<>(2);
		modeOptions.put("isGameOver", game.isGameOver());
		modeOptions.put("gameOverMessage", game.getGameOverMessage() /* get end of game message */);
		return modeOptions;
	}
}
