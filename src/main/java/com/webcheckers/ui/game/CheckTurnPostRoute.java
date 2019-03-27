package com.webcheckers.ui.game;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;

public class CheckTurnPostRoute extends CheckersPostGameRoute {
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST /checkTurn} HTTP requests.
	 *
	 * @param playerLobby the application-tier player manager
	 * @param gson        The Google JSON parser object used to render Ajax responses.
	 */
	public CheckTurnPostRoute(PlayerLobby playerLobby, Gson gson) {
		super(playerLobby, gson);
	}
	
	@Override
	public Message handle(Player player, CheckersGame game, Request request, Response response) {
		// check if it is this player's turn
		boolean isTurn = game.isPlayerTurn(player);
		return Message.info(String.valueOf(isTurn));
	}
}
