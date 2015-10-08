package gui;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lan.Client;
import lan.Host;
import logic.HighScores;
import logic.HighScoresParser;
import logic.Logger;
import logic.Player;
import logic.PlayerList;
import logic.ShutDownHook;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The main game object - basically the overall control system.
 * @author Menno
 *
 */
public class MainGame extends StateBasedGame {

	///// CONFIGURATION /////
	
	// Speeds in pixels per second
	private static final int DEFAULT_X_RES = 1600;
	private static final int DEFAULT_Y_RES = 1000;
	
	private Color color;
	private Color nextColor;
	
	// Game Colors
	private boolean shuffleColors = false;
	private static final int COLOR_COUNT = 6;
	private static final Color COLOR_RED = new Color(0.8f, 0.15f, 0.0f);
	private static final Color COLOR_ORANGE = new Color(1.0f, 0.4f, 0.1f);
	private static final Color COLOR_GREEN = new Color(0.35f, 0.6f, 0.05f);
	private static final Color COLOR_BLUE = new Color(0.15f, 0.5f, 0.8f);
	private static final Color COLOR_PINK = new Color(0.85f, 0.0f, 0.4f);
	private static final Color COLOR_WHITE = new Color(0.5f, 0.5f, 0.5f);

	private static final int NUM_3 = 3;
	private static final int NUM_4 = 4;
	private static final int NUM_5 = 5;
	private static final int NUM_6 = 6;
	
	private float gravity = DEFAULT_GRAVITY;
	private float startingSpeed = DEFAULT_STARTING_SPEED;
	private float speedStep = DEFAULT_SPEED_STEP;
	private float playerSpeed = DEFAULT_PLAYER_SPEED;
	private float laserWidth = DEFAULT_LASER_WIDTH;
	private float laserSpeed = DEFAULT_LASER_SPEED;
	private static int xRes = DEFAULT_X_RES;
	private static int yRes = DEFAULT_Y_RES;
	
	// Some often-used images
	private String player1ImageStringN;
	private String player1ImageStringA;
	private String player2ImageStringN;
	private String player2ImageStringA;
	
	private PlayerList playerList;
	private boolean multiplayer;
	private String currentDate;
	
	private int score;
	
	private static final int LIVES = 5;
	private int lifeCount;
	private int levelCounter = 0;
	private String highscoresFile = "resources/highscores.txt";
	private HighScores highscores;
	
	//////////////////////// STATES //////////////
	private static  final int MENU_START_STATE = 0;
	private static final int GAME_STATE = 1;
	private static final int MENU_GAMEOVER_STATE = 2;
	private static final int MENU_SETTINGS_STATE = 3;
	private static final int MENU_MULTIPLAYER_STATE = 4;
	
	private GameState gameStateState;
	private MenuSettingsState menuSettingsState;
	private MenuMainState menuMainState;
	private MenuGameoverState menuGameoverState;
	private MenuMultiplayerState menuMultiplayerState;
	
	private boolean shouldSwitchState = false;
	private int switchState = 0;
	
	private static AppGameContainer app;
	private GameContainer container;
	
	private static final int OPACITY_FADE_TIMER = 200;
	private static final int TARGET_FRAMERATE = 60;
	private static final float DEFAULT_GRAVITY = 500f;
	private static final float DEFAULT_STARTING_SPEED = 200f;
	private static final float DEFAULT_SPEED_STEP = 0.5f;
	private static final float DEFAULT_PLAYER_SPEED = 400f;
	private static final float DEFAULT_LASER_WIDTH = 3f;
	private static final float DEFAULT_LASER_SPEED = 1000f;
	private static final int PLAYER1_X_DEVIATION = 80;
	private static final int PLAYER2_X_DEVIATION = 380;
	private static final int PLAYER_Y_DEVIATION = 295;
	private static final int PLAYER_WIDTH = 60;
	private static final int PLAYER_HEIGHT = 92;
	private static final int VERSION_STRING_X = 164;
	private static final int VERSION_STRING_Y_DEVIATION = 190;
	private static final int MULTIPLAYER_PORT = 4455;
	private static final String STATES = "States";
	private static final String PLAYER_IMAGE_PATH = "resources/images_Player/";
	private boolean lanMultiplayer;
	private Host host;
	private Client client;
	private boolean isHost;
	private boolean isClient;
	private ExecutorService multiplayerThreadManager;


