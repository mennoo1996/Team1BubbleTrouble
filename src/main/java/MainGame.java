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
	protected float laserSpeed = 1600f;
	protected static int xRes = 1600;
	protected static int yRes = 1000;
	protected Image backgroundImage;
	
	protected int score;
	
	////////////////////////
	
	private static AppGameContainer app;
	
	/**
	 * Constructor
	 * @param name	- name of mainGame
	 * @throws SlickException 
	 */
	public MainGame(String name) throws SlickException {
		super(name);
	}

	/**
	 * Main function, starting the game happens in here
	 * 
	 * @param args
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException {
		app = new AppGameContainer(new MainGame("StateGame"));
        app.setDisplayMode(xRes, yRes, false);
		app.start();
	}

	/**
	 * InitStateLists here
	 * Add all states and start the first one
	 */
	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		
		this.addState(new StartState());
		this.addState(new GameState(this));
		this.addState(new GameOverState(this));
		this.addState(new WonState(this));
		
		this.backgroundImage = new Image("resources/grid.png");
		
		this.enterState(0);
		
	}

}
