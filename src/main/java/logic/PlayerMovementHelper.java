package logic;

import gui.GameState;
import gui.MainGame;
import lan.Client;
import lan.Host;
import logic.Logger.PriorityLevels;


/**
 * Class which helps player with processing movement.
 * @author Bart
 *
 */
public class PlayerMovementHelper {
	private Gate intersectingGate;
	private boolean stoodStillOnLastUpdate;
	private String lastLogMove;
	private Movement movement;
	private Player player;
	private GameState gameState;
    private boolean movingRight;
    private boolean movingLeft;
	private static final String PLAYER = "Player";
	
	private float x, y, centerX, maxX;
	private int playerNumber, moveLeftKey, moveRightKey;
	
	private boolean isHost, isClient, isLanMultiplayer;
	private float playerSpeed, leftWallWidth, rightWallWidth;
	private Host host;
	private Client client;
	
	/**
	 * Constructor.
	 * @param player	- the player this movementhelper helps.
	 * @param mainGame	- the game the player plays in.
	 * @param gameState	- the gameState the player plays in.
	 */
	public PlayerMovementHelper(Player player, GameState gameState) {
		stoodStillOnLastUpdate = false;
		lastLogMove = "";
		intersectingGate = null;
		movement = Movement.NO_MOVEMENT;
		this.player = player;
		this.gameState = gameState;		
		this.movingLeft = false;
		this.movingRight = false;
	}
	
	/**
	 * Represents a Player movement.
	 */
	public enum Movement {
		NO_MOVEMENT, LEFT, RIGHT
	}

	/**
	 * Process the movement of this player.
	 * @param deltaFloat the time in seconds since the last frame.
	 * @param containerWidth the width of the current GameContainer
	 * @param testing to check if we are testing or not
	 */
	public void processPlayerMovement(float deltaFloat, float containerWidth, boolean testing) {
		player.updateMovementHelperInfo();
		if (testing) {
			return;
		}
		boolean didWalk = processPlayerMovementMoving(false, deltaFloat, containerWidth);
		
		processPlayerMovementStandingStill(didWalk);
	}
	
	private boolean processPlayerMovementMoving(boolean didWalk, 
			float deltaFloat, float containerWidth) {
		if (movement != Movement.RIGHT) {
			didWalk = processMoveLeft(deltaFloat, didWalk);
		}
		if (movement != Movement.LEFT) {
			didWalk = processMoveRight(deltaFloat, containerWidth, didWalk);
		}
		
		return didWalk;
	}
	
	/**
	 * .
	 */
	private void processPlayerMovementStandingStill(boolean didWalk) {
		// didnt walk, stating still.
		if (!didWalk && !stoodStillOnLastUpdate) {
			stoodStillOnLastUpdate = true;
			Logger.getInstance().log("Moved to position " + centerX, 
					PriorityLevels.LOW, PLAYER);

			processPlayerMovementStandingStillMultiplayer();

		}
	}
	
	/**
	 * .
	 */
	private void processPlayerMovementStandingStillMultiplayer() {
		if (isLanMultiplayer) {
			if (isHost && playerNumber == 0) {
				host.playerStoppedMoving(x, y, playerNumber);
			} 
			processPlayerMovementStandingStillMultiplayer2();
		}
	}
	
