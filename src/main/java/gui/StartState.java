package gui;
import logic.Button;
import logic.Logger;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This class represents the state of the start menu.
 * @author Menno
 *
 */
public class StartState extends BasicGameState {

	
	private MainGame mg;
	private Button playButton;
	private Button play2Button;

	private Button optionsButton;
	private Button quitButton;
	
	private static final int LOGO_X = 160;
	private static final int LOGO_Y = 110;
	private static final int SEPARATOR_X = 164;
	private static final int SEPARATOR_Y = 190;
	
	private static final int BOTTOM_TEXT_OFFSET_X = 250;
	private static final int BOTTOM_TEXT_OFFSET_Y = 75;
	
	private static final int BUTTON_WIDTH = 1000;
	private static final int BUTTON_HEIGHT = 50;
	private static final int BUTTON_X = 150;
	private static final int PLAYBUTTON_Y = 225;
	private static final int PLAYBUTTON2_Y = 275;
	private static final int OPTIONSBUTTON_Y = 325;
	private static final int QUITBUTTON_Y = 375;
	
	private static final int MOUSE_OVER_RECT_X = 500;
	
	/**
	 * constructor.
	 * 
	 * @param mg	- the maingame this state belongs to
	 */
	public StartState(MainGame mg) {
		this.mg = mg;
	}
	
	/**
	 * Initialize this state.
	 * @param container The container that contains this state
	 * @param arg1 the State based game that uses this state
	 * @throws SlickException if something goes wrong
	 */
	public void init(GameContainer container, StateBasedGame arg1) throws SlickException {
		
		playButton = new Button(BUTTON_X, PLAYBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/images_UI/Menu_Button_1Player_Norm.png"),
				new Image("resources/images_UI/Menu_Button_1Player_Add.png"),
				new Image("resources/images_UI/Menu_Button_1Player2_Norm.png"),
				new Image("resources/images_UI/Menu_Button_1Player2_Add.png"));
		play2Button = new Button(BUTTON_X, PLAYBUTTON2_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/images_UI/Menu_Button_2Player_Norm.png"),
				new Image("resources/images_UI/Menu_Button_2Player_Add.png"),
				new Image("resources/images_UI/Menu_Button_2Player2_Norm.png"),
				new Image("resources/images_UI/Menu_Button_2Player2_Add.png"));
		optionsButton = new Button(BUTTON_X, OPTIONSBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/images_UI/Menu_Button_Options_Norm.png"), 
				new Image("resources/images_UI/Menu_Button_Options_Add.png"),
				new Image("resources/images_UI/Menu_Button_Options2_Norm.png"),
				new Image("resources/images_UI/Menu_Button_Options2_Add.png"));
		quitButton = new Button(BUTTON_X, QUITBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/images_UI/Menu_Button_Quit_Norm.png"),
				new Image("resources/images_UI/Menu_Button_Quit_Add.png"),
				new Image("resources/images_UI/Menu_Button_Quit2_Norm.png"),
				new Image("resources/images_UI/Menu_Button_Quit2_add.png"));
		
	}
	
	/**
	 * setup all variables when entering this state.
	 * @param container the Container this state is part of
	 * @param arg1 The statebasedgame this state is part of
	 * @throws SlickException sometimes.
	 */
	@Override
	public void enter(GameContainer container, StateBasedGame arg1) throws SlickException {
		mg.getLogger().log("Entering StartState", Logger.PriorityLevels.LOW, "States");
		RND.setOpacity(0.0f);
		mg.stopSwitchState();
	}
	
	/**
	 * Exit function for state. Fades out and everything.
	 * @param container the GameContainer we are running in
	 * @param sbg the gamestate cont.
	 * @param delta the deltatime in ms
	 */
	public void exit(GameContainer container, StateBasedGame sbg, int delta) {
		if (mg.getShouldSwitchState()) {
			if (RND.getOpacity() > 0.0f) {
				int fadeTimer = mg.getOpacityFadeTimer();
				if (mg.getSwitchState() == -1) {
					fadeTimer = 2 * 2 * 2 * fadeTimer;
				}
				RND.setOpacity(RND.getOpacity() - ((float) delta) / fadeTimer);
			} else {
				mg.getLogger().log("Exiting StartState", Logger.PriorityLevels.LOW, "States");
				if (mg.getSwitchState() == -1) {
					mg.closeRequested();
				} else {
					sbg.enterState(mg.getSwitchState());
				}
			}	
		}
	}
	
