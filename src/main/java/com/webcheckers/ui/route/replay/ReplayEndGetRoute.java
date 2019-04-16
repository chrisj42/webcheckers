package com.webcheckers.ui.route.replay;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.route.CheckersWebRoute;
import com.webcheckers.util.ViewMode;

import spark.Request;
import spark.Response;

public class ReplayEndGetRoute extends CheckersWebRoute {
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{GET /replay/stopWatching} HTTP requests.
	 *
	 * @param playerLobby the application-tier player manager
	 */
	public ReplayEndGetRoute(PlayerLobby playerLobby) {
		super(playerLobby);
	}
	
	@Override
	public Object handle(Request request, Response response) {
		Player p = request.session().attribute(WebServer.PLAYER_ATTR);
		getPlayerLobby().endGame(p, ViewMode.REPLAY);
		return redirect(response, WebServer.HOME_URL);
	}
}
