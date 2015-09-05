import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
	private static int LEVEL_POINTS = 1500;
	
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
	private long timeDelta;
	private long timeRemaining;
	private long prevTime;
	private boolean countIn;
	private String countString;
	private boolean playingState;
	private boolean waitEsc;

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
	
	private LevelContainer levels;
	
	
	
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
		// Create levels
		initializeLevels();
		
		
		//Initialize for life counter
		playerIntersect = false;
		
		// If still shooting stop it
		shot = false;
		score = 0;

		startTime = System.currentTimeMillis();
		timeRemaining = TOTAL_TIME;
		prevTime = startTime;
		countIn = true;
		playingState = true;

		// Add player sprite and walls
		playerImage = new Image("resources/" + mg.playerImage);
		player = new Player(container.getWidth()/2 -22.5f,container.getHeight()-100,45,75, playerImage);
		//player = new Rectangle(container.getWidth()/2 -22.5f,container.getHeight()-100,45,75);
		floor = new Rectangle(0,container.getHeight()-25,container.getWidth(),25);
		leftWall = new Rectangle(0,0,10,container.getHeight());
		rightWall = new Rectangle(container.getWidth()-10,0,10,container.getHeight());
		ceiling = new Rectangle(0,0,container.getWidth(),10);
		
		// Add arraylists of circles
		//circleList = new ArrayList<BouncingCircle>(); // active list
		circleList = levels.getLevel(mg.levelCounter).getCircles();
		shotList = new ArrayList<BouncingCircle>(); // list with shot circles
		
		TOTAL_TIME = levels.getLevel(mg.levelCounter).getTime()*1000;

		
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
		if (playingState) {
			// Timer logic
			long curTime = System.currentTimeMillis();
			timeDelta = curTime - prevTime;

			if (countIn) {
				if (timeDelta < 1000) {
					countString = "3";
				} else if (timeDelta < 2000) {
					countString = "2";
				} else if (timeDelta < 3000) {
					countString = "1";
				} else {
					countIn = false;
					prevTime = curTime;
				}
			} else {
				playGame(container, sbg, delta, curTime);
			}
		} else {
			if (input.isKeyDown(Input.KEY_ESCAPE) && !waitEsc) {
				// Reset time countdown
				prevTime = System.currentTimeMillis();
				countIn = true;
				playingState = true;
			}
		}
	}

	private void playGame(GameContainer container, StateBasedGame sbg, int delta, long curTime) {
		timeRemaining -= timeDelta;
		fractionTimePix = COUNTDOWN_BAR_WIDTH * (timeRemaining) / TOTAL_TIME;
		if (timeRemaining <= 0) {
            playerDeath(sbg);
        }
		prevTime = curTime;

		float deltaFloat = delta / 1000f;
		input = container.getInput();

		// Exit application when esc key is pressed at any time during game.
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			waitEsc = true;
			(new Timer()).schedule(new TimerTask() {
				@Override
				public void run() {
					waitEsc = false;
				}
			}, 300);
			playingState = false;
        }

		// Walk left when left key pressed and not at left wall
		if (input.isKeyDown(Input.KEY_LEFT) && player.getX() > leftWall.getWidth()) {
            player.setX(player.getX() - mg.playerSpeed * deltaFloat);
        }

		// Walk right when right key pressed and not at right wall
		if (input.isKeyDown(Input.KEY_RIGHT) && player.getMaxX() < (container.getWidth() - rightWall.getWidth())) {
            player.setX(player.getX() + mg.playerSpeed * deltaFloat);
        }

		// Shoot laser when spacebar is pressed and no laser is active
		if (input.isKeyPressed(Input.KEY_SPACE) && !shot) {
            shot = true;
            float x = player.getCenterX();
            laser = new Laser(x, container.getHeight() - floor.getHeight(), mg.laserSpeed, mg.laserWidth);
        }

		// Update laser
		if (shot) {
            laser.update(this, deltaFloat);
            // Disable laser when it has reached the ceiling
            if (!laser.visible) {
                shot = false;
            }
        }

		//Method 1 code
		//boolean noCircleIntersectsDetected = true;

		// loop through all active circles
		for (BouncingCircle circle : circleList) {
            //update circles
            circle.update(this, container, deltaFloat);

            // if player touches circle (for the first frame)
            if (player.getRectangle().intersects(circle) && !playerIntersect) {
                playerIntersect = true;

                //LIVES FUNCTIONALITY
                playerDeath(sbg);
            }

            // if laser intersects circle
            if (shot && laser.getRectangle().intersects(circle)) {
                // it has been shot and disabled
                shotList.add(circle);
                laser.setVisible(false);
            }

        }

		// loop through the circles that has been shot
		for (BouncingCircle circle : shotList) {
            // if the circle hasn't been handled
            if (!circle.isDone()) {
                // remove circle from active list
                if (circleList.contains(circle)) {
                    circleList.remove(circle);
                    circle.setDone(true);
                    score += circle.getScore();
                }

                // if the ball has a radius of 20, split it up
                if (circle.getRadius() >= 20) {
                    circleList.addAll(circle.getSplittedCircles(mg));
                }
            }
        }

		// if there are no active circles, process to gamover screen


		if(circleList.isEmpty()) {
			score += ((double)timeRemaining / TOTAL_TIME) * LEVEL_POINTS;
			mg.score += score;
			if (mg.levelCounter<levels.size()-1) {
				mg.levelCounter++;
				sbg.enterState(1);
			} else {
			sbg.enterState(3);
			}
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
		graphics.drawString("Score = " + (mg.score + score), 20, container.getHeight()-70);
		graphics.drawString("Level: " + (mg.levelCounter+1), 20, container.getHeight() -90);

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

		// Overlay for count-in
		if (playingState && countIn) {
			Color overLay = new Color(0f, 0f, 0f, 0.5f);
			graphics.setColor(overLay);
			graphics.fillRect(0, 0, container.getWidth(), container.getHeight());

			graphics.setColor(Color.white);
			graphics.drawString(countString, container.getWidth() / 2, container.getHeight() / 2);
		}

		if (!playingState) {
			Color overLay = new Color(0f, 0f, 0f, 0.5f);
			graphics.setColor(overLay);
			graphics.fillRect(0, 0, container.getWidth(), container.getHeight());

			graphics.setColor(Color.white);
			graphics.drawString("Paused", container.getWidth() / 2, container.getHeight() / 2);
		}
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
			mg.score += score;
			sbg.enterState(2);
		} else {
			sbg.enterState(1);
		}
	}
	
	private void initializeLevels() {
		levels = new LevelContainer();
		
		System.out.println("hier");
		
		ArrayList<BouncingCircle> circles = new ArrayList<BouncingCircle>();
		circles.add(new BouncingCircle(100, 200, 30, mg.startingSpeed, -50, mg.gravity));
		Level level = new Level(40, circles);
		levels.add(level);
		
		
		ArrayList<BouncingCircle> circles2 = new ArrayList<BouncingCircle>();
		circles2.add(new BouncingCircle(100, 200, 45, mg.startingSpeed, -50, mg.gravity));
		level = new Level(40, circles2);
		levels.add(level);
		
		
		ArrayList<BouncingCircle> circles3 = new ArrayList<BouncingCircle>();
		circles3.add(new BouncingCircle(100, 200, 65, mg.startingSpeed, -50, mg.gravity));
		level = new Level(100, circles3);
		levels.add(level);
		
		ArrayList<BouncingCircle> circles4 = new ArrayList<BouncingCircle>();
		circles4.add(new BouncingCircle(100, 200, 45, mg.startingSpeed, -50, mg.gravity));
		circles4.add(new BouncingCircle(500, 200, 65, -mg.startingSpeed, -50, mg.gravity));
		level = new Level(125, circles4);
		levels.add(level);
		
		ArrayList<BouncingCircle> circles5 = new ArrayList<BouncingCircle>();
		for (int i=0;i<10;i++) {
			circles5.add(new BouncingCircle(10, 50*i, 10, mg.startingSpeed, -50, mg.gravity));
		}
		level = new Level(60, circles5);
		levels.add(level);
		
		ArrayList<BouncingCircle> circles6 = new ArrayList<BouncingCircle>();
		circles6.add(new BouncingCircle(900, 200, 140, -mg.startingSpeed, -50, mg.gravity));
		level = new Level(120, circles6);
		levels.add(level);
		
		ArrayList<BouncingCircle> circles7 = new ArrayList<BouncingCircle>();
		circles7.add(new BouncingCircle(100, 200, 30, mg.startingSpeed, -50, mg.gravity));
		circles7.add(new BouncingCircle(500, 500, 65, 0, -50, mg.gravity));
		circles7.add(new BouncingCircle(900, 300, 90, -mg.startingSpeed, -50, mg.gravity));
		level =  new Level(150, circles7);
		levels.add(level);
		
		ArrayList<BouncingCircle> circles8 = new ArrayList<BouncingCircle>();
		circles8.add(new BouncingCircle(100, 100, 20, 0, -50, mg.gravity));
		circles8.add(new BouncingCircle(300, 100, 30, 0, -50, mg.gravity));
		circles8.add(new BouncingCircle(500, 100, 45, 0, -50, mg.gravity));
		circles8.add(new BouncingCircle(700, 100, 65, 0, -50, mg.gravity));
		level = new Level (120, circles8);
		levels.add(level);
		
		ArrayList<BouncingCircle> circles9 = new ArrayList<BouncingCircle>();
		circles9.add(new BouncingCircle(100, 200, 140, mg.startingSpeed, -50, mg.gravity));
		circles9.add(new BouncingCircle(900, 200, 140, -mg.startingSpeed, -50, mg.gravity));
		level = new Level(180, circles9);
		levels.add(level);
		
		ArrayList<BouncingCircle> circles10 = new ArrayList<BouncingCircle>();
		for (int i=0;i<20;i++) {
			circles10.add(new BouncingCircle(100, 10*i, 10, 0, -50, mg.gravity));
		}
		level = new Level(40, circles10);
		levels.add(level);
		
		
		
		System.out.println("Hier ook");
		
		
		
	}

}
