package com.webcheckers.ui;

import java.util.Objects;

import com.webcheckers.appl.PlayerLobby;
import spark.Response;
import spark.Spark;

// superclass of Routes that return a webpage.
public abstract class CheckersRoute {
	
	private final PlayerLobby playerLobby;
	
	/**
	 * Create the Spark Route (UI controller) to handle a type of HTTP request.
	 *
	 * @param playerLobby     the application-tier player manager
	 */
	CheckersRoute(PlayerLobby playerLobby) {
		// validation
		Objects.requireNonNull(playerLobby, "playerLobby must not be null");
		//
		this.playerLobby = playerLobby;
	}
	
	protected PlayerLobby getPlayerLobby() {
		return playerLobby;
	}
	
	public static <T> T redirect(Response response, String location) {
		response.redirect(location);
		Spark.halt();
		return null;
	}
	
}
