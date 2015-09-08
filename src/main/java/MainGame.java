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
	
	protected float gravity = DEFAULT_GRAVITY;
	protected float startingSpeed = DEFAULT_STARTING_SPEED;
	protected float speedStep = DEFAULT_SPEED_STEP;
	protected float playerSpeed = DEFAULT_PLAYER_SPEED;
	protected float laserWidth = DEFAULT_LASER_WIDTH;
	protected float laserSpeed = DEFAULT_LASER_SPEED;
	protected static int xRes = DEFAULT_X_RES;
	protected static int yRes = DEFAULT_Y_RES;
	
	// Some often-used images
	protected Image backgroundImage;
	protected Image foreGroundImage;
	protected Image terminalImage;
	protected Image laserHorizontalImage;
	protected Image laserVerticalImage;
	protected AngelCodeFont dosFont;
	protected String playerImage;
	
	protected int score;
	
	private static final int LIVES = 5;
	protected int lifeCount;
	protected int levelCounter = 0;
	protected String highscoresFile = "resources/highscores.txt";
	protected HighScores highscores;
	
	//////////////////////// STATES //////////////
	protected final int START_STATE = 0;
	protected final int GAME_STATE = 1;
	protected final int GAMOVER_STATE = 2;
	protected final int WON_STATE = 3;
	protected final int SETTINGS_STATE = 4;
	
	
	private static AppGameContainer app;
	
	private static final int TARGET_FRAMERATE = 60;
	private static final float DEFAULT_GRAVITY = 500f;
	private static final float DEFAULT_STARTING_SPEED = 200f;
	private static final float DEFAULT_SPEED_STEP = 0.5f;
	private static final float DEFAULT_PLAYER_SPEED = 400f;
	private static final float DEFAULT_LASER_WIDTH = 3f;
	private static final float DEFAULT_LASER_SPEED = 1000f;
	
	
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
		this.laserHorizontalImage = new Image("resources/laser_horizontal.png");
		this.laserVerticalImage = new Image("resources/laser_vertical.png");
		this.dosFont = new AngelCodeFont("resources/font/dosfont.fnt",
				"resources/font/dosfont_0.png");
		
		this.enterState(START_STATE);
		
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
	
	
}
