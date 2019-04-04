package com.webcheckers.ui.route;

import com.webcheckers.model.Player;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Message;
import com.webcheckers.util.TemplateMap;

import spark.Response;

/**
 * A specific implementation of CheckersRoute for Post routes that
 * either redirect or return a webpage (as opposed to json).
 * 
 * Note that the browser resubmits the data when the new page is refreshed.
 */
public abstract class WebPagePostRoute extends CheckersRoute {
	
	private final CheckersGetRoute getRoute;
	
	/**
	 * Create the Spark Route (UI controller) to handle HTTP requests
	 * that can render a web page, 
	 *
	 * @param getRoute the GET route to render upon erroneous input
	 */
	protected WebPagePostRoute(CheckersGetRoute getRoute) {
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
