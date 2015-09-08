import org.newdawn.slick.geom.Rectangle;

public class MyRectangle extends Rectangle {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyRectangle(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof MyRectangle) {
			MyRectangle that = (MyRectangle) other;
			if (this.getX() == that.getX() && this.getY() == that.getY() && this.getWidth() == that.getWidth() && this.getHeight() == that.getHeight()) {
				return true;
			}
		}
		return false;
	}
}
