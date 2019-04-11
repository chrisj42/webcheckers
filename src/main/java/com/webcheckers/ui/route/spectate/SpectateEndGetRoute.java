package com.webcheckers.ui.route.spectate;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.route.CheckersWebRoute;

import spark.Request;
import spark.Response;

public class SpectateEndGetRoute extends CheckersWebRoute {
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST /spectator/stopWatching} HTTP requests.
	 *
	 * @param playerLobby the application-tier player manager
	 */
	public SpectateEndGetRoute(PlayerLobby playerLobby) {
		super(playerLobby);
	}
	
	@Override
	public Object handle(Request request, Response response) {
		Player p = request.session().attribute(WebServer.PLAYER_ATTR);
		getPlayerLobby().endGame(p);
		return redirect(response, WebServer.HOME_URL);
	}
}
