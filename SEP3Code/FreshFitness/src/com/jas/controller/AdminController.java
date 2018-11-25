package com.jas.controller;

import com.jas.model.Activity;
import com.jas.model.Subscription;
import com.jas.model.SubscriptionType;
import com.jas.model.User;
import com.jas.model.UserRole;
import com.jas.model.WorkoutType;

import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.jas.Main;
import com.jas.modelData.Activities;
import com.jas.modelData.SubscriptionTypes;
import com.jas.modelData.Subscriptions;
import com.jas.modelData.UserRoles;
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
		if (Users.getUserById(instructorId) == null) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid instructor id!");
		}
		
		Activity activity = new Activity(-1, name, startTime, endTime, instructorId); // Creating activity based on request
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
			activity = Activities.getActivityById(Integer.parseInt(request.params(":id"))); // Parsing id for getting activity
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid activity id value!");
		}
		if (activity == null) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid activity id!");
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
		int instructorId = -1;
		if (request.queryParams("instructorId") != null || !request.queryParams("instructorId").isEmpty()) { // Check if we have instructor id in request
			try {
				instructorId = Integer.parseInt(request.queryParams("instructorId")); // Parsing instructorId as integer
			} catch (NumberFormatException e) {
				response.status(400); // Return 400 - Bad Request
				return Result.superUltraStatus(false, "Invalid instructor id value!");
			}
		}
		if (Users.getUserById(instructorId) == null) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid instructor id!");
		}
		activity.setInstructorId(instructorId);

		boolean result = Activities.editActivity(activity); // Edit activity to have new data
		if (!result) { // Check if it has been edited without any problems
			response.status(500); // Return 500 - Internal Server Error
			return Result.superUltraStatus(false, "Couldn't modify activity.");
		}
		return Result.superUltraStatus(true, activity.getName() + " has been edited.");
	};
	
	public static Route activityList = (Request request, Response response) -> {
		return Result.superUltraJsonData(true, Main.getServer().getGson().toJsonTree(Activities.getActivities())); // Returns all data in JSON
	};
	
	// Subscription
	public static Route subscriptionAdd = (Request request, Response response) -> {
		// Temporary variables
		Integer userId;
		Timestamp validFrom;
		Timestamp validTo;
		SubscriptionType subscriptionType;
		
		// Checking if we have all required params passed in request
		if (request.queryParams("userId") == null || request.queryParams("userId").isEmpty() ||
			request.queryParams("validFrom") == null || request.queryParams("validFrom").isEmpty() ||
			request.queryParams("validTo") == null || request.queryParams("validTo").isEmpty() ||
			request.queryParams("subscriptionType") == null || request.queryParams("subscriptionType").isEmpty()) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid request!");
		}
		
		// User ID
		try {
			userId = Integer.parseInt(request.queryParams("userId")); // Parsing userId as integer
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid user id value!");
		}
		if (Users.getUserById(userId) == null) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "User doesn't exists!");
		}
		
		// Valid from
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"); // Initializing date format
		    Date parsedDate = dateFormat.parse(request.queryParams("validFrom")); // Parsing date
		    validFrom = new Timestamp(parsedDate.getTime()); // Converting it to timestamp
		} catch (Exception ignored) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid valid from value!");
		}
		
		// Valid to
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"); // Initializing date format
		    Date parsedDate = dateFormat.parse(request.queryParams("validTo")); // Parsing date
		    validTo = new Timestamp(parsedDate.getTime()); // Converting it to timestamp
		} catch (Exception ignored) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid valid to value!");
		}
		
		// Subscription type
		try {
			subscriptionType = SubscriptionTypes.getSubscriptionTypeById(Integer.parseInt(request.queryParams("subscriptionType"))); // Parsing subscriptionType as integer
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid subscription type value!");
		}
		if(subscriptionType == null) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid subscription type!");
		}
		
		Subscription subscription = new Subscription(-1, userId, validFrom, validTo, subscriptionType); // Create subscription based on request
		boolean result = Subscriptions.addSubscription(subscription); // Adding subscription
		if (!result) { // Check if it has been added without any problems
			response.status(500); // Return 500 - Internal Server Error
			return Result.superUltraStatus(false, "Couldn't add subscription.");
		}
		return Result.superUltraStatus(true, "Subscription has been added.");
	};

	public static Route subscriptionDel = (Request request, Response response) -> {
		int id;
		try {
			id = Integer.parseInt(request.params(":id")); // Parsing id as integer
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid id value!");
		}
		
		Subscription subscription = Subscriptions.getSubscriptionById(id); // Getting subscription by it's ID
		if (subscription == null) { // Check if subscription exists
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid subscription id!");
		}
		
		boolean result = Subscriptions.delSubscription(subscription); // Removing subscription
		if (!result) { // Check if it has been deleted without any problems
			response.status(500); // Return 500 - Internal Server Error
			return Result.superUltraStatus(false, "Couldn't remove subscription.");
		}
		return Result.superUltraStatus(true, "Subscription has been removed.");
	};

	public static Route subscriptionEdit = (Request request, Response response) -> {
		// Temporary variables
		Subscription subscription;

		// Old subscription
		try {
			subscription = Subscriptions.getSubscriptionById(Integer.parseInt(request.params(":id"))); // Parsing id as id for getting subscription
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid id value!");
		}
		if (subscription == null) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid subscription id!");
		}
		
		// Valid from
		if (request.queryParams("validFrom") != null || !request.queryParams("validFrom").isEmpty()) { // Check if we have validFrom in request
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"); // Initializing date format
			    Date parsedDate = dateFormat.parse(request.queryParams("validFrom")); // Parsing date
			    subscription.setValidFrom(new Timestamp(parsedDate.getTime())); // Converting it to timestamp
			} catch (Exception ignored) {
				response.status(400); // Return 400 - Bad Request
				return Result.superUltraStatus(false, "Invalid valid from value!");
			}
		}
		
		// End time
		if (request.queryParams("validTo") != null || !request.queryParams("validTo").isEmpty()) { // Check if we have validTo in request
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"); // Initializing date format
			    Date parsedDate = dateFormat.parse(request.queryParams("validTo")); // Parsing date
			    subscription.setValidTo(new Timestamp(parsedDate.getTime())); // Converting it to timestamp
			} catch (Exception ignored) {
				response.status(400); // Return 400 - Bad Request
				return Result.superUltraStatus(false, "Invalid valid to value!");
			}
		}
		
		// Subscription type
		SubscriptionType sT = null;
		if (request.queryParams("subscriptionType") != null || !request.queryParams("subscriptionType").isEmpty()) { // Check if we have subscription type in request
			try {
				sT = SubscriptionTypes.getSubscriptionTypeById(Integer.parseInt(request.queryParams("subscriptionType"))); // Parsing subscriptionType as integer
			} catch (NumberFormatException e) {
				response.status(400); // Return 400 - Bad Request
				return Result.superUltraStatus(false, "Invalid subscription type value!");
			}
		}
		if (sT == null) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid subscription type!");
		}
		subscription.setSubscriptionType(sT);
		

		boolean result = Subscriptions.editSubscription(subscription); // Edit subscription to have new data
		if (!result) { // Check if it has been edited without any problems
			response.status(500); // Return 500 - Internal Server Error
			return Result.superUltraStatus(false, "Couldn't modify subscription.");
		}
		return Result.superUltraStatus(true, "Subscription has been edited.");
	};
	
	public static Route subscriptionList = (Request request, Response response) -> {
		return Result.superUltraJsonData(true, Main.getServer().getGson().toJsonTree(Subscriptions.getSubscriptions())); // Returns all data in JSON
	};
	
	// Users
	public static Route userAdd = (Request request, Response response) -> {
		// Temporary variables
		UserRole userRole;
		String firstName;
		String lastName;
		Timestamp dateOfBirth;
		String email;
		String password;
		Integer phoneNumber;
		
		// Checking if we have all required params passed in request
		if (request.queryParams("userRoleId") == null || request.queryParams("userRoleId").isEmpty() ||
			request.queryParams("firstName") == null || request.queryParams("firstName").isEmpty() ||
			request.queryParams("lastName") == null || request.queryParams("lastName").isEmpty() ||
			request.queryParams("dateOfBirth") == null || request.queryParams("dateOfBirth").isEmpty() ||
			request.queryParams("email") == null || request.queryParams("email").isEmpty() ||
			request.queryParams("password") == null || request.queryParams("password").isEmpty() ||
			request.queryParams("phoneNumber") == null || request.queryParams("phoneNumber").isEmpty()) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid request!");
		}
		
		// User role
		try {
			userRole = UserRoles.getUserRoleById(Integer.parseInt(request.queryParams("userRoleId"))); // Parsing as int
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid user role id value!");
		}
		if(userRole == null) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid user role!");
		}
		
		// First name
		firstName = request.queryParams("firstName");
		
		// Last name
		lastName = request.queryParams("lastName");
		
		// Date of birth
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"); // Initializing date format
		    Date parsedDate = dateFormat.parse(request.queryParams("dateOfBirth")); // Parsing date
		    dateOfBirth = new Timestamp(parsedDate.getTime()); // Converting it to timestamp
		} catch (Exception ignored) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid date of birth value!");
		}
		
		// Email
		email = request.queryParams("email");
		
		// Password
		password = request.queryParams("password");
		
		// Phone number
		try {
			phoneNumber = Integer.parseInt(request.queryParams("phoneNumber")); // Parsing as int
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid phone number value!");
		}
		
		User user = new User(-1, email, firstName, lastName, phoneNumber, dateOfBirth, userRole);
		user.setPassword(password);
		
		boolean result = Users.addUser(user);
		if (!result) { // Check if it has been added without any problems
			response.status(500); // Return 500 - Internal Server Error
			return Result.superUltraStatus(false, "Couldn't add user.");
		}
		return Result.superUltraStatus(true, "User has been added.");
	};
	
	public static Route userDel = (Request request, Response response) -> {		
		int id;
		try {
			id = Integer.parseInt(request.params(":id")); // Parsing id as integer
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid id value!");
		}
		
		User user = Users.getUserById(id); // Getting user by it's ID
		if (user == null) { // Check if user exists
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid user id!");
		}
		
		boolean result = Users.delUser(user); // Removing user
		if (!result) { // Check if it has been deleted without any problems
			response.status(500); // Return 500 - Internal Server Error
			return Result.superUltraStatus(false, "Couldn't remove user.");
		}
		return Result.superUltraStatus(true, "User has been removed.");
	};
	
	public static Route userEdit = (Request request, Response response) -> {
		// Temporary variables
		User user;
	
		// Old user
		try {
			user = Users.getUserById(Integer.parseInt(request.params(":id"))); // Parsing id as id for getting user
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid id value!");
		}
		if (user == null) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid User id value!");
		}
		
		// User role
		if (request.queryParams("userRoleId") != null || !request.queryParams("userRoleId").isEmpty()) { // Check if we have userRoleId in request
			UserRole userRole;
			try {
				userRole = UserRoles.getUserRoleById(Integer.parseInt(request.queryParams("userRoleId"))); // Parsing as int
			} catch (NumberFormatException e) {
				response.status(400); // Return 400 - Bad Request
				return Result.superUltraStatus(false, "Invalid user role id value!");
			}
			if(userRole == null) {
				response.status(400); // Return 400 - Bad Request
				return Result.superUltraStatus(false, "Invalid user role!");
			}
			user.setUserRole(userRole);
		}
		
		// First name
		if (request.queryParams("firstName") != null || !request.queryParams("firstName").isEmpty()) { // Check if we have firstName in request
			user.setFirstName(request.queryParams("firstName"));
		}
		
		// Last name
		if (request.queryParams("lastName") != null || !request.queryParams("lastName").isEmpty()) { // Check if we have lastName in request
			user.setLastName(request.queryParams("lastName"));
		}
		
		// Date of birth
		if (request.queryParams("dateOfBirth") != null || !request.queryParams("dateOfBirth").isEmpty()) { // Check if we have dateOfBirth in request
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"); // Initializing date format
			    Date parsedDate = dateFormat.parse(request.queryParams("dateOfBirth")); // Parsing date
			    user.setDateOfBirth(new Timestamp(parsedDate.getTime())); // Converting it to timestamp
			} catch (Exception ignored) {
				response.status(400); // Return 400 - Bad Request
				return Result.superUltraStatus(false, "Invalid date of birth value!");
			}
		}
		
		// Email
		if (request.queryParams("email") != null || !request.queryParams("email").isEmpty()) { // Check if we have email in request
			user.setEmail(request.queryParams("email"));
		}
		
		// Password
		if (request.queryParams("password") != null || !request.queryParams("password").isEmpty()) { // Check if we have password in request
			user.setPassword(request.queryParams("password"));
		}
		
		// Phone number
		if (request.queryParams("phoneNumber") != null || !request.queryParams("phoneNumber").isEmpty()) { // Check if we have phoneNumber in request
			try {
				user.setPhoneNumber(Integer.parseInt(request.queryParams("phoneNumber"))); // Parsing as int
			} catch (NumberFormatException e) {
				response.status(400); // Return 400 - Bad Request
				return Result.superUltraStatus(false, "Invalid phone number value!");
			}
		}
		
		boolean result = Users.editUser(user);
		if (!result) { // Check if it has been modified without any problems
			response.status(500); // Return 500 - Internal Server Error
			return Result.superUltraStatus(false, "Couldn't edit user.");
		}
		return Result.superUltraStatus(true, "User has been edited.");
	};
	
	public static Route userList = (Request request, Response response) -> {
		return Result.superUltraJsonData(true, Main.getServer().getGson().toJsonTree(Users.getUsers())); // Returns all data in JSON
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
		return Result.superUltraJsonData(true, Main.getServer().getGson().toJsonTree(user)); // Return data in JSON
	};
	
	// Workout Types
	public static Route workoutTypeAdd = (Request request, Response response) -> {
		// Temporary variables
		String type;
		
		// Checking if we have all required params passed in request
		if (request.queryParams("type") == null || request.queryParams("type").isEmpty()) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid request!");
		}
		
		// Type
		type = request.queryParams("type");
		
		WorkoutType workoutType = new WorkoutType(-1, type);
		
		boolean result = WorkoutTypes.addWorkoutType(workoutType); 
		if (!result) { // Check if it has been added without any problems
			response.status(500); // Return 500 - Internal Server Error
			return Result.superUltraStatus(false, "Couldn't add workout type.");
		}
		return Result.superUltraStatus(true, "Workout type has been added.");
	};

	public static Route workoutTypeDel = (Request request, Response response) -> {
		int id;
		try {
			id = Integer.parseInt(request.params(":id")); // Parsing id as integer
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid id value!");
		}
		
		WorkoutType workoutType = WorkoutTypes.getWorkoutTypeById(id); // Getting workout type by it's ID
		if (workoutType == null) { // Check if workout type exists
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid workout type id!");
		}
		
		boolean result = WorkoutTypes.delWorkoutType(workoutType); // Removing workout type
		if (!result) { // Check if it has been deleted without any problems
			response.status(500); // Return 500 - Internal Server Error
			return Result.superUltraStatus(false, "Couldn't remove workout type.");
		}
		return Result.superUltraStatus(true, "Workout type has been removed.");
	};
	
	public static Route workoutTypeEdit = (Request request, Response response) -> {
		// Temporary variables
		WorkoutType workoutType;
	
		// Old workoutType
		try {
			workoutType = WorkoutTypes.getWorkoutTypeById(Integer.parseInt(request.params(":id"))); // Parsing id for getting workout type
		} catch (NumberFormatException e) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid id value!");
		}
		if (workoutType == null) {
			response.status(400); // Return 400 - Bad Request
			return Result.superUltraStatus(false, "Invalid workout type id!");
		}
		
		// Type
		if (request.queryParams("type") != null || !request.queryParams("type").isEmpty()) { // Check if we have type in request
			workoutType.setType(request.queryParams("type"));
		}
		
		boolean result = WorkoutTypes.editWorkoutType(workoutType);
		if (!result) { // Check if it has been modified without any problems
			response.status(500); // Return 500 - Internal Server Error
			return Result.superUltraStatus(false, "Couldn't edit workout type.");
		}
		return Result.superUltraStatus(true, "Workout type has been edited.");
	};
	
	public static Route workoutTypeList = (Request request, Response response) -> {
		return Result.superUltraJsonData(true, Main.getServer().getGson().toJsonTree(WorkoutTypes.getWorkoutTypes())); // Returns all data in JSON
	};

}
