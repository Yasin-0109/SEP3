package com.jas.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jas.Server;

public class Result {

	public static String superUltraError(String reason) {
		JsonObject jsonCode = new JsonObject();
		jsonCode.addProperty("reason", reason);
		
		return Server.getGson().toJson(jsonCode);
	}
	
	public static String superUltraOnlineStatus(String status) {
		JsonObject jsonCode = new JsonObject();
		jsonCode.addProperty("online", true);
		jsonCode.addProperty("status", status);
		
		return Server.getGson().toJson(jsonCode);
	}
	
	public static String superUltraStatus(Boolean isOk, String message) {
		JsonObject jsonCode = new JsonObject();
		jsonCode.addProperty("status", isOk);
		jsonCode.addProperty("message", message);
		
		return Server.getGson().toJson(jsonCode);
	}
	
	public static String superUltraJsonData(Boolean isOk, JsonElement data) {
		JsonObject jsonCode = new JsonObject();
		jsonCode.addProperty("status", isOk);
		jsonCode.add("data", data);
		
		return Server.getGson().toJson(jsonCode);
	}
}
