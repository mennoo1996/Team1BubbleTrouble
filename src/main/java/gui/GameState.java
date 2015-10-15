package gui;
import logic.LevelContainer;
import logic.Logger;
import logic.MyRectangle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This class is the state that we are in during gameplay.
 * It contains basically all the game logic.
 * @author Menno
 *
 */
/**
 * @author Bart
 *
 */
public class GameState extends BasicGameState {
	
	
	
	private GameStatePlayerHelper playerHelper;
	private GameStateCirclesHelper circlesHelper;
	private GameStateInterfaceHelper interfaceHelper;
	private GameStateItemsHelper itemsHelper;
	private GameStatePauseHelper pauseHelper;
	private GameStateLogicHelper logicHelper;
	
	private MainGame mainGame;
	
	// level objects
	private MyRectangle floor;
	private MyRectangle leftWall;
	private MyRectangle rightWall;
	private MyRectangle ceiling;
	private Input savedInput;

	private LevelContainer levels;
	
	// CONSTANTS
	private static final int FLOOR_Y_DEVIATION = 210;
	private static final int FLOOR_HEIGHT = 210;
	private static final int LEFT_WALL_WIDTH = 105;
	private static final int RIGHT_WALL_X_DEVIATION = 130;
	private static final int RIGHT_WALL_WIDTH = 130;
	private static final int CEILING_HEIGHT = 110;
	
	/**
	 * The constructor.
	 * 
	 * @param mainGame the maingame this state belongs to
	 */
	public GameState(MainGame mainGame) {
		this.mainGame = mainGame;
	}
	
	/**
	 * setup all variables when entering this state.
	 * @param container the Container this state is part of
	 * @param arg1 The statebasedgame this state is part of
	 * @throws SlickException sometimes.
	 */
	@Override
	public void enter(GameContainer container, StateBasedGame arg1) throws SlickException {
		Logger.getInstance().log("Entering GameState", Logger.PriorityLevels.LOW, "States");
		RND.setOpacity(0.0f);
		mainGame.stopSwitchState();
		levels.initialize();
		playerHelper.enter();
		circlesHelper.enter();
		itemsHelper.enter();
		interfaceHelper.enter();
		logicHelper.enter();
		setFloor(new MyRectangle(0, container.getHeight() - FLOOR_Y_DEVIATION,
				container.getWidth(), FLOOR_HEIGHT));
		setLeftWall(new MyRectangle(0, 0, LEFT_WALL_WIDTH, container.getHeight()));
		setRightWall(new MyRectangle(container.getWidth() - RIGHT_WALL_X_DEVIATION,
				0, RIGHT_WALL_WIDTH, container.getHeight()));
		setCeiling(new MyRectangle(0, 0, container.getWidth(), CEILING_HEIGHT));
	}
	
	/**
	 * Exit function for state. Fades out and everything.
	 * @param container the GameContainer we are running in
	 * @param sbg the gamestate cont.
	 * @param delta the deltatime in ms
	 */
	public void exit(GameContainer container, StateBasedGame sbg, int delta) {
		if (mainGame.getShouldSwitchState()) {
			exitOpacityNotZero(delta);
			exitOpacityZero(sbg);
		}
	}
	
	/**
	 * Ran when exiting while RND opacity is not at zero.
	 * @param delta delta time for opacity checks.
	 */
	private void exitOpacityNotZero(int delta) {
		if (RND.getOpacity() > 0.0f) {
			int fadeTimer = mainGame.getOpacityFadeTimer();
			if (mainGame.getSwitchState() == -1) {
				fadeTimer = 2 * 2 * 2 * fadeTimer;
			}
			RND.setOpacity(RND.getOpacity() - ((float) delta) / fadeTimer);
		}
	}
	
	/**
	 * Ran when exitigng while RND opacity is at zero.
	 * @param sbg the statebasedgame to use for switching states.
	 */
	private void exitOpacityZero(StateBasedGame sbg) {
		if (RND.getOpacity() <= 0.0f) {
			Logger.getInstance().log("Exiting GameState", Logger.PriorityLevels.LOW, "States");
			if (mainGame.getSwitchState() == -1) {
				System.exit(0);
			} else {
				mainGame.switchColor();
				playerHelper.exit();
				sbg.enterState(mainGame.getSwitchState());
			}
		}
	}
	
	/**
	 * load resources when state is initialised.
	 * @param container The game container this state is used in
	 * @param arg1 The state based game that uses this state
	 * @throws SlickException when something goes wrong
	 */
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		playerHelper = new GameStatePlayerHelper(mainGame, this);
		circlesHelper = new GameStateCirclesHelper(mainGame, this);
		interfaceHelper = new GameStateInterfaceHelper(mainGame, this);
		itemsHelper = new GameStateItemsHelper(mainGame, this);
		pauseHelper = new GameStatePauseHelper(mainGame, this);
		logicHelper = new GameStateLogicHelper(mainGame, this);
		
