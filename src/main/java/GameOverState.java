import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class GameOverState extends BasicGameState {

	private Button playAgainButton;
	
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		
		playAgainButton = new Button(300,275,200,45, new Image("resources/play_again_button.png"));
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
			Input input = container.getInput();
			
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				if(playAgainButton.getRectangle().contains(input.getMouseX(), input.getMouseY())) {
					sbg.enterState(1);
				}
			}
		
		}

	public void render(GameContainer container, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		
		graphics.drawString("Game Over Sucker!", 100, 100);
		
		graphics.drawImage(playAgainButton.getImage(), playAgainButton.getX(), playAgainButton.getY());
	}

	

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 2;
	}
}
