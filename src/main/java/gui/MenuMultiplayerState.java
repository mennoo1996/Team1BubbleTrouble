package gui;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lan.Client;
import lan.Host;
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
 * This class represents the state of the multiplayer menu.
 * @author Mark
 *
 */
public class MenuMultiplayerState extends BasicGameState {

	private Button returnButton;
	private Button hostButton;
	private Button joinButton;

	private MainGame mainGame;
	private Input input;
	
	private static final int NUM_3 = 3;
	private static final int NUM_4 = 4;
	private static final int NUM_5 = 5;
	private static final int NUM_6 = 6;
	private static final int NUM_7 = 7;
	
	private static final int LOGO_X = 160;
	private static final int LOGO_Y = 110;
	private static final int SEPARATOR_X = 319;
	private static final int SEPARATOR_X_2 = 164;
	private static final int SEPARATOR_Y = 190;
	private static final int SEPARATOR_Y_2 = 510;
	
	private static final int BOTTOM_TEXT_OFFSET_X = 250;
	private static final int BOTTOM_TEXT_OFFSET_Y = 75;
	
	private static final int RETURN_BUTTON_X = 150;
	private static final int RETURN_BUTTON_Y = 175;
	private static final int RETURN_BUTTON_WIDTH = 1000;
	private static final int RETURN_BUTTON_HEIGHT = 50;
	
	private static final int HOST_BUTTON_X = 150;
	private static final int HOST_BUTTON_Y = 225;
	private static final int JOIN_BUTTON_X = 150;
	private static final int JOIN_BUTTON_Y = 275;
	
	private static final int CONTROL_X1 = 800;
	private static final int CONTROL_X2 = 1000;
	private static final int P1_CONTROL_Y = 238;
	private static final int P2_CONTROL_Y = 388;
	
	private static final int TEXT_X = 164;
	private static final int TEXT_1_Y = 288;
	private static final int TEXT_2_Y = 338;
	private static final int TEXT_3_Y = 388;
	private static final int TEXT_4_Y = 438;
	private static final int PLAYER_1_TEXT_Y = 590;
	private static final int PLAYER_2_TEXT_Y = 710;
	
