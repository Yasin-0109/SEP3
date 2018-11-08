import model.EUserRole;
import model.EUserRole.ERole;
import model.EWorkoutType;
import model.EWorkoutType.EType;
import model.MyDate;
import model.User;
import model.Workout;

public class test 
{
	public static void main(String[] args)
	{
		
		
		Workout workout = new Workout(1, 5, EType.benchpress);
		Workout workout2 = new Workout(1, 10, EType.benchpress);

		
		MyDate date1 = new MyDate(07,11,2018);
		
		EUserRole role = new EUserRole(1, ERole.Admin);
		
		User bob = new User(1, "email", "firstName", "lastName", 34234556, date1, role);
		
		System.out.println(bob);
		
		bob.addWorkOut(workout);
		bob.addWorkOut(workout2);
		
		System.out.println(bob.getNumberOfworkoutsByType(EType.boxing));
		
	}
}
