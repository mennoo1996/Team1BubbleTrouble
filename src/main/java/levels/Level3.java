package levels;

import java.util.ArrayList;

import gui.MainGame;
import logic.BouncingCircle;
import logic.Gate;

/**
 * This class represents the third level.
 * @author Menno
 *
 */
public class Level3 extends Level {
	
	private static final int DEFAULT_BALL_X = 200;
	private static final int DEFAULT_BALL_Y = 200;
	private static final float RADIUS_5 = 65;
	private static final int DEFAULT_YSPEED = -50;
	private static final int LEVEL_3_TIME = 100;

	/**
	 * Construct a Level3.
	 * @param mainGame the mainGame that uses this level
	 * @param isMultiplayer whether or not the level will be used in multiplayer
	 */
	public Level3(MainGame mainGame, boolean isMultiplayer) {
		super(mainGame, isMultiplayer);
	}
	
	@Override
	public void constructLevel() {
		// The circle list
		ArrayList<BouncingCircle> circles = new ArrayList<BouncingCircle>();
		circles.add(new BouncingCircle(DEFAULT_BALL_X, DEFAULT_BALL_Y, RADIUS_5,
				this.getMaingame().getStartingSpeed(), DEFAULT_YSPEED,
				this.getMaingame().getGravity(), 0));
		
		// The gate list
		ArrayList<Gate> gates = new ArrayList<Gate>();
		
		// Set the correct attributes
		this.setCircles(circles);
		this.setGates(gates);
		this.setTime(LEVEL_3_TIME);
	}

}
