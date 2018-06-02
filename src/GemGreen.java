import java.awt.Image;
import java.awt.Toolkit;
import java.util.LinkedList;

/**
	Author: Juan Lantigua
	Date: May 13, 2018
*/

public class GemGreen extends Gem{
	
	public GemGreen(int x, int y)
	{
		super(x, y);
		img = loadImg();
	}

	public Image loadImg()
	{
		return Toolkit.getDefaultToolkit().getImage("gem2.png");
	}

	// Grows the colliding Ball
	public void ability( LinkedList<Ball> b, int index )
	{
		b.get(index).setSize(b.get(index).r*2);
	}
}
