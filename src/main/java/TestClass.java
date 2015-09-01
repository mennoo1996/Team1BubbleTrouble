import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;



/**
 * Created by alexandergeenen on 01/09/15.
 */
public class TestClass extends BasicGame {
	
	private BouncingCircle circle1;
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
		
		
		circle1 = new BouncingCircle(200, 200, 20, 0.3f, 0, 0.001f);
		shapeFill = new MyShapeFill();
		
	}

	/**
	 * 
	 */
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
		circle1.update(container);
		
	}
	
	
	/**
	 * 
	 */
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		// TODO Auto-generated method stub
		graphics.drawString("Hello Bubble Trouble!", 100, 100);
		
		graphics.fill(circle1.getCircle(), shapeFill);
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