package com.webcheckers.ui.route.game;

import java.util.Objects;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.model.game.AbstractGame;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.route.CheckersRoute;
import com.webcheckers.util.Message;
import com.webcheckers.util.ViewMode;

import spark.Request;
import spark.Response;
import spark.Session;

import com.google.gson.Gson;

abstract class JsonMessagePostRoute extends CheckersRoute {
	
	private final Gson gson;
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST} HTTP requests
	 * that send back a json-serialized Message object.
	 *
	 * @param playerLobby the application-tier player manager
	 * @param gson The Google JSON parser object used to render Ajax responses.   
	 */
	protected JsonMessagePostRoute(PlayerLobby playerLobby, Gson gson) {
		super(playerLobby);
		Objects.requireNonNull(gson, "gson cannot be null");
		this.gson = gson;
	}
	
	protected Gson getGson() {
		return gson;
	}
	
	protected abstract Message handle(Player player, CheckersGame game, Request request, Response response);
	
	@Override
	public Object handle(Request request, Response response) {
		Session session = request.session();
		
		Player player = session.attribute(WebServer.PLAYER_ATTR);
		if(player == null) // not logged in
			return redirect(response, WebServer.HOME_URL);
		
		AbstractGame game = getPlayerLobby().getCurrentGame(player);
		if(game == null || game.getViewMode(player) != ViewMode.PLAY) // no current game
			return redirect(response, WebServer.HOME_URL);
		
		Message msg = handle(player, (CheckersGame)game, request, response);
		return gson.toJson(msg, Message.class);
	}
}
