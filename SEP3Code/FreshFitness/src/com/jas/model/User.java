package com.jas.model;
import java.util.ArrayList;
import java.util.Date;

import com.jas.model.EWorkoutType.EType;



public class User 
{
	

	private int ID;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String email;
	private String password;
	private int phoneNumber;
	private EUserRole userRole;
	private ArrayList<Activity> activities;
	private Subscription subscription;
	private ArrayList<Workout> workouts;
	
	public User(int ID, String email, String firstName, String lastName, int phoneNumber, Date dateOfBirth, EUserRole userRole)
	{
		this.ID = ID;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
		this.userRole = userRole;
		activities = new ArrayList<Activity>();
		workouts = new ArrayList<Workout>();
	}
	
	public User() { // If someones want to initialize it and after it set values :v
		this(-1, null, null, null, -1, null, null);
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public EUserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(EUserRole userRole) {
		this.userRole = userRole;
	}

	public ArrayList<Activity> getActivities() {
		return activities;
	}

	public void setActivities(ArrayList<Activity> activities) {
		this.activities = activities;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public ArrayList<Workout> getWorkouts() {
		return workouts;
	}

	public void setWorkouts(ArrayList<Workout> workouts) {
		this.workouts = workouts;
	}	
	
	public ArrayList<Activity> getAllActivities()
	{
		return activities;
	}
	
	public int getNumberOfActivities()
	{
		return activities.size();
	}
	
	public void addWorkOut(Workout workout)
	{
		workouts.add(workout);
	}
	
	public void removeWorkout(Workout workout)
	{
		workouts.remove(workout);
	}
	
	public void addActivity(Activity activity)
	{
		activities.add(activity);
	}
	
	public void removeActivity(Activity activity)
	{
		activities.remove(activity);
	}
	
	public ArrayList<Workout> getAllWorkouts()
	{
		return workouts;
	}
	
	public int getNumberOfworkoutsByType(EType type)
	{
		int count=0;
		for(int i = 0; i < workouts.size(); i++)
		{
			if(workouts.get(i).getType().equals(type))
			{
				count++;
			}
		}
		return count;
	}
	
	@Override
	public String toString() {
		return "User [ID=" + ID + ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth
				+ ", email=" + email + ", password=" + password + ", phoneNumber=" + phoneNumber + ", userRole="
				+ userRole + ", activities=" + activities + ", subscription=" + subscription + ", workouts=" + workouts
				+ "]";
	}
	
	
	
}
