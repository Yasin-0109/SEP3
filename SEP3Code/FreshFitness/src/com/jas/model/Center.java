package com.jas.model;

import java.util.ArrayList;

@Deprecated
public class Center {
	

	private int ID;
	private String name;
	private String address;
	private ArrayList<User> users;
	private ArrayList<Activity> activities;
	
	public Center(int ID, String name, String address)
	{
		this.ID=ID;
		this.name=name;
		this.address=address;
		users = new ArrayList<User>();
		activities = new ArrayList<Activity>();
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ArrayList<Activity> getActivities() {
		return activities;
	}

	public void setActivities(ArrayList<Activity> activities) {
		this.activities = activities;
	}
	
	public void registerUser(User user)
	{
		users.add(user);
	}
	
	public void unRegisterUser(User user)
	{
		users.remove(user);
	}
	
	public ArrayList<User> getAllUser()
	{
		return users;
	}
	
	public ArrayList<Activity> getAllActivities()
	{
		return activities;
	}
	
	public int getNumberOfActivitiesByName(String name)
	{
		int count=0;
		for(int i = 0; i < activities.size(); i++)
		{
			if(activities.get(i).getName().equals(name))
			{
				count++;
			}
		}
		return count;
	}	
		
	@Override
	public String toString() {
		return "Center [ID=" + ID + ", name=" + name + ", address=" + address + ", users=" + users + ", activities="
				+ activities + "]";
	}
	
	 
}
