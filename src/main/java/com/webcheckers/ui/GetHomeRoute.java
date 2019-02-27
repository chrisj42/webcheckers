package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.model.Color;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import com.webcheckers.util.ViewMode;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
	private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
	
	private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
	
	public static final String PLAYER_ATTR = "Player";
	
	private final TemplateEngine templateEngine;
	
	/**
	 * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
	 *
	 * @param templateEngine
	 *   the HTML template rendering engine
	 */
	public GetHomeRoute(final TemplateEngine templateEngine) {
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
		vm.put("title", "Welcome!");
		
		Session session = request.session();
		
		// display a user message in the Home page
		vm.put("message", WELCOME_MSG);
		
		
		// vm.put("currentUser", new Player("Chris"));
		
		Player p = session.attribute(PLAYER_ATTR);
		if(p != null) {
			/*vm.put("redPlayer", p);
			vm.put("whitePlayer", new Player("Steve"));
			
			vm.put("activeColor", Color.RED);
			
			vm.put("viewMode", ViewMode.PLAY);
			
			vm.put("board", new BoardView());*/
			vm.put("currentUser", p);
		}
		
		// render the View
		return templateEngine.render(new ModelAndView(vm , "home.ftl"));
	}
}
