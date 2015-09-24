package gui;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lan.Client;
import lan.Host;
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
	private GameState gameState;
	private Input input;
	
	private Textfield nameField;
	private Textfield ipField;
	
	private Separator separatorTop;
	private String separatorTopTitle = "";
	private Separator separatorHost;
	private String separatorHostTitle = " Host Game ";
	private Separator separatorJoin;
	private String separatorJoinTitle = " Join Game ";
	private Separator separatorMisc;
	private String separatorMiscTitle = " Miscellaneous ";
	
	private static final int NUM_3 = 3;
	private static final int NUM_4 = 4;
	private static final int NUM_5 = 5;
	private static final int NUM_6 = 6;
	private static final int NUM_7 = 7;
	
	private static final int LOGO_X = 160;
	private static final int LOGO_Y = 110;
	private static final int SEPARATOR_X = 164;
	private static final int SEPARATOR_Y = 190;
	private static final int SEPARATOR_Y_2 = 388;
	private static final int SEPARATOR_Y_3 = 488;
	private static final int SEPARATOR_Y_4 = 658;
	
	private static final int BOTTOM_TEXT_OFFSET_X = 250;
	private static final int BOTTOM_TEXT_OFFSET_Y = 75;
	
	private static final int RETURN_BUTTON_X = 150;
	private static final int RETURN_BUTTON_Y = 175;
	private static final int RETURN_BUTTON_WIDTH = 1000;
	private static final int RETURN_BUTTON_HEIGHT = 50;
	
	private static final int TEXT_HELP_X = 164;
	private static final int TEXT_HELP_Y_1 = 238;
	private static final int TEXT_HELP_Y_2 = 288;
	private static final int TEXT_HELP_Y_3 = 338;
	private static final int TEXT_HELP_Y_4 = 738;
	
	private static final int HOST_BUTTON_X = 150;
	private static final int HOST_BUTTON_Y = 425;
	private static final int TEXT_HOST_X = 350;
	private static final int TEXT_HOST_Y = 438;
	
	private static final int JOIN_BUTTON_X = 150;
	private static final int JOIN_BUTTON_Y = 525;
	private static final int TEXT_JOIN_Y = 588;
	
	private static final int TEXT_FIELD_X = 564;
	private static final int TEXT_FIELD_Y = 738;
	private static final int TEXT_FIELD_Y_2 = 588;
	
	/**
	 * Construct a SettingsState.
	 * @param mainGame the MainGame that uses this state.
	 * @param gameState javadoc
	 */
	public MenuMultiplayerState(MainGame mainGame, GameState gameState) {
		this.mainGame = mainGame;
		this.gameState = gameState;
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
		nameField = new Textfield(TEXT_FIELD_X, TEXT_FIELD_Y, "Player", container);
		ipField = new Textfield(TEXT_FIELD_X, TEXT_FIELD_Y_2, "127.0.0.1", container);
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
		separatorTop = new Separator(SEPARATOR_X, SEPARATOR_Y, true, separatorTopTitle,
				container.getWidth());
		separatorHost = new Separator(SEPARATOR_X, SEPARATOR_Y_2, false, separatorHostTitle,
				container.getWidth());
		separatorJoin = new Separator(SEPARATOR_X, SEPARATOR_Y_3, false, separatorJoinTitle,
				container.getWidth());
		separatorMisc = new Separator(SEPARATOR_X, SEPARATOR_Y_4, false, separatorMiscTitle,
				container.getWidth());
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
			mainGame.setLanMultiplayer(true);
			mainGame.setHost(new Host(MainGame.getMultiplayerPort(), mainGame, gameState));
			mainGame.setIsHost(true);
			mainGame.setIsClient(false);
			System.out.println(mainGame.isHost());
			ExecutorService executor = Executors.newFixedThreadPool(1);
			processPlayerHost();
			executor.submit(mainGame.getHost());
			mainGame.getLogger().log("Host started", Logger.PriorityLevels.VERYHIGH, "multiplayer");
		} 
		if (joinButton.isMouseOver(input)) {
			mainGame.setLanMultiplayer(true);
			Client client = new Client(ipField.getText(), 
					mainGame.getMultiplayerPort(), mainGame, gameState);
			mainGame.setClient(client);
	        mainGame.setIsClient(true);
	        mainGame.setIsHost(false);
			ExecutorService executor = Executors.newFixedThreadPool(1);
			processPlayerClient();
			executor.submit(client);
		} 
	}

	/**
	 * Prepare players for the host's viewpoint.
	 */
	private void processPlayerHost() {
		mainGame.getPlayerList().getPlayers().get(0).setPlayerName(nameField.getText());
		mainGame.getPlayerList().getPlayers().get(0).setControlsForPlayer1();
		mainGame.getPlayerList().getPlayers().get(1).setControlsDisabled();
	}
	
	/**
	 * Prepare players for the client's viewpoint.
	 */
	private void processPlayerClient() {
		mainGame.getPlayerList().getPlayers().get(1).setPlayerName(nameField.getText());
		mainGame.getPlayerList().getPlayers().get(1).setControlsForPlayer1();
		mainGame.getPlayerList().getPlayers().get(0).setControlsDisabled();
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
		
		drawText(graphics, container);
		drawSprites(graphics);
		mainGame.drawWaterMark();
		RND.drawColor(graphics, mainGame.getGameLogoN(), mainGame.getGameLogoA(),
				LOGO_X, LOGO_Y, mainGame.getColor());

		graphics.drawImage(mainGame.getForeGroundImage(), 0, 0);
		graphics.drawImage(mainGame.getTerminalImage(), 0, 0);
	}
	
	/**
	 * Draw all the text in this state.
	 * @param graphics context to use
	 * @param container appgamecontainer to use
	 */
	private void drawText(Graphics graphics, GameContainer container) {
		RND.text(graphics, TEXT_HELP_X, TEXT_HELP_Y_1, 
				"# You can play a game together with another player, over LAN.",
				mainGame.getColor());
		RND.text(graphics, TEXT_HELP_X, TEXT_HELP_Y_2, 
				"# If you are the host, you will have to wait until another player joins you.",
				mainGame.getColor());
		RND.text(graphics, TEXT_HELP_X, TEXT_HELP_Y_3, 
				"# If you wish to join another player,"
				+ " please enter their IP-address below.", mainGame.getColor());
		RND.text(graphics, TEXT_HELP_X, TEXT_HELP_Y_4, "# Your player name:", mainGame.getColor());
		if (mainGame.isHost()) {
			try {
				RND.text(graphics, TEXT_HOST_X, TEXT_HOST_Y, "# Hosting game on IP: " 
						+ InetAddress.getLocalHost().getHostAddress(), mainGame.getColor());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		RND.text(graphics, TEXT_HELP_X, TEXT_JOIN_Y, "# Join this IP: ", mainGame.getColor());
		RND.text(graphics, container.getWidth() / 2 - BOTTOM_TEXT_OFFSET_X,
				container.getHeight() - BOTTOM_TEXT_OFFSET_Y, "Waiting for user input...");
	}
	
	/**
	 * Draw the player sprites.
	 * @param graphics the Graphics object to draw things on screen
	 */
	private void drawSprites(Graphics graphics) {
		returnButton.drawColor(graphics, input, mainGame.getColor());
		hostButton.drawColor(graphics, input, mainGame.getColor());
		joinButton.drawColor(graphics, input, mainGame.getColor());
		separatorTop.drawColor(graphics, mainGame.getColor());
		separatorHost.drawColor(graphics, mainGame.getColor());
		separatorJoin.drawColor(graphics, mainGame.getColor());
		separatorMisc.drawColor(graphics, mainGame.getColor());
		ipField.drawColor(graphics, mainGame.getColor());
		nameField.drawColor(graphics, mainGame.getColor());
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
