package com.jas.modelData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
					rs.getTimestamp("validfrom"),
					rs.getTimestamp("validto"),
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
	
	public static boolean addSubscription(Subscription subscription) { // Adds subscription to database
		try {
			Connection conn = DataSource.getConnection();
			
			String SQL_QUERY = "insert into subscription (userid, validfrom, validto, subscriptiontypeid) values (?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, subscription.getId());
			pst.setTimestamp(2, subscription.getValidFrom());
			pst.setTimestamp(3, subscription.getValidTo());
			pst.setInt(4, subscription.getSubscriptionType().getId());
			int rc = pst.executeUpdate();
			
			if (rc > 0) { // Insert to database was success
				ResultSet gk = pst.getGeneratedKeys();
				if (gk.isBeforeFirst()) {
					subscription.setId(gk.getInt("id")); // Assigns new id to subscription
					subscriptions.add(subscription);
					return true;
				}
			}
		} catch (SQLException error) {
			System.out.println("[Error] Couldn't add subscription to database! Reason: " + error.getMessage()); // Show it to the console
		}
		return false;
	}
	
	public static boolean delSubscription(Subscription subscription) { // Removes subscription from database
		try {
			Connection conn = DataSource.getConnection();
			
			String SQL_QUERY = "delete from subscription where id=?";
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY);
			pst.setInt(1, subscription.getId());
			int rc = pst.executeUpdate();
			pst.close();
			
			if (rc > 0) { // Delete from database was success
				subscriptions.remove(subscriptions.indexOf(subscription));
				return true;
			}
		} catch (SQLException error) {
			System.out.println("[Error] Couldn't delete subscription from database! Reason: " + error.getMessage()); // Show it to the console
		}
		return false;
	}
	
	public static boolean editSubscription(Subscription subscription) { // Updates subscription in database
		try {
			Subscription old = getSubscriptionById(subscription.getId());
			
			Connection conn = DataSource.getConnection();
			String SQL_QUERY = "update activity set userid = ?, validfrom = ?, validto = ?, subscriptiontypeid = ? where id = ?";
			PreparedStatement pst = conn.prepareStatement(SQL_QUERY);
			pst.setInt(1, subscription.getUserId());
			pst.setTimestamp(2, subscription.getValidFrom());
			pst.setTimestamp(3, subscription.getValidTo());
			pst.setInt(4, subscription.getSubscriptionType().getId());
			pst.setInt(5, subscription.getId());
			int rc = pst.executeUpdate();
			pst.close();
			
			if (rc > 0) { // Update on database was success
				subscriptions.set(subscriptions.indexOf(old), subscription);
				return true;
			}
		} catch (SQLException error) {
			System.out.println("[Error] Couldn't update subscription in database! Reason: " + error.getMessage()); // Show it to the console
		}
		return false;
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
