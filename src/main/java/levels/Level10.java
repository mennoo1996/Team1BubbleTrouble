package levels;

import java.util.ArrayList;

import gui.MainGame;
import logic.BouncingCircle;
import logic.Gate;

/**
 * This class represents the tenth level.
 * @author Menno
 *
 */
public class Level10 extends Level {
	
	private static final int DEFAULT_BALL_X = 200;
	private static final int DEFAULT_BALL_Y = 200;
	private static final float MINIMUM_RADIUS = 10;
	private static final int DEFAULT_YSPEED = -50;
	private static final int LEVEL_10_TIME = 40;
	private static final int LEVEL_10_AMOUNT_OF_BALLS = 20;
	private static final int LEVEL_10_BALL_FACTOR = 10;
	private static final int LEVEL_10_XSPEED = 50;
	
	/**
	 * Construct a new Level10.
	 * @param mainGame the MainGame that uses this level
	 * @param isMultiplayer whether or not the level will be used in multiplayer
	 */
	public Level10(MainGame mainGame, boolean isMultiplayer) {
		super(mainGame, isMultiplayer);
	}
	
	@Override
	public void constructLevel() {
		// The circle list
		ArrayList<BouncingCircle> circles = new ArrayList<BouncingCircle>();
		for (int i = 0; i < LEVEL_10_AMOUNT_OF_BALLS; i++) {
			circles.add(new BouncingCircle(DEFAULT_BALL_X, 
					LEVEL_10_BALL_FACTOR * i + DEFAULT_BALL_Y,
					MINIMUM_RADIUS, LEVEL_10_XSPEED, DEFAULT_YSPEED, 
					this.getMaingame().getGravity(), i));	
		}
		
		// The gate list
		ArrayList<Gate> gates = new ArrayList<Gate>();

		// Set the correct attributes
		this.setCircles(circles);
		this.setGates(gates);
		this.setTime(LEVEL_10_TIME);
	}

}
