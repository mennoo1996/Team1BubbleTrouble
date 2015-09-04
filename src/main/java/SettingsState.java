import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class SettingsState extends BasicGameState {

	private Image mannetje;
	private Image arie;
	private Rectangle mannetjeRectangle;
	private Rectangle arieRectangle;
	private Rectangle mannetjeHighlight;
	private Rectangle arieHighlight;
	private Rectangle backButton;
	private MainGame mg;
	private Input input;
	
	public SettingsState(MainGame mg) {
		this.mg = mg;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame arg1) {
		System.out.println("got there though");
	}
	
	public void init(GameContainer container, StateBasedGame arg1) throws SlickException {
		mannetje = new Image("resources/mannetje.png");
		arie = new Image("resources/arie.png");
		mannetjeRectangle = new Rectangle(100,300,45,75);
		arieRectangle = new Rectangle(300,300,45,75);
		mannetjeHighlight = new Rectangle(90,290,65,95);
		arieHighlight = new Rectangle(290,290,65,95);
		backButton = new Rectangle(500,200,200,45);
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		input = container.getInput();
		
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if(mannetjeRectangle.contains(input.getMouseX(), input.getMouseY())) {
				mg.playerImage = "mannetje.png";
			} else if(arieRectangle.contains(input.getMouseX(), input.getMouseY())) {
				mg.playerImage = "arie.png";
			} else if(backButton.contains(input.getMouseX(), input.getMouseY())) {
				sbg.enterState(0);
			}
		}
		
		
	}

	public void render(GameContainer container, StateBasedGame arg1, Graphics graphics) throws SlickException {
		graphics.setColor(Color.white);
		
		graphics.drawString("Click the player of your chose", 100, 100);
		
		if(mg.playerImage.equals("mannetje.png")) {
			graphics.fill(mannetjeHighlight);
		} else if(mg.playerImage.equals("arie.png")) {
			graphics.fill(arieHighlight);
		}
		
		graphics.drawImage(mannetje, mannetjeRectangle.getX(), mannetjeRectangle.getY());
		graphics.drawImage(arie, arieRectangle.getX(), arieRectangle.getY());
		
		graphics.draw(backButton);
		graphics.drawString("Back to start menu", backButton.getX() + 20f, backButton.getY() + 10f);
	}

	

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 4;
	}
	
	private String getStringForColor(Color color) {
		if(color == Color.blue) {
			return "blue";
		} else if(color == Color.red) {
			return "red";
		} else if(color == Color.yellow) {
			return "yellow";
		}
		
		return "";
	}
}
