package logic;
import gui.MainGame;

import java.util.ArrayList;

import logic.Logger.PriorityLevels;

import org.newdawn.slick.GameContainer;

/**
 * This class is a container for all the levels in the game.
 * @author Menno
 *
 */
public class LevelContainer {
	
	private ArrayList<Level> levels;
	private MainGame mainGame;
	private GameContainer container;
	
	private static final int DEFAULT_BALL_X = 200;
	private static final int DEFAULT_BALL_Y = 200;
	private static final int DEFAULT_BIGBALL_Y = 300;
	private static final float MINIMUM_RADIUS = 10;
	private static final float RADIUS_2 = 20;
	private static final float RADIUS_3 = 30;
	private static final float RADIUS_4 = 45;
	private static final float RADIUS_5 = 65;
	private static final float RADIUS_6 = 90;
	private static final int DEFAULT_YSPEED = -50;
	private static final float LEVEL_1_GATE_X_DEVIATION = 50f;
	private static final float LEVEL_1_GATE_WIDTH = 45;
	private static final int LEVEL_1_TIME = 40;
	private static final int LEVEL_2_TIME = 40;
	private static final int LEVEL_3_TIME = 100;
	private static final int LEVEL_4_TIME = 125;
	private static final int LEVEL_5_TIME = 60;
	private static final int LEVEL_6_TIME = 120;
	private static final int LEVEL_7_TIME = 150;
	private static final int LEVEL_8_TIME = 120;
	private static final int LEVEL_9_TIME = 180;
	private static final int LEVEL_10_TIME = 40;
	private static final int LEVEL_4_BALL_2_X = 1200;
	private static final int LEVEL_5_AMOUNT_OF_BALLS = 10;
	private static final int LEVEL_5_BALL_FACTOR = 50;
	private static final int LEVEL_6_BALL_X = 900;
	private static final int LEVEL_7_BALL_2_X = 700;
	private static final int LEVEL_7_BALL_2_Y = 500;
	private static final int LEVEL_7_BALL_3_X = 1200;
	private static final int LEVEL_8_BALL_2_X = 300;
	private static final int LEVEL_8_BALL_3_X = 500;
	private static final int LEVEL_8_BALL_4_X = 700;
	private static final int LEVEL_9_BALL_2_X = 900;
	private static final int LEVEL_10_AMOUNT_OF_BALLS = 20;
	private static final int LEVEL_10_BALL_FACTOR = 10;
	private static final int LEVEL_10_XSPEED = 50;
	private static final int TESTING_CONTAINER_WIDTH_HALF = 800;
	private static final int TESTING_CONTAINER_HEIGHT = 1000;
	private static final int LEVEL_7_GATE_X = 900;
	
	private static boolean testing = false;
	
	//Logger
	private Logger logger;
	
	/**
	 * Initialize the container.
	 * @param mainGame the MainGame that uses this LevelContainer.
	 */
	public LevelContainer(MainGame mainGame) {
		this.mainGame = mainGame;
		this.container = mainGame.getContainer();
		this.levels = new ArrayList<Level>();
		this.logger = mainGame.getLogger();
	}
	
	/**
	 * Initialize the levels.
	 */
	public void initialize() {
		this.levels = initializeLevels();
	}
	
	/**
	 * Get the levels.
	 *  @return the levels
	 */
	public ArrayList<Level> getLevels() {
		return levels;
	}
 
	/**
	 * Initialize the levels.
	 * @return a list of all the levels in the game
	 */
	private ArrayList<Level> initializeLevels() {
		ArrayList<Level> res = new ArrayList<Level>();
		res.addAll(initializeLevels1());
		res.addAll(initializeLevels2());
		res.addAll(initializeLevels3());
		res.addAll(initializeLevels4());
		res.addAll(initializeLevels5());
		logger.log("Levels are initialized", PriorityLevels.HIGH, "LevelContainer");
		return res;
	}
	
