import java.awt.Image;
import java.awt.Toolkit;

/**
	Author: Juan Lantigua
	Date: May 13, 2018
*/

public class GemYellow extends Gem{
	
	public GemYellow(int x, int y)
	{
		super(x, y);
		img = loadImg();
	}

	public Image loadImg()
	{
		return Toolkit.getDefaultToolkit().getImage("gem3.png");
	}
	
}
