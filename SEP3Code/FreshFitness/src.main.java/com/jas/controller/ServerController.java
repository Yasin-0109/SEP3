package com.jas.controller;

import com.jas.utils.Result;

import spark.Request;
import spark.Response;
import spark.Route;

public class ServerController {

	public static Route main = (Request request, Response response) -> {
		return Result.superUltraStatus(true, "SEP3 Server v1.0"); // Some nice info on main path
	};
	
	public static Route status = (Request request, Response response) -> {
		return Result.superUltraStatus(true, "Working fine... " + System.currentTimeMillis()); // Simple message as example
	};
	
	public static Route notFound = (Request request, Response response) -> {
		return Result.superUltraStatus(false, "404 Not Found"); // Default 404 handler
	};
	
	public static Route internalError = (Request request, Response response) -> {
		return Result.superUltraStatus(false, "500 Internal Server Error"); // Default 500 handler
	};
}
