import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Player {
	//Method 1 code
	//int lifeCount;
	float x;
	float y;
	float width;
	float height;
	Image image;

	private boolean laser;
	private boolean shield;
	
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param image
	 */
	public Player(float x, float y, float width, float height, Image image) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
		this.laser = false;
		this.shield = false;
		//Method 1 code
		//this.lifeCount = lifeCount;
	}

	/**
	 * Return a bounding box rectangle of the player
	 * @return
	 */
	public Rectangle getRectangle() {
		return new Rectangle(x,y,width,height);
	}
	
	/**
	 * Get the center X coordinate
	 * @return
	 */
	public float getCenterX() {
		return x + (0.5f * width);
	}
	
	/**
	 * Get the center Y coordinate
	 * @return
	 */
	public float getCenterY() {
		return y + (0.5f * height);
	}
	
	/**
	 * Get the maximum x value
	 * @return
	 */
	public float getMaxX() {
		return x + width;
	}
	
	/**
	 * Get the maximum Y value
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

	public boolean hasShield() {
		return shield;
	}

	public void addShield() {
		shield = true;
		Executors.newScheduledThreadPool(1).schedule(() -> shield = false, 10, TimeUnit.SECONDS);
	}

	public boolean hasLaser() {
		return laser;
	}

	public void addLaser() {
		laser = true;
		Executors.newScheduledThreadPool(1).schedule(() -> laser = false, 10, TimeUnit.SECONDS);
	}
	
	//Method 1 code
//	public void decreaselifeCount() {
//		lifeCount = lifeCount -1;
//	}
//	
//	public int getLifeCount() {
//		return lifeCount;
//	}
}
