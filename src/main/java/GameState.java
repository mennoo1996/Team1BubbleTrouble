import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class GameState extends BasicGameState {
	
	private MainGame mg;
	private ArrayList<BouncingCircle> circleList;
	private ArrayList<BouncingCircle> shotList;
	private MyShapeFill shapeFill;
	private Player player;
	private Image playerImage;
	private Input input;
	private boolean shot = false;
	
	protected Laser laser;
	protected Rectangle floor;
	protected Rectangle leftWall;
	protected Rectangle rightWall;
	protected Rectangle ceiling;
	
	
	/**
	 * constructor
	 * 
	 * @param mg	- the maingame this state belongs to
	 */
	public GameState(MainGame mg) {
		this.mg = mg;
	}
	
	/**
	 * setup all variables when entering this state
	 */
	@Override
	public void enter(GameContainer container, StateBasedGame arg1) throws SlickException {
		// If still shooting stop it
		shot = false;
		
		// Add player sprite and walls
		player = new Player(380,500,45,75, playerImage);
		floor = new Rectangle(0,575,800,25);
		leftWall = new Rectangle(0,0,10,600);
		rightWall = new Rectangle(790,0,10,600);
		ceiling = new Rectangle(0,0,800,10);
		
		// Add arraylists of circles
		circleList = new ArrayList<BouncingCircle>(); // active list
		shotList = new ArrayList<BouncingCircle>(); // list with shot circles
		
		// Add initial circles
		for(int i = 0; i < 2; i++) {
			float x = 100 + i * 150;
			float y = 100 + i * 50;
			float speed = mg.startingSpeed + i*mg.speedStep;	
			circleList.add(new BouncingCircle(x, y, 20, speed, 0, mg.gravity));
		}
		
		// shapeFill which always returns the given color
		shapeFill = new MyShapeFill(Color.blue);
	}
	
	
	/**
	 * load resources when state is initialised
	 */
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		playerImage = new Image("resources/arie.png");
	}
	
	/**
	 * update method, called on each frame refresh
	 */
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		input = container.getInput();

		// Walk left when left key pressed and not at left wall
		if(input.isKeyDown(Input.KEY_LEFT) && player.getX() > leftWall.getWidth()) {
			player.setX(player.getX() - mg.playerSpeed);
		}

		// Walk right when right key pressed and not at right wall
		if(input.isKeyDown(Input.KEY_RIGHT) && player.getMaxX() < (container.getWidth() - rightWall.getWidth())) {
			player.setX(player.getX() + mg.playerSpeed);
		}
		
		// Shoot laser when spacebar is pressed and no laser is active
		if(input.isKeyPressed(Input.KEY_SPACE) && !shot) {
			shot = true;
			float x = player.getCenterX();
			laser = new Laser(x, container.getHeight() - floor.getHeight(), mg.laserSpeed, mg.laserWidth);
		}
		
		// Update laser
		if(shot) {
			laser.update(ceiling);
			// Disable laser when it has reached the ceiling
			if(!laser.visible) {
				shot = false;
			}
		}

		// loop through all active circles
		for(BouncingCircle circle : circleList) {
			//update circles
			circle.update(this, container);
			
			// if player touches circle
			if(player.getRectangle().intersects(circle)) {
				// go to gameover state
				sbg.enterState(2);
			}
			
			// if laser intersects circle
			if(shot && laser.getRectangle().intersects(circle)) {
				// it has been shot and disabled
				shotList.add(circle);
				laser.setVisible(false);
			}
		}
		
		// loop through the circles that has been shot
		for(BouncingCircle circle : shotList) {
			// if the circle hasn't been handled
			if(!circle.isDone()) {
				// remove circle from active list
				if(circleList.contains(circle)) {
					circleList.remove(circle);
					circle.setDone(true);
				}
				
				// if the ball has a radius of 20, split it up
				if(circle.getRadius() == 20) {
					float ySpeed = circle.getySpeed();
					
					// minimum speed = -2
					if(ySpeed > -2) {
						ySpeed = -2;
					}
					
					// bonus speed when hit at the right moment
					if(ySpeed < -4) {
						ySpeed -= 2;
					}
					
					// add new balls to the active list
					circleList.add(new BouncingCircle(circle.getX(), circle.getY(), 10, circle.getxSpeed(), ySpeed, mg.gravity));
					circleList.add(new BouncingCircle(circle.getX(), circle.getY(), 10, -circle.getxSpeed(), ySpeed, mg.gravity));
				}
			}
		}
		
		// if there are no active circles, process to gamover screen
		if(circleList.isEmpty()) {
			sbg.enterState(3);
		}
	}

	/**
	 * Render method
	 * draw things on screen
	 */
	public void render(GameContainer container, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		// Draw walls, floor and ceiling
		graphics.fill(floor, shapeFill);
		graphics.fill(leftWall, shapeFill);
		graphics.fill(rightWall, shapeFill);
		graphics.fill(ceiling, shapeFill);
		
		graphics.drawString("Hello Bubble Trouble!", 100, 100);

		// draw all active circles
		for(BouncingCircle circle : circleList) {
			graphics.fill(circle.getCircle(), shapeFill);
		}

		// if shot, draw laser
		if(shot) {
			graphics.fill(laser.getRectangle());
		}
		
		// draw player
		graphics.drawImage(player.getImage(), player.getX(), player.getY());
		
		
	}

	
	/**
	 * return id of state
	 */
	@Override
	public int getID() {
		return 1;
	}

}
