package com.webcheckers.ui.route;

import java.util.Objects;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.model.game.AbstractGame;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Message;
import com.webcheckers.util.ViewMode;

import spark.Request;
import spark.Response;
import spark.Session;

import com.google.gson.Gson;

public abstract class AjaxMessagePostRoute extends CheckersRoute {
	
	private final Gson gson;
	private final ViewMode requiredViewMode;
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST} HTTP requests
	 * that interface with a game of checkers in some way. Called as ajax requests
	 * by the client code to update or query some aspect of the game; a json-serialized
	 * Message object is expected in return.
	 *
	 * @param playerLobby the application-tier player manager
	 * @param gson The Google JSON parser object used to render Ajax responses
	 * @param requiredViewMode the game view mode a player must have to make
	 *                         requests on this route, or null if the view mode doesn't matter
	 */
	protected AjaxMessagePostRoute(PlayerLobby playerLobby, Gson gson, ViewMode requiredViewMode) {
		super(playerLobby);
		Objects.requireNonNull(gson, "gson cannot be null");
		this.gson = gson;
		this.requiredViewMode = requiredViewMode;
	}
	
	protected Gson getGson() {
		return gson;
	}
	
	protected abstract Message handle(Player player, AbstractGame game, Request request, Response response);
	
	@Override
	public Object handle(Request request, Response response) {
		Session session = request.session();
		Player player = session.attribute(WebServer.PLAYER_ATTR);
		AbstractGame game = getPlayerLobby().getCurrentGame(player);
		
		Message msg;
		
		if(player == null) // not logged in
			msg = Message.error("You're not signed in!");
		else if(game == null) // no registered game
			msg = Message.error("You are not currently part of a game.");
		else if(requiredViewMode != null && game.getViewMode(player) != requiredViewMode)
			// wrong view mode / game type
			msg = Message.error("Your request is not valid for your current game mode.");
		else // valid, delegate to subclass
			msg = handle(player, game, request, response);
		
		// serialize and send back to client
		return gson.toJson(msg, Message.class);
	}
}
