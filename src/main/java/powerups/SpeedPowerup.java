package powerups;

import logic.CircleList;


/**
 * Powerup which influences the speed of the circles.
 * @author Bart
 *
 */
public abstract class SpeedPowerup {

	private float multiplier;
	private float timeRemaining;
	private static final int DURATION = 5;
	private boolean done;
	
	/**
	 * Constructor.
	 * @param multiplier - the multiplier of the powerup
	 */
	public SpeedPowerup(float multiplier) {
		this.multiplier = multiplier;
		this.timeRemaining = DURATION;
		done = false;
	}
	
	/**
	 * Set multiplier of all circles in circlelist.
	 * @param circles - the circles to update
	 */
	public void updateCircles(CircleList circles) {
		circles.setAllMultipliers(multiplier);
	}
	
	/**
	 * Update the powerup.
	 * @param deltaFloat	the time since last update
	 * @param circles		the circles to update
	 */
	public void update(float deltaFloat, CircleList circles) {
		timeRemaining -= deltaFloat;
		
		if (timeRemaining <= 0) {
			this.done = true;
			this.multiplier = 1;
			updateCircles(circles);
		}
	}

	/**
	 * @return the multiplier
	 */
	public float getMultiplier() {
		return multiplier;
	}

	/**
	 * @param multiplier the multiplier to set
	 */
	public void setMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}

	/**
	 * @return the timeRemaining
	 */
	public float getTimeRemaining() {
		return timeRemaining;
	}

	/**
	 * @return the done
	 */
	public boolean isDone() {
		return done;
	}
}
