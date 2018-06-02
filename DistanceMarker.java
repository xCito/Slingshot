import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
	Author: Juan Lantigua
	Date: May 12, 2018
*/

public class DistanceMarker extends GameObject {

	int distance;
	public DistanceMarker(int x, int y, int d)
	{
		super(x,y);
		distance = d;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(x-10, y-10, 100, 50);
		g.setColor(Color.yellow);
		g.setFont(new Font("Times New Roman", Font.BOLD, 20));
		g.drawString("" + distance, x+20, y+25);
	}
}
