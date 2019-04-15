package com.webcheckers.ui.route.spectate;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.game.CheckersGame;
import com.webcheckers.model.game.AbstractGame;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.route.LiveGameGetRoute;
import com.webcheckers.util.ViewMode;

import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import com.google.gson.Gson;

public class SpectateGameGetRoute extends LiveGameGetRoute {
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{GET /spectator/game} HTTP requests.
	 *
	 * @param playerLobby    the application-tier player manager
	 * @param templateEngine the HTML template rendering engine
	 * @param gson           The Google JSON parser object used to render Ajax responses.
	 */
	public SpectateGameGetRoute(PlayerLobby playerLobby, TemplateEngine templateEngine, Gson gson) {
		super(ViewMode.SPECTATOR, playerLobby, templateEngine, gson);
	}
	
	@Override
	public Object handle(Request request, Response response) {
		// store the result of normal runtime first
		Object res = super.handle(request, response);
		
		// check if the request successfully returned a webpage, i.e. there is a valid game in progress
		if(res == null)
			return null;
		
		// fetch the game
		Session session = request.session();
		AbstractGame game = getPlayerLobby().getCurrentGame(session.attribute(WebServer.PLAYER_ATTR));
		if(!(game instanceof CheckersGame)) // should never happen if it already validated, but just in case.
			return res;
		
		// store the color of the active player, for later use by the check turn route
		session.attribute(WebServer.SUBMIT_TIME_ATTR, ((CheckersGame)game).getLastSubmitTime());
		
		// return original result/page
		return res;
	}
}
