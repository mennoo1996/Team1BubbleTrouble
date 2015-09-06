import java.util.ArrayList;

/**
 * This class represents a level, with a certain time to complete the level and a list of bouncing circles that are present in this level.
 * @author Menno
 *
 */
public class Level {
	
	
	private int time;
	private ArrayList<BouncingCircle> circles;
	private ArrayList<Gate> gates;
	
	/**
	 * Construct a new level
	 * @param time the time you get for completing this level
	 * @param circles the circles present in this level
	 */
	public Level(int time, ArrayList<BouncingCircle> circles, ArrayList<Gate> gates) {
		this.time = time;
		this.circles = circles;
		this.gates = gates;
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

	public ArrayList<Gate> getGates() {
		return gates;
	}

	public void setGates(ArrayList<Gate> gates) {
		this.gates = gates;
	}
}
