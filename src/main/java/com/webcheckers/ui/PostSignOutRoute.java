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


public class PostSignOutRoute implements Route {
	
	private final TemplateEngine templateEngine;
	private final PlayerLobby playerLobby;
	
	PostSignOutRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
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
		final Session session = request.session();
		
		Player p = session.attribute(WebServer.PLAYER_ATTR);
		if(p != null) {
			playerLobby.logoutPlayer(p.getName());
		}
		
		session.removeAttribute(WebServer.PLAYER_ATTR);
		response.redirect(WebServer.HOME_URL);
		halt();
		return null;
	}
}
