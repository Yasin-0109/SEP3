package com.jas.controller;

import com.jas.model.Activity;
import com.jas.model.User;

import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.jas.Server;
import com.jas.modelData.Activities;
import com.jas.modelData.Subscriptions;
import com.jas.modelData.Users;
import com.jas.modelData.WorkoutTypes;
import com.jas.utils.Result;

import spark.Request;
import spark.Response;
import spark.Route;

public class AdminController {
	
	// Activities
	public static Route activityAdd = (Request request, Response response) -> { // Route that adds an activity
		// Temporary variables
		String name;
		Timestamp startTime;
		Timestamp endTime;
		Integer instructorId;
		
		// Checking if we have all required params passed in request
		if (request.queryParams("name") == null || request.queryParams("name").isEmpty() ||
			request.queryParams("startTime") == null || request.queryParams("startTime").isEmpty() ||
			request.queryParams("endTime") == null || request.queryParams("endTime").isEmpty() ||
			request.queryParams("instructorId") == null || request.queryParams("instructorId").isEmpty()) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid request!");
		}
		
		// Name
		name = request.queryParams("name");
		
		// Start time
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"); // Initializing date format
		    Date parsedDate = dateFormat.parse(request.queryParams("startTime")); // Parsing date
		    startTime = new Timestamp(parsedDate.getTime()); // Converting it to timestamp
		} catch (Exception ignored) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid start time value!");
		}
		
		// End time
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"); // Initializing date format
		    Date parsedDate = dateFormat.parse(request.queryParams("endTime")); // Parsing date
		    endTime = new Timestamp(parsedDate.getTime()); // Converting it to timestamp
		} catch (Exception ignored) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid end time value!");
		}
		
		// Instructor ID
		try {
			instructorId = Integer.parseInt(request.queryParams("instructorId")); // Parsing id as int
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid instructor id value!");
		}
		
		Activity activity = new Activity(-1, name, startTime, endTime, instructorId); // Creating activity based on request
		boolean result = Activities.addActivity(activity); // Adding activity
		if (!result) { // Check if it has been added without any problems
			response.status(500); // Return 500 - Internal Server Error
		}
		return Result.superUltraStatus(result, "");
	};
	
	public static Route activityDel = (Request request, Response response) -> {
		// Checking if we have all required params passed in request
		if (request.queryParams("id") == null || request.queryParams("id").isEmpty()) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid request!");
		}
		
		int id;
		try {
			id = Integer.parseInt(request.queryParams("id")); // Parsing id as integer
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid id value!");
		}
		
		Activity activity = Activities.getActivityById(id); // Getting activity by it's ID
		if (activity == null) { // Check if activity exists
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid activity id!");
		}
		
		boolean result = Activities.delActivity(activity); // Removing activity
		if (!result) { // Check if it has been deleted without any problems
			response.status(500); // Return 500 - Internal Server Error
		}
		return Result.superUltraStatus(result, "");
	};
	
	public static Route activityEdit = (Request request, Response response) -> {
		// Temporary variables
		Activity activity;
		
		// Checking if we have all required params passed in request
		if (request.queryParams("activityId") == null || request.queryParams("activityId").isEmpty()) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid request!");
		}
	
		// Old activity
		try {
			activity = Activities.getActivityById(Integer.parseInt(request.queryParams("activityId"))); // Parsing activityId as id for getting activity
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid activity id value!");
		}
		
		// Name
		if (request.queryParams("name") != null || !request.queryParams("name").isEmpty()) { // Check if we have name in request
			activity.setName(request.queryParams("name")); // Parsing name as string
		}
		
		// Start time
		if (request.queryParams("startTime") != null || !request.queryParams("startTime").isEmpty()) { // Check if we have start time in request
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"); // Initializing date format
			    Date parsedDate = dateFormat.parse(request.queryParams("startTime")); // Parsing date
			    activity.setStartTime(new Timestamp(parsedDate.getTime())); // Converting it to timestamp
			} catch (Exception ignored) {
				response.status(400); // Return 400 - Bad Request
				return Result.superUltraStatus(false, "Invalid start time value!");
			}
		}
		
		// End time
		if (request.queryParams("endTime") != null || !request.queryParams("endTime").isEmpty()) { // Check if we have end time in request
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"); // Initializing date format
			    Date parsedDate = dateFormat.parse(request.queryParams("endTime")); // Parsing date
			    activity.setEndTime(new Timestamp(parsedDate.getTime())); // Converting it to timestamp
			} catch (Exception ignored) {
				response.status(400); // Return 400 - Bad Request
				return Result.superUltraStatus(false, "Invalid end time value!");
			}
		}
		
		// Instructor ID
		if (request.queryParams("instructorId") != null || !request.queryParams("instructorId").isEmpty()) { // Check if we have instructor id in request
			try {
				activity.setInstructorId(Integer.parseInt(request.queryParams("instructorId"))); // Parsing instructorId as integer
			} catch (NumberFormatException e) {
				response.status(400); // Return 400 - Bad Request
				return Result.superUltraStatus(false, "Invalid instructor id value!");
			}
		}

		boolean result = Activities.editActivity(activity); // Edit activity to have new data
		if (!result) { // Check if it has been edited without any problems
			response.status(500); // Return 500 - Internal Server Error
		}
		return Result.superUltraStatus(result, "");
	};
	
	public static Route activityList = (Request request, Response response) -> {
		return Result.superUltraJsonData(true, Server.getGson().toJsonTree(Activities.getActivities())); // Returns all data in JSON
	};
	
	// Subscription
	public static Route subscriptionAdd = (Request request, Response response) -> {
		return Result.notImplementedYet(response); // Send not implemented message
	};

	public static Route subscriptionDel = (Request request, Response response) -> {
		return Result.notImplementedYet(response); // Send not implemented message
	};

	public static Route subscriptionEdit = (Request request, Response response) -> {
		return Result.notImplementedYet(response); // Send not implemented message
	};
	
	public static Route subscriptionList = (Request request, Response response) -> {
		return Result.superUltraJsonData(true, Server.getGson().toJsonTree(Subscriptions.getSubscriptions())); // Returns all data in JSON
	};
	
	// Users
	public static Route userAdd = (Request request, Response response) -> {
		return Result.notImplementedYet(response); // Send not implemented message
	};
	
	public static Route userDel = (Request request, Response response) -> {
		return Result.notImplementedYet(response); // Send not implemented message
	};
	
	public static Route userEdit = (Request request, Response response) -> {
		return Result.notImplementedYet(response); // Send not implemented message
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
		return Result.notImplementedYet(response); // Send not implemented message
	};

	public static Route workoutTypeDel = (Request request, Response response) -> {
		return Result.notImplementedYet(response); // Send not implemented message
	};
	
	public static Route workoutTypeEdit = (Request request, Response response) -> {
		return Result.notImplementedYet(response); // Send not implemented message
	};
	
	public static Route workoutTypeList = (Request request, Response response) -> {
		return Result.superUltraJsonData(true, Server.getGson().toJsonTree(WorkoutTypes.getWorkoutTypes())); // Returns all data in JSON
	};

}
