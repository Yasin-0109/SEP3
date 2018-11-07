package model;


/**
 * Stores data about EUserRole
 * 
 * @author Jaser Ghasemi (267243)
 * @version 1.0
 */
public enum EUserRole 
{
	Admin (3),
	Instructor (2),
	Member (1);
	
	private final int ID;
	
	EUserRole(int ID)
	{
		this.ID = ID;
	}
	
	public int getID()
	{
		return this.ID; 
	}
}
