package com.webcheckers.ui.route.game;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.model.game.CheckersGame;
import com.webcheckers.util.Message;
import com.webcheckers.util.ViewMode;

import spark.Request;
import spark.Response;

import com.google.gson.Gson;

public class BackupPostRoute extends GameUpdatePostRoute {
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST /backupMove} HTTP requests.
	 *
	 * @param playerLobby the application-tier player manager
	 * @param gson        The Google JSON parser object used to render Ajax responses.
	 */
	public BackupPostRoute(PlayerLobby playerLobby, Gson gson) {
		super(playerLobby, gson, ViewMode.PLAY);
	}
	
	@Override
	public Message handle(Player player, CheckersGame game, Request request, Response response) {
		return game.backupMove(player);
	}
}
