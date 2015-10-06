package levels;

import gui.MainGame;

import java.util.ArrayList;

import logic.BouncingCircle;
import logic.Gate;

/**
 * Represents the first level.
 * @author Stefan
 *
 */
public class Level1 extends Level {

	/**
	 * Creates a level.
	 * @param maingame the game in which the level is created
	 */
	public Level1(MainGame maingame) {
		super(maingame);
	}

	private static final int DEFAULT_BALL_X = 200;
	private static final int DEFAULT_BALL_Y = 200;
	private static final float RADIUS_3 = 30;
	private static final int DEFAULT_YSPEED = -50;
	private static final int LEVEL_1_TIME = 40;
	
	@Override
	public void constructLevel() {
		//The circle list
		ArrayList<BouncingCircle> circles = new ArrayList<BouncingCircle>();
		BouncingCircle circle11 = new BouncingCircle(DEFAULT_BALL_X, DEFAULT_BALL_Y,
				RADIUS_3, this.getMaingame().getStartingSpeed(),
				DEFAULT_YSPEED, this.getMaingame().getGravity(), 0);
		circles.add(circle11);
		
		//The gate list
		ArrayList<Gate> gates = new ArrayList<Gate>();
		
		//Set the correct attributes
		this.setCircles(circles);
		this.setGates(gates);
		this.setTime(LEVEL_1_TIME);
	}
}
