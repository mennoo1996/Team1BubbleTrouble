import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Circle;


public class BouncingCircle extends Circle {

	/**
	 * variables
	 */
	private static final long serialVersionUID = 1L;
	private float xSpeed;
	private float ySpeed;
	private float gravity;
	private boolean done;
	
	
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
		this.done = false;
	}

	/**
	 * Update the circle in the given container
	 * @param gs			- the gamestate the circle is in
	 * @param container		- the container the circle is in
	 */
	public void update(GameState gs, GameContainer container) {
		// Calculations for Y coordinates
		this.setY(this.getY() + ySpeed);
		// When the ball hit the floor reverse it's speed
		if(this.getMaxY() > container.getHeight() - gs.floor.getHeight() ) {
			ySpeed *= -1;
		} else {
			// Else increase the speed
			ySpeed += gravity;
		}
		
		// Calculations for X coordinates
		this.setX(this.getX() + xSpeed);
		// If the ball hit a wall reverse it's speed
		if(this.getMinX() < gs.leftWall.getWidth() || this.getMaxX() > container.getWidth() - gs.rightWall.getWidth()) {
			xSpeed *= -1;
		}
		
	}
	
	/**
	 * Get the maximum x value of the circle
	 */
	@Override
	public float getMaxX() {
		return this.getX() + 2 * this.getRadius();
	}
	
	/**
	 * Get the maximum y value of the circle
	 */
	@Override
	public float getMaxY() {
		return this.getY() + 2 * this.getRadius();
	}
	
	/**
	 * Get the minimum x value of the circle
	 */
	@Override
	public float getMinX() {
		return this.getX();
	}
	
	/**
	 * Get the minimum y value of the circle
	 */
	@Override
	public float getMinY() {
		return this.getY();
	}
	
	
	
	
	/**
	 * @return the done
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * @param done the done to set
	 */
	public void setDone(boolean done) {
		this.done = done;
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
