import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
	Author: Juan Lantigua
	Date: Apr 10, 2018
*/

@SuppressWarnings("serial")
public class Main extends Applet implements Runnable, MouseListener, MouseMotionListener, KeyListener
{
	Image background; 
	Image Screen;
    Graphics ScreenG;
    BackgroundAnimation bg; 
    
    static final short leftBound = 200;
    static final short rightBound = 1000;
    static final short height = 600;
    static final short width = 1200;
    static final short floor = 580;
    static final short wall = 1200;
    static final int maxDistance = width*7;
    static Color[] colors = { Color.black , Color.blue , Color.green,
    						  Color.red   , Color.cyan , Color.pink, 
    						  Color.yellow, Color.gray , Color.magenta,
    						  Color.orange, Color.white, Color.lightGray};

    
    Random r = new Random();
	Thread t;
	boolean mDown = false;
	boolean gameOver = false;
	int mx;
	int my;
	
	String title = "Slingshot!";
	Rectangle rect = new Rectangle(100, 100, 30, 30);
	
	// Menu Items
	Bubble[] bubble = new Bubble[50];
	
	Button startBtn;
	Slider lifeSlider;
	Slider bGemSlider;
	Slider rGemSlider;
	Slider yGemSlider;
	Slider pGemSlider;
	Slider gGemSlider;
	Slider coinSlider;
	
	int numLifeLine;
	int numRedGem;
	int numBlueGem;
	int numYellowGem;
	int numPurpleGem;
	int numGreenGem;
	int numCoins;
	
	boolean menuOpen = true;
	boolean pause = false;
	boolean sliderHeld = false;
	
	// Game Play Objects/Items
	int points = 0;
	Button restartBtn;
	Button pauseBtn;
	DistanceMarker[] dm = new DistanceMarker[10];
	ArrayList<Gem> gem = new ArrayList<>();
	ArrayList<Coin> coin = new ArrayList<>();
	ArrayList<int[]> collisionRecord = new ArrayList<>();
	ArrayList<GameObject> gameObj = new ArrayList<>();
	ArrayList<Ball> inLoading = new ArrayList<>();
	LinkedList<Ball> activeBalls = new LinkedList<>();
	SlingShot sling;
	
	
	public void init()
	{
		this.resize(width, height);
        this.requestFocus();
        
        Screen=createImage(getSize().width, getSize().height);
        ScreenG=Screen.getGraphics();
        ScreenG.setColor(Color.black);
        
        createUIObjects();
        createGameObjects();    
       
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        
        t = new Thread(this);
        t.start();
	}
	
	@SuppressWarnings("static-access")
	public void run()
	{
		while(true)
        {
			
			if(!pause)
			{
			
				if(!gameOver)
				{
					if(menuOpen)
					{
						// Collision detection between active balls 
						for(int i=0, size = bubble.length; i < size; ++i)
							for(int j=0; j < size; ++j)
								if(i != j)
									if(bubble[i].isCollision(bubble[j]) && !alreadyCollided(i, j))
									{	
										bubble[i].collideWith(bubble[j]);
										collisionRecord.add( new int[]{i,j} );
									}
						
						collisionRecord.clear();
						
						for(Bubble b: bubble)
							b.move();
						
//						System.out.println("X: " + bubble[0].x);
//						System.out.println("Y: " + bubble[0].y);
//						System.out.println("Vx: " + bubble[0].vx);
//						System.out.println("Vy: " + bubble[0].vy + "\n");
						
					}
					else
					{
						// Camera follows the ball in focus, (moves other objects in opposite direction)
						if((activeBalls.peekFirst().x >= rightBound || activeBalls.peekFirst().x <= leftBound) && 
						   (activeBalls.peekFirst().vx > 0.1f || activeBalls.peekFirst().vx < -0.1f)){
						
							for(GameObject g: gameObj)
								g.moveHorizontalBy((int)-activeBalls.peekFirst().vx);
						
						}
						
					
			//			// Collision detection between active ball and Gems 
			//			//		(Main active BALL collide with gems)
			//			for(int i=0; i<gem.size(); ++i)
			//			{
			//				Gem g = gem.get(i);
			//				if( activeBalls.peekFirst().isCollision(g) ){
			//					activeBalls.peekFirst().collideWith(g);
			//					g.ability(activeBalls, 0);
			//					points+=5;
			//					gem.remove(g);
			//					gameObj.remove(g);
			//				}
			//			}
					
						// Collision detection between active balls and Gems
						//		(ALL active BALLS collide with gems)
						for(int j=0, size = activeBalls.size(); j< size; ++j)
						{
							for(int i=0; i<gem.size(); ++i)
							{
								Gem g = gem.get(i);
								if( activeBalls.get(j).isCollision(g) ){
									activeBalls.get(j).collideWith(g);
									g.ability(activeBalls, j);
									points+=5;
									gem.remove(g);
									gameObj.remove(g);
								}
							}
						}
						
						// Collision detection between active balls and Coins
						for(int j=0, size = activeBalls.size(); j< size; ++j)
						{
							for(int i=0; i<coin.size(); ++i)
							{
								Coin c = coin.get(i);
								if( activeBalls.get(j).isCollision(c) ){
									activeBalls.get(j).collideWith(c);
									points+=10;
									coin.remove(c);
									gameObj.remove(c);
								}
							}
						}
						// Collision detection between active balls 
						if(activeBalls.size() > 1)
						{
							for(int i=0, size = activeBalls.size(); i< size; ++i)
								for(int j=0; j< size; ++j)
									if(i != j)
										if(activeBalls.get(i).isCollision(activeBalls.get(j)) && !alreadyCollided(i, j))
										{	
											activeBalls.get(i).collideWith(activeBalls.get(j));
											collisionRecord.add( new int[]{i,j} );
										}
						}
						collisionRecord.clear();
						
						
						// Move all the active balls
						for(Ball x: activeBalls)
						{	
							x.move();
						}
					}
				}
			}
			
			repaint();
            try{
                t.sleep(16);
            }
            catch(Exception e){}
        }
	}
	
