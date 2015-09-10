import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Class that represents the state of the game when the game is over.
 * @author Menno
 *
 */
public class GameOverState extends BasicGameState {

	private Button playButton;
	private Button menuButton;
	private Button saveButton;
	private Button exitButton;
	
	private MainGame mg;
	private TextField tf;
	private Image tfBackground;
	private String inputMessage;

	private boolean highScoreEntered;

	private static final int TEXT_X = 164;
	private static final int TEXT_1_Y = 238;
	private static final int TEXT_2_Y = 288;
	private static final int TEXT_3_Y = 338;
	private static final int TEXT_4_Y = 538;

	private static final int BUTTON_X = 150;
	private static final int SAVE_BUTTON_Y = 525;
	private static final int PLAY_BUTTON_Y = 575;
	private static final int MENU_BUTTON_Y = 625;
	private static final int EXIT_BUTTON_Y = 675;
	
	private static final int HIGHSCORES_X = 1000;
	
	private static final int BUTTON_WIDTH = 1000;
	private static final int BUTTON_HEIGHT = 50;
	
	private static final int MOUSE_OVER_RECT_X = 500;
	
	private static final int TEXT_FIELD_X = 164;
	private static final int TEXT_FIELD_Y = 438;
	private static final int TEXT_FIELD_WIDTH = 700;
	private static final int TEXT_FIELD_HEIGHT = 60;
	private static final int TF_BACKGROUND_DEVIATION = 27;
	
	private static final int LOGO_X = 160;
	private static final int LOGO_Y = 110;
	private static final int SEPARATOR_X = 164;
	private static final int SEPARATOR_Y = 190;
	private static final int BOTTOM_TEXT_OFFSET_X = 250;
	private static final int BOTTOM_TEXT_OFFSET_Y = 75;
	
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
		
		saveButton = new Button(BUTTON_X, SAVE_BUTTON_Y,
				BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/Menus/Menu_Button_SaveHighscore.png"),
				new Image("resources/Menus/Menu_Button_SaveHighscore2.png"));
		
		playButton = new Button(BUTTON_X, PLAY_BUTTON_Y,
				BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/Menus/Menu_Button_PlayAgain.png"),
				new Image("resources/Menus/Menu_Button_PlayAgain2.png"));
		
		menuButton = new Button(BUTTON_X, MENU_BUTTON_Y,
				BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/Menus/Menu_Button_MainMenu.png"),
				new Image("resources/Menus/Menu_Button_MainMenu2.png"));
		
		exitButton = new Button(BUTTON_X, EXIT_BUTTON_Y,
				BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/Menus/Menu_Button_Quit.png"),
				new Image("resources/Menus/Menu_Button_Quit2.png"));
		
		tfBackground = new Image("resources/textfield.png");

	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame sbg) {
		tf = new TextField(container, mg.getDosFont(), TEXT_FIELD_X, TEXT_FIELD_Y,
				TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		tf.setBackgroundColor(null);
		tf.setBorderColor(null);
		tf.setFocus(true);
		inputMessage = null;
		highScoreEntered = false;
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
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				if (playButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
					// Start over
					mg.resetLifeCount();
					mg.resetLevelCount();
					mg.setScore(0);
					sbg.enterState(1);
				} 
				else if (saveButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
					// Save score
					saveScore();
				}
				else if (menuButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
					// Go to startState
					mg.setScore(0);
					mg.setLevelCounter(0);
					sbg.enterState(0);
				}
				else if (exitButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
					container.exit();
				}
			}
			if (tf.hasFocus() && input.isKeyPressed(Input.KEY_ENTER) 
					&& (inputMessage == null || inputMessage.equals("Maximum length is 34 characters")) && !highScoreEntered) {
				if (tf.getText().length()<34) {
	                highScoreEntered = true;
	                saveScore(); }
				else {
					inputMessage = "Maximum length is 34 characters";
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
		graphics.drawImage(mg.getBackgroundImage(), 0, 0);
		mg.getDosFont().drawString(container.getWidth() / 2 - BOTTOM_TEXT_OFFSET_X,
				container.getHeight() - BOTTOM_TEXT_OFFSET_Y, "Waiting for user input...");
		if (mg.getLifeCount() <= 0) {
			mg.getDosFont().drawString(TEXT_X, TEXT_1_Y , "# Game Over");
		} else {
			mg.getDosFont().drawString(TEXT_X, TEXT_1_Y , 
					"# You won! You are the champion of soup!");
		}
		mg.getDosFont().drawString(TEXT_X, TEXT_2_Y, "# Your score was: " + mg.getScore());
		mg.getDosFont().drawString(TEXT_X, TEXT_3_Y, "# Please enter your name below");

		graphics.drawImage(tfBackground, tf.getX() - TF_BACKGROUND_DEVIATION, 
				tf.getY() - TF_BACKGROUND_DEVIATION);
		tf.render(container, graphics);
		
		if (inputMessage != null) {
			mg.getDosFont().drawString(TEXT_X, TEXT_4_Y, inputMessage);
		}
		renderButtons(container, graphics);
		mg.drawWaterMark();
		graphics.drawImage(mg.getGameLogo(), LOGO_X, LOGO_Y);
		mg.getDosFont().drawString(SEPARATOR_X, SEPARATOR_Y, "========================");
		graphics.drawImage(mg.getForeGroundImage(), 0, 0);
		graphics.drawImage(mg.getTerminalImage(), 0, 0);
		mg.getHighscores().sort();
		String highScoresString = mg.getHighscores().toString();
		mg.getDosFont().drawString(HIGHSCORES_X, SEPARATOR_Y, highScoresString);
	}
	
	/**
	 * Method renders buttons in GameOverState to screen.
	 * @param container appgamecontainer used
	 * @param graphics graphics context used
	 */
	private void renderButtons(GameContainer container, Graphics graphics) {
		Input input = container.getInput();
		if (playButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
			graphics.drawImage(playButton.getImageMouseOver(), playButton.getX(), 
					playButton.getY());
		} else {
			graphics.drawImage(playButton.getImage(), 
					playButton.getX(), playButton.getY());
		}
		if (menuButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
			graphics.drawImage(menuButton.getImageMouseOver(), menuButton.getX(), 
					menuButton.getY());
		} else {
			graphics.drawImage(menuButton.getImage(), 
					menuButton.getX(), menuButton.getY());
		}
		if (inputMessage == null) {
			if (saveButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
				graphics.drawImage(saveButton.getImageMouseOver(), saveButton.getX(), 
						saveButton.getY());
			} else {
				graphics.drawImage(saveButton.getImage(), 
						saveButton.getX(), saveButton.getY());
			} }
		if (exitButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
			graphics.drawImage(exitButton.getImageMouseOver(), exitButton.getX(), 
					exitButton.getY());
		} else {
			graphics.drawImage(exitButton.getImage(), exitButton.getX(), exitButton.getY()); }
	}

	/**
	 * Saves score for this player.
	 */
	private void saveScore() {
		Score score = new Score(mg.getScore(), tf.getText());
		mg.getHighscores().add(score);
		mg.getHighscores().sort();
		HighScoresParser.writeHighScores(mg.getHighscoresFile(), mg.getHighscores());
		inputMessage = "# " + tf.getText() + ", your score of " + mg.getScore();
		inputMessage += " points is saved!";
	}
	
	
	/**
	 * returns id of the state.
	 */
	@Override
	public int getID() {
		return 2;
	}
}
