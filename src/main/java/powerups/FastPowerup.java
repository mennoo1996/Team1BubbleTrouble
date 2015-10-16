package powerups;

/**
 * A powerup which will let all circles stand still.
 * @author Bart
 *
 */
public class FastPowerup extends SpeedPowerup {
	
	private static final float MULTIPLIER = 1.5f;
	
	/**
	 * Construct a new FastPowerup.
	 */
	public FastPowerup() {
		super(MULTIPLIER);
	}

}
