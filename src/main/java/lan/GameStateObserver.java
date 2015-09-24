package lan;

import gui.GameState;

/**
 * javadoc.
 * @author Bart
 *
 */
public abstract class GameStateObserver {
	private GameState gameState;
	
	/**
	 * javadoc.
	 */
	public abstract void update();

	/**
	 * @return the gameState
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * @param gameState the gameState to set
	 */
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	
}
