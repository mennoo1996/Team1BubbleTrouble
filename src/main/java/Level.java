import java.util.ArrayList;
import java.util.HashMap;

public class Level {
	
	private int time;
	private ArrayList<BouncingCircle> circles;
	
//	public HashMap<Integer, ArrayList<BouncingCircle>> map;
//	public int levelCounter;
//	
//	public Level () {
//		map = new HashMap<Integer, ArrayList<BouncingCircle>>();
//		levelCounter = 0;
//	}
//	
//	public void add(ArrayList<BouncingCircle> circles) {
//		map.put(levelCounter, circles);
//		levelCounter++;
//	}
//	
//	public ArrayList<BouncingCircle> getCirclesForId(int levelId) {
//		return map.get(levelId);
//	}
	
	public Level(int time, ArrayList<BouncingCircle> circles) {
		this.time = time;
		this.circles = circles;
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
	
	
	
	

}
