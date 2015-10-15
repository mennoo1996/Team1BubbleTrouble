package logic;

import guigame.GameState;
import guimenu.MainGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import powerups.FastPowerup;
import powerups.FreezePowerup;
import powerups.Powerup;
import powerups.SlowPowerup;
import powerups.SpeedPowerup;

/**
 * Class which helps player processing powerups.
 * @author Bart
 *
 */
public class PlayerPowerupHelper {
	
	private Player player;
	private MainGame mainGame;
	private GameState gameState;
	
	private long shieldTimeRemaining;
	private int shieldCount;

	private static final int SECONDS_TO_MS = 1000;
	private static final int RANDOM_DURATION = 100;
	private static final int POWERUP_DURATION = 10;
	private static final String POWERUPS = "powerups";
	
	/**
	 * @param player	- the player this helper belongs to.
	 * @param mainGame	- the gamestate the player plays in.
	 * @param gameState	- the game the player plays in.
	 */
	public PlayerPowerupHelper(Player player, MainGame mainGame, GameState gameState) {
		super();
		this.player = player;
		this.mainGame = mainGame;
		this.gameState = gameState;
		this.shieldTimeRemaining = 0;
		this.shieldCount = 0;
	}

	/**
	 * Process the intersection of a player with the powerups.
	 * @param deltaFloat the time in seconds since the last frame.
	 * @param containerHeight the height of the current GameContainer
	 */
	public void update(float deltaFloat, float containerHeight) { // MARKED
		if (!gameState.getLogicHelper().isPaused() && shieldTimeRemaining > 0) {
			shieldTimeRemaining -= deltaFloat * SECONDS_TO_MS;
		}
		ArrayList<Powerup> usedPowerups = new ArrayList<>();
		synchronized (gameState.getItemsHelper().getDroppedPowerups()) {
			for (Powerup powerup : gameState.getItemsHelper().getDroppedPowerups()) {
				powerup.update(gameState, containerHeight, deltaFloat);

				if (powerup.getRectangle().intersects(player.getRectangle())) {
					if (!mainGame.isLanMultiplayer() | (mainGame.isHost() 
							& player.getPlayerNumber() == 0)) {
						//Add a powerup to the player
						this.addPowerup(powerup.getType());
						gameState.getInterfaceHelper().getFloatingScores().
						add(new FloatingScore(powerup));
						usedPowerups.add(powerup);

						if (mainGame.isHost() & player.getPlayerNumber() == 0) {
							mainGame.getHost().updatePowerupsDictate(powerup);
						}
					} else if (mainGame.isClient() & player.getPlayerNumber() == 1) {
						mainGame.getClient().pleaPowerup(powerup);
					}
				}
			}
		}
		gameState.getItemsHelper().getDroppedPowerups().removeAll(usedPowerups);
		gameState.getItemsHelper().getDroppedPowerups().removeIf(Powerup::removePowerup);
	}
	
	/**
	 * Add a powerup to the player.
	 * @param type powerup type
	 */
	public void addPowerup(Powerup.PowerupType type) {
		Logger.getInstance().log("Adding powerup " + type.toString(),
				Logger.PriorityLevels.MEDIUM, POWERUPS);
		if (type == Powerup.PowerupType.INSTANT) {
			player.addWeapon(type);
		} else if (type == Powerup.PowerupType.SHIELD) {
			player.addShield();
		} else if (type == Powerup.PowerupType.SPIKY) {
			player.addWeapon(type);
		} else if (type == Powerup.PowerupType.FREEZE) {
			addFreeze();
		} else if (type == Powerup.PowerupType.SLOW) {
			addSlow();
		} else if (type == Powerup.PowerupType.FAST) {
			addFast();
		} else if (type == Powerup.PowerupType.HEALTH) {
			addHealth();
		} else if (type == Powerup.PowerupType.RANDOM) {
			addRandom();
		}
	}

