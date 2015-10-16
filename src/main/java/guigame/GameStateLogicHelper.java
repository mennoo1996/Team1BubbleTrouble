package guigame;

import guimenu.MainGame;
import logic.Logger;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

/**
 * GameState Helper class for managing time and logic. This is done to prevent GameState 
 * from holding too much responsibility and/or knowledge. The class should only
 * be used in conjunction with GameState.
 * @author Mark
 */
public class GameStateLogicHelper extends GameStateHelper {

	private MainGame mainGame;
	private GameState parentState;
	
	private int totaltime;
	private long timeDelta;
	private long timeRemaining;
	private long prevTime;
	private int fractionTimeParts;
	private boolean countinStarted;
	private boolean countIn;
	private boolean playingState;
	private boolean levelStarted;
	private boolean waitForLevelEnd = false;
	
	private int score;
	

	private static final int SECOND_TO_MS_FACTOR = 1000;
	private static final float SECOND_TO_MS_FACTOR_FLOAT = 1000f;
	private static final int COUNTDOWN_BAR_PARTS = 56;
	private static final float COUNT_IN_TIME = 3000f;
	private static final int LEVEL_POINTS = 150;
	private static final float TIME_REMAINING_FACTOR = 0.01f;
	
	/**
	 * Constructor for a GameStateTimeHelper object.
	 * @param app the Main Game this class draws data from.
	 * @param state the GameState parent.
	 */
	public GameStateLogicHelper(MainGame app, GameState state) {
		this.mainGame = app;
		this.parentState = state;
	}
	
	@Override
	public void enter() {
		totaltime = parentState.getLevelsHelper().getLevelContainer().
				getLevel(mainGame.getLevelCounter()).getTime() * SECOND_TO_MS_FACTOR;
		fractionTimeParts = COUNTDOWN_BAR_PARTS;
		timeRemaining = totaltime;
		prevTime = System.currentTimeMillis();
		countIn = true;
		if (mainGame.isHost()) {
			mainGame.getHost().updateCountinStarted();
		} else if (mainGame.isClient()) {
			countIn = false;
		}
		playingState = true;
		score = 0;
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, float deltaFloat) {
		if (playingState & !mainGame.getShouldSwitchState()) {
			updateCountIn(container, sbg, (int) deltaFloat);
		} else {
			parentState.getPauseHelper().update(container, sbg, deltaFloat);
		} 
	}

	/**
	 * Update the Countin, and either go through with that or play the game.
	 * @param container The GameContainer this state is used in
	 * @param sbg The state based game that uses this state
	 * @param delta The time in ms since the last frame
	 */
	private void updateCountIn(GameContainer container, StateBasedGame sbg, int delta) {
		long curTime = System.currentTimeMillis();
		timeDelta = curTime - prevTime;
		if ((countIn & !mainGame.isClient()) || (mainGame.isClient() & countinStarted)) {
			switchCountInOff(curTime);
		} else if (!mainGame.isLanMultiplayer() | mainGame.isHost() | levelStarted) {
			playGame(container, sbg, delta, curTime);
		}
	}
	
	/**
	 * Update the Countin values from the Update loop.
	 * @param curTime used to set previous starting time.
	 */
	private void switchCountInOff(long curTime) {
		if (timeDelta >= COUNT_IN_TIME) {
			Logger.getInstance().log("Starting level", 
					Logger.PriorityLevels.MEDIUM, "levels");
			countIn = false;
			mainGame.getPlayerList().setDied(false);
			if (mainGame.isHost()) {
				mainGame.getHost().updateLevelStarted();
			}
			prevTime = curTime;					
		}
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
		if (parentState.getSavedInput().isKeyDown(Input.KEY_ESCAPE)) {
			parentState.getPauseHelper().pauseStarted(false);
        }
		parentState.getPlayerHelper().update(container, sbg, deltaFloat);
		parentState.getCirclesHelper().update(container, sbg, deltaFloat);
		parentState.getItemsHelper().update(container, sbg, deltaFloat);
		parentState.getInterfaceHelper().update(container, sbg, deltaFloat);
		if (parentState.getCirclesHelper().getCircleList().getCircles().isEmpty()) {
			endLevel();
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
	 * Stop the paused mode of the game.
	 * @param fromPeer	indicates if this has been requested from peer
	 */
	public void pauseStopped(boolean fromPeer) {
		prevTime = System.currentTimeMillis();
		countIn = true;
		playingState = true;		
		if (mainGame.isHost()) {
			if (!fromPeer) {
				mainGame.getHost().updatePauseStopped();
			} else {
				mainGame.getHost().updateCountinStarted();
			}
		} else if (mainGame.isClient()) {
			mainGame.getClient().updatePauseStopped();
		}
	}
	
	/**
	 * End the level if needed.
	 */
	private void endLevel() {
		waitForLevelEnd = true;
		score += ((double) timeRemaining / totaltime) * LEVEL_POINTS; // add level-ending score
		if (waitForLevelEnd && timeRemaining == 1) {
            mainGame.setScore(mainGame.getScore() + score); // update total score
            int levelCounter = mainGame.getLevelCounter();
            waitForLevelEnd = false;
			if (levelCounter < parentState.getLevelsHelper().getLevelContainer().size() - 1) {
                mainGame.setLevelCounter(mainGame.getLevelCounter() + 1);
                mainGame.setSwitchState(mainGame.getGameState());
            } else {
                mainGame.setSwitchState(mainGame.getGameOverState());
            }
        }
	}
	
	@Override
	public void render(Graphics graphics, GameContainer container) {
		if (playingState & (countIn || (mainGame.isClient() & countinStarted))) {
			parentState.getInterfaceHelper().drawCountIn(container, graphics, timeDelta);
		}
		if (!playingState) {
			parentState.getPauseHelper().render(graphics, container);
		}
		parentState.getInterfaceHelper().renderTopLayer(graphics, container, playingState);
	}

	/**
	 * @return the countinStarted
	 */
	public boolean isCountinStarted() {
		return countinStarted;
	}

	/**
	 * @param countinStarted the countinStarted to set
	 */
	public void setCountinStarted(boolean countinStarted) {
		this.countinStarted = countinStarted;
	}
	
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param points the number to increment score
	 */
	public void addToScore(int points) {
		this.score += points;
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
	 * @return The number of bars to draw in the bottom counter
	 */
	public int getFractionTimeParts() {
		return fractionTimeParts;
	}
	
	/**
	 * @return the elapsed delta time.
	 */
	public long getTimeDelta() {
		return timeDelta;
	}
	
	/**
	 * Set the playingState boolean.
	 * @param state we want to set for PlayingState.
	 */
	public void setPlayingState(boolean state) {
		playingState = state;
	}
	
	/**
	 * @return the PlayingState boolean used to pause the game.
	 */
	public boolean getPlayingState() {
		return playingState;
	}
	
}
