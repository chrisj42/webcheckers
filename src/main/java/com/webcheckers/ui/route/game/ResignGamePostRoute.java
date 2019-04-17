package com.webcheckers.ui.route.game;

import java.util.Objects;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayArchive;
import com.webcheckers.model.Player;
import com.webcheckers.model.game.CheckersGame;
import com.webcheckers.util.Message;
import com.webcheckers.util.ViewMode;

import spark.Request;
import spark.Response;

import com.google.gson.Gson;

public class ResignGamePostRoute extends GameUpdatePostRoute {
	
	private final ReplayArchive replayArchive;
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST} HTTP requests
	 * that send back a json object.
	 *
	 * @param playerLobby    the application-tier player manager
	 * @param replayArchive  the application-tier container for finished, replayable games   
	 * @param gson           The Google JSON parser object used to render Ajax responses.
	 */
	public ResignGamePostRoute(PlayerLobby playerLobby, ReplayArchive replayArchive, Gson gson) {
		super(playerLobby, gson, ViewMode.PLAY);
		Objects.requireNonNull(replayArchive, "replayArchive must not be null");
		this.replayArchive = replayArchive;
	}
	
	@Override
	public Message handle(Player player, CheckersGame game, Request request, Response response) {
		boolean alreadyOver = game.isGameOver();
		Message msg = game.resignGame(player);
		if(msg.isSuccessful()) {
			if(!alreadyOver)
				replayArchive.addGame(game.generateReplayData());
			getPlayerLobby().endGame(player, ViewMode.PLAY);
		}
		return msg;
	}
}
