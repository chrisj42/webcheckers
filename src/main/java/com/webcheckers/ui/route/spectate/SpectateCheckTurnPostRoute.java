package com.webcheckers.ui.route.spectate;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.model.game.CheckersGame;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.route.game.GameUpdatePostRoute;
import com.webcheckers.util.Message;
import com.webcheckers.util.ViewMode;

import spark.Request;
import spark.Response;
import spark.Session;

import com.google.gson.Gson;

public class SpectateCheckTurnPostRoute extends GameUpdatePostRoute {
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST /spectator/checkTurn}
	 * HTTP requests.
	 *
	 * @param playerLobby the application-tier player manager
	 * @param gson        The Google JSON parser object used to render Ajax responses
	 */
	public SpectateCheckTurnPostRoute(PlayerLobby playerLobby, Gson gson) {
		super(playerLobby, gson, ViewMode.SPECTATOR);
	}
	
	@Override
	public Message handle(Player player, CheckersGame game, Request request, Response response) {
		Session session = request.session();
		
		long cachedUpdate = session.attribute(WebServer.SUBMIT_TIME_ATTR);
		long lastUpdate = game.getLastSubmitTime();
		
		// refresh the page if there's been an update since initial page load.
		boolean refresh = lastUpdate > cachedUpdate;
		
		if(refresh)
			// remove the attribute since it is no longer needed
			session.removeAttribute(WebServer.SUBMIT_TIME_ATTR);
		
		return Message.info(String.valueOf(refresh));
	}
}
