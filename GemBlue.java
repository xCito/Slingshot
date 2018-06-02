import java.awt.Image;
import java.awt.Toolkit;
import java.util.LinkedList;

/**
	Author: Juan Lantigua
	Date: May 13, 2018
*/

public class GemBlue extends Gem{
	
	public GemBlue(int x, int y)
	{
		super(x, y);
		img = loadImg();
	}

	public Image loadImg()
	{
		return Toolkit.getDefaultToolkit().getImage("gem5.png");
	}
	
	// Freezes Ball in place
	public void ability( LinkedList<Ball> b, int index )
	{
		b.get(index).frozen = true;
	}
}
