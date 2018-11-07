package model;
import java.util.ArrayList;

public class User 
{
	private int ID;
	private String firstName;
	private String lastName;
	private MyDate dateOfBirth;
	private String email;
	private String password;
	private int phoneNumber;
	private EUserRole userRole;
	private ArrayList<Activity> activities;
	private Subscription subscription;
	private ArrayList<Workout> workouts;
	
	public User(int ID, String email, String firstName, String lastName, int phoneNumber, MyDate dateOfBirth, EUserRole userRole)
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
	
	public ArrayList<Workout> getAllWorkouts()
	{
		return workouts;
	}
	
	public int getNumberOfworkoutsByType(EWorkoutType type)
	{
		int count = 0;
		for(int i = 0; i < workouts.size(); i++)
		{
			if(workouts.get(i).equals(type))
			{
				count++;
			}
		}
		return count;
	}
	
	
	
	
}
