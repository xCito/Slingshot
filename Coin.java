import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
	Author: Juan Lantigua
	Date: May 8, 2018
*/

public class Coin extends Ball 
{
	Image img;
	boolean collected = false; 
	
	public Coin(int x, int y)
	{
		super(x, y, 25, 0f, 10f);
		img = loadImg();
	}
	
	/*
	 * References a random gem color for an image
	 */
	public Image loadImg()
	{
		return Toolkit.getDefaultToolkit().getImage("bitcoin.gif");
	}
	
	
	public void draw(Graphics g)
	{
		
		if(!collected)
			g.drawImage(img, x-r, y-r, d, d, null);
		
	}
}
