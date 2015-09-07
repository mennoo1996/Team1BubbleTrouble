import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;


public class Gate extends Rectangle {

	/**
	 * variables
	 */
	private static final long serialVersionUID = 1L;
	private float x;
	private float y;
	private float width;
	private float height;
	private ArrayList<BouncingCircle> required = new ArrayList<BouncingCircle>();
	private boolean done;
	private boolean fading;
	private float heightPercentage;
	private float fadingSpeed;
	
	/**
	 * Constructs a gate object
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Gate(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		done = false;
		fading = false;
		heightPercentage = 1;
		fadingSpeed = 1000;
	}
	
	/**
	 * Updates the gate
	 * @param delta
	 */
	public void update(float delta) {
		if(fading) {
			if(heightPercentage <= 0) {
				done = true;
				fading = false;
			} else {
				heightPercentage -= (fadingSpeed/height) * delta;
			}
		}
	}
	
	/**
	 * @return the fading
	 */
	public boolean isFading() {
		return fading;
	}

	/**
	 * @param fading the fading to set
	 */
	public void setFading(boolean fading) {
		this.fading = fading;
	}

	/**
	 * @return the heightPercentage
	 */
	public float getHeightPercentage() {
		return heightPercentage;
	}

	/**
	 * @param heightPercentage the heightPercentage to set
	 */
	public void setHeightPercentage(float heightPercentage) {
		this.heightPercentage = heightPercentage;
	}


	/**
	 * 
	 * @return whether done is true or false
	 */
	public boolean isDone() {
		return done;
	}
	
	/**
	 * 
	 * @param done the done to set
	 */
	public void setDone(boolean done) {
		this.done = done;
	}

	/**
	 * 
	 * @return the rectangle identical to the gate's
	 */
	public Rectangle getRectangle() {
		return new Rectangle(x, y, width, height);
	}
	
	/**
	 * Add a BouncingCircle to the list that needs to be shot before the gate opens
	 * @param circle the circle to add
	 */
	public void addToRequirements(BouncingCircle circle) {
		required.add(circle);
	}
	
	/**
	 * Remove a BouncingCircle from the required list
	 * @param circle the circle to remove
	 */
	public void removeFromRequirements(BouncingCircle circle) {
		required.remove(circle);
	}

	/**
	 * Return the required list
	 * @return
	 */
	public ArrayList<BouncingCircle> getRequired() {
		return required;
	}

	/**
	 * 
	 * @param required the required to set
	 */
	public void setRequired(ArrayList<BouncingCircle> required) {
		this.required = required;
	}

	/**
	 * Add a list of circles to the required list of the gate
	 * @param splits the circles to add
	 */
	public void addToRequirements(ArrayList<BouncingCircle> splits) {
		for(BouncingCircle circle : splits) {
			required.add(circle);
		}
	}

	/**
	 * Return the fadingSpeed
	 * @return
	 */
	public float getFadingSpeed() {
		return fadingSpeed;
	}
	
	/**
	 * 
	 * @param fadingSpeed the fadingSpeed to set
	 */
	public void setFadingSpeed(float fadingSpeed) {
		this.fadingSpeed = fadingSpeed;
	}
}
