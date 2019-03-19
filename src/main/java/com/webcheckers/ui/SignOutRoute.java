package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Session;

public class SignOutRoute extends CheckersRoute implements CheckersPostRoute {
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST /signout} HTTP requests.
	 *
	 * @param playerLobby    the application-tier player manager
	 */
	SignOutRoute(PlayerLobby playerLobby) {
		super(playerLobby);
	}
	
	@Override
	public Object post(Request request, Response response) {
		final Session session = request.session();
		
		// logout the player if logged in
		Player player = session.attribute(WebServer.PLAYER_ATTR);
		if(player != null)
			getPlayerLobby().logoutPlayer(player.getName());
		
		// remove player from session object
		session.removeAttribute(WebServer.PLAYER_ATTR);
		
		// redirect to home screen
		return redirect(response, WebServer.HOME_URL);
	}
}
