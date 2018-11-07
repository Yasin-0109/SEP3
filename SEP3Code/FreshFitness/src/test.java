import model;
import model.EWorkoutType;
import model.EWorkoutType.EType;
import model.MyDate;
import model.User;
import model.Workout;

public class test 
{
	public static void main(String[] args)
	{
		EWorkoutType Type1 = new EWorkoutType(1, EType.benchpress);
		
		Workout workout = new Workout(1, 5, Type1);
		
		MyDate date1 = new MyDate(07,11,2018);
		
		User bob = new User(1, "email", "firstName", "lastName", 34234556, date1, 1);
		
		
		
		
		
	}
}
