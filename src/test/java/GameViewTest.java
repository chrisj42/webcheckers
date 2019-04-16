

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.ui.TemplateEngineTester;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.route.game.GameGetRoute;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.ModelAndView;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

@Tag("UI-tier")
public class GameViewTest {

  private static final String TITLE = "CheckersGame";
  private static final String TITLE_HEAD_TAG = "<title>" + TITLE + "</title>";



  private static String makeMessageTag(final String text,final String type){
    return String.format("<div class=\"message %s\">%s</div>",type,text);
  }

  private static String makeSignInMessage(PlayerLobby pl,String name){
    if(pl.tryLoginPlayer(name)!=null){
      return String.format("<div class=\"message %s\"</div>",name);
    }else {
      return "Name not available";
    }
  }

  private final TemplateEngine engine = new FreeMarkerEngine();

  @Test
  public void new_game(){

    // Arrange test
    final Map<String,Object> vm = new HashMap<>();
    final ModelAndView modelAndView = new ModelAndView(vm, WebServer.GAME_VIEW);
    // setup View-Model for a new player
    vm.put(WebServer.HOME_URL,WebServer.HOME_VIEW);


    final String viewHtml = engine.render(modelAndView);



  }

}
