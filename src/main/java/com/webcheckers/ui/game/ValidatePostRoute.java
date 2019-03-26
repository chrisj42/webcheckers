package com.webcheckers.ui.game;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.ui.CheckersPostJsonRoute;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Session;

public class ValidatePostRoute extends CheckersPostJsonRoute {
	
	static final String MOVE_PARAM = "actionData";
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST /validateMove} HTTP requests.
	 *
	 * @param playerLobby the application-tier player manager
	 * @param gson        The Google JSON parser object used to render Ajax responses.
	 */
	public ValidatePostRoute(PlayerLobby playerLobby, Gson gson) {
		super(playerLobby, gson);
	}
	
	
	@Override
	public Object handle(Request request, Response response) {
		Session session = request.session();
		
		Player player = session.attribute(WebServer.PLAYER_ATTR);
		if(player == null) // not logged in
			return redirect(response, WebServer.HOME_URL);
		
		CheckersGame game = getPlayerLobby().getCurrentGame(player);
		if(game == null) // no current game
			return redirect(response, WebServer.HOME_URL);
		
		// parse move
		Move move = getGson().fromJson(request.queryParams(MOVE_PARAM), Move.class);
		// validate move and get message to return
		Message message = game.validateMove(move, player);
		// serialize message and return it
		return getGson().toJson(message);
	}
}
