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
	 * MyButton constructor.
	 * @param x coordinate
	 * @param y coordinate
	 * @param z coordinate
	 * @param a coordinate
	 * @param image normal image
	 * @param imageA additive image
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
