import org.newdawn.slick.geom.Rectangle;

/**
 * Class that represents a weapon.
 * @author Menno
 *
 */
public class Weapon {

	private float x;
	private float y;
	private float width;
	private float height;
	private float laserSpeed;
	private boolean visible;
	private static final float HALF = 0.5f;

	/**
	 * Constructs a new laser.
	 * @param x the x coordinate of the laser
	 * @param y the y coordinate of the laser
	 * @param laserWidth the width of the laser
	 * @param laserSpeed the speed of the laser
	 */
	public Weapon(float x, float y, float laserSpeed, float laserWidth) {
		super();
		this.x = x - (HALF * laserWidth);
		this.y = y;
		this.laserSpeed = laserSpeed;
		this.width = laserWidth;
		this.height = 0;
		this.visible = true;
	}
	
	/**
	 * Return the rectangle object of this laser.
	 * @return the rectangle corresponding to this laser
	 */
	public MyRectangle getRectangle() {
		return new MyRectangle(x, y, width, height);
	}
	
	/**
	 * Update the laser.
	 * @param gs The gamestate that called the update
	 * @param deltaFloat the time in ms since the last frame
	 */
	public void update(float deltaFloat, Rectangle ceiling, Rectangle floor) {
		y -= laserSpeed * deltaFloat;
		height += laserSpeed * deltaFloat;
		
		if (y < ceiling.getHeight()) {
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
