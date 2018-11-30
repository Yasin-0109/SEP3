package com.jas;

import static spark.Spark.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jas.controller.*;
import com.jas.utils.Utils;

public class Server 
{
	private Gson gson;
	
	public void start() {
		
		gson = new GsonBuilder()
				.serializeNulls() // Serialize null values.
				.setPrettyPrinting() // Make JSON results prettier!
				.create(); 
		
		secure("deploy/keystore3.jks", "aBKzWP2QfZx4XwHdFjtYUj56", null, null);
		System.out.println("Starting API Server at " + Main.getConfig().getProperty("server.port") + " port!");
		port(Integer.parseInt(Main.getConfig().getProperty("server.port"))); // Sets Spark server port
		init(); // Initializes Spark server
		
		before((o,a) -> a.type("application/json")); // API will return JSON data by default.
		
		// Server
		notFound(ServerController.notFound); // Handle 404 errors.
		internalServerError(ServerController.internalError); // Handle 500 errors.
		get("/", ServerController.main); // Add main route for the server
		get("/status", ServerController.status); // Add status route
		
		// Admin routes
		path("/admin", () -> {
			before("/*", (o,a) -> SessionController.isLoggedInAs(o, Utils.getAdminRole())); // Check if user is an admin for all reqests
			
			path("/activities", () -> {
				get("/", AdminController.activityList);
				post("/add", AdminController.activityAdd);
				
				path("/:id", () -> {
					put("/edit", AdminController.activityEdit);
					delete("/delete", AdminController.activityDel);
				});
			});
			
			path("/subscriptions", () -> {
				get("/", AdminController.subscriptionList);
				post("/add", AdminController.subscriptionAdd);
				
				path("/:id", () -> {
					put("/edit", AdminController.subscriptionEdit);
					delete("/delete", AdminController.subscriptionDel);
				});
			});
			
			path("/users", () -> {
				get("/", AdminController.userList);
				post("/add", AdminController.userAdd);
				
				path("/:id", () -> {
					get("/", AdminController.userInfo);
					put("/edit", AdminController.userEdit);
					delete("/delete", AdminController.userDel);
				});
			});
			
			path("/workouts/types", () -> {
				get("/", AdminController.workoutTypeList);
				post("/add", AdminController.workoutTypeAdd);
				
				path("/:id", () -> {
					put("/edit", AdminController.workoutTypeEdit);
					delete("/delete", AdminController.workoutTypeDel);
				});
			});
		});
		
		// Instructor routes
		path("/instructor", () -> {
			before("/*", (o,a) -> SessionController.isLoggedInAs(o, Utils.getInstructorRole(), Utils.getAdminRole())); // Check if user is an admin or instructor for all reqests
			
			path("/activities", () -> {
				get("/", InstructorController.activityList);
				post("/add", InstructorController.activityAdd);
				
				path("/:id", () -> {
					put("/edit", InstructorController.activityEdit);
					delete("/delete", InstructorController.activityDel);
				});
			});
		});
		
		// Authentication routes
		post("/login", SessionController.loginPost);
		post("/logout", SessionController.logoutPost);
		
		// User routes
		path("/user", () -> {
			before("/*", (o,a) -> SessionController.isLoggedIn(o)); // Check if user is logged in for all requests
			get("/", UserController.userInfo);
			get("/subscription/", UserController.subscriptionInfo);	
			get("/activities/", UserController.activityList);
			
			path("/useractivities", () -> {
				get("/", UserController.userActivityList);
				post("/add", UserController.userActivityAdd);
				
				path("/:id", () -> {
					get("/", UserController.userActivityInfo);
					delete("/delete", UserController.userActivityDel);
				});
			});
			
			path("/workouts", () -> {
				get("/", UserController.workoutList);
				post("/add", UserController.workoutAdd);
				
				path("/:id", () -> {
					put("/edit", UserController.workoutEdit);
					delete("/delete", UserController.workoutDel);
				});
			});
		});
	}
	
	public void isGUI() { // Enables debug logging to existing GUI
		before("*", (a,o) -> Main.getGUI().setSparkRequestInfo(a)); // Catch all the traffic
	}
	
	public Gson getGson() { // Returns initialized gson instance
		return gson;
	}
	
}
