package com.jas.model;

import java.sql.Timestamp;

/**
 * Stores data about Activity
 * 
 * @author Yasin Issa Aden
 * @version 1.0
 */

public class Activity {
	
	public int id;
	public String name;
	public Timestamp startTime;
	public Timestamp endTime;
	private int instructorId;
	
	public Activity(int id, String name, Timestamp startTime, Timestamp endTime, int instructorId)
	{
		this.id=id;
		this.name=name;
		this.startTime=startTime;
		this.endTime=endTime;
		this.instructorId = instructorId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	
	public int getInstructorId() {
		return instructorId;
	}
	
	public void setInstructorId(int instructorId) {
		this.instructorId = instructorId;
	}
	
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Activity)) {
			return false;
		}
		Activity other = (Activity) obj;
		return id == other.getId() && name.equals(other.getName())
				&& startTime.equals(other.getStartTime()) 
				&& endTime.equals(other.getEndTime());
	}

	@Override
	public String toString() {
		return "Activity [id=" + id + ", name=" + name + ", startTime=" + startTime + ", endTime="
				+ endTime + "]";
	}	
}