	/**
	 * .
	 */
	private void processPlayerMovementStandingStillMultiplayer2() {
		if (isClient && playerNumber == 1) {
			client.playerStoppedMoving(x, y, playerNumber);
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
		if (processRightNeeded(containerWidth)) {
			setPlayerX(x + playerSpeed * deltaFloat);
			this.movement = Movement.RIGHT;
			didWalk = true;
			
			processMoveRightMultiplayer();
			
			if (!lastLogMove.equals("right")) {
				Logger.getInstance().log("Moving right from position " + centerX,
						PriorityLevels.VERYLOW, PLAYER);
				lastLogMove = "right";
			}
			stoodStillOnLastUpdate = false;
		} return didWalk;
	}
	
	/**
	 * .
	 */
	private void processMoveRightMultiplayer() {
		if (isLanMultiplayer && stoodStillOnLastUpdate && isHost && playerNumber == 0) {
			host.playerStartedMoving(x, y, playerNumber, "RIGHT");
		} else if (isLanMultiplayer && stoodStillOnLastUpdate && isClient 
				&& playerNumber == 1) {
			client.playerStartedMoving(x, y, playerNumber, "RIGHT");
		}
	}
	
	/**
	 * Determines if it is needed to process right movement.
	 * @param containerWidth the width of the container
	 * @return	true or false
	 */
	private boolean processRightNeeded(float containerWidth) {
		boolean isMovingRight = false;
		if (isLanMultiplayer && player.isOthersPlayer() && movingRight) {
			isMovingRight = true;
		}
		return (processRightNeeded2(containerWidth, isMovingRight) && (player.isFreeToRoam()
						| (centerX > intersectingGate.getRectangle().getCenterX())));
	}
	
	/**
	 * Determines if it is needed to process right movement.
	 * @param containerWidth the width of the container
	 * @return	true or false
	 */
	private boolean processRightNeeded2(float containerWidth, boolean isMovingRight) {
		return (gameState.getSavedInput().isKeyDown(moveRightKey) 
				&& !gameState.getSavedInput().isKeyDown(moveLeftKey)
				&& maxX < (containerWidth - rightWallWidth))
				|| isMovingRight;
	}

	/**
	 * Process a movement to the left.
	 * @param deltaFloat the time in seconds since the last frame
	 * @param didWalk if the player did actually walk
	 * @return a boolean to check if the player walked.
	 */
	private boolean processMoveLeft(float deltaFloat, boolean didWalk) {
		if (processLeftNeeded()) {
			setPlayerX(x - playerSpeed * deltaFloat);
			this.movement = Movement.LEFT;
			didWalk = true;
			
			processMoveLeftMultiplayer();
			
			if (!lastLogMove.equals("left")) {
				Logger.getInstance().log("Moving left from position " + player.getCenterX(),
						PriorityLevels.VERYLOW, PLAYER);
				lastLogMove = "left";
			}
			stoodStillOnLastUpdate = false;
		} return didWalk;
	}
	
	/**
	 * .
	 */
	private void processMoveLeftMultiplayer() {
		if (isLanMultiplayer && stoodStillOnLastUpdate) {
			if (isHost && playerNumber == 0) {
				host.playerStartedMoving(x, y, playerNumber, "LEFT");
			} 
			processMoveLeftMultiplayer2();
		}
	}
	
	/**
	 * .
	 */
	private void processMoveLeftMultiplayer2() {
		if (isClient && playerNumber == 1) {
			client.playerStartedMoving(x, y, playerNumber, "LEFT");
		}

	}
	
	
	/**
	 * Determines if it is needed to process left movement.
	 * @return	true or false
	 */
	private boolean processLeftNeeded() {
		boolean isMovingLeft = false;
		if (isLanMultiplayer && player.isOthersPlayer() && movingLeft) {
			isMovingLeft = true;
		}
		return (processLeftNeeded2() || isMovingLeft) && (player.isFreeToRoam() 
				|| (centerX < intersectingGate.getRectangle().getCenterX()));
	}
	
	/**
	 * Determines if it is needed to process left movement.
	 * @return	true or false
	 */
	private boolean processLeftNeeded2() {
		return (gameState.getSavedInput().isKeyDown(moveLeftKey) 
				&& !gameState.getSavedInput().isKeyDown(moveRightKey)
				&& x > leftWallWidth && !movingRight);
	}

	/**
	 * @param intersectingGate the intersectingGate to set
	 */
	public void setIntersectingGate(Gate intersectingGate) {
		this.intersectingGate = intersectingGate;
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
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}
	/**
	 * @param playerNumber the playerNumber to set
	 */
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	/**
	 * @param centerX the centerX to set
	 */
	public void setCenterX(float centerX) {
		this.centerX = centerX;
	}


	/**
	 * @param maxX the maxX to set
	 */
	public void setMaxX(float maxX) {
		this.maxX = maxX;
	}
	/**
	 * @param moveLeftKey the moveLeftKey to set
	 */
	public void setMoveLeftKey(int moveLeftKey) {
		this.moveLeftKey = moveLeftKey;
	}

	/**
	 * @param moveRightKey the moveRightKey to set
	 */
	public void setMoveRightKey(int moveRightKey) {
		this.moveRightKey = moveRightKey;
	}
	
	/**
	 * .
	 * @param x .
	 */
	private void setPlayerX(float x) {
		player.setX(x);
	}

	/**
	 * @param isHost the isHost to set
	 */
	public void setIsHost(boolean isHost) {
		this.isHost = isHost;
	}

	/**
	 * @param isClient the isClient to set
	 */
	public void setIsClient(boolean isClient) {
		this.isClient = isClient;
	}

	/**
	 * @param isLanMultiplayer the isLanMultiplayer to set
	 */
	public void setLanMultiplayer(boolean isLanMultiplayer) {
		this.isLanMultiplayer = isLanMultiplayer;
	}

	/**
	 * @param playerSpeed the playerSpeed to set
	 */
	public void setPlayerSpeed(float playerSpeed) {
		this.playerSpeed = playerSpeed;
	}

	/**
	 * @param leftWallWidth the leftWallWidth to set
	 */
	public void setLeftWallWidth(float leftWallWidth) {
		this.leftWallWidth = leftWallWidth;
	}

	/**
	 * @param rightWallWidth the rightWallWidth to set
	 */
	public void setRightWallWidth(float rightWallWidth) {
		this.rightWallWidth = rightWallWidth;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(Host host) {
		this.host = host;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(Client client) {
		this.client = client;
	}
	
	
	
}
