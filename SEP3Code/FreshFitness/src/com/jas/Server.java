package com.jas;

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jas.controller.LoginController;
import com.jas.model.EUserRole;
import com.jas.model.Result;
import com.jas.model.User;
import com.jas.model.Workout;
import com.jas.modelData.Users;
import com.jas.model.EUserRole.ERole;
import com.jas.model.EWorkoutType.EType;

public class Server 
{
	private static Gson gson;
	
	public static void main(String[] args)
	{	
		gson = new GsonBuilder()
				.setPrettyPrinting()
				.create(); // Make JSON results prettier!
		
		port(8080); // Sets Spark server port
		init(); // Initializes Spark server
		enableDebugScreen();
		
		get("/", (request, response) -> { // Makes default GET / route
			return "Hello world lol!"; // Returns this text on request
		});
		
		get("/users", (request, response) -> { // Makes GET /users route - get list of all users
			response.type("application/json"); // Make result a JSON.
			
			if (!LoginController.isLoggedIn(request)) {
				response.status(403); // Return 403 - Forbidden
				return Result.superUltraError("You have to be logged in!");
			}
			
			if (Users.getUserById(request.session().attribute("currentUserId")).getUserRole() != ERole.Admin) {
				response.status(403); // Return 403 - Forbidden
				return Result.superUltraError("You need to be an Admin!");
			}
			
			response.status(200); // Return 200 - Success with body data
			return getGson().toJson(Users.getUsers()); // Returns all users in JSON
		}); 
		
		get("/user/:id", (request, response) -> { // Makes GET /users/id route - get user by id
			response.type("application/json"); // Make result a JSON.
			
			int userId = -1; // Initialize local userId variable
			try { // Check if id parameter is valid
				userId = Integer.parseInt(request.params(":id")); // Assign id parameter to userId variable
			} catch (NumberFormatException ignored) { // Catch the error if it's not integer
				response.status(400); // Return 400 - Bad Request
				return Result.superUltraError("Id parameter must be an integer!");
			}
			
			User user = Users.getUserById(userId); // Get user by id
			
			if (user == null) { // Check if user doesn't exists
				response.status(404); // Return 404 - Not Found
				return Result.superUltraError("User with specified id doesn't exist!");
			}
			
			response.status(200); // Return 200 - Success with response body
			return getGson().toJson(user); // Return user in JSON
		});
		
		post("/login", LoginController.loginPost);
		post("/logout", LoginController.logoutPost);
		
		///
		
		
		Workout workout = new Workout(1, 5, EType.benchpress);
		Workout workout2 = new Workout(1, 10, EType.benchpress);
		
		ERole role = ERole.Admin;
		
		User bob = new User(1, "email", "firstName", "lastName", 34234556, new Date(), role);
		
		System.out.println(bob);
		
		bob.addWorkOut(workout);
		bob.addWorkOut(workout2);
		
		System.out.println(bob.getNumberOfworkoutsByType(EType.boxing));
		
	}
	
	public static Gson getGson() {
		return gson;
	}
}
