package powerups;


/**
 * A powerup which will let all circles stand still.
 * @author Bart
 *
 */
public class FreezePowerup extends SpeedPowerup {
	
	private static final int MULTIPLIER = 0;
	
	/**
	 * Construct a new FreezePowerup.
	 */
	public FreezePowerup() {
		super(MULTIPLIER);
	}

}
