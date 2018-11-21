package com.jas.modelData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jas.DataSource;
import com.jas.model.Activity;
import com.jas.model.User;
import com.jas.model.Workout;

public class Users {

	/**
	 * ToDo:
	 * Think about having something to refresh this data.
	 * You shouldn't get all tables every request (that's why it's cached here).
	 * At least it's on the server so we can update it once we add adding users
	 * or modify them in database. Then we can update this cache :P.
	 */
	
	private static List<User> users;
	
	static { // Will be called as soon as this class will be executed.
		getDataFromDataBase(); // Pulls users data from database into users variable.
	}
	
	private static void getDataFromDataBase() {
		// Initializing users variable
		try {
			
			// All values from `users` table.
			String SQL_QUERY = "select * from users;";
			Connection conn = DataSource.getConnection(); // Getting connection to database
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY); // Preparing the query
			ResultSet rs = pst.executeQuery(); // Executing query	
			
			List<User> temp = new ArrayList<>(); // Initializing temporary users list - temp one so while updating there still exists current data
			
			while (rs.next()) { // Loop through all returned rows
				User user = new User(); // Creating a new user
				user.setId(rs.getInt("id")); // Setting it's ID
				user.setUserRole(UserRoles.getUserRoleById(rs.getInt("userroleid"))); // Setting it's Role
				user.setFirstName(rs.getString("firstname")); // Setting it's first name
				user.setLastName(rs.getString("lastname")); // Setting it's last name
				user.setDateOfBirth(rs.getDate("dateofbirth")); // Setting it's date of birth
				user.setEmail(rs.getString("email")); // Setting it's email
				user.setPassword(rs.getString("password")); // Setting it's password
				user.setPhoneNumber(rs.getInt("phonenumber")); // Setting it's phone number
				
				// User activities
				List<Activity> userActivity = new ArrayList<>(); // Create temporary user activity list
				PreparedStatement pst2 = conn.prepareStatement("select * from userActivity where userid = ?"); // Prepare the query
				pst2.setInt(1, user.getId()); // Set user id in query
				ResultSet rs2 = pst2.executeQuery(); // Executing query
				while(rs2.next()) { // Loop through all returned rows
					userActivity.add(Activities.getActivityById(rs2.getInt("activityid"))); // Add activity by it's ID.
				}
				user.setActivities(userActivity); // Set user activities
				
				// User subscription
				user.setSubscription(Subscriptions.getUserSubscription(user.getId())); // Set user subscription
				
				// User workouts
				user.setWorkouts(Workouts.getUserWorkouts(user.getId())); // Set user workouts

				temp.add(user); // Add user to temporary list
			}
			
			users = temp; // Assigning temporary users list to users variable
		} catch (SQLException error) { // Catch any SQL errors
			System.out.println("[Error] Couldn't initialize Users data! Reason: " + error.getMessage()); // Show it to the console
		}
	}
	
	public static void refreshData() { // Refreshes data in variable
		getDataFromDataBase();
	}
	
	/**
	 * Gets all existing users.
	 * @return
	 */
	public static List<User> getUsers() { // Returns a list of users
		return users;
	}
	
	/**
	 * Gets user by given id
	 * @param id Id of user
	 * @return User
	 */
	public static User getUserById(int id) { // Returns a user by it's ID
		for(User user : users) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}
	
	/**
	 * Gets user by its email address
	 * @param email Email addres of user
	 * @return
	 */
	public static User getUserByEmail(String email) { // Returns a user by it's email
		return users.stream() // In Java 8 we can simply use streams instead of for loop :)
				.filter(user -> user.getEmail().equalsIgnoreCase(email)) // Filter the list
				.findFirst() // Get first user from filtered list
				.orElse(null); // Return null if there's no user
	}
	
	
}
