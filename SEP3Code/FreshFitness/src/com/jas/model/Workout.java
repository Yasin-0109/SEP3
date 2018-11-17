package com.jas.model;


/**
 * Stores data about Workout
 * 
 * @author Yasin Issa Aden
 * @version 1.0
 */


public class Workout {

	private int id;
	private int numberOfWorkouts;
	private WorkoutType type;
	
	public Workout(int id, int numberOfWorkouts, WorkoutType type)
	{
		this.id = id;
		this.numberOfWorkouts = numberOfWorkouts;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumberOfWorkouts() {
		return numberOfWorkouts;
	}

	public void setNumberOfWorkouts(int numberOfWorkouts) {
		this.numberOfWorkouts = numberOfWorkouts;
	}

	public WorkoutType getType() {
		return type;
	}

	public void setType(WorkoutType type) {
		this.type = type;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Workout)) {
			return false;
		}
		Workout other = (Workout) obj;
		return id == other.getId() && numberOfWorkouts == other.getNumberOfWorkouts() && type.equals(other.getType());
	}

	@Override
	public String toString() {
		return "Workout [id=" + id + ", numberOfWorkouts=" + numberOfWorkouts + ", type=" + type + "]";
	}
}
