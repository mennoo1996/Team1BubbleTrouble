package logic;

import gui.GameState;
import gui.MainGame;
import logic.Logger.PriorityLevels;
import powerups.InstantLaser;
import powerups.Powerup;
import powerups.Spiky;

/**
 * Class which helps player processing weapons.
 * @author Bart
 *
 */
public class PlayerWeaponHelper {
	private MainGame mainGame;
	private GameState gameState;
	private Player player;
	
	private int playerNumber;
	private boolean shot;
	
	private static final String PLAYER = "Player";
	
	
	
	/**
	 * @param mainGame		the maingame the player plays in.
	 * @param gameState		the gamestate the player plays in.
	 * @param player		the player ythis helper belongs to.
	 */
	public PlayerWeaponHelper(MainGame mainGame, GameState gameState, Player player) {
		super();
		this.mainGame = mainGame;
		this.gameState = gameState;
		this.player = player;
		this.playerNumber = player.getPlayerNumber();
		this.shot = false;
	}

	/**
	 * Process the weapon of this player.
	 * @param deltaFloat the time in seconds since the last frame.
	 * @param containerHeight the height of the current GameContainer
	 * @param testing to check if we are testing or not.
	 */
	public void processWeapon(float deltaFloat, float containerHeight, boolean testing) {
		// Shoot laser when spacebar is pressed and no laser is active
		if (!testing && gameState.getSavedInput().isKeyPressed(player.getShootKey())
				&& !shot) {
			shot = true;
			gameState.getWeaponList().setWeapon(playerNumber, this.getWeapon(containerHeight));
			
			Weapon weapon = gameState.getWeaponList().getWeaponList().get(playerNumber);
			boolean spiky = (weapon instanceof Spiky);
			if (mainGame.isHost()) {
				mainGame.getHost().updateLaser(playerNumber, weapon.getX(), 
						weapon.getY(), weapon.getLaserSpeed(), weapon.getWidth(), spiky);
			} else if (mainGame.isClient()) {
				mainGame.getClient().updateLaser(playerNumber, weapon.getX(), 
						weapon.getY(), weapon.getLaserSpeed(), weapon.getWidth(), spiky);
			}
			
		}
		Weapon weapon = gameState.getWeaponList().getWeaponList().get(playerNumber);
		// Update laser
		if (shot) {
			weapon.update(gameState.getCeiling(), gameState.getFloor(), deltaFloat);
			// Disable laser when it has reached the ceiling
			if (!weapon.isVisible()) {
				shot = false;
			}
		}
	}
	
	/**
	 * Get the weapon of this player.
	 * @param containerHeight the height of the current GameContainer.
	 * @return the Weapon of this player.
	 */
	public Weapon getWeapon(float containerHeight) {
		if (player.getWeapons().isEmpty()) {
			Logger.getInstance().log("Shot regular laser from position " + player.getCenterX(),
					PriorityLevels.MEDIUM, PLAYER);
			return new Weapon(player.getCenterX(), 
					containerHeight - gameState.getFloor().getHeight(),
					mainGame.getLaserSpeed(), mainGame.getLaserWidth());
		}
		Powerup.PowerupType subType = player.getWeapons().peekLast();
		if (subType == Powerup.PowerupType.SPIKY) {
			Logger.getInstance().log("Shot spiky laser from position " + player.getCenterX(), 
					PriorityLevels.HIGH, PLAYER);
			return new Spiky(player.getCenterX(), containerHeight 
					- gameState.getFloor().getHeight(), mainGame.getLaserSpeed(), 
					mainGame.getLaserWidth());
		}
		if (subType == Powerup.PowerupType.INSTANT) {
			Logger.getInstance().log("Shot instant laser from position " + player.getCenterX(), 
					PriorityLevels.HIGH, PLAYER);
			return new InstantLaser(player.getCenterX(),
					containerHeight - gameState.getFloor().getHeight(), mainGame.getLaserWidth());
		}
		// Wrong weapon type, time to crash hard.
		throw new EnumConstantNotPresentException(Powerup.PowerupType.class, subType.toString());
	}

	/**
	 * @param shot the shot to set
	 */
	public void setShot(boolean shot) {
		this.shot = shot;
	}

	/**
	 * @return the shot
	 */
	public boolean isShot() {
		return shot;
	}
	
	

}
