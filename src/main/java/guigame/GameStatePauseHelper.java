package guigame;

import guimenu.Button;
import guimenu.MainGame;
import guimenu.RND;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

/**
 * GameState Helper class for managing the Pause UI. This is done to prevent
 * GameState and GameStateInterfaceHeplper from having too much responsibility 
 * and/or knowledge. The class should only be used in conjunction with GameState.
 * @author Mark
 */
public class GameStatePauseHelper extends GameStateHelper {

	private MainGame mainGame;
	private GameState parentState;
	
	private Button returnButton;
	private Button menuButton;
	private Button exitButton;
	
	private boolean waitEsc;
	
	private static final int BUTTON_X = 164;
	private static final int RETURN_BUTTON_Y = 238;
	private static final int MENU_BUTTON_Y = 288;
	private static final int EXIT_BUTTON_Y = 338;
	private static final int BUTTON_WIDTH = 1000;
	private static final int BUTTON_HEIGHT = 50;
	private static final int TEXT_X = 164;
	private static final int TEXT_1_Y = 142;
	private static final int TEXT_2_Y = 190;
	private static final int PAUSE_FACTOR = 300;
	private static final float PAUSE_OVERLAY_COLOR_FACTOR = 0.5f;
	
	/**
	 * Constructor method for creating GameStateInterfaceHelper object.
	 * @param app the Main Game this class draws data from.
	 * @param state the GameState parent.
	 */
	public GameStatePauseHelper(MainGame app, GameState state) {
		this.mainGame = app;
		this.parentState = state;
		initButtons();
	}
	
	/**
	 * Initialize the buttons.
	 */
	private void initButtons() {
		returnButton = new Button(BUTTON_X, RETURN_BUTTON_Y,
				BUTTON_WIDTH, BUTTON_HEIGHT,
				"> Return");
		menuButton = new Button(BUTTON_X, MENU_BUTTON_Y,
				BUTTON_WIDTH, BUTTON_HEIGHT,
				"> Main Menu");
		exitButton = new Button(BUTTON_X, EXIT_BUTTON_Y,
				BUTTON_WIDTH, BUTTON_HEIGHT,
				"> Quit");
	}
	
	@Override
	public void enter() {
		  throw new UnsupportedOperationException("not supported");
	}

	@Override
	public void exit() {
		  throw new UnsupportedOperationException("not supported");
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, float deltaFloat) {
		Input input = container.getInput();
		if (parentState.getSavedInput().isKeyDown(Input.KEY_ESCAPE) & !waitEsc) {
			parentState.getLogicHelper().pauseStopped(false);
		}
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) & !mainGame.getShouldSwitchState()) {
			if (returnButton.isMouseOver(input) & !waitEsc) {
				parentState.getLogicHelper().pauseStopped(false); }
			if (menuButton.isMouseOver(input)) {
				mainGame.setScore(0);
				mainGame.setLevelCounter(0);
				mainGame.killMultiplayer();
				mainGame.setSwitchState(mainGame.getStartState()); }
			if (exitButton.isMouseOver(input)) {
				mainGame.killMultiplayer();
				mainGame.setSwitchState(-1); }
		}
	}
	
	/**
	 * Set the game to paused mode.
	 * @param fromPeer	indicates if the pause has ben requested from peer
	 */
	public void pauseStarted(boolean fromPeer) {
		waitEsc = true;
		Executors.newScheduledThreadPool(1).schedule(() -> waitEsc = false,
				PAUSE_FACTOR, TimeUnit.MILLISECONDS);
		parentState.getLogicHelper().setPlayingState(false);
		
		if (mainGame.isHost() && !fromPeer) {
			mainGame.getHost().updatePauseStarted();
		} else if (mainGame.isClient() && !fromPeer) {
			mainGame.getClient().updatePauseStarted();
		}
	}

	@Override
	public void render(Graphics graphics, GameContainer container) {
		Color overLay = new Color(0f, 0f, 0f, PAUSE_OVERLAY_COLOR_FACTOR);
		graphics.setColor(overLay);
		graphics.fillRect(0, 0, container.getWidth(), container.getHeight());
		RND.getInstance().text(graphics, TEXT_X, TEXT_1_Y, "# Game is paused...");
		RND.getInstance().text(graphics, TEXT_X, TEXT_2_Y, "========================");
		Input input = container.getInput();
		returnButton.drawColor(graphics, input, mainGame.getColor());
		menuButton.drawColor(graphics, input, mainGame.getColor());
		exitButton.drawColor(graphics, input, mainGame.getColor());
	}

}
