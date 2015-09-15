package logic;
import org.newdawn.slick.Image;

/**
 * Class that respresents a button - you can click on it and something might happen.
 * @author Menno
 *
 */
public class Button {
	private float x;
	private float y;
	private float width;
	private float height;
	private Image imageN;
	private Image imageA;
	private Image imageMouseoverN;
	private Image imageMouseoverA;
	
	private static final float HALF = 0.5f;
	
	/**
	 * Button constructor class.
	 * @param x coordinate
	 * @param y coordinate
	 * @param width of the button
	 * @param height of the button
	 * @param imageN normal image
	 * @param imageA additive image
	 */
	public Button(float x, float y, float width, float height, Image imageN, Image imageA) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.imageN = imageN;
		this.imageA = imageA;
	}
	
	/**
	 * Button constructor class.
	 * @param x coordinate
	 * @param y coordinate
	 * @param width of button
	 * @param height of button
	 * @param imageN normal image
	 * @param imageA additive image
	 * @param imageMouseoverN normal image
	 * @param imageMouseroverA additive image
	 */
	public Button(float x, float y, float width, float height, Image imageN, Image imageA,
			Image imageMouseoverN, Image imageMouseroverA) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.imageN = imageN;
		this.imageA = imageA;
		this.imageMouseoverN = imageMouseoverN;
		this.imageMouseoverA = imageMouseroverA;
	}

	/**
	 * get bounding box rectangle of the button.
	 * @return the Rectangle object of this button.
	 */
	public MyRectangle getRectangle() {
		return new MyRectangle(x, y, width, height);
	}
	
	/**
	 * Get the center x value of the button.
	 * @return the x value of the center of the button.
	 */
	public float getCenterX() {
		return x + (HALF * width);
	}
	
	/**
	 * Get the center y value of the button.
	 * @return the y value of the center of the button.
	 */
	public float getCenterY() {
		return y + (HALF * height);
	}
	
	/**
	 * Get the maximum x value of the button.
	 * @return the maximum x value of this button.
	 */
	public float getMaxX() {
		return x + width;
	}
	
	/**
	 * Get the maximum y value of the button.
	 * @return the maximum y value of this button.
	 */
	public float getMaxY() {
		return y + height;
	}
	
	
	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * @return image_norm for this button
	 */
	public Image getImageN() {
		return imageN;
	}
	
	/**
	 * @return image_add for this button
	 */
	public Image getImageA() {
		return imageA;
	}
	
	/**
	 * @return image_norm for this button
	 */
	public Image getImageMouseOverN() {
		return imageMouseoverN;
	}
	
	/**
	 * @return image_add for this button
	 */
	public Image getImageMouseOverA() {
		return imageMouseoverA;
	}
	
	/**
	 * @param imageN the image_norm to set
	 * @param imageA the image_add to set
	 */
	public void setImage(Image imageN, Image imageA) {
		this.imageN = imageN;
		this.imageA = imageA;
	}
	
	/**
	 * @param imageN the image_norm to set
	 * @param imageA the image_add to set
	 */
	public void setImageMouseOver(Image imageN, Image imageA) {
		this.imageMouseoverN = imageN;
		this.imageMouseoverA = imageA;
	}
	
}
