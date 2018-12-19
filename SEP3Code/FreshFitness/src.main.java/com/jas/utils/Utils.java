package com.jas.utils;

import java.util.Date;

import com.jas.model.UserRole;
import com.jas.modelData.UserRoles;

import spark.Request;
import spark.Response;

public class Utils {

	public static UserRole getAdminRole() { // Returns admin user role
		return UserRoles.getUserRoleById(1); // Hardcoded in DB
	}
	
	public static UserRole getInstructorRole() { // Returns instructor user role
		return UserRoles.getUserRoleById(3); // Hardcoded in DB
	}
	
	public static String requestToString(Request request) {
		StringBuilder sb = new StringBuilder();
		sb.append("Time: ");
		sb.append(new Date());
		sb.append("\nSession ID: ");
		sb.append(request.session().id());
		sb.append("\nMethod: ");
		sb.append(request.requestMethod());
		
		sb.append("\nURI: ");
		sb.append(request.uri());
		
		sb.append("\nParams:");
		request.params().forEach((key,value) -> sb.append("\n - " + key + ": " + value));
		
		sb.append("\nQuery params:");
		request.queryParams().forEach((value) -> sb.append("\n - " + value + ": " + request.queryParams(value)));
		
		sb.append("\nUser agent: ");
		sb.append(request.userAgent());
		
		sb.append("\nBody:\n");
		sb.append(request.body());
		return sb.toString();
	}
	
	public static String responseToString(Response response) {
		StringBuilder sb = new StringBuilder();
		sb.append(response.body());
		return sb.toString();
	}
}
