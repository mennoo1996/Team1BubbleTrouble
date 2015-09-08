import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class SettingsState extends BasicGameState {

	private Button returnButton;
	
	private SpriteSheet mannetje;
	private SpriteSheet arie;
	
	private Image highLight;
	private Image background;
	
	private MyRectangle mannetjeRectangle;
	private MyRectangle arieRectangle;

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
		mannetje = new SpriteSheet("resources/Playersprite.png", 120, 120);
		arie = new SpriteSheet("resources/Ariesprite.png", 120, 120);
		background = new Image("resources/menus/Menu_Options_Text.png");
		mannetjeRectangle = new MyRectangle(320,380,120,120);
		arieRectangle = new MyRectangle(450,380,120,120);
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		input = container.getInput();
		
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if(mannetjeRectangle.contains(input.getMouseX(), input.getMouseY())) {
				mg.playerImage = "Playersprite.png";
			} else if(arieRectangle.contains(input.getMouseX(), input.getMouseY())) {
				mg.playerImage = "Ariesprite.png";
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
		
		if(mg.playerImage.equals("Playersprite.png")) {
			graphics.drawImage(highLight, 320, 380);
		} else if(mg.playerImage.equals("Ariesprite.png")) {
			graphics.drawImage(highLight, 450, 380);
		}
		
		if(returnButton.getRectangle().contains(500, input.getMouseY())) {
			graphics.drawImage(returnButton.getImageMouseOver(), returnButton.getX(), returnButton.getY());
		} else {
			graphics.drawImage(returnButton.getImage(), returnButton.getX(), returnButton.getY());
		}
		
		graphics.drawImage(mannetje.getSprite(2, 0), mannetjeRectangle.getX(), mannetjeRectangle.getY());
		graphics.drawImage(arie.getSprite(2, 0), arieRectangle.getX(), arieRectangle.getY());
		
		// draw version number
		mg.dosFont.drawString(70, container.getHeight() - 190, "#Version 0.98");
		
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
