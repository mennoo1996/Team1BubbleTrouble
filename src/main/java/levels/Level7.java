package levels;



import java.util.ArrayList;

import guimenu.MainGame;
import logic.BouncingCircle;
import logic.Gate;

/**
 * This class represents the seventh level.
 * @author Menno
 *
 */
public class Level7 extends Level {
	
	private static final int DEFAULT_BALL_X = 200;
	private static final int DEFAULT_BALL_Y = 200;
	private static final int DEFAULT_BIGBALL_Y = 300;
	private static final float RADIUS_3 = 30;
	private static final float RADIUS_5 = 65;
	private static final int DEFAULT_YSPEED = -50;
	private static final int LEVEL_7_BALL_2_X = 700;
	private static final int LEVEL_7_BALL_2_Y = 500;
	private static final int LEVEL_7_BALL_3_X = 1200;
	private static final int LEVEL_7_GATE_X = 900;
	private static final float LEVEL_1_GATE_WIDTH = 45;
	private static final int TESTING_CONTAINER_HEIGHT = 1000;
	private static final int LEVEL_7_TIME = 150;


	
	/**
	 * Construct a new Level7.
	 * @param mainGame the mainGame that uses this level
	 * @param isMultiplayer whether or not the level will be used in multiplayer
	 */
	public Level7(MainGame mainGame, boolean isMultiplayer) {
		super(mainGame, isMultiplayer);
	}
	
	@Override
	public void constructLevel() {
		ArrayList<BouncingCircle> circles = new ArrayList<BouncingCircle>();
		BouncingCircle ball = new BouncingCircle(DEFAULT_BALL_X, DEFAULT_BALL_Y, RADIUS_3,
				this.getMaingame().getStartingSpeed(), DEFAULT_YSPEED,
				this.getMaingame().getGravity(), 0);
		BouncingCircle ball2 = new BouncingCircle(LEVEL_7_BALL_2_X, LEVEL_7_BALL_2_Y, RADIUS_3,
				this.getMaingame().getStartingSpeed(), DEFAULT_YSPEED,
				this.getMaingame().getGravity(), 1);
		BouncingCircle ball3 = new BouncingCircle(LEVEL_7_BALL_3_X, DEFAULT_BIGBALL_Y, RADIUS_5,
				this.getMaingame().getStartingSpeed(), DEFAULT_YSPEED,
				this.getMaingame().getGravity(), 2);
		
		circles.add(ball); circles.add(ball2); circles.add(ball3); Gate gate2;
		ArrayList<Gate> gates = new ArrayList<Gate>();
		if (!this.getMultiplayer()) {
			if (!isTesting()) {
				gate2 = new Gate(LEVEL_7_GATE_X, 0, LEVEL_1_GATE_WIDTH,
						this.getMaingame().getContainer().getHeight());
			} else {
				gate2 = new Gate(LEVEL_7_GATE_X, 0, LEVEL_1_GATE_WIDTH, TESTING_CONTAINER_HEIGHT);
			}
			gate2.addToRequirements(ball);
			gate2.addToRequirements(ball2);
			gates.add(gate2);
		}
		this.setCircles(circles);
		this.setGates(gates);
		this.setTime(LEVEL_7_TIME);
	}

}
