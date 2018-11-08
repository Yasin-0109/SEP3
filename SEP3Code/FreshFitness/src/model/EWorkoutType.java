package model;

/**
 * Stores data about EWorkoutType
 * 
 * @author Yasin Issa Aden
 * @version 1.0
 */

public class EWorkoutType {
	private int ID;
	private EType type;

	public enum EType {

		benchpress, curls, squat, deadlift, pullups, crunch, pulldowns, boxing, legextension;

	}

	public EWorkoutType(int ID, EType type) {
		this.ID = ID;
		this.type = type;
		
	}

	@Override
	public String toString() {
		return "EWorkoutType [ID=" + ID + ", type=" + type + "]";
	}
	
	

}