package gui;
import logic.Button;
import logic.Logger;
import logic.MyRectangle;

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
	
	private SpriteSheet mannetjeN;
	private SpriteSheet mannetjeA;
	private SpriteSheet arieN;
	private SpriteSheet arieA;
	
	private Image highLightN;
	private Image highLightA;
	
	private MyRectangle mannetje1Rectangle;
	private MyRectangle arie1Rectangle;
	private MyRectangle mannetje2Rectangle;
	private MyRectangle arie2Rectangle;

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
	private static final int CONTROL_X1 = 800;
	private static final int CONTROL_X2 = 1000;
	private static final int P1_CONTROL_Y = 238;
	private static final int P2_CONTROL_Y = 388;
	
	private static final int TEXT_X = 164;
	private static final int TEXT_1_Y = 288;
	private static final int TEXT_2_Y = 338;
	private static final int TEXT_3_Y = 388;
	private static final int TEXT_4_Y = 438;
	private static final int PLAYER_1_TEXT_Y = 550;
	private static final int PLAYER_2_TEXT_Y = 700;
	
	private static final int PLAYER_SPRITE_WIDTH = 120;
	private static final int PLAYER_SPRITE_HEIGHT = 120;
	
	private static final int MANNETJE_1_X = 390;
	private static final int MANNETJE_1_Y = 500;
	private static final int ARIE_1_X = 550;
	private static final int ARIE_1_Y = 500;

	private static final int MANNETJE_2_X = 390;
	private static final int MANNETJE_2_Y = 650;
	private static final int ARIE_2_X = 550;
	private static final int ARIE_2_Y = 650;
	
	private static final int MOUSE_OVER_RECT_X = 500;
	
	/**
	 * Construct a SettingsState.
	 * @param mg the MainGame that uses this state.
	 */
	public SettingsState(MainGame mg) {
		this.mg = mg;
	}
	
	/**
	 * setup all variables when entering this state.
	 * @param container the Container this state is part of
	 * @param arg1 The statebasedgame this state is part of
	 * @throws SlickException sometimes.
	 */
	@Override
	public void enter(GameContainer container, StateBasedGame arg1) throws SlickException {
		mg.getLogger().log("Entering SettingsState", 
				Logger.PriorityLevels.LOW, "States");
		RND.setOpacity(0.0f);
		mg.stopSwitchState();
	}
	
	/**
	 * Exit function for state. Fades out and everything.
	 * @param container the GameContainer we are running in
	 * @param sbg the gamestate cont.
	 * @param delta the deltatime in ms
	 */
	public void exit(GameContainer container, StateBasedGame sbg, int delta) {
		if (mg.getShouldSwitchState()) {
			if (RND.getOpacity() > 0.0f) {
				RND.setOpacity(RND.getOpacity() - ((float) delta) / mg.getOpacityFadeTimer());
			} else {
				mg.getLogger().log("Exiting SettingsState", Logger.PriorityLevels.LOW, "States");
				if (mg.getSwitchState() == -1) {
					container.exit();
				} else {
					sbg.enterState(mg.getSwitchState());
				}
			}	
		}
	}
	
	/**
	 * Initialize this state.
	 * @param container the GameContainer that contains this state
	 * @param arg1 the state based game that uses this state
	 * @throws SlickException if something goes wrong
	 */
	public void init(GameContainer container, StateBasedGame arg1) throws SlickException {
		returnButton = new Button(RETURN_BUTTON_X, RETURN_BUTTON_Y, RETURN_BUTTON_WIDTH,
				RETURN_BUTTON_HEIGHT, 
				new Image("resources/images_UI/Menu_Button_Return_Norm.png"),
				new Image("resources/images_UI/Menu_Button_Return_Add.png"),
				new Image("resources/images_UI/Menu_Button_Return2_Norm.png"),
				new Image("resources/images_UI/Menu_Button_Return2_Add.png"));
		highLightN = new Image("resources/images_UI/Menu_Highlight_Norm.png");
		highLightA = new Image("resources/images_UI/Menu_Highlight_Add.png");
		mannetjeN = new SpriteSheet("resources/images_Player/Playersprite_Norm.png",
				PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT);
		mannetjeA = new SpriteSheet("resources/images_Player/Playersprite_Add.png",
				PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT);
		arieN = new SpriteSheet("resources/images_Player/Player2sprite_Norm.png",
				PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT);
		arieA = new SpriteSheet("resources/images_Player/Player2sprite_Add.png",
				PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT);
		mannetje1Rectangle = new MyRectangle(MANNETJE_1_X, MANNETJE_1_Y, PLAYER_SPRITE_WIDTH,
				PLAYER_SPRITE_HEIGHT);
		arie1Rectangle = new MyRectangle(ARIE_1_X, ARIE_1_Y, PLAYER_SPRITE_WIDTH, 
				PLAYER_SPRITE_HEIGHT);
		mannetje2Rectangle = new MyRectangle(MANNETJE_2_X, MANNETJE_2_Y, PLAYER_SPRITE_WIDTH,
				PLAYER_SPRITE_HEIGHT);
		arie2Rectangle = new MyRectangle(ARIE_2_X, ARIE_2_Y, PLAYER_SPRITE_WIDTH, 
				PLAYER_SPRITE_HEIGHT);
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
		if (RND.getOpacity() < 1.0f && !mg.getShouldSwitchState()) {
			RND.setOpacity(RND.getOpacity() + ((float) delta) / mg.getOpacityFadeTimer());
		}
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && !mg.getShouldSwitchState()) {
			if (mannetje1Rectangle.contains(input.getMouseX(), input.getMouseY())) {
				mg.setPlayer1ImageString("Playersprite_Norm.png", "Playersprite_Add.png");
				mg.getPlayerList().setPlayerImage(0, mg.getPlayer1ImageStringN(), 
						mg.getPlayer1ImageStringA());
			} else if (arie1Rectangle.contains(input.getMouseX(), input.getMouseY())) {
				mg.setPlayer1ImageString("Player2sprite_Norm.png", "Player2sprite_Add.png");
				mg.getPlayerList().setPlayerImage(0, mg.getPlayer1ImageStringN(), 
						mg.getPlayer1ImageStringA());
			} else if (mannetje2Rectangle.contains(input.getMouseX(), input.getMouseY())) {
				mg.setPlayer2ImageString("Playersprite_Norm.png", "Playersprite_Add.png");
				mg.getPlayerList().setPlayerImage(1, mg.getPlayer2ImageStringN(), 
						mg.getPlayer2ImageStringA());
			} else if (arie2Rectangle.contains(input.getMouseX(), input.getMouseY())) {
				mg.setPlayer2ImageString("Player2sprite_Norm.png", "Player2sprite_Add.png");
				mg.getPlayerList().setPlayerImage(1, mg.getPlayer2ImageStringN(), 
						mg.getPlayer2ImageStringA());
			} else if (returnButton.getRectangle().contains(input.getMouseX(), input.getMouseY())) {
				mg.setSwitchState(mg.getStartState());
			}
		}
		exit(container, sbg, delta);
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
		this.input = container.getInput();
		
		graphics.drawImage(mg.getBackgroundImage(), 0, 0);
		RND.text(graphics, TEXT_X, TEXT_1_Y, "# You can choose a player skin per");
		RND.text(graphics, TEXT_X, TEXT_2_Y, "# player by clicking on it below,");
		RND.text(graphics, TEXT_X, TEXT_3_Y, "# we advice different sprites for");
		RND.text(graphics, TEXT_X, TEXT_4_Y, "# each player but it's your choice!");
	
		RND.text(graphics, TEXT_X, PLAYER_1_TEXT_Y, "> Player 1:");
		RND.text(graphics, TEXT_X, PLAYER_2_TEXT_Y, "> Player 2:");
	
		RND.text(graphics, container.getWidth() / 2 - BOTTOM_TEXT_OFFSET_X,
				container.getHeight() - BOTTOM_TEXT_OFFSET_Y, "Waiting for user input...");
		drawSprites(graphics);

		mg.drawWaterMark();
		RND.drawColor(graphics, mg.getGameLogoN(), mg.getGameLogoA(),
				LOGO_X, LOGO_Y, mg.getColor());
		String tempString = "========================================";
		tempString += "=======================================";
		RND.text(graphics, SEPARATOR_X, SEPARATOR_Y, tempString);
		graphics.drawImage(mg.getForeGroundImage(), 0, 0);
		graphics.drawImage(mg.getTerminalImage(), 0, 0);
		
		drawControls(graphics);
	}
	
	private void drawControls(Graphics graphics) {
		String controlsPlayer1 = "Move left = left arrow\nMove right = right arrow\n";
		controlsPlayer1 += "Shoot weapon = spacebar";
		String controlsPlayer2 = "Move left = a\nMove right = d\nShoot weapon = w";
		
		RND.text(graphics, CONTROL_X1, P1_CONTROL_Y, "Player 1:");
		RND.text(graphics, CONTROL_X2, P1_CONTROL_Y, controlsPlayer1);
		RND.text(graphics, CONTROL_X1, P2_CONTROL_Y, "Player 2:");
		RND.text(graphics, CONTROL_X2, P2_CONTROL_Y, controlsPlayer2);
		
	}
	
	private void drawSprites(Graphics graphics) {
		if (mg.getPlayer1ImageStringN().equals("Playersprite_Norm.png")) {
			RND.drawColor(graphics, highLightN, highLightA, MANNETJE_1_X, MANNETJE_1_Y, 
					mg.getColor());
		} else if (mg.getPlayer1ImageStringN().equals("Player2sprite_Norm.png")) {
			RND.drawColor(graphics, highLightN, highLightA, ARIE_1_X, ARIE_1_Y, mg.getColor());
		}
		
		if (mg.getPlayer2ImageStringN().equals("Playersprite_Norm.png")) {
			RND.drawColor(graphics, highLightN, highLightA, MANNETJE_2_X, MANNETJE_2_Y, 
					mg.getColor());
		} else if (mg.getPlayer2ImageStringN().equals("Player2sprite_Norm.png")) {
			RND.drawColor(graphics, highLightN, highLightA, ARIE_2_X, ARIE_2_Y, mg.getColor());
		}
		
		if (returnButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
			RND.drawColor(graphics, returnButton.getImageMouseOverN(), 
					returnButton.getImageMouseOverA(), 
					returnButton.getX(), returnButton.getY(), mg.getColor());
		} else {
			RND.drawColor(graphics, returnButton.getImageN(), returnButton.getImageA(), 
					returnButton.getX(), returnButton.getY(), mg.getColor());
		}

		drawSprites2(graphics);
	}
	
	private void drawSprites2(Graphics graphics) {
		RND.drawColor(graphics, mannetjeN.getSprite(2, 0), mannetjeA.getSprite(2, 0),
				mannetje1Rectangle.getX(), mannetje1Rectangle.getY(), mg.getColor());
		RND.drawColor(graphics, arieN.getSprite(2, 0), arieA.getSprite(2, 0),
				arie1Rectangle.getX(), arie1Rectangle.getY(), mg.getColor());
		
		RND.drawColor(graphics, mannetjeN.getSprite(2, 0), mannetjeA.getSprite(2, 0),
				mannetje2Rectangle.getX(), mannetje2Rectangle.getY(), mg.getColor());
		RND.drawColor(graphics, arieN.getSprite(2, 0), arieA.getSprite(2, 0),
				arie2Rectangle.getX(), arie2Rectangle.getY(), mg.getColor());
	}


	@Override
	public int getID() {
		return mg.getSettingsState();
	}

	/**
	 * @return the mg
	 */
	public MainGame getMg() {
		return mg;
	}

	/**
	 * @param mg the mg to set
	 */
	public void setMg(MainGame mg) {
		this.mg = mg;
	}
	
	
}
