package com.jas.modelData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		String SQL_QUERY = "select * from workouttype;";
		try(Connection conn = DataSource.getConnection();PreparedStatement pst = conn.prepareStatement(SQL_QUERY);) { // Getting connection to database, preparing the query
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
	
	public static boolean addWorkoutType(WorkoutType workoutType) { // Adds workout type to database
		String SQL_QUERY = "insert into workouttype(type) values (?)";
		try (Connection conn = DataSource.getConnection();
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY, Statement.RETURN_GENERATED_KEYS);) {
			
			pst.setString(1, workoutType.getType());
			int rc = pst.executeUpdate();
			
			if (rc > 0) { // Insert to database was success
				ResultSet gk = pst.getGeneratedKeys();
				if (gk.isBeforeFirst()) {
					gk.next();
					workoutType.setId(gk.getInt("id")); // Assign new workout type Id to it
					workoutTypes.add(workoutType);
					return true;
				}
			}
		} catch (SQLException error) {
			System.out.println("[Error] Couldn't add workout type to database! Reason: " + error.getMessage()); // Show it to the console
		}
		return false;
	}
	
	public static boolean delWorkoutType(WorkoutType workoutType) { // Removes workout type from database
		String SQL_QUERY = "delete from workouttype where id=?";
		try (Connection conn = DataSource.getConnection();
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY);) {
			
			pst.setInt(1, workoutType.getId());
			int rc = pst.executeUpdate();
			pst.close();
			
			if (rc > 0) { // Delete from database was success
				workoutTypes.remove(workoutTypes.indexOf(workoutType));
				return true;
			}
		} catch (SQLException error) {
			System.out.println("[Error] Couldn't delete workout type from database! Reason: " + error.getMessage()); // Show it to the console
		}
		return false;
	}
	
	public static boolean editWorkoutType(WorkoutType workoutType) { // Updates workout type in database
		String SQL_QUERY = "update workouttype set type = ? where id = ?";
		try (Connection conn = DataSource.getConnection();
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY);) {
			
			WorkoutType old = getWorkoutTypeById(workoutType.getId());
			
			pst.setString(1, workoutType.getType());
			pst.setInt(2, workoutType.getId());
			int rc = pst.executeUpdate();
			pst.close();
			
			if (rc > 0) { // Update on database was success
				workoutTypes.set(workoutTypes.indexOf(old), workoutType);
				return true;
			}
		} catch (SQLException error) {
			System.out.println("[Error] Couldn't update workout type on database! Reason: " + error.getMessage()); // Show it to the console
		}
		return false;
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
