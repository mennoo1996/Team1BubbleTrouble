import org.newdawn.slick.Color;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;


public class MyShapeFill implements ShapeFill {
	
	private Color color;
	
	public MyShapeFill(Color color) {
		this.color = color;
	}

	public Color colorAt(Shape arg0, float arg1, float arg2) {
		return color;
	}

	public Vector2f getOffsetAt(Shape arg0, float arg1, float arg2) {
		// TODO Auto-generated method stub
		return new Vector2f();
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	

}
