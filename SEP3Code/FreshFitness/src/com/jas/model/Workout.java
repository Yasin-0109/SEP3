package com.jas.model;

import com.jas.model.EWorkoutType.EType;

/**
 * Stores data about Workout
 * 
 * @author Yasin Issa Aden
 * @version 1.0
 */


public class Workout {

	public int ID;
	public int numberOfWorkouts;
	public EType type;
	
	public Workout(int ID, int numberOfWorkouts, EType type)
	{
		this.ID = ID;
		this.numberOfWorkouts = numberOfWorkouts;
		this.type = type;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getNumberOfWorkouts() {
		return numberOfWorkouts;
	}

	public void setNumberOfWorkouts(int numberOfWorkouts) {
		this.numberOfWorkouts = numberOfWorkouts;
	}

	public EType getType() {
		return type;
	}

	public void setType(EType type) {
		this.type = type;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Workout)) {
			return false;
		}
		Workout other = (Workout) obj;
		return ID == other.ID && numberOfWorkouts == other.numberOfWorkouts && type.equals(other.type);
	}

	@Override
	public String toString() {
		return "Workout [ID=" + ID + ", numberOfWorkouts=" + numberOfWorkouts + ", type=" + type + "]";
	}
}
