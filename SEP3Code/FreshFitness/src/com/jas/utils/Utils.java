package com.jas.utils;

import com.jas.model.UserRole;
import com.jas.modelData.UserRoles;

public class Utils {

	public static UserRole getAdminRole() {
		return UserRoles.getUserRoleById(1); // Hardcoded in DB
	}
	
	public static UserRole getInstructorRole() {
		return UserRoles.getUserRoleById(3); // Hardcoded in DB
	}
}