	/**
	 * Update this state.
	 * @param container The container that contains this state
	 * @param sbg the state based game that uses this state
	 * @param delta the time in ms since the last frame
	 * @throws SlickException if something goes wrong
	 */
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = container.getInput();
		if (RND.getOpacity() < 1.0f && !mg.getShouldSwitchState()) {
			RND.setOpacity(RND.getOpacity() + ((float) delta) / mg.getOpacityFadeTimer());
		}

		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && !mg.getShouldSwitchState()) {
			if (playButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
				// Go to gamestate in singleplayer
				mg.setMultiplayer(false);
				mg.setSwitchState(mg.getGameState());
			} 
			if (play2Button.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
				// Go to gamestate in multiplayer
				mg.setMultiplayer(true);
				mg.setSwitchState(mg.getGameState());
			} 
			else if (optionsButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
				// Go to settingsState
				mg.setSwitchState(mg.getSettingsState());
			}
			else if (quitButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
				// Quit game
				mg.setSwitchState(-1);
			}
		}
		exit(container, sbg, delta);
	}

	/**
	 * Render this state.
	 * @param container The gamecontainer that contains this state
	 * @param arg1 the state based game that contains this state
	 * @param graphics The Graphics used to draw things in this state
	 * @throws SlickException if something goes wrong
	 */
	public void render(GameContainer container, StateBasedGame arg1, Graphics graphics) 
			throws SlickException {
		graphics.drawImage(mg.getBackgroundImage(), 0, 0);
		RND.text(graphics, container.getWidth() / 2 - BOTTOM_TEXT_OFFSET_X,
				container.getHeight() - BOTTOM_TEXT_OFFSET_Y, "Waiting for user input...");
		renderButtons(container, graphics);
		mg.drawWaterMark();
		RND.drawColor(graphics, mg.getGameLogoN(), mg.getGameLogoA(),
				LOGO_X, LOGO_Y, mg.getColor());
		RND.text(graphics, SEPARATOR_X, SEPARATOR_Y, "========================");
		graphics.drawImage(mg.getForeGroundImage(), 0, 0);
		graphics.drawImage(mg.getTerminalImage(), 0, 0);
	}

	/**
	 * Method renders buttons in StartState to screen.
	 * @param container appgamecontainer used
	 * @param graphics graphics context used
	 */
	private void renderButtons(GameContainer container, Graphics graphics) {
		Input input = container.getInput();
		if (playButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
			RND.drawColor(graphics, playButton.getImageMouseOverN(), 
					playButton.getImageMouseOverA(), playButton.getX(), playButton.getY(), 
					mg.getColor());
		} else {
			RND.drawColor(graphics, playButton.getImageN(), playButton.getImageA(),
					playButton.getX(), playButton.getY(), mg.getColor());
		}
		if (play2Button.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
			RND.drawColor(graphics, play2Button.getImageMouseOverN(), 
					play2Button.getImageMouseOverA(), play2Button.getX(), play2Button.getY(), 
					mg.getColor());
		} else {
			RND.drawColor(graphics, play2Button.getImageN(), play2Button.getImageA(),
					play2Button.getX(), play2Button.getY(), mg.getColor());
		}
		if (optionsButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
			RND.drawColor(graphics, optionsButton.getImageMouseOverN(), 
					optionsButton.getImageMouseOverA(), optionsButton.getX(), optionsButton.getY(), 
					mg.getColor());
		} else {
			RND.drawColor(graphics, optionsButton.getImageN(), optionsButton.getImageA(),
					optionsButton.getX(), optionsButton.getY(), mg.getColor());
		}
		if (quitButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
			RND.drawColor(graphics, quitButton.getImageMouseOverN(), 
					quitButton.getImageMouseOverA(), quitButton.getX(), quitButton.getY(), 
					mg.getColor());
		} else {
			RND.drawColor(graphics, quitButton.getImageN(), quitButton.getImageA(),
					quitButton.getX(), quitButton.getY(), mg.getColor());
		}
	}

	@Override
	public int getID() {
		return 0;
	}
	
	/**
	 * Get the main game.
	 * @return the maingame
	 */
	public MainGame getMainGame() {
		return mg;
	}
}
