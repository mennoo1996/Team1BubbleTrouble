import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class StartState extends BasicGameState {

	private Button playButton;
	private MyRectangle settingsButton;
	
	public void init(GameContainer container, StateBasedGame arg1) throws SlickException {
		
		playButton = new Button(300,275,200,45, new Image("resources/play_button.png"));
		settingsButton = new MyRectangle(300,325,200,45);
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		Input input = container.getInput();

		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if(playButton.getRectangle().contains(input.getMouseX(), input.getMouseY())) {
				// Go to gamestate
				sbg.enterState(1);
			} else if(settingsButton.contains(input.getMouseX(), input.getMouseY())) {
				// Go to settingsState
				sbg.enterState(4);
			}
		}

	}

	public void render(GameContainer container, StateBasedGame arg1, Graphics graphics) throws SlickException {
		
		graphics.drawString("If you want to play this awesome game, click the play button!", 100, 100);
		
		graphics.drawImage(playButton.getImage(), playButton.getX(), playButton.getY());
		graphics.draw(settingsButton);
		graphics.drawString("Settings Screen", settingsButton.getX() + 20f, settingsButton.getY() + 10f);
	}

	

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
}
