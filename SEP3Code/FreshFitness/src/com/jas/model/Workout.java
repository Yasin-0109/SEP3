package com.jas.model;


/**
 * Stores data about Workout
 * 
 * @author Yasin Issa Aden
 * @version 1.0
 */


public class Workout {

	private int id;
	private int userId;
	private WorkoutType type;
	private int numberOfWorkouts;
	
	public Workout(int id, int userId, WorkoutType type, int numberOfWorkouts)
	{
		this.id = id;
		this.userId = userId;
		this.type = type;
		this.numberOfWorkouts = numberOfWorkouts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public WorkoutType getType() {
		return type;
	}

	public void setType(WorkoutType type) {
		this.type = type;
	}
	
	public int getNumberOfWorkouts() {
		return numberOfWorkouts;
	}

	public void setNumberOfWorkouts(int numberOfWorkouts) {
		this.numberOfWorkouts = numberOfWorkouts;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Workout)) {
			return false;
		}
		Workout other = (Workout) obj;
		return id == other.getId() && userId == other.getUserId() && type.equals(other.getType()) && numberOfWorkouts == other.getNumberOfWorkouts() ;
	}

	@Override
	public String toString() {
		return "Workout [id=" + id + ", userId=" + userId + "type=" + type + ", numberOfWorkouts=" + numberOfWorkouts + "]";
	}
}