	/**
	 * Initalize the first batch of levels.
	 * @return a list with the first batch of levels.
	 */
	private ArrayList<Level> initializeLevels1() {
		ArrayList<Level> res = new ArrayList<Level>();
				ArrayList<BouncingCircle> circles = new ArrayList<BouncingCircle>();
				BouncingCircle circle11 = new BouncingCircle(DEFAULT_BALL_X, DEFAULT_BALL_Y,
						RADIUS_3, mainGame.getStartingSpeed(),
						DEFAULT_YSPEED, mainGame.getGravity());
				circle11.setLogger(logger);
				circles.add(circle11);
				ArrayList<Gate> gates = new ArrayList<Gate>();
				/*if (!testing) {
					gate11 = new Gate((float) container.getWidth() / 2.0f 
							+ LEVEL_1_GATE_X_DEVIATION, 0, LEVEL_1_GATE_WIDTH, 
							container.getHeight());
				} else {
					gate11 = new Gate(TESTING_CONTAINER_WIDTH_HALF + LEVEL_1_GATE_X_DEVIATION,
							0, LEVEL_1_GATE_WIDTH, TESTING_CONTAINER_HEIGHT);
				} gate11.addToRequirements(circle11);
				*/
				Level level = new Level(LEVEL_1_TIME, circles, gates);
				res.add(level); 
				ArrayList<BouncingCircle> circles2 = new ArrayList<BouncingCircle>();
				ArrayList<Gate> gates2 = new ArrayList<Gate>();
				circles2.add(new BouncingCircle(DEFAULT_BALL_X, DEFAULT_BALL_Y, 
						RADIUS_4, mainGame.getStartingSpeed(),
						DEFAULT_YSPEED, mainGame.getGravity()));
				circles2.get(circles2.size() - 1).setLogger(logger);
				level = new Level(LEVEL_2_TIME, circles2, gates2);
				res.add(level);	
				return res;
	}
	
	/**
	 * Initalize the second batch of levels.
	 * @return a list with the second batch of levels.
	 */
	private ArrayList<Level> initializeLevels2() {
		Level level;
		ArrayList<Level> res = new ArrayList<Level>();
		ArrayList<BouncingCircle> circles3 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates3 = new ArrayList<Gate>();
		circles3.add(new BouncingCircle(DEFAULT_BALL_X, DEFAULT_BALL_Y, RADIUS_5,
				mainGame.getStartingSpeed(), DEFAULT_YSPEED, mainGame.getGravity()));
		level = new Level(LEVEL_3_TIME, circles3, gates3);
		circles3.get(circles3.size() - 1).setLogger(logger); res.add(level);
		ArrayList<BouncingCircle> circles4 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates4 = new ArrayList<Gate>();
		BouncingCircle ball = new BouncingCircle(DEFAULT_BALL_X, DEFAULT_BALL_Y, RADIUS_4,
				mainGame.getStartingSpeed(), DEFAULT_YSPEED, mainGame.getGravity());
		ball.setLogger(logger); circles4.add(ball);
		circles4.add(new BouncingCircle(LEVEL_4_BALL_2_X, DEFAULT_BIGBALL_Y, RADIUS_5,
				-mainGame.getStartingSpeed(), DEFAULT_YSPEED, mainGame.getGravity()));
		circles4.get(circles4.size() - 1).setLogger(logger);
		Gate gate;
		if (!testing) {
			gate = new Gate((float) container.getWidth() / 2.0f
					+ LEVEL_1_GATE_X_DEVIATION, 0, LEVEL_1_GATE_WIDTH,
					container.getHeight());
		} else {
			gate = new Gate(TESTING_CONTAINER_WIDTH_HALF + LEVEL_1_GATE_X_DEVIATION,
					0, LEVEL_1_GATE_WIDTH, TESTING_CONTAINER_HEIGHT); }
		gate.addToRequirements(ball); gates4.add(gate);
		level = new Level(LEVEL_4_TIME, circles4, gates4);
		res.add(level);
		return res;
	}
	
