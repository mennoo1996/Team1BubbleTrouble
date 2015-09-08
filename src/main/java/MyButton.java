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
			if (this.x == that.x && this.y == that.y && this.width == that.width &&
					this.height == that.height && this.image == that.image) {
				return true;
			}
		}
		return false;
	}
}
