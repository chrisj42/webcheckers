package com.webcheckers.ui.route.game;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.ui.route.LiveGameGetRoute;
import com.webcheckers.util.ViewMode;

import spark.TemplateEngine;

import com.google.gson.Gson;

public class PlayGameGetRoute extends LiveGameGetRoute {
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{GET /game} HTTP requests.
	 *
	 * @param playerLobby    the application-tier player manager
	 * @param templateEngine the HTML template rendering engine
	 * @param gson           The Google JSON parser object used to render Ajax responses.
	 */
	public PlayGameGetRoute(PlayerLobby playerLobby, TemplateEngine templateEngine, Gson gson) {
		super(ViewMode.PLAY, playerLobby, templateEngine, gson);
	}
	
}
