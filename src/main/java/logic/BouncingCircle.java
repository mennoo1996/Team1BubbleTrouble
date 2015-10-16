package logic;
import guigame.GameState;
import guimenu.MainGame;

import java.util.ArrayList;

import logic.Logger.PriorityLevels;

import org.newdawn.slick.geom.Circle;

/**
 * Class that represents a bouncing circle (bubble).
 * @author Menno
 *
 */
public class BouncingCircle extends Circle implements Cloneable {
	
	
	private static final int MINIMUM_SPEED = -200;
	private static final int BONUS_SPEED_FACTOR = 4;
	private static final int BONUS_SPEED = 50;
	
	private static final int MINIMUM_RADIUS = 10;
	private static final int RADIUS_2 = 20;
	private static final int RADIUS_3 = 30;
	private static final int RADIUS_4 = 45;
	private static final int RADIUS_5 = 65;
	private static final int RADIUS_6 = 90;
	
	private static final int SPEED_FOR_MINIMUM_RADIUS = 360;
	private static final int SPEED_FOR_RADIUS_2 = 470;
	private static final int SPEED_FOR_RADIUS_3 = 530;
	private static final int SPEED_FOR_RADIUS_4 = 570;
	private static final int SPEED_FOR_RADIUS_5 = 610;
	private static final int SPEED_FOR_RADIUS_6 = 650;
	
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
	private int id;
	private Logger logger = Logger.getInstance();
	private float multiplier;

