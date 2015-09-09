import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This class is the state that we are in during gameplay.
 * It contains basically all the game logic.
 * @author Menno
 *
 */
public class GameState extends BasicGameState {

	
	
	private static int totaltime;
	
	private MainGame mg;
	private ArrayList<BouncingCircle> circleList;
	private ArrayList<BouncingCircle> shotList;
	private ArrayList<FloatingScore> floatingScoreList;
	private ArrayList<Gate> gateList;

	private Player player;
	private int score;
	private long startTime;
	private long timeDelta;
	private long timeRemaining;
	private long prevTime;
	private boolean countIn;
	private boolean playingState;
	private boolean waitEsc;
	
	// Images
	private Image playerImage;
	private Image wallsImage;
	private Image health1Image;
	private Image health2Image;
	private Image health3Image;
	private Image health4Image;
	private Image health5Image;
	private Image nobuttonImage;
	private Image[] ballsImages;
	private Image ceilingImage;
	private Image laserbeamimage;
	private Image lasertipimage;
	private Image counterBarImage;
	private Image gateUpper;
	private Image gateLower;
	
	// Countdown Bar Logic
	private static final int COUNTDOWN_BAR_PARTS = 56;
	private int fractionTimeParts;
	private boolean waitForLevelEnd = false;
	
	// level objects
	private Laser laser;
	private MyRectangle floor;
	private MyRectangle leftWall;
	private MyRectangle rightWall;
	private MyRectangle ceiling;
	private Input savedInput;
	private boolean shot;
	
	private LevelContainer levels;
	
	// CONSTANTS
	private static final int LEVEL_POINTS = 1500;
	private static final int SECOND_TO_MS_FACTOR = 1000;
	private static final float SECOND_TO_MS_FACTOR_FLOAT = 1000f;
	private static final int PLAYER_X_DEVIATION = 80;
	private static final int PLAYER_Y_DEVIATION = 295;
	private static final int PLAYER_WIDTH = 60;
	private static final int PLAYER_HEIGHT = 92;
	private static final int FLOOR_Y_DEVIATION = 210;
	private static final int FLOOR_HEIGHT = 210;
	private static final int LEFT_WALL_WIDTH = 105;
	private static final int RIGHT_WALL_X_DEVIATION = 130;
	private static final int RIGHT_WALL_WIDTH = 130;
	private static final int CEILING_HEIGHT = 110;
	private static final float COUNT_IN_TIME = 3000f;
	private static final float TIME_REMAINING_FACTOR = 0.01f;
	private static final int MINIMUM_SPLIT_RADIUS = 20;
	private static final int PAUSE_FACTOR = 300;
	private static final int CEILING_DRAW_X_DEVIATION = 10;
	private static final int CEILING_DRAW_Y_DEVIATION = 25;
	private static final int LEVEL_STRING_X_DEVIATION = 270;
	private static final int LEVEL_STRING_Y_DEVIATION = 84;
	private static final int SCORE_STRING_Y_DEVIATION = 84;
	private static final int VERSION_STRING_X = 70;
	private static final int VERSION_STRING_Y_DEVIATION = 190;
	private static final int SPRITE_SHEET_THREE = 3;
	private static final int SPRITE_SHEET_FOUR = 4;
	private static final float MOVEMENT_COUNTER_FACTOR = 0.5f;
	private static final int PLAYER_DRAW_X_DEVIATION = 30;
	private static final int PLAYER_DRAW_Y_DEVIATION = 23;
	private static final int COUNTER_BAR_X_DEVIATION = 80;
	private static final int COUNTER_BAR_Y_DEVIATION = 60;
	private static final int COUNTER_BAR_PARTS_FACTOR = 5;
	private static final int COUNTER_BAR_X_FACTOR = 10;
	private static final int LASER_X_DEVIATION = 18;
	private static final int LASER_TIP_Y_DEVIATION = 14;
	private static final int LASER_BEAM_Y_DEVIATION = 13;
	private static final int LASER_BEAM_X2_DEVIATION = 17;
	private static final int LASER_BEAM_SRCX2 = 35;
	private static final int LASER_BEAM_SRCY2 = 300;
	private static final int GATE_LEFT = 11;
	private static final int GATE_DOWN = 9;
	private static final int GATE_Y_DEVIATION = 15;
	private static final int GATE_Y_FACTOR = 348;
	private static final int GATE_UP = 9;
	private static final int GATE_LEFT_LOWER = 9;
	private static final int GATE_Y_FACTOR_LOWER = 347;
	private static final int CIRCLE_DRAW_OFFSET = 13;
	private static final int MINIMUM_RADIUS = 10;
	private static final int RADIUS_2 = 20;
	private static final int RADIUS_3 = 30;
	private static final int RADIUS_4 = 45;
	private static final int RADIUS_5 = 65;
	private static final int RADIUS_6 = 90;
	private static final int BALL_IMAGE_THREE = 3;
	private static final int BALL_IMAGE_FOUR = 4;
	private static final int BALL_IMAGE_FIVE = 5;
	private static final int HEALTH_IMAGE_THREE = 3;
	private static final int HEALTH_IMAGE_FOUR = 4;
	private static final int HEALTH_IMAGE_FIVE = 5;
	private static final float PAUSE_OVERLAY_COLOR_FACTOR = 0.5f;
	private static final int PAUSED_RECT_Y_DEVIATION = 150;
	private static final int PAUSED_STRING_X_DEVIATION = 130;
	private static final int PAUSED_STRING_Y_DEVIATION = 53;
	private static final float COUNT_FACTOR = 15f;
	private static final int STARTING_STRING_X_DEVIATION = 90;
	private static final int STARTING_COUNT_X_DEVIATION = 5;
	private static final int WHOLE_CIRCLE_DEGREES = 360;
	private static final int COUNT_IN_DEGREES = 15;
	private static final int COUNTER_BAR_ROTATION_X = 12;
	private static final int COUNTER_BAR_ROTATION_Y = 50;
	private static final int COUNTER_BAR_DRAW_X_DEVIATION = 10;
	private static final int COUNTER_BAR_DRAW_Y_DEVIATION = 91;
	private static final int AMOUNT_OF_BALLS = 6;
	private static final int FLOATING_SCORE_BRIGHTNESS = 1;
	
