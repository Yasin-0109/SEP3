package com.jas.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jas.Main;
import com.jas.model.Activity;
import com.jas.modelData.Activities;
import com.jas.utils.Result;

import spark.Request;
import spark.Response;
import spark.Route;

public class InstructorController {

	// Activities
	public static Route activityAdd = (Request request, Response response) -> { // Route that adds an activity
		// Temporary variables
		String name;
		Timestamp startTime;
		Timestamp endTime;
		
		// Checking if we have all required params passed in request
		if (request.queryParams("name") == null || request.queryParams("name").isEmpty() ||
			request.queryParams("startTime") == null || request.queryParams("startTime").isEmpty() ||
			request.queryParams("endTime") == null || request.queryParams("endTime").isEmpty()) {
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
		
		Activity activity = new Activity(-1, name, startTime, endTime, SessionController.getUserId(request)); // Creating activity based on request
		boolean result = Activities.addActivity(activity); // Adding activity
		if (!result) { // Check if it has been added without any problems
			response.status(500); // Return 500 - Internal Server Error
			return Result.superUltraStatus(false, "Couldn't add activity.");
		}
		return Result.superUltraStatus(true, activity.getName() + " has been added.");
	};
	
	public static Route activityDel = (Request request, Response response) -> {
		int id;
		try {
			id = Integer.parseInt(request.params(":id")); // Parsing id as integer
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid id value!");
		}
		
		Activity activity = Activities.getActivityById(id); // Getting activity by it's ID
		if (activity == null) { // Check if activity exists
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid activity id!");
		}
		
		if (activity.getInstructorId() != SessionController.getUserId(request)) {
			response.status(400);
			return Result.superUltraStatus(false, "That's not your activity.");
		}
		
		boolean result = Activities.delActivity(activity); // Removing activity
		if (!result) { // Check if it has been deleted without any problems
			response.status(500); // Return 500 - Internal Server Error
			return Result.superUltraStatus(false, "Couldn't remove activity.");
		}
		return Result.superUltraStatus(true, activity.getName() + " has been removed.");
	};
	
	public static Route activityEdit = (Request request, Response response) -> {
		// Temporary variables
		Activity activity;

		// Old activity
		try {
			activity = Activities.getActivityById(Integer.parseInt(request.params(":id"))); // Parsing activityId as id for getting activity
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid activity id value!");
		}
		
		if (activity.getInstructorId() != SessionController.getUserId(request)) {
			response.status(400);
			return Result.superUltraStatus(false, "That's not your activity.");
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

		boolean result = Activities.editActivity(activity); // Edit activity to have new data
		if (!result) { // Check if it has been edited without any problems
			response.status(500); // Return 500 - Internal Server Error
			return Result.superUltraStatus(false, "Couldn't modify activity.");
		}
		return Result.superUltraStatus(true, activity.getName() + " has been edited.");
	};
	
	public static Route activityList = (Request request, Response response) -> {
		return Result.superUltraJsonData(true,
				Main.getServer().getGson().toJsonTree(Activities.getInstructorActivities(SessionController.getUserId(request)))); // Returns all data in JSON
	};
	
}
