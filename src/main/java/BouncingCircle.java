import java.util.ArrayList;

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
	private boolean hitCeiling;
	
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
		this.hitCeiling = false;
		
	}

	/**
	 * Update the circle in the given container
	 * @param gs			- the gamestate the circle is in
	 * @param container		- the container the circle is in
	 */
	public void update(GameState gs, GameContainer container, float deltaFloat) {
		
		// Calculations for Y coordinates
		this.setY(this.getY() + ySpeed*deltaFloat);
		// When the ball hit the floor reverse it's speed
		if(this.getMaxY() > container.getHeight() - gs.floor.getHeight() ) {
			ySpeed = -getSpeedForRadius();
		} else {
			// Else increase the speed
			ySpeed += gravity*deltaFloat;
		}
		
		// When ball hits ceiling
		System.out.println(this.getMinX());
		if(this.getMinY() <= gs.ceiling.getHeight()) {
			System.out.println("hitceiling");
			this.hitCeiling = true;
		}
		
		// Calculations for X coordinates
		this.setX(this.getX() + xSpeed*deltaFloat);
		// If the ball hit a wall reverse it's speed
		if(this.getX() < gs.leftWall.getWidth()) {
			xSpeed = -xSpeed;
		} else if(this.getMaxX() > container.getWidth() - gs.rightWall.getWidth()) {
			xSpeed = -xSpeed;
		} else {
			for(Gate gate : gs.getGateList()) {
				if(gate.getRectangle().intersects(this.getCircle())) {
					xSpeed = -xSpeed;
				}
			}
		}
		
		//
	}
	
	/**
	 * Return splitted balls
	 * @param mg
	 * @return
	 */
	public ArrayList<BouncingCircle> getSplittedCircles(MainGame mg) {
		if(radius == 10) {
			return null;
		}
		
		ArrayList<BouncingCircle> res = new ArrayList<BouncingCircle>();
		
		float newYSpeed = ySpeed;
		// minimum speed = -2
		if(newYSpeed > -200) {
			newYSpeed = -200;
		}
		
		// bonus speed when hit at the right moment
		if(newYSpeed < -4*radius) {
			newYSpeed -= 50;
		}
		
		// add new balls to the active list
		res.add(new BouncingCircle(getCenterX(), getCenterY(), getNewRadius(), xSpeed, newYSpeed, mg.gravity));
		res.add(new BouncingCircle(getCenterX(), getCenterY(), getNewRadius(), -xSpeed, newYSpeed, mg.gravity));
		
		return res;
	}
	
	private float getSpeedForRadius() {
		if(radius == 10f) {
			return 360f;
		} else if(radius == 20f) {
			return 470f;
		} else if(radius == 30f) {
			return 530f;
		} else if(radius == 45f) {
			return 570f;
		} else if(radius == 65f) {
			return 610f;
		} else if(radius == 90f) {
			return 650f;
		} 
		
		return 0f;
	}
	
	private float getNewRadius() {
		if(radius == 10f) {
			return 10f;
		} else if(radius == 20f) {
			return 10f;
		} else if(radius == 30f) {
			return 20f;
		} else if(radius == 45f) {
			return 30f;
		} else if(radius == 65f) {
			return 45f;
		} else if(radius == 90f) {
			return 65f;
		} 
		
		return 0f;
	}
	
	public int getScore() {
		if(radius == 10f) {
			return 100;
		} else if(radius == 20f) {
			return 50;
		} else if(radius == 30f) {
			return 25;
		} else if(radius == 45f) {
			return 10;
		} else if(radius == 65f) {
			return 5;
		} else if(radius == 90f) {
			return 2;
		} else if(radius == 140f) {
			return 1;
		}
		
		return 0;
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

	/**
	 * @return the hitCeiling
	 */
	public boolean isHitCeiling() {
		return hitCeiling;
	}

	/**
	 * @param hitCeiling the hitCeiling to set
	 */
	public void setHitCeiling(boolean hitCeiling) {
		this.hitCeiling = hitCeiling;
	}
	
	
	
	
}
