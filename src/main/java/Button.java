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
	private Image image;
	private Image imageMouseover;
	
	private static final float HALF = 0.5f;
	
	/**
	 * @param x the x coordinate of this button
	 * @param y the y coordinate of this button
	 * @param width the width of the button
	 * @param height the height of the button
	 * @param image the image of the button
	 */
	public Button(float x, float y, float width, float height, Image image) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
	}
	
	/**
	 * @param x the x coordinate of this button
	 * @param y the y coordinate of this button
	 * @param width the width of the button
	 * @param height the height of the button
	 * @param image the image of the button
	 * @param imageMouseover the image of the button when your mouse hovers over the button
	 */
	public Button(float x, float y, float width, float height, Image image, Image imageMouseover) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
		this.imageMouseover = imageMouseover;
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
	 * @return image for this button
	 */
	public Image getImage() {
		return image;
	}
	
	/**
	 * @return image for this button
	 */
	public Image getImageMouseOver() {
		return imageMouseover;
	}
	
	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}
	
	/**
	 * @param image the image to set
	 */
	public void setImageMouseOver(Image image) {
		this.imageMouseover = image;
	}
	
}
