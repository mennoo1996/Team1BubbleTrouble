import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This class represents the state of the settings menu.
 * @author Menno
 *
 */
public class SettingsState extends BasicGameState {

	private Button returnButton;
	
	private SpriteSheet mannetje;
	private SpriteSheet arie;
	
	private Image highLight;
	
	private MyRectangle mannetjeRectangle;
	private MyRectangle arieRectangle;

	private MainGame mg;
	private Input input;
	
	private static final int LOGO_X = 160;
	private static final int LOGO_Y = 110;
	private static final int SEPARATOR_X = 164;
	private static final int SEPARATOR_Y = 190;
	
	private static final int BOTTOM_TEXT_OFFSET_X = 250;
	private static final int BOTTOM_TEXT_OFFSET_Y = 75;
	
	private static final int RETURN_BUTTON_X = 150;
	private static final int RETURN_BUTTON_Y = 225;
	private static final int RETURN_BUTTON_WIDTH = 1000;
	private static final int RETURN_BUTTON_HEIGHT = 50;
	
	private static final int TEXT_X = 164;
	private static final int TEXT_1_Y = 288;
	private static final int TEXT_2_Y = 338;
	
	private static final int PLAYER_SPRITE_WIDTH = 120;
	private static final int PLAYER_SPRITE_HEIGHT = 120;
	
	private static final int MANNETJE_X = 220;
	private static final int MANNETJE_Y = 380;
	
	private static final int ARIE_X = 380;
	private static final int ARIE_Y = 380;
	
	private static final int MOUSE_OVER_RECT_X = 500;
	
	private static final int STATE_ID = 4;
	
	/**
	 * Construct a SettingsState.
	 * @param mg the MainGame that uses this state.
	 */
	public SettingsState(MainGame mg) {
		this.mg = mg;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame arg1) {
	}
	
	/**
	 * Initialize this state.
	 * @param container the GameContainer that contains this state
	 * @param arg1 the state based game that uses this state
	 * @throws SlickException if something goes wrong
	 */
	public void init(GameContainer container, StateBasedGame arg1) throws SlickException {
		returnButton = new Button(RETURN_BUTTON_X, RETURN_BUTTON_Y, RETURN_BUTTON_WIDTH,
				RETURN_BUTTON_HEIGHT, new Image("resources/menus/Menu_Button_Return.png"),
				new Image("resources/menus/Menu_Button_Return2.png"));
		highLight = new Image("resources/menus/Menu_Highlight.png");
		mannetje = new SpriteSheet("resources/Playersprite.png",
				PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT);
		arie = new SpriteSheet("resources/Ariesprite.png",
				PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT);
		mannetjeRectangle = new MyRectangle(MANNETJE_X, MANNETJE_Y, PLAYER_SPRITE_WIDTH,
				PLAYER_SPRITE_HEIGHT);
		arieRectangle = new MyRectangle(ARIE_X, ARIE_Y, PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT);
	}
	
	/**
	 * Update this state.
	 * @param container The gamecontainer that contains this state
	 * @param sbg the state based game that uses this state
	 * @param delta the time in ms since the last frame
	 * @throws SlickException if something goes wrong
	 */
	public void update(GameContainer container, StateBasedGame sbg, int delta) 
			throws SlickException {
		input = container.getInput();
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if (mannetjeRectangle.contains(input.getMouseX(), input.getMouseY())) {
				mg.setPlayerImage("Playersprite.png");
			} else if (arieRectangle.contains(input.getMouseX(), input.getMouseY())) {
				mg.setPlayerImage("Ariesprite.png");
			} else if (returnButton.getRectangle().contains(input.getMouseX(), input.getMouseY())) {
				sbg.enterState(0);
			}
		}
		
	}

	/**
	 * Render this state.
	 * @param container the Gamecontainer that contains this state
	 * @param arg1 the state based game that uses this state
	 * @param graphics the Graphics object used in this state
	 * @throws SlickException if something goes wrong.
	 */
	public void render(GameContainer container, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		Input input = container.getInput();
		graphics.drawImage(mg.getBackgroundImage(), 0, 0);
		mg.getDosFont().drawString(TEXT_X, TEXT_1_Y, "# You can choose a player skin");
		mg.getDosFont().drawString(TEXT_X, TEXT_2_Y, "# by clicking on it");
		mg.getDosFont().drawString(container.getWidth() / 2 - BOTTOM_TEXT_OFFSET_X,
				container.getHeight() - BOTTOM_TEXT_OFFSET_Y, "Waiting for user input...");
		
		if (mg.getPlayerImage().equals("Playersprite.png")) {
			graphics.drawImage(highLight, MANNETJE_X, MANNETJE_Y);
		} else if (mg.getPlayerImage().equals("Ariesprite.png")) {
			graphics.drawImage(highLight, ARIE_X, ARIE_Y);
		}
		if (returnButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
			graphics.drawImage(returnButton.getImageMouseOver(), returnButton.getX(),
					returnButton.getY());
		} else {
			graphics.drawImage(returnButton.getImage(), returnButton.getX(), returnButton.getY());
		}
		graphics.drawImage(mannetje.getSprite(2, 0), mannetjeRectangle.getX(),
				mannetjeRectangle.getY());
		graphics.drawImage(arie.getSprite(2, 0), arieRectangle.getX(), arieRectangle.getY());

		mg.drawWaterMark();
		graphics.drawImage(mg.getGameLogo(), LOGO_X, LOGO_Y);
		mg.getDosFont().drawString(SEPARATOR_X, SEPARATOR_Y, "========================");
		graphics.drawImage(mg.getForeGroundImage(), 0, 0);
		graphics.drawImage(mg.getTerminalImage(), 0, 0);
	}


	@Override
	public int getID() {
		return STATE_ID;
	}
}
