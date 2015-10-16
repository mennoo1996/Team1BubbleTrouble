package powerups;

/**
 * A powerup which will let all circles stand still.
 * @author Bart
 *
 */
public class SlowPowerup extends SpeedPowerup {
	
	private static final float MULTIPLIER = 0.5f;
	
	/**
	 * Construct a new SlowPowerup.
	 */
	public SlowPowerup() {
		super(MULTIPLIER);
	}

}
