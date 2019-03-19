package com.webcheckers.ui;

import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import com.webcheckers.util.TemplateMap;
import spark.*;

import static spark.Spark.halt;
import static spark.Spark.redirect;

public class SignInRoute extends CheckersGetRoute implements CheckersPostRoute {
	private static final Logger LOG = Logger.getLogger(SignInRoute.class.getName());
	
	// query parameters (matches name attribute of input elements inside a form element in ftl files)
	private static final String USERNAME_PARAM = "userName";
	
	
	/**
	 * Create the Spark Route (UI controller) to handle HTTP requests.
	 *
	 * @param viewName       name of the ftl view that this route renders
	 * @param playerLobby    the application-tier player manager
	 * @param templateEngine the HTML template rendering engine
	 */
	SignInRoute(String viewName, PlayerLobby playerLobby, TemplateEngine templateEngine) {
		super(viewName, playerLobby, templateEngine);
	}
	
	
	@Override
	protected TemplateMap loadTemplate(Player player, Response response) {
		LOG.finer("GetSignInRoute is invoked.");
		
		if(player != null) // player is already signed in
			return redirect(response, WebServer.HOME_URL);
		
		return new TemplateMap(); // nothing needs to be specified
	}
	
	@Override
	public Object post(Request request, Response response) {
		LOG.finer("PostSignInRoute is invoked.");
		
		String username = request.queryParams(USERNAME_PARAM);
		Message error = null;
		
		if(username.length() == 0)
			error = Message.error("Username must contain at least 1 character.");
		
		else if(!username.matches("[a-zA-Z0-9 ]+"))
			error = Message.error("Username can consist only of alphanumeric characters and spaces.");
		
		else if(!username.matches(".*[a-zA-Z0-9]+.*"))
			error = Message.error("Username must contain at least one alphanumeric character.");
		
		if(error == null) {
			// trim removes trailing and leading whitespace, and replaces sequential whitespace with a single instance.
			username = username.trim();
			
			Player player = getPlayerLobby().tryLoginPlayer(username);
			if(player == null)
				error = Message.error("Username '" + username + "' already taken. Please choose another.");
			else
				request.session().attribute(WebServer.PLAYER_ATTR, player);
		}
		
		// re-renders sign-in page with error message
		if(error != null)
			return renderTemplate(null, response, error);
		
		// sign-in success; back to home screen
		return redirect(response, WebServer.HOME_URL);
	}
}
