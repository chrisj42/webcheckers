package com.webcheckers.ui;

import java.util.Objects;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import com.webcheckers.util.TemplateMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

public abstract class CheckersGetRoute extends CheckersRoute implements Route {
	
	private final TemplateEngine templateEngine;
	private final String viewName;
	
	/**
	 * Create the Spark Route (UI controller) to handle @code{GET} HTTP requests.
	 *
	 * @param viewName       name of the ftl view that this route renders
	 * @param playerLobby    the application-tier player manager
	 * @param templateEngine the HTML template rendering engine
	 */
	CheckersGetRoute(String viewName, PlayerLobby playerLobby, TemplateEngine templateEngine) {
		super(playerLobby);
		// validation
		Objects.requireNonNull(viewName, "viewName must not be null");
		Objects.requireNonNull(templateEngine, "templateEngine must not be null");
		//
		this.viewName = viewName;
		this.templateEngine = templateEngine;
	}
	
	@Override
	public Object handle(Request request, Response response) {
		Player player = request.session().attribute(WebServer.PLAYER_ATTR);
		
		// attempt to load the page template; if a redirect occurs it will return null
		TemplateMap map = loadTemplate(player, response);
		
		return render(map);
	}
	
	protected abstract TemplateMap loadTemplate(Player player, Response response);
	
	public Object render(TemplateMap map) {
		return map == null ? null : templateEngine.render(new ModelAndView(map, viewName));
	}
	
	protected Object renderTemplate(Player player, Response response, Message message) {
		TemplateMap map = loadTemplate(player, response);
		if(map != null)
			map.put(WebServer.MESSAGE_KEY, message);
		
		return render(map);
	}
}