	public void paint(Graphics g)
	{
		ScreenG.clearRect(0, 0, width, height);
		
		if(menuOpen)
		{
			drawMainMenu(g);
		}else
		{
			// Draw the background
			bg.draw(g);
			
			// Draw all the coins
			for(Coin c: coin)
				c.draw(g);
			
			// Draw all the gems
			for(Gem gm: gem)
				gm.draw(g);
			
			// Draw all the distance markers
			for(DistanceMarker d: dm)
				d.draw(g);
			
			// Draws the bar in upper left corner w/ balls
			drawBallsBar(g);
			
			// Draw the sling-shot
			sling.draw(g);
			
			// Draw the balls actively moving
			for(Ball x: activeBalls)
				x.draw(g);
			
			// Draw out the points system
			g.setColor(Color.black);
			g.fillRect(930, -20, 140, 50);
			g.setColor(Color.magenta);
			Font myFont = new Font ("Cracked", 1, 20);
			g.setFont(myFont);
			g.drawString("Points: " + points, 935, 23);
			
			
			restartBtn.draw(g);
			pauseBtn.draw(g);
			
			if(pause)
			{
				g.setColor(new Color(43, 54, 71, 150));
				g.fillRect(50, 50, width-100, height-100);

				g.setColor(Color.black);
				g.setFont(new Font("Papyrus", Font.PLAIN, 120)); 
				g.drawString(title, 350, 200);
				g.setColor(Color.green);
				g.drawString(title, 355, 204);
				
				g.setColor(Color.BLACK);
				g.setFont( new Font ("Cracked", 1, 100));
				g.drawString("PAUSED", 400, 400);
			}
			
			if(gameOver)
			{
				g.setColor(new Color(43, 54, 71, 150));
				g.fillRect(50, 50, width-100, height-100);

				g.setColor(Color.black);
				g.setFont(new Font("Papyrus", Font.PLAIN, 120)); 
				g.drawString(title, 350, 200);
				g.setColor(Color.green);
				g.drawString(title, 355, 204);
				
				g.setColor(Color.BLACK);
				g.setFont( new Font ("Cracked", 1, 100));
				g.drawString("GAME OVERRRR", 200, 400);
			}
			
		}
		
	}
	
	public void drawBallsBar(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(5, 5, 330, 50);
		g.setColor(Color.yellow);
		g.fillRect(8, 8, 326, 45);
		g.setColor(Color.black);
		
		for(Ball x: inLoading)
			x.draw(g);
		
	}
	public void update(Graphics g)
	{
		if ( Screen == null ) {
            Screen = createImage(getHeight(), getHeight());
        }
        ScreenG = Screen.getGraphics();

        ScreenG.setColor(getBackground());
        ScreenG.fillRect(0, 0, getWidth(), getHeight());

        ScreenG.setColor(getForeground());
        paint(ScreenG);
        g.drawImage(Screen, 0, 0, null);
	}
	
