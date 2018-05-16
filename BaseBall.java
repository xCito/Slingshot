import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
	Author: Juan Lantigua
	Date: Apr 26, 2018
*/

public class BaseBall extends Ball{

	Image still;
	Image left;
	Image right;
	
	public BaseBall(int x, int y) {
		super(x, y, 10, 0.4f, 0.2f);
		
		still = Toolkit.getDefaultToolkit().getImage("baseball.png");
		left = Toolkit.getDefaultToolkit().getImage("baseballleft.gif");
		right = Toolkit.getDefaultToolkit().getImage("baseballright.gif");
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
