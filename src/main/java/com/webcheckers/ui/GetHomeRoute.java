package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
	private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
	
	private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
	
	static final String VIEW_NAME = "home.ftl";
	
	private final PlayerLobby playerLobby;
	private final TemplateEngine templateEngine;
	
	/**
	 * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
	 *
	 * @param templateEngine
	 *   the HTML template rendering engine
	 */
	public GetHomeRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
		this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");;
		this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
		//
		LOG.config("GetHomeRoute is initialized.");
	}
	
	/**
	 * Render the WebCheckers Home page.
	 *
	 * @param request
	 *   the HTTP request
	 * @param response
	 *   the HTTP response
	 *
	 * @return
	 *   the rendered HTML for the Home page
	 */
	@Override
	public Object handle(Request request, Response response) {
		LOG.finer("GetHomeRoute is invoked.");
		//
		Map<String, Object> vm = new HashMap<>();
		
		Session session = request.session();
		
		// check if the user is signed in
		Player p = session.attribute(WebServer.PLAYER_ATTR);
		if(p != null) {
			// check if player is already in a game (ask PlayerLobby); if so, redirect to /game and return, else continue
			if(playerLobby.hasGame(p)) {
				response.redirect(WebServer.GAME_URL);
				Spark.halt();
				return null;
			}
			
			// add user object
			vm.put(WebServer.USER_KEY, p);
		}
		
		// display a user message in the Home page
		vm.put(WebServer.MESSAGE_KEY, WELCOME_MSG);
		
		vm.put(WebServer.PLAYER_LOBBY_KEY, playerLobby);
		
		// render the View
		return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
	}
}
