import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class MainGame extends StateBasedGame {

	///// CONFIGURATION /////
	
	static int maxFPS = 60;
	static float gravity = 0.1f;
	static float startingSpeed = 0.5f;
	static float speedStep = 0.5f;
	static int playerSpeed = 5;
	
	////////////////////////
	
	private static AppGameContainer app;
	
	
	public MainGame(String name) {
		super(name);
	}

	public static void main(String[] args) throws SlickException {
		app = new AppGameContainer(new MainGame("StateGame"));
        app.setTargetFrameRate(maxFPS);
        
        app.setDisplayMode(800, 600, false);

        app.start();

	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		
		this.addState(new StartState());
		this.addState(new GameState());
		this.addState(new GameOverState());
		this.addState(new WonState());
		
		this.enterState(0);
		
	}

}
