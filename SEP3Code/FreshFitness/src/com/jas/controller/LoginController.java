package com.jas.controller;

import com.jas.model.Result;
import com.jas.modelData.Users;

import spark.Request;
import spark.Response;
import spark.Route;

public class LoginController {

	public static Route loginPost = (Request request, Response response) -> {
		response.type("application/json"); // Make result a JSON.
		
		if (request.queryParams("email") == null || request.queryParams("password") == null) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "email and password are required!");
		}
		
		if (request.queryParams("email").isEmpty() || request.queryParams("password").isEmpty()) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "E-mail/Password can't be empty.");
		}
		
		if (Users.getUserByEmail(request.queryParams("email")) == null) {
			response.status(404); // Return 404 - Not Found
			return Result.superUltraStatus(false, "User with specified e-mail doesn't exist!");
		}
		
		if (!UserController.authenticate(request.queryParams("email"), request.queryParams("password"))) {
			response.status(403); // Return 403 - Forbidden
			return Result.superUltraStatus(false, "User password is incorrect.");
		}
		
		request.session().attribute("currentUserId", Users.getUserByEmail(request.queryParams("email")).getID()); // Let's make a session!
		response.status(200); // Return 200 - Success
		return Result.superUltraStatus(true, "You have been logged in.");
	};
	
	public static Route logoutPost = (Request request, Response response) -> {
		response.type("application/json"); // Make result a JSON.
		
		request.session().removeAttribute("currentUserId");
		request.session().attribute("loggedOut", true);
		
		response.status(200); // Return 200 - Success
		return Result.superUltraStatus(true, "You have been logged out.");
	};
	
	public static boolean isLoggedIn(Request request) {
		return request.session().attribute("currentUserId") != null;
	}
	
}
