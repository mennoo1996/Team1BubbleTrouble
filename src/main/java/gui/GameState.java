package gui;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import lan.GameStateObserver;
import lan.PlayerMovementObserver;
import logic.BouncingCircle;
import logic.Button;
import logic.Coin;
import logic.FloatingScore;
import logic.Gate;
import logic.LevelContainer;
import logic.Logger;
import logic.Logger.PriorityLevels;
import logic.MyRectangle;
import logic.Player;
import logic.Powerup;
import logic.Weapon;
import logic.WeaponList;

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
/**
 * @author Bart
 *
 */
public class GameState extends BasicGameState {
	
	private  int totaltime;
	private ArrayList<PlayerMovementObserver> playerMovementObservers 
		= new ArrayList<PlayerMovementObserver>();
	
	private MainGame mainGame;
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
	private boolean levelStarted;
	
	// Images
	private Image health0Image;
	private Image health1Image;
	private Image health2Image;
	private Image health3Image;
	private Image health4Image;
	private Image health5Image;
	private Image nobuttonImage;
	private Image wallsImageN;
	private Image wallsImageA;
	private Image laserImageN;
	private Image laserImageA;
	private Image shieldImageN;
	private Image shieldImageA;
	private Image vineImageN;
	private Image vineImageA;
	private Image[] ballsImagesN;
	private Image[] ballsImagesA;
	private Image ceilingImageN;
	private Image ceilingImageA;
	private Image counterBarImageN;
	private Image counterBarImageA;
	private Image gateUpperN;
	private Image gateUpperA;
	private Image gateLowerN;
	private Image gateLowerA;
	private Image coinImageN;
	private Image coinImageA;
	
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
	private static final int SHIELD_COUNTER_OFFSET_X = 120;
	private static final int SHIELD_COUNTER_OFFSET_Y = 90;
	private static final int SHIELD_COUNTER_OFFSET_1_Y = -6;
	private static final int SHIELD_COUNTER_OFFSET_1_X = 290;
	private static final int SHIELD_COUNTER_OFFSET_2_X = 305;
	private static final int SHIELD_COUNTER_INCREMENT_Y = 40;
	private static final float SHIELD_COUNTER_DIVIDER = 1000f;
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
	private static final int POWERUP_CHANCE = 20;
	private static final int COIN_CHANCE = 30;
	private static final int POWERUP_IMAGE_OFFSET = 12;
	private static final int COIN_IMAGE_OFFSET = 3;
	// Level ending, empty bar
	
	private Random random;
	
	/**
	 * constructor.
	 * 
	 * @param mainGame	- the maingame this state belongs to
	 */
	public GameState(MainGame mainGame) {
		this.mainGame = mainGame;
	}
	
	/**
	 * setup all variables when entering this state.
	 * @param container the Container this state is part of
	 * @param arg1 The statebasedgame this state is part of
	 * @throws SlickException sometimes.
	 */
	@Override
	public void enter(GameContainer container, StateBasedGame arg1) throws SlickException {
		mainGame.getLogger().log("Entering GameState", Logger.PriorityLevels.LOW, "States");
		RND.setOpacity(0.0f);
		mainGame.stopSwitchState();
		random = new Random();
		mainGame.getPlayerList().setAllPlayersShot(false);
		mainGame.getPlayerList().getPlayers().forEach(Player::respawn);
		score = 0;
		levels.initialize();
		totaltime = levels.getLevel(mainGame.getLevelCounter()).getTime() * SECOND_TO_MS_FACTOR;
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
		floatingScoreList = new ArrayList<FloatingScore>();
		circleList = levels.getLevel(mainGame.getLevelCounter()).getCircles();
		shotList = new ArrayList<BouncingCircle>(); // list with shot circles
		gateList = levels.getLevel(mainGame.getLevelCounter()).getGates();
		droppedPowerups = new ArrayList<>();
		droppedCoins = new ArrayList<>();
	}
	
	/**
	 * javadoc.
	 * @param observer .
	 */
	public void attach(GameStateObserver observer) {
		if (observer instanceof PlayerMovementObserver) {
			playerMovementObservers.add((PlayerMovementObserver) observer);
		}
	}
	
	/**
	 * javadoc.
	 */
	public void notifyAllPlayerMovementObservers() {
		for (PlayerMovementObserver observer : playerMovementObservers) {
			observer.update();
		}
	}
	
