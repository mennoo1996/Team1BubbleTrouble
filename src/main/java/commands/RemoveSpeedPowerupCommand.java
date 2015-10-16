package commands;

import java.util.ArrayList;

import powerups.SpeedPowerup;

/**
 * Command which removes speed powerup from given list.
 * @author Bart
 *
 */
public class RemoveSpeedPowerupCommand extends Command {

	private final ArrayList<SpeedPowerup> list;
	private final SpeedPowerup item;
	
	/**
	 * @param list	- the list to remove from
	 * @param item	- the item to remove
	 */
	public RemoveSpeedPowerupCommand(ArrayList<SpeedPowerup> list, SpeedPowerup item) {
		this.list = list;
		this.item = item;
	}



	@Override
	public void execute() {
		synchronized (list) {
			if (list.contains(item)) {
				list.remove(item);
			}
		}
	}

}
