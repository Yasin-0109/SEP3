package com.jas.modelData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jas.DataSource;
import com.jas.model.EUserRole;
import com.jas.model.User;

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
			String SQL_QUERY = "select * from users;";
			Connection conn = DataSource.getConnection(); // Getting connection to database
			PreparedStatement pst1 = conn.prepareStatement("set search_path = 'freshfitness';"); // Preparing the query
			pst1.execute(); // Executing query
			
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY); // Preparing the query
			ResultSet rs = pst.executeQuery(); // Executing query
			
			List<User> temp = new ArrayList<>(); // Initializing temporary users list - temp one so when updating there still exists current data
			
			while (rs.next()) { // Loop through all returned rows
				User user = new User(); // Creating a new user
				user.setID(rs.getInt("id")); // Setting it's ID
				user.setUserRole(EUserRole.ERole.fromInt(rs.getInt("userroleid"))); // Setting it's Role
				user.setFirstName(rs.getString("firstname")); // Setting it's first name
				user.setLastName(rs.getString("lastname")); // Setting it's last name
				user.setDateOfBirth(rs.getDate("dateofbirth")); // Setting it's date of birth
				user.setEmail(rs.getString("email")); // Setting it's email
				user.setPassword(rs.getString("password")); // Setting it's password
				user.setPhoneNumber(rs.getInt("phonenumber")); // Setting it's phone number
				
				temp.add(user); // Adding it to temporary users list
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
			if (user.getID() == id) {
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
