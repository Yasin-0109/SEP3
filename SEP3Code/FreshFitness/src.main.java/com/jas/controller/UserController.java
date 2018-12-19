package com.jas.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jas.Server;
import com.jas.model.Activity;
import com.jas.model.User;
import com.jas.model.Workout;
import com.jas.model.WorkoutType;
import com.jas.modelData.Activities;
import com.jas.modelData.Subscriptions;
import com.jas.modelData.UserActivities;
import com.jas.modelData.Users;
import com.jas.modelData.WorkoutTypes;
import com.jas.modelData.Workouts;
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
		return user.getPassword().equals(password); 
	}
	
	public static Route userInfo = (Request request, Response response) -> { // Returns user information
		User user = Users.getUserById(SessionController.getUserId(request)); // Get logged in user
		return Result.superUltraJsonData(true, Server.getGson().toJsonTree(user)); // Return user in JSON
	};
	
	public static Route subscriptionInfo = (Request request, Response response) -> {
		return Result.superUltraJsonData(true,
				Server.getGson().toJsonTree(Subscriptions.getUserSubscription(SessionController.getUserId(request)))); // Return user in JSON
	};
	
	// Activities
	public static Route activityList = (Request request, Response response) -> {
		return Result.superUltraJsonData(true, 
				Server.getGson().toJsonTree(Activities.getActivities())); // Return user in JSON
	};
	
	// User activities
	public static Route userActivityAdd = (Request request, Response response) -> {
		// Temporary variables
		Integer activityId;
		
		// Checking if we have all required params passed in request
		if (request.queryParams("activityId") == null || request.queryParams("activityId").isEmpty()) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid request!");
		}
		
		// Activity ID
		try {
			activityId = Integer.parseInt(request.queryParams("activityId")); // Parsing id as int
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid activity id value!");
		}
		
		Activity activity = Activities.getActivityById(activityId);
		if (activity == null) { // Check if activity exists
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid activity id!");
		}
		
		boolean result = UserActivities.addUserActivity(Users.getUserById(SessionController.getUserId(request)), activity);
		if (!result) { // Check if it has been added without any problems
			response.status(500); // Return 500 - Internal Server Error
			return Result.superUltraStatus(false, "Couldn't add user activity.");
		}
		return Result.superUltraStatus(true, activity.getName() + " activity has been added to your profile!");
	};
	
	public static Route userActivityDel = (Request request, Response response) -> {
		// Temporary variables
		Integer activityId;

		// Activity ID
		try {
			activityId = Integer.parseInt(request.params(":id")); // Parsing id as int
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid activity id value!");
		}
		
		Activity activity = Activities.getActivityById(activityId);
		if (activity == null) { // Check if activity exists
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid activity id!");
		}
		
		boolean result = UserActivities.delUserActivity(Users.getUserById(SessionController.getUserId(request)), activity);
		if (!result) { // Check if it has been added without any problems
			response.status(500); // Return 500 - Internal Server Error
			return Result.superUltraStatus(false, "Couldn't remove user activity.");
		}
		return Result.superUltraStatus(true, activity.getName() + " activity has been removed from your profile!");
	};
	
	public static Route userActivityList = (Request request, Response response) -> {
		return Result.superUltraJsonData(true, 
				Server.getGson().toJsonTree(UserActivities.getUserActivities(SessionController.getUserId(request)))); // Return user in JSON
	};
	
	public static Route userActivityInfo = (Request request, Response response) -> {
		// Temporary variables
		Integer activityId;
		
		// Activity ID
		try {
			activityId = Integer.parseInt(request.params(":id")); // Parsing id as int
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid activity id value!");
		}
		
		Activity activity = Activities.getActivityById(activityId);
		if (activity == null) { // Check if activity exists
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid activity id!");
		}
		
		if (!UserActivities.getUserActivities(SessionController.getUserId(request)).contains(activity.getId())) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "You don't have this activity!");
		}
		
		return Result.superUltraJsonData(true, 
				Server.getGson().toJsonTree(activity)); // Return user in JSON
	};
	
	// Workouts
	public static Route workoutAdd = (Request request, Response response) -> {
		// Temporary variables
		Integer workoutTypeId;
		Integer numberOfWorkouts;
		Timestamp date;
		
		// Checking if we have all required params passed in request
		if (request.queryParams("workoutTypeId") == null || request.queryParams("workoutTypeId").isEmpty() ||
			request.queryParams("numberOfWorkouts") == null || request.queryParams("numberOfWorkouts").isEmpty() ||
			request.queryParams("date") == null || request.queryParams("date").isEmpty()) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid request!");
		}
		
		// Workout Type ID
		try {
			workoutTypeId = Integer.parseInt(request.queryParams("workoutTypeId")); // Parsing id as int
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid workout id value!");
		}
		
		WorkoutType wT = WorkoutTypes.getWorkoutTypeById(workoutTypeId);
		if (wT == null) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid workout type!");
		}
		
		// Number of workouts
		try {
			numberOfWorkouts = Integer.parseInt(request.queryParams("numberOfWorkouts")); // Parsing id as int
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid number of workouts value!");
		}
		if (numberOfWorkouts < 0 && numberOfWorkouts > 5000) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Impossible or exceeded number of workouts!");
		}
		
		// Date
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"); // Initializing date format
		    Date parsedDate = dateFormat.parse(request.queryParams("date")); // Parsing date
		    date = new Timestamp(parsedDate.getTime()); // Converting it to timestamp
		} catch (Exception ignored) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid date value!");
		}
		
		Workout workout = new Workout(-1, SessionController.getUserId(request), wT, numberOfWorkouts, date);
		boolean result = Workouts.addWorkout(workout);
		if (!result) { // Check if it has been added without any problems
			response.status(500); // Return 500 - Internal Server Error
			return Result.superUltraStatus(false, workout.getType().getType() + " workout couldn't be added to your profile!");
		}
		return Result.superUltraStatus(true, workout.getType().getType() + " workout has been added to your profile!");
	};

	public static Route workoutDel = (Request request, Response response) -> {
		// Temporary variables
		Integer workoutId;
		
		try {
			workoutId = Integer.parseInt(request.params(":id")); // Parsing id as int
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid workout id value!");
		}
		
		Workout workout = Workouts.getWorkoutById(workoutId);
		if (workout == null || workout.getUserId() != SessionController.getUserId(request)) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid workout!");
		}
		
		boolean result = Workouts.delWorkout(workout);
		if (!result) { // Check if it has been added without any problems
			response.status(500); // Return 500 - Internal Server Error
			return Result.superUltraStatus(false, workout.getType().getType() + " workout couldn't be removed from your profile!");
		}
		return Result.superUltraStatus(true, workout.getType().getType() + " workout has been removed from your profile!");
	};
	
	public static Route workoutEdit = (Request request, Response response) -> {
		// Temporary variables
		Workout workout;
		
		// Old workout
		try {
			workout = Workouts.getWorkoutById(Integer.parseInt(request.params(":id"))); // Parsing workoutId as id for getting workout
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid workout id value!");
		}
		
		// Workout type
		if (request.queryParams("workoutTypeId") != null && !request.queryParams("workoutTypeId").isEmpty()) { // Check if we have workout type in request
			int workoutTypeId;
			try {
				workoutTypeId = Integer.parseInt(request.queryParams("workoutTypeId")); // Parsing id as int
			} catch (NumberFormatException e) {
				response.status(400); // Return 400 - Bad Request
				return Result.superUltraStatus(false, "Invalid workout id value!");
			}
			
			WorkoutType wT = WorkoutTypes.getWorkoutTypeById(workoutTypeId);
			if (wT == null) {
				response.status(400); // Return 400 - Bad Request
				return Result.superUltraStatus(false, "Invalid workout type!");
			}
			
			workout.setType(wT);
		}
		
		// Number of workouts
		if (request.queryParams("numberOfWorkouts") != null && !request.queryParams("numberOfWorkouts").isEmpty()) {
			int numberOfWorkouts;
			try {
				numberOfWorkouts = Integer.parseInt(request.queryParams("numberOfWorkouts")); // Parsing id as int
			} catch (NumberFormatException e) {
				response.status(400); // Return 400 - Bad Request
				return Result.superUltraStatus(false, "Invalid number of workouts value!");
			}
			if (numberOfWorkouts < 0 && numberOfWorkouts > 5000) {
				response.status(400); // Return 400 - Bad Request
				return Result.superUltraStatus(false, "Impossible or exceeded number of workouts!");
			}
			
			workout.setNumberOfWorkouts(numberOfWorkouts);
		}
		
		// Date
		if (request.queryParams("date") != null && !request.queryParams("date").isEmpty()) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"); // Initializing date format
			    Date parsedDate = dateFormat.parse(request.queryParams("date")); // Parsing date
			    workout.setDate(new Timestamp(parsedDate.getTime())); // Converting it to timestamp
			} catch (Exception ignored) {
				response.status(400); // Return 400 - Bad Request
				return Result.superUltraStatus(false, "Invalid date value!");
			}
		}
		
		boolean result = Workouts.editWorkout(workout);
		if (!result) { // Check if it has been edited without any problems
			response.status(500); // Return 500 - Internal Server Error
			return Result.superUltraStatus(false, "Workout ID: " + workout.getId() + " couldn't be modified!");
		}
		return Result.superUltraStatus(true, "Workout ID: " + workout.getId() + " has been modified!");
	};
	
	public static Route workoutList = (Request request, Response response) -> {
		return Result.superUltraJsonData(true,
				Server.getGson().toJsonTree(Workouts.getUserWorkouts(SessionController.getUserId(request)))); // Return workouts in JSON
	};
	
	public static Route workoutTypeList = (Request request, Response response) -> {
		return Result.superUltraJsonData(true, Server.getGson().toJsonTree(WorkoutTypes.getWorkoutTypes())); // Returns all data in JSON
	};
	
}
