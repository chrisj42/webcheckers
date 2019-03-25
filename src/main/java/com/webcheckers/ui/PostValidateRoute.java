package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Session;

public class PostValidateRoute extends CheckersRoute implements CheckersPostRoute {
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST /backupMove} HTTP requests.
	 *
	 * @param playerLobby the application-tier player manager
	 */
	PostValidateRoute(PlayerLobby playerLobby) {
		super(playerLobby);
	}
	
	@Override
	public Object post(Request request, Response response) {
		Session session = request.session();
		
		Player player = session.attribute(WebServer.PLAYER_ATTR);
		
		
		CheckersGame game = getPlayerLobby().getCurrentGame(player);
	}
}
