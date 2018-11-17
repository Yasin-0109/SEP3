package com.jas;

import static spark.Spark.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jas.controller.*;
import com.jas.utils.Utils;

public class Server 
{
	private static Gson gson;
	
	public static void main(String[] args)
	{	
		gson = new GsonBuilder()
				.serializeNulls() // Serialize null values.
				.setPrettyPrinting() // Make JSON results prettier!
				.create(); 
		
		port(8080); // Sets Spark server port
		init(); // Initializes Spark server
		
		before((o,a) -> a.type("application/json")); // API will return JSON data by default.
		
		// Server
		notFound(ServerController.notFound); // Handle 404 errors.
		internalServerError(ServerController.internalError); // Handle 500 errors.
		get("/", ServerController.main);
		get("/status", ServerController.status);
		
		// Admin
		path("/admin", () -> {
			before("/*", (o,a) -> SessionController.isLoggedInAs(o, Utils.getAdminRole()));
			
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
		
		// Instructor
		path("/instructor", () -> {
			before("/*", (o,a) -> SessionController.isLoggedInAs(o, Utils.getInstructorRole(), Utils.getAdminRole()));
			
			path("/activities", () -> {
				get("/", InstructorController.activityList);
				post("/add", InstructorController.activityAdd);
				
				path("/:id", () -> {
					put("/edit", InstructorController.activityEdit);
					delete("/delete", InstructorController.activityDel);
				});
			});
		});
		
		// Authentication
		post("/login", SessionController.loginPost);
		post("/logout", SessionController.logoutPost);
		
		// User
		path("/user", () -> {
			before("/*", (o,a) -> SessionController.isLoggedIn(o));
			get("/", UserController.userInfo);
			get("/subscription/", UserController.subscriptionInfo);
			
			path("/activities", () -> {
				get("/", UserController.userActivityList);
				post("/add", UserController.userActivityAdd);
				
				path("/:id", () -> {
					put("/edit", UserController.userActivityEdit);
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
	
	public static Gson getGson() {
		return gson;
	}
	
}