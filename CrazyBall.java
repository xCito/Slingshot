import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
	Author: Juan Lantigua
	Date: Apr 26, 2018
*/

public class CrazyBall extends Ball 
{

	Image still;
	Image left;
	Image right;
	
	public CrazyBall(int x, int y) {
		super(x, y, 21, 0.71f, 0.1999f);
		super.minVelocity = 0.1f;
		still = Toolkit.getDefaultToolkit().getImage("crazyball.png");
		left = Toolkit.getDefaultToolkit().getImage("crazyballleft.gif");
		right = Toolkit.getDefaultToolkit().getImage("crazyballright.gif");
		
	}
	
	public void draw(Graphics g)
	{	
		super.draw(g);
		
		if(notMovingX)
			g.drawImage(still, x-r, y-r, d, d, null);
		else
		{
			if(vx > 0)
				g.drawImage(right, x-r, y-r, d, d, null);
			else
				g.drawImage(left, x-r, y-r, d, d, null);
		}
	}
}
