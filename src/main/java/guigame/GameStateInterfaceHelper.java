package guigame;

import guimenu.MainGame;
import guiobjects.RND;
import guiobjects.RenderOptions;

import java.util.ArrayList;
import java.util.Iterator;

import logic.FloatingScore;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * GameState Helper class for managing the general UI. This is done to prevent
 * GameState from having too much responsibility and/or knowledge. The class
 * should only be used in conjunction with GameState.
 * @author Mark
 */
public class GameStateInterfaceHelper extends GameStateHelper {
	
	private ArrayList<FloatingScore> floatingScoreList;
	
	private Image health0Image;
	private Image health1Image;
	private Image health2Image;
	private Image health3Image;
	private Image health4Image;
	private Image health5Image;
	private Image nobuttonImage;
	private Image wallsImageN;
	private Image wallsImageA;
	private Image ceilingImageN;
	private Image ceilingImageA;
	private Image counterBarImageN;
	private Image counterBarImageA;
	
	private static final int CEILING_DRAW_X_DEVIATION = 10;
	private static final int CEILING_DRAW_Y_DEVIATION = 25;
	private static final int SCORE_STRING_Y_DEVIATION = 84;
	private static final int HEALTH_IMAGE_THREE = 3;
	private static final int HEALTH_IMAGE_FOUR = 4;
	private static final int HEALTH_IMAGE_FIVE = 5;
	private static final int LEVEL_STRING_X_DEVIATION = 270;
	private static final int LEVEL_STRING_Y_DEVIATION = 84;
	private static final int SHIELD_COUNTER_OFFSET_X = 120;
	private static final int SHIELD_COUNTER_OFFSET_Y = 90;
	private static final int SHIELD_COUNTER_OFFSET_1_Y = -6;
	private static final int SHIELD_COUNTER_OFFSET_1_X = 290;
	private static final int SHIELD_COUNTER_OFFSET_2_X = 305;
	private static final int SHIELD_COUNTER_INCREMENT_Y = 40;
	private static final float SHIELD_COUNTER_DIVIDER = 1000f;
	private static final int COUNTER_BAR_X_FACTOR = 10;
	private static final float COUNT_FACTOR = 15f;
	private static final int STARTING_STRING_X_DEVIATION = 90;
	private static final int STARTING_COUNT_X_DEVIATION = 5;
	private static final int WHOLE_CIRCLE_DEGREES = 360;
	private static final int COUNT_IN_DEGREES = 15;
	private static final int COUNTER_BAR_ROTATION_X = 12;
	private static final int COUNTER_BAR_ROTATION_Y = 50;
	private static final int COUNTER_BAR_DRAW_X_DEVIATION = 10;
	private static final int COUNTER_BAR_DRAW_Y_DEVIATION = 91;
	private static final float COUNT_IN_TIME = 3000f;
	private static final int SECOND_TO_MS_FACTOR = 1000;
	private static final int PAUSED_STRING_X_DEVIATION = 130;
	private static final int PAUSED_STRING_Y_DEVIATION = 53;
	private static final int COUNTDOWN_BAR_PARTS = 56;
	private static final int COUNTER_BAR_X_DEVIATION = 80;
	private static final int COUNTER_BAR_Y_DEVIATION = 60;
	private static final int COUNTER_BAR_PARTS_FACTOR = 5;
	private static final float COUNTIN_OVERLAY_COLOR_FACTOR = 0.5f;
	
	/**
	 * Constructor method for creating GameStateInterfaceHelper object.
	 * @param app the Main Game this class draws data from.
	 * @param state the GameState parent.
	 */
	public GameStateInterfaceHelper(MainGame app, GameState state) {
		this.mainGame = app;
		this.parentState = state;
		initImages();
	}
	