	/**
	 * Exit function for state. Fades out and everything.
	 * @param container the GameContainer we are running in
	 * @param sbg the gamestate cont.
	 * @param delta the deltatime in ms
	 */
	public void exit(GameContainer container, StateBasedGame sbg, int delta) {
		if (mainGame.getShouldSwitchState()) {
			if (RND.getOpacity() > 0.0f) {
				int fadeTimer = mainGame.getOpacityFadeTimer();
				if (mainGame.getSwitchState() == -1) {
					fadeTimer = 2 * 2 * 2 * fadeTimer;
				}
				RND.setOpacity(RND.getOpacity() - ((float) delta) / fadeTimer);
			} else {
				mainGame.getLogger().log("Exiting GameState", Logger.PriorityLevels.LOW, "States");
				if (mainGame.getSwitchState() == -1) {
					System.exit(0);
				} else {
					mainGame.getPlayerList().getPlayers().forEach(Player::respawn);
					mainGame.getPlayerList().setProcessCollisions(true);
					mainGame.switchColor();
					sbg.enterState(mainGame.getSwitchState());
				}
			}	
		}
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
		
		levels = new LevelContainer(mainGame);
		
		Weapon weapon1 = null;
		Weapon weapon2 = null;
		weaponList = new WeaponList(weapon1, mainGame, this, false);
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
		if (RND.getOpacity() < 1.0f && !mainGame.getShouldSwitchState()) {
			RND.setOpacity(RND.getOpacity() + ((float) delta) / mainGame.getOpacityFadeTimer());
		}
		setSavedInput(container.getInput());
		if (playingState && !mainGame.getShouldSwitchState()) {
			// Timer logic
			long curTime = System.currentTimeMillis();
			timeDelta = curTime - prevTime;
			if (countIn) {
				if (timeDelta >= COUNT_IN_TIME) {
					mainGame.getLogger().log("Starting level", 
							Logger.PriorityLevels.MEDIUM, "levels");
					countIn = false;
					if (mainGame.isHost()) {
						mainGame.getHost().updateLevelStarted();
					}
					prevTime = curTime;
				}
			} else {
				if (!mainGame.isLanMultiplayer() || mainGame.isHost() || levelStarted) {
					playGame(container, sbg, delta, curTime);
				}
			}
		} else {
			processEscape(container, sbg);
		}
		exit(container, sbg, delta);
	}
	
	/**
	 * javadoc.
	 * @param container .
	 * @param sbg .
	 */
	private void processEscape(GameContainer container, StateBasedGame sbg) {
		if (getSavedInput().isKeyDown(Input.KEY_ESCAPE) && !waitEsc) {
			// Reset time countdown
			prevTime = System.currentTimeMillis();
			countIn = true;
			playingState = true;
		}
		processPauseButtons(container, sbg);
	}

	/**
	 * Process everything in the game, for one frame.
	 * @param container the GameContainer we are playing in
	 * @param sbg the StateBasedGame that we are playing in
	 * @param delta the time since the last frame in ms
	 * @param curTime the current time
	 */
	private void playGame(GameContainer container, StateBasedGame sbg, int delta, long curTime) {
		processTime(sbg, curTime);

		float deltaFloat = delta / SECOND_TO_MS_FACTOR_FLOAT;

		// player-thingy
		mainGame.getPlayerList().updatePlayers(deltaFloat,
				container.getHeight(),
				container.getWidth());
		processPause();
		
		processCircles(container, sbg, deltaFloat);
		updateFloatingScores(deltaFloat);
		// if there are no circles required to be shot by a gate, remove said gate
		updateGateExistence(deltaFloat);
		// if there are no active circles, process to gameover screen
		processCoins(container, deltaFloat);
		if (circleList.isEmpty()) {
			endLevel(sbg);
		}
	}

