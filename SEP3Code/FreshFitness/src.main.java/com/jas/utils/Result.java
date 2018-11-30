package com.jas.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jas.Main;

import spark.Response;

public class Result {

	/* Premade result json objects */
	public static String superUltraStatus(Boolean isOk, String message) {
		JsonObject jsonCode = new JsonObject();
		jsonCode.addProperty("status", isOk);
		jsonCode.addProperty("message", message);
		
		return Main.getServer().getGson().toJson(jsonCode);
	}
	
	public static String superUltraJsonData(Boolean isOk, JsonElement data) {
		JsonObject jsonCode = new JsonObject();
		jsonCode.addProperty("status", isOk);
		jsonCode.add("data", data);
		
		return Main.getServer().getGson().toJson(jsonCode);
	}
	
	public static String notImplementedYet(Response response) {
		response.status(501);
		return superUltraStatus(false, "Not implemented yet!");
	}
}
