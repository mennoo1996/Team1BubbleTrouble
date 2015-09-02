import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;


public class Laser {
	
	private float x;
	private float y;
	private float width;
	private float height;
	private float laserSpeed;
	boolean visible;
	
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param duration
	 */
	public Laser(float x, float y,  float laserSpeed, float laserWidth) {
		super();
		this.x = x - (0.5f * laserWidth);
		this.y = y;
		this.laserSpeed = laserSpeed;
		this.width = laserWidth;
		this.height = 0;
		this.visible = true;
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(x,y,width,height);
	}
	
	public void update(GameContainer container) {
		y -= laserSpeed;
		height += laserSpeed;
		
		if(y < 0) {
			this.visible = false;
		}
		
		
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
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}
	/**
	 * @param visible the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * @return the laserSpeed
	 */
	public float getLaserSpeed() {
		return laserSpeed;
	}

	/**
	 * @param laserSpeed the laserSpeed to set
	 */
	public void setLaserSpeed(float laserSpeed) {
		this.laserSpeed = laserSpeed;
	}
	
	
	

}
