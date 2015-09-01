import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Circle;


public class BouncingCircle extends Circle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float xSpeed;
	private float ySpeed;
	private float gravity;
	
	
	
	/**
	 * @param centerPointX
	 * @param centerPointY
	 * @param radius
	 * @param xSpeed
	 * @param ySpeed
	 * @param gravity
	 */
	public BouncingCircle(float centerPointX, float centerPointY, float radius,
			float xSpeed, float ySpeed, float gravity) {
		super(centerPointX, centerPointY, radius);
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.gravity = gravity;
	}

	public void update(GameContainer container) {
		
		
		// Calculations for Y coordinates
		this.setY(this.getY() + ySpeed);
		if(this.getMaxY() > container.getHeight() ) {
			ySpeed *= -1;
		} else {
			ySpeed += gravity;
		}
		
		// Calculations for X coordinates
		this.setX(this.getX() + xSpeed);
		if(this.getMinX() < 0 || this.getMaxX() > container.getWidth()) {
			xSpeed *= -1;
		}
		
	}
	
	@Override
	public float getMaxX() {
		return this.getX() + 2 * this.getRadius();
	}
	
	@Override
	public float getMaxY() {
		return this.getY() + 2 * this.getRadius();
	}
	
	@Override
	public float getMinX() {
		return this.getX();
	}
	
	@Override
	public float getMinY() {
		return this.getY();
	}
	
	
	public Circle getCircle() {
		return new Circle(this.getCenterX(), this.getCenterY(), this.getRadius());
	}
	
	/**
	 * @return the xSpeed
	 */
	public float getxSpeed() {
		return xSpeed;
	}
	/**
	 * @param xSpeed the xSpeed to set
	 */
	public void setxSpeed(float xSpeed) {
		this.xSpeed = xSpeed;
	}
	/**
	 * @return the ySpeed
	 */
	public float getySpeed() {
		return ySpeed;
	}
	/**
	 * @param ySpeed the ySpeed to set
	 */
	public void setySpeed(float ySpeed) {
		this.ySpeed = ySpeed;
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
	 * @return the gravity
	 */
	public float getGravity() {
		return gravity;
	}
	/**
	 * @param gravity the gravity to set
	 */
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
	
	
}
