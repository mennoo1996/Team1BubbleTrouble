import java.util.Calendar;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
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
	
	private float gravity = DEFAULT_GRAVITY;
	private float startingSpeed = DEFAULT_STARTING_SPEED;
	private float speedStep = DEFAULT_SPEED_STEP;
	private float playerSpeed = DEFAULT_PLAYER_SPEED;
	private float laserWidth = DEFAULT_LASER_WIDTH;
	private float laserSpeed = DEFAULT_LASER_SPEED;
	private static int xRes = DEFAULT_X_RES;
	private static int yRes = DEFAULT_Y_RES;
	
	// Some often-used images
	private Image backgroundImage;
	private Image foreGroundImage;
	private Image terminalImage;
	private Image gameLogo;
	private Image laserHorizontalImage;
	private Image laserVerticalImage;
	private AngelCodeFont dosFont;
	private String playerImage;
	private String currentDate;
	
	private int score;
	
	private static final int LIVES = 5;
	private int lifeCount;
	private int levelCounter = 0;
	private String highscoresFile = "resources/highscores.txt";
	private HighScores highscores;
	
	//////////////////////// STATES //////////////
	private final int startState = 0;
	private final int gameState = 1;
	private final int gameOverState = 2;
	private final int wonState = 3;
	private final int settingsState = 4;
	
	
	private static AppGameContainer app;
	
	private static final int TARGET_FRAMERATE = 60;
	private static final float DEFAULT_GRAVITY = 500f;
	private static final float DEFAULT_STARTING_SPEED = 200f;
	private static final float DEFAULT_SPEED_STEP = 0.5f;
	private static final float DEFAULT_PLAYER_SPEED = 400f;
	private static final float DEFAULT_LASER_WIDTH = 3f;
	private static final float DEFAULT_LASER_SPEED = 1000f;
	private static final int VERSION_STRING_X = 164;
	private static final int VERSION_STRING_Y_DEVIATION = 190;
	
	
	/**
	 * Constructor.
	 * @param name	- name of mainGame
	 * @throws SlickException 
	 */
	public MainGame(String name) {
		super(name);
		this.playerImage = "Playersprite.png";
		this.lifeCount = LIVES;
		this.highscores = HighScoresParser.readHighScores(highscoresFile);
	}

	/**
	 * Get the playerImage.
	 * @return the playerImage
	 */
	public String getPlayerImage() {
		return playerImage;
	}

	/**
	 * Set the playerImage.
	 * @param playerImage the playerImage to set
	 */
	public void setPlayerImage(String playerImage) {
		this.playerImage = playerImage;
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
		app.setDisplayMode(xRes, Math.round(yRes), false);
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
	public void initStatesList(GameContainer arg0) throws SlickException {
		
		this.addState(new StartState(this));
		this.addState(new GameState(this));
		this.addState(new GameOverState(this));
		this.addState(new WonState(this));
		this.addState(new SettingsState(this));
		
		this.backgroundImage = new Image("resources/terminal/Screen_Underlayer.png");
		this.foreGroundImage = new Image("resources/terminal/Screen_Overlayer.png");
		this.terminalImage = new Image("resources/terminal/Terminal_Base.png");
		this.gameLogo = new Image("resources/menus/Menu_Logo.png");
		this.laserHorizontalImage = new Image("resources/laser_horizontal.png");
		this.laserVerticalImage = new Image("resources/laser_vertical.png");
		this.dosFont = new AngelCodeFont("resources/font/dosfont.fnt",
				"resources/font/dosfont_0.png");
		
		Calendar cal = Calendar.getInstance();
		this.currentDate = cal.get(Calendar.DATE) 
				+ "/" + cal.get(Calendar.MONTH) 
				+ "/" + cal.get(Calendar.YEAR);
		
		this.enterState(startState);
		
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
	 * @return the backgroundImage
	 */
	public Image getBackgroundImage() {
		return backgroundImage;
	}
	
	/**
	 * @return the game logo image
	 */
	public Image getGameLogo() {
		return gameLogo;
	}

	/**
	 * @param backgroundImage the backgroundImage to set
	 */
	public void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	/**
	 * @return the foreGroundImage
	 */
	public Image getForeGroundImage() {
		return foreGroundImage;
	}

	/**
	 * @param foreGroundImage the foreGroundImage to set
	 */
	public void setForeGroundImage(Image foreGroundImage) {
		this.foreGroundImage = foreGroundImage;
	}

	/**
	 * @return the terminalImage
	 */
	public Image getTerminalImage() {
		return terminalImage;
	}

	/**
	 * @param terminalImage the terminalImage to set
	 */
	public void setTerminalImage(Image terminalImage) {
		this.terminalImage = terminalImage;
	}

	/**
	 * @return the laserHorizontalImage
	 */
	public Image getLaserHorizontalImage() {
		return laserHorizontalImage;
	}

	/**
	 * @param laserHorizontalImage the laserHorizontalImage to set
	 */
	public void setLaserHorizontalImage(Image laserHorizontalImage) {
		this.laserHorizontalImage = laserHorizontalImage;
	}

	/**
	 * @return the laserVerticalImage
	 */
	public Image getLaserVerticalImage() {
		return laserVerticalImage;
	}

	/**
	 * @param laserVerticalImage the laserVerticalImage to set
	 */
	public void setLaserVerticalImage(Image laserVerticalImage) {
		this.laserVerticalImage = laserVerticalImage;
	}

	/**
	 * @return the dosFont
	 */
	public AngelCodeFont getDosFont() {
		return dosFont;
	}

	/**
	 * @param dosFont the dosFont to set
	 */
	public void setDosFont(AngelCodeFont dosFont) {
		this.dosFont = dosFont;
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
		return startState;
	}

	/**
	 * @return the gameState
	 */
	public int getGameState() {
		return gameState;
	}

	/**
	 * @return the gameOverState
	 */
	public int getGameOverState() {
		return gameOverState;
	}

	/**
	 * @return the wonState
	 */
	public int getWonState() {
		return wonState;
	}

	/**
	 * @return the settingsState
	 */
	public int getSettingsState() {
		return settingsState;
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
		dosFont.drawString(VERSION_STRING_X, app.getHeight() - VERSION_STRING_Y_DEVIATION, 
				"#Version 1.0"  
				+ " #Date: " + currentDate
				+ " #fps: " + Integer.toString(getFpsInGame())
				);
	}
	
}
