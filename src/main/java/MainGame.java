import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class MainGame extends StateBasedGame {

	///// CONFIGURATION /////
	
	protected float gravity = 0.1f;
	protected float startingSpeed = 0.5f;
	protected float speedStep = 0.5f;
	protected int playerSpeed = 5;
	protected float laserWidth = 3f;
	protected int laserSpeed = 10;
	
	private static int maxFPS = 60;
	
	////////////////////////
	
	private static AppGameContainer app;
	
	/**
	 * Constructor
	 * @param name	- name of mainGame
	 */
	public MainGame(String name) {
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
        app.setTargetFrameRate(maxFPS);
        
        app.setDisplayMode(800, 600, false);

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
		this.addState(new GameOverState());
		this.addState(new WonState());
		
		this.enterState(0);
		
	}

}