	/**
	 * Initialize the images.
	 */
	private void initImages() {
		try {
			nobuttonImage = new Image("resources/Terminal/Terminal_No_Button.png");
			wallsImageN = new Image("resources/images_Level/walls_Norm.png");
			wallsImageA = new Image("resources/images_Level/walls_Add.png");
			ceilingImageN = new Image("resources/images_Level/ceiling_Norm.png");
			ceilingImageA = new Image("resources/images_Level/ceiling_Add.png");
			nobuttonImage = new Image("resources/Terminal/Terminal_No_Button.png");
			health0Image = new Image("resources/Terminal/Terminal_Lights_0.png");
			health1Image = new Image("resources/Terminal/Terminal_Lights_1.png");
			health2Image = new Image("resources/Terminal/Terminal_Lights_2.png");
			health3Image = new Image("resources/Terminal/Terminal_Lights_3.png");
			health4Image = new Image("resources/Terminal/Terminal_Lights_4.png");
			health5Image = new Image("resources/Terminal/Terminal_Lights_5.png");
			counterBarImageN = new Image("resources/images_UI/counter_Norm.png");
			counterBarImageA = new Image("resources/images_UI/counter_Add.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void enter() {
		floatingScoreList = new ArrayList<FloatingScore>();
	}

	@Override
	public void exit() {
		  throw new UnsupportedOperationException("not supported");
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, float deltaFloat) {
		// anything needed for UI on update without mattering where?
		Iterator<FloatingScore> scoreListIterator = floatingScoreList.iterator();
		synchronized (scoreListIterator) {
			while (scoreListIterator.hasNext()) {
				FloatingScore score = scoreListIterator.next();
				if (score.isDead()) {
					scoreListIterator.remove();
				} else {
					score.update(deltaFloat, parentState.getLogicHelper().getTimeDelta());
				}
			}
		}
	}
	
	@Override
	public void render(Graphics graphics, GameContainer container) {
		
	}
	
	/**
	 * Render the bottom layer of the GUI in gamestate.
	 * @param graphics context to draw in.
	 * @param container in which most objects are located.
	 */
	public void renderBottomLayer(Graphics graphics, GameContainer container) {
		RND.getInstance().drawColor(new RenderOptions(graphics, ceilingImageN, 
				ceilingImageA, parentState.getLevelsHelper().getLeftWall().getWidth()
					- CEILING_DRAW_X_DEVIATION, 
				parentState.getLevelsHelper().getCeiling().getHeight() - CEILING_DRAW_Y_DEVIATION, 
				mainGame.getColor()));
		RND.getInstance().drawColor(new RenderOptions(graphics, wallsImageN, wallsImageA, 
				0, 0, mainGame.getColor()));
		for (int x = 0; x < parentState.getLogicHelper().getFractionTimeParts(); x++) {
			RND.getInstance().drawColor(new RenderOptions(graphics, counterBarImageN, 
					counterBarImageA,
					container.getWidth() / 2 - COUNTER_BAR_X_DEVIATION - COUNTER_BAR_PARTS_FACTOR
					* (COUNTDOWN_BAR_PARTS) + x * COUNTER_BAR_X_FACTOR,
					container.getHeight() - COUNTER_BAR_Y_DEVIATION, mainGame.getColor()));
		}
		mainGame.drawWaterMark();
		renderFloatingScores(graphics);
		RND.getInstance().text(graphics, container.getWidth() / 2 - LEVEL_STRING_X_DEVIATION, 
				container.getHeight() - LEVEL_STRING_Y_DEVIATION, "Level: "
						+ Integer.toString(mainGame.getLevelCounter() + 1));
		renderShieldTimer(graphics);
		renderScore(graphics, container);
	}
	
	/**
	 * Render the top layer of the GUI in gamestate.
	 * @param graphics context to draw in.
	 * @param container in which most objects are located.
	 * @param playingState whether to draw the pause menu etc
	 */
	public void renderTopLayer(Graphics graphics, GameContainer container, boolean playingState) {
		RND.getInstance().drawForeGround(graphics);
		if (!playingState) {
			graphics.drawImage(nobuttonImage, 0, 0);
		}
		renderHealth(graphics);
	}

	/**
	 * Draw the count in.
	 * @param container the GameContainer we are playing in
	 * @param graphics the Graphics object to draw things on screen
	 * @param timeDelta used to determine how much time has elapsed in counting
	 */
	public void renderCountIn(GameContainer container, Graphics graphics, long timeDelta) {
		int count = (int) Math.ceil((COUNT_IN_TIME - timeDelta) / SECOND_TO_MS_FACTOR),
				amount = Math.round((COUNT_IN_TIME - timeDelta) / COUNT_IN_TIME * COUNT_FACTOR);

		graphics.setColor(new Color(0f, 0f, 0f, COUNTIN_OVERLAY_COLOR_FACTOR));
		graphics.fillRect(0, 0, container.getWidth(), container.getHeight());

		RND.getInstance().text(graphics, container.getWidth() / 2 - STARTING_STRING_X_DEVIATION,
				container.getHeight() / 2 - PAUSED_STRING_X_DEVIATION, "Starting in");
		RND.getInstance().text(graphics, container.getWidth() / 2 - STARTING_COUNT_X_DEVIATION,
				container.getHeight() / 2 - PAUSED_STRING_Y_DEVIATION, Integer.toString(count));
		
		for (int i = 0; i < amount; i++) {
            float degree = i * (WHOLE_CIRCLE_DEGREES / COUNT_IN_DEGREES);
            counterBarImageN.setCenterOfRotation(COUNTER_BAR_ROTATION_X, COUNTER_BAR_ROTATION_Y);
            counterBarImageA.setCenterOfRotation(COUNTER_BAR_ROTATION_X, COUNTER_BAR_ROTATION_Y);
            counterBarImageN.rotate(degree);
            counterBarImageA.rotate(degree);
            RND.getInstance().drawColor(new RenderOptions(graphics, counterBarImageN, 
            		counterBarImageA, container.getWidth() / 2 - COUNTER_BAR_DRAW_X_DEVIATION, 
            		container.getHeight() / 2 - COUNTER_BAR_DRAW_Y_DEVIATION, 
            		mainGame.getColor()));
            counterBarImageN.rotate(-degree);
            counterBarImageA.rotate(-degree);
        }
	}
	
	/**
	 * Draw the floating scores.
	 * @param graphics context to draw in.
	 */
	private void renderFloatingScores(Graphics graphics) {
		synchronized (floatingScoreList) {
			for (FloatingScore score : floatingScoreList) {
				RND.getInstance().textSpecifiedColor(graphics, score.getX(), score.getY(), 
						score.getScore(),
						new Color(mainGame.getColor().r, mainGame.getColor().g,
								mainGame.getColor().b, score.getOpacity()));
			}
		}
	}
	
	/**
	 * Draw the score numbers in the interface.
	 * @param graphics context to draw in.
	 * @param container most objects are located in.
	 */
	private void renderScore(Graphics graphics, GameContainer container) {
		String renderedScore;
		if (mainGame.getShouldSwitchState()) {
			renderedScore = Integer.toString(mainGame.getScore());
		} else {
			renderedScore = Integer.toString(mainGame.getScore() 
					+ parentState.getLogicHelper().getScore());
		}
		RND.getInstance().text(graphics, (float) container.getWidth() / 2.0f, container.getHeight()
				- SCORE_STRING_Y_DEVIATION, "Score: " + renderedScore);
	}
	
	/**
	 * Draw health lights.
	 * @param graphics context to draw in
	 */
	private void renderHealth(Graphics graphics) {
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
	 * Draw the shield timer.
	 * @param graphics the Graphics object to draw things on screne
	 */
	private void renderShieldTimer(Graphics graphics) {
		int height = SHIELD_COUNTER_OFFSET_Y;
		if (mainGame.getPlayerList().getPlayers().get(0).getPowerupHelper().hasShield()) {
			height += SHIELD_COUNTER_INCREMENT_Y;
			renderShieldTimerPlayer1(graphics, height);
		}
		if ((mainGame.isMultiplayer() || mainGame.isLanMultiplayer()) 
				&& mainGame.getPlayerList().getPlayers().get(1).getPowerupHelper().hasShield()) {
			height += SHIELD_COUNTER_INCREMENT_Y;
			renderShieldTimerPlayer2(graphics, height);
		}
	}
	
	/**
	 * Draw the shield timer for player 1, if necessary.
	 * @param graphics the context to draw in
	 * @param height the height to draw the timer at
	 */
	private void renderShieldTimerPlayer1(Graphics graphics, int height) {
		float rem = mainGame.getPlayerList().getPlayers().get(0).
				getPowerupHelper().getShieldTimeRemaining();
		RND.getInstance().text(graphics, SHIELD_COUNTER_OFFSET_X, height, ">PL_1.Sh():");
		for (int x = 0; x < Math.round(rem / SHIELD_COUNTER_DIVIDER); x++) {
			RND.getInstance().drawColor(new RenderOptions(graphics, counterBarImageN, 
					counterBarImageA, SHIELD_COUNTER_OFFSET_1_X + x * COUNTER_BAR_X_FACTOR, 
					height + SHIELD_COUNTER_OFFSET_1_Y, mainGame.getColor()));
		}
		RND.getInstance().text(graphics, SHIELD_COUNTER_OFFSET_2_X
				+ Math.round(rem / SHIELD_COUNTER_DIVIDER)
				* COUNTER_BAR_X_FACTOR, height,
				"#" + rem / SHIELD_COUNTER_DIVIDER + "s"); 
	}
	
	/**
	 * Draw the shield timer for player 2, if necessary.
	 * @param graphics the context to draw in
	 * @param height the height to draw the timer at
	 */
	private void renderShieldTimerPlayer2(Graphics graphics, int height) {
		float rem = mainGame.getPlayerList().getPlayers().get(1).
				getPowerupHelper().getShieldTimeRemaining();
		RND.getInstance().text(graphics, SHIELD_COUNTER_OFFSET_X, height, ">PL_2.Sh():");
		for (int x = 0; x < Math.round(rem / SHIELD_COUNTER_DIVIDER); x++) {
			RND.getInstance().drawColor(new RenderOptions(graphics, counterBarImageN, 
					counterBarImageA, SHIELD_COUNTER_OFFSET_1_X + x * COUNTER_BAR_X_FACTOR, 
					height + SHIELD_COUNTER_OFFSET_1_Y, mainGame.getColor()));
		}
		RND.getInstance().text(graphics, SHIELD_COUNTER_OFFSET_2_X 
				+ Math.round(rem / SHIELD_COUNTER_DIVIDER) 
				* COUNTER_BAR_X_FACTOR, height, 
				"#" + rem / SHIELD_COUNTER_DIVIDER + "s"); 
	}
	
	/**
	 * @return the floatingScoreList
	 */
	public ArrayList<FloatingScore> getFloatingScores() {
		return floatingScoreList;
	}

	/**
	 * @param floatingScoreList the floatingScoreList to set
	 */
	public void setFloatingScores(ArrayList<FloatingScore> floatingScoreList) {
		this.floatingScoreList = floatingScoreList;
	}
	
}
