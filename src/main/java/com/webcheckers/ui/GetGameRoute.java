package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Color;
import com.webcheckers.model.Player;
import com.webcheckers.ui.board.BoardView;
import com.webcheckers.util.TemplateMap;
import com.webcheckers.util.ViewMode;
import spark.*;

public class GetGameRoute extends CheckersGetRoute {
	private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{GET /game} HTTP requests.
	 *
	 * @param viewName       name of the ftl view that this route renders
	 * @param playerLobby    the application-tier player manager
	 * @param templateEngine the HTML template rendering engine
	 */
	GetGameRoute(String viewName, PlayerLobby playerLobby, TemplateEngine templateEngine) {
		super(viewName, playerLobby, templateEngine);
	}
	
	@Override
	protected TemplateMap loadTemplate(Player player, Response response) {
		LOG.finer("GetGameRoute is invoked.");
		
		// if player is not logged in, or not in a game, then redirect to home screen
		if(player == null || !getPlayerLobby().hasGame(player))
			return redirect(response, WebServer.HOME_URL);
		
		// get current game
		CheckersGame game = getPlayerLobby().getCurrentGame(player);
		// Player opponent = game.getOpponent(player);
		
		// TODO (finish in sprint 2) else player is in a game; fill with params; fetch current game model to do so
		
		TemplateMap map = new TemplateMap();
		map.put("currentUser", player);
		map.put("redPlayer", game.getRedPlayer());
		map.put("whitePlayer", game.getWhitePlayer());
		
		map.put("activeColor", Color.RED); // replace with actual active player
		
		map.put("viewMode", ViewMode.PLAY); // replace with actual view mode
		
		map.put("board", new BoardView(game.getBoard(), game.isPlayer1(player))); // pass board array from model to BoardView constructor
		
		return map;
	}
}
