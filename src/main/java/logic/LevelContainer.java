package logic;
import gui.MainGame;

import java.util.ArrayList;

import levels.Level;
import levels.LevelFactory;
import levels.LevelFactoryMultiPlayer;
import levels.LevelFactorySinglePlayer;
import logic.Logger.PriorityLevels;

/**
 * This class is a container for all the levels in the game.
 * @author Menno
 *
 */
public class LevelContainer {
	
	private ArrayList<Level> levels;
	private MainGame mainGame;
	
	private static final int ELEVEN = 11;
	
	
	private static boolean testing = false;
	
	//Logger
	private Logger logger = Logger.getInstance();
	
	/**
	 * Initialize the container.
	 * @param mainGame the MainGame that uses this LevelContainer.
	 */
	public LevelContainer(MainGame mainGame) {
		this.mainGame = mainGame;
		this.levels = new ArrayList<Level>();

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
		LevelFactory levelFactory;
		if (this.mainGame.isLanMultiplayer()) {
			levelFactory = new LevelFactoryMultiPlayer();
		} else {
			levelFactory = new LevelFactorySinglePlayer();
		}

		for (int i = 1; i < ELEVEN; i++) {
			res.add(levelFactory.orderLevel(i, this.mainGame));
		}
		
		logger.log("Levels are initialized", PriorityLevels.HIGH, "LevelContainer");
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