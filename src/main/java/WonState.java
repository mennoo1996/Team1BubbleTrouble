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

	private Button playButton;
	private Button menuButton;
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
	
	private static final int TEXT_X = 164;
	private static final int TEXT_1_Y = 238;
	private static final int TEXT_2_Y = 288;

	private static final int MOUSE_OVER_RECT_X = 500;
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
		
		playButton = new Button(BUTTON_X, PLAYBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/menus/Menu_Button_PlayAgain.png"),
				new Image("resources/menus/Menu_Button_PlayAgain2.png"));
		menuButton = new Button(BUTTON_X, MENUBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/menus/Menu_Button_MainMenu.png"), 
				new Image("resources/menus/Menu_Button_MainMenu2.png"));
		exitButton = new Button(BUTTON_X, EXITBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/menus/Menu_Button_Quit.png"),
				new Image("resources/menus/Menu_Button_Quit2.png"));
		
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
			
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				if (playButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
					// Start over
					mg.setLevelCounter(0);
					sbg.enterState(1);
				} 
				else if (menuButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
					// Go to settingsState
					mg.setLevelCounter(0);
					sbg.enterState(0);
				}
				else if (exitButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
					// Quit game
					System.exit(0);
				}
			}
			
			// If mouse is pressed inside the button, go to the gameState
//			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
//				if (playAgainButton.getRectangle().contains(input.getMouseX(), input.getMouseY())) {
//					mg.setLevelCounter(0);
//					sbg.enterState(1);
//				}
//			}
			
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
		graphics.drawImage(mg.getBackgroundImage(), 0, 0);
		mg.getDosFont().drawString(container.getWidth() / 2 - BOTTOM_TEXT_OFFSET_X,
				container.getHeight() - BOTTOM_TEXT_OFFSET_Y, "Waiting for user input...");
		// draw string and button
		mg.getDosFont().drawString(TEXT_X, TEXT_1_Y,
				"> You won, you are the champion!");
		mg.getDosFont().drawString(TEXT_X, TEXT_2_Y,
				"> Your score was: " + mg.getScore());
		renderButtons(container, graphics);
		mg.drawWaterMark();
		graphics.drawImage(mg.getGameLogo(), LOGO_X, LOGO_Y);
		mg.getDosFont().drawString(SEPARATOR_X, SEPARATOR_Y, "========================");
		graphics.drawImage(mg.getForeGroundImage(), 0, 0);
		graphics.drawImage(mg.getTerminalImage(), 0, 0);
	}

	/**
	 * Method renders buttons in WonState to screen.
	 * @param container appgamecontainer used
	 * @param graphics graphics context used
	 */
	private void renderButtons(GameContainer container, Graphics graphics) {
		Input input = container.getInput();
		if (playButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
			graphics.drawImage(playButton.getImageMouseOver(), playButton.getX(), 
					playButton.getY());
		} else {
			graphics.drawImage(playButton.getImage(), playButton.getX(), playButton.getY());
		}
		if (menuButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
			graphics.drawImage(menuButton.getImageMouseOver(), menuButton.getX(),
					menuButton.getY());
		} else {
			graphics.drawImage(menuButton.getImage(), menuButton.getX(), 
					menuButton.getY());
		}
		if (exitButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
			graphics.drawImage(exitButton.getImageMouseOver(), exitButton.getX(), 
					exitButton.getY());
		} else {
			graphics.drawImage(exitButton.getImage(), exitButton.getX(), exitButton.getY());
		}
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
