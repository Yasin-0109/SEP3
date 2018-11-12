package com.jas.controller;

import com.jas.model.User;
import com.jas.modelData.Users;

public class UserController {

	/**
	 * This function is for authenticating a user.
	 * @param email email of user
	 * @param password password of user
	 * @return true if password is correct
	 */
	public static boolean authenticate(String email, String password) {
		if (email.isEmpty() || password.isEmpty() || Users.getUserByEmail(email) == null) {
			return false; // If email/password is empty or there's no user with that email - return false 
		}
		
		User user = Users.getUserByEmail(email);
		return user.getPassword().equals(password); // Why we don't use any encryption?! It's 2018 - leaks are everywhere :V 
	}
	
}