	/**
	 * Construct a SettingsState.
	 * @param mainGame the MainGame that uses this state.
	 */
	public MenuMultiplayerState(MainGame mainGame) {
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
		mainGame.getLogger().log("Entering MenuMultiplayerState", 
				Logger.PriorityLevels.LOW, "States");
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
				RND.setOpacity(RND.getOpacity() - ((float) delta) / mainGame.getOpacityFadeTimer());
			} else {
				mainGame.getLogger().log("Exiting MenuMultiplayerState", 
						Logger.PriorityLevels.LOW, "States");
				if (mainGame.getSwitchState() == -1) {
					container.exit();
				} else {
					mainGame.switchColor();
					sbg.enterState(mainGame.getSwitchState());
				}
			}	
		}
	}
	
	/**
	 * Initialize this state.
	 * @param container the GameContainer that contains this state
	 * @param arg1 the state based game that uses this state
	 * @throws SlickException if something goes wrong
	 */
	public void init(GameContainer container, StateBasedGame arg1) throws SlickException {
		initButtons();
	}
	
	/**
	 * Initialize the buttons.
	 * @throws SlickException if something goes wrong / file not found
	 */
	private void initButtons() throws SlickException {
		returnButton = new Button(RETURN_BUTTON_X, RETURN_BUTTON_Y, RETURN_BUTTON_WIDTH,
				RETURN_BUTTON_HEIGHT, 
				new Image("resources/images_UI/Menu_Button_Return_Norm.png"),
				new Image("resources/images_UI/Menu_Button_Return_Add.png"),
				new Image("resources/images_UI/Menu_Button_Return2_Norm.png"),
				new Image("resources/images_UI/Menu_Button_Return2_Add.png"));
		hostButton = new Button(HOST_BUTTON_X, HOST_BUTTON_Y, RETURN_BUTTON_WIDTH,
				RETURN_BUTTON_HEIGHT, 
				new Image("resources/images_UI/Menu_Button_Host_Norm.png"),
				new Image("resources/images_UI/Menu_Button_Host_Add.png"),
				new Image("resources/images_UI/Menu_Button_Host2_Norm.png"),
				new Image("resources/images_UI/Menu_Button_Host2_Add.png"));
		joinButton = new Button(JOIN_BUTTON_X, JOIN_BUTTON_Y, RETURN_BUTTON_WIDTH,
				RETURN_BUTTON_HEIGHT, 
				new Image("resources/images_UI/Menu_Button_Join_Norm.png"),
				new Image("resources/images_UI/Menu_Button_Join_Add.png"),
				new Image("resources/images_UI/Menu_Button_Join2_Norm.png"),
				new Image("resources/images_UI/Menu_Button_Join2_Add.png"));
	}
	
	/**
	 * Update this state.
	 * @param container The gamecontainer that contains this state
	 * @param sbg the state based game that uses this state
	 * @param delta the time in ms since the last frame
	 * @throws SlickException if something goes wrong
	 */
	public void update(GameContainer container, StateBasedGame sbg, int delta) 
			throws SlickException {
		
		if (RND.getOpacity() < 1.0f && !mainGame.getShouldSwitchState()) {
			RND.setOpacity(RND.getOpacity() + ((float) delta) / mainGame.getOpacityFadeTimer());
		}
		
		input = container.getInput();
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && !mainGame.getShouldSwitchState()) {
			processButtons(input);
		}
		
		exit(container, sbg, delta);
	}

	/**
	 * Process the buttons.
	 * @param input the keyboard/mouse input of the user
	 */
	private void processButtons(Input input) {
		if (returnButton.isMouseOver(input)) {
			mainGame.setSwitchState(mainGame.getStartState());
		} 
		if (hostButton.isMouseOver(input)) {
			int i = 1;
			i = 0;
			// Spawn thread logic
			// TODO: Move this to another location for multiplayer menu
			mainGame.setLanMultiplayer(true);
			mainGame.setHost(new Host(MainGame.getMultiplayerPort()));
			ExecutorService executor = Executors.newFixedThreadPool(1);
			executor.submit(mainGame.getHost());
			mainGame.getLogger().log("Host started", Logger.PriorityLevels.VERYHIGH, "multiplayer");
			// host button stuff
		} 
		if (joinButton.isMouseOver(input)) {
			// join button stuff
			int i = 1;
			i = 0;
			// Spawn thread logic
			// TODO: Move this to another location for multiplayer menu
			mainGame.setLanMultiplayer(true);
			Client client = new Client("127.0.0.1", mainGame.getMultiplayerPort());
			ExecutorService executor = Executors.newFixedThreadPool(1);
			executor.submit(client);
			
		} 
	}
	
	/**
	 * Render this state.
	 * @param container the Gamecontainer that contains this state
	 * @param arg1 the state based game that uses this state
	 * @param graphics the Graphics object used in this state
	 * @throws SlickException if something goes wrong.
	 */
	public void render(GameContainer container, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		this.input = container.getInput();
		
		graphics.drawImage(mainGame.getBackgroundImage(), 0, 0);
//		RND.text(graphics, TEXT_X, TEXT_1_Y, "# You can choose a player skin per");
//		RND.text(graphics, TEXT_X, TEXT_2_Y, "# player by clicking on it below,");
//		RND.text(graphics, TEXT_X, TEXT_3_Y, "# we advice different sprites for");
//		RND.text(graphics, TEXT_X, TEXT_4_Y, "# each player but it's your choice!");
//	
//		RND.text(graphics, TEXT_X, PLAYER_1_TEXT_Y, "> Player 1:");
//		RND.text(graphics, TEXT_X, PLAYER_2_TEXT_Y, "> Player 2:");
	
		RND.text(graphics, container.getWidth() / 2 - BOTTOM_TEXT_OFFSET_X,
				container.getHeight() - BOTTOM_TEXT_OFFSET_Y, "Waiting for user input...");
		drawSprites(graphics);

		mainGame.drawWaterMark();
		RND.drawColor(graphics, mainGame.getGameLogoN(), mainGame.getGameLogoA(),
				LOGO_X, LOGO_Y, mainGame.getColor());
		String tempString = "==============================";
		tempString += "=======================================";
		RND.text(graphics, SEPARATOR_X, SEPARATOR_Y, tempString);
		RND.text(graphics, SEPARATOR_X_2, SEPARATOR_Y_2, tempString + "==========");
		graphics.drawImage(mainGame.getForeGroundImage(), 0, 0);
		graphics.drawImage(mainGame.getTerminalImage(), 0, 0);
	}
	
	/**
	 * Draw the player sprites.
	 * @param graphics the Graphics object to draw things on screen
	 */
	private void drawSprites(Graphics graphics) {
		returnButton.drawColor(graphics, input, mainGame.getColor());
		hostButton.drawColor(graphics, input, mainGame.getColor());
		joinButton.drawColor(graphics, input, mainGame.getColor());
	}
	

	@Override
	public int getID() {
		return mainGame.getMultiplayerState();
	}

	/**
	 * @return the mainGame
	 */
	public MainGame getmainGame() {
		return mainGame;
	}

	/**
	 * @param mainGame the mainGame to set
	 */
	public void setmainGame(MainGame mainGame) {
		this.mainGame = mainGame;
	}
	
	
}
