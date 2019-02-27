package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Color;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import com.webcheckers.util.ViewMode;
import spark.*;

public class GetSignInRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());
    
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    
    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine){
        // validation
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
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
        LOG.finer("GetSignInRoute is invoked.");
    
        Session session = request.session();
        Player p = session.attribute(GetHomeRoute.PLAYER_ATTR);
        if(p != null) {
            response.redirect(WebServer.HOME_URL);
            Spark.halt();
            return null;
        }
        
        //
        Map<String, Object> vm = new HashMap<>();
        
        vm.put("title", "Sign In");
        // vm.put("message", WELCOME_MSG);
        
        // render the View
        return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
    }
}
