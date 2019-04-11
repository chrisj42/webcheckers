package com.webcheckers.ui.route.game;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import com.webcheckers.util.ViewMode;

import spark.Request;
import spark.Response;

import com.google.gson.Gson;

public class ValidatePostRoute extends GameUpdatePostRoute {
	
	static final String MOVE_PARAM = "actionData";
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST /validateMove} HTTP requests.
	 *
	 * @param playerLobby the application-tier player manager
	 * @param gson        The Google JSON parser object used to render Ajax responses.
	 */
	public ValidatePostRoute(PlayerLobby playerLobby, Gson gson) {
		super(playerLobby, gson, ViewMode.PLAY);
	}
	
	@Override
	public Message handle(Player player, CheckersGame game, Request request, Response response) {
		// parse move
		Move move = getGson().fromJson(request.queryParams(MOVE_PARAM), Move.class);
		// validate move and return message
		return game.validateMove(move, player);
	}
}
