package com.webcheckers.ui.game;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.ui.CheckersPostJsonRoute;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Session;

abstract class CheckersPostGameRoute extends CheckersPostJsonRoute {
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST} HTTP requests
	 * that send back a json object.
	 *
	 * @param playerLobby the application-tier player manager
	 * @param gson The Google JSON parser object used to render Ajax responses.   
	 */
	CheckersPostGameRoute(PlayerLobby playerLobby, Gson gson) {
		super(playerLobby, gson);
	}
	
	protected abstract Message handle(Player player, CheckersGame game, Request request, Response response);
	
	@Override
	public Object handle(Request request, Response response) {
		Session session = request.session();
		
		Player player = session.attribute(WebServer.PLAYER_ATTR);
		if(player == null) // not logged in
			return redirect(response, WebServer.HOME_URL);
		
		CheckersGame game = getPlayerLobby().getCurrentGame(player);
		if(game == null) // no current game
			return redirect(response, WebServer.HOME_URL);
		
		Message msg = handle(player, game, request, response);
		return getGson().toJson(msg, Message.class);
	}
}
