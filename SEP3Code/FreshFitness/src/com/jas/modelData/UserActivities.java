package com.jas.modelData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jas.DataSource;
import com.jas.model.Activity;
import com.jas.model.User;

public class UserActivities {

	private static HashMap<Integer, List<Integer>> userActivities; // User ID, List of Activity ID
	
	static { // Will be called as soon as this class will be executed.
		getDataFromDataBase(); // Pulls data from database into variable.
	}
	
	private static void getDataFromDataBase() {
		// Initializing userActivities variable
		try {
			String SQL_QUERY = "select * from userActivity;";
			Connection conn = DataSource.getConnection(); // Getting connection to database
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY); // Preparing the query
			ResultSet rs = pst.executeQuery(); // Executing query
			
			HashMap<Integer, List<Integer>> temp = new HashMap<>(); // Initializing temporary userActivities list - temp one so while updating there still exists current data
			
			while (rs.next()) {
				List<Integer> uA;
				if (temp.containsKey(rs.getInt("userid"))) {
					uA = temp.get(rs.getInt("userid"));
				} else {
					uA = new ArrayList<>();
				}
				
				uA.add(rs.getInt("activityid"));
				temp.put(rs.getInt("userid"), uA);
			}

			userActivities = temp; // Assigning temporary userActivities list to activities variable
			pst.close();
		} catch (SQLException error) { // Catch any SQL errors
			System.out.println("[Error] Couldn't initialize User activities data! Reason: " + error.getMessage()); // Show it to the console
		}
	}
	
	public static void refreshData() { // Refreshes data in variable
		getDataFromDataBase();
	}
	
	public static boolean addUserActivity(User user, Activity activity) {
		try {
			Connection conn = DataSource.getConnection();
			
			String SQL_QUERY = "insert into userActivity(userid, activityid) values (?,?)";
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY);
			pst.setInt(1, user.getId());
			pst.setInt(2, activity.getId());
			int rc = pst.executeUpdate();
			
			if (rc > 0) { // Insert to database was success
				List<Integer> uA;
				if (userActivities.containsKey(user.getId())) {
					uA = userActivities.get(user.getId());
				} else {
					uA = new ArrayList<>();
				}
				
				uA.add(activity.getId());
				userActivities.put(user.getId(), uA);
				return true;
			}
		} catch (SQLException error) {
			System.out.println("[Error] Couldn't add user activity to database! Reason: " + error.getMessage()); // Show it to the console
		}
		return false;
	}
	
	public static boolean delUserActivity(User user, Activity activity) {
		try {
			Connection conn = DataSource.getConnection();
			
			String SQL_QUERY = "delete from userActivity where userid=? and activityid=?";
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY);
			pst.setInt(1, user.getId());
			pst.setInt(2, activity.getId());
			int rc = pst.executeUpdate();
			pst.close();
			
			if (rc > 0) { // Delete from database was success
				if (userActivities.containsKey(user.getId())) {
					List<Integer> uA = userActivities.get(user.getId());
					uA.remove(activity.getId());
					userActivities.put(user.getId(), uA);
				}
				return true;
			}
		} catch (SQLException error) {
			System.out.println("[Error] Couldn't delete activity from database! Reason: " + error.getMessage()); // Show it to the console
		}
		return false;
	}
	
	public static List<Integer> getUserActivities(int id) { // Returns activities by user ID
		return userActivities.getOrDefault(id, null);
	}
}
