import java.util.ArrayList;
import java.util.LinkedList;
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


public class GameState extends BasicGameState {

	// CONSTANTS
	private static int TOTAL_TIME;
	private static final int LEVEL_POINTS = 1500;
	
	private MainGame mg;
	private ArrayList<BouncingCircle> circleList;
	private ArrayList<BouncingCircle> shotList;
	protected ArrayList<Gate> gateList;
	private Player player;
	protected Input input;
	protected boolean shot;
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
	private Image health_1_Image;
	private Image health_2_Image;
	private Image health_3_Image;
	private Image health_4_Image;
	private Image health_5_Image;
	private Image nobutton_Image;
	private Image[] balls_Images;
	private Image ceiling_Image;
	private Image laser_beam_image;
	private Image laser_tip_image;
	private Image counterBarImage;
	private Image scoretextImage;
	private Image leveltextImage;
	private Image pausedtextImage;
	private Image gateUpper;
	private Image gateLower;
	
	// Countdown Bar Logic
	private static int COUNTDOWN_BAR_PARTS = 56;
	private int fractionTimeParts;
	private boolean waitForLevelEnd = false;
	
	// level objects
	protected Weapon weapon;
	protected MyRectangle floor;
	protected MyRectangle leftWall;
	protected MyRectangle rightWall;
	protected MyRectangle ceiling;
	
	private LevelContainer levels;
	
	// Level ending, empty bar
	
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
		score = 0;

		levels.initialize();
		
		
		TOTAL_TIME = levels.getLevel(mg.levelCounter).getTime()*1000;
		startTime = System.currentTimeMillis();
		timeRemaining = TOTAL_TIME;
		prevTime = startTime;
		countIn = true;
		playingState = true;
		
		// Add player sprite and walls
		playerImage = new Image("resources/" + mg.playerImage);
		player = new Player(container.getWidth()/2 -22.5f,container.getHeight()-285,45,75, playerImage, mg);

		floor = new MyRectangle(0,container.getHeight()-210,container.getWidth(),210);
		leftWall = new MyRectangle(0,0,105,container.getHeight());
		rightWall = new MyRectangle(container.getWidth()-130,0,130,container.getHeight());
		ceiling = new MyRectangle(0,0,container.getWidth(),110);
		
		// Add arraylists of circles
		//circleList = new ArrayList<BouncingCircle>(); // active list

		circleList = levels.getLevel(mg.levelCounter).getCircles();
		
		shotList = new ArrayList<BouncingCircle>(); // list with shot circles

