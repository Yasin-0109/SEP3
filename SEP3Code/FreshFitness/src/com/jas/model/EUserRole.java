package com.jas.model;

/**
 * Stores data about EUserRole
 * 
 * @author Jaser Ghasemi (267243)
 * @version 1.0
 */

public class EUserRole {

	private int ID;
	private ERole role;

	public enum ERole {
		Admin(1), 
		Member(2),
		Instructor(3);
		
		private final int value;
		
		ERole(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
		
		public static ERole fromInt(int id) {
			for (ERole r : ERole.values()) {
				if (r.getValue() == id) {
					return r;
				}
			}
			throw new IllegalArgumentException("No ERole with ID: " + id);
		}
	}

	public EUserRole(int ID, ERole role) {
		this.ID = ID;
		this.role = role;
	}

	public int getID() {
		return this.ID;
	}

	@Override
	public String toString() {
		return "EUserRole [ID=" + ID + ", role=" + role + "]";
	}
	
	
}
