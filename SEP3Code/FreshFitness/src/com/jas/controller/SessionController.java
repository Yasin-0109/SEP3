package com.jas.controller;

import static spark.Spark.halt;

import java.util.Arrays;

import com.jas.model.UserRole;
import com.jas.modelData.Users;
import com.jas.utils.Result;

import spark.Request;
import spark.Response;
import spark.Route;

public class SessionController {

	public static Route loginPost = (Request request, Response response) -> {
		response.type("application/json"); // Make result a JSON.
		
		if (request.queryParams("email") == null || request.queryParams("password") == null) { // Check if we have required params in request
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "email and password are required!");
		}
		
		if (request.queryParams("email").isEmpty() || request.queryParams("password").isEmpty()) { // Check if they aren't empty
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "E-mail/Password can't be empty.");
		}
		
		if (Users.getUserByEmail(request.queryParams("email")) == null) { // Check if there's an user with that email
			response.status(404); // Return 404 - Not Found
			return Result.superUltraStatus(false, "User with specified e-mail doesn't exist!");
		}
		
		if (!UserController.authenticate(request.queryParams("email"), request.queryParams("password"))) { // Make authenticate attempt
			response.status(403); // Return 403 - Forbidden
			return Result.superUltraStatus(false, "User password is incorrect.");
		}
		
		request.session().attribute("currentUserId", Users.getUserByEmail(request.queryParams("email")).getId()); // Let's make a session!
		response.status(200); // Return 200 - Success
		return Result.superUltraStatus(true, "You have been logged in.");
	};
	
	public static Route logoutPost = (Request request, Response response) -> {
		response.type("application/json"); // Make result a JSON.
		
		request.session().removeAttribute("currentUserId"); // Remove information about session
		request.session().attribute("loggedOut", true);
		
		response.status(200); // Return 200 - Success
		return Result.superUltraStatus(true, "You have been logged out.");
	};
	
	public static void isLoggedIn(Request request) {
		if(request.session().attribute("currentUserId") == null) { // Check if we have information about session
			halt(403, Result.superUltraStatus(false, "You need to be logged in!"));
		} 
	}
	
	public static void isLoggedInAs(Request request, UserRole... roles) {
		StringBuilder sb = new StringBuilder("You need to be logged in as ");
		String prefix = "";
		for(UserRole role : roles) { // Return each name of role from roles argument
			sb.append(prefix);
			prefix = "/";
			sb.append(role.getName());
		}
		sb.append('!');
		
		if (request.session().attribute("currentUserId") == null || // Check if it's not logged
			!Arrays.stream(roles).anyMatch(Users.getUserById(request.session().attribute("currentUserId")).getUserRole()::equals)) { // Check if user doesn't have specific role
			halt(403, Result.superUltraStatus(false, sb.toString())); // Stop execution, return 403 code.
		}
	};
	
	public static int getUserId(Request request) {
		return request.session().attribute("currentUserId"); // Return user id from session
	}
	
}
