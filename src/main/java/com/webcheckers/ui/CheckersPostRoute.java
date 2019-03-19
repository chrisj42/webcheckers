package com.webcheckers.ui;

import spark.Request;
import spark.Response;

@FunctionalInterface
public interface CheckersPostRoute {
	
	Object post(Request request, Response response);
	
}
