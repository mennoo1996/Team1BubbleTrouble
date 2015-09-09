import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This class represents the state of the win game screen.
 * @author Menno
 *
 */
public class WonState extends BasicGameState {

	private Button playAgainButton;
	private Button mainMenuButton;
	private Button exitButton;
	private MainGame mg;
	
	private static final int LOGO_X = 160;
	private static final int LOGO_Y = 110;
	private static final int SEPARATOR_X = 164;
	private static final int SEPARATOR_Y = 190;
	
	private static final int BOTTOM_TEXT_OFFSET_X = 250;
	private static final int BOTTOM_TEXT_OFFSET_Y = 75;
	
	private static final int BUTTON_WIDTH = 1000;
	private static final int BUTTON_HEIGHT = 50;
	private static final int BUTTON_X = 150;
	private static final int PLAYBUTTON_Y = 325;
	private static final int MENUBUTTON_Y = 375;
	private static final int EXITBUTTON_Y = 425;
	
	private static final int PLAY_AGAIN_BUTTON_X = 300;
	private static final int PLAY_AGAIN_BUTTON_Y = 275;
	private static final int PLAY_AGAIN_BUTTON_WIDTH = 200;
	private static final int PLAY_AGAIN_BUTTON_HEIGHT = 45;
	
	private static final int WON_STRING_X = 100;
	private static final int WON_STRING_Y = 100;
	private static final int SCORE_STRING_X = 100;
	private static final int SCORE_STRING_Y = 140;
	
	private static final int STATE_ID = 3;
	
	/**
	 * Construct a WonState.
	 * @param mg the maingame that uses this state
	 */
	public WonState(MainGame mg) {
		this.mg = mg;
	}
	
	/**
	 * Init module, load resources.
	 * @param container The gamecontainer that contains this state
	 * @param arg1 the state based game that uses this state
	 * @throws SlickException if something goes wrong
	 */
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		
		playAgainButton = new Button(BUTTON_X, PLAYBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/menus/Menu_Button_Play.png"),
				new Image("resources/menus/Menu_Button_Play2.png"));
		mainMenuButton = new Button(BUTTON_X, MENUBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/menus/Menu_Button_MainMenu.png"), 
				new Image("resources/menus/Menu_Button_MainMenu2.png"));
		exitButton = new Button(BUTTON_X, EXITBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/menus/Menu_Button_Quit.png"),
				new Image("resources/menus/Menu_Button_Quit2.png"));
		
		
		playAgainButton = new Button(PLAY_AGAIN_BUTTON_X, PLAY_AGAIN_BUTTON_Y,
				PLAY_AGAIN_BUTTON_WIDTH, PLAY_AGAIN_BUTTON_HEIGHT,
				new Image("resources/play_again_button.png"));
	}
	
	/**
	 * Update method, called every frame.
	 * @param container The container that contains this state
	 * @param sbg the state based game that uses this state
	 * @param delta the time in ms since the last frame
	 * @throws SlickException if something goes wrong
	 */
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
			Input input = container.getInput();
			
			// If mouse is pressed inside the button, go to the gameState
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				if (playAgainButton.getRectangle().contains(input.getMouseX(), input.getMouseY())) {
					mg.setLevelCounter(0);
					sbg.enterState(1);
				}
			}
		}

	/**
	 * Render method, draw things to screen.
	 * @param container the Gamecontainer that contains this state
	 * @param arg1 the state based game that uses this state
	 * @param graphics the Graphics uses to draw things
	 * @throws SlickException if something goes wrong
	 */
	public void render(GameContainer container, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		Input input = container.getInput();
		graphics.drawImage(mg.getBackgroundImage(), 0, 0);
		mg.getDosFont().drawString(container.getWidth() / 2 - BOTTOM_TEXT_OFFSET_X,
				container.getHeight() - BOTTOM_TEXT_OFFSET_Y, "Waiting for user input...");
		// draw string and button
		mg.getDosFont().drawString(WON_STRING_X, WON_STRING_Y,
				"You won, you are the champion!");
		mg.getDosFont().drawString(SCORE_STRING_X, SCORE_STRING_Y,
				"Your score was: " + mg.getScore());
		graphics.drawImage(playAgainButton.getImage(), playAgainButton.getX(),
				playAgainButton.getY());
		
		// watermark
		mg.drawWaterMark();
		
		graphics.drawImage(mg.getForeGroundImage(), 0, 0);
		graphics.drawImage(mg.getTerminalImage(), 0, 0);
		
	}

	
	/**
	 * return the id of the state.
	 */
	@Override
	public int getID() {
		return STATE_ID;
	}

	/**
	 * Get the maingame.
	 * @return the maingame of this state.
	 */
	public MainGame getMg() {
		return mg;
	}

	/**
	 * Set the maingame.
	 * @param mg the maingame to set.
	 */
	public void setMg(MainGame mg) {
		this.mg = mg;
	}
}