	/**
	 * 
	 * @param centerPointX the X coordinate of the center point of the circle
	 * @param centerPointY the Y coordinate of the center point of the circle
	 * @param radius the radius of the circle
	 * @param xSpeed the speed in horizontal direction
	 * @param ySpeed the speed in vertical direction
	 * @param gravity the gravity affecting this circle
	 * @param id the id of this circle
	 */
	public BouncingCircle(float centerPointX, float centerPointY, 
			float radius, float xSpeed, float ySpeed, float gravity, int id) {
		super(centerPointX, centerPointY, radius);
		
		this.xSpeed = xSpeed;
		this.initSpeed = Math.abs(xSpeed);
		this.ySpeed = ySpeed;
		this.gravity = gravity;
		this.done = false;
		this.hitCeiling = false;
		this.id = id;
		this.multiplier = 1;
		
	}
	

	
	/**
	 * Equals method for a BouncingCircle.
	 * @other the BouncingCircle to compare
	 * @return whether the two BouncingCircles are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof BouncingCircle) {
			BouncingCircle that = (BouncingCircle) other;
			if (this.getxSpeed() == that.getxSpeed() 
					&& this.getySpeed() == that.getySpeed()
					&& this.getGravity() == that.getGravity()
					&& this.isDone() == that.isDone()
					&& this.isHitCeiling() == that.isHitCeiling()
					&& this.getId() == that.getId()
					&& this.getMultiplier() == that.getMultiplier()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}

	/**
	 * Update the circle in the given container.
	 * @param gameState			- the gamestate the circle is in
	 * @param containerHeight		- the height of the ccontainer
	 * @param containerWidth		- the width of the container
	 * @param deltaFloat    - the time in ms since last frame
	 */
	public void update(GameState gameState, float containerHeight, float containerWidth,
			float deltaFloat) {
		// Calculations for Y coordinates
		this.setY(this.getY() + ySpeed * deltaFloat * multiplier);
		// When the ball hit the floor reverse it's speed
		if (this.getMaxY() > containerHeight - gameState.getFloor().getHeight()) {
			ySpeed = -getSpeedForRadius();
		} else if (multiplier != 0) {
			// Else increase the speed
			ySpeed += (gravity * deltaFloat) * multiplier;
		}
		// When ball hits ceiling
		if (this.getMinY() <= gameState.getCeiling().getHeight()) {
			this.hitCeiling = true;
			logger.log("circles hitted ceiling", Logger.PriorityLevels.LOW, "circles");
		}
		handleXCalculations(gameState, containerWidth, deltaFloat);
	}
	/**
	 * Handle the X coordinate calculations of the circle. 
	 * @param gameState the GameState that uses this circle.
	 * @param containerWidth the width of the used GameContainer
	 * @param deltaFloat the time in seconds since the last frame
	 */
	private void handleXCalculations(GameState gameState, float containerWidth, float deltaFloat) {
		// Calculations for X coordinates
		this.setX(this.getX() + xSpeed * deltaFloat * multiplier);
		// If the ball hit a wall reverse it's speed
		if (this.getX() < gameState.getLeftWall().getWidth()) {
			xSpeed = initSpeed;
		} else if (this.getMaxX() > containerWidth - gameState.getRightWall().getWidth()) {
			xSpeed = -initSpeed;
		} else {
			for (Gate gate : gameState.getGateHelper().getGateList()) {
				if (gate.getRectangle().intersects(this.getCircle())) {
					if (gate.getUnlockCircles().contains(this)) {
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
	 * @param mainGame the maingame this bouncing circle is in.
	 * @param gameState the gamestate his bouncing circle is in.
	 * @return an arraylist with the splitted circles
	 */
	public ArrayList<BouncingCircle> getSplittedCircles(MainGame mainGame, GameState gameState) {
		logger.log("Circle with radius " + radius + " shot, two circles with radius " 
				+ getNewRadius() + " entered the game", PriorityLevels.MEDIUM,
				"BouncingCircles");
		if (radius == MINIMUM_RADIUS) {
			return null;
		}
		ArrayList<BouncingCircle> res = new ArrayList<BouncingCircle>();
		float newYSpeed = ySpeed;
		if (newYSpeed > MINIMUM_SPEED) {
			newYSpeed = MINIMUM_SPEED;
		}
		if (newYSpeed < BONUS_SPEED_FACTOR * radius) {
			logger.log("New balls aquired bonus speed",
					PriorityLevels.VERYLOW, "BouncingCircles");
			newYSpeed -= BONUS_SPEED;
		}
		BouncingCircle circle1 = new BouncingCircle(getCenterX(), getCenterY(), getNewRadius(), 
				xSpeed,	newYSpeed, mainGame.getGravity(), 
				gameState.getCirclesHelper().getCircleList().getNewID());
		BouncingCircle circle2 = new BouncingCircle(getCenterX(), getCenterY(), getNewRadius(), 
				-xSpeed, newYSpeed, mainGame.getGravity(), 
				gameState.getCirclesHelper().getCircleList().getNewID());
		if (multiplier != 0) {
			circle1.setMultiplier(multiplier);
			circle2.setMultiplier(multiplier);
		}
		res.add(circle1); res.add(circle2);	
		return res;
	}
	
	/**
	 * Get the speed that goes with a certain radius.
	 * @return the speed for the current radius
	 */
	public float getSpeedForRadius() {
		int switchRadius = (int) radius;
		switch (switchRadius) {
		case MINIMUM_RADIUS:
			return SPEED_FOR_MINIMUM_RADIUS;
		case RADIUS_2:
			return SPEED_FOR_RADIUS_2;
		case RADIUS_3:
			return SPEED_FOR_RADIUS_3;
		case RADIUS_4:
			return SPEED_FOR_RADIUS_4;
		case RADIUS_5:
			return SPEED_FOR_RADIUS_5;
		case RADIUS_6:
			return SPEED_FOR_RADIUS_6;
			default: return 0;
		}
		
	}
	
	/**
	 * Get new radius (of splitted circles).
	 * @return the new radius
	 */
	public float getNewRadius() {
		int switchRadius = (int) radius;
		switch (switchRadius) {
		case MINIMUM_RADIUS:
			return MINIMUM_RADIUS;
		case RADIUS_2:
			return MINIMUM_RADIUS;
		case RADIUS_3:
			return RADIUS_2;
		case RADIUS_4:
			return RADIUS_3;
		case RADIUS_5:
			return RADIUS_4;
		case RADIUS_6:
			return RADIUS_5;
			default: return 0;
		}
	}
	
	/**
	 * Returns the score of this bouncing circle.
	 * @return the points associated with the radius of this circle
	 */
	public int getScore() {
		int switchRadius = (int) radius;
		switch (switchRadius) {
		case MINIMUM_RADIUS:
			return POINTS_FOR_MINIMUM_RADIUS;
		case RADIUS_2:
			return POINTS_FOR_RADIUS_2;
		case RADIUS_3:
			return POINTS_FOR_RADIUS_3;
		case RADIUS_4:
			return POINTS_FOR_RADIUS_4;
		case RADIUS_5:
			return POINTS_FOR_RADIUS_5;
		case RADIUS_6:
			return POINTS_FOR_RADIUS_6;
			default: return 0;
		}
		
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
	
	/**
	 * Return a String-representation of this BouncingCircle.
	 *	@return a String-representation of this BouncingCircle.
	 */
	@Override
	public String toString() {
		String res = "CIRCLE " + this.getCenterX() + " " + this.getCenterY() 
				+ " " + this.getRadius() + " " + this.xSpeed + " " + this.ySpeed 
				+ " " + this.gravity + " " + this.multiplier + " " + id;
		return res;
	}
	
	/**
	 * Method that returns a string representations of a circleList.
	 * @param circleList	the circlelist to represent
	 * @return				a string containing the representations
	 */
	public static String circleListToString(ArrayList<BouncingCircle> circleList) {
		StringBuffer res = new StringBuffer().append("UPDATE CIRCLELIST START");
		
		for (BouncingCircle bCircle : circleList) {
			res.append('\n');
			res.append(bCircle.toString());
		}
		res.append("\nUPDATE CIRCLELIST END");
		
		return res.toString();
	}
	
	/**
	 * Method that returns a string representations of a circleList.
	 * @param circleList	the circlelist to represent\
	 * @param gateNumber	the gatenumber
	 * @return				a string containing the representations
	 */
	public static String requiredListToString(ArrayList<BouncingCircle> circleList, 
			int gateNumber) {
		StringBuffer res = new StringBuffer().append("UPDATE REQUIREDLIST START");
		res.append(gateNumber);
		
		for (BouncingCircle bCircle : circleList) {
			res.append('\n');
			res.append(bCircle.toString());
		}
		res.append("\nUPDATE CIRCLELIST END");

		return res.toString();
	}
	
	/**
	 * Clone the bouncingCircle.
	 */
	@Override
	public BouncingCircle clone() throws CloneNotSupportedException {
		BouncingCircle res = new BouncingCircle(this.getCenterX(), this.getCenterY(), 
				this.getRadius(), xSpeed, ySpeed, gravity, id);
		res.setMultiplier(this.multiplier);
		
		return res;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the multiplier
	 */
	public float getMultiplier() {
		return multiplier;
	}

	/**
	 * @param multiplier the multiplier to set
	 */
	public void setMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}
	
	
	
}
