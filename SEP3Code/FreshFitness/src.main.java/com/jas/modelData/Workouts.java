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
import com.jas.model.Workout;

public class Workouts {

	private static List<Workout> workouts;
	
	static { // Will be called as soon as this class will be executed.
		getDataFromDataBase(); // Pulls data from database into variable.
	}
	
	private static void getDataFromDataBase() {
		// Initializing workouts variable
		String SQL_QUERY = "select * from workout;";
		try(Connection conn = DataSource.getConnection(); // Getting connection to database
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY); /* Preparing the query */) {
			ResultSet rs = pst.executeQuery(); // Executing query
			
			List<Workout> temp = new ArrayList<>(); // Initializing temporary workout list - temp one so while updating there still exists current data
			
			while (rs.next()) {
				temp.add(new Workout( // Adding workout to temporary list
					rs.getInt("id"),
					rs.getInt("userid"),
					WorkoutTypes.getWorkoutTypeById(rs.getInt("workouttypeid")),
					rs.getInt("numberofworkouts"),
					rs.getTimestamp("date")
				));
			}

			workouts = temp; // Assigning temporary workout list to workouts variable
			pst.close();
		} catch (SQLException error) { // Catch any SQL errors
			System.out.println("[Error] Couldn't initialize Workouts data! Reason: " + error.getMessage()); // Show it to the console
		}
	}
	
	public static void refreshData() { // Refreshes data in variable
		getDataFromDataBase();
	}
	
	public static boolean addWorkout(Workout workout) { // Adds workout to database
		String SQL_QUERY = "insert into workout(userid, workouttypeid, numberofworkouts, date) values (?,?,?,?)";
		try(Connection conn = DataSource.getConnection();
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY, Statement.RETURN_GENERATED_KEYS);) {
			pst.setInt(1, workout.getUserId());
			pst.setInt(2, workout.getType().getId());
			pst.setInt(3, workout.getNumberOfWorkouts());
			pst.setTimestamp(4, workout.getDate());
			int rc = pst.executeUpdate();
			
			if (rc > 0) { // Insert to database was success
				ResultSet gk = pst.getGeneratedKeys();
				if (gk.isBeforeFirst()) {
					gk.next();
					workout.setId(gk.getInt("id")); // Assign new workout Id to it
					workouts.add(workout);
					return true;
				}
			}
		} catch (SQLException error) {
			System.out.println("[Error] Couldn't add workout to database! Reason: " + error.getMessage()); // Show it to the console
		}
		return false;
	}
	
	public static boolean delWorkout(Workout workout) { // Removes workout from database
		String SQL_QUERY = "delete from workout where id=?";
		try(Connection conn = DataSource.getConnection();
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY);) {
			pst.setInt(1, workout.getId());
			int rc = pst.executeUpdate();
			pst.close();
			
			if (rc > 0) { // Delete from database was success
				workouts.remove(workouts.indexOf(workout));
				return true;
			}
		} catch (SQLException error) {
			System.out.println("[Error] Couldn't delete workout from database! Reason: " + error.getMessage()); // Show it to the console
		}
		return false;
	}
	
	public static boolean editWorkout(Workout workout) { // Updates workout in database
		String SQL_QUERY = "update workout set userid = ?, workouttypeid = ?, numberofworkouts = ?, date = ? where id = ?";
		try(Connection conn = DataSource.getConnection();
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY);) {
			Workout old = getWorkoutById(workout.getId());
			
			pst.setInt(1, workout.getUserId());
			pst.setInt(2, workout.getType().getId());
			pst.setInt(3, workout.getNumberOfWorkouts());
			pst.setTimestamp(4, workout.getDate());
			pst.setInt(5, workout.getId());
			int rc = pst.executeUpdate();
			pst.close();
			
			if (rc > 0) { // Update on database was success
				workouts.set(workouts.indexOf(old), workout);
				return true;
			}
		} catch (SQLException error) {
			System.out.println("[Error] Couldn't update workout on database! Reason: " + error.getMessage()); // Show it to the console
		}
		return false;
	}
	
	public static List<Workout> getWorkouts() { // Returns a list of workouts
		return workouts;
	}
	
	public static Workout getWorkoutById(int id) { // Returns a specific workout by it's ID
		return workouts.stream()
			.filter(a -> a.getId() == id)
			.findFirst()
			.orElse(null);
	}
	
	public static List<Workout> getUserWorkouts(int userid) { // Returns workouts of user
		return workouts.stream() // In Java 8 we can simply use streams instead of for loop :)
			.filter(a -> a.getUserId() == userid) // Filter the list
			.collect(Collectors.toList()); // Return a filtered list
	}
}
