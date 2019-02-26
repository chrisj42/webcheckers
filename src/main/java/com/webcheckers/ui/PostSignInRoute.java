package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

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
import static spark.Spark.halt;


public class PostSignInRoute implements Route {
    static final String USERNAME = "userName";

    static final String VIEW_NAME = "signin.ftl";

    private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    private static String username = null;


    private final TemplateEngine templateEngine;

    PostSignInRoute(TemplateEngine templateEngine){
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
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

        vm.put("title", "Welcome!");

        // display a user message in the Home page
        vm.put("message", WELCOME_MSG);

        username = request.queryParams(USERNAME);

        vm.put("currentUser", new Player(username));


        // render the View
        return templateEngine.render(new ModelAndView(vm , "home.ftl"));
    }
}
