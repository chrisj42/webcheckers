package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;
import static spark.Spark.halt;


public class PostSignInRoute implements Route {
	static final String USERNAME = "userName";
	
	static final String VIEW_NAME = "signin.ftl";
	
	private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
	
	private static String username = null;
	
	private final TemplateEngine templateEngine;
	private final PlayerLobby playerLobby;
	
	PostSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine){
		// validation
		Objects.requireNonNull(playerLobby, "playerLobby must not be null");
		Objects.requireNonNull(templateEngine, "templateEngine must not be null");
		//
		this.playerLobby = playerLobby;
		this.templateEngine = templateEngine;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @throws NoSuchElementException
	 *    when an invalid result is returned after making a guess
	 */
	@Override
	public String handle(Request request, Response response) {
		// start building the View-Model
		final Map<String, Object> vm = new HashMap<>();
		
		final Session session = request.session();
		
		vm.put("title", "Sign In");
		
		username = request.queryParams(USERNAME);
		
		System.out.println("username: \""+username+"\"");
		
		if(username.length() == 0) {
			vm.put("message", Message.error("Username must contain at least 1 character."));
			
			return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
		}
		
		Player p = playerLobby.tryLoginPlayer(username);
		if(p == null) {
			vm.put("message", Message.error("Username already taken. Please choose another."));
			
			return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
		}
		
		session.attribute(GetHomeRoute.PLAYER_ATTR, p);
		response.redirect(WebServer.HOME_URL);
		halt();
		return null;
	}
}
