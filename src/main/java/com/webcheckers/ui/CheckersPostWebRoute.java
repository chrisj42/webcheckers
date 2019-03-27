package com.webcheckers.ui;

import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import com.webcheckers.util.TemplateMap;
import spark.Response;

public abstract class CheckersPostWebRoute extends CheckersRoute {
	
	private final CheckersGetRoute getRoute;
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST} HTTP requests
	 * that can re-render a web page with an error message.
	 *
	 * @param getRoute the GET route to render upon erroneous input
	 */
	protected CheckersPostWebRoute(CheckersGetRoute getRoute) {
		super(getRoute.getPlayerLobby());
		this.getRoute = getRoute;
	}
	
	protected Object refreshWithMessage(Player player, Response response, Message message) {
		TemplateMap map = getRoute.get(player, response);
		if(map != null)
			map.put(WebServer.MESSAGE_KEY, message);
		
		return getRoute.render(map);
	}
}