		setFloor(new MyRectangle(0, container.getHeight() - FLOOR_Y_DEVIATION,
				container.getWidth(), FLOOR_HEIGHT));
		setLeftWall(new MyRectangle(0, 0, LEFT_WALL_WIDTH, container.getHeight()));
		setRightWall(new MyRectangle(container.getWidth() - RIGHT_WALL_X_DEVIATION,
				0, RIGHT_WALL_WIDTH, container.getHeight()));
		setCeiling(new MyRectangle(0, 0, container.getWidth(), CEILING_HEIGHT));
		levels = new LevelContainer(mainGame);
	}
	
	/**
	 * update method, called on each frame refresh.
	 * @param container The GameContainer this state is used in
	 * @param sbg The state based game that uses this state
	 * @param delta The time in ms since the last frame
	 * @throws SlickException if something goes wrong
	 */
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		updateOpacity(delta);
		setSavedInput(container.getInput());
		logicHelper.update(container, sbg, delta);
		exit(container, sbg, delta);
	}
	
	/**
	 * Process properly updating and increasing the opacity from the update loop.
	 * @param delta elapsed delta time.
	 */
	private void updateOpacity(int delta) {
		if (RND.getOpacity() < 1.0f && !mainGame.getShouldSwitchState()) {
			RND.setOpacity(RND.getOpacity() + ((float) delta) / mainGame.getOpacityFadeTimer());
		}
	}

	/**
	 * Render method.
	 * draw things on screen
	 * @param container The gamecontainer this state is used in.
	 * @param arg1 The state based game that uses this state.
	 * @param graphics The graphics object used for drawing things on screen
	 * @throws SlickException when something goes wrong.
	 */
	public void render(GameContainer container, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		RND.drawBackground(graphics);
		circlesHelper.render(graphics, container);
		playerHelper.render(graphics, container);
		itemsHelper.render(graphics, container);
		interfaceHelper.renderBottomLayer(graphics, container);
		logicHelper.render(graphics, container);
	}

	/**
	 * Return the id of this gameState.
	 * @return the id of gameState
	 */
	@Override
	public int getID() {
		return 1;
	}
	
	/**
	 * Set the ceiling.
	 * @param c the ceiling to set
	 */
	public void setCeiling(MyRectangle c) {
		ceiling = c;
	}
	
	/**
	 * Set the floor.
	 * @param floor the floor to set
	 */
	public void setFloor(MyRectangle floor) {
		this.floor = floor;
	}
	
	/**
	 * Set the left wall.
	 * @param leftWall the left wall to set
	 */
	public void setLeftWall(MyRectangle leftWall) {
		this.leftWall = leftWall;
	}
	
	/**
	 * Set the right wall.
	 * @param rightWall the right wall to set
	 */
	public void setRightWall(MyRectangle rightWall) {
		this.rightWall = rightWall;
	}

	/**
	 * Get the MainGame.
	 * @return the maingame
	 */
	public MainGame getmainGame() {
		return mainGame;
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
	
	/**
	 * @return the savedInput
	 */
	public Input getSavedInput() {
		return savedInput;
	}

	/**
	 * @param savedInput the savedInput to set
	 */
	public void setSavedInput(Input savedInput) {
		this.savedInput = savedInput;
	}
	
	/**
	 * @return The levelContainer used for holding and creating levels.
	 */
	public LevelContainer getLevelContainer() {
		return levels;
	}
	
	/**
	 * @return The GameStateCirclesHelper object.
	 */
	public GameStateCirclesHelper getCirclesHelper() {
		return circlesHelper;
	}
	
	/**
	 * @return The GameStateInterfaceHelper object.
	 */
	public GameStateInterfaceHelper getInterfaceHelper() {
		return interfaceHelper;
	}
	
	/**
	 * @return The GameStatePauseHelper object.
	 */
	public GameStatePauseHelper getPauseHelper() {
		return pauseHelper;
	}
	
	/**
	 * @return The GameStatePlayerHelper object.
	 */
	public GameStatePlayerHelper getPlayerHelper() {
		return playerHelper;
	}
	
	/**
	 * @return The GameStateLogicHelper object.
	 */
	public GameStateLogicHelper getLogicHelper() {
		return logicHelper;
	}
	
	/**
	 * @return The GameStateItemsHelper object
	 */
	public GameStateItemsHelper getItemsHelper() {
		return itemsHelper;
	}
	
}
