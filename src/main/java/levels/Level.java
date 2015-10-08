package levels;
import gui.MainGame;

import java.util.ArrayList;

import logic.BouncingCircle;
import logic.Gate;

/**
 * This class represents a level, with a certain time to complete the level and a list 
 * of bouncing circles & gates that are present in this level.
 * @author Menno
 *
 */
public abstract class Level {
	
	
	private int time;
	private ArrayList<BouncingCircle> circles;
	private ArrayList<Gate> gates;
	private MainGame maingame;
	private boolean isMultiplayer;
	
	private static boolean testing = false;
	
	/**
	 * Construct a new level.
	 * @param maingame the game in which the level will appear
	 * @param isMultiplayer whether or not the level will be used in multiplayer
	 */
	public Level(MainGame maingame, boolean isMultiplayer) {
		this.time = 0;
		this.circles = new ArrayList<BouncingCircle>();
		this.gates = new ArrayList<Gate>();
		this.maingame = maingame;
		this.isMultiplayer = isMultiplayer;
	}
	
	/**
	 * Create the correct level.
	 */
	public abstract void constructLevel();

	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * @return the circles
	 */
	public ArrayList<BouncingCircle> getCircles() {
		return circles;
	}

	/**
	 * @param circles the circles to set
	 */
	public void setCircles(ArrayList<BouncingCircle> circles) {
		this.circles = circles;
	}

	/**
	 * Get the gates in this level.
	 * @return the gates
	 */
	public ArrayList<Gate> getGates() {
		return gates;
	}

	/**
	 * Set the gates of this level.
	 * @param gates the gates to set
	 */
	public void setGates(ArrayList<Gate> gates) {
		this.gates = gates;
	}

	/**
	 * Returns the game.
	 * @return the game to return
	 */
	public MainGame getMaingame() {
		return maingame;
	}

	/**
	 * Sets the game.
	 * @param maingame the game to set
	 */
	public void setMaingame(MainGame maingame) {
		this.maingame = maingame;
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
		Level.testing = testing;
	}

	/**
	 * @return whether or not the level will be used in multiplayer
	 */
	public boolean getMultiplayer() {
		return this.isMultiplayer;
	}
	
}
