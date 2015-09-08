import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class GameOverState extends BasicGameState {

	private Button playAgainButton;
	private MainGame mg;
	TextField tf;
	private Image tfBackground;
	private String inputMessage;
	
	public GameOverState(MainGame mg) {
		this.mg = mg;
	}
	
	/**
	 * Init method - load resources here
	 */
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		playAgainButton = new Button(300,475,200,45, new Image("resources/play_again_button.png"));
		tfBackground = new Image("resources/textfield.png");
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame sbg) {
		tf = new TextField(container, mg.dosFont, 115,245,700,60);
		tf.setBackgroundColor(null);
		tf.setBorderColor(null);
		tf.setFocus(true);
		inputMessage = null;
	}
	
	/**
	 * update method - called every frame
	 */
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
			Input input = container.getInput();
			
			// If the mouse is pressed inside the playAgainButton, enter the gameState
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				if(playAgainButton.getRectangle().contains(input.getMouseX(), input.getMouseY())) {
					mg.resetLifeCount();
					mg.resetLevelCount();
					sbg.enterState(1);
				}
			}
			
			if(tf.hasFocus() && input.isKeyPressed(Input.KEY_ENTER)) {
				Score score = new Score(mg.score, tf.getText());
				mg.highscores.add(score);
				HighScoresParser.writeHighScores(mg.highscoresFile, mg.highscores);
				
				inputMessage = tf.getText() + ", your score of " + mg.score + " points is saved!";
			}
		}

	/**
	 * Render method - draw things to screen
	 */
	public void render(GameContainer container, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		// draw string and button
		mg.dosFont.drawString(100,100,">Game Over Sucker!");
		mg.dosFont.drawString(100, 140, ">Your score was: "+ mg.score);
		mg.dosFont.drawString(100, 180, ">Please enter your name below");
		graphics.drawImage(playAgainButton.getImage(), playAgainButton.getX(), playAgainButton.getY());
		graphics.drawImage(tfBackground, tf.getX()-27, tf.getY()-27);
		tf.render(container, graphics);
		
		if(inputMessage != null) {
			mg.dosFont.drawString(100, 320, inputMessage);
		}
	}

	
	/**
	 * returns id of the state
	 */
	@Override
	public int getID() {
		return 2;
	}
}
