package logic;
import gui.GameState;
import gui.MainGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import logic.Logger.PriorityLevels;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;

/**
 * This class represents a Player.
 * @author Menno
 *
 */
public class Player {

	/**
	 * Represents a Player movement.
	 */
	public enum Movement {
		NO_MOVEMENT, LEFT, RIGHT
	}

	private int shieldCount;
	private float x;
	private float y;
	private float width;
	private float height;
	private Movement movement = Movement.NO_MOVEMENT;
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
	private Logger logger;
	private GameState gameState;
	private Gate intersectingGate;
	private boolean stoodStillOnLastUpdate = false;
	// weapon management
	private LinkedList<Powerup.PowerupType> weapons;
	private boolean shot;
	private int playerNumber;
	private final float startX;
	private final float startY;
	private String lastLogMove = "";
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
    private boolean movingRight;
    private boolean movingLeft;
	
	private static final int POWERUP_DURATION = 10;
	private long shieldTimeRemaining;

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
		this.logger = mainGame.getLogger();
		this.gameState = (GameState) mainGame.getState(mainGame.getGameState());
		moveLeftKey = Input.KEY_LEFT;
		moveRightKey = Input.KEY_RIGHT;
		shootKey = Input.KEY_SPACE;
		this.weapons = new LinkedList<>();
		this.shieldCount = 0;
		this.shieldTimeRemaining = 0;
		this.shot = false;
		this.movingLeft = false;
		this.movingRight = false;
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
		if (!gameState.isPaused() && shieldTimeRemaining > 0) {
			shieldTimeRemaining -= deltaFloat * SECONDS_TO_MS;
		}
		//System.out.println(deltaFloat);
		processGates();
		processWeapon(deltaFloat, containerHeight, testing);
		processPlayerMovement(deltaFloat, containerWidth, testing);
		processPowerups(deltaFloat, containerHeight, containerWidth);
		processCoins(deltaFloat, containerHeight);
		
	}
	
	/**
	 * Process the intersection with gates.
	 */
	private void processGates() {
		// Check the intersection of a player with a gate
		freeToRoam = true;
		for (Gate someGate :gameState.getGateList()) {
			if (this.getRectangle().intersects(someGate.getRectangle())) {
				freeToRoam = false;
				intersectingGate = someGate;
			}
		}
		// Reset intersecting gate to none if there is no intersection
		if (freeToRoam) {
			intersectingGate = null;
		}
	}
	 
	/**
	 * Process the intersection of a player with the powerups.
	 * @param deltaFloat the time in seconds since the last frame.
	 * @param containerHeight the height of the current GameContainer
	 * @param containerWidth the width of the current GameContainer
	 */
	private void processPowerups(float deltaFloat, float containerHeight,
			float containerWidth) { // MARKED
		ArrayList<Powerup> usedPowerups = new ArrayList<>();
		for (Powerup powerup : gameState.getDroppedPowerups()) {
			powerup.update(gameState, containerHeight, deltaFloat);

			if (powerup.getRectangle().intersects(this.getRectangle())) {
				this.addPowerup(powerup.getType());
				gameState.getFloatingScores().add(new FloatingScore(powerup));
				usedPowerups.add(powerup);
			}
		}

		gameState.getDroppedPowerups().removeAll(usedPowerups);
		gameState.getDroppedPowerups().removeIf(Powerup::removePowerup);
	}

	/**
	 * Process the intersection of a player with the coins.
	 * @param deltaFloat the time in seconds since the last frame.
	 * @param containerHeight the height of the current GameContainer.
	 */
	private void processCoins(float deltaFloat, float containerHeight) {
		ArrayList<Coin> usedCoins = new ArrayList<>();
		for (Coin coin : gameState.getDroppedCoins()) {

			if (coin.getRectangle().intersects(this.getRectangle())) {
				gameState.addToScore(coin.getPoints());
				gameState.getFloatingScores().add(new FloatingScore(coin));
				usedCoins.add(coin);
				mainGame.getLogger().log("Picked up coin", 
						Logger.PriorityLevels.MEDIUM, "powerups");
			}
		}

		gameState.getDroppedCoins().removeAll(usedCoins);
	}

	/**
	 * Process the weapon of this player.
	 * @param deltaFloat the time in seconds since the last frame.
	 * @param containerHeight the height of the current GameContainer
	 * @param testing to check if we are testing or not.
	 */
	private void processWeapon(float deltaFloat, float containerHeight, boolean testing) {
		// Shoot laser when spacebar is pressed and no laser is active
		if (!testing && gameState.getSavedInput().isKeyPressed(shootKey)
				&& !shot) {
			
			shot = true;
			gameState.getWeaponList().setWeapon(playerNumber, this.getWeapon(containerHeight));
			
			Weapon weapon = gameState.getWeaponList().getWeaponList().get(0);
			if (mainGame.isHost()) {
				mainGame.getHost().updateLaser(weapon.getX(), 
						weapon.getY(), weapon.getLaserSpeed(), weapon.getWidth());
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
	 * Process the movement of this player.
	 * @param deltaFloat the time in seconds since the last frame.
	 * @param containerWidth the width of the current GameContainer
	 * @param testing to check if we are testing or not
	 */
	private void processPlayerMovement(float deltaFloat, float containerWidth, boolean testing) {
		if (testing) {
			return;
		}
		boolean didWalk = false;
		// Walk left when left key pressed and not at left wall OR a gate
		didWalk = processMoveLeft(deltaFloat, didWalk);
		// Walk right when right key pressed and not at right wall OR a gate
		didWalk = processMoveRight(deltaFloat, containerWidth, didWalk);

		if (!didWalk && !stoodStillOnLastUpdate) {
			stoodStillOnLastUpdate = true;
			logger.log("Moved to position " + this.getCenterX(), PriorityLevels.LOW, "Player");
		
			if (mainGame.isLanMultiplayer() && mainGame.isHost()) {
				mainGame.getHost().playerStoppedMoving(x, y, playerNumber);
			}
		}
		
	}

	/**
	 * Process a movement to the right.
	 * @param deltaFloat the time in seconds since the last frame
	 * @param containerWidth the width of the current GameContainer
	 * @param didWalk if the player did actually walk
	 * @return a boolean to check if the player walked.
	 */
	private boolean processMoveRight(float deltaFloat, float containerWidth, boolean didWalk) {

		boolean isMovingRight = false;
		
		if (mainGame.isLanMultiplayer() && !mainGame.isHost() && playerNumber == 0 && movingRight) {
			isMovingRight = true;
		}
		
		if ((gameState.getSavedInput().isKeyDown(moveRightKey) && this.getMaxX()
				< (containerWidth - gameState.getRightWall().getWidth()))
				|| isMovingRight) {
           if (freeToRoam || (this.getCenterX() > intersectingGate.getRectangle().getCenterX())) {
        	   this.setX(this.getX() + mainGame.getPlayerSpeed() * deltaFloat);
        	   this.movement = Movement.RIGHT;
        	   didWalk = true;
        	   stoodStillOnLastUpdate = false;
        	   if (!lastLogMove.equals("right")) {
        		   logger.log("Moving right from position " + this.getCenterX(),
        				   PriorityLevels.VERYLOW, "Player");
        		   lastLogMove = "right";

        		   if (mainGame.isLanMultiplayer() && mainGame.isHost()) {
        			   mainGame.getHost().playerStartedMoving(x, y, playerNumber, "RIGHT");
        		   }   
        	   }
           }
        }
		return didWalk;
	}

	/**
	 * Process a movement to the left.
	 * @param deltaFloat the time in seconds since the last frame
	 * @param didWalk if the player did actually walk
	 * @return a boolean to check if the player walked.
	 */
	private boolean processMoveLeft(float deltaFloat, boolean didWalk) {
		
		boolean isMovingLeft = false;
		
		if (mainGame.isLanMultiplayer() && !mainGame.isHost() && playerNumber == 0 && movingLeft) {
			isMovingLeft = true;
		}
		
		boolean isKeyLeft = gameState.getSavedInput().isKeyDown(moveLeftKey);
		if ((isKeyLeft && this.getX() > gameState.getLeftWall().getWidth()) || isMovingLeft) {
            if (freeToRoam || (this.getCenterX() < intersectingGate.getRectangle().getCenterX())) {
            	this.setX(this.getX() - mainGame.getPlayerSpeed() * deltaFloat);
            	this.movement = Movement.LEFT;
            	didWalk = true;
            	stoodStillOnLastUpdate = false;
            	if (!lastLogMove.equals("left")) {
            		logger.log("Moving left from position " + this.getCenterX(),
            				PriorityLevels.VERYLOW, "Player");
            		lastLogMove = "left";
            		
            		if (mainGame.isLanMultiplayer() && mainGame.isHost()) {
            			mainGame.getHost().playerStartedMoving(x, y, playerNumber, "LEFT");
            		}
            	}
            }
        }
		return didWalk;
	}

	/**
	 * Get the weapon of this player.
	 * @param containerHeight the height of the current GameContainer.
	 * @return the Weapon of this player.
	 */
	private Weapon getWeapon(float containerHeight) {
		if (weapons.isEmpty()) {
			logger.log("Shot regular laser from position " + this.getCenterX(), 
					PriorityLevels.MEDIUM, "Player");
			return new Weapon(this.getCenterX(), containerHeight - gameState.getFloor().getHeight(),
					mainGame.getLaserSpeed(), mainGame.getLaserWidth());
		}
		Powerup.PowerupType subType = weapons.peekLast();
		if (subType == Powerup.PowerupType.SPIKY) {
			logger.log("Shot spiky laser from position " + this.getCenterX(), 
					PriorityLevels.HIGH, "Player");
			return new Spiky(this.getCenterX(), containerHeight - gameState.getFloor().getHeight(),
					mainGame.getLaserSpeed(), mainGame.getLaserWidth());
		}
		if (subType == Powerup.PowerupType.INSTANT) {
			logger.log("Shot instant laser from position " + this.getCenterX(), 
					PriorityLevels.HIGH, "Player");
			return new InstantLaser(this.getCenterX(),
					containerHeight - gameState.getFloor().getHeight(), mainGame.getLaserWidth());
		}
		// Wrong weapon type, time to crash hard.
		throw new EnumConstantNotPresentException(Powerup.PowerupType.class, subType.toString());
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
	 * Add a powerup to the player.
	 * @param type powerup type
	 */
	public void addPowerup(Powerup.PowerupType type) {
		if (type == Powerup.PowerupType.INSTANT) {
			addWeapon(type);
			mainGame.getLogger().log("Added powerup instant", 
					Logger.PriorityLevels.MEDIUM, "powerups");
		}
		if (type == Powerup.PowerupType.SHIELD) {
			addShield();
			mainGame.getLogger().log("Added powerup shield", 
					Logger.PriorityLevels.MEDIUM, "powerups");
		}
		if (type == Powerup.PowerupType.SPIKY) {
			addWeapon(type);
			mainGame.getLogger().log("Added powerup spiky", 
					Logger.PriorityLevels.MEDIUM, "powerups");
		}
	}

	/**
	 * Add a shield for this player.
	 */
	private void addShield() {
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
	private void addWeapon(Powerup.PowerupType type) {
		weapons.add(type);
		Executors.newScheduledThreadPool(1).schedule(() -> {
					if (!weapons.isEmpty()) {
						weapons.removeFirst();
					}
				},
				POWERUP_DURATION, TimeUnit.SECONDS);
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
	 * @param movement the movement integer used to determine movement state. 
	 */
	public void setMovement(Movement movement) {
		this.movement = movement;
	}
	
	/**
	 * @return the current movement used to determine movement
	 */
	public Movement getMovement() {
		return movement;
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
		return shot;
	}

	/**
	 * @param shot the shot to set
	 */
	public void setShot(boolean shot) {
		this.shot = shot;
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
	 * @return the movingRight
	 */
	public boolean isMovingRight() {
		return movingRight;
	}

	/**
	 * @param movingRight the movingRight to set
	 */
	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	/**
	 * @return the movingLeft
	 */
	public boolean isMovingLeft() {
		return movingLeft;
	}

	/**
	 * @param movingLeft the movingLeft to set
	 */
	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
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
	
}
