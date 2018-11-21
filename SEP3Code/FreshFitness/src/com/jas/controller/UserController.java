package com.jas.controller;

import com.jas.Server;
import com.jas.model.User;
import com.jas.modelData.Users;
import com.jas.utils.Result;

import spark.Request;
import spark.Response;
import spark.Route;

public class UserController {

	/**
	 * This function is for authenticating a user.
	 * @param email email of user
	 * @param password password of user
	 * @return true if password is correct
	 */
	public static boolean authenticate(String email, String password) {
		if (email.isEmpty() || password.isEmpty() || Users.getUserByEmail(email) == null) {
			return false; // If email/password is empty or there's no user with that email - return false 
		}
		
		User user = Users.getUserByEmail(email);
		return user.getPassword().equals(password); // Why we don't use any encryption?! It's 2018 - leaks are everywhere :V 
	}
	
	public static Route userInfo = (Request request, Response response) -> { // Returns user information
		User user = Users.getUserById(SessionController.getUserId(request)); // Get logged in user
		return Result.superUltraJsonData(true, Server.getGson().toJsonTree(user)); // Return user in JSON
	};
	
	public static Route subscriptionInfo = (Request request, Response response) -> {
		User user = Users.getUserById(SessionController.getUserId(request)); // Get logged in user
		return Result.superUltraJsonData(true, Server.getGson().toJsonTree(user.getSubscription())); // Return user in JSON
	};
	
	// User activities
	public static Route userActivityAdd = (Request request, Response response) -> {
		return Result.notImplementedYet(response); // Send not implemented message
	};
	
	public static Route userActivityDel = (Request request, Response response) -> {
		return Result.notImplementedYet(response); // Send not implemented message
	};
	
	public static Route userActivityEdit = (Request request, Response response) -> {
		return Result.notImplementedYet(response); // Send not implemented message
	};
	
	public static Route userActivityList = (Request request, Response response) -> {
		User user = Users.getUserById(SessionController.getUserId(request)); // Get logged in user
		return Result.superUltraJsonData(true, Server.getGson().toJsonTree(user.getActivities())); // Return user in JSON
	};
	
	// Workouts
	public static Route workoutAdd = (Request request, Response response) -> {
		return Result.notImplementedYet(response); // Send not implemented message
	};

	public static Route workoutDel = (Request request, Response response) -> {
		return Result.notImplementedYet(response); // Send not implemented message
	};
	
	public static Route workoutEdit = (Request request, Response response) -> {
		return Result.notImplementedYet(response); // Send not implemented message
	};
	
	public static Route workoutList = (Request request, Response response) -> {
		User user = Users.getUserById(SessionController.getUserId(request)); // Get logged in user
		return Result.superUltraJsonData(true, Server.getGson().toJsonTree(user.getWorkouts())); // Return user in JSON
	};
	
}
