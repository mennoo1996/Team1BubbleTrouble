package logic;
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
	public MyButton(float x, float y, float z, float a, Image image, Image imageA) {
		super(x, y, z, a, image, imageA);
	}
	
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof MyButton) {
			MyButton that = (MyButton) other;
			if (this.getX() == that.getX() 
					&& this.getY() == that.getY()
					&& this.getWidth() == that.getWidth() 
					&& this.getHeight() == that.getHeight() 
					&& this.getImageN() == that.getImageN() 
					&& this.getImageA() == that.getImageA()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}

	/**
	 * Getter for serialVersionUID.
	 * @return	the serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
