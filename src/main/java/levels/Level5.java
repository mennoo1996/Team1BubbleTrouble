package levels;

import java.util.ArrayList;

import gui.MainGame;
import logic.BouncingCircle;
import logic.Gate;

/**
 * This class represents the fifth level.
 * @author Menno
 *
 */
public class Level5 extends Level {
	
	private static final int DEFAULT_BALL_X = 200;
	private static final int DEFAULT_BIGBALL_Y = 300;
	private static final float MINIMUM_RADIUS = 10;
	private static final int DEFAULT_YSPEED = -50;
	private static final int LEVEL_5_TIME = 60;
	private static final int LEVEL_5_AMOUNT_OF_BALLS = 10;
	private static final int LEVEL_5_BALL_FACTOR = 50;
	
	/**
	 * Construct a new Level5.
	 * @param mainGame the mainGame that uses this level
	 * @param isMultiplayer whether or not the level will be used in multiplayer
	 */
	public Level5(MainGame mainGame, boolean isMultiplayer) {
		super(mainGame, isMultiplayer);
	}
	
	@Override
	public void constructLevel() {
		// The circle list
		ArrayList<BouncingCircle> circles = new ArrayList<BouncingCircle>();
		for (int i = 0; i < LEVEL_5_AMOUNT_OF_BALLS; i++) {
			circles.add(new BouncingCircle(LEVEL_5_BALL_FACTOR * i + DEFAULT_BALL_X, 
					DEFAULT_BIGBALL_Y, MINIMUM_RADIUS, this.getMaingame().getStartingSpeed(),
					DEFAULT_YSPEED, this.getMaingame().getGravity(), i));
		}
		
		// The gate list
		ArrayList<Gate> gates = new ArrayList<Gate>();
		
		// Set the correct attributes
		this.setCircles(circles);
		this.setGates(gates);
		this.setTime(LEVEL_5_TIME);
		
		
	}

}
