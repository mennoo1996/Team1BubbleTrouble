package logic;

import java.util.ArrayList;

/**
 * Class containing a list of bouncingcircles.
 * @author Bart
 *
 */
public class CircleList {
	
	private ArrayList<BouncingCircle> circles;
	private int highestID;
	
	
	
	/**
	 * @param circles the circles of this circlelist.
	 */
	public CircleList(ArrayList<BouncingCircle> circles) {
		this.circles = circles;
		highestID = 0;
		
	}
	
	/**
	 * Method to add a new circle to the list.
	 * @param circle	the circle to add
	 */
	public void add(BouncingCircle circle) {
		if (circle.getId() > highestID) {
			highestID = circle.getId();
		}
		
		circles.add(circle);
	}
	
	/**
	 * method that returns the circle with the given id, if exists and null otherwise.
	 * @param id	the id to match
	 * @return		the matched circle
	 */
	public BouncingCircle getCircleForID(int id) {
		for (BouncingCircle circle : circles) {
			if (circle.getId() == id) {
				return circle;
			}
		}
		
		return null;
	}
	
	/**
	 * Return index of a circle with the given id.
	 * @param id	the id to match
	 * @return		the index
	 */
	public int getIndexForCircleWithID(int id) {
		BouncingCircle circle = getCircleForID(id);
		
		return circles.indexOf(circle);
	}
	
	/**
	 * Method that returns a new unused id.
	 * @return	the new id
	 */
	public int getNewID() {
		highestID++;
		return highestID;
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
