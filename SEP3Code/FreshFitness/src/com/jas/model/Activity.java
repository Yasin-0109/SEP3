package com.jas.model;

/**
 * Stores data about Activity
 * 
 * @author Yasin Issa Aden
 * @version 1.0
 */

public class Activity {
	
	public int ID;
	public String name;
	public MyDate date;
	public MyDate startTime;
	public MyDate endTime;
	
	public Activity(int ID, String name, MyDate date, MyDate startTime, MyDate endTime)
	{
		this.ID=ID;
		this.name=name;
		this.date=date;
		this.startTime=startTime;
		this.endTime=endTime;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MyDate getDate() {
		return date;
	}

	public void setDate(MyDate date) {
		this.date = date;
	}

	public MyDate getStartTime() {
		return startTime;
	}

	public void setStartTime(MyDate startTime) {
		this.startTime = startTime;
	}

	public MyDate getEndTime() {
		return endTime;
	}

	public void setEndTime(MyDate endTime) {
		this.endTime = endTime;
	}
	
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Activity)) {
			return false;
		}
		Activity other = (Activity) obj;
		return ID == other.ID && name.equals(other.name)
				&& date.equals(other.date) && startTime.equals(other.startTime) 
				&& endTime.equals(other.endTime);
	}

	@Override
	public String toString() {
		return "Activity [ID=" + ID + ", name=" + name + ", date=" + date + ", startTime=" + startTime + ", endTime="
				+ endTime + "]";
	}	
}
