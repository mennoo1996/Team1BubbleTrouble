import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
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
	private ArrayList<Gate> gateList;
	private Gate intersectingGate;
	private MyShapeFill shapeFill;
	private Player player;
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

	// Images
	private Image playerImage;
	private Image wallsImage;
	private Image health_0_Image;
	private Image health_1_Image;
	private Image health_2_Image;
	private Image health_3_Image;
	private Image health_4_Image;
	private Image health_5_Image;
	private Image counterBarImage;
	
	TrueTypeFont font;
	
	// Countdown Bar Logic
	private static int COUNTDOWN_BAR_WIDTH = 550;
	private static int COUNTDOWN_BAR_PARTS = 56;
	private float fractionTimePix;
	private int fractionTimeParts;

	// Life counter method 1 code
	private boolean playerIntersect;
	
	// level objects
	protected Laser laser;
	protected MyRectangle floor;
	protected MyRectangle leftWall;
	protected MyRectangle rightWall;
	protected MyRectangle ceiling;
	
	private LevelContainer levels;
	
	// Container width and height coordinates
	private float containerWidth;
	private float containerHeight;
	
	
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
		// Remember container width and height
		containerWidth = container.getWidth();
		containerHeight = container.getHeight();
		
		// Create levels
		initializeLevels();
		
		//Initialize for life counter
		playerIntersect = false;
		
		// If still shooting stop it
		shot = false;
		score = 0;
		
		TOTAL_TIME = levels.getLevel(mg.levelCounter).getTime()*1000;
		startTime = System.currentTimeMillis();
		timeRemaining = TOTAL_TIME;
		prevTime = startTime;
		countIn = true;
		playingState = true;

		// load health images
		health_0_Image = new Image("resources/Terminal/Terminal_Lights_0.png");
		health_1_Image = new Image("resources/Terminal/Terminal_Lights_1.png");
		health_2_Image = new Image("resources/Terminal/Terminal_Lights_2.png");
		health_3_Image = new Image("resources/Terminal/Terminal_Lights_3.png");
		health_4_Image = new Image("resources/Terminal/Terminal_Lights_4.png");
		health_5_Image = new Image("resources/Terminal/Terminal_Lights_5.png");
		
		// countdown bar images
		counterBarImage = new Image("resources/counter_bar.png");
		
		// Add player sprite and walls
		playerImage = new Image("resources/" + mg.playerImage);
		player = new Player(container.getWidth()/2 -22.5f,container.getHeight()-285,45,75, playerImage);
		wallsImage = new Image("resources/walls_blue.png");
		//player = new Rectangle(container.getWidth()/2 -22.5f,container.getHeight()-100,45,75);
		floor = new MyRectangle(0,container.getHeight()-210,container.getWidth(),210);
		leftWall = new MyRectangle(0,0,105,container.getHeight());
		rightWall = new MyRectangle(container.getWidth()-130,0,130,container.getHeight());
		ceiling = new MyRectangle(0,0,container.getWidth(),95);
		
		// Add arraylists of circles
		//circleList = new ArrayList<BouncingCircle>(); // active list
		circleList = levels.getLevel(mg.levelCounter).getCircles();
		shotList = new ArrayList<BouncingCircle>(); // list with shot circles

		
		// Add gates
		gateList = levels.getLevel(mg.levelCounter).getGates();

		
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
		fractionTimeParts = Math.round(COUNTDOWN_BAR_PARTS * (timeRemaining) / TOTAL_TIME);
		
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

		// Check the intersection of a player with a gate
		boolean freeToRoam = true;
		for(Gate machvise : gateList) {
			if(player.getRectangle().intersects(machvise.getRectangle())) {
				freeToRoam = false;
				intersectingGate = machvise;
			}
		}
		// Reset intersecting gate to none if there is no intersection
		if(freeToRoam) {
			intersectingGate = null;
		}
		
		// Walk left when left key pressed and not at left wall OR a gate
		if (input.isKeyDown(Input.KEY_LEFT) && player.getX() > leftWall.getWidth()) {
            if(freeToRoam || (player.getCenterX() < intersectingGate.getRectangle().getCenterX())) {
            	player.setX(player.getX() - mg.playerSpeed * deltaFloat);
            }
        }

		// Walk right when right key pressed and not at right wall OR a gate
		if (input.isKeyDown(Input.KEY_RIGHT) && player.getMaxX() < (container.getWidth() - rightWall.getWidth())) {
           if(freeToRoam || (player.getCenterX() > intersectingGate.getRectangle().getCenterX())) {
        	   player.setX(player.getX() + mg.playerSpeed * deltaFloat);
           }
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
                // if the ball has a radius of 20, split it u
                ArrayList<BouncingCircle> splits = new ArrayList<BouncingCircle>(); 
                if (circle.getRadius() >= 20) {
                	splits = circle.getSplittedCircles(mg);
                    circleList.addAll(splits);
                    // if it was part of the gate requirements, add to new gate requirements
                }
                //if it was part of the gate requirements remove it from the gate requirements (+ add new ones)
                for(Gate gate : gateList) {
                	if(gate.getRequired().contains(circle)) {
                		gate.getRequired().remove(circle);
                	}
                	if(circle.getRadius() >= 20) {
                		gate.addToRequirements(splits);
                	}
                }
            }
        }
		
		// if there are no circles required to be shot by a gate, remove said gate
		ArrayList<Gate> tempGateList = new ArrayList<Gate>();
		for(Gate gate : gateList) {
			if(gate.getRequired().isEmpty()) {
				tempGateList.add(gate);
			}
		}
		for(Gate gate : tempGateList) {
			if(gateList.contains(gate)) {
				gateList.remove(gate);
			}
		}
		// if there are no active circles, process to gameover screen


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

	public ArrayList<Gate> getGateList() {
		return gateList;
	}

	/**
	 * Render method
	 * draw things on screen
	 */
	public void render(GameContainer container, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		
		// draw background layer
		graphics.drawImage(mg.backgroundImage, 0, 0);
		graphics.setColor(Color.white);

		// draw all active circles
		for(BouncingCircle circle : circleList) {
			graphics.fill(circle.getCircle(), shapeFill);
		}
		
		// draw all active gates
		for(Gate gate : gateList) {
			graphics.fill(gate, shapeFill);
		}

		// if shot, draw laser
		if(shot) {
			graphics.fill(laser.getRectangle());
		}
		
		// draw player
		graphics.drawImage(player.getImage(), player.getX(), player.getY());
		//graphics.drawImage(player.getImage(), player.getX(), player.getY());

		// Draw walls, floor and ceiling
//		graphics.fill(floor, shapeFill);
//		graphics.fill(leftWall, shapeFill);
//		graphics.fill(rightWall, shapeFill);
//		graphics.fill(ceiling, shapeFill);
		graphics.drawImage(wallsImage, 0, 0);
		
		// Draw timer countdown bar
//		graphics.fillRect(container.getWidth() - COUNTDOWN_BAR_WIDTH - 600, container.getHeight() - 50, COUNTDOWN_BAR_WIDTH + 2, 20);
//		graphics.setColor(Color.red);
//		graphics.fillRect(container.getWidth() - COUNTDOWN_BAR_WIDTH - 599, container.getHeight() - 49, fractionTimePix, 18);
//		graphics.setColor(Color.white);

		// Draw timer countdown images
		for(int x = 0; x < fractionTimeParts; x++) {
			graphics.drawImage(counterBarImage, container.getWidth()/2 - 80 - 5*(COUNTDOWN_BAR_PARTS) + x*10, container.getHeight() - 60);//
		}
		
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

		// experimenting with stretched laser textures
		//graphics.drawImage(mg.laserHorizontalImage, 100, 70, 1495, 105, 0, 0, 128, 35);
		//graphics.drawImage(mg.laserHorizontalImage, 100, 800, 1495, 835, 0, 0, 128, 35);
		//graphics.drawImage(mg.laserVerticalImage, 100, 100, 135, 1400, 0, 0, 35, 128);
		
		// draw foreground layer
		graphics.drawImage(mg.foreGroundImage, 0, 0);
		
		// draw terminal
		graphics.drawImage(mg.terminalImage, 0, 0);
		
		switch(mg.getLifeCount()) {
			case(0) : 
				graphics.drawImage(health_0_Image, 0, 0);
			break;
			case(1) : 
				graphics.drawImage(health_1_Image, 0, 0);
			break;
			case(2) : 
				graphics.drawImage(health_2_Image, 0, 0);
			break;
			case(3) : 
				graphics.drawImage(health_3_Image, 0, 0);
			break;
			case(4) : 
				graphics.drawImage(health_4_Image, 0, 0);
			break;
			case(5) : 
				graphics.drawImage(health_5_Image, 0, 0);
			break;
		}
		
		graphics.setColor(Color.green);
		graphics.drawString("Debug values ", 20, container.getHeight()-90);
		graphics.drawString("Lives: " + mg.getLifeCount(), 20, container.getHeight()-70);
		graphics.drawString("Score = " + (mg.score + score), 20, container.getHeight()-50);
		graphics.drawString("Level: " + (mg.levelCounter+1), 20, container.getHeight() -30);
		
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
		
		//First level, test with gate
		
		ArrayList<BouncingCircle> circles = new ArrayList<BouncingCircle>();
		BouncingCircle circle11 = new BouncingCircle(200, 100, 30, mg.startingSpeed, -50, mg.gravity);
		circles.add(new BouncingCircle(1200, 100, 30, mg.startingSpeed, -50, mg.gravity));
		circles.add(circle11);
		
		ArrayList<Gate> gates = new ArrayList<Gate>();
		Gate gate11 = new Gate(containerWidth/2+50f,0,45,containerHeight);
		gate11.addToRequirements(circle11);
		gates.add(gate11);
		//Height to height-150?
		Level level = new Level(40, circles, gates);
		levels.add(level);
		
		
		ArrayList<BouncingCircle> circles2 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates2 = new ArrayList<Gate>();
		circles2.add(new BouncingCircle(200, 200, 45, mg.startingSpeed, -50, mg.gravity));
		level = new Level(40, circles2, gates2);
		levels.add(level);
		
		
		ArrayList<BouncingCircle> circles3 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates3 = new ArrayList<Gate>();
		circles3.add(new BouncingCircle(200, 200, 65, mg.startingSpeed, -50, mg.gravity));
		level = new Level(100, circles3, gates3);
		levels.add(level);
		
		ArrayList<BouncingCircle> circles4 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates4 = new ArrayList<Gate>();
		circles4.add(new BouncingCircle(200, 200, 45, mg.startingSpeed, -50, mg.gravity));
		circles4.add(new BouncingCircle(500, 200, 65, -mg.startingSpeed, -50, mg.gravity));
		level = new Level(125, circles4, gates4);
		levels.add(level);
		
		ArrayList<BouncingCircle> circles5 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates5 = new ArrayList<Gate>();
		for (int i=0;i<10;i++) {
			circles5.add(new BouncingCircle(50*i+200, 300, 10, mg.startingSpeed, -50, mg.gravity));
		}
		level = new Level(60, circles5, gates5);
		levels.add(level);
		
		ArrayList<BouncingCircle> circles6 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates6 = new ArrayList<Gate>();
		circles6.add(new BouncingCircle(900, 200, 140, -mg.startingSpeed, -50, mg.gravity));
		level = new Level(120, circles6, gates6);
		levels.add(level);
		
		ArrayList<BouncingCircle> circles7 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates7 = new ArrayList<Gate>();
		circles7.add(new BouncingCircle(200, 200, 30, mg.startingSpeed, -50, mg.gravity));
		circles7.add(new BouncingCircle(500, 500, 65, 0, -50, mg.gravity));
		circles7.add(new BouncingCircle(900, 300, 90, -mg.startingSpeed, -50, mg.gravity));
		level =  new Level(150, circles7, gates7);
		levels.add(level);
		
		ArrayList<BouncingCircle> circles8 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates8 = new ArrayList<Gate>();
		circles8.add(new BouncingCircle(200, 100, 20, 0, -50, mg.gravity));
		circles8.add(new BouncingCircle(300, 100, 30, 0, -50, mg.gravity));
		circles8.add(new BouncingCircle(500, 100, 45, 0, -50, mg.gravity));
		circles8.add(new BouncingCircle(700, 100, 65, 0, -50, mg.gravity));
		level = new Level (120, circles8, gates8);
		levels.add(level);
		
		ArrayList<BouncingCircle> circles9 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates9 = new ArrayList<Gate>();
		circles9.add(new BouncingCircle(200, 200, 140, mg.startingSpeed, -50, mg.gravity));
		circles9.add(new BouncingCircle(900, 200, 140, -mg.startingSpeed, -50, mg.gravity));
		level = new Level(180, circles9, gates9);
		levels.add(level);
		
		ArrayList<BouncingCircle> circles10 = new ArrayList<BouncingCircle>();
		ArrayList<Gate> gates10 = new ArrayList<Gate>();
		for (int i=0;i<20;i++) {
			circles10.add(new BouncingCircle(200, 10*i, 10, 0, -50, mg.gravity));
		}
		level = new Level(40, circles10, gates10);
		levels.add(level);
		
		
		
		System.out.println("Hier ook");
		
		
		
	}
	
	public void setCeiling(MyRectangle c) {
		ceiling = c;
	}

}
