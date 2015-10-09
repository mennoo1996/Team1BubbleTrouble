package levels;

import java.util.ArrayList;

import gui.MainGame;
import logic.BouncingCircle;
import logic.Gate;

/**
 * This class represents the sixth level.
 * @author Menno
 *
 */
public class Level6 extends Level {
	
	private static final int DEFAULT_BIGBALL_Y = 300;
	private static final int DEFAULT_YSPEED = -50;
	private static final float RADIUS_6 = 90;
	private static final int LEVEL_6_TIME = 120;
	private static final int LEVEL_6_BALL_X = 900;

	/**
	 * Construct a new Level6.
	 * @param mainGame the MainGame that uses this level
	 * @param isMultiplayer whether or not the level will be used in multiplayer
	 */
	public Level6(MainGame mainGame, boolean isMultiplayer) {
		super(mainGame, isMultiplayer);
	}
	
	@Override
	public void constructLevel() {
		// The circle list
		ArrayList<BouncingCircle> circles = new ArrayList<BouncingCircle>();
		circles.add(new BouncingCircle(LEVEL_6_BALL_X, DEFAULT_BIGBALL_Y, RADIUS_6,
				-this.getMaingame().getStartingSpeed(), DEFAULT_YSPEED,
				this.getMaingame().getGravity(), 0));
		
		// The gate list
		ArrayList<Gate> gates = new ArrayList<Gate>();
		
		// Set the correct attributes
		this.setCircles(circles);
		this.setGates(gates);
		this.setTime(LEVEL_6_TIME);

	}

}
