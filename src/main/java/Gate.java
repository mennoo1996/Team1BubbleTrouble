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
	private boolean done;
	private boolean fading;
	private float heightPercentage;
	private float fadingSpeed;
	
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

	public float getFadingSpeed() {
		return fadingSpeed;
	}

	public void setFadingSpeed(float fadingSpeed) {
		this.fadingSpeed = fadingSpeed;
	}
}
