package commands;

import logic.Player;
import powerups.Powerup.PowerupType;

/**
 * Command to add powerup to a player.
 * @author Bart
 *
 */
public class AddPowerupToPlayerCommand extends Command {
	private final Player player;
	private final PowerupType type;
	
	/**
	 * @param player	- the player to add the powerup to.
	 * @param type		- the powerup type to add.
	 */
	public AddPowerupToPlayerCommand(Player player, PowerupType type) {
		this.player = player;
		this.type = type;
	}



	@Override
	public void execute() {
		synchronized (player) {
			player.addPowerup(type);
		}
	}
}
