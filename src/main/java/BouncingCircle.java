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
		
		System.out.println(container.getHeight());
		
		// Calculations for Y coördinates
		this.setY(this.getY() + ySpeed);
		System.out.println(this.getMaxY());
		if(this.getMaxY() > container.getHeight() ) {
			ySpeed *= -1;
			System.out.println("reverse");
		} else {
			ySpeed += gravity;
			System.out.println(ySpeed);
		}
		
		// Calculations for X coördinates
		this.setX(x + xSpeed);
		if(minX < 0 || maxX > container.getWidth()) {
			xSpeed *= -1;
		}
		
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
