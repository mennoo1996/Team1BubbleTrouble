package guigame;
import guimenu.MainGame;
import guiobjects.RND;
import logic.Logger;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import commands.CommandQueue;

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
	
	private CommandQueue commandQueue;
	
	private GameStatePlayerHelper playerHelper;
	private GameStateCirclesHelper circlesHelper;
	private GameStateInterfaceHelper interfaceHelper;
	private GameStateItemsHelper itemsHelper;
	private GameStatePauseHelper pauseHelper;
	private GameStateLogicHelper logicHelper;
	private GameStateGateHelper gateHelper;
	private GameStateLevelsHelper levelsHelper;
	
	private MainGame mainGame;
	
	private Input savedInput;
	
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
		RND.getInstance().setOpacity(0.0f);
		mainGame.stopSwitchState();
		playerHelper.enter();
		levelsHelper.enter();
		circlesHelper.enter();
		itemsHelper.enter();
		interfaceHelper.enter();
		logicHelper.enter();
		gateHelper.enter();
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
		if (RND.getInstance().getOpacity() > 0.0f) {
			int fadeTimer = mainGame.getOpacityFadeTimer();
			if (mainGame.getSwitchState() == -1) {
				fadeTimer = 2 * 2 * 2 * fadeTimer;
			}
			RND.getInstance().setOpacity(RND.getInstance().getOpacity()
					- ((float) delta) / fadeTimer);
		}
	}
	
	/**
	 * Ran when exitigng while RND opacity is at zero.
	 * @param sbg the statebasedgame to use for switching states.
	 */
	private void exitOpacityZero(StateBasedGame sbg) {
		if (RND.getInstance().getOpacity() <= 0.0f) {
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
		gateHelper = new GameStateGateHelper(mainGame, this);
		levelsHelper = new GameStateLevelsHelper(mainGame, this, container);
		commandQueue = CommandQueue.getInstance();
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
		
		commandQueue.executeQueue();
	}
	
	/**
	 * Process properly updating and increasing the opacity from the update loop.
	 * @param delta elapsed delta time.
	 */
	private void updateOpacity(int delta) {
		if (RND.getInstance().getOpacity() < 1.0f && !mainGame.getShouldSwitchState()) {
			RND.getInstance().setOpacity(RND.getInstance().getOpacity()
					+ ((float) delta) / mainGame.getOpacityFadeTimer());
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
		RND.getInstance().drawBackground(graphics);
		circlesHelper.render(graphics, container);
		gateHelper.render(graphics, container);
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
	 * Get the MainGame.
	 * @return the maingame
	 */
	public MainGame getmainGame() {
		return mainGame;
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
	 * @return The GameStateCirclesHelper object.
	 */
	public GameStateLevelsHelper getLevelsHelper() {
		return levelsHelper;
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
	
	/**
	 * @return The GameStateGateHelper object
	 */
	public GameStateGateHelper getGateHelper() {
		return gateHelper;
	}
	
}