		// Add gates
		gateList = levels.getLevel(mg.levelCounter).getGates();

	}
	
	
	/**
	 * load resources when state is initialised
	 */
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		loadImages();
		floor = new MyRectangle(0,container.getHeight()-210,container.getWidth(),210);
		leftWall = new MyRectangle(0,0,105,container.getHeight());
		rightWall = new MyRectangle(container.getWidth()-130,0,130,container.getHeight());
		ceiling = new MyRectangle(0,0,container.getWidth(),110);
		
		levels = new LevelContainer(mg);
	}

	

	/**
	 * update method, called on each frame refresh
	 */
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {

		input = container.getInput();
		if (playingState) {
			// Timer logic
			long curTime = System.currentTimeMillis();
			timeDelta = curTime - prevTime;

			if (countIn) {
				if (timeDelta >= 3000) {
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
		processTime(sbg, curTime);

		float deltaFloat = delta / 1000f;

		player.update(deltaFloat);
		processPause();
		processCircles(container, sbg, deltaFloat);
		
		// if there are no circles required to be shot by a gate, remove said gate
		updateGateExistence(deltaFloat);
		// if there are no active circles, process to gameover screen

		if(circleList.isEmpty()) {
			endLevel(sbg);
		}

	}

	private void processTime(StateBasedGame sbg, long curTime) {
		timeRemaining -= timeDelta;
		fractionTimeParts = Math.round(COUNTDOWN_BAR_PARTS * (timeRemaining) / TOTAL_TIME);

		if(waitForLevelEnd) {
			timeRemaining -= 0.01f*TOTAL_TIME;
			if(timeRemaining < 1)
				timeRemaining = 1;
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
	}

	private void removeCeilingCircles(ArrayList<BouncingCircle> ceilingList) {
		for(BouncingCircle circle : ceilingList) {
			if(circleList.contains(circle)) {
				circleList.remove(circle);
			}
		}
	}

	private void updateActiveCircles(GameContainer container, StateBasedGame sbg, float deltaFloat, ArrayList<BouncingCircle> ceilingList) {
		for (BouncingCircle circle : circleList) {
            //update circles
            circle.update(this, container, deltaFloat);

            // if player touches circle (for the first frame)
            if (player.getRectangle().intersects(circle) && !player.hasShield()) {

                //LIVES FUNCTIONALITY
                playerDeath(sbg);
            }

            // if laser intersects circle
            if (shot && weapon.getRectangle().intersects(circle)) {
                // it has been shot and disabled
                shotList.add(circle);
                weapon.setVisible(false);
            }

            if (circle.isHitCeiling()) {
            	ceilingList.add(circle);
            }

        }
	}

	private void updateGateExistence(float deltaFloat) {
		ArrayList<Gate> tempGateList = new ArrayList<Gate>();
		for(Gate gate : gateList) {
			if(gate.getRequired().isEmpty()) {
				tempGateList.add(gate);
				gate.setFading(true);
			}
		}
		for(Gate gate : tempGateList) {
			if(gateList.contains(gate) && gate.isDone()) {
				gateList.remove(gate);
			} else if(gateList.contains(gate) && gate.isFading()) {
				gate.update(deltaFloat);
				}
		}
	}

	private void endLevel(StateBasedGame sbg) {
		if(!waitForLevelEnd) {
            waitForLevelEnd = true;
        }
		if(waitForLevelEnd && timeRemaining == 1) {
            score += ((double)timeRemaining / TOTAL_TIME) * LEVEL_POINTS; // add level-ending score
            mg.score += score; // update total score
            if (mg.levelCounter<levels.size()-1) {
                waitForLevelEnd = false;
                mg.levelCounter++;
                sbg.enterState(1); // next level
            } else {
                waitForLevelEnd = false;
                sbg.enterState(3); // game completed
            }
        }
	}

	

	private void processPause() {
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			waitEsc = true;
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					waitEsc = false;
				}
			}, 300);
			playingState = false;
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
		drawActiveCircles(graphics);

		graphics.drawImage(ceiling_Image, leftWall.getWidth() - 10, ceiling.getHeight() - 25);
		
		// draw all active gates
		drawActiveGates(container, graphics);

		// if shot, draw laser
		if(shot) {
			//graphics.fill(laser.getRectangle());
			drawWeapon(graphics);
		}
		
		// draw player
		graphics.drawImage(player.getImage(), player.getX(), player.getY());

		// Draw walls, floor and ceiling
		graphics.drawImage(wallsImage, 0, 0);
		
		// Draw timer countdown bar
		drawCountdownBar(container, graphics);


		// Draw level/Score data
		LinkedList<Integer> numberStack = new LinkedList<Integer>();
		int levelInt = (mg.levelCounter+1), scoreInt = (mg.score + score), stackCount = 0;

		graphics.drawImage(leveltextImage, container.getWidth() / 2, container.getHeight() - 90);
		while(levelInt > 0) {
			numberStack.push(levelInt % 10);
			levelInt /= 10;
		}
		while(!numberStack.isEmpty()) {
			graphics.drawImage(mg.numberImages[numberStack.pop()], container.getWidth() / 2 + 116 + 20*stackCount, container.getHeight() - 89);
			stackCount++;
		}

		graphics.drawImage(scoretextImage, container.getWidth() / 2 - 300, container.getHeight() - 90);
		stackCount = 0;
		if(scoreInt == 0) {
			numberStack.push(scoreInt);
		}
		while(scoreInt > 0) {
			numberStack.push(scoreInt % 10);
			scoreInt /= 10;
		}
		
		while(!numberStack.isEmpty()) {
			graphics.drawImage(mg.numberImages[numberStack.pop()], container.getWidth() / 2 - 184 + 20*stackCount, container.getHeight() - 90);
			stackCount++;
		}

		
		// Pause overlay and counter
		if (playingState && countIn) {
			drawCountIn(container, graphics);
		}

		if (!playingState) {
			drawPausedScreen(container, graphics);
		}
		
		// draw version number (BECAUZ ITZ COOL)
		graphics.drawImage(mg.versiontextImage, 72, container.getHeight() - 195);
		graphics.drawImage(mg.numberImages[1], 72 + 150, container.getHeight() - 196);
		int versionnumber = 105;
		stackCount = 0;
		while(versionnumber > 0) {
			numberStack.push(versionnumber % 10);
			versionnumber /= 10;
		}
		while(!numberStack.isEmpty()) {
			graphics.drawImage(mg.numberImages[numberStack.pop()], 72 + 175 + 20*stackCount, container.getHeight() - 196);
			stackCount++;
		}
		
		// draw foreground layer
		graphics.drawImage(mg.foreGroundImage, 0, 0);
		
		// draw terminal
		graphics.drawImage(mg.terminalImage, 0, 0);
		
		// disable button when paused
		if(!playingState) {
			graphics.drawImage(nobutton_Image, 0, 0);
		}
		
		// show correct health lights
		drawHealth(graphics);

		// experimenting with stretched laser textures -> MARK HERE: DONT DELETE THIS COMMENTED CODE, ITS A PAIN TO SET UP AGAIN
		//graphics.drawImage(mg.laserHorizontalImage, 100, 70, 1495, 105, 0, 0, 128, 35);
		//graphics.drawImage(mg.laserHorizontalImage, 100, 800, 1495, 835, 0, 0, 128, 35);
		//graphics.drawImage(mg.laserVerticalImage, 100, 100, 135, 1400, 0, 0, 35, 128);
		
//		graphics.setColor(Color.green);
//		graphics.drawString("Debug values ", 20, container.getHeight()-90);
//		graphics.drawString("Lives: " + mg.getLifeCount(), 20, container.getHeight()-70);
//		graphics.drawString("Score = " + (mg.score + score), 20, container.getHeight()-50);
//		graphics.drawString("Level: " + (mg.levelCounter+1), 20, container.getHeight() -30);
		
	}

	private void drawCountdownBar(GameContainer container, Graphics graphics) {
		for(int x = 0; x < fractionTimeParts; x++) {
			//counterBarImage.rotate(0.5f*x); // EPIC
			graphics.drawImage(counterBarImage, container.getWidth()/2 - 80 - 5*(COUNTDOWN_BAR_PARTS) + x*10, container.getHeight() - 60);//
			//counterBarImage.rotate(-10*x); // EPIC
		}
	}

	private void drawWeapon(Graphics graphics) {
		graphics.drawImage(laser_tip_image, weapon.getX() - 18, weapon.getY() - 14);
		graphics.drawImage(laser_beam_image, weapon.getX() - 18, weapon.getRectangle().getMinY() + 13, weapon.getX() + 17, weapon.getRectangle().getMaxY(), 0, 0, 35, 300);
	}

	private void drawActiveGates(GameContainer container, Graphics graphics) {
		for(Gate gate : gateList) {

			//upper
			int left = 11;
			int down = 9;
			float x = gate.getMinX() - left;
			float y = ceiling.getHeight() - 15;
			float x2 = x + gateUpper.getWidth();
			float y2 = ceiling.getHeight() + 348*gate.getHeightPercentage() + down - 15;
			float srcx = 0;
			float srcy = gateUpper.getHeight() - 348*gate.getHeightPercentage();
			float srcx2 = gateUpper.getWidth();
			float srcy2 = gateUpper.getHeight();
			graphics.drawImage(gateUpper, x, y, x2, y2, srcx, srcy, srcx2, srcy2);

			//lower
			left = 9;
			float up = 9;
			x = gate.getMinX() - left -1;
			y = container.getHeight() - floor.getHeight() - 347*gate.getHeightPercentage() - up;
			x2 = x + gateLower.getWidth() - 1;
			y2 = container.getHeight() - floor.getHeight();
			srcx = 0;
			srcy = 0;
			srcx2 = gateLower.getWidth();
			srcy2 = 347*gate.getHeightPercentage();
			graphics.drawImage(gateLower, x, y, x2, y2, srcx, srcy, srcx2, srcy2);
		}
	}

	private void drawActiveCircles(Graphics graphics) {
		for(BouncingCircle circle : circleList) {
			//graphics.fill(circle.getCircle(), shapeFill);
			int r = (int)circle.getRadius();
			int offset = 13;
			switch(r) {
				case(90) : graphics.drawImage(balls_Images[0], circle.getMinX()-offset, circle.getMinY()-offset); break;
				case(65) : graphics.drawImage(balls_Images[1], circle.getMinX()-offset, circle.getMinY()-offset); break;
				case(45) : graphics.drawImage(balls_Images[2], circle.getMinX()-offset, circle.getMinY()-offset); break;
				case(30) : graphics.drawImage(balls_Images[3], circle.getMinX()-offset, circle.getMinY()-offset); break;
				case(20) : graphics.drawImage(balls_Images[4], circle.getMinX()-offset, circle.getMinY()-offset); break;
				case(10) : graphics.drawImage(balls_Images[5], circle.getMinX()-offset, circle.getMinY()-offset); break;
			}
		}
	}

	private void drawHealth(Graphics graphics) {
		switch(mg.getLifeCount()) {
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
	}

	private void drawPausedScreen(GameContainer container, Graphics graphics) {
		Color overLay = new Color(0f, 0f, 0f, 0.5f);
		graphics.setColor(overLay);
		graphics.fillRect(0, 0, container.getWidth(), container.getHeight() - 150);
		graphics.drawImage(pausedtextImage, container.getWidth() / 2 - 130, container.getHeight() / 2 - 60);
	}

	private void drawCountIn(GameContainer container, Graphics graphics) {
		int count = (int)Math.ceil((3000.0-timeDelta)/1000.0), amount = Math.round((3000f-timeDelta)/3000f*15f);

		graphics.setColor(new Color(0f, 0f, 0f, 0.5f));
		graphics.fillRect(0, 0, container.getWidth(), container.getHeight() - 150);
		graphics.drawImage(mg.numberImages[count], container.getWidth() / 2 - 18, container.getHeight() / 2 - 60);

		for(int i = 0; i < amount; i++) {
            float degree = i*(360/15);
            counterBarImage.setCenterOfRotation(12, 50);
            counterBarImage.rotate(degree);
            graphics.drawImage(counterBarImage, container.getWidth() / 2 - 10, container.getHeight() / 2 - 91);
            counterBarImage.rotate(-degree);
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
			sbg.enterState(mg.GAMOVER_STATE);
		} else {
			sbg.enterState(mg.GAME_STATE);
		}
	}
	
	private void loadImages() throws SlickException {
		// load health images
		health_1_Image = new Image("resources/Terminal/Terminal_Lights_1.png");
		health_2_Image = new Image("resources/Terminal/Terminal_Lights_2.png");
		health_3_Image = new Image("resources/Terminal/Terminal_Lights_3.png");
		health_4_Image = new Image("resources/Terminal/Terminal_Lights_4.png");
		health_5_Image = new Image("resources/Terminal/Terminal_Lights_5.png");
		// button image
		nobutton_Image = new Image("resources/Terminal/Terminal_No_Button.png");
		// laser images
		laser_beam_image = new Image("resources/laser/laser_beam_blue.png");
		laser_tip_image = new Image("resources/laser/laser_tip_blue.png");
		// countdown bar images
		counterBarImage = new Image("resources/counter_bar.png");
		// text images
		scoretextImage = new Image("resources/text/text_score.png");
		leveltextImage = new Image("resources/text/text_level.png");
		pausedtextImage = new Image("resources/text/text_paused.png");

		// gate images
		gateUpper = new Image("resources/gate_upper.png");
		gateLower = new Image("resources/gate_lower.png");
		// walls image
		wallsImage = new Image("resources/walls_blue.png");

		// load health images
		health_1_Image = new Image("resources/Terminal/Terminal_Lights_1.png");
		health_2_Image = new Image("resources/Terminal/Terminal_Lights_2.png");
		health_3_Image = new Image("resources/Terminal/Terminal_Lights_3.png");
		health_4_Image = new Image("resources/Terminal/Terminal_Lights_4.png");
		health_5_Image = new Image("resources/Terminal/Terminal_Lights_5.png");

		// balls images
		balls_Images = new Image[6];
		balls_Images[0] = new Image("resources/Balls/Ball_90.png");
		balls_Images[1] = new Image("resources/Balls/Ball_65.png");
		balls_Images[2] = new Image("resources/Balls/Ball_45.png");
		balls_Images[3] = new Image("resources/Balls/Ball_30.png");
		balls_Images[4] = new Image("resources/Balls/Ball_20.png");
		balls_Images[5] = new Image("resources/Balls/Ball_10.png");

		// button image
		nobutton_Image = new Image("resources/Terminal/Terminal_No_Button.png");

		// laser images
		laser_beam_image = new Image("resources/laser/laser_beam_blue.png");
		laser_tip_image = new Image("resources/laser/laser_tip_blue.png");

		// countdown bar images
		counterBarImage = new Image("resources/counter_bar.png");

		// text images
		scoretextImage = new Image("resources/text/text_score.png");
		leveltextImage = new Image("resources/text/text_level.png");

		// ceiling image
		ceiling_Image = new Image("resources/ceiling.png");
	}
	
	public void setCeiling(MyRectangle c) {
		ceiling = c;
	}
	
	public void setFloor (MyRectangle floor) {
		this.floor = floor;
	}
	
	public void setLeftWall (MyRectangle leftWall) {
		this.leftWall = leftWall;
	}
	
	public void setRightWall(MyRectangle rightWall) {
		this.rightWall = rightWall;
	}
	
	public void setGateList(ArrayList<Gate> gatelist) {
		this.gateList = gatelist;
	}

}