	public void createGameObjects()
	{
    	bg = new BackgroundAnimation();
    	sling = new SlingShot(300, 450);
    	
    	
    	// Create the coins
    	for(int i=0; i<numCoins; ++i)
    	{
    		Coin c = new Coin(r.nextInt(maxDistance-200)+100, r.nextInt(height-100));
    		coin.add(c);
    		gameObj.add(c);
    	}
    	
    	// Create the distance Markers
    	for(int i=0; i<dm.length; ++i)
    	{
    		dm[i] = new DistanceMarker((i*width), 100, width+(i*width));
    		gameObj.add(dm[i]);
    	}
    	
    	// Create Balls to add to loading section
    	for(int i=0; i< numLifeLine; ++i) 
        {
        	Ball b = getRandomBall();
        	b.freeze();
        	b.setSize(15);
        	b.stopAndSet(20 + (i*40), 30);
        	inLoading.add(b);
        }
        
    	// Creates All the gems
    	gem = createGems(); 
       	gameObj.addAll(gem);
        
        gameObj.add(sling);
        gameObj.add(bg);
        
        Ball b = inLoading.remove(0);
        b.resetSize();
        b.unfreeze();
        b.focusedOn = true;
        activeBalls.add(b);
    }
	
	public void restartGame()
	{
		gameObj.clear();
		coin.clear();
		activeBalls.clear();
		gem.clear();
		inLoading.clear();
		points = 0;
		gameOver = false;
		pause = false;
		menuOpen = true;
		createGameObjects();
		
	}
	
	public void createUIObjects()
	{
		startBtn = new Button(700, 300, 320, 116, " Start");
		startBtn.fontSize = 70;
		restartBtn = new Button(1100, 10, 97, 30, " Restart");
		restartBtn.fontSize = 15;
		pauseBtn = new Button(1100, 50, 97,30, "  Pause");
		pauseBtn.fontSize = 15;
		
		lifeSlider = new Slider(40, 100, 30, 400, 10, "Life-Line");
		bGemSlider = new Slider(130, 150, 30, 400, 10, "   Blue");
		rGemSlider = new Slider(200, 150, 30, 400, 200, "   Red");
		yGemSlider = new Slider(270, 150, 30, 400, 50, " Yellow");
		pGemSlider = new Slider(340, 150, 30, 400, 50, " Purple");
		gGemSlider = new Slider(410, 150, 30, 400, 10, "  Green");
		coinSlider = new Slider(480, 150, 30, 400, 500,"  Coins");
		
		bGemSlider.setColor(new Color(96, 117, 255));
		rGemSlider.setColor(new Color(221, 19, 19));
		yGemSlider.setColor(new Color(215, 234, 0));
		pGemSlider.setColor(new Color(204, 66, 255));
		gGemSlider.setColor(new Color(69, 181, 41));
		coinSlider.setColor(new Color(250, 255, 0));
		
		numLifeLine = lifeSlider.getValue();
		numRedGem = rGemSlider.getValue();
		numBlueGem = bGemSlider.getValue();
		numYellowGem = yGemSlider.getValue();
		numPurpleGem = pGemSlider.getValue();
		numGreenGem = gGemSlider.getValue();
		numCoins = coinSlider.getValue();
		
		for(int i=0; i<bubble.length; ++i) {
			bubble[i] = new Bubble(r.nextInt(800)+ 100, r.nextInt(400)+100, 40);
			bubble[i].vx = r.nextInt(5)-2;		// range is 1, 0 ,-1
			bubble[i].vy = r.nextInt(5)-2;		// range is 1, 0 ,-1
		}
	}
	
	public void drawMainMenu(Graphics g)
	{
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, width, height);
		
		for(Bubble b: bubble)
			b.draw(g);
		
		g.setColor(Color.black);
		g.setFont(new Font("Papyrus", Font.PLAIN, 120)); 
		g.drawString(title, 570, 200);
		g.setColor(Color.green);
		g.drawString(title, 575, 204);
		
		startBtn.draw(g);
		
