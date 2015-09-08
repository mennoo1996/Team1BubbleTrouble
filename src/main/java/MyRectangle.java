import org.newdawn.slick.geom.Rectangle;

/**
 * Extension of the library class Rectangle for equals method.
 * @author Menno
 *
 */
public class MyRectangle extends Rectangle {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct a MyRectangle.
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param width the width of the rectangle
	 * @param height the height of the rectangle
	 */
	public MyRectangle(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof MyRectangle) {
			MyRectangle that = (MyRectangle) other;
			if (this.getX() == that.getX() 
					&& this.getY() == that.getY() 
					&& this.getWidth() == that.getWidth() 
					&& this.getHeight() == that.getHeight()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}
}
