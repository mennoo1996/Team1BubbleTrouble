package logic;
import gui.GameState;
import gui.MainGame;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Circle;

/**
 * Class that represents a bouncing circle (bubble).
 * @author Menno
 *
 */
public class BouncingCircle extends Circle {
	
	
	private static final int MINIMUM_SPEED = -200;
	private static final int BONUS_SPEED_FACTOR = 4;
	private static final int BONUS_SPEED = 50;
	
	private static final float MINIMUM_RADIUS = 10;
	private static final float RADIUS_2 = 20;
	private static final float RADIUS_3 = 30;
	private static final float RADIUS_4 = 45;
	private static final float RADIUS_5 = 65;
	private static final float RADIUS_6 = 90;
	
	private static final float SPEED_FOR_MINIMUM_RADIUS = 360;
	private static final float SPEED_FOR_RADIUS_2 = 470;
	private static final float SPEED_FOR_RADIUS_3 = 530;
	private static final float SPEED_FOR_RADIUS_4 = 570;
	private static final float SPEED_FOR_RADIUS_5 = 610;
	private static final float SPEED_FOR_RADIUS_6 = 650;
	
	private static final int POINTS_FOR_MINIMUM_RADIUS = 100;
	private static final int POINTS_FOR_RADIUS_2 = 50;
	private static final int POINTS_FOR_RADIUS_3 = 25;
	private static final int POINTS_FOR_RADIUS_4 = 10;
	private static final int POINTS_FOR_RADIUS_5 = 5;
	private static final int POINTS_FOR_RADIUS_6 = 2;


	/**
	 * Variables.
	 */
	private static final long serialVersionUID = 1L;
	private float xSpeed;
	private float initSpeed;
	private float ySpeed;
	private float gravity;
	private boolean done;
	private boolean hitCeiling;

	/**
	 * 
	 * @param centerPointX the X coordinate of the center point of the circle
	 * @param centerPointY the Y coordinate of the center point of the circle
	 * @param radius the radius of the circle
	 * @param xSpeed the speed in horizontal direction
	 * @param ySpeed the speed in vertical direction
	 * @param gravity the gravity affecting this circle
	 */
	public BouncingCircle(float centerPointX, float centerPointY, 
			float radius, float xSpeed, float ySpeed, float gravity) {
		super(centerPointX, centerPointY, radius);
		
		this.xSpeed = xSpeed;
		this.initSpeed = Math.abs(xSpeed);
		this.ySpeed = ySpeed;
		this.gravity = gravity;
		this.done = false;
		this.hitCeiling = false;
		
	}

	/**
	 * Update the circle in the given container.
	 * @param gs			- the gamestate the circle is in
	 * @param container		- the container the circle is in
	 * @param deltaFloat    - the time in ms since last frame
	 */
	public void update(GameState gs, float containerHeight, float containerWidth, float deltaFloat) {
		// Calculations for Y coordinates
		this.setY(this.getY() + ySpeed * deltaFloat);
		// When the ball hit the floor reverse it's speed
		if (this.getMaxY() > containerHeight - gs.getFloor().getHeight()) {
			ySpeed = -getSpeedForRadius();
		} else {
			// Else increase the speed
			ySpeed += gravity * deltaFloat;
		}
		// When ball hits ceiling
		if (this.getMinY() <= gs.getCeiling().getHeight()) {
			this.hitCeiling = true;
		}
		handleXCalculations(gs, containerWidth, deltaFloat);
	}
	
	private void handleXCalculations(GameState gs, float containerWidth, float deltaFloat) {
		// Calculations for X coordinates
		this.setX(this.getX() + xSpeed * deltaFloat);
		// If the ball hit a wall reverse it's speed
		if (this.getX() < gs.getLeftWall().getWidth()) {
			xSpeed = initSpeed;
		} else if (this.getMaxX() > containerWidth - gs.getRightWall().getWidth()) {
			xSpeed = -initSpeed;
		} else {
			for (Gate gate : gs.getGateList()) {
				if (gate.getRectangle().intersects(this.getCircle())) {
					if (gate.getRequired().contains(this)) {
						xSpeed = -initSpeed;
					} else {
						xSpeed = initSpeed;
					}
				}
			}
		}
		
	}
	
