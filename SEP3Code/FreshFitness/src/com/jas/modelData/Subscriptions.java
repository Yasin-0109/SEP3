package com.jas.modelData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jas.DataSource;
import com.jas.model.Subscription;

public class Subscriptions {


	private static List<Subscription> subscriptions;
	
	static { // Will be called as soon as this class will be executed.
		getDataFromDataBase(); // Pulls data from database into variable.
	}
	
	private static void getDataFromDataBase() {
		// Initializing variable
		try {
			String SQL_QUERY = "select * from subscription;";
			Connection conn = DataSource.getConnection(); // Getting connection to database
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY); // Preparing the query
			ResultSet rs = pst.executeQuery(); // Executing query
			
			List<Subscription> temp = new ArrayList<>(); // Initializing temporary list - temp one so while updating there still exists current data
			
			while (rs.next()) {
				temp.add(new Subscription(
					rs.getInt("id"),
					rs.getInt("userid"),
					rs.getDate("validfrom"),
					rs.getDate("validto"),
					SubscriptionTypes.getSubscriptionTypeById(rs.getInt("subscriptiontypeid"))
				));
			}

			subscriptions = temp; // Assigning temporary list to variable
		} catch (SQLException error) { // Catch any SQL errors
			System.out.println("[Error] Couldn't initialize Subscription types data! Reason: " + error.getMessage()); // Show it to the console
		}
	}
	
	public static void refreshData() { // Refreshes data in variable
		getDataFromDataBase();
	}
	
	public static List<Subscription> getSubscriptions() { // Returns a list of subcriptions
		return subscriptions;
	}
	
	public static Subscription getSubscriptionById(int id) { // Returns a subscription by it's ID
		return subscriptions.stream()
			.filter(a -> a.getId() == id)
			.findFirst()
			.orElse(null);
	}
	
	public static Subscription getUserSubscription(int userid) { // Returns user subscription
		return subscriptions.stream() // In Java 8 we can simply use streams instead of for loop :)
			.filter(a -> a.getUserId() == userid) // Filter the list
			.findFirst() // Get first subscription from filtered list
			.orElse(null); // Return null if there's no subscription
	}
}
