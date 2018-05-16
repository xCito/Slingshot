import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
	Author: Juan Lantigua
	Date: Apr 26, 2018
*/

public class BowlingBall extends Ball{

	Image still;
	Image left;
	Image right;
	
	public BowlingBall(int x, int y) {
		super(x, y, 25, 0.3f, 0.4f);
		
		still = Toolkit.getDefaultToolkit().getImage("bowling_ball.png");
		left = Toolkit.getDefaultToolkit().getImage("bowling_ballleft.gif");
		right = Toolkit.getDefaultToolkit().getImage("bowling_ballright.gif");
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