	/**
	 * Constructor.
	 * @param name	- name of mainGame
	 * @throws SlickException 
	 */
	public MainGame(String name) {
		super(name);
		
		Logger.getInstance().setConsoleLoggingOn(false);
		this.player1ImageStringN = "Playersprite_Norm.png";
		this.player1ImageStringA = "Playersprite_Add.png";
		this.player2ImageStringN = "Player2sprite_Norm.png";
		this.player2ImageStringA = "Player2sprite_Add.png";
		this.lifeCount = LIVES;
		this.setColor(COLOR_GREEN);
		this.setNextColor(COLOR_GREEN);
		this.highscores = HighScoresParser.readHighScores(highscoresFile);
		this.multiplayer = false;
		this.lanMultiplayer = false;
		this.isHost = false;
		this.isClient = false;
		

		ShutDownHook shutDownHook = new ShutDownHook(this);
		shutDownHook.attachShutDownHook();
	}

	/**
	 * Exit the LAN multiplayer properly.
	 */
	public void closeLANMultiplayer() {
		if (lanMultiplayer) {
			try {
				getHost().shutdown();
			} catch (InterruptedException ec) {
				ec.printStackTrace();
			}
		}
	}

	/**
	 * Get the playerImage_norm.
	 * @return the playerImage
	 */
	public String getPlayer1ImageStringN() {
		return player1ImageStringN;
	}
	
	/**
	 * Get the playerImage_add.
	 * @return the playerImage
	 */
	public String getPlayer1ImageStringA() {
		return player1ImageStringA;
	}

	/**
	 * Set the playerImage.
	 * @param playerImageStringN the playerImage_norm to set
	 * @param playerImageStringA the playerImage_add to set
	 */
	public void setPlayer1ImageString(String playerImageStringN, String playerImageStringA) {
		this.player1ImageStringN = playerImageStringN;
		this.player1ImageStringA = playerImageStringA;
	}

	/**
	 * @return the player2ImageString_norm
	 */
	public String getPlayer2ImageStringN() {
		return player2ImageStringN;
	}
	
	/**
	 * @return the player2ImageString_add
	 */
	public String getPlayer2ImageStringA() {
		return player2ImageStringA;
	}

	/**
	 * @param player2ImageStringN the player2ImageString_Norm to set
	 * @param player2ImageStringA the player2ImageString_Add to set
	 */
	public void setPlayer2ImageString(String player2ImageStringN, String player2ImageStringA) {
		this.player2ImageStringN = player2ImageStringN;
		this.player2ImageStringA = player2ImageStringA;
	}

	/**
	 * @return the current game color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * @param color the new game color
	 */
	public void setColor(Color color) {
		this.color = color;
		RND.setColor(color);
	}
	
	/**
	 * @param shuffle whether to shuffle colors.
	 */
	public void shuffleColor(boolean shuffle) {
		this.shuffleColors = shuffle;
	}
	
	/**
	 * @param color the next color for the game to use
	 */
	public void setNextColor(Color color) {
		this.nextColor = color;
	}
	
	/**
	 * Tell the game to update to new color.
	 */
	public void switchColor() {
		this.color = nextColor;
		if (shuffleColors) { // shuffle
			shuffleColor();
		}
		String logString = "Color changed to " + this.color.toString();
		Logger.getInstance().log(logString, Logger.PriorityLevels.MEDIUM, "color");
		RND.setColor(color);
	}
	
