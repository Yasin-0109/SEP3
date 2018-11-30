package com.jas.modelData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jas.DataSource;
import com.jas.model.SubscriptionType;

public class SubscriptionTypes {

	
	private static List<SubscriptionType> subscriptionTypes;
	
	static { // Will be called as soon as this class will be executed.
		getDataFromDataBase(); // Pulls data from database into variable.
	}
	
	private static void getDataFromDataBase() {
		// Initializing subscriptions variable
		try {
			String SQL_QUERY = "select * from subscriptiontype;";
			Connection conn = DataSource.getConnection(); // Getting connection to database
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY); // Preparing the query
			ResultSet rs = pst.executeQuery(); // Executing query
			
			List<SubscriptionType> temp = new ArrayList<>(); // Initializing temporary subscription types list - temp one so while updating there still exists current data
			
			while (rs.next()) { // Adding subscription types from database
				temp.add(new SubscriptionType(rs.getInt("id"), rs.getString("type"), rs.getBigDecimal("price")));
			}

			subscriptionTypes = temp; // Assigning temporary subscription types list to subscriptionTypes variable
		} catch (SQLException error) { // Catch any SQL errors
			System.out.println("[Error] Couldn't initialize Subscription types data! Reason: " + error.getMessage()); // Show it to the console
		}
	}
	
	public static void refreshData() { // Refreshes data in variable
		getDataFromDataBase();
	}
	
	public static List<SubscriptionType> getSubscriptionTypes() { // Returns a list of subscription types
		return subscriptionTypes;
	}
	
	public static SubscriptionType getSubscriptionTypeById(int id) { // Returns a subscription type by it's id
		return subscriptionTypes.stream() // In Java 8 we can simply use streams instead of for loop :)
				.filter(st -> st.getId() == id) // Filter the list
				.findFirst() // Get first subscription type from filtered list
				.orElse(null); // Return null if there's no subscription type
	}
}
