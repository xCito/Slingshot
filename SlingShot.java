import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

/**
	Author: Juan Lantigua
	Date: Apr 12, 2018
*/

public class SlingShot extends GameObject
{
	int pullx;
	int pully;
	Ball projectile;
	Ball placeHolder;
	float strength = 0.2f;
	boolean held = false;
	Polygon leftBar = new Polygon();
	Polygon rightBar = new Polygon();
	Polygon leftSling = new Polygon();
	Polygon rightSling = new Polygon();
	
	public SlingShot(int x, int y)
	{
		super(x, y);
	
		pullx = x; 
		pully = y;
		
		leftBar.addPoint(x-10, y+45); // bl
		leftBar.addPoint(x-0, y+40); // br
		leftBar.addPoint(x-20, y-20); // tr
		leftBar.addPoint(x-30, y-20); // tl
		
		rightBar.addPoint(x+0, y+40);	// bl
		rightBar.addPoint(x+10, y+45);// br
		rightBar.addPoint(x+20, y+20);// tr
		rightBar.addPoint(x+10, y+20);// tl
		
		projectile = new Ball(0,0,1, 0,0);
		//load(placeHolder,x,y);
		
	}
	
	public void load(Ball b, int x, int y)
	{
		projectile = b;
		projectile.freeze();
		pullBack(x, y);
	}
	
	public void pullBack(int x, int y)
	{
		held = true;
		pullx = x;
		pully = y;
		projectile.stopAndSet(pullx - projectile.r, pully - projectile.r);
	}
	
	public void release()
	{
		held = false; 

		projectile.unfreeze();
		projectile.set(x - projectile.r, y - projectile.r);
		projectile.bounceX((x - pullx) * strength );
		projectile.bounceY((pully - y) * strength );

		pullx = x;	// to reset line
		pully = y;  // to reset line
		//load(placeHolder,x,y);
	}
	
	public void moveHorizontalBy(int dx)
	{
		x += dx;
	}
	
	public void SlingMovement()
	{
		leftSling.reset();
		rightSling.reset();
		leftBar.reset();
		rightBar.reset();

		
		if(held)
		{
			if(x - pullx > 0) // left
			{
				leftSling.addPoint(pullx, pully-projectile.r); // bl
				leftSling.addPoint(pullx, pully-projectile.r+5); // br
				rightSling.addPoint(pullx- projectile.d, pully-projectile.r  );	// bl
				rightSling.addPoint(pullx- projectile.d, pully-projectile.r+5);// br
			}
			else	// right
			{
				leftSling.addPoint(pullx-projectile.r, pully+5); // br
				leftSling.addPoint(pullx-projectile.r, pully); // bl
				rightSling.addPoint(pullx+ projectile.r, pully  );	// bl
				rightSling.addPoint(pullx+ projectile.r, pully+5);// br
			}
			
			leftSling.addPoint(x-20, y-20); // tr
			leftSling.addPoint(x-30, y-20); // tl
			
			
			rightSling.addPoint(x+20, y+25);// tr
			rightSling.addPoint(x+10, y+20);// tl
		}
		else
		{
			leftSling.addPoint(x, y+5); // bl
			leftSling.addPoint(x, y-5); // br
			leftSling.addPoint(x-20, y-20); // tr
			leftSling.addPoint(x-30, y-20); // tl
			
			rightSling.addPoint(x, y+5);	// bl
			rightSling.addPoint(x, y-5);// br
			rightSling.addPoint(x+20, y+25);// tr
			rightSling.addPoint(x+10, y+20);// tl
		}
		
		leftBar.addPoint(x-10, y+45); // bl
		leftBar.addPoint(x-0, y+40); // br
		leftBar.addPoint(x-20, y-20); // tr
		leftBar.addPoint(x-30, y-20); // tl
		
		rightBar.addPoint(x+0, y+40);	// bl
		rightBar.addPoint(x+10, y+45);// br
		rightBar.addPoint(x+20, y+20);// tr
		rightBar.addPoint(x+10, y+20);// tl
		
	}
	
	public void draw(Graphics g)
	{
		SlingMovement();
		g.setColor(Color.black);
//		if(held)
//			g.drawLine(x, y, pullx, pully);
		g.fillRect(x-10, y+40, 20, 60);		// base 
		g.fillPolygon(leftBar);
		g.fillPolygon(rightBar);
		
		g.setColor(Color.red);
		g.fillPolygon(leftSling);
		g.fillPolygon(rightSling);
	}
}