	// Level ending, empty bar
	
	/**
	 * constructor.
	 * 
	 * @param mg	- the maingame this state belongs to
	 */
	public GameState(MainGame mg) {
		this.mg = mg;
	}
	
	/**
	 * setup all variables when entering this state.
	 * @param container the Container this state is part of
	 * @param arg1 The statebasedgame this state is part of
	 * @throws SlickException sometimes.
	 */
	@Override
	public void enter(GameContainer container, StateBasedGame arg1) throws SlickException {
		// If still shooting stop it
		setShot(false);
		score = 0;
		levels.initialize();
		
		totaltime = levels.getLevel(mg.getLevelCounter()).getTime() * SECOND_TO_MS_FACTOR;
		startTime = System.currentTimeMillis();
		timeRemaining = totaltime;
		prevTime = startTime;
		countIn = true;
		playingState = true;
		// Add player sprite and walls
		playerImage = new Image("resources/" + mg.getPlayerImage());
		player = new Player(container.getWidth() / 2 - PLAYER_X_DEVIATION,
				container.getHeight() - PLAYER_Y_DEVIATION, PLAYER_WIDTH, PLAYER_HEIGHT,
				playerImage, mg);
		setFloor(new MyRectangle(0, container.getHeight() - FLOOR_Y_DEVIATION,
				container.getWidth(), FLOOR_HEIGHT));
		setLeftWall(new MyRectangle(0, 0, LEFT_WALL_WIDTH, container.getHeight()));
		setRightWall(new MyRectangle(container.getWidth() - RIGHT_WALL_X_DEVIATION,
				0, RIGHT_WALL_WIDTH, container.getHeight()));
		setCeiling(new MyRectangle(0, 0, container.getWidth(), CEILING_HEIGHT));
		// Add arraylists of circles
		floatingScoreList = new ArrayList<FloatingScore>();
		circleList = levels.getLevel(mg.getLevelCounter()).getCircles();
		shotList = new ArrayList<BouncingCircle>(); // list with shot circles
		// Add gates
		gateList = levels.getLevel(mg.getLevelCounter()).getGates();
	}
	
	
	/**
	 * load resources when state is initialised.
	 * @param container The game container this state is used in
	 * @param arg1 The state based game that uses this state
	 * @throws SlickException when something goes wrong
	 */
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		loadImages();
		setFloor(new MyRectangle(0, container.getHeight() - FLOOR_Y_DEVIATION,
				container.getWidth(), FLOOR_HEIGHT));
		setLeftWall(new MyRectangle(0, 0, LEFT_WALL_WIDTH, container.getHeight()));
		setRightWall(new MyRectangle(container.getWidth() - RIGHT_WALL_X_DEVIATION,
				0, RIGHT_WALL_WIDTH, container.getHeight()));
		setCeiling(new MyRectangle(0, 0, container.getWidth(), CEILING_HEIGHT));
		
