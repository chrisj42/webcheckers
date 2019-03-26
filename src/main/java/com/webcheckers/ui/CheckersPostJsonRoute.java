package com.webcheckers.ui;

import java.util.Objects;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;

public abstract class CheckersPostJsonRoute extends CheckersRoute {
	
	private final Gson gson;
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST} HTTP requests
	 * that send back a json object.
	 *
	 * @param playerLobby the application-tier player manager
	 * @param gson The Google JSON parser object used to render Ajax responses.   
	 */
	protected CheckersPostJsonRoute(PlayerLobby playerLobby, Gson gson) {
		super(playerLobby);
		Objects.requireNonNull(gson, "gson cannot be null");
		this.gson = gson;
	}
	
	protected Gson getGson() { return gson; }
}
