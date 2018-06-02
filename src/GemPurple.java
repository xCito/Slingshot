import java.awt.Image;
import java.awt.Toolkit;
import java.util.LinkedList;

/**
	Author: Juan Lantigua
	Date: May 13, 2018
*/

public class GemPurple extends Gem{
	
	public GemPurple(int x, int y)
	{
		super(x, y);
		img = loadImg();
	}

	public Image loadImg()
	{
		return Toolkit.getDefaultToolkit().getImage("gem4.png");
	}
	
	// Makes Ball really bouncy
	public void ability( LinkedList<Ball> b, int index )
	{
		b.get(index).bounceFactor = 0.7f;
		b.get(index).mass = 0.15f;
		//0.8f, 0.15f
	}
}