		lifeSlider.draw(g);
		gGemSlider.draw(g);
		rGemSlider.draw(g);
		bGemSlider.draw(g);
		yGemSlider.draw(g);
		pGemSlider.draw(g);
		coinSlider.draw(g);
	}
	
	public Ball getRandomBall()
	{
		int choice = r.nextInt(5);
		
		switch(choice)
		{
			case 0:
				return new SoccerBall(100, 200);
			case 1:
				return new BowlingBall(100, 200);
			case 2:
				return new DiscoBall(100, 200);
			case 3:
				return new BaseBall(100, 200);
			default:
				return new CrazyBall(100, 200);
		}
	}
	
	public ArrayList<Gem> createGems()
	{
		ArrayList<Gem> g = new ArrayList<Gem>();
		
		for(int i=0; i<numRedGem; ++i)
			g.add(new GemRed(r.nextInt(maxDistance-200)+500, r.nextInt(height-100)));
		for(int i=0; i<numBlueGem; ++i)
			g.add(new GemBlue(r.nextInt(maxDistance-200)+500, r.nextInt(height-100)));
		for(int i=0; i<numGreenGem; ++i)
			g.add(new GemGreen(r.nextInt(maxDistance-200)+500, r.nextInt(height-100)));
		for(int i=0; i<numYellowGem; ++i)
			g.add(new GemYellow(r.nextInt(maxDistance-200)+500, r.nextInt(height-100)));
		for(int i=0; i<numPurpleGem; ++i)
			g.add(new GemPurple(r.nextInt(maxDistance-200)+500, r.nextInt(height-100)));
	
		return g;
	}
	
	public void setNewActiveBall()
	{
		if(inLoading.size() == 0)
		{	
			gameOver = true;
			return;
		}
		Ball b = inLoading.remove(0);
		b.resetSize();
		b.unfreeze();
		b.focusedOn = true;
		activeBalls.clear();
		activeBalls.add(b);
	}
	
	public void resetCameraPosition()
	{
		int distTraveled = sling.origX - sling.x;

		for(GameObject g: gameObj){	
			g.resetPosition();
		}
		activeBalls.peekLast().focusedOn = false;
		activeBalls.peekLast().x += distTraveled;
		
	}
	
	public boolean alreadyCollided(int b1, int b2)
	{
		for(int i=0; i<collisionRecord.size(); ++i)
		{
			int[] arr = collisionRecord.get(i);
			if((arr[0] == b1 && arr[1] == b2) || (arr[1] == b1 && arr[0] == b2))
				return true;
		}
		return false;
	}
	
// Mouse Listeners	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		int my = e.getY();
		
		if(menuOpen && mDown)
		{
			if(lifeSlider.held)
				lifeSlider.slideHandle(my - this.my);
			else if(gGemSlider.held)
				gGemSlider.slideHandle(my - this.my);
			else if(rGemSlider.held)
				rGemSlider.slideHandle(my - this.my);
			else if(bGemSlider.held)
				bGemSlider.slideHandle(my - this.my);
			else if(yGemSlider.held)
				yGemSlider.slideHandle(my - this.my);
			else if(pGemSlider.held)
				pGemSlider.slideHandle(my - this.my);
			else if(coinSlider.held)
				coinSlider.slideHandle(my - this.my);
			
			this.my = my;
		}
		else
		{
			if(mDown && !pause)
				sling.pullBack(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(menuOpen)
		{
			startBtn.onHover(mx, my);
		}
		else
		{
			//restartBtn.onHover(mx, my);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.mx = e.getX();
		this.my = e.getY();
		mDown = true;
		if(menuOpen)
		{	
			if(startBtn.clicked(this.mx, this.my))
			{	
				numLifeLine = lifeSlider.getValue();
				numRedGem = rGemSlider.getValue();
				numBlueGem = bGemSlider.getValue();
				numYellowGem = yGemSlider.getValue();
				numPurpleGem = pGemSlider.getValue();
				numGreenGem = gGemSlider.getValue();
				numCoins = coinSlider.getValue();
				restartGame();
				
				menuOpen = false;
			}
			lifeSlider.sliderHeld(this.mx, this.my);
			bGemSlider.sliderHeld(this.mx, this.my);
			rGemSlider.sliderHeld(this.mx, this.my);
			gGemSlider.sliderHeld(this.mx, this.my);
			yGemSlider.sliderHeld(this.mx, this.my);
			pGemSlider.sliderHeld(this.mx, this.my);
			coinSlider.sliderHeld(this.mx, this.my);
		}
		else
		{
			if(restartBtn.clicked(mx, my))
				restartGame();
			
			if(!gameOver)
			{if(pauseBtn.clicked(mx, my) && !pause){
				pause = true; 
				pauseBtn.text = "Unpause";
				for(Ball b: activeBalls)
					b.freeze();
			}
			else if(pauseBtn.clicked(mx, my) && pause){
				pause = false; 
				pauseBtn.text = "Pause";
				for(Ball b: activeBalls)
					b.unfreeze();
			}
			else
				sling.load(activeBalls.get(activeBalls.size()-1), this.mx, this.my);
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mDown = false;
		
		if(menuOpen)
		{
			
			
			lifeSlider.held = false;
			rGemSlider.held = false;
			bGemSlider.held = false;
			gGemSlider.held = false;
			yGemSlider.held = false;
			pGemSlider.held = false;
			coinSlider.held = false;
		}
		else
		{
			if(!pause || gameOver)
				sling.release();
		}
		//frozen = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		if(!pause)
		{
			int code = e.getKeyCode();
			
			if(code == KeyEvent.VK_SPACE)
			{	
				resetCameraPosition();
				setNewActiveBall();
			}
		}
	}
}

/* Collision detection or AI
 * 
 * 
 * 
 * 
 * 
*/


