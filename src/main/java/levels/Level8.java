package levels;

import java.util.ArrayList;

import gui.MainGame;
import logic.BouncingCircle;
import logic.Gate;

/**
 * This class represents the eighth level.
 * @author Menno
 *
 */
public class Level8 extends Level {
	
	private static final int DEFAULT_BALL_X = 200;
	private static final int DEFAULT_BALL_Y = 200;
	private static final float RADIUS_2 = 20;
	private static final float RADIUS_3 = 30;
	private static final float RADIUS_4 = 45;
	private static final float RADIUS_5 = 65;
	private static final int DEFAULT_YSPEED = -50;
	private static final int LEVEL_8_TIME = 120;
	private static final int LEVEL_8_BALL_2_X = 300;
	private static final int LEVEL_8_BALL_3_X = 500;
	private static final int LEVEL_8_BALL_4_X = 700;
	private static final int THREE = 3;


	/**
	 * Construct a new Level8.
	 * @param mainGame the mainGame that uses this level
	 */
	public Level8(MainGame mainGame) {
		super(mainGame);
	}
	
	@Override
	public void constructLevel() {
		// The circle list
		ArrayList<BouncingCircle> circles = new ArrayList<BouncingCircle>();
		circles.add(new BouncingCircle(DEFAULT_BALL_X, DEFAULT_BALL_Y, RADIUS_2,
		this.getMaingame().getStartingSpeed(), DEFAULT_YSPEED, this.getMaingame().getGravity(), 0));

		circles.add(new BouncingCircle(LEVEL_8_BALL_2_X, DEFAULT_BALL_Y, RADIUS_3,
				this.getMaingame().getStartingSpeed(), DEFAULT_YSPEED,
				this.getMaingame().getGravity(), 1));

		circles.add(new BouncingCircle(LEVEL_8_BALL_3_X, DEFAULT_BALL_Y, RADIUS_4,
				this.getMaingame().getStartingSpeed(), DEFAULT_YSPEED,
				this.getMaingame().getGravity(), 2));

		circles.add(new BouncingCircle(LEVEL_8_BALL_4_X, DEFAULT_BALL_Y, RADIUS_5,
				this.getMaingame().getStartingSpeed(), DEFAULT_YSPEED,
				this.getMaingame().getGravity(), THREE));
		
		// The gate list
		ArrayList<Gate> gates = new ArrayList<Gate>();
		
		// Set up the correct attributes
		this.setCircles(circles);
		this.setGates(gates);
		this.setTime(LEVEL_8_TIME);


	}

}