	/**
	 * Sets a shuffled color for the game by RND. If the color returned is the same as before,
	 * another color is force-picked.
	 */
	public void shuffleColor() {
		switch ((int) Math.round(Math.random() * COLOR_COUNT)) {
		case 1: if (this.color == COLOR_BLUE) {
			this.color = COLOR_ORANGE; } else {
				this.color = COLOR_BLUE; } break;
		case 2: if (this.color == COLOR_ORANGE) {
			this.color = COLOR_GREEN; } else {
				this.color = COLOR_ORANGE; } break;
		case NUM_3: if (this.color == COLOR_RED) {
			this.color = COLOR_BLUE; } else {
				this.color = COLOR_RED; } break;
		case NUM_4: if (this.color == COLOR_GREEN) {
			this.color = COLOR_PINK; } else {
				this.color = COLOR_GREEN; } break;
		case NUM_5: if (this.color == COLOR_WHITE) {
			this.color = COLOR_RED; } else {
				this.color = COLOR_WHITE; } break;
		case NUM_6: if (this.color == COLOR_PINK) {
			this.color = COLOR_WHITE; } else {
				this.color = COLOR_PINK; } break;
		default: break;
		}
	}
	
	/**
	 * Set the lifeCount.
	 * @param lifeCount the lifeCount to set
	 */
	public void setLifeCount(int lifeCount) {
		this.lifeCount = lifeCount;
	}

	/**
	 * Main function, starting the game happens in here.
	 * 
	 * @param args for command line arguments - not used
	 * @throws SlickException when something goes wrong
	 */
	public static void main(String[] args) throws SlickException {
		app = new AppGameContainer(new MainGame("StateGame"));
		app.setAlwaysRender(true);
		app.setDisplayMode(xRes, yRes, false);
		app.setVSync(true);
		app.setTargetFrameRate(TARGET_FRAMERATE);
		app.setShowFPS(false);
		//app.setMaximumLogicUpdateInterval(10); // Do not touch this - Mark
		app.start();
		app.setSmoothDeltas(true);
	}

