package levels;

import java.util.ArrayList;

import guimenu.MainGame;
import logic.BouncingCircle;
import logic.Gate;

/**
 * Represents the second level.
 * @author Menno
 *
 */
public class Level2 extends Level {
	
	private static final int DEFAULT_BALL_X = 200;
	private static final int DEFAULT_BALL_Y = 200;
	private static final float RADIUS_4 = 45;
	private static final int DEFAULT_YSPEED = -50;
	private static final int LEVEL_2_TIME = 40;

	/**
	 * Creates a Level2.
	 * @param maingame the mainGame that uses this level
	 * @param isMultiplayer whether or not the level will be used in multiplayer
	 */
	public Level2(MainGame maingame, boolean isMultiplayer) {
		super(maingame, isMultiplayer);
	}
	
	@Override
	public void constructLevel() {
		//The circle list
		ArrayList<BouncingCircle> circles = new ArrayList<BouncingCircle>();
		circles.add(new BouncingCircle(DEFAULT_BALL_X, DEFAULT_BALL_Y, 
				RADIUS_4, this.getMaingame().getStartingSpeed(),
				DEFAULT_YSPEED, this.getMaingame().getGravity(), 0));
		
		//The gate list
		ArrayList<Gate> gates = new ArrayList<Gate>();
		
		// Set the correct attributes
		this.setCircles(circles);
		this.setGates(gates);
		this.setTime(LEVEL_2_TIME);
	}

}
