import org.newdawn.slick.Image;


public class MyButton extends Button {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MyButton(float x, float y, float z, float a, Image image) {
		super(x, y, z, a, image);
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof MyButton) {
			MyButton that = (MyButton) other;
			if (this.getX() == that.getX() && this.getY() == that.getY() && this.getWidth() == that.getWidth() &&
					this.getHeight() == that.getHeight() && this.getImage() == that.getImage()) {
				return true;
			}
		}
		return false;
	}
}