	/**
	 * Initalize the third batch of levels.
	 * @return a list with the third batch of levels.
	 */
	private ArrayList<Level> initializeLevels3() {
		
		Level level;
		ArrayList<Level> res = new ArrayList<Level>();
		
		ArrayList<BouncingCircle> circles5 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates5 = new ArrayList<Gate>();
		for (int i = 0; i < LEVEL_5_AMOUNT_OF_BALLS; i++) {
			circles5.add(new BouncingCircle(LEVEL_5_BALL_FACTOR * i + DEFAULT_BALL_X, 
					DEFAULT_BIGBALL_Y, MINIMUM_RADIUS, mainGame.getStartingSpeed(),
					DEFAULT_YSPEED, mainGame.getGravity()));
			circles5.get(circles5.size() - 1).setLogger(logger);
		}
		level = new Level(LEVEL_5_TIME, circles5, gates5);
		res.add(level);
		ArrayList<BouncingCircle> circles6 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates6 = new ArrayList<Gate>();
		circles6.add(new BouncingCircle(LEVEL_6_BALL_X, DEFAULT_BIGBALL_Y, RADIUS_6,
				-mainGame.getStartingSpeed(), DEFAULT_YSPEED, mainGame.getGravity()));
		circles6.get(circles6.size() - 1).setLogger(logger);
		level = new Level(LEVEL_6_TIME, circles6, gates6);
		res.add(level);
		
		
		
		return res;
	}
	
	/**
	 * Initalize the fourth batch of levels - part 1.
	 * @return a list with the fourth batch of levels.
	 */
	private ArrayList<Level> initializeLevels4() {
		Level level; ArrayList<Level> res = new ArrayList<Level>();
		ArrayList<BouncingCircle> circles7 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates7 = new ArrayList<Gate>();
		BouncingCircle ball = new BouncingCircle(DEFAULT_BALL_X, DEFAULT_BALL_Y, RADIUS_3,
				mainGame.getStartingSpeed(), DEFAULT_YSPEED, mainGame.getGravity());
		BouncingCircle ball2 = new BouncingCircle(LEVEL_7_BALL_2_X, LEVEL_7_BALL_2_Y, RADIUS_3,
				mainGame.getStartingSpeed(), DEFAULT_YSPEED, mainGame.getGravity());
		BouncingCircle ball3 = new BouncingCircle(LEVEL_7_BALL_3_X, DEFAULT_BIGBALL_Y, RADIUS_5,
				mainGame.getStartingSpeed(), DEFAULT_YSPEED, mainGame.getGravity());
		ball.setLogger(logger); ball2.setLogger(logger); ball3.setLogger(logger);
		circles7.add(ball); circles7.add(ball2); circles7.add(ball3); Gate gate2;
		if (!testing) {
			gate2 = new Gate(LEVEL_7_GATE_X, 0, LEVEL_1_GATE_WIDTH, container.getHeight());
		} else { 
			gate2 = new Gate(LEVEL_7_GATE_X, 0, LEVEL_1_GATE_WIDTH, TESTING_CONTAINER_HEIGHT); }
		gate2.addToRequirements(ball); gate2.addToRequirements(ball2); gates7.add(gate2);
		level = new Level(LEVEL_7_TIME, circles7, gates7); res.add(level);
		ArrayList<BouncingCircle> circles8 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates8 = new ArrayList<Gate>();
		initializeLevels4_2(circles8);
		circles8.get(circles8.size() - 1).setLogger(logger);
		level = new Level(LEVEL_8_TIME, circles8, gates8); res.add(level); return res;
	}
	
