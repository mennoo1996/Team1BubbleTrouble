import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class SettingsState extends BasicGameState {

	private Button returnButton;
	
	private Image mannetje;
	private Image arie;
	private Image highLight;
	private Image background;
	
	private Rectangle mannetjeRectangle;
	private Rectangle arieRectangle;
	
	private MainGame mg;
	private Input input;
	
	public SettingsState(MainGame mg) {
		this.mg = mg;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame arg1) {
	}
	
	public void init(GameContainer container, StateBasedGame arg1) throws SlickException {
		returnButton = new Button(228,190,1000,50, new Image("resources/menus/Menu_Button_Return.png"), new Image("resources/menus/Menu_Button_Return2.png"));
		highLight = new Image("resources/menus/Menu_Highlight.png");
		mannetje = new Image("resources/mannetje.png");
		arie = new Image("resources/arie.png");
		background = new Image("resources/menus/Menu_Options_Text.png");
		mannetjeRectangle = new Rectangle(350,380,45,75);
		arieRectangle = new Rectangle(450,380,45,75);
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		input = container.getInput();
		
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if(mannetjeRectangle.contains(input.getMouseX(), input.getMouseY())) {
				mg.playerImage = "mannetje.png";
			} else if(arieRectangle.contains(input.getMouseX(), input.getMouseY())) {
				mg.playerImage = "arie.png";
			} else if(returnButton.getRectangle().contains(input.getMouseX(), input.getMouseY())) {
				sbg.enterState(0);
			}
		}
		
	}

	public void render(GameContainer container, StateBasedGame arg1, Graphics graphics) throws SlickException {
		
		Input input = container.getInput();
		
		// background
		graphics.drawImage(mg.backgroundImage, 0, 0);
		graphics.drawImage(background, 0, 0);
		
		if(mg.playerImage.equals("mannetje.png")) {
			graphics.drawImage(highLight, 340, 370);
		} else if(mg.playerImage.equals("arie.png")) {
			graphics.drawImage(highLight, 440, 370);
		}
		
		
		if(returnButton.getRectangle().contains(500, input.getMouseY())) {
			graphics.drawImage(returnButton.getImageMouseOver(), returnButton.getX(), returnButton.getY());
		} else {
			graphics.drawImage(returnButton.getImage(), returnButton.getX(), returnButton.getY());
		}
		
		graphics.drawImage(mannetje, mannetjeRectangle.getX(), mannetjeRectangle.getY());
		graphics.drawImage(arie, arieRectangle.getX(), arieRectangle.getY());
		
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
		
		// foreground and terminal
		graphics.drawImage(mg.foreGroundImage, 0, 0);
		graphics.drawImage(mg.terminalImage, 0, 0);
		
	}


	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 4;
	}
}
