package com.jas.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.jas.DataSource;
import com.jas.Server;
import com.jas.utils.Result;

import spark.Request;
import spark.Response;
import spark.Route;

public class ServerController {

	public static Route main = (Request request, Response response) -> {
		return Result.superUltraStatus(true, "SEP3 Server v1.0"); // Some nice info on main path
	};
	
	public static Route status = (Request request, Response response) -> {
		try(Connection conn = DataSource.getConnection();) {
			if (!conn.isValid(3000)) {
				return Result.superUltraStatus(false, "Connection problems with database server.");
			}
		} catch (SQLException error) { // Catch any SQL errors
			System.out.println("[Error] Couldn't check db connection! Reason: " + error.getMessage()); // Show it to the console
			return Result.superUltraStatus(false, "Communication problems with database server.");
		}
		
		Properties data = new Properties();
		data.put("loggedIn", request.session().attribute("currentUserId") != null);
		
		return Result.superUltraStatusData(true, 
				"Working fine... " + System.currentTimeMillis(),
				Server.getGson().toJsonTree(data));
	};
	
	public static Route notFound = (Request request, Response response) -> {
		return Result.superUltraStatus(false, "404 Not Found"); // Default 404 handler
	};
	
	public static Route internalError = (Request request, Response response) -> {
		return Result.superUltraStatus(false, "500 Internal Server Error"); // Default 500 handler
	};
}
