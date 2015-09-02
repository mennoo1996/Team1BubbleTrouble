import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class GameState extends BasicGameState {
	
	///// CONFIGURATION /////
	
	static float gravity = 0.1f;
	static float startingSpeed = 0.5f;
	static float speedStep = 0.5f;
	static int playerSpeed = 5;
	static float laserWidth = 3f;
	private int laserSpeed = 10;
	
	////////////////////////
	
	
	private ArrayList<BouncingCircle> circleList;
	private ArrayList<BouncingCircle> shotList;
	private MyShapeFill shapeFill;
	private Player player;
	private Image playerImage;
	private Input input;
	private boolean shot = false;
	private Laser laser;
	
	@Override
	public void enter(GameContainer container, StateBasedGame arg1) throws SlickException {
		shot = false;
		
		player = new Player(380,500,45,75, playerImage);
		
		
		circleList = new ArrayList<BouncingCircle>();
		shotList = new ArrayList<BouncingCircle>();
		
		for(int i = 0; i < 5; i++) {
			float x = 100 + i * 150;
			float y = 100 + i * 50;
			float speed = startingSpeed + i*speedStep;
			
			circleList.add(new BouncingCircle(x, y, 20, speed, 0, gravity));
		}
		
		shapeFill = new MyShapeFill(Color.blue);
	}
	
	
	
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		
		playerImage = new Image("resources/arie.png");
		
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		
		input = container.getInput();

		if(input.isKeyDown(Input.KEY_LEFT) && player.getX() > 0) {
			player.setX(player.getX() - playerSpeed);
		}

		if(input.isKeyDown(Input.KEY_RIGHT) && player.getMaxX() < container.getWidth()) {
			player.setX(player.getX() + playerSpeed);
		}
		
		if(input.isKeyPressed(Input.KEY_SPACE)) {
			shot = true;
			float x = player.getCenterX();
			
			laser = new Laser(x, container.getHeight()-40, laserSpeed, laserWidth);
			
		}
		
		if(shot) {
			laser.update(container);
			if(!laser.visible) {
				shot = false;
			}
		}

		for(BouncingCircle circle : circleList) {
			circle.update(container);
			if(player.getRectangle().intersects(circle) || player.getRectangle().contains(circle)) {
				sbg.enterState(2);
			}
			
			if(shot && laser.getRectangle().intersects(circle)) {
				shotList.add(circle);
				laser.setVisible(false);
			}
		}
		
		for(Circle circle : shotList) {
			if(circleList.contains(circle)) {
				circleList.remove(circle);
			}
		}
		
		if(circleList.isEmpty()) {
			sbg.enterState(3);
		}
	}

	public void render(GameContainer container, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		
		graphics.drawString("Hello Bubble Trouble!", 100, 100);


		for(BouncingCircle circle : circleList) {
			graphics.fill(circle.getCircle(), shapeFill);
		}

		if(shot) {
			graphics.fill(laser.getRectangle());
		}
		
		graphics.drawImage(player.getImage(), player.getX(), player.getY());
		
		
	}

	

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}

}
