/**
	Author: Juan Lantigua
	Date: Apr 12, 2018
*/

public class Circle extends GameObject 
{
	int r;	// radius
	int d; 	// diameter
	double vy = 0;
	double vx = 0;
	float mass = 0.25f;
	float A;	// angle
	int origR;
	public Circle(int x, int y, int r)
	{
		super(x, y);
		this.r = r;
		this.d = r*2;
		this.origR = r;
	}
	
	public void resetSize()
	{
		r = origR;
		d = origR*2;
	}
	
	public boolean isCollision(Circle other)
	{
		int dx = other.x - x;
		int dy = other.y - y;
		double d = Math.sqrt(dx*dx + dy*dy);		//  d^2 =  (dx^2 + dy^2)
		
		if(d < (other.r + r))
		{
			double midpointx = (x + other.x) / 2; 
			double midpointy = (y + other.y) / 2;
			
			x = (int) (midpointx + r * (x - other.x) / d); 
			y = (int) (midpointy + r * (y - other.y) / d); 
			other.x = (int) (midpointx + other.r * dx / d); 
			other.y = (int) (midpointy + other.r * dy / d);
			
			return true;
		}
		return false;
	}
}
