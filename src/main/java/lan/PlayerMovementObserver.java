package lan;

import gui.GameState;

/**
 * javadoc.
 * @author Bart
 *
 */
public class PlayerMovementObserver extends GameStateObserver {

	private Host host;
	
	/**
	 * javadoc.
	 * @param gameState .
	 * @param host .
	 */
	public PlayerMovementObserver(GameState gameState, Host host) {
		this.setGameState(gameState);
		this.host = host;
	}
	
	@Override
	public void update() {
		host.sendMessageToClient("player movement :O");
	}

}
