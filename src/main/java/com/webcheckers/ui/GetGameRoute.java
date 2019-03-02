package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Color;
import com.webcheckers.model.Player;
import com.webcheckers.util.ViewMode;
import spark.*;

public class GetGameRoute implements Route {
    private int getridofme = 0;
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());
    
    private static final String VIEW_NAME = "game.ftl";
    
    private static final String TITLE_KEY = "title";

    private static final String OPPONENT_PARAM = "opponent";
    
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    
    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetGameRoute(PlayerLobby playerLobby, TemplateEngine templateEngine){
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
        LOG.finer("GetGameRoute is invoked.");
        
        Map<String, Object> vm = new HashMap<>();
        Session session = request.session();
        
        // fetch the player from the session
        Player p = session.attribute(WebServer.PLAYER_ATTR);
        if(p == null) {
            // user not logged in, redirect to home screen
            response.redirect(WebServer.HOME_URL);
            Spark.halt();
            return null;
        }
        
        // TODO if player is not in a game, redirect to home screen
        if(!p.hasGame()){
            response.redirect(WebServer.HOME_URL);
            Spark.halt();
            return null;
        }
        String opponent = request.queryParams(OPPONENT_PARAM);
        Player Opponent = null;
        Iterator<Player> players = playerLobby.iterator();
        while(players.hasNext()){
            Opponent =  players.next();
            if(Opponent.getName().equals(opponent)){
                break;
            }
        }
        if(Opponent != null){
            if(!Opponent.getName().equals(opponent)){
                response.redirect(WebServer.HOME_URL);
                Spark.halt();
                return null;
            }
        }

        
        // TODO else player is in a game; fill with params; fetch current game model to do so
        
        vm.put(TITLE_KEY, "Play");
        
        vm.put("currentUser", p);
        vm.put("redPlayer", p);
        vm.put("whitePlayer", Opponent); // replace with actual opponent player
    
        vm.put("activeColor", Color.RED); // replace with actual active player
    
        vm.put("viewMode", ViewMode.PLAY); // replace with actual view mode
    
        vm.put("board", new BoardView()); // pass board array from model to BoardView constructor
    
        // render the View
        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
    }
}
