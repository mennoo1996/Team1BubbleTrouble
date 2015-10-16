package logic;
import guigame.GameState;
import guimenu.MainGame;

import java.util.ArrayList;
import java.util.LinkedList;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;

import powerups.Powerup;

/**
 * This class represents a Player.
 * @author Menno
 *
 */
public class Player {
	
	private PlayerMovementHelper movementHelper;
	private PlayerPowerupHelper powerupHelper;
	private PlayerWeaponHelper weaponHelper;
	private PlayerGateHelper gateHelper;
	private PlayerLogicHelper logicHelper;

	
	private MainGame mainGame;

	private GameState gameState;
	
	private int playerNumber;
	private String playerName = "";
	private int moveLeftKey;
	private int moveRightKey;
	private int shootKey;
	
	private static final int SPRITESHEET_VALUE = 120;
	private static final int PLAYER1_X_DEVIATION = 720;
	private static final int PLAYER2_X_DEVIATION = 420;
	private static final int PLAYER_Y_DEVIATION = 705;
	
	private static final String POWERUPS = "powerups";

	/**
	 * Constructor class.
	 * @param x coordinate
	 * @param y coordinate
	 * @param width of player
	 * @param height of palyer
	 * @param imageN normal image
	 * @param imageA additive image
	 * @param shieldImageN normal shield image
	 * @param shieldImageA additive shield image
	 * @param mainGame maingame app object
	 */
	public Player(float x, float y, float width, float height, 
			Image imageN, Image imageA, Image shieldImageN,
			Image shieldImageA, MainGame mainGame) {
		super();
		this.mainGame = mainGame;
		this.gameState = (GameState) mainGame.getState(mainGame.getGameState());
		moveLeftKey = Input.KEY_LEFT;
		moveRightKey = Input.KEY_RIGHT;
		shootKey = Input.KEY_SPACE;
		this.movementHelper = new PlayerMovementHelper(this, gameState);
		this.powerupHelper = new PlayerPowerupHelper(this, mainGame, gameState);
		this.weaponHelper = new PlayerWeaponHelper(mainGame, gameState, this);
		this.gateHelper = new PlayerGateHelper(mainGame, gameState, this);
		this.logicHelper = new PlayerLogicHelper(this, mainGame, gameState, x, y);
		logicHelper.setSpritesheet(new SpriteSheet(imageN, SPRITESHEET_VALUE, SPRITESHEET_VALUE), 
				new SpriteSheet(imageA, SPRITESHEET_VALUE, SPRITESHEET_VALUE));
		logicHelper.setImage(imageN, imageA);
		logicHelper.setShieldImage(shieldImageN, shieldImageA);
		logicHelper.setX(x);
		logicHelper.setY(y);
		logicHelper.setWidth(width);
		logicHelper.setHeight(height);
	}
	
	/**
	 * Update this player.
	 * @param deltaFloat the time in ms since the last frame.
	 * @param containerHeight - the height of the container
	 * @param containerWidth - the width of the container
	 * @param testing	are we testing or not
	 */
	public void update(float deltaFloat, float containerHeight, float containerWidth, 
			boolean testing) {
		gateHelper.processGates();
		weaponHelper.processWeapon(deltaFloat, containerHeight, testing);
		movementHelper.processPlayerMovement(deltaFloat, containerWidth, testing);
		powerupHelper.update(deltaFloat, containerHeight);
		processCoins();
	}

	/**
	 * Add a powerup to the player.
	 * @param type powerup type
	 */
	public void addPowerup(Powerup.PowerupType type) {
		powerupHelper.addPowerup(type);
	}

	/**
	 * Process the intersection of a player with the coins.
	 */
	private void processCoins() {
		ArrayList<Coin> usedCoins = new ArrayList<>();
		synchronized (gameState.getItemsHelper().getDroppedCoins()) {
			for (Coin coin : gameState.getItemsHelper().getDroppedCoins()) {
				if (coin.getRectangle().intersects(logicHelper.getRectangle())) {
					processCoin(coin, usedCoins);
				}
			}
		}
		gameState.getItemsHelper().getDroppedCoins().removeAll(usedCoins);
	}	
	
	/**
	 * Process the intersection of a player with an individual coin.
	 * @param coin to process
	 * @param usedCoins to dump it in
	 */
	private void processCoin(Coin coin, ArrayList<Coin> usedCoins) {
		if (!mainGame.isLanMultiplayer() | (mainGame.isHost() & playerNumber == 0)) {
			gameState.getLogicHelper().addToScore(coin.getPoints());
			gameState.getInterfaceHelper().getFloatingScores().
			add(new FloatingScore(coin));
			usedCoins.add(coin);
			Logger.getInstance().log("Picked up coin", 
					Logger.PriorityLevels.MEDIUM, POWERUPS);
			if (mainGame.isHost() & playerNumber == 0) {
				mainGame.getHost().updateCoinsDictate(coin);
			}
		} else if (mainGame.isClient() & playerNumber == 1) {
			mainGame.getClient().pleaCoin(coin);
		}
	}

	/**
	 * @return Whether or not current player is the host
	 */
	public boolean isOthersPlayer() {
		if (mainGame.isHost()) {
			return playerNumber == 1;
		} else {
			return playerNumber == 0;
		}
	}
	
