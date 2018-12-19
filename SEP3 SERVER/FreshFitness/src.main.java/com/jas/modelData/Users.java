package com.jas.modelData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jas.DataSource;
import com.jas.model.Subscription;
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
		String SQL_QUERY = "select * from users;";
		try(Connection conn = DataSource.getConnection(); // Getting connection to database
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY); /* Preparing the query */) {
			
			// All values from `users` table.
			ResultSet rs = pst.executeQuery(); // Executing query	
			
			List<User> temp = new ArrayList<>(); // Initializing temporary users list - temp one so while updating there still exists current data
			
			while (rs.next()) { // Loop through all returned rows
				User user = new User(rs.getInt("id"), // Setting it's ID
						rs.getString("email"), // Setting it's email
						rs.getString("firstname"), // Setting it's first name
						rs.getString("lastname"), // Setting it's last name
						rs.getInt("phonenumber"), // Setting it's phone number
						rs.getTimestamp("dateofbirth"), // Setting it's date of birth
						UserRoles.getUserRoleById(rs.getInt("userroleid")) // Setting it's Role
				); // Creating a new user
				
				user.setPassword(rs.getString("password")); // Setting it's password
			
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
	
	public static boolean addUser(User user) { // Adds user to database
		String SQL_QUERY = "INSERT INTO users (userroleid,firstname,lastname,dateofbirth,email,password,phonenumber) VALUES (?,?,?,?,?,?,?)";
		try(Connection conn = DataSource.getConnection();
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY, Statement.RETURN_GENERATED_KEYS);) {
			pst.setInt(1, user.getUserRole().getId());
			pst.setString(2, user.getFirstName());
			pst.setString(3, user.getLastName());
			pst.setTimestamp(4, user.getDateOfBirth());
			pst.setString(5, user.getEmail());
			pst.setString(6, user.getPassword());
			pst.setInt(7, user.getPhoneNumber());
			int rc = pst.executeUpdate();
			
			if (rc > 0) { // Insert to database was success
				ResultSet gk = pst.getGeneratedKeys();
				if (gk.isBeforeFirst()) {
					gk.next();
					user.setId(gk.getInt("id")); // Assign new user Id to it
					users.add(user);
					return true;
				}
			}
		} catch (SQLException error) {
			System.out.println("[Error] Couldn't add user to database! Reason: " + error.getMessage()); // Show it to the console
		}
		return false;
	}
	
	public static boolean delUser(User user) { // Removes user from database
		String SQL_QUERY = "delete from users where id=?";
		try(Connection conn = DataSource.getConnection();
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY, Statement.RETURN_GENERATED_KEYS);) {
			pst.setInt(1, user.getId());
			int rc = pst.executeUpdate();
			pst.close();
			
			if (rc > 0) { // Delete from database was success
				// if user had subscription then remove it too
				Subscription sub = Subscriptions.getUserSubscription(user.getId());
				if (sub != null) {
					Subscriptions.delSubscription(sub);
				}
				
				users.remove(users.indexOf(user));
				return true;
			}
		} catch (SQLException error) {
			System.out.println("[Error] Couldn't delete user from database! Reason: " + error.getMessage()); // Show it to the console
		}
		return false;
	}
	
	public static boolean editUser(User user) { // Updates user in database
		String SQL_QUERY = "update users set userroleid = ?, firstname = ?,lastname = ?, dateofbirth = ?, email = ?, password = ?, phonenumber = ? where id = ?";
		try(Connection conn = DataSource.getConnection();
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY);) {
			User old = getUserById(user.getId());
		
			pst.setInt(1, user.getUserRole().getId());
			pst.setString(2, user.getFirstName());
			pst.setString(3, user.getLastName());
			pst.setTimestamp(4, user.getDateOfBirth());
			pst.setString(5, user.getEmail());
			pst.setString(6, user.getPassword());
			pst.setInt(7, user.getPhoneNumber());
			pst.setInt(8, user.getId());
			int rc = pst.executeUpdate();
			pst.close();
			
			if (rc > 0) { // Update on database was success
				users.set(users.indexOf(old), user);
				return true;
			}
		} catch (SQLException error) {
			System.out.println("[Error] Couldn't update user in database! Reason: " + error.getMessage()); // Show it to the console
		}
		return false;
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
