package com.jas.controller;

import com.jas.utils.Result;

import spark.Request;
import spark.Response;
import spark.Route;

public class ServerController {

	public static Route main = (Request request, Response response) -> {
		return Result.superUltraStatus(true, "Hello world lol!");
	};
	
	public static Route status = (Request request, Response response) -> {
		return Result.superUltraOnlineStatus("Sup boii?");
	};
	
	public static Route notFound = (Request request, Response response) -> {
		return Result.superUltraStatus(false, "404 Not Found");
	};
	
	public static Route internalError = (Request request, Response response) -> {
		return Result.superUltraStatus(false, "500 Internal Server Error");
	};
}
