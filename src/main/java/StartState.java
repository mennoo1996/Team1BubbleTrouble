import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class StartState extends BasicGameState {

	
	private MainGame mg;
	private Image miscText;
	private Button playButton;
	private Button optionsButton;
	private Button quitButton;
	
	/**
	 * constructor
	 * 
	 * @param mg	- the maingame this state belongs to
	 */
	public StartState(MainGame mg) {
		this.mg = mg;
	}
	
	public void init(GameContainer container, StateBasedGame arg1) throws SlickException {
		
		miscText = new Image("resources/menus/Menu_Main_Text.png");
		
		playButton = new Button(250,275,1000,50, new Image("resources/menus/Menu_Button_Play.png"), new Image("resources/menus/Menu_Button_Play2.png"));
		optionsButton = new Button(250,325,1000,50, new Image("resources/menus/Menu_Button_Options.png"), new Image("resources/menus/Menu_Button_Options2.png"));
		quitButton = new Button(250,375,1000,50, new Image("resources/menus/Menu_Button_Quit.png"), new Image("resources/menus/Menu_Button_Quit2.png"));
		
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		Input input = container.getInput();

		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if(playButton.getRectangle().contains(500, input.getMouseY())) {
				// Go to gamestate
				sbg.enterState(1);
			} 
			else if(optionsButton.getRectangle().contains(500, input.getMouseY())) {
				// Go to settingsState
				sbg.enterState(4);
			}
			else if(quitButton.getRectangle().contains(500, input.getMouseY())) {
				// Quit game
				System.exit(0);
			}
		}

	}

	public void render(GameContainer container, StateBasedGame arg1, Graphics graphics) throws SlickException {
		
		graphics.drawString("If you want to play this awesome game, click the play button!", 100, 100);
		
		Input input = container.getInput();
		
		graphics.drawImage(mg.backgroundImage, 0, 0);
		
		if(playButton.getRectangle().contains(500, input.getMouseY())) {
			graphics.drawImage(playButton.getImageMouseOver(), playButton.getX(), playButton.getY());
		} else {
			graphics.drawImage(playButton.getImage(), playButton.getX(), playButton.getY());
		}
		
		if(optionsButton.getRectangle().contains(500, input.getMouseY())) {
			graphics.drawImage(optionsButton.getImageMouseOver(), optionsButton.getX(), optionsButton.getY());
		} else {
			graphics.drawImage(optionsButton.getImage(), optionsButton.getX(), optionsButton.getY());
		}
		
		if(quitButton.getRectangle().contains(500, input.getMouseY())) {
			graphics.drawImage(quitButton.getImageMouseOver(), quitButton.getX(), quitButton.getY());
		} else {
			graphics.drawImage(quitButton.getImage(), quitButton.getX(), quitButton.getY());
		}

		// draw version number (BECAUZ ITZ COOL)
		graphics.drawImage(mg.versiontextImage, 72, container.getHeight() - 195);
		graphics.drawImage(mg.numberImages[1], 72 + 150, container.getHeight() - 196);
		LinkedList<Integer> numberStack = new LinkedList<Integer>();
		int versionnumber = 105;
		int stackCount = 0;
		while(versionnumber > 0) {
			numberStack.push(versionnumber % 10);
			versionnumber /= 10;
		}
		while(!numberStack.isEmpty()) {
			graphics.drawImage(mg.numberImages[numberStack.pop()], 72 + 175 + 20*stackCount, container.getHeight() - 196);
			stackCount++;
		}
		
		graphics.drawImage(miscText, 0, 0);
		
		graphics.drawImage(mg.foreGroundImage, 0, 0);
		graphics.drawImage(mg.terminalImage, 0, 0);
	}

	

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
}
