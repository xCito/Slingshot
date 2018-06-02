/**
	Author: Juan Lantigua
	Date: May 8, 2018
*/

public class GameObject {
	int x;	// x axis position 
	int y;	// y axis position
	int origX;
	int origY;
	
	public GameObject(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		origX = x;
		origY = y;
	}
	
	public void moveHorizontalBy(int dx)
	{
		x += dx;
	}
	
	public void resetPosition()
	{
		x = origX;
		y = origY;
	}
	
}
