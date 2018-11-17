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
		getDataFromDataBase(); // Pulls users data from database into users variable.
	}
	
	private static void getDataFromDataBase() {
		// Initializing subscriptions variable
		try {
			String SQL_QUERY = "select * from userrole;";
			Connection conn = DataSource.getConnection(); // Getting connection to database
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY); // Preparing the query
			ResultSet rs = pst.executeQuery(); // Executing query
			
			List<UserRole> temp = new ArrayList<>(); // Initializing temporary user roles list - temp one so while updating there still exists current data
			
			while (rs.next()) {
				temp.add(new UserRole(rs.getInt("id"), rs.getString("role")));
			}

			userRoles = temp; // Assigning temporary user roles list to subscriptionTypes variable
		} catch (SQLException error) { // Catch any SQL errors
			System.out.println("[Error] Couldn't initialize User roles data! Reason: " + error.getMessage()); // Show it to the console
		}
	}
	
	public static void refreshData() {
		getDataFromDataBase();
	}
	
	public static List<UserRole> getUserRoles() {
		return userRoles;
	}
	
	public static UserRole getUserRoleById(int id) {
		return userRoles.stream()
				.filter(ur -> ur.getId() == id)
				.findFirst()
				.orElse(null);
	}
}