	/**
	 * Return splitted balls.
	 * @param mg the maingame this bouncing circle is in.
	 * @return an arraylist with the splitted circles
	 */
	public ArrayList<BouncingCircle> getSplittedCircles(MainGame mg) {
		if (radius == MINIMUM_RADIUS) {
			return null;
		}
		
		ArrayList<BouncingCircle> res = new ArrayList<BouncingCircle>();
		
		float newYSpeed = ySpeed;
		// minimum speed = -2
		if (newYSpeed > MINIMUM_SPEED) {
			newYSpeed = MINIMUM_SPEED;
		}
		
		// bonus speed when hit at the right moment
		if (newYSpeed < BONUS_SPEED_FACTOR * radius) {
			newYSpeed -= BONUS_SPEED;
		}
		
		// add new balls to the active list
		res.add(new BouncingCircle(getCenterX(), getCenterY(), getNewRadius(), xSpeed,
				newYSpeed, mg.getGravity()));
		res.add(new BouncingCircle(getCenterX(), getCenterY(), getNewRadius(), -xSpeed,
				newYSpeed, mg.getGravity()));
		
		return res;
	}
	
	/**
	 * Get the speed that goes with a certain radius.
	 * @return the speed for the current radius
	 */
	public float getSpeedForRadius() {
		if (radius == MINIMUM_RADIUS) {
			return SPEED_FOR_MINIMUM_RADIUS;
		} else if (radius == RADIUS_2) {
			return SPEED_FOR_RADIUS_2;
		} else if (radius == RADIUS_3) {
			return SPEED_FOR_RADIUS_3;
		} else if (radius == RADIUS_4) {
			return SPEED_FOR_RADIUS_4;
		} else if (radius == RADIUS_5) {
			return SPEED_FOR_RADIUS_5;
		} else if (radius == RADIUS_6) {
			return SPEED_FOR_RADIUS_6;
		} 
		
		return 0f;
	}
	
	/**
	 * Get new radius (of splitted circles).
	 * @return the new radius
	 */
	public float getNewRadius() {
		if (radius == MINIMUM_RADIUS) {
			return MINIMUM_RADIUS;
		} else if (radius == RADIUS_2) {
			return MINIMUM_RADIUS;
		} else if (radius == RADIUS_3) {
			return RADIUS_2;
		} else if (radius == RADIUS_4) {
			return RADIUS_3;
		} else if (radius == RADIUS_5) {
			return RADIUS_4;
		} else if (radius == RADIUS_6) {
			return RADIUS_5;
		} 
		
		return 0f;
	}
	
	/**
	 * Returns the score of this bouncing circle.
	 * @return the points associated with the radius of this circle
	 */
	public int getScore() {
		if (radius == MINIMUM_RADIUS) {
			return POINTS_FOR_MINIMUM_RADIUS;
		} else if (radius == RADIUS_2) {
			return POINTS_FOR_RADIUS_2;
		} else if (radius == RADIUS_3) {
			return POINTS_FOR_RADIUS_3;
		} else if (radius == RADIUS_4) {
			return POINTS_FOR_RADIUS_4;
		} else if (radius == RADIUS_5) {
			return POINTS_FOR_RADIUS_5;
		} else if (radius == RADIUS_6) {
			return POINTS_FOR_RADIUS_6;
		} 
		
		return 0;
	}
	
	/**
	 * Get the maximum x value of the circle.
	 */
	@Override
	public float getMaxX() {
		return this.getX() + 2 * this.getRadius();
	}
	
	/**
	 * Get the maximum y value of the circle.
	 */
	@Override
	public float getMaxY() {
		return this.getY() + 2 * this.getRadius();
	}
	
	/**
	 * Get the minimum x value of the circle.
	 */
	@Override
	public float getMinX() {
		return this.getX();
	}
	
	/**
	 * Get the minimum y value of the circle.
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

	/**
	 * Get the Circle object of this Bouncing circle.
	 * @return the Circle object of this Bouncing circle.
	 */
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
