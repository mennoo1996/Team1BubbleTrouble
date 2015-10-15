package commands;

import java.util.ArrayList;

import logic.BouncingCircle;
import logic.CircleList;

/**
 * Command to set circlelist.
 * @author Bart
 *
 */
public class SetCirclelistCommand extends Command {

	private final CircleList circleList;
	private final ArrayList<BouncingCircle> newCircleList;
	
	 
	
	/**
	 * @param circleList	the circlelist to reset
	 * @param newCircleList		the circlelist to reset
	 */
	public SetCirclelistCommand(CircleList circleList, ArrayList<BouncingCircle> newCircleList) {
		super();
		this.circleList = circleList;
		this.newCircleList = newCircleList;
	}



	@Override
	public void execute() {
		synchronized (circleList.getCircles()) {
			circleList.setCircles(newCircleList);
		}
	}

}