	/**
	 * InitStateLists here.
	 * Add all states and start the first one
	 */
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.container = container;
		this.gameStateState = new GameState(this);
		Logger.getInstance().log("GameState initialized", Logger.PriorityLevels.LOW, STATES);
		this.menuSettingsState = new MenuSettingsState(this);
		Logger.getInstance().log("MenuSettingsState initialized",
				Logger.PriorityLevels.LOW, STATES);
		this.menuMainState = new MenuMainState(this);
		Logger.getInstance().log("MenuMainState initialized", Logger.PriorityLevels.LOW, STATES);
		this.menuGameoverState = new MenuGameoverState(this);
		Logger.getInstance().log("MenuGameoverState initialized", 
				Logger.PriorityLevels.LOW, STATES);
		this.menuMultiplayerState = new MenuMultiplayerState(this, gameStateState);
		Logger.getInstance().log("MenuMultiplayerState initialized", 
				Logger.PriorityLevels.LOW, STATES);
		this.addState(menuMainState);
		Logger.getInstance().log("MenuMainstate added", Logger.PriorityLevels.LOW, STATES);
		this.addState(gameStateState);
		Logger.getInstance().log("GameState added", Logger.PriorityLevels.LOW, STATES);
		this.addState(menuGameoverState);
		Logger.getInstance().log("MenuGameoverState added", Logger.PriorityLevels.LOW, STATES);
		this.addState(menuSettingsState);
		Logger.getInstance().log("MenuSettingsstate added", Logger.PriorityLevels.LOW, STATES);
		this.addState(menuMultiplayerState);
		Logger.getInstance().log("MenuMultiplayerState added", Logger.PriorityLevels.LOW, STATES);
		RND.init();		initPlayers();
		Calendar cal = Calendar.getInstance();
		this.currentDate = cal.get(Calendar.DATE) + "/" + cal.get(Calendar.MONTH) 
				+ "/" + cal.get(Calendar.YEAR);
	}
	
	/**
	 * Initalize the players.
	 * @throws SlickException if something goes wrong / file not found
	 */
	private void initPlayers() throws SlickException {

		Image player1ImageN = new Image(PLAYER_IMAGE_PATH + player1ImageStringN);
		Image player1ImageA = new Image(PLAYER_IMAGE_PATH + player1ImageStringA);
		Image player2ImageN = new Image(PLAYER_IMAGE_PATH + player2ImageStringN);
		Image player2ImageA = new Image(PLAYER_IMAGE_PATH + player2ImageStringA);
		Image shieldImageN = new Image("resources/images_Gameplay/shield_Norm.png");
		Image shieldImageA = new Image("resources/images_Gameplay/shield_Add.png");
		
		Player player1 = new Player(container.getWidth() / 2 - PLAYER1_X_DEVIATION,
				container.getHeight() - PLAYER_Y_DEVIATION, PLAYER_WIDTH, PLAYER_HEIGHT,
				player1ImageN, player1ImageA, shieldImageN, shieldImageA, this);
		player1.setPlayerNumber(0);
		
		Player player2 = new Player(container.getWidth() / 2 - PLAYER2_X_DEVIATION,
				container.getHeight() - PLAYER_Y_DEVIATION, PLAYER_WIDTH, PLAYER_HEIGHT,
				player2ImageN, player2ImageA, shieldImageN, shieldImageA, this);
		player2.setPlayerNumber(1);
		
		playerList = new PlayerList(player1, this, gameStateState);
		playerList.add(player2);
	}
	
	/**
	 * Set the levelCounter.
	 * @param levelCounter the levelCounter to set.
	 */
	public void setLevelCounter(int levelCounter) {
		this.levelCounter = levelCounter;
	}

	/**
	 * Get the levelCounter.
	 * @return the levelCounter
	 */
	public int getLevelCounter() {
		return levelCounter;
	}

	/**
	 * Decrease the life count.
	 */
	public void decreaselifeCount() {
		lifeCount = lifeCount - 1;
	}

	/**
	 * Reset the life count.
	 */
	public void resetLifeCount() {
		lifeCount = LIVES;
	}
	
	/**
	 * Get the life count.
	 * @return the lifeCount
	 */
	public int getLifeCount() {
		return lifeCount;
	}
	
	/**
	 * Reset the level count.
	 */
	public void resetLevelCount() {
		levelCounter = 0;
	}

	/**
	 * @return the gravity
	 */
	public float getGravity() {
		return gravity;
	}

	/**
	 * @param gravity the gravity to set
	 */
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	/**
	 * @return the startingSpeed
	 */
	public float getStartingSpeed() {
		return startingSpeed;
	}

	/**
	 * @param startingSpeed the startingSpeed to set
	 */
	public void setStartingSpeed(float startingSpeed) {
		this.startingSpeed = startingSpeed;
	}

	/**
	 * @return the speedStep
	 */
	public float getSpeedStep() {
		return speedStep;
	}

	/**
	 * @param speedStep the speedStep to set
	 */
	public void setSpeedStep(float speedStep) {
		this.speedStep = speedStep;
	}

	/**
	 * @return the playerSpeed
	 */
	public float getPlayerSpeed() {
		return playerSpeed;
	}

	/**
	 * @param playerSpeed the playerSpeed to set
	 */
	public void setPlayerSpeed(float playerSpeed) {
		this.playerSpeed = playerSpeed;
	}

	/**
	 * @return the laserWidth
	 */
	public float getLaserWidth() {
		return laserWidth;
	}

	/**
	 * @param laserWidth the laserWidth to set
	 */
	public void setLaserWidth(float laserWidth) {
		this.laserWidth = laserWidth;
	}

	/**
	 * @return the laserSpeed
	 */
	public float getLaserSpeed() {
		return laserSpeed;
	}

	/**
	 * @param laserSpeed the laserSpeed to set
	 */
	public void setLaserSpeed(float laserSpeed) {
		this.laserSpeed = laserSpeed;
	}

	/**
	 * @return the xRes
	 */
	public static int getxRes() {
		return xRes;
	}

	/**
	 * @param xRes the xRes to set
	 */
	public static void setxRes(int xRes) {
		MainGame.xRes = xRes;
	}

	/**
	 * @return the yRes
	 */
	public static int getyRes() {
		return yRes;
	}

	/**
	 * @param yRes the yRes to set
	 */
	public static void setyRes(int yRes) {
		MainGame.yRes = yRes;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the highscoresFile
	 */
	public String getHighscoresFile() {
		return highscoresFile;
	}

	/**
	 * @param highscoresFile the highscoresFile to set
	 */
	public void setHighscoresFile(String highscoresFile) {
		this.highscoresFile = highscoresFile;
	}

	/**
	 * @return the highscores
	 */
	public HighScores getHighscores() {
		return highscores;
	}

	/**
	 * @param highscores the highscores to set
	 */
	public void setHighscores(HighScores highscores) {
		this.highscores = highscores;
	}

	/**
	 * @return the app
	 */
	public static AppGameContainer getApp() {
		return app;
	}

	/**
	 * @param app the app to set
	 */
	public static void setApp(AppGameContainer app) {
		MainGame.app = app;
	}

	/**
	 * @return the defaultXRes
	 */
	public static int getDefaultXRes() {
		return DEFAULT_X_RES;
	}

	/**
	 * @return the defaultYRes
	 */
	public static int getDefaultYRes() {
		return DEFAULT_Y_RES;
	}

	/**
	 * @return the lives
	 */
	public static int getLives() {
		return LIVES;
	}

	
	/**
	 * @return the startState
	 */
	public int getStartState() {
		return MENU_START_STATE;
	}

	/**
	 * @return the gameState
	 */
	public int getGameState() {
		return GAME_STATE;
	}

	/**
	 * @return the gameOverState
	 */
	public int getGameOverState() {
		return MENU_GAMEOVER_STATE;
	}

	/**
	 * @return the settingsState
	 */
	public int getSettingsState() {
		return MENU_SETTINGS_STATE;
	}
	
	/**
	 * @return the multiplayerState
	 */
	public int getMultiplayerState() {
		return MENU_MULTIPLAYER_STATE;
	}

	/**
	 * @return the targetFramerate
	 */
	public static int getTargetFramerate() {
		return TARGET_FRAMERATE;
	}

	/**
	 * @return the defaultGravity
	 */
	public static float getDefaultGravity() {
		return DEFAULT_GRAVITY;
	}

	/**
	 * @return the defaultStartingSpeed
	 */
	public static float getDefaultStartingSpeed() {
		return DEFAULT_STARTING_SPEED;
	}

	/**
	 * @return the defaultSpeedStep
	 */
	public static float getDefaultSpeedStep() {
		return DEFAULT_SPEED_STEP;
	}

	/**
	 * @return the defaultPlayerSpeed
	 */
	public static float getDefaultPlayerSpeed() {
		return DEFAULT_PLAYER_SPEED;
	}

	/**
	 * @return the defaultLaserWidth
	 */
	public static float getDefaultLaserWidth() {
		return DEFAULT_LASER_WIDTH;
	}

	/**
	 * @return the defaultLaserSpeed
	 */
	public static float getDefaultLaserSpeed() {
		return DEFAULT_LASER_SPEED;
	}
	
	/**
	 * @return the fps
	 */
	public int getFpsInGame() {
		return app.getFPS();
	}
	
	/**
	 * Draws version number, fps, and other info.
	 */
	public void drawWaterMark() {
		RND.text(app.getGraphics(), VERSION_STRING_X, app.getHeight() - VERSION_STRING_Y_DEVIATION,
				"#Version 1.0" + " #Date: " + currentDate 
				+ " #fps: " + Integer.toString(getFpsInGame()));
	}
	
	/**
	 * @return the multiplayer
	 */
	public boolean isMultiplayer() {
		return multiplayer;
	}

	/**
	 * @param multiplayer the multiplayer to set
	 */
	public void setMultiplayer(boolean multiplayer) {
		this.multiplayer = multiplayer;
	}

	/**
	 * @return the container
	 */
	public GameContainer getContainer() {
		return container;
	}

	/**
	 * @param container the container to set
	 */
	public void setContainer(GameContainer container) {
		this.container = container;
	}

	/**
	 * @return the playerList
	 */
	public PlayerList getPlayerList() {
		return playerList;
	}

	/**
	 * @param playerList the playerList to set
	 */
	public void setPlayerList(PlayerList playerList) {
		this.playerList = playerList;
	}

	
	
	/**
	 * @return the opacity fade timer, which should be used for scene transitions.
	 */
	public int getOpacityFadeTimer() {
		return OPACITY_FADE_TIMER;
	}
	
	/**
	 * Force game to switch state.
	 * @param state to switch to
	 */
	public void setSwitchState(int state) {
		String stateString = "Showing UI-transition to ";
		switch (state) {
		case (-1):
			stateString = "Showing UI-transition to exit application.";
			break;
		case (0):
			stateString += "StartState";
			break;
		case (1):
			stateString += "GameState";
			break;
		case (2):
			stateString += "GameOverState";
			break;
		case (2 + 1):
			stateString += "SettingsState";	
			break;
		case (2 + 2):
			stateString += "MultiplayerState";	
			break;
		default:
			break;
		}
		
		Logger.getInstance().log(stateString, Logger.PriorityLevels.VERYLOW, "GUI");
		shouldSwitchState = true;
		switchState = state;
	}
	
	
	
	/**
	 * Force game to stop switching state.
	 */
	public void stopSwitchState() {
		shouldSwitchState = false;
	}
	
	/**
	 * @return whether game should switch state.
	 */
	public boolean getShouldSwitchState() {
		return shouldSwitchState;
	}
	
	/**
	 * @return the state the game is switching to.
	 */
	public int getSwitchState() {
		return switchState;
	}

	/**
	 * @return the Host object for this game.
	 */
	public Host getHost() {
		return host;
	}

	/**
	 * @return the multiplayerPort
	 */
	public static int getMultiplayerPort() {
		return MULTIPLAYER_PORT;
	}

	/**
	 * Sets and starts the host.
	 * @param host the host to set
	 */
	public void spawnHost(Host host) {
		this.host = host;
		multiplayerThreadManager = Executors.newFixedThreadPool(1);
		multiplayerThreadManager.submit(this.host);
	}

	/**
	 * @return the lanMultiplayer
	 */
	public boolean isLanMultiplayer() {
		return lanMultiplayer;
	}

	/**
	 * @param lanMultiplayer the lanMultiplayer to set
	 */
	public void setLanMultiplayer(boolean lanMultiplayer) {
		this.lanMultiplayer = lanMultiplayer;
	}

	/**
	 * @return the isHost
	 */
	public boolean isHost() {
		return isHost;
	}

	/**
	 * @param isHost the isHost to set
	 */
	public void setIsHost(boolean isHost) {
		this.isHost = isHost;
	}

	/**
	 * @return the isHost
	 */
	public boolean isClient() {
		return isClient;
	}

	/**
	 * @param isClient the isClient to set
	 */
	public void setIsClient(boolean isClient) {
		this.isClient = isClient;
	}

	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * Sets and starts client.
	 * @param client the client to set
	 */
	public void spawnClient(Client client) {
		this.client = client;
		this.multiplayerThreadManager = Executors.newFixedThreadPool(1);
		this.multiplayerThreadManager.submit(this.client);
	}

	/**
	 * Kill any multiplayer socket connections running.
	 */
	public void killMultiplayer() {
		System.out.println("Killing multiplayer");
		if (this.lanMultiplayer) {
			if (isHost) {
				try {
					host.shutdown();
				} catch (InterruptedException e) {
					System.out.println(e);
				}
				this.isHost = false;
			} else {
				try {
					client.shutdown();
				} catch (InterruptedException e) {
					System.out.println(e);
				}
				this.isClient = false;
			}
			this.lanMultiplayer = false;
			this.multiplayerThreadManager.shutdownNow();
		}
	}
	
}

