package commands;

import logic.BouncingCircle;
import logic.CircleList;

/**
 * Command to remove circle from circlelist.
 * @author Bart
 *
 */
public class RemoveCircleCommand extends Command {

	private final CircleList circleList;
	private final BouncingCircle circle;
	
	 
	
	/**
	 * @param circleList	the circlelist to remove from.
	 * @param circle		the circle to remove.
	 */
	public RemoveCircleCommand(CircleList circleList, BouncingCircle circle) {
		super();
		this.circleList = circleList;
		this.circle = circle;
	}



	@Override
	public void execute() {
		circleList.removeCircle(circle);
	}

}
