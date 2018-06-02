import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.Random;

/**
	Author: Juan Lantigua
	Date: Apr 17, 2018
*/

public class Gem extends Ball
{	
	Image img;
	boolean broken = false;
	
	public Gem(int x, int y)
	{
		super(x, y, 22, 0f, 10f);
		//freeze();
		
		img = loadImg();
	}
	
	public void ability(LinkedList<Ball> b, int index)
	{
		
	}
	
	
	/*
	 * References a random gem color for an image
	 */
	public Image loadImg()
	{
		Random r = new Random();
		return Toolkit.getDefaultToolkit().getImage("gem" + (r.nextInt(5)+1) + ".png");
	}
	
	
	public void draw(Graphics g)
	{
		if(!broken)
		{
			g.fillOval(x-r-2, y-r-3, d+4, d+3);
			g.drawImage(img, x-r, y-r, d, d, null);
		}
	}
}
