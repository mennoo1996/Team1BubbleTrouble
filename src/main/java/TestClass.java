import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;



/**
 * Created by alexandergeenen on 01/09/15.
 */
public class TestClass extends BasicGame {
	
	private ArrayList<BouncingCircle> circleList;
	private MyShapeFill shapeFill;
	
	public TestClass(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws SlickException {
		
        System.out.print("Hello World");
        
        AppGameContainer app = new AppGameContainer(new TestClass("TestClass"));
        
        app.setDisplayMode(800, 600, false);

        app.start();
        
    }
	
	/**
	 * 
	 */
	@Override
	public void init(GameContainer container) throws SlickException {
		
		circleList = new ArrayList<BouncingCircle>();
		for(int i = 0; i < 5; i++) {
			float x = 100 + i * 150;
			float y = 100 + i * 50;
			float speed = 0.1f + i*0.1f;
			
			circleList.add(new BouncingCircle(x, y, 20, speed, 0, 0.001f));
		}
		
		shapeFill = new MyShapeFill();
		
	}

	/**
	 * 
	 */
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
		for(BouncingCircle circle : circleList) {
			circle.update(container);
		}
		
	}
	
	
	/**
	 * 
	 */
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		// TODO Auto-generated method stub
		graphics.drawString("Hello Bubble Trouble!", 100, 100);
		
		
		for(BouncingCircle circle : circleList) {
			graphics.fill(circle.getCircle(), shapeFill);
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