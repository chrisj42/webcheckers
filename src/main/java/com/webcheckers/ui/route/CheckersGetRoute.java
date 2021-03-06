package com.webcheckers.ui.route;

import java.util.Objects;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.TemplateMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

public abstract class CheckersGetRoute extends CheckersWebRoute {
	
	private final TemplateEngine templateEngine;
	private final String viewName;

	/**
	 * Create the Spark Route (UI controller) to handle @code{GET} HTTP requests.
	 *  @param viewName       name of the ftl view that this route renders
	 * @param playerLobby    the application-tier player manager
	 * @param templateEngine the HTML template rendering engine
	 */
	protected CheckersGetRoute(String viewName, PlayerLobby playerLobby, TemplateEngine templateEngine) {
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
		TemplateMap map = get(player, response);
		
		return render(map);
	}
	
	protected abstract TemplateMap get(Player player, Response response);
	
	public Object render(TemplateMap map) {
		return map == null ? null : templateEngine.render(new ModelAndView(map, viewName));
	}
}
