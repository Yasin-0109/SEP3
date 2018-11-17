package com.jas.controller;

import com.jas.model.User;
import com.jas.Server;
import com.jas.modelData.Activities;
import com.jas.modelData.Users;
import com.jas.modelData.WorkoutTypes;
import com.jas.utils.Result;

import spark.Request;
import spark.Response;
import spark.Route;

public class AdminController {
	
	// Activities
	public static Route activityAdd = (Request request, Response response) -> {
		return Result.superUltraStatus(false, "Not implemented yet!");
	};
	
	public static Route activityDel = (Request request, Response response) -> {
		return Result.superUltraStatus(false, "Not implemented yet!");
	};
	
	public static Route activityEdit = (Request request, Response response) -> {
		return Result.superUltraStatus(false, "Not implemented yet!");
	};
	
	public static Route activityList = (Request request, Response response) -> {
		return Result.superUltraJsonData(true, Server.getGson().toJsonTree(Activities.getActivities())); // Returns all data in JSON
	};
	
	// Subscription
	public static Route subscriptionAdd = (Request request, Response response) -> {
		return Result.superUltraStatus(false, "Not implemented yet!");
	};

	public static Route subscriptionDel = (Request request, Response response) -> {
		return Result.superUltraStatus(false, "Not implemented yet!");
	};

	public static Route subscriptionEdit = (Request request, Response response) -> {
		return Result.superUltraStatus(false, "Not implemented yet!");
	};
	
	public static Route subscriptionList = (Request request, Response response) -> {
		return Result.superUltraStatus(false, "Not implemented yet!");
	};
	
	// Users
	public static Route userAdd = (Request request, Response response) -> {
		return Result.superUltraStatus(false, "Not implemented yet!");
	};
	
	public static Route userDel = (Request request, Response response) -> {
		return Result.superUltraStatus(false, "Not implemented yet!");
	};
	
	public static Route userEdit = (Request request, Response response) -> {
		return Result.superUltraStatus(false, "Not implemented yet!");
	};
	
	public static Route userList = (Request request, Response response) -> {
		return Result.superUltraJsonData(true, Server.getGson().toJsonTree(Users.getUsers())); // Returns all data in JSON
	};
	
	public static Route userInfo = (Request request, Response response) -> {
		int userId = -1; // Initialize local userId variable
		try { // Check if id parameter is valid
			userId = Integer.parseInt(request.params(":id")); // Assign id parameter to userId variable
		} catch (NumberFormatException ignored) { // Catch the error if it's not integer
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Id parameter must be an integer!");
		}
		
		User user = Users.getUserById(userId); // Get user by id
		if (user == null) { // Check if user doesn't exists
			response.status(404); // Return 404 - Not Found
			return Result.superUltraStatus(false, "User with specified id doesn't exist!");
		}
		
		response.status(200); // Return 200 - Success with response body
		return Result.superUltraJsonData(true, Server.getGson().toJsonTree(user)); // Return data in JSON
	};
	
	// Workout Types
	public static Route workoutTypeAdd = (Request request, Response response) -> {
		return Result.superUltraStatus(false, "Not implemented yet!");
	};

	public static Route workoutTypeDel = (Request request, Response response) -> {
		return Result.superUltraStatus(false, "Not implemented yet!");
	};
	
	public static Route workoutTypeEdit = (Request request, Response response) -> {
		return Result.superUltraStatus(false, "Not implemented yet!");
	};
	
	public static Route workoutTypeList = (Request request, Response response) -> {
		return Result.superUltraJsonData(true, Server.getGson().toJsonTree(WorkoutTypes.getWorkoutTypes())); // Returns all data in JSON
	};

}
