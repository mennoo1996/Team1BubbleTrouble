package guigame;

import logic.LevelContainer;
import logic.MyRectangle;
import guimenu.MainGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * GameState Helper class for dealing with level information, to help GameState carry less load.
 * @author Mark
 */
public class GameStateLevelsHelper extends GameStateHelper {
	
	private MyRectangle floor;
	private MyRectangle leftWall;
	private MyRectangle rightWall;
	private MyRectangle ceiling;

	private LevelContainer levels;

	private static final int FLOOR_Y_DEVIATION = 210;
	private static final int FLOOR_HEIGHT = 210;
	private static final int LEFT_WALL_WIDTH = 105;
	private static final int RIGHT_WALL_X_DEVIATION = 130;
	private static final int RIGHT_WALL_WIDTH = 130;
	private static final int CEILING_HEIGHT = 110;
	
	/**
	 * Constructor for creating a GameStateLevelsHelper object.
	 * @param app the mainGame we are in.
	 * @param state the parent GameState
	 * @param container the parent gamecontainer.
	 */
	public GameStateLevelsHelper(MainGame app, GameState state, GameContainer container) {
		floor = new MyRectangle(0, container.getHeight() - FLOOR_Y_DEVIATION,
				container.getWidth(), FLOOR_HEIGHT);
		
		leftWall = new MyRectangle(0, 0, LEFT_WALL_WIDTH, container.getHeight());
		rightWall = new MyRectangle(container.getWidth() - RIGHT_WALL_X_DEVIATION,
				0, RIGHT_WALL_WIDTH, container.getHeight());
		ceiling = new MyRectangle(0, 0, container.getWidth(), CEILING_HEIGHT);
		levels = new LevelContainer(app);
	}
	
	@Override
	public void enter() {
		levels.initialize();
	}

	@Override
	public void exit() {
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg,
			float deltaFloat) {
	}

	@Override
	public void render(Graphics graphics, GameContainer container) {
	}
	
	/**
	 * @return The levelContainer used for holding and creating levels.
	 */
	public LevelContainer getLevelContainer() {
		return levels;
	}
	
	/**
	 * Set the ceiling.
	 * @param c the ceiling to set
	 */
	public void setCeiling(MyRectangle c) {
		ceiling = c;
	}
	
	/**
	 * @return the floor
	 */
	public MyRectangle getFloor() {
		return floor;
	}

	/**
	 * @return the ceiling
	 */
	public MyRectangle getCeiling() {
		return ceiling;
	}

	/**
	 * @return the leftWall
	 */
	public MyRectangle getLeftWall() {
		return leftWall;
	}

	/**
	 * @return the rightWall
	 */
	public MyRectangle getRightWall() {
		return rightWall;
	}

}
