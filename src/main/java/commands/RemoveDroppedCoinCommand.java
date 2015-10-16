package commands;

import java.util.ArrayList;

import logic.Coin;

/**
 * Command which removes coin from given list.
 * @author Bart
 *
 */
public class RemoveDroppedCoinCommand extends Command {

	private final ArrayList<Coin> list;
	private final Coin item;
	
	/**
	 * @param list	- the list to remove from
	 * @param item	- the item to remove
	 */
	public RemoveDroppedCoinCommand(ArrayList<Coin> list, Coin item) {
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