	/**
	 * Initalize the fourth batch of levels- part 2.
	 * @param circles8 a necessary list for this.
	 */
	private void initializeLevels4_2(ArrayList<BouncingCircle> circles8) {
		circles8.add(new BouncingCircle(DEFAULT_BALL_X, DEFAULT_BALL_Y, RADIUS_2,
				mainGame.getStartingSpeed(), DEFAULT_YSPEED, mainGame.getGravity()));
		circles8.get(circles8.size() - 1).setLogger(logger);
		circles8.add(new BouncingCircle(LEVEL_8_BALL_2_X, DEFAULT_BALL_Y, RADIUS_3,
				mainGame.getStartingSpeed(), DEFAULT_YSPEED, mainGame.getGravity()));
		circles8.get(circles8.size() - 1).setLogger(logger);
		circles8.add(new BouncingCircle(LEVEL_8_BALL_3_X, DEFAULT_BALL_Y, RADIUS_4,
				mainGame.getStartingSpeed(), DEFAULT_YSPEED, mainGame.getGravity()));
		circles8.get(circles8.size() - 1).setLogger(logger);
		circles8.add(new BouncingCircle(LEVEL_8_BALL_4_X, DEFAULT_BALL_Y, RADIUS_5,
				mainGame.getStartingSpeed(), DEFAULT_YSPEED, mainGame.getGravity()));
	}
	
	/**
	 * Initalize the fifth batch of levels.
	 * @return a list with the fifth batch of levels.
	 */
	private ArrayList<Level> initializeLevels5() {
		Level level;
		ArrayList<Level> res = new ArrayList<Level>();
		ArrayList<BouncingCircle> circles9 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates9 = new ArrayList<Gate>();
		circles9.add(new BouncingCircle(DEFAULT_BALL_X, DEFAULT_BIGBALL_Y, RADIUS_6,
				mainGame.getStartingSpeed(), DEFAULT_YSPEED, mainGame.getGravity()));
		circles9.get(circles9.size() - 1).setLogger(logger);
		circles9.add(new BouncingCircle(LEVEL_9_BALL_2_X, DEFAULT_BIGBALL_Y, RADIUS_6,
				-mainGame.getStartingSpeed(), DEFAULT_YSPEED, mainGame.getGravity()));
		circles9.get(circles9.size() - 1).setLogger(logger);
		level = new Level(LEVEL_9_TIME, circles9, gates9);
		res.add(level);
		
		ArrayList<BouncingCircle> circles10 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates10 = new ArrayList<Gate>();
		for (int i = 0; i < LEVEL_10_AMOUNT_OF_BALLS; i++) {
			circles10.add(new BouncingCircle(DEFAULT_BALL_X, 
					LEVEL_10_BALL_FACTOR * i + DEFAULT_BALL_Y,
					MINIMUM_RADIUS, LEVEL_10_XSPEED, DEFAULT_YSPEED, mainGame.getGravity()));
			circles10.get(circles10.size() - 1).setLogger(logger);
		}
		level = new Level(LEVEL_10_TIME, circles10, gates10);
		res.add(level);	
		return res;
		
	}
	
	/**
	 * Set the levels.
	 * @param levels the levels to set
	 */
	public void setLevels(ArrayList<Level> levels) {
		this.levels = levels;
	}

	/**
	 * Add a level to the container.
	 * @param level the level to add
	 */
	public void add(Level level) {
		levels.add(level);
	}
	
	/**
	 * Get a level from the container.
	 * @param id the ID from the level to get
	 * @return the level with ID specified
	 */
	public Level getLevel(int id) {
		return levels.get(id);
	}
	
	/**
	 * The size of the container.
	 * @return the size (amount of levels)
	 */
	public int size() {
		return levels.size();
	}

	/**
	 * @return the testing
	 */
	public static boolean isTesting() {
		return testing;
	}

	/**
	 * @param testing the testing to set
	 */
	public static void setTesting(boolean testing) {
		LevelContainer.testing = testing;
	}

	
	

}

