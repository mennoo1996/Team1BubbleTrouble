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
public class MenuMainState extends BasicGameState {

	 
	private MainGame mainGame;
	
	private Button playButton;
	private Button play2Button;
	private Button lanButton;
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
	private static final int PLAYBUTTONLAN_Y = 325;
	private static final int OPTIONSBUTTON_Y = 375;
	private static final int QUITBUTTON_Y = 425;
	private static final int HIGHSCORES_X = 900;
	private static final int HIGHSCORES_Y = 240;
	private static final int HIGHSCORES_TITLE_X = 760;
	private static final int HIGHSCORES_TITLE_Y = 140;
	
	/**
	 * constructor.
	 * 
	 * @param mainGame	- the maingame this state belongs to
	 */
	public MenuMainState(MainGame mainGame) {
		this.mainGame = mainGame;
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
		lanButton = new Button(BUTTON_X, PLAYBUTTONLAN_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/images_UI/Menu_Button_LAN_Norm.png"),
				new Image("resources/images_UI/Menu_Button_LAN_Add.png"),
				new Image("resources/images_UI/Menu_Button_LAN2_Norm.png"),
				new Image("resources/images_UI/Menu_Button_LAN2_Add.png"));
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
		mainGame.getLogger().log("Entering MenuMainState", Logger.PriorityLevels.LOW, "States");
		RND.setOpacity(0.0f);
		mainGame.stopSwitchState();
	}
	
	/**
	 * Exit function for state. Fades out and everything.
	 * @param container the GameContainer we are running in
	 * @param sbg the gamestate cont.
	 * @param delta the deltatime in ms
	 */
	public void exit(GameContainer container, StateBasedGame sbg, int delta) {
		if (mainGame.getShouldSwitchState()) {
			if (RND.getOpacity() > 0.0f) {
				int fadeTimer = mainGame.getOpacityFadeTimer();
				if (mainGame.getSwitchState() == -1) {
					fadeTimer = 2 * 2 * 2 * fadeTimer;
				}
				RND.setOpacity(RND.getOpacity() - ((float) delta) / fadeTimer);
			} else {
				mainGame.getLogger().log("Exiting MenuMainState", 
						Logger.PriorityLevels.LOW, "States");
				if (mainGame.getSwitchState() == -1) {
					System.exit(0);
				} else {
					mainGame.switchColor();
					sbg.enterState(mainGame.getSwitchState());
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
		if (RND.getOpacity() < 1.0f && !mainGame.getShouldSwitchState()) {
			RND.setOpacity(RND.getOpacity() + ((float) delta) / mainGame.getOpacityFadeTimer());
		}

		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && !mainGame.getShouldSwitchState()) {
			processButtons(input);
		}
		exit(container, sbg, delta);
	}
	
	/**
	 * Process the buttons.
	 * @param input the keyboard/mouse input of the user.
	 */
	private void processButtons(Input input) {
		if (playButton.isMouseOver(input)) { // Go to gamestate in singleplayer
			mainGame.setMultiplayer(false);
			mainGame.setSwitchState(mainGame.getGameState());
			mainGame.getLogger().log("Play button pressed", 
					Logger.PriorityLevels.MEDIUM, "user-input");
		} else if (play2Button.isMouseOver(input)) { // Go to gamestate in multiplayer
			mainGame.setMultiplayer(true);
			mainGame.setSwitchState(mainGame.getGameState());
			mainGame.getLogger().log("Play multiplayer button pressed", 
					Logger.PriorityLevels.MEDIUM, "user-input");
		} else if (lanButton.isMouseOver(input)) { // Go to gamestate in multiplayer
			//mainGame.setMultiplayer(true);
			mainGame.setSwitchState(mainGame.getMultiplayerState());
			mainGame.getLogger().log("Play lan button pressed", 
					Logger.PriorityLevels.MEDIUM, "user-input");
		} else if (optionsButton.isMouseOver(input)) { // Go to settingsState
			mainGame.setSwitchState(mainGame.getSettingsState());
			mainGame.getLogger().log("options button pressed", 
					Logger.PriorityLevels.MEDIUM, "user-input");
		} else if (quitButton.isMouseOver(input)) { // Quit game
			mainGame.setSwitchState(-1);
			mainGame.getLogger().log("quit button pressed", 
					Logger.PriorityLevels.MEDIUM, "user-input");
		}
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
		graphics.drawImage(mainGame.getBackgroundImage(), 0, 0);
		RND.text(graphics, container.getWidth() / 2 - BOTTOM_TEXT_OFFSET_X,
				container.getHeight() - BOTTOM_TEXT_OFFSET_Y, "Waiting for user input...");
		renderButtons(container, graphics);
		mainGame.drawWaterMark();
		RND.drawColor(graphics, mainGame.getGameLogoN(), mainGame.getGameLogoA(),
				LOGO_X, LOGO_Y, mainGame.getColor());
		String equalsString = "===================================================================";
		equalsString += "============";
		RND.text(graphics, SEPARATOR_X, SEPARATOR_Y, equalsString);
		String highScoresString = mainGame.getHighscores().toString();
		RND.text(graphics, HIGHSCORES_X, HIGHSCORES_Y, highScoresString);
		RND.text(graphics, HIGHSCORES_TITLE_X, HIGHSCORES_TITLE_Y, 
				"The best scores of your predecessors!");
		// NO DRAWING AFTER THIS POINT. BOO.
		graphics.drawImage(mainGame.getForeGroundImage(), 0, 0);
		graphics.drawImage(mainGame.getTerminalImage(), 0, 0);
		// NO DRAWING HERE. BAD PROGRAMMER. BAD.
	}

	/**
	 * Method renders buttons in StartState to screen.
	 * @param container the GameContainer used
	 * @param graphics graphics context used
	 */
	private void renderButtons(GameContainer container, Graphics graphics) {
		Input input = container.getInput();
		playButton.drawColor(graphics, input, mainGame.getColor());
		play2Button.drawColor(graphics, input, mainGame.getColor());
		lanButton.drawColor(graphics, input, mainGame.getColor());
		optionsButton.drawColor(graphics, input, mainGame.getColor());
		quitButton.drawColor(graphics, input, mainGame.getColor());
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
		return mainGame;
	}
}
