import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class MainGame extends StateBasedGame {

	///// CONFIGURATION /////
	
	// Speeds in pixels per second
	protected float gravity = 500f;
	protected float startingSpeed = 200f; 
	protected float speedStep = 0.5f;
	protected float playerSpeed = 400f;
	protected float laserWidth = 3f;
	protected float laserSpeed = 1000f;
	protected static int xRes = 1600;
	protected static int yRes = 1000;
	
	// Some environment images
	protected Image backgroundImage;
	protected Image foreGroundImage;
	protected Image terminalImage;
	protected Image versiontextImage;
	protected Image[] numberImages;
	protected Image laserHorizontalImage;
	protected Image laserVerticalImage;
	protected String playerImage;
	
	protected int score;
	
	private static int LIVES = 5;
	protected int lifeCount;
	protected int levelCounter = 0;
	
	//////////////////////// STATES //////////////
	protected final int START_STATE = 0;
	protected final int GAME_STATE = 1;
	protected final int GAMOVER_STATE = 2;
	protected final int WON_STATE = 3;
	protected final int SETTINGS_STATE = 4;
	
	private static AppGameContainer app;
	
	/**
	 * Constructor
	 * @param name	- name of mainGame
	 * @throws SlickException 
	 */
	public MainGame(String name) {
		super(name);
		this.playerImage = "mannetje.png";
		this.lifeCount = LIVES;
	}

	public String getPlayerImage() {
		return playerImage;
	}

	public void setPlayerImage(String playerImage) {
		this.playerImage = playerImage;
	}

	public void setLifeCount(int lifeCount) {
		this.lifeCount = lifeCount;
	}

	/**
	 * Main function, starting the game happens in here
	 * 
	 * @param args
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException {
		app = new AppGameContainer(new MainGame("StateGame"));
		app.setDisplayMode(xRes, Math.round(yRes), false);
		app.setVSync(true);
		app.setTargetFrameRate(60);
		//app.setMaximumLogicUpdateInterval(10); // Do not touch this - Mark
		app.start();
		app.setSmoothDeltas(true);
	}

	/**
	 * InitStateLists here
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
		this.versiontextImage = new Image("resources/text/text_version.png");
		this.numberImages = new Image[10];
		for(int i = 0; i < 10; i++) {
			this.numberImages[i] = new Image("resources/numbers/" + Integer.toString(i) + ".png");
		}
		
		this.enterState(START_STATE);
		
	}
	
	public void setLevelCounter(int levelCounter) {
		this.levelCounter = levelCounter;
	}

	public int getLevelCounter() {
		return levelCounter;
	}

	public void decreaselifeCount() {
		lifeCount = lifeCount -1;
	}

	public void resetLifeCount() {
		lifeCount = LIVES;
	}
	
	public int getLifeCount() {
		return lifeCount;
	}
	
	public void resetLevelCount() {
		levelCounter = 0;
	}
	
	
}
