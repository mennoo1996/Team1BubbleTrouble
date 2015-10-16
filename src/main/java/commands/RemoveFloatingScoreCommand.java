package commands;

import java.util.ArrayList;

import logic.FloatingScore;

/**
 * Command which removes floatingscore from given list.
 * @author Bart
 *
 */
public class RemoveFloatingScoreCommand extends Command {

	private final ArrayList<FloatingScore> list;
	private final FloatingScore item;
	
	/**
	 * @param list	- the list to remove from
	 * @param item	- the item to remove
	 */
	public RemoveFloatingScoreCommand(ArrayList<FloatingScore> list, FloatingScore item) {
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
