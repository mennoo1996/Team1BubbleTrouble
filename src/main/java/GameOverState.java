import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Class that represents the state of the game when the game is over.
 * @author Menno
 *
 */
public class GameOverState extends BasicGameState {

	private Button playAgainButton;
	private MainGame mg;
	
	private static final int PLAY_AGAIN_BUTTON_X = 300;
	private static final int PLAY_AGAIN_BUTTON_Y = 275;
	private static final int PLAY_AGAIN_BUTTON_WIDTH = 200;
	private static final int PLAY_AGAIN_BUTTON_HEIGHT = 45;
	
	private static final int GAME_OVER_X = 100;
	private static final int GAME_OVER_Y = 100;
	private static final int POINTS_X = 100;
	private static final int POINTS_Y = 120;
	
	
	/**
	 * Constructor.
	 * @param mg the maingame in which this state will be used.
	 */
	public GameOverState(MainGame mg) {
		this.mg = mg;
	}
	
	/**
	 * Init method - load resources here.
	 * @param container The container this state should be initialized in.
	 * @param arg1 the StateBasedGame this state is in.
	 * @throws SlickException idk when
	 */
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		playAgainButton = new Button(PLAY_AGAIN_BUTTON_X, PLAY_AGAIN_BUTTON_Y,
				PLAY_AGAIN_BUTTON_WIDTH, PLAY_AGAIN_BUTTON_HEIGHT,
				new Image("resources/play_again_button.png"));
	}
	
	/**
	 * update method - called every frame.
	 * @param container The container this state is in.
	 * @param sbg the StateBasedGame this state is in
	 * @param delta the time in ms since the last frame
	 * @throws SlickException sometimes.
	 */
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
			Input input = container.getInput();
			
			// If the mouse is pressed inside the playAgainButton, enter the gameState
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				if (playAgainButton.getRectangle().contains(input.getMouseX(), input.getMouseY())) {
					mg.resetLifeCount();
					mg.resetLevelCount();
					sbg.enterState(1);
				}
			}
		}

	/**
	 * Render method - draw things to screen.
	 * @param container The container this state is in
	 * @param arg1 the statebasedgame this state is in.
	 * @param graphics the Graphics used in this gameoverstate.
	 * @throws SlickException idk when
	 */
	public void render(GameContainer container, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		// draw string and button
		graphics.drawString("Game Over Sucker!", GAME_OVER_X, GAME_OVER_Y);
		graphics.drawString("Youre score was: " + mg.score, POINTS_X, POINTS_Y);
		graphics.drawImage(playAgainButton.getImage(), playAgainButton.getX(),
				playAgainButton.getY());
	}

	
	/**
	 * returns id of the state.
	 */
	@Override
	public int getID() {
		return 2;
	}
}
