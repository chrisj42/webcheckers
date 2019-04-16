package com.webcheckers.ui.route.replay;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.model.game.GameReplay;
import com.webcheckers.util.Message;

import spark.Request;
import spark.Response;

import com.google.gson.Gson;

public class ReplayPrevPostRoute extends ReplayMovePostRoute {
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST} HTTP requests
	 * that interface with a game of checkers in some way. Called as ajax requests
	 * by the client code to update or query some aspect of the game; a json-serialized
	 * Message object is expected in return.
	 *
	 * @param playerLobby the application-tier player manager
	 * @param gson        The Google JSON parser object used to render Ajax responses
	 */
	public ReplayPrevPostRoute(PlayerLobby playerLobby, Gson gson) {
		super(playerLobby, gson);
	}
	
	@Override
	protected Message handle(Player player, GameReplay game, Request request, Response response) {
		return game.undoMove();
	}
}
