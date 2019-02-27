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
	
	// query parameters (matches name attribute of input elements inside a form element in ftl files)
	private static final String USERNAME_PARAM = "userName";
	
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
		
		String username = request.queryParams(USERNAME_PARAM);
		
		if(username.length() == 0) {
			vm.put(WebServer.MESSAGE_KEY, Message.error("Username must contain at least 1 character."));
			
			return templateEngine.render(new ModelAndView(vm, GetSignInRoute.VIEW_NAME));
		}
		
		Player p = playerLobby.tryLoginPlayer(username);
		if(p == null) {
			vm.put(WebServer.MESSAGE_KEY, Message.error("Username already taken. Please choose another."));
			
			return templateEngine.render(new ModelAndView(vm, GetSignInRoute.VIEW_NAME));
		}
		
		session.attribute(WebServer.PLAYER_ATTR, p);
		response.redirect(WebServer.HOME_URL);
		halt();
		return null;
	}
}
