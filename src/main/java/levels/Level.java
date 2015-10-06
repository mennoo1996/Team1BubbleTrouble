package levels;
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
	
	/**
	 * Construct a new level.
	 */
	public Level() {
		this.time = 0;
		this.circles = new ArrayList<BouncingCircle>();
		this.gates = new ArrayList<Gate>();
	}
	
	

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
}
