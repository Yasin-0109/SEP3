package com.jas.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class User {
	private int id;
	private String firstName;
	private String lastName;
	private Timestamp dateOfBirth;
	private String email;
	private String password;
	private int phoneNumber;
	private UserRole userRole;
	//private List<Activity> userActivities; // We already have them somewhere else
	//private Subscription subscription; // We already have it somewhere else
	//private List<Workout> workouts; // We already have them somewhere else
	
	public User(int id, String email, String firstName, String lastName, int phoneNumber, Timestamp dateOfBirth, UserRole userRole)
	{
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
		this.userRole = userRole;
		//userActivities = new ArrayList<Activity>();
		//workouts = new ArrayList<Workout>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Timestamp getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Timestamp dateOfBirth) {
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

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth
				+ ", email=" + email + ", password=" + password + ", phoneNumber=" + phoneNumber + ", userRole="
				+ userRole + "]";
	}
	
	
	
}
