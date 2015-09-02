import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;


public class StateGame extends StateBasedGame {

	///// CONFIGURATION /////
	
	static int maxFPS = 60;
	static float gravity = 0.1f;
	static float startingSpeed = 0.5f;
	static float speedStep = 0.5f;
	static int playerSpeed = 5;
	
	////////////////////////
	
	private static AppGameContainer app;
	
	
	public StateGame(String name) {
		super(name);
	}

	public static void main(String[] args) throws SlickException {
		app = new AppGameContainer(new StateGame("StateGame"));
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
