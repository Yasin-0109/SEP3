package com.jas.modelData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jas.DataSource;
import com.jas.model.UserRole;

public class UserRoles {

	private static List<UserRole> userRoles;
	
	static { // Will be called as soon as this class will be executed.
		getDataFromDataBase(); // Pulls data from database into variable.
	}
	
	private static void getDataFromDataBase() {
		// Initializing user roles variable
		String SQL_QUERY = "select * from userrole;";
		try(Connection conn = DataSource.getConnection(); // Getting connection to database
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY); /* Preparing the query */) {
			ResultSet rs = pst.executeQuery(); // Executing query
			
			List<UserRole> temp = new ArrayList<>(); // Initializing temporary user roles list - temp one so while updating there still exists current data
			
			while (rs.next()) { // Add user roles from database
				temp.add(new UserRole(rs.getInt("id"), rs.getString("role")));
			}

			userRoles = temp; // Assigning temporary user roles list to user roles variable
		} catch (SQLException error) { // Catch any SQL errors
			System.out.println("[Error] Couldn't initialize User roles data! Reason: " + error.getMessage()); // Show it to the console
		}
	}
	
	public static void refreshData() { // Refreshes data in variable
		getDataFromDataBase();
	}
	
	public static List<UserRole> getUserRoles() { // Returns a list of user roles
		return userRoles;
	}
	
	public static UserRole getUserRoleById(int id) { // Returns a specific user role by it's ID
		return userRoles.stream() // In Java 8 we can simply use streams instead of for loop :)
				.filter(ur -> ur.getId() == id) // Filter the list
				.findFirst() // Get first user role from filtered list
				.orElse(null); // Return null if there's no user role
	}
}
