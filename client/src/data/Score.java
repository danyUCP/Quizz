package data;

public class Score 
{
	private int pointsJ1;
	private int pointsJ2;

	
	public Score(int pointsJ1, int pointsJ2) 
	{
		this.pointsJ1 = pointsJ1;
		this.pointsJ2 = pointsJ2;
	}
	
	
	public int getPointsJ1() 
	{
		return pointsJ1;
	}
	
	public void setPointsJ1(int pointsJ1) 
	{
		this.pointsJ1 = pointsJ1;
	}
	
	public int getPointsJ2() 
	{
		return pointsJ2;
	}
	
	public void setPointsJ2(int pointsJ2) 
	{
		this.pointsJ2 = pointsJ2;
	}


	public String toString() 
	{
		return pointsJ1 + " - " + pointsJ2;
	}
	
	

}
