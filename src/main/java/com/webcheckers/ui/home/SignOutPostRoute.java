package com.webcheckers.ui.home;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.ui.CheckersRoute;
import com.webcheckers.ui.WebServer;
import spark.Request;
import spark.Response;
import spark.Session;

public class SignOutPostRoute extends CheckersRoute {
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST /signout} HTTP requests,
	 * which are used to sign out of an account.
	 *
	 * @param playerLobby    the application-tier player manager
	 */
	public SignOutPostRoute(PlayerLobby playerLobby) {
		super(playerLobby);
	}
	
	@Override
	public Object handle(Request request, Response response) {
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
