import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
	Author: Juan Lantigua
	Date: Apr 26, 2018
*/

public class SoccerBall extends Ball 
{
	Image still;
	Image left;
	Image right;
	
	public SoccerBall(int x, int y) 
	{
		super(x, y, 25, 0.8f, 0.15f);
		
		still = Toolkit.getDefaultToolkit().getImage("soccerball.png");
		left = Toolkit.getDefaultToolkit().getImage("soccerballleft.gif");
		right = Toolkit.getDefaultToolkit().getImage("soccerballright.gif");
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
