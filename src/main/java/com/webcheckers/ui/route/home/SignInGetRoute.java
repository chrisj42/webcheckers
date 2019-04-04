package com.webcheckers.ui.route.home;

import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.ui.route.CheckersGetRoute;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.TemplateMap;
import spark.Response;
import spark.TemplateEngine;

public class SignInGetRoute extends CheckersGetRoute {
	private static final Logger LOG = Logger.getLogger(SignInGetRoute.class.getName());
	
	/**
	 * Create the Spark Route (UI controller) to handle HTTP requests.
	 *
	 * @param viewName       name of the ftl view that this route renders
	 * @param playerLobby    the application-tier player manager
	 * @param templateEngine the HTML template rendering engine
	 */
	public SignInGetRoute(String viewName, PlayerLobby playerLobby, TemplateEngine templateEngine) {
		super(viewName, playerLobby, templateEngine);
	}
	
	@Override
	protected TemplateMap get(Player player, Response response) {
		LOG.finer("GetSignInRoute is invoked.");
		
		if(player != null) // player is already signed in
			return redirect(response, WebServer.HOME_URL);
		
		return new TemplateMap(); // nothing needs to be specified
	}
}
