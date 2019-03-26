package com.webcheckers.ui.home;

import java.util.logging.Logger;

import com.webcheckers.model.Player;
import com.webcheckers.ui.CheckersPostWebRoute;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;

public class SignInPostRoute extends CheckersPostWebRoute {
	private static final Logger LOG = Logger.getLogger(SignInPostRoute.class.getName());
	
	// query parameters (matches name attribute of input elements inside a form element in ftl files)
	private static final String USERNAME_PARAM = "userName";
	
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{POST /signin} HTTP requests,
	 * which are used to sign in to the server.
	 *
	 * @param getRoute the GET route to render upon erroneous input
	 */
	public SignInPostRoute(SignInGetRoute getRoute) {
		super(getRoute);
	}
	
	@Override
	public Object handle(Request request, Response response) {
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
			return refreshWithMessage(null, response, error);
		
		// sign-in success; back to home screen
		return redirect(response, WebServer.HOME_URL);
	}
}
