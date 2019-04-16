package com.webcheckers.ui.route.replay;

import java.util.HashMap;
import java.util.Map;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.game.AbstractGame;
import com.webcheckers.model.game.GameReplay;
import com.webcheckers.ui.route.GameGetRoute;
import com.webcheckers.util.ViewMode;

import spark.TemplateEngine;

import com.google.gson.Gson;

public class GameReplayGetRoute extends GameGetRoute {
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{GET /replay/game} HTTP requests that
	 * render the game view.
	 *
	 * @param playerLobby    the application-tier player manager
	 * @param templateEngine the HTML template rendering engine
	 * @param gson           The Google JSON parser object used to render Ajax responses.
	 */
	public GameReplayGetRoute(PlayerLobby playerLobby, TemplateEngine templateEngine, Gson gson) {
		super(ViewMode.REPLAY, playerLobby, templateEngine, gson);
	}
	
	@Override
	protected Map<String, Object> getModeOptions(AbstractGame game) {
		final Map<String, Object> modeOptions = new HashMap<>(2);
		GameReplay gameReplay = (GameReplay) game; 
		modeOptions.put("hasNext", gameReplay.hasNextMove());
		modeOptions.put("hasPrevious", gameReplay.hasPreviousMove());
		// modeOptions.put("isGameOver", game.isGameOver());
		// modeOptions.put("gameOverMessage", game.getGameOverMessage());
		return modeOptions;
	}
}
