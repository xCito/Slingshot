import java.awt.Graphics;

/**
	Author: Juan Lantigua
	Date: Apr 10, 2018
*/

public class Ball extends Circle
{
	double vy = 0;
	double vx = 0;
	int distance;
	float mass;
	float bounceFactor;
	boolean frozen = false;
	float minVelocity;
	boolean antigravity = false;
	boolean onFloor = false;
	boolean notMovingX = true;
	boolean focusedOn = false;
	boolean stayOnScreen = false;
	
	public Ball(int x, int y, int r, float b, float m)
	{
		super(x,y,r);
		bounceFactor = b;
		mass = m;
		minVelocity = 1.1f;
		distance = x;
	
	}
	
	public void bounceY(double vy)
	{
		this.vy -= vy;
		y-= this.vy;
	}
	public void bounceX(double vx)
	{
		this.vx += vx;
		distance += vx;
		x+= this.vx;
	}

	public void move()
	{		
		if(frozen)
			return;								
	
		if(y >= Main.height-(r))									// Check if ball is on bl
			onFloor = true;	
		else
			onFloor = false;
		
		
		if(!antigravity)
			vy += Physics.gravity * mass;						// Apply the force of Gravity

		y += vy; 
		
		if(Math.abs(vx) > 0 && !antigravity)		
			vx += Physics.xdrag*mass * (Math.signum(vx)*-1);
		x += vx;
		distance+=vx;	
		
		if(Math.abs(vx) < minVelocity){
			vx = 0;	notMovingX = true;	}
		else 		notMovingX = false;
		
		if(Math.abs(vy) < minVelocity)
			vy = 0;
		
		if(stayOnScreen)
		{
			checkForBounceX(vx);
			checkForBounceY(vy);
		}
		else 
		{
			if(Math.abs(vx) > minVelocity)
				checkForBounceX(vx);
			if(Math.abs(vy) > minVelocity)
				checkForBounceY(vy);
		}
		if(x > Main.rightBound && focusedOn)
			x = Main.rightBound;
		if(x < Main.leftBound && focusedOn)
			x = Main.leftBound;
	}
	
	public void checkForBounceX(double initVx)
	{
		if(stayOnScreen) {
			// If ball hits a certain x value (walls), BOUNCE back the with opposite velocity
			if(x < r)	 	// left wall
			{
				x = r;			// reset back into screen
				vx = 0;			// kill velocity
				bounceX(-initVx*bounceFactor);			// bounce Right with slightly less velocity
			}
			
			if(x > Main.wall-r)	// right wall
			{
				x = Main.wall-r;	// reset back into screen
				vx = 0;			// kill velocity
				bounceX(-initVx*bounceFactor); 			// bounce Left with slightly less velocity
			}
		}
		else
		{
			if(distance > Main.maxDistance)	// MAX right wall
			{
				System.out.println("Right Wall hit!\ndistance=" + distance +"\nx=" +x);
				distance -= distance - Main.maxDistance;
				x =  1100;	// reset back into screen
				vx = 0;			// kill velocity
				System.out.println("Collision Fixed!\ndistance=" + distance +"\nx=" +x +"\n");
				bounceX(-initVx*bounceFactor); 			// bounce Left with slightly less velocity
			}
			
			if(distance < 0	)	// MAX left wall
			{
				System.out.println("Left Wall hit!\ndistance=" + distance +"\nx=" +x);
				distance = 0;
				x = 100;	// reset back into screen
				vx = 0;			// kill velocity
				System.out.println("Collision Fixed!\ndistance=" + distance +"\nx=" +x +"\n");
				bounceX(-initVx*bounceFactor); 			// bounce Right with slightly less velocity
			}
		}
	}
	
	public void checkForBounceY(double initVy)
	{
		// If ball hits a certain y value (floor), BOUNCE back the with opposite velocity
		if(y > (Main.floor-r))
		{
			y = (Main.floor-r)-1;	// reset back into screen
			vy = 0;					// kill velocity
			bounceY((int)(initVy*bounceFactor));	// bounceUp with slightly less velocity	
		}	
		if(stayOnScreen)
		{
			if(y-r+1 < 0)
			{
				y = r+1;
				vy = 0;
				bounceY((int)(initVy*bounceFactor));	// bounceUp with slightly less velocity	
			}
		}
	}
	
	
	public void freeze()
	{
		frozen = true;
		notMovingX = true;
	}
	public void unfreeze()
	{
		frozen = false;
		notMovingX =false;
	}
	public void removeGravity()
	{
		antigravity = true;
	}
	public void applyGravity()
	{
		antigravity = false;
	}
	
	public void stopAndSet(int x, int y)
	{
		this.x = x;
		this.y = y;
		vx = 0;
		vy = 0;
	}
	public void set(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setSize(int r)
	{
		this.r = r;
		this.d = r*2;
	}
	
	public void collideWith(Ball other) 
	{
		if(other instanceof Gem)
		{
			((Gem) other).broken = true;
			return;
		}
		else if(other instanceof Coin)
		{
			((Coin) other).collected = true;
			return;
		}
//		double angle = Math.atan2( other.y - y, other.x - x);
//		float distanceBetweenCircles = 
//				  (float)Math.sqrt(
//				    (other.x - x) * (other.x - x) + 
//				    (other.y - y) * (other.y - y)
//				  );
//		other.x += (int)(Math.cos(angle) * distanceBetweenCircles);
//		other.y += (int)(Math.cos(angle) * distanceBetweenCircles);
//		
		int dx = other.x - x;
		int dy = other.y - y;
		double d = Math.sqrt(dx*dx + dy*dy);
		
		double overLapHalf = 0.5 * (d - r - other.r); // The amount the balls are overlapping by, divided by 2.
		
		x -= overLapHalf * (x - other.x) / d;		// Pushes back this ball overLapHalf in the correct direction 
		y -= overLapHalf * (y - other.y) / d;		// normalized
		
		other.x += overLapHalf * (x - other.x) / d; // Pushes back other ball overLapHalf in the correct direction 
		other.y += overLapHalf * (y - other.y) / d; // normalized
		
		double nx = dx / d; 
		double ny = dy / d;
		
		double kx = (vx - other.vx);
		double ky = (vy - other.vy);
		double p = 2.0 * (nx * kx + ny * ky) / (mass + other.mass);
		
		vx = vx - p * other.mass * nx;
		vy = vy - p * other.mass * ny;
		other.vx = other.vx + p * mass * nx;
		other.vy = other.vy + p * mass * ny;
		
//		int dx = other.x - x;
//		int dy = other.y - y;
//		double d = Math.sqrt(dx*dx + dy*dy);
//		
//		double nx = (other.x - x) / d; 
//		double ny = (other.y - y) / d; 
//		double p = 2 * (vx * nx + vy * ny - other.vx * nx - other.vy * ny) / 
//		        (mass + other.mass); 
//		vx = (vx - p * mass * nx) * 0.8; 
//		vy = (vy - p * mass * ny) * 0.8; 
//		other.vx = (float) ((other.vx + p * other.mass * nx) * 0.8); 
//		other.vy = (float) ((other.vy + p * other.mass * ny) * 0.8);	
	} 
	
	public void drawArrow(Graphics g)
	{
		int[] xp = {x-20,x,x+20};
		int[] yp = {y-r-20,y-r,y-r-20};
		
		//g.fillRect(x, y-40, 20, 30);
		g.fillPolygon(xp, yp, 3);
	}
	
	public void draw(Graphics g)
	{
		if(focusedOn)
			drawArrow(g);
	}

}
