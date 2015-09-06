import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;


public class Gate extends Rectangle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float x;
	private float y;
	private float width;
	private float height;
	private ArrayList<BouncingCircle> required = new ArrayList<BouncingCircle>();
	private boolean done = false;
	
	public Gate(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Rectangle getRectangle() {
		return new Rectangle(x, y, width, height);
	}
	
	public void addToRequirements(BouncingCircle circle) {
		required.add(circle);
	}
	public void removeFromRequirements(BouncingCircle circle) {
		required.remove(circle);
	}

	public ArrayList<BouncingCircle> getRequired() {
		return required;
	}

	public void setRequired(ArrayList<BouncingCircle> required) {
		this.required = required;
	}

	public void addToRequirements(ArrayList<BouncingCircle> splits) {
		for(BouncingCircle circle : splits) {
			required.add(circle);
		}
	}
}