	/**
	 * Process the time in the current game.
	 * @param sbg the statebasedgame we are playing in 
	 * @param curTime the current time, used to calculate the difference
	 */
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
            mainGame.getPlayerList().playerDeath(sbg);
        }
		prevTime = curTime;
	}

	/**
	 * Process the buttons if you are in the pause menu.
	 * @param container the GameContainer we are playing in
	 * @param sbg the StateBasedGame we are playing
	 */
	private void processPauseButtons(GameContainer container, StateBasedGame sbg) {
		Input input = container.getInput();
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && !mainGame.getShouldSwitchState()) {
			if (returnButton.isMouseOver(input) && !waitEsc) {
				prevTime = System.currentTimeMillis();
				countIn = true;
				playingState = true;
			} else if (menuButton.isMouseOver(input)) {
				mainGame.setScore(0);
				mainGame.setLevelCounter(0);
				mainGame.setSwitchState(mainGame.getStartState());
			} else if (exitButton.isMouseOver(input)) {
				mainGame.setSwitchState(-1);
			}
		}
	}
	
	/**
	 * Process the coins that are currently in the game.
	 * @param container the GameContainer we are playing in
	 * @param deltafloat the time in seconds since the last frame
	 */
	private void processCoins(GameContainer container, float deltafloat) {
		for (Coin coin : droppedCoins) {
			coin.update(getFloor(), deltafloat, container.getHeight());
		}
	}
	
	/**
	 * Process the circles in the game.
	 * @param container the GameContainer we are playing the game in
	 * @param sbg the StateBasedGame we are playing
	 * @param deltaFloat the time in seconds since the last frame
	 */
	private void processCircles(GameContainer container, StateBasedGame sbg, float deltaFloat) {
		ArrayList<BouncingCircle> ceilingList = new ArrayList<BouncingCircle>();
		updateActiveCircles(container, sbg, deltaFloat, ceilingList);
		removeCeilingCircles(ceilingList);
		updateShotCircles();
	}

	/**
	 * Update the circles that have been shot.
	 */
	private void updateShotCircles() {
		for (BouncingCircle circle : shotList) {
            if (!circle.isDone()) { // if the circle hasn't been handled
            	floatingScoreList.add(new FloatingScore(circle));
                if (circleList.contains(circle)) {
                    circleList.remove(circle);
                    circle.setDone(true);
                    score += circle.getScore();
                } // if the ball has a radius of 20, split it u
                ArrayList<BouncingCircle> splits = new ArrayList<BouncingCircle>();
                if (circle.getRadius() >= MINIMUM_SPLIT_RADIUS) {
                	splits = circle.getSplittedCircles(mainGame);
                    circleList.addAll(splits);
					checkItem(circle);
                } else {
                	mainGame.getLogger().log(
							"Circle with radius 10 shot, no new balls entered the game",
                			PriorityLevels.MEDIUM,
							"BouncingCircles");
                } // if it was part of the gate reqs, add to new gate reqs
				processUnlockCirclesGates(circle, splits);
				
				if (mainGame.isHost()) {
					mainGame.getHost().updateCircles(getCircleList());
				}
			}
        }
	}

	/**
	 * Process the circles in the requirements lists of the gates.
	 * @param circle the circle you are processing
	 * @param splits the list of circles that have been split
	 */
	private void processUnlockCirclesGates(BouncingCircle circle,
										   ArrayList<BouncingCircle> splits) {
		for (Gate gate : gateList) {
            if (gate.getUnlockCircles().contains(circle)) {
                gate.getUnlockCircles().remove(circle);
            }
            if (circle.getRadius() >= MINIMUM_SPLIT_RADIUS) {
                gate.addToRequirements(splits);
            }
        }
	}

	/**
	 * Remove the circles that hit the ceiling.
	 * @param ceilingList the list of circles that hit the ceiling
	 */
	private void removeCeilingCircles(ArrayList<BouncingCircle> ceilingList) {
		circleList.removeAll(ceilingList);
	}

	/**
	 * Update the circles that are still in the game, after all the other process methods.
	 * @param container the GameContainer you are playing in
	 * @param sbg the StateBasedGame you are playing
	 * @param deltaFloat the time ins seconds since the last frame
	 * @param ceilingList the circles that have hit the ceiling
	 */
	private void updateActiveCircles(GameContainer container, StateBasedGame sbg,
			float deltaFloat, ArrayList<BouncingCircle> ceilingList) {
		for (BouncingCircle circle : circleList) {
            //update circles
            circle.update(this, container.getHeight(), container.getWidth(), deltaFloat);

            mainGame.getPlayerList().intersectPlayersWithCircle(circle);
            
            weaponList.intersectWeaponsWithCircle(circle);

            if (circle.isHitCeiling()) {
            	ceilingList.add(circle);
            }

        }
	}

	/**
	 * Update the floating scores in the game.
	 * @param deltaFloat the time in seconds since the last frame
	 */
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
	
	/**
	 * Update the existence of the gates. Remove them if the required balls have disappeared.
	 * @param deltaFloat the time in seconds since the last frame.
	 */
	private void updateGateExistence(float deltaFloat) {
		ArrayList<Gate> tempGateList = new ArrayList<Gate>();
		for (Gate gate : gateList) {
			if (gate.getUnlockCircles().isEmpty()) {
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

	/**
	 * End the level if needed.
	 * @param sbg the StateBasedGame we are playing
	 */
	private void endLevel(StateBasedGame sbg) {
		if (!waitForLevelEnd) {
            waitForLevelEnd = true;
        }
		score += ((double) timeRemaining / totaltime) * LEVEL_POINTS; // add level-ending score
		
		if (waitForLevelEnd && timeRemaining == 1) {
            
            mainGame.setScore(mainGame.getScore() + score); // update total score
            int levelCounter = mainGame.getLevelCounter();
			if (levelCounter < levels.size() - 1) {
                waitForLevelEnd = false;
                mainGame.setLevelCounter(mainGame.getLevelCounter() + 1);
                //sbg.enterState(mainGame.getGameState()); // next level
                mainGame.setSwitchState(mainGame.getGameState());
            } else {
                waitForLevelEnd = false;
               // sbg.enterState(mainGame.getGameOverState()); // game completed
                mainGame.setSwitchState(mainGame.getGameOverState());
            }
        }
	}


	/**
	 * Process the pause screen.
	 */
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
		RND.draw(graphics, mainGame.getBackgroundImage(), 0, 0);
		graphics.setColor(Color.white);
		drawActiveCircles(graphics);
		drawFloatingScores(graphics);
		RND.drawColor(graphics, ceilingImageN, ceilingImageA, getLeftWall().getWidth() 
				- CEILING_DRAW_X_DEVIATION, getCeiling().getHeight() - CEILING_DRAW_Y_DEVIATION, 
				mainGame.getColor());
		drawGates(container, graphics);
		weaponList.drawWeapons(graphics);
		mainGame.getPlayerList().drawPlayers(graphics);
		drawItems(graphics);
		// Draw walls, floor and ceiling
		RND.drawColor(graphics, wallsImageN, wallsImageA, 0, 0, mainGame.getColor());
		drawCountdownBar(container, graphics);
		// Draw level/Score data
		RND.text(graphics, container.getWidth() / 2 - LEVEL_STRING_X_DEVIATION, 
				container.getHeight() - LEVEL_STRING_Y_DEVIATION, "Level: "
						+ Integer.toString(mainGame.getLevelCounter() + 1));
		drawScore(container, graphics);
		// Pause overlay and counter
		if (playingState && countIn) {
			drawCountIn(container, graphics);
		}
		drawMiscellaneous(container, graphics);
	}

	/**
	 * Draw the score.
	 * @param container the GameContainer we are playing in
	 * @param graphics the graphics object to draw things on screen
	 */
	private void drawScore(GameContainer container, Graphics graphics) {
		String renderedScore;
		if (mainGame.getShouldSwitchState()) {
			renderedScore = Integer.toString(mainGame.getScore());
		} else {
			renderedScore = Integer.toString(mainGame.getScore() + score);
		}
		RND.text(graphics, (float) container.getWidth() / 2.0f, container.getHeight()
				- SCORE_STRING_Y_DEVIATION, "Score: " + renderedScore);
	}

	/**
	 * Draw all the gates.
	 * @param container the GameContainer we are playing in
	 * @param graphics the Graphics object to draw things on screen
	 */
	private void drawGates(GameContainer container, Graphics graphics) {
		// draw all active gates
		drawActiveGates(container, graphics);
	}

	/**
	 * Draw some things like shield timer, health lights, etc.
	 * @param container the GameContainer we are playing in
	 * @param graphics the Graphics object to draw things on screen
	 */
	private void drawMiscellaneous(GameContainer container, Graphics graphics) {
		// draw shield timers
		drawShieldTimer(graphics);
		if (!playingState) {
			drawPausedScreen(container, graphics);
		}
		// draw version number
		mainGame.drawWaterMark();
		// draw foreground layer
		graphics.drawImage(mainGame.getForeGroundImage(), 0, 0);
		// draw terminal
		graphics.drawImage(mainGame.getTerminalImage(), 0, 0);
		// disable button when paused
		if (!playingState) {
			graphics.drawImage(nobuttonImage, 0, 0);
		}
		// show correct health lights
		drawHealth(graphics);
	}

	/**
	 * Draw the shield timer.
	 * @param graphics the Graphics object to draw things on screne
	 */
	private void drawShieldTimer(Graphics graphics) {
		int height = SHIELD_COUNTER_OFFSET_Y;
		if (mainGame.getPlayerList().getPlayers().get(0).hasShield()) {
			height += SHIELD_COUNTER_INCREMENT_Y;
			float rem = mainGame.getPlayerList().getPlayers().get(0).shieldTimeRemaining();
			RND.text(graphics, SHIELD_COUNTER_OFFSET_X, height, ">PL_1.Sh():");
			for (int x = 0; x < Math.round(rem / SHIELD_COUNTER_DIVIDER); x++) {
				RND.drawColor(graphics, counterBarImageN, counterBarImageA,
						SHIELD_COUNTER_OFFSET_1_X + x * COUNTER_BAR_X_FACTOR, 
						height + SHIELD_COUNTER_OFFSET_1_Y, mainGame.getColor());
			}
			RND.text(graphics, SHIELD_COUNTER_OFFSET_2_X
					+ Math.round(rem / SHIELD_COUNTER_DIVIDER)
					* COUNTER_BAR_X_FACTOR, height, "#" + rem / SHIELD_COUNTER_DIVIDER + "s");
		}
		if (mainGame.isMultiplayer() && mainGame.getPlayerList().getPlayers().get(1).hasShield()) {
			height += SHIELD_COUNTER_INCREMENT_Y;
			float rem = mainGame.getPlayerList().getPlayers().get(1).shieldTimeRemaining();
			RND.text(graphics, SHIELD_COUNTER_OFFSET_X, height, ">PL_2.Sh():");
			for (int x = 0; x < Math.round(rem / SHIELD_COUNTER_DIVIDER); x++) {
				RND.drawColor(graphics, counterBarImageN, counterBarImageA,
						SHIELD_COUNTER_OFFSET_1_X + x * COUNTER_BAR_X_FACTOR, 
						height + SHIELD_COUNTER_OFFSET_1_Y, mainGame.getColor());
			}
			RND.text(SHIELD_COUNTER_OFFSET_2_X 
					+ Math.round(rem / SHIELD_COUNTER_DIVIDER) 
					* COUNTER_BAR_X_FACTOR, height, 
					"#" + rem / SHIELD_COUNTER_DIVIDER + "s");
		}
	}
	
	/**
	 * Draw the items in the game.
	 * @param graphics the Graphics object to draw things on screen.
	 */
	private void drawItems(Graphics graphics) {
		drawPowerups(graphics);
		drawCoins(graphics);
	}

	/**
	 * Draw the powerups.
	 * @param graphics the Graphics object to draw things on screen
	 */
	private void drawPowerups(Graphics graphics) {

		for (Powerup pow : droppedPowerups) {
			if (pow.getType() == Powerup.PowerupType.SHIELD) {
				RND.drawColor(graphics, shieldImageN, shieldImageA, 
						pow.getX() - POWERUP_IMAGE_OFFSET, pow.getY() - POWERUP_IMAGE_OFFSET, 
						mainGame.getColor());
			} else if (pow.getType() == Powerup.PowerupType.SPIKY) {
				RND.drawColor(graphics, vineImageN, vineImageA, 
						pow.getX() - POWERUP_IMAGE_OFFSET, pow.getY() - POWERUP_IMAGE_OFFSET, 
						mainGame.getColor());
			} else if (pow.getType() == Powerup.PowerupType.INSTANT) {
				RND.drawColor(graphics, laserImageN, laserImageA, 
						pow.getX() - POWERUP_IMAGE_OFFSET, pow.getY() - POWERUP_IMAGE_OFFSET, 
						mainGame.getColor());
			}
//			graphics.fillRect(pow.getX(), pow.getY(),
//					pow.getRectangle().getWidth(), pow.getRectangle().getHeight());
		}
	}

	/**
	 * Draw the coins.
	 * @param graphics the Graphics object to draw things on screen
	 */
	private void drawCoins(Graphics graphics) {
		graphics.setColor(Color.blue);
		for (Coin coin : droppedCoins) {
			RND.drawColor(graphics, coinImageN, coinImageA, 
					coin.getX() - COIN_IMAGE_OFFSET, coin.getY() - COIN_IMAGE_OFFSET,
					mainGame.getColor());
		}
		graphics.setColor(Color.white);
	}

	/**
	 * Draw the countdown bar.
	 * @param container the GameContainer we are playing in
	 * @param graphics the Graphics object to draw things on screen
	 */
	private void drawCountdownBar(GameContainer container, Graphics graphics) {
		for (int x = 0; x < fractionTimeParts; x++) {
			RND.drawColor(graphics, counterBarImageN, counterBarImageA,
					container.getWidth() / 2 - COUNTER_BAR_X_DEVIATION - COUNTER_BAR_PARTS_FACTOR
					* (COUNTDOWN_BAR_PARTS) + x * COUNTER_BAR_X_FACTOR,
					container.getHeight() - COUNTER_BAR_Y_DEVIATION, mainGame.getColor());
		}
	}

	/**
	 * Draw the active gates.
	 * @param container the GameContainer we are playing in
	 * @param graphics the Graphics object to draw things on screen
	 */
	private void drawActiveGates(GameContainer container, Graphics graphics) {
		for (Gate gate : gateList) {
			//upper
			int left = GATE_LEFT, down = GATE_DOWN;
			float x = gate.getMinX() - left, y = getCeiling().getHeight() - GATE_Y_DEVIATION;
			float x2 = x + gateUpperN.getWidth();
			float y2 = getCeiling().getHeight() + GATE_Y_FACTOR * gate.getHeightPercentage() 
				+ down - GATE_Y_DEVIATION;
			float srcx = 0;
			float srcy = gateUpperN.getHeight() - GATE_Y_FACTOR * gate.getHeightPercentage();
			float srcx2 = gateUpperN.getWidth();
			float srcy2 = gateUpperN.getHeight();
			RND.drawColor(graphics, gateUpperN, gateUpperA, x, y, x2, y2, 
					srcx, srcy, srcx2, srcy2, mainGame.getColor());
			//lower
			left = GATE_LEFT_LOWER;
			float up = GATE_UP;
			x = gate.getMinX() - left - 1;
			y = container.getHeight() - getFloor().getHeight()
					- GATE_Y_FACTOR_LOWER * gate.getHeightPercentage() - up;
			x2 = x + gateLowerN.getWidth() - 1;
			y2 = container.getHeight() - getFloor().getHeight();
			srcx = 0;
			srcy = 0;
			srcx2 = gateLowerN.getWidth();
			srcy2 = GATE_Y_FACTOR_LOWER * gate.getHeightPercentage();
			RND.drawColor(graphics, gateLowerN, gateLowerA, x, y, x2, y2, 
					srcx, srcy, srcx2, srcy2, mainGame.getColor());
		}
	}

	/**
	 * Draw the active circles.
	 * @param graphics the Graphics object to draw things on screen.
	 */
	private void drawActiveCircles(Graphics graphics) {
		for (BouncingCircle circle : circleList) {
			int r = (int) circle.getRadius(), offset = CIRCLE_DRAW_OFFSET;
			final float xPosition = circle.getMinX() - offset;
			final float yPosition = circle.getMinY() - offset;
			switch (r) {
				case(RADIUS_6) : RND.drawColor(graphics, ballsImagesN[0], ballsImagesA[0],
						xPosition, yPosition, mainGame.getColor()); break;
				case(RADIUS_5) : RND.drawColor(graphics, ballsImagesN[1], ballsImagesA[1],
						xPosition, yPosition, mainGame.getColor()); break;
				case(RADIUS_4) : RND.drawColor(graphics, ballsImagesN[2], ballsImagesA[2],
						xPosition, yPosition, mainGame.getColor()); break;
				case(RADIUS_3) : RND.drawColor(graphics, 
						ballsImagesN[BALL_IMAGE_THREE], ballsImagesA[BALL_IMAGE_THREE],
						xPosition, yPosition, mainGame.getColor()); break;
				case(RADIUS_2) : RND.drawColor(graphics, 
						ballsImagesN[BALL_IMAGE_FOUR], ballsImagesA[BALL_IMAGE_FOUR],
						xPosition, yPosition, mainGame.getColor()); break;
				case(MINIMUM_RADIUS) : RND.drawColor(graphics, 
						ballsImagesN[BALL_IMAGE_FIVE], ballsImagesA[BALL_IMAGE_FIVE],
						xPosition, yPosition, mainGame.getColor()); break;
				default:
					try {
						throw new SlickException("Radius was not one of the supported");
					} catch (SlickException e) {
						e.printStackTrace();
					}
			}
		}
	}

	/**
	 * Draw the floating scores.
	 * @param graphics the Graphics object to draw things on screen
	 */
	private void drawFloatingScores(Graphics graphics) {
		for (FloatingScore score : floatingScoreList) {
			RND.text(graphics, score.getX(), score.getY(), score.getScore(),
					new Color(mainGame.getColor().r, mainGame.getColor().g,
							mainGame.getColor().b, score.getOpacity()));
		}
	}
	
	/**
	 * Draw the health lights.
	 * @param graphics the Graphics object to draw things on screen
	 */
	private void drawHealth(Graphics graphics) {
		switch (mainGame.getLifeCount()) {
			case(0) :
				graphics.drawImage(health0Image, 0, 0);
			break;
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

	/**
	 * Draw the paused screen if needed.
	 * @param container the GameContainer we are playing in
	 * @param graphics the Graphics object to draw things on screen
	 */
	private void drawPausedScreen(GameContainer container, Graphics graphics) {
		Color overLay = new Color(0f, 0f, 0f, PAUSE_OVERLAY_COLOR_FACTOR);
		graphics.setColor(overLay);
		graphics.fillRect(0, 0, container.getWidth(), container.getHeight()
				- PAUSED_RECT_Y_DEVIATION);
		RND.text(graphics, TEXT_X, TEXT_1_Y, "# Game is paused...");
		RND.text(graphics, TEXT_X, TEXT_2_Y, "========================");
		Input input = container.getInput();
		drawMouseOvers(input, graphics);
	}
	
	/**
	 * Draw the buttons with mouse over skin if mouse is near that button.
	 * @param input the Input of the user
	 * @param graphics the Graphics object in
	 */
	private void drawMouseOvers(Input input, Graphics graphics) {
		returnButton.drawColor(graphics, input, mainGame.getColor());
		menuButton.drawColor(graphics, input, mainGame.getColor());
		exitButton.drawColor(graphics, input, mainGame.getColor());
	}

	/**
	 * Draw the count in.
	 * @param container the GameContainer we are playing in
	 * @param graphics the Graphics object to draw things on screen
	 */
	private void drawCountIn(GameContainer container, Graphics graphics) {
		int count = (int) Math.ceil((COUNT_IN_TIME - timeDelta) / SECOND_TO_MS_FACTOR),
				amount = Math.round((COUNT_IN_TIME - timeDelta) / COUNT_IN_TIME * COUNT_FACTOR);

		graphics.setColor(new Color(0f, 0f, 0f, PAUSE_OVERLAY_COLOR_FACTOR));
		graphics.fillRect(0, 0, container.getWidth(), container.getHeight()
				- PAUSED_RECT_Y_DEVIATION);

		RND.text(graphics, container.getWidth() / 2 - STARTING_STRING_X_DEVIATION,
				container.getHeight() / 2 - PAUSED_STRING_X_DEVIATION, "Starting in");
		RND.text(graphics, container.getWidth() / 2 - STARTING_COUNT_X_DEVIATION,
				container.getHeight() / 2 - PAUSED_STRING_Y_DEVIATION, Integer.toString(count));
		
		for (int i = 0; i < amount; i++) {
            float degree = i * (WHOLE_CIRCLE_DEGREES / COUNT_IN_DEGREES);
            counterBarImageN.setCenterOfRotation(COUNTER_BAR_ROTATION_X, COUNTER_BAR_ROTATION_Y);
            counterBarImageA.setCenterOfRotation(COUNTER_BAR_ROTATION_X, COUNTER_BAR_ROTATION_Y);
            counterBarImageN.rotate(degree);
            counterBarImageA.rotate(degree);
            RND.drawColor(graphics, counterBarImageN, counterBarImageA,
            		container.getWidth() / 2 - COUNTER_BAR_DRAW_X_DEVIATION, 
            		container.getHeight() / 2 - COUNTER_BAR_DRAW_Y_DEVIATION, 
            		mainGame.getColor());
            counterBarImageN.rotate(-degree);
            counterBarImageA.rotate(-degree);
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
	 * Load all the images.
	 * @throws SlickException if something goes wrong / file not found
	 */
	private void loadImages() throws SlickException {
		loadHealthAndBallImages();
		loadPowerupImages();
		// button image
		nobuttonImage = new Image("resources/Terminal/Terminal_No_Button.png");
		// countdown bar images
		counterBarImageN = new Image("resources/images_UI/counter_Norm.png");
		counterBarImageA = new Image("resources/images_UI/counter_Add.png");
		// gate images
		gateUpperN = new Image("resources//images_Level/gate_upper_Norm.png");
		gateUpperA = new Image("resources/images_Level/gate_upper_Add.png");
		gateLowerN = new Image("resources/images_Level/gate_lower_Norm.png");
		gateLowerA = new Image("resources/images_Level/gate_lower_Add.png");
		// walls image
		wallsImageN = new Image("resources/images_Level/walls_Norm.png");
		wallsImageA = new Image("resources/images_Level/walls_Add.png");
		// ceiling image
		ceilingImageN = new Image("resources/images_Level/ceiling_Norm.png");
		ceilingImageA = new Image("resources/images_Level/ceiling_Add.png");
		// balls images
		
		// button image
		nobuttonImage = new Image("resources/Terminal/Terminal_No_Button.png");
		coinImageN = new Image("resources/images_Gameplay/coin_Norm.png");
		coinImageA = new Image("resources/images_Gameplay/coin_Add.png");
	}
	
	/**
	 * Load all the buttons.
	 * @throws SlickException if something goes wrong / file not found
	 */
	private void loadButtons() throws SlickException {
		returnButton = new Button(BUTTON_X, RETURN_BUTTON_Y,
				BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/images_UI/Menu_Button_Return_Norm.png"),
				new Image("resources/images_UI/Menu_Button_Return_Add.png"),
				new Image("resources/images_UI/Menu_Button_Return2_Norm.png"),
				new Image("resources/images_UI/Menu_Button_Return2_Add.png"));
		menuButton = new Button(BUTTON_X, MENU_BUTTON_Y,
				BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/images_UI/Menu_Button_MainMenu_Norm.png"),
				new Image("resources/images_UI/Menu_Button_MainMenu_Add.png"),
				new Image("resources/images_UI/Menu_Button_MainMenu2_Norm.png"),
				new Image("resources/images_UI/Menu_Button_MainMenu2_Add.png"));
		exitButton = new Button(BUTTON_X, EXIT_BUTTON_Y,
				BUTTON_WIDTH, BUTTON_HEIGHT,
				new Image("resources/images_UI/Menu_Button_Quit_Norm.png"),
				new Image("resources/images_UI/Menu_Button_Quit_Add.png"),
				new Image("resources/images_UI/Menu_Button_Quit2_Norm.png"),
				new Image("resources/images_UI/Menu_Button_Quit2_Add.png"));
	}
	
	/**
	 * Load the powerup images.
	 * @throws SlickException if something goes wrong / file not found
	 */
	private void loadPowerupImages() throws SlickException {
		// load powerup images
		laserImageN = new Image("resources/images_Gameplay/laserPowerup_Norm.png");
		laserImageA = new Image("resources/images_Gameplay/laserPowerup_Add.png");
		shieldImageN = new Image("resources/images_Gameplay/shieldPowerup_Norm.png");
		shieldImageA = new Image("resources/images_Gameplay/shieldPowerup_Add.png");
		vineImageN = new Image("resources/images_Gameplay/vinePowerup_Norm.png");
		vineImageA = new Image("resources/images_Gameplay/vinePowerup_Add.png");
	}
	
	/**
	 * Load the images for health and balls.
	 * @throws SlickException if something goes wrong / file not found
	 */
	private void loadHealthAndBallImages() throws SlickException {
		// load health images
		health0Image = new Image("resources/Terminal/Terminal_Lights_0.png");
		health1Image = new Image("resources/Terminal/Terminal_Lights_1.png");
		health2Image = new Image("resources/Terminal/Terminal_Lights_2.png");
		health3Image = new Image("resources/Terminal/Terminal_Lights_3.png");
		health4Image = new Image("resources/Terminal/Terminal_Lights_4.png");
		health5Image = new Image("resources/Terminal/Terminal_Lights_5.png");
		
		ballsImagesN = new Image[AMOUNT_OF_BALLS];
		ballsImagesN[0] = new Image("resources/images_Balls/Ball_90_Norm.png");
		ballsImagesN[1] = new Image("resources/images_Balls/Ball_65_Norm.png");
		ballsImagesN[2] = new Image("resources/images_Balls/Ball_45_Norm.png");
		ballsImagesN[BALL_IMAGE_THREE] = new Image("resources/images_Balls/Ball_30_Norm.png");
		ballsImagesN[BALL_IMAGE_FOUR] = new Image("resources/images_Balls/Ball_20_Norm.png");
		ballsImagesN[BALL_IMAGE_FIVE] = new Image("resources/images_Balls/Ball_10_Norm.png");
		ballsImagesA = new Image[AMOUNT_OF_BALLS];
		ballsImagesA[0] = new Image("resources/images_Balls/Ball_90_Add.png");
		ballsImagesA[1] = new Image("resources/images_Balls/Ball_65_Add.png");
		ballsImagesA[2] = new Image("resources/images_Balls/Ball_45_Add.png");
		ballsImagesA[BALL_IMAGE_THREE] = new Image("resources/images_Balls/Ball_30_Add.png");
		ballsImagesA[BALL_IMAGE_FOUR] = new Image("resources/images_Balls/Ball_20_Add.png");
		ballsImagesA[BALL_IMAGE_FIVE] = new Image("resources/images_Balls/Ball_10_Add.png");
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
	 * Check if an item should be dropped.
	 * @param circle the circle that could drop this item
	 */
	private void checkItem(BouncingCircle circle) {
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

	/**
	 * Drop a coin.
	 * @param circle the circle that should drop the coin.
	 */
	private void dropCoin(BouncingCircle circle) {
		boolean bigMoney = random.nextBoolean();
		droppedCoins.add(new Coin(circle.getCenterX(), circle.getCenterY(), bigMoney));
	}

	/**
	 * Drop a powerup.
	 * @param circle the circle that should drop the powerup
	 */
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
	public MainGame getmainGame() {
		return mainGame;
	}

	/**
	 * Set the maingame.
	 * @param mainGame the maingame to set.
	 */
	public void setmainGame(MainGame mainGame) {
		this.mainGame = mainGame;
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

	/**
	 * @return whether or not the game is paused
	 */
	public boolean isPaused() {
		return !playingState;
	}

	/**
	 * @return the levelStarted
	 */
	public boolean isLevelStarted() {
		return levelStarted;
	}

	/**
	 * @param levelStarted the levelStarted to set
	 */
	public void setLevelStarted(boolean levelStarted) {
		this.levelStarted = levelStarted;
	}

	/**
	 * @return the circleList
	 */
	public ArrayList<BouncingCircle> getCircleList() {
		return circleList;
	}

	/**
	 * @param circleList the circleList to set
	 */
	public void setCircleList(ArrayList<BouncingCircle> circleList) {
		this.circleList = circleList;
	}
	
}
