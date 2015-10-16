package logic;

import guigame.GameState;
import guimenu.MainGame;

/**
 * Class which helps player processing gates. Should only be used by the Player class.
 * @author Mark
 *
 */
public class PlayerGateHelper {
	private MainGame mainGame;
	private GameState gameState;
	private Player player;

	private boolean freeToRoam;
	
	/**
	 * Constructor for a PlayerGateHelper object.
	 * @param mainGame		the maingame the player plays in.
	 * @param gameState		the gamestate the player plays in.
	 * @param player		the player ythis helper belongs to.
	 */
	public PlayerGateHelper(MainGame mainGame, GameState gameState, Player player) {
		this.mainGame = mainGame;
		this.gameState = gameState;
		this.player = player;
	}
	
	/**
	 * Process the intersection with gates.
	 */
	public void processGates() {
		// Check the intersection of a player with a gate
		freeToRoam = true;
		synchronized (gameState.getGateHelper().getGateList()) {
			for (Gate someGate :gameState.getGateHelper().getGateList()) {
				if (player.getLogicHelper().getRectangle().intersects(someGate.getRectangle())) {
					freeToRoam = false;
					player.getMovementHelper().setIntersectingGate(someGate);
				}
			}
		}
		// Reset intersecting gate to none if there is no intersection
		if (freeToRoam) {
			player.getMovementHelper().setIntersectingGate(null);
		}
	}
	
	/**
	 * @return the freeToRoam
	 */
	public boolean isFreeToRoam() {
		return freeToRoam;
	}
	
	/**
	 * @param freeToRoam the freeToRoam to set
	 */
	public void setFreeToRoam(boolean freeToRoam) {
		this.freeToRoam = freeToRoam;
	}
	
}
