import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
	Author: Juan Lantigua
	Date: May 14, 2018
*/

public class Button {
	
	int x;
	int y;
	int w;
	int h;
	String text;
	Color hover  = new Color(76, 232, 118);
	Color normal = new Color(47, 153, 75);
	Color c;
	int fontSize = 30;
	int stringWidth;
	
	public Button(int x, int y, int w, int h, String t)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		text = t;
		stringWidth = t.length();
		c = normal;
	}
	
	public void onHover(int mx, int my)
	{
		if(x < mx && mx < x+w && y < my && my < y+h)
			c = hover;
		else
			c = normal;
	}
	
	public boolean clicked(int mx, int my)
	{
		if(x < mx && mx < x+w && y < my && my < y+h)
			return true;
		return false;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(x-2, y-2, w+4, h+4);
		g.setColor(c);
		g.fillRect(x, y, w, h);
		g.setColor(Color.black);
		g.setFont(new Font("Courier New", Font.BOLD, fontSize));
		g.drawString(text, x+(stringWidth+1), y+fontSize+5);
		
	}
}
