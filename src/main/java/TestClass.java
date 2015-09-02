import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;



/**
 * Created by alexandergeenen on 01/09/15.
 */
public class TestClass extends BasicGame {
	
	///// CONFIGURATION /////
	
	static int maxFPS = 60;
	static float gravity = 0.1f;
	static float startingSpeed = 0.5f;
	static float speedStep = 0.5f;
	static int playerSpeed = 5;
	
	////////////////////////
	
	
	private boolean gameOver = false;
	private static AppGameContainer app;
	private ArrayList<BouncingCircle> circleList;
	private MyShapeFill shapeFill;
	private Rectangle player;
	private Input input;
	
	
	public TestClass(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws SlickException {
		
        System.out.print("Hello World");
        
        app = new AppGameContainer(new TestClass("TestClass"));
        app.setTargetFrameRate(maxFPS);
        
        app.setDisplayMode(800, 600, false);

        app.start();
        
    }
	
	/**
	 * 
	 */
	@Override
	public void init(GameContainer container) throws SlickException {
		
		player = new Rectangle(380, 500, 40, 80);
		
		circleList = new ArrayList<BouncingCircle>();
		for(int i = 0; i < 5; i++) {
			float x = 100 + i * 150;
			float y = 100 + i * 50;
			float speed = startingSpeed + i*speedStep;
			
			circleList.add(new BouncingCircle(x, y, 20, speed, 0, gravity));
		}
		
		shapeFill = new MyShapeFill();
		
	}

	/**
	 * 
	 */
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		if(gameOver) {
			
		} else {

			input = container.getInput();

			if(input.isKeyDown(Input.KEY_LEFT) && player.getMinX() > 0) {
				player.setX(player.getX() - playerSpeed);
			}

			if(input.isKeyDown(Input.KEY_RIGHT) && player.getMaxX() < container.getWidth()) {
				player.setX(player.getX() + playerSpeed);
			}

			for(BouncingCircle circle : circleList) {
				circle.update(container);
				if(player.intersects(circle) || player.contains(circle)) {
					gameOver = true;
				}
			}
		}
	}
	
	
	/**
	 * 
	 */
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		// TODO Auto-generated method stub
		
		if(gameOver) {
			graphics.drawString("Game Over Sucker!", 300, 250);
		} else {
			graphics.drawString("Hello Bubble Trouble!", 100, 100);


			for(BouncingCircle circle : circleList) {
				graphics.fill(circle.getCircle(), shapeFill);
			}

			graphics.fill(player, shapeFill);
		}
	}
}





class MyShapeFill implements ShapeFill {

	public Color colorAt(Shape arg0, float arg1, float arg2) {
		// TODO Auto-generated method stub
		return Color.blue;
	}

	public Vector2f getOffsetAt(Shape arg0, float arg1, float arg2) {
		// TODO Auto-generated method stub
		Vector2f myVec = new Vector2f();
		return myVec;
	}
	
}
