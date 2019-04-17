package com.webcheckers.ui.route;

import java.util.Objects;

import com.webcheckers.appl.PlayerLobby;

import spark.Request;
import spark.Response;
import spark.Route;

// superclass of all Routes implemented in com.webcheckers.ui.
public abstract class CheckersRoute implements Route {
	
	private final PlayerLobby playerLobby;
	
	/**
	 * Create the Spark Route (UI controller) to handle a type of HTTP request.
	 *
	 * @param playerLobby     the application-tier player manager
	 */
	protected CheckersRoute(PlayerLobby playerLobby) {
		// validation
		Objects.requireNonNull(playerLobby, "playerLobby must not be null");
		//
		this.playerLobby = playerLobby;
	}
	
	protected PlayerLobby getPlayerLobby() {
		return playerLobby;
	}
	
	@Override
	public abstract Object handle(Request request, Response response);
	
}
