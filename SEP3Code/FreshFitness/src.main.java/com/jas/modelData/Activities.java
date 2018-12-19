package com.jas.modelData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jas.DataSource;
import com.jas.model.Activity;
import com.jas.model.User;

public class Activities {

	private static List<Activity> activities;
	
	static { // Will be called as soon as this class will be executed.
		getDataFromDataBase(); // Pulls data from database into variable.
	}
	
	private static void getDataFromDataBase() {
		String SQL_QUERY = "select * from activity;";
		// Initializing activities variable
		try(Connection conn = DataSource.getConnection(); // Getting connection to database
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY); /* Preparing the query */) {
			ResultSet rs = pst.executeQuery(); // Executing query
			
			List<Activity> temp = new ArrayList<>(); // Initializing temporary activities list - temp one so while updating there still exists current data
			
			while (rs.next()) {
				String iName = "Unknown";
				User user = Users.getUserById(rs.getInt("instructorID"));
				if (user != null) {
					iName = user.getFirstName() + " " + user.getLastName();
				}
				
				temp.add(new Activity(
					rs.getInt("id"), 
					rs.getString("name"), 
					rs.getTimestamp("startTime"), 
					rs.getTimestamp("endTime"),
					rs.getInt("instructorID"),
					iName
				));
			}

			activities = temp; // Assigning temporary activities list to activities variable
			pst.close();
		} catch (SQLException error) { // Catch any SQL errors
			System.out.println("[Error] Couldn't initialize Activities data! Reason: " + error.getMessage()); // Show it to the console
		}
	}
	
	public static void refreshData() { // Refreshes data in variable
		getDataFromDataBase();
	}
	
	public static boolean addActivity(Activity activity) { // Adds activity to database
		String SQL_QUERY = "insert into activity (name, startTime, endTime, instructorID) values (?,?,?,?)";
		try(Connection conn = DataSource.getConnection();
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY, Statement.RETURN_GENERATED_KEYS);) {
			
			pst.setString(1, activity.getName());
			pst.setTimestamp(2, activity.getStartTime());
			pst.setTimestamp(3, activity.getEndTime());
			pst.setInt(4, activity.getInstructorId());
			int rc = pst.executeUpdate();
			
			if (rc > 0) { // Insert to database was success
				ResultSet gk = pst.getGeneratedKeys();
				if (gk.isBeforeFirst()) {
					gk.next();
					activity.setId(gk.getInt("id")); // Assigns new id to activity
					activities.add(activity);
					return true;
				}
			}
		} catch (SQLException error) {
			System.out.println("[Error] Couldn't add activity to database! Reason: " + error.getMessage()); // Show it to the console
		}
		return false;
	}
	
	public static boolean delActivity(Activity activity) { // Removes activity from database
		String SQL_QUERY = "delete from activity where id=?";
		try(Connection conn = DataSource.getConnection();
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY);) {
			pst.setInt(1, activity.getId());
			
			UserActivities.delUserActivities(activity); // Remove user activities of this activity first.
			int rc = pst.executeUpdate();
			pst.close();
			
			if (rc > 0) { // Delete from database was success
				activities.remove(activities.indexOf(activity));
				return true;
			}
		} catch (SQLException error) {
			System.out.println("[Error] Couldn't delete activity from database! Reason: " + error.getMessage()); // Show it to the console
		}
		return false;
	}
	
	public static boolean editActivity(Activity activity) { // Updates activity in database
		String SQL_QUERY = "update activity set name = ?, startTime = ?, endTime = ?, instructorID = ? where id = ?";
		try(Connection conn = DataSource.getConnection();
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY);) {
			Activity old = getActivityById(activity.getId());
			
			pst.setString(1, activity.getName());
			pst.setTimestamp(2, activity.getStartTime());
			pst.setTimestamp(3, activity.getEndTime());
			pst.setInt(4, activity.getInstructorId());
			pst.setInt(5, activity.getId());
			int rc = pst.executeUpdate();
			pst.close();
			
			if (rc > 0) { // Update on database was success
				activities.set(activities.indexOf(old), activity);
				return true;
			}
		} catch (SQLException error) {
			System.out.println("[Error] Couldn't update activity on database! Reason: " + error.getMessage()); // Show it to the console
		}
		return false;
	}
	
	public static List<Activity> getActivities() { // Returns a list of activities
		return activities;
	}
	
	public static List<Activity> getInstructorActivities(int instructorId) { // Returns activities which belong to an instructor
		return activities.stream() // In Java 8 we can simply use streams instead of for loop :)
				.filter(a -> a.getInstructorId() == instructorId) // Filter the list
				.collect(Collectors.toList()); // Return a filtered list
	}
	
	public static Activity getActivityById(int id) { // Returns a specific activity by it's ID
		return activities.stream() // In Java 8 we can simply use streams instead of for loop :)
			.filter(a -> a.getId() == id) // Filter the list
			.findFirst() // Get first activity from filtered list
			.orElse(null); // Return null if there's no activity
	}
	
}
