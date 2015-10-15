package levels;

import java.util.ArrayList;

import guimenu.MainGame;
import logic.BouncingCircle;
import logic.Gate;

/**
 * Represents the fourth level.
 * @author Menno
 *
 */
public class Level4 extends Level {
	

	private static final int DEFAULT_BALL_X = 200;
	private static final int DEFAULT_BALL_Y = 200;
	private static final int DEFAULT_BIGBALL_Y = 300;
	private static final float RADIUS_5 = 65;
	private static final int DEFAULT_YSPEED = -50;
	private static final int LEVEL_4_BALL_2_X = 1200;
	private static final int TESTING_CONTAINER_WIDTH_HALF = 800;
	private static final int TESTING_CONTAINER_HEIGHT = 1000;
	private static final float LEVEL_1_GATE_X_DEVIATION = 50f;
	private static final float LEVEL_1_GATE_WIDTH = 45;
	private static final float RADIUS_4 = 45;
	private static final int LEVEL_4_TIME = 125;


	/**
	 * Construct a new Level4.
	 * @param mainGame the mainGame that uses this level
	 * @param isMultiplayer whether or not the level will be used in multiplayer
	 */
	public Level4(MainGame mainGame, boolean isMultiplayer) {
		super(mainGame, isMultiplayer);
	}
	
	@Override
	public void constructLevel() {
		//The circle list
		ArrayList<BouncingCircle> circles = new ArrayList<BouncingCircle>();
		BouncingCircle ball = new BouncingCircle(DEFAULT_BALL_X, DEFAULT_BALL_Y, RADIUS_4,
				this.getMaingame().getStartingSpeed(), DEFAULT_YSPEED,
				this.getMaingame().getGravity(), 0);
		 circles.add(ball);
		circles.add(new BouncingCircle(LEVEL_4_BALL_2_X, DEFAULT_BIGBALL_Y, RADIUS_5,
				-this.getMaingame().getStartingSpeed(), DEFAULT_YSPEED,
				this.getMaingame().getGravity(), 1));
		// The gate list
		ArrayList<Gate> gates = new ArrayList<Gate>();
		if (!this.getMultiplayer()) {
			Gate gate;
			if (!isTesting()) {
				gate = new Gate((float) this.getMaingame().getContainer().getWidth() / 2.0f 
						+ LEVEL_1_GATE_X_DEVIATION, 0,
						LEVEL_1_GATE_WIDTH, this.getMaingame().getContainer().getHeight());
			} else {
				gate = new Gate(TESTING_CONTAINER_WIDTH_HALF + LEVEL_1_GATE_X_DEVIATION, 0,
						LEVEL_1_GATE_WIDTH, TESTING_CONTAINER_HEIGHT);
			}
			gate.addToRequirements(ball);
			gates.add(gate);
			// Set the correct attributes
			this.setGates(gates);	this.setTime(LEVEL_4_TIME);
		}
		this.setCircles(circles);

	}
}
