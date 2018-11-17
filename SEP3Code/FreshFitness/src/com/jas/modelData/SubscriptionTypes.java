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
		getDataFromDataBase(); // Pulls users data from database into users variable.
	}
	
	private static void getDataFromDataBase() {
		// Initializing subscriptions variable
		try {
			String SQL_QUERY = "select * from subscriptiontype;";
			Connection conn = DataSource.getConnection(); // Getting connection to database
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY); // Preparing the query
			ResultSet rs = pst.executeQuery(); // Executing query
			
			List<SubscriptionType> temp = new ArrayList<>(); // Initializing temporary subscription types list - temp one so while updating there still exists current data
			
			while (rs.next()) {
				temp.add(new SubscriptionType(rs.getInt("id"), rs.getString("type"), rs.getBigDecimal("price")));
			}

			subscriptionTypes = temp; // Assigning temporary subscription types list to subscriptionTypes variable
		} catch (SQLException error) { // Catch any SQL errors
			System.out.println("[Error] Couldn't initialize Subscription types data! Reason: " + error.getMessage()); // Show it to the console
		}
	}
	
	public static void refreshData() {
		getDataFromDataBase();
	}
	
	public static List<SubscriptionType> getSubscriptionTypes() {
		return subscriptionTypes;
	}
	
	public static SubscriptionType getSubscriptionTypeById(int id) {
		return subscriptionTypes.stream()
				.filter(st -> st.getId() == id)
				.findFirst()
				.orElse(null);
	}
}
