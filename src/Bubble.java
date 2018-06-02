import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

public class Bubble extends Ball{
	
	Color c;
	
	public Bubble(int x, int y, int r)
	{
		super(x, y, r, 1f, 0.1f);
		c = Main.colors[new Random().nextInt(11)];
		antigravity = true;
		stayOnScreen = true;
		minVelocity = 0;
	}

	public void move()
	{
		super.move();
		if(Math.abs(vx) < 1)
			vx += 0.1 * Math.signum(vx);
		
		if(Math.abs(vy) < 1)
			vy += 0.1 * Math.signum(vy);
	
		if(Math.abs(vx) > 1.5)
			vx -= 0.3 * Math.signum(vx);
		
		if(Math.abs(vy) > 1.5)
			vy -= 0.3 * Math.signum(vy);
	}
	
	public void collideWith(Ball other)
	{
		int dx = other.x - x;
		int dy = other.y - y;
		double d = Math.sqrt(dx*dx + dy*dy);
		
		double overLapHalf = 0.5 * (d - r - other.r); // The amount the balls are overlapping by, divided by 2.
		
		x -= overLapHalf * (x - other.x) / d;		// Pushes back this ball overLapHalf in the correct direction 
		y -= overLapHalf * (y - other.y) / d;		// normalized
		
		other.x += overLapHalf * (x - other.x) / d; // Pushes back other ball overLapHalf in the correct direction 
		other.y += overLapHalf * (y - other.y) / d; // normalized
		
		double nx = dx / d; 
		double ny = dy / d;
		
		double kx = (vx - other.vx);
		double ky = (vy - other.vy);
		double p = 2.0 * (nx * kx + ny * ky) / (1 + 1);
		
		vx = vx - p * 1 * nx;
		vy = vy - p * 1 * ny;
		other.vx = other.vx + p * 1 * nx;
		other.vy = other.vy + p * 1 * ny;
		
//		double nx = (other.x - x) / d; 
//		double ny = (other.y - y) / d; 
//		double p = 2 * (vx * nx + vy * ny - other.vx * nx - other.vy * ny) / 
//		        (mass + other.mass); 
//		vx = (vx - p * mass * nx) * 1; 
//		vy = (vy - p * mass * ny) * 1; 
//		other.vx = (float) ((other.vx + p * other.mass * nx) * 1); 
//		other.vy = (float) ((other.vy + p * other.mass * ny) * 1);	
	}
	
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
	    g2.setStroke(new BasicStroke(1));
		g2.setColor(c);
		g2.drawOval(x-r, y-r, d, d);
	}
}
