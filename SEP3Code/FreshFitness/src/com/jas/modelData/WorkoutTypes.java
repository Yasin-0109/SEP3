package com.jas.modelData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jas.DataSource;
import com.jas.model.WorkoutType;

public class WorkoutTypes {

	
	private static List<WorkoutType> workoutTypes;
	
	static { // Will be called as soon as this class will be executed.
		getDataFromDataBase(); // Pulls users data from database into users variable.
	}
	
	private static void getDataFromDataBase() {
		// Initializing workoutTypes variable
		try {
			String SQL_QUERY = "select * from workouttype;";
			Connection conn = DataSource.getConnection(); // Getting connection to database
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY); // Preparing the query
			ResultSet rs = pst.executeQuery(); // Executing query
			
			List<WorkoutType> temp = new ArrayList<>(); // Initializing temporary workout types list - temp one so while updating there still exists current data
			
			while (rs.next()) {
				temp.add(new WorkoutType(rs.getInt("id"), rs.getString("type")));
			}

			workoutTypes = temp; // Assigning temporary workout types list to workoutTypes variable
		} catch (SQLException error) { // Catch any SQL errors
			System.out.println("[Error] Couldn't initialize Workout types data! Reason: " + error.getMessage()); // Show it to the console
		}
	}
	
	public static void refreshData() { // Refreshes data in variable
		getDataFromDataBase();
	}
	
	public static List<WorkoutType> getWorkoutTypes() {
		return workoutTypes;
	}
	
	public static WorkoutType getWorkoutTypeById(int id) {
		return workoutTypes.stream() // In Java 8 we can simply use streams instead of for loop :)
				.filter(wt -> wt.getId() == id) // Filter the list
				.findFirst() // Get first workout type from filtered list
				.orElse(null); // Return null if there's no user
	}
}
