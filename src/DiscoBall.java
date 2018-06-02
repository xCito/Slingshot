import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
	Author: Juan Lantigua
	Date: Apr 26, 2018
*/

public class DiscoBall extends Ball{

	Image still;
	Image left;
	Image right;
	
	public DiscoBall(int x, int y) {
		super(x, y,  30, 0.5f, 0.17f);
		
		still = Toolkit.getDefaultToolkit().getImage("discoball.png");
		left = Toolkit.getDefaultToolkit().getImage("discoballleft.gif");
		right = Toolkit.getDefaultToolkit().getImage("discoballright.gif");
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
