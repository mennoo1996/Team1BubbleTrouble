package logic;
import gui.GameState;
import gui.MainGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;

/**
 * This class represents a Player.
 * @author Menno
 *
 */
public class Player {

	private int shieldCount;
	private float x;
	private float y;
	private float width;
	private float height;
	private int movement = 0;
	private int movementCounter = 0;
	private int movementCounterMax = DEFAULT_MOVEMENTCOUNTER_MAX;
	private Image imageN;
	private Image imageA;
	private Image shieldImageN;
	private Image shieldImageA;
	private SpriteSheet spritesheetN;
	private SpriteSheet spritesheetA;
	private boolean freeToRoam;
	private MainGame mg;
	private GameState gs;
	private Gate intersectingGate;
	// weapon management
	private LinkedList<Powerup.PowerupType> weapons;
	private boolean shot;
	private int playerNumber;
	private final float startX;
	private final float startY;

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
	
	private static final int POWERUP_DURATION = 10;
	private long shieldTimeRemaining;

	/**
	 * @param x the x coordinate of the player
	 * @param y the y coordinate of the player
	 * @param width the width of the player
	 * @param height the height of the player
	 * @param image the image used on the player
	 * @param shieldImage the image used for the player's shield
	 * @param mg the maingame used on the player
	 */
	public Player(float x, float y, float width, float height, Image imageN, Image imageA, Image shieldImageN,
			Image shieldImageA, MainGame mg) {
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
		this.mg = mg;
		this.gs = (GameState) mg.getState(mg.getGameState());
		moveLeftKey = Input.KEY_LEFT;
		moveRightKey = Input.KEY_RIGHT;
		shootKey = Input.KEY_SPACE;
		this.weapons = new LinkedList<>();
		this.shieldCount = 0;
		this.shieldTimeRemaining = 0;
		this.shot = false;
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
		if (!gs.isPaused() && shieldTimeRemaining > 0) {
			shieldTimeRemaining -= deltaFloat * SECONDS_TO_MS;
		}
		System.out.println(deltaFloat);
		processGates();
		processWeapon(deltaFloat, containerHeight, testing);
		processPlayerMovement(deltaFloat, containerWidth, testing);
		processPowerups(deltaFloat, containerHeight, containerWidth);
		processCoins(deltaFloat, containerHeight);
	}
	
	private void processGates() {
		// Check the intersection of a player with a gate
		freeToRoam = true;
		for (Gate someGate :gs.getGateList()) {
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
	
	private void processPowerups(float deltaFloat, float containerHeight,
			float containerWidth) { // MARKED
		ArrayList<Powerup> usedPowerups = new ArrayList<>();
		for (Powerup powerup : gs.getDroppedPowerups()) {
			powerup.update(gs, containerHeight, deltaFloat);

			if (powerup.getRectangle().intersects(this.getRectangle())) {
				this.addPowerup(powerup.getType());
				gs.getFloatingScores().add(new FloatingScore(powerup));
				usedPowerups.add(powerup);
			}
		}

		gs.getDroppedPowerups().removeAll(usedPowerups);
		gs.getDroppedPowerups().removeIf(Powerup::removePowerup);
	}

	private void processCoins(float deltaFloat, float containerHeight) {
		ArrayList<Coin> usedCoins = new ArrayList<>();
		for (Coin coin : gs.getDroppedCoins()) {
			coin.update(gs.getFloor(), containerHeight, deltaFloat);

			if (coin.getRectangle().intersects(this.getRectangle())) {
				gs.addToScore(coin.getPoints());
				gs.getFloatingScores().add(new FloatingScore(coin));
				usedCoins.add(coin);
			}
		}

		gs.getDroppedCoins().removeAll(usedCoins);
	}

	private void processWeapon(float deltaFloat, float containerHeight, boolean testing) {
		// Shoot laser when spacebar is pressed and no laser is active
		if (!testing && gs.getSavedInput().isKeyPressed(shootKey)
				&& !shot) {
			shot = true;
			gs.getWeaponList().setWeapon(playerNumber, this.getWeapon(containerHeight));
		}
		
		Weapon weapon = gs.getWeaponList().getWeaponList().get(playerNumber);

		// Update laser
		if (shot) {
			weapon.update(gs.getCeiling(), gs.getFloor(), deltaFloat);
			// Disable laser when it has reached the ceiling
			if (!weapon.isVisible()) {
				shot = false;
			}
		}
	}
	
	private void processPlayerMovement(float deltaFloat, float containerWidth, boolean testing) {
		// Walk left when left key pressed and not at left wall OR a gate
		if (testing) {
			return;
		}
		boolean isKeyLeft = gs.getSavedInput().isKeyDown(moveLeftKey);
		if (isKeyLeft && this.getX() > gs.getLeftWall().getWidth()) {
            if (freeToRoam || (this.getCenterX() < intersectingGate.getRectangle().getCenterX())) {
            	this.setX(this.getX() - mg.getPlayerSpeed() * deltaFloat);
            	this.movement = 1;
            }
        }

		// Walk right when right key pressed and not at right wall OR a gate
		if (gs.getSavedInput().isKeyDown(moveRightKey) && this.getMaxX()
				< (containerWidth - gs.getRightWall().getWidth())) {
           if (freeToRoam || (this.getCenterX() > intersectingGate.getRectangle().getCenterX())) {
        	   this.setX(this.getX() + mg.getPlayerSpeed() * deltaFloat);
        	   this.movement = 2;
           }
        }
	}

	private Weapon getWeapon(float containerHeight) {
		if (weapons.isEmpty()) {
			return new Weapon(this.getCenterX(), containerHeight - gs.getFloor().getHeight(),
					mg.getLaserSpeed(), mg.getLaserWidth());
		}
		Powerup.PowerupType subType = weapons.peekLast();
		if (subType == Powerup.PowerupType.SPIKY) {
			return new Spiky(this.getCenterX(), containerHeight - gs.getFloor().getHeight(),
					mg.getLaserSpeed(), mg.getLaserWidth());
		}
		if (subType == Powerup.PowerupType.INSTANT) {
			return new InstantLaser(this.getCenterX(),
					containerHeight - gs.getFloor().getHeight(), mg.getLaserWidth());
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
	 * @param image the image to set
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
		}
		if (type == Powerup.PowerupType.SHIELD) {
			addShield();
		}
		if (type == Powerup.PowerupType.SPIKY) {
			addWeapon(type);
		}
	}

	private void addShield() {
		shieldCount += 1;
		shieldTimeRemaining = TimeUnit.SECONDS.toMillis(POWERUP_DURATION);
		Executors.newScheduledThreadPool(1).schedule(() -> shieldCount -= 1,
				POWERUP_DURATION, TimeUnit.SECONDS);
	}

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
	public void setMovement(int movement) {
		this.movement = movement;
	}
	
	/**
	 * @return the current movement integer used to determine movement 
	 */
	public int getMovement() {
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
	 * @return the gs
	 */
	public GameState getGs() {
		return gs;
	}

	/**
	 * @param gs the gs to set
	 */
	public void setGs(GameState gs) {
		this.gs = gs;
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
	
}
