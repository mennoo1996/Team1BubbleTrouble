package logic;
import guigame.GameState;
import guimenu.MainGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import logic.PlayerMovementHelper.Movement;

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

	private int shieldCount;
	private float x;
	private float y;
	private float width;
	private float height;
	private int movementCounter = 0;
	private int movementCounterMax = DEFAULT_MOVEMENTCOUNTER_MAX;
	private Image imageN;
	private Image imageA;
	private Image shieldImageN;
	private Image shieldImageA;
	private SpriteSheet spritesheetN;
	private SpriteSheet spritesheetA;
	private boolean freeToRoam;
	private MainGame mainGame;

	private GameState gameState;
	// weapon management
	private LinkedList<Powerup.PowerupType> weapons;
	private int playerNumber;
	private final float startX;
	private final float startY;
	private String playerName = "";
	private static final int DEFAULT_MOVEMENTCOUNTER_MAX = 18;
	private static final int SPRITESHEET_VALUE = 120;
	private static final int PLAYER1_X_DEVIATION = 720;
	private static final int PLAYER2_X_DEVIATION = 420;
	private static final int PLAYER_Y_DEVIATION = 705;
	private static final float HALF = 0.5f;
	private static final int SECONDS_TO_MS = 1000;
	private int moveLeftKey;
	private int moveRightKey;
	private int shootKey;
	
	private long shieldTimeRemaining;
	
	private Logger logger = Logger.getInstance();
	
	private static final String POWERUPS = "powerups";
	private static final int POWERUP_DURATION = 10;

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
		this.x = x;
		this.y = y;
		this.startX = x;
		this.startY = y;
		this.width = width;
		this.height = height;
		this.imageN = imageN;
		this.imageA = imageA;
		this.shieldImageN = shieldImageN;
		this.shieldImageA = shieldImageA;
		this.spritesheetN = new SpriteSheet(imageN, SPRITESHEET_VALUE, SPRITESHEET_VALUE);
		this.spritesheetA = new SpriteSheet(imageA, SPRITESHEET_VALUE, SPRITESHEET_VALUE);
		this.mainGame = mainGame;
		this.gameState = (GameState) mainGame.getState(mainGame.getGameState());
		moveLeftKey = Input.KEY_LEFT;
		moveRightKey = Input.KEY_RIGHT;
		shootKey = Input.KEY_SPACE;
		this.weapons = new LinkedList<>();
		this.shieldCount = 0;
		this.shieldTimeRemaining = 0;
		
		this.movementHelper = new PlayerMovementHelper(this, gameState);
		this.powerupHelper = new PlayerPowerupHelper(this, mainGame, gameState);
		this.weaponHelper = new PlayerWeaponHelper(mainGame, gameState, this);
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
		if (!gameState.getLogicHelper().isPaused() && shieldTimeRemaining > 0) {
			shieldTimeRemaining -= deltaFloat * SECONDS_TO_MS;
		}
		processGates();
		weaponHelper.processWeapon(deltaFloat, containerHeight, testing);
		movementHelper.processPlayerMovement(deltaFloat, containerWidth, testing);
		powerupHelper.processPowerups(deltaFloat, containerHeight);
		processCoins();
		
	}
	
	/**
	 * Process the intersection with gates.
	 */
	private void processGates() {
		// Check the intersection of a player with a gate
		freeToRoam = true;
		synchronized (gameState.getGateHelper().getGateList()) {
			for (Gate someGate :gameState.getGateHelper().getGateList()) {
				if (this.getRectangle().intersects(someGate.getRectangle())) {
					freeToRoam = false;
					movementHelper.setIntersectingGate(someGate);
				}
			}
		}
		// Reset intersecting gate to none if there is no intersection
		if (freeToRoam) {
			movementHelper.setIntersectingGate(null);
		}
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

				if (coin.getRectangle().intersects(this.getRectangle())) {
					if (!mainGame.isLanMultiplayer() | (mainGame.isHost() & playerNumber == 0)) {
						//Here is the claim
						gameState.getLogicHelper().addToScore(coin.getPoints());
						gameState.getInterfaceHelper().getFloatingScores().
						add(new FloatingScore(coin));
						usedCoins.add(coin);
						logger.log("Picked up coin", 
								Logger.PriorityLevels.MEDIUM, POWERUPS);
						if (mainGame.isHost() & playerNumber == 0) {
							mainGame.getHost().updateCoinsDictate(coin);
						}
					} else if (mainGame.isClient() & playerNumber == 1) {
						mainGame.getClient().pleaCoin(coin);
					}

				}
			}
		}
		//If client no used coins
		gameState.getItemsHelper().getDroppedCoins().removeAll(usedCoins);
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
			this.x = PLAYER1_X_DEVIATION;
		} else {
			this.x = PLAYER2_X_DEVIATION;
		}
		this.y = PLAYER_Y_DEVIATION;
	}
	
	/**
	 * Return a bounding box rectangle of the player.
	 * @return a bounding box rectangle
	 */
	public MyRectangle getRectangle() {
		return new MyRectangle(x, y, width, height);
	}
	
	/**
	 * Get the center X coordinate.
	 * @return the center x
	 */
	public float getCenterX() {
		return x + (HALF * width);
	}
	
	/**
	 * Get the center Y coordinate.
	 * @return the center Y
	 */
	public float getCenterY() {
		return y + (HALF * height);
	}
	
	/**
	 * Get the maximum x value.
	 * @return the maximum x.
	 */
	public float getMaxX() {
		return x + width;
	}
	
	/**
	 * Get the maximum Y value.
	 * @return the maximum Y.
	 */
	public float getMaxY() {
		return y + height;
	}
	
	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	
	/**
	 * @return the player image_norm
	 */
	public Image getImageN() {
		return imageN;
	}
	
	/**
	 * @return the player image_add
	 */
	public Image getImageA() {
		return imageA;
	}
	
	/**
	 * sets player images.
	 * @param imageN normal image
	 * @param imageA additive image
	 */
	public void setImage(Image imageN, Image imageA) {
		this.imageN = imageN;
		this.imageA = imageA;
		this.spritesheetN = new SpriteSheet(imageN, SPRITESHEET_VALUE, SPRITESHEET_VALUE);
		this.spritesheetA = new SpriteSheet(imageA, SPRITESHEET_VALUE, SPRITESHEET_VALUE);
	}
	
	/**
	 * @return the shield image_norm
	 */
	public Image getShieldImageN() {
		return shieldImageN;
	}
	
	/**
	 * @return the shield image_add
	 */
	public Image getShieldImageA() {
		return shieldImageA;
	}

	/**
	 * @return Whether or not the player has a shield
	 */
	public boolean hasShield() {
		return shieldCount > 0;
	}
	
	/**
	 * @return the player spritesheet_norm
	 */
	public SpriteSheet getSpritesheetN() {
		return spritesheetN;
	}
	
	/**
	 * @return the player spritesheet_add
	 */
	public SpriteSheet getSpritesheetA() {
		return spritesheetA;
	}
	
	/**
	 * @param spritesheetN the spritesheet_norm to set.
	 * @param spritesheetA the spritesheet_add to set.
	 */
	public void setSpritesheet(SpriteSheet spritesheetN, SpriteSheet spritesheetA) {
		this.spritesheetN = spritesheetN;
		this.spritesheetA = spritesheetA;
	}
	
	/**
	 * @return movement counter for spritesheets
	 */
	public int getMovementCounter() {
		return movementCounter;
	}
	
	/**
	 * increment the movement counter used for spritesheets.
	 */
	public void incrementMovementCounter() {
		movementCounter++;
		if (movementCounter > movementCounterMax) {
			resetMovementCounter();
		}
	}
	
	/**
	 * reset the movement counter used for spritesheets.
	 */
	public void resetMovementCounter() {
		movementCounter = 0;
	}
	
	/**
	 * @return movementCounter_max get the movement counter maximum used for spritesheets
	 */
	public int getMovementCounter_Max() {
		return movementCounterMax;
	}
	
	/**
	 * @param newMax set the movement counter maximum used for spritesheets
	 */
	public void setMovementCounter_Max(int newMax) {
		movementCounterMax = newMax;
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
	 * @return the shootKey
	 */
	public int getShootKey() {
		return shootKey;
	}

	/**
	 * @param shootKey the shootKey to set
	 */
	public void setShootKey(int shootKey) {
		this.shootKey = shootKey;
	}

	/**
	 * Respawn player (i.e. reset powerups)
	 */
	public void respawn() {
		weapons = new LinkedList<>();
		shieldCount = 0;
		shieldTimeRemaining = 0;
		this.x = startX;
		this.y = startY;
	}

	/**
	 * @return the shot
	 */
	public boolean isShot() {
		return weaponHelper.isShot();
	}

	/**
	 * @param shot the shot to set
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
	}

	/**
	 * @return shield time remaing (MS)
	 */
	public float shieldTimeRemaining() {
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
	 * Name for player.
	 * @param name to set.
	 */
	public void setPlayerName(String name) {
		this.playerName = name;
	}
	
	/**
	 * @return Name of player.
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
	
	/**
	 * @param movement the movement integer used to determine movement state. 
	 */
	public void setMovement(Movement movement) {
		movementHelper.setMovement(movement);
	}
	
	/**
	 * @return the current movement used to determine movement
	 */
	public Movement getMovement() {
		return movementHelper.getMovement();
	}
	

	/**
	 * @param movement the movement integer used to determine movement state. 
	 */
	public void setMovingLeft(boolean movement) {
		movementHelper.setMovingLeft(movement);
	}
	
	/**
	 * @param movement the movement integer used to determine movement state. 
	 */
	public void setMovingRight(boolean movement) {
		movementHelper.setMovingRight(movement);
	}
	
	/**
	 * Add a shield for this player.
	 */
	public void addShield() {
        shieldCount += 1;
        shieldTimeRemaining = TimeUnit.SECONDS.toMillis(POWERUP_DURATION);
        Executors.newScheduledThreadPool(1).schedule(() -> {
                    if (shieldCount > 0) {
                        shieldCount -= 1;
                    }
                },
                POWERUP_DURATION, TimeUnit.SECONDS);
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
	 * @return the weapons
	 */
	public LinkedList<Powerup.PowerupType> getWeapons() {
		return weapons;
	}

	/**
	 * Update the info in the movement helper.
	 */
	public void updateMovementHelperInfo() {
		movementHelper.setX(x);
		movementHelper.setY(y);
		movementHelper.setPlayerNumber(playerNumber);
		movementHelper.setMaxX(this.getMaxX());
		movementHelper.setCenterX(this.getCenterX());
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
}
