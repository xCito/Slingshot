import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
	Author: Juan Lantigua
	Date: May 15, 2018
*/

public class Slider {

	String title;
	int tickX;
	int tickY;
	int tickW;
	int tickH;
	int x;
	int y;
	int w;
	int h;
	int range;
	boolean held = false;
	Color tickColor;
	
	public Slider(int x, int y, int w, int h, int r, String t)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		tickX = x;
		tickY = (y+h)/2;
		tickW = w;
		tickH = 30;
		range = r;
		title = t;
		tickColor = Color.cyan;
	}
	
	public boolean sliderHeld(int mx, int my)
	{
		if(tickX < mx && mx < tickX+tickW && tickY < my && my < tickY+tickH)
		{
			held = true;
			return true;
		}
		held = false;
		return false;
	}
	
	public void setColor(Color c)
	{
		tickColor = c;
	}
	public void slideHandle(int dy)
	{
		tickY += dy;
		
		if(tickY < this.y)
			tickY = this.y;
		
		if(tickY+tickH > y+h)
			tickY = y+h-tickH;
		
	}
	
	public int getValue()
	{
		return (int)(( (double)tickY-y )/( h-tickH )*range);
	}
	public void draw(Graphics g)
	{
		g.setColor(Color.gray);
		g.fillRect(x, y, w, h);
		g.setColor(tickColor
				);
		g.fillRect(tickX, tickY, tickW, tickH);
		
		g.setColor(Color.black);
		g.setFont(new Font("Courier New", Font.BOLD, 16));
		g.drawString(title, x-30, y-10);
		g.drawString(""+getValue(), x+10, h+y+20);
	}
}
