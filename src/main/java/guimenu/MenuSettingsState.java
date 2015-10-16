package guimenu;
import logic.Logger;
import logic.MyRectangle;
import logic.RenderOptions;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This class represents the state of the settings menu.
 * @author Menno
 *
 */
public class MenuSettingsState extends BasicGameState {

	private Button returnButton;
	
	private Button blueButton;
	private Button redButton;
	private Button yellowButton;
	private Button greenButton;
	private Button orangeButton;
	private Button whiteButton;
	private Button pinkButton;
	private Button shuffleButton;
	 
	// Game Colors
	private static final Color COLOR_RED = new Color(0.9f, 0.15f, 0.1f);
	private static final Color COLOR_YELLOW = new Color(0.7f, 0.65f, 0.1f);
	private static final Color COLOR_ORANGE = new Color(1.0f, 0.4f, 0.1f);
	private static final Color COLOR_GREEN = new Color(0.35f, 0.6f, 0.05f);
	private static final Color COLOR_BLUE = new Color(0.15f, 0.5f, 0.8f);
	private static final Color COLOR_PINK = new Color(0.85f, 0.0f, 0.4f);
	private static final Color COLOR_WHITE = new Color(0.5f, 0.5f, 0.5f);
	
	private SpriteSheet mannetjeN;
	private SpriteSheet mannetjeA;
	private SpriteSheet telefoonN;
	private SpriteSheet telefoonA;
	private SpriteSheet arieN;
	private SpriteSheet arieA;
	
	private Image highLightN;
	private Image highLightA;
	
	private MyRectangle mannetje1Rectangle;
	private MyRectangle telefoon1Rectangle;
	private MyRectangle arie1Rectangle;
	private MyRectangle mannetje2Rectangle;
	private MyRectangle telefoon2Rectangle;
	private MyRectangle arie2Rectangle;

	private Separator separatorTop;
	private Separator separatorMiddle;
	private Separator separatorBottom;
	private String separatorTopTitle = "";
	private String separatorMiddleTitle = " Player Controls ";
	private String separatorBottomTitle = " Settings ";
	
	private MainGame mainGame;
	private Input input;
	
	private static final int LOGO_X = 160;
	private static final int LOGO_Y = 110;
	private static final int SEPARATOR_X = 164;
	private static final int SEPARATOR_Y = 190;
	private static final int SEPARATOR_Y_2 = 338;
	private static final int SEPARATOR_Y_3 = 510;
	
	private static final int BOTTOM_TEXT_OFFSET_X = 250;
	private static final int BOTTOM_TEXT_OFFSET_Y = 75;
	
	private static final int RETURN_BUTTON_X = 164;
	private static final int RETURN_BUTTON_Y = 188;
	private static final int RETURN_BUTTON_WIDTH = 1000;
	private static final int RETURN_BUTTON_HEIGHT = 50;
	
	private static final int TEXT_X = 164;
	private static final int TEXT_1_Y = 238;
	private static final int TEXT_2_Y = 288;
	private static final int PLAYER_1_TEXT_Y = 590;
	private static final int PLAYER_2_TEXT_Y = 710;
	
	private static final int CONTROL_X1 = 164;
	private static final int CONTROL_XBETW = 210;
	private static final int CONTROL_X2 = 834;
	private static final int P1_CONTROL_Y = 388;
	
	private static final int COLOR_TEXT_X = 800;
	private static final int COLOR_TEXT_1_Y = 550;
	private static final int COLOR_TEXT_2_Y = 600;
	private static final int COLOR_BUTTON_1_X = 800;
	private static final int COLOR_BUTTON_2_X = 1014;
	private static final int COLOR_BUTTON_3_X = 1200;
	private static final int COLOR_BUTTON_WIDTH = 200;
	private static final int COLOR_BUTTON_1_Y = 653;
	private static final int COLOR_BUTTON_2_Y = 703;
	private static final int COLOR_BUTTON_3_Y = 753;
	
	private static final int PLAYER_SPRITE_WIDTH = 120;
	private static final int PLAYER_SPRITE_HEIGHT = 120;
	
	private static final int MANNETJE_1_X = 370;
	private static final int MANNETJE_1_Y = 540;
	private static final int TELEFOON_1_X = 500;
	private static final int TELEFOON_1_Y = 540;
	private static final int ARIE_1_X = 630;
	private static final int ARIE_1_Y = 540;

