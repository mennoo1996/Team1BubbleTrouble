import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This class represents the state of the start menu.
 * @author Menno
 *
 */
public class StartState extends BasicGameState {

	
	private MainGame mg;
	private Button playButton;

	private Button optionsButton;
	private Button quitButton;
	
	private static final int LOGO_X = 160;
	private static final int LOGO_Y = 110;
	private static final int SEPARATOR_X = 164;
	private static final int SEPARATOR_Y = 190;
	
	private static final int BOTTOM_TEXT_OFFSET_X = 250;
	private static final int BOTTOM_TEXT_OFFSET_Y = 75;
	
	private static final int BUTTON_WIDTH = 1000;
	private static final int BUTTON_HEIGHT = 50;
	private static final int BUTTON_X = 150;
	private static final int PLAYBUTTON_Y = 225;
	private static final int OPTIONSBUTTON_Y = 275;
	private static final int QUITBUTTON_Y = 325;
	
	private static final int MOUSE_OVER_RECT_X = 500;
	private static final int SETTINGS_STATE_ID = 4;

	
	/**
	 * constructor.
	 * 
	 * @param mg	- the maingame this state belongs to
	 */
	public StartState(MainGame mg) {
		this.mg = mg;
	}
	
	/**
	 * Initialize this state.
	 * @param container The container that contains this state
	 * @param arg1 the State based game that uses this state
	 * @throws SlickException if something goes wrong
	 */
	public void init(GameContainer container, StateBasedGame arg1) throws SlickException {
		
		playButton = new Button(BUTTON_X, PLAYBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/menus/Menu_Button_Play.png"),
				new Image("resources/menus/Menu_Button_Play2.png"));
		optionsButton = new Button(BUTTON_X, OPTIONSBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/menus/Menu_Button_Options.png"), 
				new Image("resources/menus/Menu_Button_Options2.png"));
		quitButton = new Button(BUTTON_X, QUITBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/menus/Menu_Button_Quit.png"),
				new Image("resources/menus/Menu_Button_Quit2.png"));
		
	}
	
	/**
	 * Update this state.
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
				// Go to gamestate
				sbg.enterState(1);
			} 
			else if (optionsButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
				// Go to settingsState
				sbg.enterState(SETTINGS_STATE_ID);
			}
			else if (quitButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
				// Quit game
				System.exit(0);
			}
		}

	}

	/**
	 * Render this state.
	 * @param container The gamecontainer that contains this state
	 * @param arg1 the state based game that contains this state
	 * @param graphics The Graphics used to draw things in this state
	 * @throws SlickException if something goes wrong
	 */
	public void render(GameContainer container, StateBasedGame arg1, Graphics graphics) 
			throws SlickException {
		graphics.drawImage(mg.getBackgroundImage(), 0, 0);
		mg.getDosFont().drawString(container.getWidth() / 2 - BOTTOM_TEXT_OFFSET_X,
				container.getHeight() - BOTTOM_TEXT_OFFSET_Y, "Waiting for user input...");
		renderButtons(container, graphics);
		mg.drawWaterMark();
		graphics.drawImage(mg.getGameLogo(), LOGO_X, LOGO_Y);
		mg.getDosFont().drawString(SEPARATOR_X, SEPARATOR_Y, "========================");
		graphics.drawImage(mg.getForeGroundImage(), 0, 0);
		graphics.drawImage(mg.getTerminalImage(), 0, 0);
	}

	/**
	 * Method renders buttons in StartState to screen.
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
		if (optionsButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
			graphics.drawImage(optionsButton.getImageMouseOver(), optionsButton.getX(),
					optionsButton.getY());
		} else {
			graphics.drawImage(optionsButton.getImage(), optionsButton.getX(), 
					optionsButton.getY());
		}
		if (quitButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
			graphics.drawImage(quitButton.getImageMouseOver(), quitButton.getX(), 
					quitButton.getY());
		} else {
			graphics.drawImage(quitButton.getImage(), quitButton.getX(), quitButton.getY());
		}
	}

	@Override
	public int getID() {
		return 0;
	}
	
	/**
	 * Get the main game.
	 * @return the maingame
	 */
	public MainGame getMainGame() {
		return mg;
	}
}
