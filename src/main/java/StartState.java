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
	private Image miscText;
	private Button playButton;

	private Button optionsButton;
	private Button quitButton;
	
	private static final int BUTTON_WIDTH = 1000;
	private static final int BUTTON_HEIGHT = 50;
	private static final int BUTTON_X = 250;
	private static final int PLAYBUTTON_Y = 275;
	private static final int OPTIONSBUTTON_Y = 325;
	private static final int QUITBUTTON_Y = 375;
	
	private static final int MOUSE_OVER_RECT_X = 500;
	private static final int SETTINGS_STATE_ID = 4;
	
	private static final int VERSION_STRING_X = 70;
	private static final int VERSION_STRING_Y_DEVIATION = 190;

	
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
		
		miscText = new Image("resources/menus/Menu_Main_Text.png");
		
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
		Input input = container.getInput();
		graphics.drawImage(mg.getBackgroundImage(), 0, 0);
		
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
		// draw version number
		mg.getDosFont().drawString(VERSION_STRING_X, container.getHeight() 
				- VERSION_STRING_Y_DEVIATION, "#Version 0.98");
		graphics.drawImage(miscText, 0, 0);
		graphics.drawImage(mg.getForeGroundImage(), 0, 0);
		graphics.drawImage(mg.getTerminalImage(), 0, 0);
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
