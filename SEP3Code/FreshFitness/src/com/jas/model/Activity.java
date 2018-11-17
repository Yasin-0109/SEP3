package com.jas.model;

import java.sql.Time;
import java.util.Date;

/**
 * Stores data about Activity
 * 
 * @author Yasin Issa Aden
 * @version 1.0
 */

public class Activity {
	
	public int id;
	public String name;
	public Date date;
	public Time startTime;
	public Time endTime;
	private int instructorId;
	
	public Activity(int id, String name, Date date, Time startTime, Time endTime, int instructorId)
	{
		this.id=id;
		this.name=name;
		this.date=date;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
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
				&& date.equals(other.getDate()) && startTime.equals(other.getStartTime()) 
				&& endTime.equals(other.getEndTime());
	}

	@Override
	public String toString() {
		return "Activity [id=" + id + ", name=" + name + ", date=" + date + ", startTime=" + startTime + ", endTime="
				+ endTime + "]";
	}	
}
