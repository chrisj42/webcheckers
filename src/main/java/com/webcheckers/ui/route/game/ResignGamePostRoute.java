package com.webcheckers.ui.route.game;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import com.webcheckers.util.ViewMode;

import spark.Request;
import spark.Response;

import com.google.gson.Gson;

public class ResignGamePostRoute extends GameUpdatePostRoute {
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST} HTTP requests
	 * that send back a json object.
	 *
	 * @param playerLobby the application-tier player manager
	 * @param gson        The Google JSON parser object used to render Ajax responses.
	 */
	public ResignGamePostRoute(PlayerLobby playerLobby, Gson gson) {
		super(playerLobby, gson, ViewMode.PLAY);
	}
	
	@Override
	public Message handle(Player player, CheckersGame game, Request request, Response response) {
		Message message = game.resignGame(player);
		if(message.isSuccessful())
			getPlayerLobby().endGame(player);
		
		return message;
	}
}
