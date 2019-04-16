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
public abstract class ValidationPostRoute extends CheckersWebRoute {
	
	private final CheckersGetRoute getRoute;
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST} HTTP requests
	 * that check their input and must be able to refresh the page with an error
	 * message if the input is not valid. In order to "refresh" the page, a GET
	 * route must be provided as the page to render, and add the error message to.
	 *
	 * @param getRoute the GET route to render upon erroneous input
	 */
	protected ValidationPostRoute(CheckersGetRoute getRoute) {
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
