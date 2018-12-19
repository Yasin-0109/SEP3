package com.jas.model;

import java.sql.Timestamp;

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
	private Timestamp date;
	
	public Workout(int id, int userId, WorkoutType type, int numberOfWorkouts, Timestamp date)
	{
		this.id = id;
		this.userId = userId;
		this.type = type;
		this.numberOfWorkouts = numberOfWorkouts;
		this.date = date;
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
	
	public Timestamp getDate() {
		return date;
	}
	
	public void setDate(Timestamp date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + id;
		result = prime * result + numberOfWorkouts;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Workout other = (Workout) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id != other.id)
			return false;
		if (numberOfWorkouts != other.numberOfWorkouts)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Workout [id=" + id + ", userId=" + userId + ", type=" + type + ", numberOfWorkouts=" + numberOfWorkouts
				+ ", date=" + date + "]";
	}
}
