import java.awt.Image;
import java.awt.Toolkit;
import java.util.LinkedList;

/**
	Author: Juan Lantigua
	Date: May 13, 2018
*/

public class GemRed extends Gem{
	
	public GemRed(int x, int y)
	{
		super(x, y);
		img = loadImg();
	}

	public Image loadImg()
	{
		return Toolkit.getDefaultToolkit().getImage("gem1.png");
	}
	
	// Duplicate Ball
	public void ability( LinkedList<Ball> b, int index )
	{
		Ball ball;
		
		if(b.get(index) instanceof SoccerBall)
			ball = new SoccerBall(b.get(index).x-d, b.get(index).y-d);
		else if(b.get(index) instanceof BowlingBall)
			ball = new BowlingBall(b.get(index).x-d, b.get(index).y-d);
		else if(b.get(index) instanceof BaseBall)
			ball = new BaseBall(b.get(index).x-d, b.get(index).y-d);
		else if(b.get(index) instanceof DiscoBall)
			ball = new DiscoBall(b.get(index).x-d, b.get(index).y-d);
		else 
			ball = new CrazyBall(b.get(index).x-d, b.get(index).y-d);
							
		ball.vx = -b.get(index).vx; 
		ball.vy = -50f;
		ball.focusedOn = false;
		b.add(ball);
	}
}