	/**
	 * Reset this player to its original location.
	 * @param m multiplayer index of player
	 */
	public void resetPlayerLocation(int m) {
		if (m == 0) {
			logicHelper.setX(PLAYER1_X_DEVIATION);
		} else {
			logicHelper.setX(PLAYER2_X_DEVIATION);
		}
		logicHelper.setY(PLAYER_Y_DEVIATION);
	}
	
	/**
	 * @return the gameState
	 */
	public GameState getgameState() {
		return gameState;
	}

	/**
	 * @param gameState the gameState to set
	 */
	public void setgameState(GameState gameState) {
		this.gameState = gameState;
	}

	/**
	 * @return the moveLeftKey
	 */
	public int getMoveLeftKey() {
		return moveLeftKey;
	}

	/**
	 * @param moveLeftKey the moveLeftKey to set
	 */
	public void setMoveLeftKey(int moveLeftKey) {
		this.moveLeftKey = moveLeftKey;
	}

	/**
	 * @return the moveRightKey
	 */
	public int getMoveRightKey() {
		return moveRightKey;
	}

	/**
	 * @param moveRightKey the moveRightKey to set
	 */
	public void setMoveRightKey(int moveRightKey) {
		this.moveRightKey = moveRightKey;
	}

	/**
	 * @return the key used for shooting
	 */
	public int getShootKey() {
		return shootKey;
	}

	/**
	 * @param shootKey define the key used for shooting.
	 */
	public void setShootKey(int shootKey) {
		this.shootKey = shootKey;
	}

	/**
	 * Respawn player (i.e. reset powerups, location, etc)
	 */
	public void respawn() {
		weaponHelper.setWeapons(new LinkedList<>());
		powerupHelper.respawn();
		logicHelper.respawn();
	}

	/**
	 * @return the shot
	 */
	public boolean isShot() {
		return weaponHelper.isShot();
	}

	/**
	 * @param shot set that player is shot.
	 */
	public void setShot(boolean shot) {
		weaponHelper.setShot(shot);
	}

	/**
	 * @return the playerNumber
	 */
	public int getPlayerNumber() {
		return playerNumber;
	}

	/**
	 * @param playerNumber the playerNumber to set
	 */
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
		weaponHelper.setPlayerNumber(playerNumber);
	}
	
	/**
	 * Set the player's display name.
	 * @param name to set.
	 */
	public void setPlayerName(String name) {
		this.playerName = name;
	}
	
	/**
	 * @return The player's display name.
	 */
	public String getPlayerName() {
		return playerName;
	}
	
	/**
	 * Changes controls of player to be arrow-key based.
	 */
	public void setControlsForPlayer1() {
		setMoveLeftKey(Input.KEY_LEFT);
		setMoveRightKey(Input.KEY_RIGHT);
		setShootKey(Input.KEY_SPACE);
	}
	
	/**
	 * Changes controls of player to be wasd-key based.
	 */
	public void setControlsForPlayer2() {
		setMoveLeftKey(Input.KEY_A);
		setMoveRightKey(Input.KEY_D);
		setShootKey(Input.KEY_W);
	}
	
	/**
	 * Changes controls of player to be disabled.
	 */
	public void setControlsDisabled() {
		setMoveLeftKey(0);
		setMoveRightKey(0);
		setShootKey(0);
	}

	/**
	 * Update the info in the movement helper, as fetched from the logichelper and mainGame.
	 */
	public void updateMovementHelperInfo() {
		movementHelper.setX(logicHelper.getX());
		movementHelper.setY(logicHelper.getY());
		movementHelper.setPlayerNumber(playerNumber);
		movementHelper.setMaxX(logicHelper.getMaxX());
		movementHelper.setCenterX(logicHelper.getCenterX());
		movementHelper.setMoveLeftKey(moveLeftKey);
		movementHelper.setMoveRightKey(moveRightKey);
		movementHelper.setIsClient(mainGame.isClient());
		movementHelper.setIsHost(mainGame.isHost());
		movementHelper.setLanMultiplayer(mainGame.isLanMultiplayer());
		movementHelper.setHost(mainGame.getHost());
		movementHelper.setClient(mainGame.getClient());
		movementHelper.setPlayerSpeed(mainGame.getPlayerSpeed());
		movementHelper.setRightWallWidth(gameState.getRightWall().getWidth());
		movementHelper.setLeftWallWidth(gameState.getLeftWall().getWidth());
	}
	
	/**
	 * @return the helper class for player movement
	 */
	public PlayerMovementHelper getMovementHelper() {
		return movementHelper;
	}
	
	/**
	 * @return the helper class for player powerups
	 */
	public PlayerPowerupHelper getPowerupHelper() {
		return powerupHelper;
	}
	
	/**
	 * @return the helper class for player weapons
	 */
	public PlayerWeaponHelper getWeaponHelper() {
		return weaponHelper;
	}
	
	/**
	 * @return the helper class for player weapons
	 */
	public PlayerGateHelper getGateHelper() {
		return gateHelper;
	}
	
	/**
	 * @return the helper class for player logic/images/location
	 */
	public PlayerLogicHelper getLogicHelper() {
		return logicHelper;
	}
	
}
