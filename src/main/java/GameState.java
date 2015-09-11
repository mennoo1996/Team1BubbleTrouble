import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

	
	
	private  int totaltime;
	
	private MainGame mg;
	private ArrayList<BouncingCircle> circleList;
	private ArrayList<BouncingCircle> shotList;
	private ArrayList<Powerup> droppedPowerups;
	private ArrayList<Coin> droppedCoins;
	private ArrayList<FloatingScore> floatingScoreList;
	private ArrayList<Gate> gateList;

	private int score;
	private long startTime;
	private long timeDelta;
	private long timeRemaining;
	private long prevTime;
	private boolean countIn;
	private boolean playingState;
	private boolean waitEsc;
	
	// Images
	private Image wallsImage;
	private Image health1Image;
	private Image health2Image;
	private Image health3Image;
	private Image health4Image;
	private Image health5Image;
	private Image laserImage;
	private Image shieldImage;
	private Image vineImage;
	private Image nobuttonImage;
	private Image[] ballsImages;
	private Image ceilingImage;
	private Image counterBarImage;
	private Image gateUpper;
	private Image gateLower;
	private Image coinImage;
	
	// pause game buttons
	private Button returnButton;
	private Button menuButton;
	private Button exitButton;
	
	// Countdown Bar Logic
	private static final int COUNTDOWN_BAR_PARTS = 56;
	private int fractionTimeParts;
	private boolean waitForLevelEnd = false;
	
	// level objects
	private WeaponList weaponList;
	private MyRectangle floor;
	private MyRectangle leftWall;
	private MyRectangle rightWall;
	private MyRectangle ceiling;
	private Input savedInput;

	private LevelContainer levels;
	
	// PAUSE MENU
	private static final int BUTTON_X = 150;
	private static final int RETURN_BUTTON_Y = 225;
	private static final int MENU_BUTTON_Y = 275;
	private static final int EXIT_BUTTON_Y = 325;
	private static final int TEXT_X = 164;
	private static final int TEXT_1_Y = 142;
	private static final int TEXT_2_Y = 190;
	private static final int BUTTON_WIDTH = 1000;
	private static final int BUTTON_HEIGHT = 50;
	private static final int MOUSE_OVER_RECT_X = 500;
	
	// CONSTANTS
	private static final int LEVEL_POINTS = 150;
	private static final int SECOND_TO_MS_FACTOR = 1000;
	private static final float SECOND_TO_MS_FACTOR_FLOAT = 1000f;
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
	private static final int COUNTER_BAR_X_DEVIATION = 80;
	private static final int COUNTER_BAR_Y_DEVIATION = 60;
	private static final int COUNTER_BAR_PARTS_FACTOR = 5;
	private static final int COUNTER_BAR_X_FACTOR = 10;
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
	private static final int POWERUP_CHANCE = 20;
	private static final int COIN_CHANCE = 30;
	private static final int POWERUP_IMAGE_OFFSET = 12;
	private static final int COIN_IMAGE_OFFSET = 3;
	// Level ending, empty bar
	
	private Random random;
	
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
		random = new Random();
		mg.getPlayerList().setAllPlayersShot(false);
		//mg.getPlayerList().resetPlayerLocations();
		score = 0;
		levels.initialize();
		totaltime = levels.getLevel(mg.getLevelCounter()).getTime() * SECOND_TO_MS_FACTOR;
		startTime = System.currentTimeMillis();
		timeRemaining = totaltime;
		prevTime = startTime;
		countIn = true;
		playingState = true;
		// Add player sprite and walls
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
		gateList = levels.getLevel(mg.getLevelCounter()).getGates();
		droppedPowerups = new ArrayList<>();
		droppedCoins = new ArrayList<>();
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
		loadButtons();
		setFloor(new MyRectangle(0, container.getHeight() - FLOOR_Y_DEVIATION,
				container.getWidth(), FLOOR_HEIGHT));
		setLeftWall(new MyRectangle(0, 0, LEFT_WALL_WIDTH, container.getHeight()));
		setRightWall(new MyRectangle(container.getWidth() - RIGHT_WALL_X_DEVIATION,
				0, RIGHT_WALL_WIDTH, container.getHeight()));
		setCeiling(new MyRectangle(0, 0, container.getWidth(), CEILING_HEIGHT));
		
		levels = new LevelContainer(mg);
		
		Weapon weapon1 = null;
		Weapon weapon2 = null;
		weaponList = new WeaponList(weapon1, mg, this);
		weaponList.add(weapon2);
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
			processPauseButtons(container, sbg);
		}
	}

	private void playGame(GameContainer container, StateBasedGame sbg, int delta, long curTime) {
		processTime(sbg, curTime);

		float deltaFloat = delta / SECOND_TO_MS_FACTOR_FLOAT;

		// player-thingy
		mg.getPlayerList().updatePlayers(deltaFloat);
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
		fractionTimeParts = Math.round((float) COUNTDOWN_BAR_PARTS 
				* (float) timeRemaining / (float) totaltime);

		if (waitForLevelEnd) {
			timeRemaining -= TIME_REMAINING_FACTOR * totaltime;
			if (timeRemaining < 1) {
				timeRemaining = 1;
			}
		}

		if (timeRemaining <= 0) {
            mg.getPlayerList().playerDeath(sbg);
        }
		prevTime = curTime;
	}

	private void processPauseButtons(GameContainer container, StateBasedGame sbg) {
		Input input = container.getInput();
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if (returnButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY()) 
					&& !waitEsc) {
				prevTime = System.currentTimeMillis();
				countIn = true;
				playingState = true;
			} else if (menuButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
				mg.setScore(0);
				mg.setLevelCounter(0);
				sbg.enterState(0);
			} else if (exitButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
				container.exit();
			}
		}
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
					checkBonus(circle);
                }
				// if it was part of the gate reqs, add to new gate reqs
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
		circleList.removeAll(ceilingList);
	}

	private void updateActiveCircles(GameContainer container, StateBasedGame sbg,
			float deltaFloat, ArrayList<BouncingCircle> ceilingList) {
		for (BouncingCircle circle : circleList) {
            //update circles
            circle.update(this, container, deltaFloat);

            mg.getPlayerList().intersectPlayersWithCircle(circle);
            
            weaponList.intersectWeaponsWithCircle(circle);

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
		score += ((double) timeRemaining / totaltime) * LEVEL_POINTS; // add level-ending score
		
		if (waitForLevelEnd && timeRemaining == 1) {
            
            mg.setScore(mg.getScore() + score); // update total score
            int levelCounter = mg.getLevelCounter();
			if (levelCounter < levels.size() - 1) {
                waitForLevelEnd = false;
                mg.setLevelCounter(mg.getLevelCounter() + 1);
                sbg.enterState(mg.getGameState()); // next level
            } else {
                waitForLevelEnd = false;
                sbg.enterState(mg.getGameOverState()); // game completed
            }
        }
	}

	
	private void processPause() {
		if (getSavedInput().isKeyDown(Input.KEY_ESCAPE)) {
			waitEsc = true;
			Executors.newScheduledThreadPool(1).schedule(() -> waitEsc = false,
					PAUSE_FACTOR, TimeUnit.MILLISECONDS);
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
		drawGates(container, graphics);
		weaponList.drawWeapons(graphics);
		// draw player
		mg.getPlayerList().drawPlayers(container, graphics);
		drawItems(graphics);
		// Draw walls, floor and ceiling
		graphics.drawImage(wallsImage, 0, 0);
		drawCountdownBar(container, graphics);
		// Draw level/Score data
		mg.getDosFont().drawString(container.getWidth() / 2 - LEVEL_STRING_X_DEVIATION,
				container.getHeight() - LEVEL_STRING_Y_DEVIATION, "Level: "
						+ Integer.toString(mg.getLevelCounter() + 1));
		mg.getDosFont().drawString((float) container.getWidth() / 2.0f, container.getHeight()
				- SCORE_STRING_Y_DEVIATION, "Score: " + Integer.toString(mg.getScore() + score));
		// Pause overlay and counter
		if (playingState && countIn) {
			drawCountIn(container, graphics);
		}
		drawMiscellaneous(container, graphics);
	}
	
	private void drawGates(GameContainer container, Graphics graphics) {
		// draw all active gates
		drawActiveGates(container, graphics);
	}

	private void drawMiscellaneous(GameContainer container, Graphics graphics) {
		if (!playingState) {
			drawPausedScreen(container, graphics);
		}
		// draw version number
		mg.drawWaterMark();
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

	private void drawItems(Graphics graphics) {
		drawPowerups(graphics);
		drawCoins(graphics);
	}

	private void drawPowerups(Graphics graphics) {

		for (Powerup pow : droppedPowerups) {
			if (pow.getType() == Powerup.PowerupType.SHIELD) {
				graphics.drawImage(shieldImage,
						pow.getX() - POWERUP_IMAGE_OFFSET, pow.getY() - POWERUP_IMAGE_OFFSET);
			} else if (pow.getType() == Powerup.PowerupType.SPIKY) {
				graphics.drawImage(vineImage,
						pow.getX() - POWERUP_IMAGE_OFFSET, pow.getY() - POWERUP_IMAGE_OFFSET);
			} else if (pow.getType() == Powerup.PowerupType.INSTANT) {
				graphics.drawImage(laserImage,
						pow.getX() - POWERUP_IMAGE_OFFSET, pow.getY() - POWERUP_IMAGE_OFFSET);
			}
//			graphics.fillRect(pow.getX(), pow.getY(),
//					pow.getRectangle().getWidth(), pow.getRectangle().getHeight());
		}
	}

	private void drawCoins(Graphics graphics) {
		graphics.setColor(Color.blue);
		for (Coin coin : droppedCoins) {
			graphics.drawImage(coinImage, coin.getX() 
					- COIN_IMAGE_OFFSET, coin.getY() - COIN_IMAGE_OFFSET);
		}
		graphics.setColor(Color.white);
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
					try {
						throw new SlickException("Radius was not one of the supported");
					} catch (SlickException e) {
						e.printStackTrace();
					}
			}
		}
	}

	private void drawFloatingScores() {
		for (FloatingScore score : floatingScoreList) {
			mg.getDosFont().drawString(score.getX(), score.getY(),
					score.getScore(),
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
				try {
					throw new SlickException("Life count was not in the correct range");
				} catch (SlickException e) {
					e.printStackTrace();
				}
		}
	}

	private void drawPausedScreen(GameContainer container, Graphics graphics) {
		Color overLay = new Color(0f, 0f, 0f, PAUSE_OVERLAY_COLOR_FACTOR);
		graphics.setColor(overLay);
		graphics.fillRect(0, 0, container.getWidth(), container.getHeight()
				- PAUSED_RECT_Y_DEVIATION);
		mg.getDosFont().drawString(TEXT_X, TEXT_1_Y, "# Game is paused...");
		mg.getDosFont().drawString(TEXT_X, TEXT_2_Y, "========================");
		Input input = container.getInput();
		if (returnButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
			graphics.drawImage(returnButton.getImageMouseOver(), returnButton.getX(), 
					returnButton.getY());
		} else {
			graphics.drawImage(returnButton.getImage(), 
					returnButton.getX(), returnButton.getY());
		}
		if (menuButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
			graphics.drawImage(menuButton.getImageMouseOver(), menuButton.getX(), 
					menuButton.getY());
		} else {
			graphics.drawImage(menuButton.getImage(), 
					menuButton.getX(), menuButton.getY());
		}
		if (exitButton.getRectangle().contains(MOUSE_OVER_RECT_X, input.getMouseY())) {
			graphics.drawImage(exitButton.getImageMouseOver(), exitButton.getX(), 
					exitButton.getY());
		} else {
			graphics.drawImage(exitButton.getImage(), 
					exitButton.getX(), exitButton.getY());
		}
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

	private void loadImages() throws SlickException {
		loadHealthAndBallImages();
		loadPowerupImages();
		// button image
		nobuttonImage = new Image("resources/Terminal/Terminal_No_Button.png");
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
		// countdown bar images
		counterBarImage = new Image("resources/counter_bar.png");
		coinImage = new Image("resources/coin.png");
	}
	
	private void loadButtons() throws SlickException {
		returnButton = new Button(BUTTON_X, RETURN_BUTTON_Y,
				BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/Menus/Menu_Button_Return.png"),
				new Image("resources/Menus/Menu_Button_Return2.png"));
		menuButton = new Button(BUTTON_X, MENU_BUTTON_Y,
				BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/Menus/Menu_Button_MainMenu.png"),
				new Image("resources/Menus/Menu_Button_MainMenu2.png"));
		exitButton = new Button(BUTTON_X, EXIT_BUTTON_Y,
				BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/Menus/Menu_Button_Quit.png"),
				new Image("resources/Menus/Menu_Button_Quit2.png"));
	}
	
	private void loadPowerupImages() throws SlickException {
		// load powerup images
		laserImage = new Image("resources/Powerups/Laser.png");
		shieldImage = new Image("resources/Powerups/Shield.png");
		vineImage = new Image("resources/Powerups/Vine.png");
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

	private void checkBonus(BouncingCircle circle) {
		// 5% of the time
		final int total = 100;
		int randInt = new Random().nextInt(total) + 1;
		if (randInt <= POWERUP_CHANCE) {
			dropPowerup(circle);
		}
		else if (randInt <= POWERUP_CHANCE + COIN_CHANCE) {
			dropCoin(circle);
		}
	}

	private void dropCoin(BouncingCircle circle) {
		boolean bigMoney = random.nextBoolean();
		droppedCoins.add(new Coin(circle.getCenterX(), circle.getCenterY(), bigMoney));
	}

	private void dropPowerup(BouncingCircle circle) {
		// Get a random powerup
		Powerup.PowerupType newPowerup = Powerup.PowerupType.values()[new Random()
				.nextInt(Powerup.PowerupType.values().length)];
		droppedPowerups.add(new Powerup(circle.getCenterX(), circle.getCenterY(), newPowerup));
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
	 * @return the weaponList
	 */
	public WeaponList getWeaponList() {
		return weaponList;
	}

	/**
	 * @param weaponList the weaponList to set
	 */
	public void setWeaponList(WeaponList weaponList) {
		this.weaponList = weaponList;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @param points the number to increment score
	 */
	public void addToScore(int points) {
		this.score += points;
	}

	/**
	 * @return the droppedPowerups
	 */
	public ArrayList<Powerup> getDroppedPowerups() {
		return droppedPowerups;
	}

	/**
	 * @param droppedPowerups the droppedPowerups to set
	 */
	public void setDroppedPowerups(ArrayList<Powerup> droppedPowerups) {
		this.droppedPowerups = droppedPowerups;
	}

	/**
	 * @return dropped coins
	 */
	public ArrayList<Coin> getDroppedCoins() {
		return droppedCoins;
	}

	/**
	 * @return the shotList
	 */
	public ArrayList<BouncingCircle> getShotList() {
		return shotList;
	}

	/**
	 * @param shotList the shotList to set
	 */
	public void setShotList(ArrayList<BouncingCircle> shotList) {
		this.shotList = shotList;
	}

	/**
 	* @param droppedCoins list to set
 	*/
	public void setDroppedCoins(ArrayList<Coin> droppedCoins) {
		this.droppedCoins = droppedCoins;
	}
	
	/**
	 * @return floating scores
	 */
	public ArrayList<FloatingScore> getFloatingScores() {
		return floatingScoreList;
	}
	
}
