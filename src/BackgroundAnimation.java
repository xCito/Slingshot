import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.LinkedList;

/**
	Author: Juan Lantigua
	Date: Apr 26, 2018
*/

public class BackgroundAnimation extends GameObject {

	Image layer1;
	Image layer2;
	Image layer4;
	Image layer7;
	Image layer8;
	LinkedList<Integer> l1;
	LinkedList<Integer> l2;
	LinkedList<Integer> l4;
	LinkedList<Integer> l8;
	public BackgroundAnimation()
	{
		super(0, 0);

		l1 = new LinkedList<>();
		l2 = new LinkedList<>();
		l4 = new LinkedList<>();
		l8 = new LinkedList<>();
		
		layer1 = Toolkit.getDefaultToolkit().getImage("layer_01.png");
		layer2 = Toolkit.getDefaultToolkit().getImage("layer_02.png");
		layer4 = Toolkit.getDefaultToolkit().getImage("layer_04.png");
		layer7 = Toolkit.getDefaultToolkit().getImage("layer_07.png");
		layer8 = Toolkit.getDefaultToolkit().getImage("layer_08.png");
		
		for(int i=0; i<20; ++i){	
			l1.add((i*1200)-3600);
			l2.add((i*1200)-3600);
			l4.add((i*1200)-3600);
			l8.add((i*1200)-3600);
		}	
	}
	
	public void moveHorizontalBy(int dx)
	{
		for(int i=0; i<l1.size(); ++i){
			l1.set(i , l1.get(i) + dx);
			l2.set(i , l2.get(i) + dx/2);
			l4.set(i , l4.get(i) + dx/2);
			l8.set(i , l8.get(i) + dx/8);
			
		}
	}
	
	public void resetPosition()
	{
		for(int i=0; i<20; ++i){	
			l1.add((i*1200)-3600);
			l2.add((i*1200)-3600);
			l4.add((i*1200)-3600);
			l8.add((i*1200)-3600);
		}	
	}
	
//	public void scrollGround(int dx)
//	{
//		for(int i=0; i<gx.size(); ++i)
//			gx.set(i , gx.get(i) - dx);
//		
//		if(gx.get(2) >= 600)	// background going off to the right
//		{
//			int x = gx.peekFirst();
//			gx.removeLast();
//			gx.addFirst(x-1200);
//		}
//
//		
//		if(gx.get(2) <= -600)	// background going off to the left
//		{
//			int x = gx.peekLast();
//			gx.removeFirst();
//			gx.addLast(x+1200);
//		}
//	}
//	public void scrollSky(int dx)
//	{
//		for(int i=0; i<gx.size(); ++i)
//			sx.set(i , sx.get(i) - dx/4);
//
//		if(sx.get(2) >= 600)
//		{
//			int x = sx.peekFirst();
//			sx.removeLast();
//			sx.addFirst(x-1200);
//		}
//		
//		if(sx.get(2) <= -600)
//		{
//			int x = sx.peekLast();
//			sx.removeFirst();
//			sx.addLast(x+1200);
//		}
//	}
	public void draw(Graphics g)
	{	
		for(int i=0; i<20; ++i){
			g.drawImage(layer8, l8.get(i)-i, 0, Main.width, Main.height, null);
			g.drawImage(layer4, l4.get(i)-i, 0, Main.width, Main.height, null);
			g.drawImage(layer2, l2.get(i)-i, 0, Main.width, Main.height, null);
			g.drawImage(layer1, l1.get(i)-i, 0, Main.width, Main.height, null);
		}
		g.drawImage(layer7, 0, 0, Main.width, Main.height, null);
	}

}
