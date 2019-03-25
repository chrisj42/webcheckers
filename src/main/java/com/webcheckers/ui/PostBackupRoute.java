package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import spark.Request;
import spark.Response;

public class PostBackupRoute extends CheckersRoute implements CheckersPostRoute {
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST /validateMove} HTTP requests.
	 *
	 * @param playerLobby the application-tier player manager
	 */
	PostBackupRoute(PlayerLobby playerLobby) {
		super(playerLobby);
	}
	
	@Override
	public Object post(Request request, Response response) {
		return null;
	}
}
