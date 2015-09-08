import org.newdawn.slick.Image;

/**
 * Extension of Button class for equals method.
 * @author Menno
 *
 */
public class MyButton extends Button {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Construct a MyButton.
	 * @param x the x coordinate of the button
	 * @param y the y coordinate of the button
	 * @param z the width of the button
	 * @param a the height of the button
	 * @param image the image on the button.
	 */
	public MyButton(float x, float y, float z, float a, Image image) {
		super(x, y, z, a, image);
	}
	
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof MyButton) {
			MyButton that = (MyButton) other;
			if (this.getX() == that.getX() 
					&& this.getY() == that.getY()
					&& this.getWidth() == that.getWidth() 
					&& this.getHeight() == that.getHeight() 
					&& this.getImage() == that.getImage()) {
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