	private static final int MANNETJE_2_X = 370;
	private static final int MANNETJE_2_Y = 660;
	private static final int TELEFOON_2_X = 500;
	private static final int TELEFOON_2_Y = 660;
	private static final int ARIE_2_X = 630;
	private static final int ARIE_2_Y = 660;
	
	private static final String PLAYERSPRITE_NORM = "Playersprite_Norm.png";
	private static final String PLAYERS = "players";
	private static final String PLAYER2SPRITE_NORM = "Player2sprite_Norm.png";
	private static final String ARIESPRITE = "arieSprite.png";
	
	/**
	 * Construct a SettingsState.
	 * @param mainGame the MainGame that uses this state.
	 */
	public MenuSettingsState(MainGame mainGame) {
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
		Logger.getInstance().log("Entering MenuSettingsState", 
				Logger.PriorityLevels.LOW, "States");
		RND.getInstance().setOpacity(0.0f);
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
			if (RND.getInstance().getOpacity() > 0.0f) {
				RND.getInstance().setOpacity(RND.getInstance().getOpacity() 
						- ((float) delta) / mainGame.getOpacityFadeTimer());
			} else {
				Logger.getInstance().log("Exiting MenuSettingsState", 
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
		initPlayerImages();
		mannetje1Rectangle = new MyRectangle(MANNETJE_1_X, MANNETJE_1_Y, PLAYER_SPRITE_WIDTH,
				PLAYER_SPRITE_HEIGHT);
		telefoon1Rectangle = new MyRectangle(TELEFOON_1_X, TELEFOON_1_Y, PLAYER_SPRITE_WIDTH, 
				PLAYER_SPRITE_HEIGHT);
		arie1Rectangle = new MyRectangle(ARIE_1_X, ARIE_1_Y, PLAYER_SPRITE_WIDTH, 
				PLAYER_SPRITE_HEIGHT);
		mannetje2Rectangle = new MyRectangle(MANNETJE_2_X, MANNETJE_2_Y, PLAYER_SPRITE_WIDTH,
				PLAYER_SPRITE_HEIGHT);
		telefoon2Rectangle = new MyRectangle(TELEFOON_2_X, TELEFOON_2_Y, PLAYER_SPRITE_WIDTH, 
				PLAYER_SPRITE_HEIGHT);
		arie2Rectangle = new MyRectangle(ARIE_2_X, ARIE_2_Y, PLAYER_SPRITE_WIDTH, 
				PLAYER_SPRITE_HEIGHT);
		separatorTop = new Separator(SEPARATOR_X, SEPARATOR_Y, true, separatorTopTitle,
				container.getWidth());
		separatorMiddle = new Separator(SEPARATOR_X, SEPARATOR_Y_2, false, separatorMiddleTitle,
				container.getWidth());
		separatorBottom = new Separator(SEPARATOR_X, SEPARATOR_Y_3, false, separatorBottomTitle,
				container.getWidth());
	}
	
	/**
	 * Initialize player images.
	 * @throws SlickException if something goes wrong / file not found
	 */
	private void initPlayerImages() throws SlickException {
		highLightN = new Image("resources/images_UI/Menu_Highlight_Norm.png");
		highLightA = new Image("resources/images_UI/Menu_Highlight_Add.png");
		mannetjeN = new SpriteSheet("resources/images_Player/Playersprite_Norm.png",
				PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT);
		mannetjeA = new SpriteSheet("resources/images_Player/Playersprite_Add.png",
				PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT);
		telefoonN = new SpriteSheet("resources/images_Player/Player2sprite_Norm.png",
				PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT);
		telefoonA = new SpriteSheet("resources/images_Player/Player2sprite_Add.png",
				PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT);
		arieN = new SpriteSheet("resources/images_Player/arieSprite.png",
				PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT);
		arieA = new SpriteSheet("resources/images_Player/arieSprite_Add.png",
				PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT);
	}
	
	/**
	 * Initialize the buttons.
	 * @throws SlickException if something goes wrong / file not found
	 */
	private void initButtons() throws SlickException {
		returnButton = new Button(RETURN_BUTTON_X, RETURN_BUTTON_Y, RETURN_BUTTON_WIDTH,
				RETURN_BUTTON_HEIGHT, 
				"< Return");
		shuffleButton = new Button(COLOR_BUTTON_1_X, COLOR_BUTTON_1_Y, 
				COLOR_BUTTON_WIDTH, RETURN_BUTTON_HEIGHT,
				"> Shuffle");
		redButton = new Button(COLOR_BUTTON_1_X, COLOR_BUTTON_2_Y, 
				COLOR_BUTTON_WIDTH, RETURN_BUTTON_HEIGHT,
				"> Red");
		orangeButton = new Button(COLOR_BUTTON_1_X, COLOR_BUTTON_3_Y, 
				COLOR_BUTTON_WIDTH, RETURN_BUTTON_HEIGHT,
				"> Orange");
		greenButton = new Button(COLOR_BUTTON_2_X, COLOR_BUTTON_1_Y, 
				COLOR_BUTTON_WIDTH, RETURN_BUTTON_HEIGHT,
				"> Green");
		blueButton = new Button(COLOR_BUTTON_2_X, COLOR_BUTTON_2_Y, 
				COLOR_BUTTON_WIDTH, RETURN_BUTTON_HEIGHT,
				"> Blue");
		whiteButton = new Button(COLOR_BUTTON_2_X, COLOR_BUTTON_3_Y, 
				COLOR_BUTTON_WIDTH, RETURN_BUTTON_HEIGHT,
				"> White");
		pinkButton = new Button(COLOR_BUTTON_3_X, COLOR_BUTTON_1_Y, 
				COLOR_BUTTON_WIDTH, RETURN_BUTTON_HEIGHT,
				"> Purple");
		yellowButton = new Button(COLOR_BUTTON_3_X, COLOR_BUTTON_2_Y, 
				COLOR_BUTTON_WIDTH, RETURN_BUTTON_HEIGHT,
				"> Yellow");
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
		
		if (RND.getInstance().getOpacity() < 1.0f && !mainGame.getShouldSwitchState()) {
			RND.getInstance().setOpacity(RND.getInstance().getOpacity() 
					+ ((float) delta) / mainGame.getOpacityFadeTimer());
		}
		
		input = container.getInput();
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && !mainGame.getShouldSwitchState()) {
			processButtons(input);
			processColorButtons(input);
		}
		
		exit(container, sbg, delta);
	}

	/**
	 * Process the buttons.
	 * @param input the keyboard/mouse input of the user
	 */
	private void processButtons(Input input) {
		if (mannetje1Rectangle.contains(input.getMouseX(), input.getMouseY())) {
			processMannetje1Button();
		} else if (telefoon1Rectangle.contains(input.getMouseX(), input.getMouseY())) {
			processTelefoon1Button();
		} else if (mannetje2Rectangle.contains(input.getMouseX(), input.getMouseY())) {
			processMannetje2Button();
		} else if (telefoon2Rectangle.contains(input.getMouseX(), input.getMouseY())) {
			processTelefoon2Button();
		} else if (arie1Rectangle.contains(input.getMouseX(), input.getMouseY())) {
			processArie1Button();
		} else if (arie2Rectangle.contains(input.getMouseX(), input.getMouseY())) {
			processArie2Button();
		} else if (returnButton.isMouseOver(input)) {
			processReturnButton();
		} 
	}
	
	/**
	 * Process a click on the mannetje for player 1.
	 */
	private void processMannetje1Button() {
		mainGame.setPlayer1ImageString(PLAYERSPRITE_NORM, "Playersprite_Add.png");
		mainGame.getPlayerList().setPlayerImage(0, mainGame.getPlayer1ImageStringN(), 
				mainGame.getPlayer1ImageStringA());
		Logger.getInstance().log("Player 1 sprite changed to gameboy", 
				Logger.PriorityLevels.MEDIUM, PLAYERS);
	}
	
	/**
	 * Process a click on the mannetje for player 2.
	 */
	private void processMannetje2Button() {
		mainGame.setPlayer2ImageString(PLAYERSPRITE_NORM, "Playersprite_Add.png");
		mainGame.getPlayerList().setPlayerImage(1, mainGame.getPlayer2ImageStringN(), 
				mainGame.getPlayer2ImageStringA());
		Logger.getInstance().log("Player 2 sprite changed to gameboy", 
				Logger.PriorityLevels.MEDIUM, PLAYERS);
	}
	
	/**
	 * Process a click on the telefoon for player 1.
	 */
	private void processTelefoon1Button() {
		mainGame.setPlayer1ImageString(PLAYER2SPRITE_NORM, "Player2sprite_Add.png");
		mainGame.getPlayerList().setPlayerImage(0, mainGame.getPlayer1ImageStringN(), 
				mainGame.getPlayer1ImageStringA());
		Logger.getInstance().log("Player 1 sprite changed to phone", 
				Logger.PriorityLevels.MEDIUM, PLAYERS);
	}
	
	/**
	 * Process a click on the telefoon for player 2.
	 */
	private void processTelefoon2Button() {
		mainGame.setPlayer2ImageString(PLAYER2SPRITE_NORM, "Player2sprite_Add.png");
		mainGame.getPlayerList().setPlayerImage(1, mainGame.getPlayer2ImageStringN(), 
				mainGame.getPlayer2ImageStringA());
		Logger.getInstance().log("Player 2 sprite changed to phone", 
				Logger.PriorityLevels.MEDIUM, PLAYERS);
	}
	
	/**
	 * Process a click on Arie for player 1.
	 */
	private void processArie1Button() {
		mainGame.setPlayer1ImageString(ARIESPRITE, "arieSprite_Add.png");
		mainGame.getPlayerList().setPlayerImage(0, mainGame.getPlayer1ImageStringN(), 
				mainGame.getPlayer1ImageStringA());
		Logger.getInstance().log("Player 1 sprite changed to arie", 
				Logger.PriorityLevels.MEDIUM, PLAYERS);
	}
	
	/**
	 * Process a click on Arie for player 2.
	 */
	private void processArie2Button() {
		mainGame.setPlayer2ImageString(ARIESPRITE, "arieSprite_Add.png");
		mainGame.getPlayerList().setPlayerImage(1, mainGame.getPlayer2ImageStringN(), 
				mainGame.getPlayer2ImageStringA());
		Logger.getInstance().log("Player 2 sprite changed to arie", 
				Logger.PriorityLevels.MEDIUM, PLAYERS);
	}
	
	/**
	 * Process a click on the return button.
	 */
	private void processReturnButton() {
		mainGame.setSwitchState(mainGame.getMainState());
	}
	
	
	/**
	 * Process the color of the buttons.
	 * @param input the keyboard/mouse input of the user
	 */
	private void processColorButtons(Input input) {
		mainGame.shuffleColor(false);
		if (shuffleButton.getRectangle().contains(input.getMouseX(), input.getMouseY())) {
			mainGame.shuffleColor(true);
			mainGame.setSwitchState(mainGame.getSettingsState());
		} else if (redButton.getRectangle().contains(input.getMouseX(), input.getMouseY())) {
			mainGame.setNextColor(COLOR_RED);
			mainGame.setSwitchState(mainGame.getSettingsState());
		} else if (blueButton.getRectangle().contains(input.getMouseX(), input.getMouseY())) {
			mainGame.setNextColor(COLOR_BLUE);
			mainGame.setSwitchState(mainGame.getSettingsState());
		} else if (orangeButton.getRectangle().contains(input.getMouseX(), input.getMouseY())) {
			mainGame.setNextColor(COLOR_ORANGE);
			mainGame.setSwitchState(mainGame.getSettingsState());
		} else if (greenButton.getRectangle().contains(input.getMouseX(), input.getMouseY())) {
			mainGame.setNextColor(COLOR_GREEN);
			mainGame.setSwitchState(mainGame.getSettingsState());
		} else if (whiteButton.getRectangle().contains(input.getMouseX(), input.getMouseY())) {
			mainGame.setNextColor(COLOR_WHITE);
			mainGame.setSwitchState(mainGame.getSettingsState());
		} else if (pinkButton.getRectangle().contains(input.getMouseX(), input.getMouseY())) {
			mainGame.setNextColor(COLOR_PINK);
			mainGame.setSwitchState(mainGame.getSettingsState());
		} else if (yellowButton.getRectangle().contains(input.getMouseX(), input.getMouseY())) {
			mainGame.setNextColor(COLOR_YELLOW);
			mainGame.setSwitchState(mainGame.getSettingsState());
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
		
		RND.getInstance().drawBackground(graphics);
		RND.getInstance().text(graphics, TEXT_X, TEXT_1_Y, 
				"# You can choose a player skin per player by clicking on it below.");
		RND.getInstance().text(graphics, TEXT_X, TEXT_2_Y, 
				"# We advice different skins for each player but it's your choice!");

		RND.getInstance().text(graphics, TEXT_X, PLAYER_1_TEXT_Y, "> Player 1:");
		RND.getInstance().text(graphics, TEXT_X, PLAYER_2_TEXT_Y, "> Player 2:");
	
		RND.getInstance().text(graphics, container.getWidth() / 2 - BOTTOM_TEXT_OFFSET_X,
				container.getHeight() - BOTTOM_TEXT_OFFSET_Y, "Waiting for user input...");
		drawSprites(graphics);

		mainGame.drawWaterMark();
		RND.getInstance().drawLogo(graphics, LOGO_X, LOGO_Y);
		separatorTop.drawColor(graphics, mainGame.getColor());
		separatorMiddle.drawColor(graphics, mainGame.getColor());
		separatorBottom.drawColor(graphics, mainGame.getColor());
		drawControls(graphics);
		drawColorControls(graphics);
		// any and all drawing is done BEFORE THESE TWO FOR THE 1000TH TIME
		RND.getInstance().drawForeGround(graphics);
		// NO DRAWING HERE. BAD. BOO. SHOO. BEGONE.
	}
	
	/**
	 * Draw the controls for the players.
	 * @param graphics the Graphics object to draw things on screen
	 */
	private void drawControls(Graphics graphics) {
		String controlsPlayer1 = "Move left = left arrow\nMove right = right arrow\n";
		controlsPlayer1 += "Shoot weapon = spacebar";
		String controlsPlayer2 = "Move left = a\nMove right = d\nShoot weapon = w";
		
		RND.getInstance().text(graphics, CONTROL_X1, P1_CONTROL_Y, "# Player 1:");
		RND.getInstance().text(graphics, CONTROL_X1 + CONTROL_XBETW, P1_CONTROL_Y, controlsPlayer1);
		RND.getInstance().text(graphics, CONTROL_X2, P1_CONTROL_Y, "# Player 2:");
		RND.getInstance().text(graphics, CONTROL_X2 + CONTROL_XBETW, P1_CONTROL_Y, controlsPlayer2);
		
	}
	
	/**
	 * Draw the player sprites.
	 * @param graphics the Graphics object to draw things on screen
	 */
	private void drawSprites(Graphics graphics) {
		drawPlayer1(graphics);
		drawPlayer2(graphics);
		returnButton.drawColor(graphics, input, mainGame.getColor());
		drawSprites2(graphics);
	}
	
	/**
	 * Draw the sprite for player 1.
	 * @param graphics the graphics object to draw things on screen.
	 */
	private void drawPlayer1(Graphics graphics) {
		switch (mainGame.getPlayer1ImageStringN()) {
		case PLAYERSPRITE_NORM:
			drawPlayer1Mannetje(graphics);
			break;
		case PLAYER2SPRITE_NORM:
			drawPlayer1Telefoon(graphics);
			break;
		case ARIESPRITE:
			drawPlayer1Arie(graphics);
			break;
		default:	
		}
	}
	
	/**
	 * Draw the sprite for player 2.
	 * @param graphics the graphics object to draw things on screen.
	 */
	private void drawPlayer2(Graphics graphics) {
		switch (mainGame.getPlayer2ImageStringN()) {
		case PLAYERSPRITE_NORM:
			drawPlayer2Mannetje(graphics);
			break;
		case PLAYER2SPRITE_NORM:
			drawPlayer2Telefoon(graphics);
			break;
		case ARIESPRITE:
			drawPlayer2Arie(graphics);
			break;
		default:
		}
	}
	
	/**
	 * Draw the mannetje sprite for player 1.
	 * @param graphics the graphics object to draw things on screen.
	 */
	private void drawPlayer1Mannetje(Graphics graphics) {
		RND.getInstance().drawColor(new RenderOptions(graphics, highLightN, highLightA, 
				MANNETJE_1_X, MANNETJE_1_Y, mainGame.getColor()));
	}
	
	/**
	 * Draw the telefoon sprite for player 1.
	 * @param graphics the graphics object to draw things on screen.
	 */
	private void drawPlayer1Telefoon(Graphics graphics) {
		RND.getInstance().drawColor(new RenderOptions(graphics, highLightN, highLightA, 
				TELEFOON_1_X, TELEFOON_1_Y, mainGame.getColor()));
	}
	
	/**
	 * Draw the arie sprite for player 1.
	 * @param graphics the graphics object to draw things on screen.
	 */
	private void drawPlayer1Arie(Graphics graphics) {
		RND.getInstance().drawColor(new RenderOptions(graphics, highLightN, highLightA, 
				ARIE_1_X, ARIE_1_Y, mainGame.getColor()));
	}
	
	/**
	 * Draw the mannetje sprite for player 2.
	 * @param graphics the graphics object to draw things on screen.
	 */
	private void drawPlayer2Mannetje(Graphics graphics) {
		RND.getInstance().drawColor(new RenderOptions(graphics, highLightN, highLightA, 
				MANNETJE_2_X, MANNETJE_2_Y, mainGame.getColor()));
	}
	
	/**
	 * Draw the telefoon sprite for player 2.
	 * @param graphics the graphics object to draw things on screen.
	 */
	private void drawPlayer2Telefoon(Graphics graphics) {
		RND.getInstance().drawColor(new RenderOptions(graphics, highLightN, highLightA, 
				TELEFOON_2_X, TELEFOON_2_Y, mainGame.getColor()));
	}
	
	/**
	 * Draw the arie sprite for player 2.
	 * @param graphics the graphics object to draw things on screen.
	 */
	private void drawPlayer2Arie(Graphics graphics) {
		RND.getInstance().drawColor(new RenderOptions(graphics, highLightN, highLightA, 
				ARIE_2_X, ARIE_2_Y, mainGame.getColor()));
	}
	
	/**
	 * Draw the second batch of player sprites.
	 * @param graphics the Graphics object to draw things on screen
	 */
	private void drawSprites2(Graphics graphics) {
		RND.getInstance().drawColor(new RenderOptions(graphics, 
				mannetjeN.getSprite(2, 0), mannetjeA.getSprite(2, 0),
				mannetje1Rectangle.getX(), mannetje1Rectangle.getY(), mainGame.getColor()));
		RND.getInstance().drawColor(new RenderOptions(graphics, 
				telefoonN.getSprite(2, 0), telefoonA.getSprite(2, 0),
				telefoon1Rectangle.getX(), telefoon1Rectangle.getY(), mainGame.getColor()));
		RND.getInstance().drawColor(new RenderOptions(graphics, 
				arieN.getSprite(2, 0), arieA.getSprite(2, 0),
				arie1Rectangle.getX(), arie1Rectangle.getY(), mainGame.getColor()));
		
		RND.getInstance().drawColor(new RenderOptions(graphics, 
				mannetjeN.getSprite(2, 0), mannetjeA.getSprite(2, 0),
				mannetje2Rectangle.getX(), mannetje2Rectangle.getY(), mainGame.getColor()));
		RND.getInstance().drawColor(new RenderOptions(graphics, 
				telefoonN.getSprite(2, 0), telefoonA.getSprite(2, 0),
				telefoon2Rectangle.getX(), telefoon2Rectangle.getY(), mainGame.getColor()));
		RND.getInstance().drawColor(new RenderOptions(graphics, 
				arieN.getSprite(2, 0), arieA.getSprite(2, 0),
				arie2Rectangle.getX(), arie2Rectangle.getY(), mainGame.getColor()));
	}

	/**
	 * Draw the color of the controls.
	 * @param graphics the Graphics object to draw things on screen.
	 */
	private void drawColorControls(Graphics graphics) {
		RND.getInstance().text(graphics, COLOR_TEXT_X, COLOR_TEXT_1_Y,
				"# Change game color manually,");
		RND.getInstance().text(graphics, COLOR_TEXT_X, COLOR_TEXT_2_Y,
				"# or let it shuffle!.");

		shuffleButton.drawColor(graphics, input, mainGame.getColor());
		redButton.drawColor(graphics, input, mainGame.getColor());
		blueButton.drawColor(graphics, input, mainGame.getColor());
		orangeButton.drawColor(graphics, input, mainGame.getColor());
		whiteButton.drawColor(graphics, input, mainGame.getColor());
		pinkButton.drawColor(graphics, input, mainGame.getColor());
		greenButton.drawColor(graphics, input, mainGame.getColor());
		yellowButton.drawColor(graphics, input, mainGame.getColor());
		
	}
	

	@Override
	public int getID() {
		return mainGame.getSettingsState();
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
