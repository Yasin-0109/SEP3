package model;

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
		Admin, Instructor, Member;
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
