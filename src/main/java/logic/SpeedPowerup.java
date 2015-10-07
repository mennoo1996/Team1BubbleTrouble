package logic;


/**
 * powerup which influences the speed of the circles.
 * @author Bart
 *
 */
public abstract class SpeedPowerup {

	private float multiplier;
	private float timeRemaining;
	private static final int DURATION = 10;
	
	/**
	 * Construcor.
	 * @param multiplier - the multiplayer of the powerup
	 */
	public SpeedPowerup(float multiplier) {
		this.multiplier = multiplier;
		this.timeRemaining = DURATION;
	}
	
	/**
	 * Set multiplier of all circles in circlelist.
	 * @param circles - the circles to update
	 */
	public void updateCircles(CircleList circles) {
		circles.setAllMultipliers(multiplier);
	}
	
	public void update() {
		
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
	
	
	
}
