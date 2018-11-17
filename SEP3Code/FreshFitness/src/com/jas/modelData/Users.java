package com.jas.modelData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jas.DataSource;
import com.jas.model.Activity;
import com.jas.model.Subscription;
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
				ArrayList<Activity> userActivity = new ArrayList<>();
				PreparedStatement pst2 = conn.prepareStatement("select * from userActivity where userid = ?");
				pst2.setInt(1, user.getId());
				ResultSet rs2 = pst2.executeQuery();
				while(rs2.next()) {
					userActivity.add(Activities.getActivityById(rs2.getInt("activityid")));
				}
				user.setActivities(userActivity);
				
				// User subscription
				PreparedStatement pst3 = conn.prepareStatement("select * from subscription where userid = ?;");
				pst3.setInt(1, user.getId());
				ResultSet rs3 = pst3.executeQuery();
				if (rs3.isBeforeFirst()) { // Simply - check if result set is not empty.
					rs3.next();
					user.setSubscription(new Subscription(
						rs3.getInt("id"),
						user.getId(),
						rs3.getDate("validfrom"),
						rs3.getDate("validto"),
						SubscriptionTypes.getSubscriptionTypeById(rs3.getInt("subscriptiontypeid"))
					));
				}
				
				// User workouts
				ArrayList<Workout> userWorkouts = new ArrayList<>();
				PreparedStatement pst4 = conn.prepareStatement("select * from workout where userid = ?;");
				pst4.setInt(1, user.getId());
				ResultSet rs4 = pst4.executeQuery();
				while(rs4.next()) {
					userWorkouts.add(new Workout(
						rs4.getInt("id"),
						rs4.getInt("numberofworkouts"),
						WorkoutTypes.getWorkoutTypeById(rs4.getInt("workouttypeid"))
					));
				}
				user.setWorkouts(userWorkouts);

				temp.add(user);
			}
			
			users = temp; // Assigning temporary users list to users variable
		} catch (SQLException error) { // Catch any SQL errors
			System.out.println("[Error] Couldn't initialize Users data! Reason: " + error.getMessage()); // Show it to the console
		}
	}
	
	public static void refreshData() {
		getDataFromDataBase();
	}
	
	/**
	 * Gets all existing users.
	 * @return
	 */
	public static List<User> getUsers() {
		return users;
	}
	
	/**
	 * Gets user by given id
	 * @param id Id of user
	 * @return User
	 */
	public static User getUserById(int id) {
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
	public static User getUserByEmail(String email) {
		return users.stream()
				.filter(user -> user.getEmail().equalsIgnoreCase(email))
				.findFirst()
				.orElse(null); // In Java 8 we can simply use streams instead of for loop :)
	}
	
	
}
