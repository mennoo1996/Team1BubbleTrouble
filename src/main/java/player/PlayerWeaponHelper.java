package player;

import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import guigame.GameState;
import guimenu.MainGame;
import logic.Logger;
import logic.Weapon;
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

	private boolean shot;
	
	private static final String PLAYER = "Player";
	private static final int POWERUP_DURATION = 10;

	private LinkedList<Powerup.PowerupType> weapons;
	
	/**
	 * @param mainGame		the maingame the player plays in.
	 * @param gameState		the gamestate the player plays in.
	 * @param player		the player ythis helper belongs to.
	 */
	public PlayerWeaponHelper(MainGame mainGame, GameState gameState, Player player) {
		this.mainGame = mainGame;
		this.gameState = gameState;
		this.player = player;
		this.shot = false;
		this.weapons = new LinkedList<>();
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
			gameState.getPlayerHelper().getWeaponList().setWeapon(player.getPlayerNumber(), 
					this.getWeapon(containerHeight));
			Weapon weapon = gameState.getPlayerHelper().getWeaponList().
					getWeaponList().get(player.getPlayerNumber());
			boolean spiky = (weapon instanceof Spiky);
			if (mainGame.isHost()) {
				mainGame.getHost().updateLaser(player.getPlayerNumber(), weapon.getX(), 
						weapon.getY(), weapon.getLaserSpeed(), weapon.getWidth(), spiky);
			} else if (mainGame.isClient()) {
				mainGame.getClient().updateLaser(player.getPlayerNumber(), weapon.getX(), 
						weapon.getY(), weapon.getLaserSpeed(), weapon.getWidth(), spiky);
			}
		}
		Weapon weapon = gameState.getPlayerHelper().getWeaponList().
				getWeaponList().get(player.getPlayerNumber());
		// Update laser
		if (shot) {
			weapon.update(gameState.getLevelsHelper().getCeiling(), 
					gameState.getLevelsHelper().getFloor(), deltaFloat);
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
		if (weapons.isEmpty()) {
			Logger.getInstance().log("Shot regular laser from position " 
		+ player.getLogicHelper().getCenterX(),
					PriorityLevels.MEDIUM, PLAYER);
			return new Weapon(player.getLogicHelper().getCenterX(), 
					containerHeight - gameState.getLevelsHelper().getFloor().getHeight(),
					mainGame.getLaserSpeed(), mainGame.getLaserWidth());
		}
		Powerup.PowerupType subType = weapons.peekLast();
		if (subType == Powerup.PowerupType.SPIKY) {
			Logger.getInstance().log("Shot spiky laser from position " 
		+ player.getLogicHelper().getCenterX(), 
					PriorityLevels.HIGH, PLAYER);
			return new Spiky(player.getLogicHelper().getCenterX(), containerHeight 
					- gameState.getLevelsHelper().getFloor().getHeight(), mainGame.getLaserSpeed(), 
					mainGame.getLaserWidth());
		}
		if (subType == Powerup.PowerupType.INSTANT) {
			Logger.getInstance().log("Shot instant laser from position " 
		+ player.getLogicHelper().getCenterX(), 
					PriorityLevels.HIGH, PLAYER);
			return new InstantLaser(player.getLogicHelper().getCenterX(),
					containerHeight - gameState.getLevelsHelper().getFloor().getHeight(), 
						mainGame.getLaserWidth());
		}
		// Wrong weapon type, time to crash hard.
		throw new EnumConstantNotPresentException(Powerup.PowerupType.class, subType.toString());
	}

	/**
	 * Add a weapon for this player.
	 * @param type the type of the powerup
	 */
	public void addWeapon(Powerup.PowerupType type) {
		weapons.add(type);
		Executors.newScheduledThreadPool(1).schedule(() -> {
					if (!weapons.isEmpty()) {
						weapons.removeFirst();
					}
				},
				POWERUP_DURATION, TimeUnit.SECONDS);
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
	
	/**
	 * @return the weapons
	 */
	public LinkedList<Powerup.PowerupType> getWeapons() {
		return weapons;
	}
	
	/**
	 * Set the weapons to this new list.
	 * @param newList the list to set.
	 */
	public void setWeapons(LinkedList<Powerup.PowerupType> newList) {
		weapons = newList;
	}
	
}
