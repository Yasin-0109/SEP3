package com.jas.model;

import com.google.gson.JsonObject;
import com.jas.Server;

public class Result {

	public static String superUltraError(String reason) {
		JsonObject jsonCode = new JsonObject();
		jsonCode.addProperty("reason", reason);
		
		return Server.getGson().toJson(jsonCode);
	}
}
