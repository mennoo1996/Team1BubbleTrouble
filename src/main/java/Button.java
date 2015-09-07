import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;


public class Button {
	float x;
	float y;
	float width;
	float height;
	Image image;
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param image
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
	 * get bounding box rectangle of the button
	 * @return
	 */
	public MyRectangle getRectangle() {
		return new MyRectangle(x,y,width,height);
	}
	
	/**
	 * Get the center x value of the button
	 * @return
	 */
	public float getCenterX() {
		return x + (0.5f * width);
	}
	
	/**
	 * Get the center y value of the button
	 */
	public float getCenterY() {
		return y + (0.5f * height);
	}
	
	/**
	 * Get the maximum x value of the button
	 * @return
	 */
	public float getMaxX() {
		return x + width;
	}
	
	/**
	 * Get the maximum y value of the button
	 * @return
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

	public Image getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}
	
	
}