	/**
	 * Add a freeze powerup to the player.
	 */
	private void addFreeze() {
		FreezePowerup fp = new FreezePowerup();
		GameState gs = (GameState) mainGame.getState(mainGame.getGameState());
		gs.getItemsHelper().getSpeedPowerupList().add(fp);
		fp.updateCircles(gs.getCirclesHelper().getCircleList());
	}
	
	/**
	 * Add a slow powerup to the player.
	 */
	private void addSlow() {
		SlowPowerup sp = new SlowPowerup();
		GameState gs = (GameState) mainGame.getState(mainGame.getGameState());
		ArrayList<SpeedPowerup> list = gs.getItemsHelper().getSpeedPowerupList();
		list.add(sp);
		sp.updateCircles(gs.getCirclesHelper().getCircleList());
	}
	
	/**
	 * Add a fast powerup to the player.
	 */
	private void addFast() {
		FastPowerup fp = new FastPowerup();
		GameState gs = (GameState) mainGame.getState(mainGame.getGameState());
		gs.getItemsHelper().getSpeedPowerupList().add(fp);
		fp.updateCircles(gs.getCirclesHelper().getCircleList());
	}
	
	/**
	 * Add a random powerup to the player. We do this by spawning another powerup 
	 * (only for the playing player!!!) so multiplayer code can handle this by itself.
	 */
	private void addRandom() {
		Powerup.PowerupType newPowerup = Powerup.PowerupType.values()[new Random()
		.nextInt(Powerup.PowerupType.values().length - 1)];
		Executors.newScheduledThreadPool(1).schedule(() -> {
			Powerup powerup = new Powerup(player.getX(), player.getY(), newPowerup);
			synchronized (gameState.getItemsHelper().getDroppedPowerups()) {
				if (!mainGame.isLanMultiplayer()) {
					gameState.getItemsHelper().getDroppedPowerups().add(powerup);
				} else if (mainGame.isHost() & player.getPlayerNumber() == 0) {
					gameState.getItemsHelper().getDroppedPowerups().add(powerup);
					mainGame.getHost().updatePowerupsAdd(powerup);
				} else if (mainGame.isClient() & player.getPlayerNumber() == 1) {
					mainGame.getClient().updatePowerupsAdd(powerup);
				}
			}
		}, RANDOM_DURATION, TimeUnit.MILLISECONDS);

	}
	
	/**
	 * Add health to the player.
	 */
	private void addHealth() {
		if (mainGame.getLifeCount() < MainGame.getLives()) {
			mainGame.setLifeCount(mainGame.getLifeCount() + 1);
		}
	}
	
	/**
	 * Add a shield for this player.
	 */
	public void addShield() {
        shieldCount += 1;
        player.getPowerupHelper().setShieldTimeRemaining(
        		TimeUnit.SECONDS.toMillis(POWERUP_DURATION));
        Executors.newScheduledThreadPool(1).schedule(() -> {
                    if (shieldCount > 0) {
                        shieldCount -= 1;
                    }
                },
                POWERUP_DURATION, TimeUnit.SECONDS);
    }
	
	/**
	 * Called from the Player class on respawns, resetting all necessary variables.
	 */
	public void respawn() {
		shieldTimeRemaining = 0;
		shieldCount = 0;
	}
	
	/**
	 * @return shield time remaing (MS)
	 */
	public float getShieldTimeRemaining() {
		if (shieldCount > 0) {
			return shieldTimeRemaining;
		} else {
			return 0;
		}
	}

	/**
	 * @param shieldTimeRemaining the shieldTimeRemaining to set
	 */
	public void setShieldTimeRemaining(long shieldTimeRemaining) {
		this.shieldTimeRemaining = shieldTimeRemaining;
	}
	
	/**
	 * @return Whether or not the player has a shield
	 */
	public boolean hasShield() {
		return shieldCount > 0;
	}
	
}
