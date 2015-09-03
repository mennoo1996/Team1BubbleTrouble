import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class WonState extends BasicGameState {

	private Button playAgainButton;
	
	/**
	 * Init module, load resources
	 */
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		playAgainButton = new Button(300,275,200,45, new Image("resources/play_again_button.png"));
	}
	
	/**
	 * Update method, called every frame
	 */
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
			Input input = container.getInput();
			
			// If mouse is pressed inside the button, go to the gameState
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				if(playAgainButton.getRectangle().contains(input.getMouseX(), input.getMouseY())) {
					sbg.enterState(1);
				}
			}
		}

	/**
	 * Render method, draw things to screen
	 */
	public void render(GameContainer container, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		
		// draw string and button
		graphics.drawString("You won, you are the champion!", 100, 100);
		graphics.drawImage(playAgainButton.getImage(), playAgainButton.getX(), playAgainButton.getY());
	}

	
	/**
	 * return the id of the state
	 */
	@Override
	public int getID() {
		return 3;
	}
}
