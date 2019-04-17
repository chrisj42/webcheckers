package com.webcheckers.ui.route.game;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.model.game.AbstractGame;
import com.webcheckers.model.game.CheckersGame;
import com.webcheckers.ui.route.AjaxMessagePostRoute;
import com.webcheckers.util.Message;
import com.webcheckers.util.ViewMode;

import spark.Request;
import spark.Response;

import com.google.gson.Gson;

public abstract class GameUpdatePostRoute extends AjaxMessagePostRoute {
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST} HTTP requests
	 * that interface with a live game of checkers in some way.
	 *
	 * @param playerLobby      the application-tier player manager
	 * @param gson             The Google JSON parser object used to render Ajax responses
	 * @param requiredViewMode the game view mode a player must have to make
	 */
	protected GameUpdatePostRoute(PlayerLobby playerLobby, Gson gson, ViewMode requiredViewMode) {
		super(playerLobby, gson, requiredViewMode);
	}
	
	protected abstract Message handle(Player player, CheckersGame game, Request request, Response response);
	
	@Override
	protected Message handle(Player player, AbstractGame game, Request request, Response response) {
		if(!(game instanceof CheckersGame))
			return Message.error("invalid game type.");
		
		return handle(player, (CheckersGame)game, request, response);
	}
}
