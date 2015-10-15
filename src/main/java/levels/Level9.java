package levels;

import java.util.ArrayList;

import guimenu.MainGame;
import logic.BouncingCircle;
import logic.Gate;

/**
 * This class represents the ninth level.
 * @author Menno
 *
 */
public class Level9 extends Level {
	
	private static final int DEFAULT_BALL_X = 200;
	private static final int DEFAULT_BIGBALL_Y = 300;
	private static final int DEFAULT_YSPEED = -50;
	private static final float RADIUS_6 = 90;
	private static final int LEVEL_9_TIME = 180;
	private static final int LEVEL_9_BALL_2_X = 900;

	/**
	 * Construct a new Level9.
	 * @param mainGame the mainGame that uses this level
	 * @param isMultiplayer whether or not the level will be used in multiplayer
	 */
	public Level9(MainGame mainGame, boolean isMultiplayer) {
		super(mainGame, isMultiplayer);
	}
	
	@Override
	public void constructLevel() {
		// The circle list
		ArrayList<BouncingCircle> circles = new ArrayList<BouncingCircle>();
		circles.add(new BouncingCircle(DEFAULT_BALL_X, DEFAULT_BIGBALL_Y, RADIUS_6,
				this.getMaingame().getStartingSpeed(), DEFAULT_YSPEED,
				this.getMaingame().getGravity(), 0));
		
		circles.add(new BouncingCircle(LEVEL_9_BALL_2_X, DEFAULT_BIGBALL_Y, RADIUS_6,
				-this.getMaingame().getStartingSpeed(), DEFAULT_YSPEED,
				this.getMaingame().getGravity(), 1));
	
		// The gate list
		ArrayList<Gate> gates = new ArrayList<Gate>();
		
		// Set the correct attributes
		this.setCircles(circles);
		this.setGates(gates);
		this.setTime(LEVEL_9_TIME);

	}

}