		levels = new LevelContainer(mg);
	}


	
	/**
	 * update method, called on each frame refresh.
	 * @param container The GameContainer this state is used in
	 * @param sbg The state based game that uses this state
	 * @param delta The time in ms since the last frame
	 * @throws SlickException if something goes wrong
	 */
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {

		setSavedInput(container.getInput());
		if (playingState) {
			// Timer logic
			long curTime = System.currentTimeMillis();
			timeDelta = curTime - prevTime;

			if (countIn) {
				if (timeDelta >= COUNT_IN_TIME) {
					countIn = false;
					prevTime = curTime;
				}
			} else {
				playGame(container, sbg, delta, curTime);
			}
		} else {
			if (getSavedInput().isKeyDown(Input.KEY_ESCAPE) && !waitEsc) {
				// Reset time countdown
				prevTime = System.currentTimeMillis();
				countIn = true;
				playingState = true;
			}
		}
	}

	private void playGame(GameContainer container, StateBasedGame sbg, int delta, long curTime) {
		processTime(sbg, curTime);

		float deltaFloat = delta / SECOND_TO_MS_FACTOR_FLOAT;

		player.update(deltaFloat);
		processPause();
		processCircles(container, sbg, deltaFloat);
		updateFloatingScores(deltaFloat);
		// if there are no circles required to be shot by a gate, remove said gate
		updateGateExistence(deltaFloat);
		// if there are no active circles, process to gameover screen

		if (circleList.isEmpty()) {
			endLevel(sbg);
		}

	}

	private void processTime(StateBasedGame sbg, long curTime) {
		timeRemaining -= timeDelta;
		fractionTimeParts = Math.round(COUNTDOWN_BAR_PARTS * (timeRemaining) / totaltime);

		if (waitForLevelEnd) {
			timeRemaining -= TIME_REMAINING_FACTOR * totaltime;
			if (timeRemaining < 1) {
				timeRemaining = 1;
			}
		}

		if (timeRemaining <= 0) {
            playerDeath(sbg);
        }
		prevTime = curTime;
	}

	

	private void processCircles(GameContainer container, StateBasedGame sbg, float deltaFloat) {
		ArrayList<BouncingCircle> ceilingList = new ArrayList<BouncingCircle>();
		updateActiveCircles(container, sbg, deltaFloat, ceilingList);
		removeCeilingCircles(ceilingList);
		updateShotCircles();
	}

	private void updateShotCircles() {
		for (BouncingCircle circle : shotList) {
            // if the circle hasn't been handled
            if (!circle.isDone()) {
            	floatingScoreList.add(new FloatingScore(circle));
                // remove circle from active list
                if (circleList.contains(circle)) {
                    circleList.remove(circle);
                    circle.setDone(true);
                    score += circle.getScore();
                }
                // if the ball has a radius of 20, split it u
                ArrayList<BouncingCircle> splits = new ArrayList<BouncingCircle>();
                if (circle.getRadius() >= MINIMUM_SPLIT_RADIUS) {
                	splits = circle.getSplittedCircles(mg);
                    circleList.addAll(splits);
                    // if it was part of the gate reqs, add to new gate reqs
                }
                // if it was part of the gate reqs remove it from the gate reqs (+ add new ones)
                for (Gate gate : gateList) {
                	if (gate.getRequired().contains(circle)) {
                		gate.getRequired().remove(circle);
                	}
                	if (circle.getRadius() >= MINIMUM_SPLIT_RADIUS) {
                		gate.addToRequirements(splits);
                	}
                }
            }
        }
	}

	private void removeCeilingCircles(ArrayList<BouncingCircle> ceilingList) {
		for (BouncingCircle circle : ceilingList) {
			if (circleList.contains(circle)) {
				circleList.remove(circle);
			}
		}
	}

	private void updateActiveCircles(GameContainer container, StateBasedGame sbg,
			float deltaFloat, ArrayList<BouncingCircle> ceilingList) {
		for (BouncingCircle circle : circleList) {
            //update circles
            circle.update(this, container, deltaFloat);

            // if player touches circle (for the first frame)
            if (player.getRectangle().intersects(circle)) {

                //LIVES FUNCTIONALITY
                playerDeath(sbg);
            }

            // if laser intersects circle
            if (isShot() && getLaser().getRectangle().intersects(circle)) {
                // it has been shot and disabled
                shotList.add(circle);
                getLaser().setVisible(false);
            }

            if (circle.isHitCeiling()) {
            	ceilingList.add(circle);
            }

        }
	}

	private void updateFloatingScores(float deltaFloat) {
		Iterator<FloatingScore> scoreListIterator = floatingScoreList.iterator();
		while (scoreListIterator.hasNext()) {
			FloatingScore score = scoreListIterator.next();
			if (score.isDead()) {
				scoreListIterator.remove();
			} else {
				score.update(deltaFloat, timeDelta);
			}
		}
	}
	
	private void updateGateExistence(float deltaFloat) {
		ArrayList<Gate> tempGateList = new ArrayList<Gate>();
		for (Gate gate : gateList) {
			if (gate.getRequired().isEmpty()) {
				tempGateList.add(gate);
				gate.setFading(true);
			}
		}
		for (Gate gate : tempGateList) {
			if (gateList.contains(gate) && gate.isDone()) {
				gateList.remove(gate);
			} else if (gateList.contains(gate) && gate.isFading()) {
				gate.update(deltaFloat);
				}
		}
	}

	private void endLevel(StateBasedGame sbg) {
		if (!waitForLevelEnd) {
            waitForLevelEnd = true;
        }
		if (waitForLevelEnd && timeRemaining == 1) {
            score += ((double) timeRemaining / totaltime) * LEVEL_POINTS; // add level-ending score
            mg.setScore(mg.getScore() + score); // update total score
            int levelCounter = mg.getLevelCounter();
			if (levelCounter < levels.size() - 1) {
                waitForLevelEnd = false;
                mg.setLevelCounter(mg.getLevelCounter() + 1);
                sbg.enterState(mg.getGameState()); // next level
            } else {
                waitForLevelEnd = false;
                sbg.enterState(mg.getWonState()); // game completed
            }
        }
	}

	

	private void processPause() {
		if (getSavedInput().isKeyDown(Input.KEY_ESCAPE)) {
			waitEsc = true;
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					waitEsc = false;
				}
			}, PAUSE_FACTOR);
			playingState = false;
        }
	}

	/**
	 * Get the gate list.
	 * @return The gatelist.
	 */
	public ArrayList<Gate> getGateList() {
		return gateList;
	}

	/**
	 * Render method.
	 * draw things on screen
	 * @param container The gamecontainer this state is used in.
	 * @param arg1 The state based game that uses this state.
	 * @param graphics The graphics object used for drawing things on screen
	 * @throws SlickException when something goes wrong.
	 */
	public void render(GameContainer container, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		// draw background layer
		graphics.drawImage(mg.getBackgroundImage(), 0, 0);
		graphics.setColor(Color.white);
		// draw all active circles
		drawActiveCircles(graphics);
		drawFloatingScores();
		graphics.drawImage(ceilingImage, getLeftWall().getWidth() - CEILING_DRAW_X_DEVIATION,
				getCeiling().getHeight() - CEILING_DRAW_Y_DEVIATION);
		drawGatesLaser(container, graphics);
		// draw player
		drawPlayer(container, graphics);
		// Draw walls, floor and ceiling
		graphics.drawImage(wallsImage, 0, 0);
		// Draw timer countdown bar
		drawCountdownBar(container, graphics);
		// Draw level/Score data
		mg.getDosFont().drawString(container.getWidth() / 2 - LEVEL_STRING_X_DEVIATION,
				container.getHeight() - LEVEL_STRING_Y_DEVIATION, "Level: "
						+ Integer.toString(mg.getLevelCounter() + 1));
		mg.getDosFont().drawString(container.getWidth() / 2, container.getHeight() 
				- SCORE_STRING_Y_DEVIATION, "Score: " + Integer.toString(mg.getScore() + score));
		// Pause overlay and counter
		if (playingState && countIn) {
			drawCountIn(container, graphics);
		}
		drawMiscellaneous(container, graphics);
	}
	
	private void drawGatesLaser(GameContainer container, Graphics graphics) {
		// draw all active gates
				drawActiveGates(container, graphics);
				// if shot, draw laser
				if (isShot()) {
					//graphics.fill(laser.getRectangle());
					drawWeapon(graphics);
				}
	}
	
	private void drawMiscellaneous(GameContainer container, Graphics graphics) {
		if (!playingState) {
			drawPausedScreen(container, graphics);
		}
		// draw version number
				mg.getDosFont().drawString(VERSION_STRING_X, container.getHeight() 
						- VERSION_STRING_Y_DEVIATION, "#Version 0.98");
				// draw foreground layer
				graphics.drawImage(mg.getForeGroundImage(), 0, 0);
				// draw terminal
				graphics.drawImage(mg.getTerminalImage(), 0, 0);
				// disable button when paused
				if (!playingState) {
					graphics.drawImage(nobuttonImage, 0, 0);
				}
				// show correct health lights
				drawHealth(graphics);
	}
	
	private void drawPlayer(GameContainer container, Graphics graphics) {
		if (player.getMovement() == 2) {
			player.incrementMovementCounter();
			int sp = SPRITE_SHEET_THREE;
			if (player.getMovementCounter() > player.getMovementCounter_Max() 
					* MOVEMENT_COUNTER_FACTOR) {
				sp = SPRITE_SHEET_FOUR;
			}
			graphics.drawImage(player.getSpritesheet().getSprite(sp, 0), player.getX() 
					- PLAYER_DRAW_X_DEVIATION, player.getY() - PLAYER_DRAW_Y_DEVIATION);
		} else if (player.getMovement() == 1) {
			player.incrementMovementCounter();
			int sp = 1;
			if (player.getMovementCounter() > player.getMovementCounter_Max()
					* MOVEMENT_COUNTER_FACTOR) {
				sp = 0;
			}
			graphics.drawImage(player.getSpritesheet().getSprite(sp, 0), player.getX()
					- PLAYER_DRAW_X_DEVIATION, player.getY() - PLAYER_DRAW_Y_DEVIATION);
		} else {
			player.resetMovementCounter();
			graphics.drawImage(player.getSpritesheet().getSprite(2, 0), player.getX()
					- PLAYER_DRAW_X_DEVIATION, player.getY() - PLAYER_DRAW_Y_DEVIATION);
		}
		player.setMovement(0);
	}

	private void drawCountdownBar(GameContainer container, Graphics graphics) {
		for (int x = 0; x < fractionTimeParts; x++) {
			//counterBarImage.rotate(0.5f*x); // EPIC
			graphics.drawImage(counterBarImage, container.getWidth() / 2 - COUNTER_BAR_X_DEVIATION 
					- COUNTER_BAR_PARTS_FACTOR * (COUNTDOWN_BAR_PARTS) + x * COUNTER_BAR_X_FACTOR, 
					container.getHeight() - COUNTER_BAR_Y_DEVIATION);
			//counterBarImage.rotate(-10*x); // EPIC
		}
	}

	private void drawWeapon(Graphics graphics) {
		graphics.drawImage(lasertipimage, getLaser().getX() - LASER_X_DEVIATION,
				getLaser().getY() - LASER_TIP_Y_DEVIATION);
		graphics.drawImage(laserbeamimage, getLaser().getX() - LASER_X_DEVIATION,
				getLaser().getRectangle().getMinY() + LASER_BEAM_Y_DEVIATION, getLaser().getX()
				+ LASER_BEAM_X2_DEVIATION, getLaser().getRectangle().getMaxY(), 0, 0,
				LASER_BEAM_SRCX2, LASER_BEAM_SRCY2);
	}

	private void drawActiveGates(GameContainer container, Graphics graphics) {
		for (Gate gate : gateList) {
			//upper
			int left = GATE_LEFT;
			int down = GATE_DOWN;
			float x = gate.getMinX() - left;
			float y = getCeiling().getHeight() - GATE_Y_DEVIATION;
			float x2 = x + gateUpper.getWidth();
			float y2 = getCeiling().getHeight() + GATE_Y_FACTOR * gate.getHeightPercentage() 
				+ down - GATE_Y_DEVIATION;
			float srcx = 0;
			float srcy = gateUpper.getHeight() - GATE_Y_FACTOR * gate.getHeightPercentage();
			float srcx2 = gateUpper.getWidth();
			float srcy2 = gateUpper.getHeight();
			graphics.drawImage(gateUpper, x, y, x2, y2, srcx, srcy, srcx2, srcy2); 
			//lower
			left = GATE_LEFT_LOWER;
			float up = GATE_UP;
			x = gate.getMinX() - left - 1;
			y = container.getHeight() - getFloor().getHeight()
					- GATE_Y_FACTOR_LOWER * gate.getHeightPercentage() - up;
			x2 = x + gateLower.getWidth() - 1;
			y2 = container.getHeight() - getFloor().getHeight();
			srcx = 0;
			srcy = 0;
			srcx2 = gateLower.getWidth();
			srcy2 = GATE_Y_FACTOR_LOWER * gate.getHeightPercentage();
			graphics.drawImage(gateLower, x, y, x2, y2, srcx, srcy, srcx2, srcy2);
		}
	}

	private void drawActiveCircles(Graphics graphics) {
		for (BouncingCircle circle : circleList) {
			//graphics.fill(circle.getCircle(), shapeFill);
			int r = (int) circle.getRadius();
			int offset = CIRCLE_DRAW_OFFSET;
			switch (r) {
				case(RADIUS_6) : graphics.drawImage(ballsImages[0],
						circle.getMinX() - offset, circle.getMinY() - offset); break;
				case(RADIUS_5) : graphics.drawImage(ballsImages[1],
						circle.getMinX() - offset, circle.getMinY() - offset); break;
				case(RADIUS_4) : graphics.drawImage(ballsImages[2],
						circle.getMinX() - offset, circle.getMinY() - offset); break;
				case(RADIUS_3) : 
					graphics.drawImage(ballsImages[BALL_IMAGE_THREE],
						circle.getMinX() - offset, circle.getMinY() - offset); break;
				case(RADIUS_2) : graphics.drawImage(ballsImages[BALL_IMAGE_FOUR],
						circle.getMinX() - offset, circle.getMinY() - offset); break;
				case(MINIMUM_RADIUS) : graphics.drawImage(ballsImages[BALL_IMAGE_FIVE],
						circle.getMinX() - offset, circle.getMinY() - offset); break;
				default:
			}
		}
	}

	private void drawFloatingScores() {
		for (FloatingScore score : floatingScoreList) {
			mg.getDosFont().drawString(score.getX(), score.getY(), 
					Integer.toString(score.getScore()), 
					new Color(FLOATING_SCORE_BRIGHTNESS, FLOATING_SCORE_BRIGHTNESS, 
							FLOATING_SCORE_BRIGHTNESS, score.getOpacity()));
		}
	}
	
	private void drawHealth(Graphics graphics) {
		switch (mg.getLifeCount()) {
			case(1) :
				graphics.drawImage(health1Image, 0, 0);
			break;
			case(2) :
				graphics.drawImage(health2Image, 0, 0);
			break;
			case(HEALTH_IMAGE_THREE) :
				graphics.drawImage(health3Image, 0, 0);
			break;
			case(HEALTH_IMAGE_FOUR) :
				graphics.drawImage(health4Image, 0, 0);
			break;
			case(HEALTH_IMAGE_FIVE) :
				graphics.drawImage(health5Image, 0, 0);
			break;
			default:
		}
	}

	private void drawPausedScreen(GameContainer container, Graphics graphics) {
		Color overLay = new Color(0f, 0f, 0f, PAUSE_OVERLAY_COLOR_FACTOR);
		graphics.setColor(overLay);
		graphics.fillRect(0, 0, container.getWidth(), container.getHeight()
				- PAUSED_RECT_Y_DEVIATION);
		mg.getDosFont().drawString(container.getWidth() / 2 - PAUSED_STRING_X_DEVIATION, 
				container.getHeight() / 2 - PAUSED_STRING_Y_DEVIATION, "Game is paused...");
	}

	private void drawCountIn(GameContainer container, Graphics graphics) {
		int count = (int) Math.ceil((COUNT_IN_TIME - timeDelta) / SECOND_TO_MS_FACTOR),
				amount = Math.round((COUNT_IN_TIME - timeDelta) / COUNT_IN_TIME * COUNT_FACTOR);

		graphics.setColor(new Color(0f, 0f, 0f, PAUSE_OVERLAY_COLOR_FACTOR));
		graphics.fillRect(0, 0, container.getWidth(), container.getHeight() 
				- PAUSED_RECT_Y_DEVIATION);
		mg.getDosFont().drawString(container.getWidth() / 2 - STARTING_STRING_X_DEVIATION, 
				container.getHeight() / 2 - PAUSED_STRING_X_DEVIATION, "Starting in");
		mg.getDosFont().drawString(container.getWidth() / 2 - STARTING_COUNT_X_DEVIATION, 
				container.getHeight() / 2 - PAUSED_STRING_Y_DEVIATION, Integer.toString(count));

		for (int i = 0; i < amount; i++) {
            float degree = i * (WHOLE_CIRCLE_DEGREES / COUNT_IN_DEGREES);
            counterBarImage.setCenterOfRotation(COUNTER_BAR_ROTATION_X, COUNTER_BAR_ROTATION_Y);
            counterBarImage.rotate(degree);
            graphics.drawImage(counterBarImage, container.getWidth() / 2 
            		- COUNTER_BAR_DRAW_X_DEVIATION, container.getHeight() / 2 
            		- COUNTER_BAR_DRAW_Y_DEVIATION);
            counterBarImage.rotate(-degree);
        }
	}


	/**
	 * return id of state.
	 * @return the id of gamestate
	 */
	@Override
	public int getID() {
		return 1;
	}

	/**
	 * Player death.
	 * @param sbg The stateBasedGame that uses this state.
	 */
	private void playerDeath(StateBasedGame sbg) {
		mg.decreaselifeCount();
		if (mg.getLifeCount() <= 0) {
			mg.setScore(mg.getScore() + score);
			sbg.enterState(mg.getGameOverState());
		} else {
			sbg.enterState(mg.getGameState());
		}
	}
	

	
		
		

	private void loadImages() throws SlickException {
		loadHealthAndBallImages();
		// button image
		nobuttonImage = new Image("resources/Terminal/Terminal_No_Button.png");
		// laser images
		laserbeamimage = new Image("resources/laser/laser_beam_blue.png");
		lasertipimage = new Image("resources/laser/laser_tip_blue.png");
		// countdown bar images
		counterBarImage = new Image("resources/counter_bar.png");
		// gate images
		gateUpper = new Image("resources/gate_upper.png");
		gateLower = new Image("resources/gate_lower.png");
		// walls image
		wallsImage = new Image("resources/walls_blue.png");
		// ceiling image
		ceilingImage = new Image("resources/ceiling.png");
		// balls images
		
		// button image
		nobuttonImage = new Image("resources/Terminal/Terminal_No_Button.png");
		// laser images
		laserbeamimage = new Image("resources/laser/laser_beam_blue.png");
		lasertipimage = new Image("resources/laser/laser_tip_blue.png");
		// countdown bar images
		counterBarImage = new Image("resources/counter_bar.png");
	}
	
	private void loadHealthAndBallImages() throws SlickException {
		// load health images
		health1Image = new Image("resources/Terminal/Terminal_Lights_1.png");
		health2Image = new Image("resources/Terminal/Terminal_Lights_2.png");
		health3Image = new Image("resources/Terminal/Terminal_Lights_3.png");
		health4Image = new Image("resources/Terminal/Terminal_Lights_4.png");
		health5Image = new Image("resources/Terminal/Terminal_Lights_5.png");
		
		ballsImages = new Image[AMOUNT_OF_BALLS];
		ballsImages[0] = new Image("resources/Balls/Ball_90.png");
		ballsImages[1] = new Image("resources/Balls/Ball_65.png");
		ballsImages[2] = new Image("resources/Balls/Ball_45.png");
		ballsImages[BALL_IMAGE_THREE] = new Image("resources/Balls/Ball_30.png");
		ballsImages[BALL_IMAGE_FOUR] = new Image("resources/Balls/Ball_20.png");
		ballsImages[BALL_IMAGE_FIVE] = new Image("resources/Balls/Ball_10.png");
	}
	
	/**
	 * Set the ceiling.
	 * @param c the ceiling to set
	 */
	public void setCeiling(MyRectangle c) {
		ceiling = c;
	}
	
	/**
	 * Set the floor.
	 * @param floor the floor to set
	 */
	public void setFloor(MyRectangle floor) {
		this.floor = floor;
	}
	
	/**
	 * Set the left wall.
	 * @param leftWall the left wall to set
	 */
	public void setLeftWall(MyRectangle leftWall) {
		this.leftWall = leftWall;
	}
	
	/**
	 * Set the right wall.
	 * @param rightWall the right wall to set
	 */
	public void setRightWall(MyRectangle rightWall) {
		this.rightWall = rightWall;
	}
	
	/**
	 * set the gatelist.
	 * @param gatelist the gatelist to set
	 */
	public void setGateList(ArrayList<Gate> gatelist) {
		this.gateList = gatelist;
	}

	/**
	 * Get the MainGame.
	 * @return the maingame
	 */
	public MainGame getMg() {
		return mg;
	}

	/**
	 * Set the maingame.
	 * @param mg the maingame to set.
	 */
	public void setMg(MainGame mg) {
		this.mg = mg;
	}

	/**
	 * @return the floor
	 */
	public MyRectangle getFloor() {
		return floor;
	}

	/**
	 * @return the ceiling
	 */
	public MyRectangle getCeiling() {
		return ceiling;
	}

	/**
	 * @return the leftWall
	 */
	public MyRectangle getLeftWall() {
		return leftWall;
	}

	/**
	 * @return the rightWall
	 */
	public MyRectangle getRightWall() {
		return rightWall;
	}

	/**
	 * @return the shot
	 */
	public boolean isShot() {
		return shot;
	}

	/**
	 * @param shot the shot to set
	 */
	public void setShot(boolean shot) {
		this.shot = shot;
	}

	/**
	 * @return the savedInput
	 */
	public Input getSavedInput() {
		return savedInput;
	}

	/**
	 * @param savedInput the savedInput to set
	 */
	public void setSavedInput(Input savedInput) {
		this.savedInput = savedInput;
	}

	/**
	 * @return the laser
	 */
	public Laser getLaser() {
		return laser;
	}

	/**
	 * @param laser the laser to set
	 */
	public void setLaser(Laser laser) {
		this.laser = laser;
	}
	
	

}
