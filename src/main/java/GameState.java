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

	// CONSTANTS
	private static int TOTAL_TIME = 40000;
	
	private MainGame mg;
	private ArrayList<BouncingCircle> circleList;
	private ArrayList<BouncingCircle> shotList;
	private MyShapeFill shapeFill;
	private Player player;
	private Image playerImage;
	private Input input;
	private boolean shot;
	private int score;
	private long startTime;
	private float currentTime;

	// Countdown Bar Logic
	private static int COUNTDOWN_BAR_WIDTH = 300;
	private float fractionTimePix;

	//Life counter method 1 code
	private boolean playerIntersect;
	
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
		//Initialize for life counter
		playerIntersect = false;
		
		// If still shooting stop it
		shot = false;
		score = 0;
		startTime = System.currentTimeMillis();
		
		// Add player sprite and walls
		playerImage = new Image("resources/" + mg.playerImage);
		player = new Player(container.getWidth()/2 -22.5f,container.getHeight()-100,45,75, playerImage);
		//player = new Rectangle(container.getWidth()/2 -22.5f,container.getHeight()-100,45,75);
		floor = new Rectangle(0,container.getHeight()-25,container.getWidth(),25);
		leftWall = new Rectangle(0,0,10,container.getHeight());
		rightWall = new Rectangle(container.getWidth()-10,0,10,container.getHeight());
		ceiling = new Rectangle(0,0,container.getWidth(),10);
		
		// Add arraylists of circles
		circleList = new ArrayList<BouncingCircle>(); // active list
		shotList = new ArrayList<BouncingCircle>(); // list with shot circles
		
		// Add initial circle
		circleList.add(new BouncingCircle(100,200,90,mg.startingSpeed, -50, mg.gravity));
		
		// shapeFill which always returns the given color
		shapeFill = new MyShapeFill(Color.blue);
	}
	
	
	/**
	 * load resources when state is initialised
	 */
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
	}
	
	/**
	 * update method, called on each frame refresh
	 */
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		// Timer logic
		currentTime = (System.currentTimeMillis() - startTime);
		fractionTimePix = COUNTDOWN_BAR_WIDTH*(TOTAL_TIME - currentTime) / TOTAL_TIME;
		if (currentTime >= TOTAL_TIME) {
			playerDeath(sbg);
		}

		float deltaFloat = delta/1000f;
		input = container.getInput();
		
		// Exit application when esc key is pressed at any time during game.
		if(input.isKeyDown(Input.KEY_ESCAPE)) {
			System.exit(0);
		}
		
		// Walk left when left key pressed and not at left wall
		if(input.isKeyDown(Input.KEY_LEFT) && player.getX() > leftWall.getWidth()) {
			player.setX(player.getX() - mg.playerSpeed*deltaFloat);
		}

		// Walk right when right key pressed and not at right wall
		if(input.isKeyDown(Input.KEY_RIGHT) && player.getMaxX() < (container.getWidth() - rightWall.getWidth())) {
			player.setX(player.getX() + mg.playerSpeed*deltaFloat);
		}
		
		// Shoot laser when spacebar is pressed and no laser is active
		if(input.isKeyPressed(Input.KEY_SPACE) && !shot) {
			shot = true;
			float x = player.getCenterX();
			laser = new Laser(x, container.getHeight() - floor.getHeight(), mg.laserSpeed, mg.laserWidth);
		}
		
		// Update laser
		if(shot) {
			laser.update(this, deltaFloat);
			// Disable laser when it has reached the ceiling
			if(!laser.visible) {
				shot = false;
			}
		}
		
		//Method 1 code
		//boolean noCircleIntersectsDetected = true;
		
		// loop through all active circles
		for(BouncingCircle circle : circleList) {
			//update circles
			circle.update(this, container, deltaFloat);
			
			// if player touches circle (for the first frame)
			if(player.getRectangle().intersects(circle) && !playerIntersect) {
				playerIntersect = true;
				
				//LIVES FUNCTIONALITY
				playerDeath(sbg);
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
					score += circle.getScore();
				}
				
				// if the ball has a radius of 20, split it up
				if(circle.getRadius() >= 20) {
					circleList.addAll(circle.getSplittedCircles(mg));
				}
			}
		}
		
		// if there are no active circles, process to gamover screen
		if(circleList.isEmpty()) {
			mg.score = score;
			sbg.enterState(3);
		}
	}

	/**
	 * Render method
	 * draw things on screen
	 */
	public void render(GameContainer container, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		
		// draw background
		graphics.drawImage(mg.backgroundImage, 0, 0);
		graphics.setColor(Color.white);
		
		// Draw walls, floor and ceiling
		graphics.fill(floor, shapeFill);
		graphics.fill(leftWall, shapeFill);
		graphics.fill(rightWall, shapeFill);
		graphics.fill(ceiling, shapeFill);
		
		
		graphics.drawString("Lives: " + mg.getLifeCount(), 20, container.getHeight()-50);
		graphics.drawString("Score = " + score, 20, container.getHeight()-70);

		// draw all active circles
		for(BouncingCircle circle : circleList) {
			graphics.fill(circle.getCircle(), shapeFill);
		}

		// if shot, draw laser
		if(shot) {
			graphics.fill(laser.getRectangle());
		}
		
		graphics.drawImage(player.getImage(), player.getX(), player.getY());
		// draw player
		//graphics.drawImage(player.getImage(), player.getX(), player.getY());

		// Draw timer countdown bar
		graphics.fillRect(container.getWidth() - COUNTDOWN_BAR_WIDTH - 20, container.getHeight() - 50, COUNTDOWN_BAR_WIDTH + 2, 20);

		graphics.setColor(Color.red);

		graphics.fillRect(container.getWidth() - COUNTDOWN_BAR_WIDTH - 19, container.getHeight() - 49, fractionTimePix, 18);

		graphics.setColor(Color.white);
	}

	
	/**
	 * return id of state
	 */
	@Override
	public int getID() {
		return 1;
	}

	/**
	 * Player death
	 */
	private void playerDeath(StateBasedGame sbg) {
		mg.decreaselifeCount();
		if(mg.getLifeCount() <= 0) {
			mg.score = score;
			sbg.enterState(2);
		} else {
			sbg.enterState(1);
		}
	}

}
