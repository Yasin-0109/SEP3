package com.jas.modelData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jas.DataSource;
import com.jas.model.Activity;

public class Activities {


	private static List<Activity> activities;
	
	static { // Will be called as soon as this class will be executed.
		getDataFromDataBase(); // Pulls users data from database into users variable.
	}
	
	private static void getDataFromDataBase() {
		// Initializing activities variable
		try {
			String SQL_QUERY = "select * from activity;";
			Connection conn = DataSource.getConnection(); // Getting connection to database
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY); // Preparing the query
			ResultSet rs = pst.executeQuery(); // Executing query
			
			List<Activity> temp = new ArrayList<>(); // Initializing temporary activities list - temp one so while updating there still exists current data
			
			while (rs.next()) {
				temp.add(new Activity(
					rs.getInt("id"), 
					rs.getString("name"), 
					rs.getDate("date"), 
					rs.getTime("startTime"), 
					rs.getTime("endTime"),
					rs.getInt("instructorID")
				));
			}

			activities = temp; // Assigning temporary activities list to subscriptionTypes variable
		} catch (SQLException error) { // Catch any SQL errors
			System.out.println("[Error] Couldn't initialize Subscription types data! Reason: " + error.getMessage()); // Show it to the console
		}
	}
	
	public static void refreshData() {
		getDataFromDataBase();
	}
	
	public static List<Activity> getActivities() {
		return activities;
	}
	
	public static Activity getActivityById(int id) {
		return activities.stream()
			.filter(a -> a.getId() == id)
			.findFirst()
			.orElse(null);
	}
}
