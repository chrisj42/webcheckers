package com.webcheckers.ui.route;

import com.webcheckers.appl.PlayerLobby;

import spark.Response;
import spark.Spark;

public abstract class CheckersWebRoute extends CheckersRoute {
	
	/**
	 * Create the Spark Route (UI controller) for routes that ought
	 * to be capable of redirecting. A simple, static utility method
	 * is provided for doing so. This does not technically prevent other
	 * routes from redirecting, but if all redirects are performed through
	 * this method, then it will.
	 *
	 * @param playerLobby the application-tier player manager
	 */
	protected CheckersWebRoute(PlayerLobby playerLobby) {
		super(playerLobby);
	}
	
	protected static <T> T redirect(Response response, String location) {
		response.redirect(location);
		Spark.halt();
		return null;
	}
}
