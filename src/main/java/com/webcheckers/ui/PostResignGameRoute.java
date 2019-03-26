package com.webcheckers.ui;

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




public class PostResignGameRoute extends CheckersPostJsonRoute {

    PlayerLobby playerLobby;

	PostResignGameRoute(PlayerLobby playerLobby, Gson gson) {
        super(playerLobby, gson);
	}


    @Override
	public Object handle(Request request, Response response) {
        Message message;
		Session session = request.session();

		Player player = session.attribute(WebServer.PLAYER_ATTR);
        if(player == null) // not logged in
            return redirect(response, WebServer.HOME_URL);


        CheckersGame game = getPlayerLobby().getCurrentGame(player);
        if(game == null) // no current game
            return redirect(response, WebServer.HOME_URL);


        message = game.resignGame(player);
        if(message.isSuccessful()) {
            playerLobby.endGame(player);
            return redirect(response, WebServer.HOME_URL);
        }
        else{
            return getGson().toJson(message, Message.class);
        }
	}
}
