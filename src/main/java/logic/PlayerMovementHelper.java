package logic;

import gui.GameState;
import gui.MainGame;
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
	private MainGame mainGame;	
	private GameState gameState;
    private boolean movingRight;
    private boolean movingLeft;
	private static final String PLAYER = "Player";
	
	/**
	 * Constructor.
	 * @param player	- the player this movementhelper helps.
	 * @param mainGame	- the game the player plays in.
	 */
	public PlayerMovementHelper(Player player, MainGame mainGame) {
		stoodStillOnLastUpdate = false;
		lastLogMove = "";
		intersectingGate = null;
		movement = Movement.NO_MOVEMENT;
		this.player = player;
		this.mainGame = mainGame;
		this.gameState = player.getgameState();		
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
		if (testing) {
			return;
		}
		boolean didWalk = false;

		if (movement != Movement.RIGHT) {
			didWalk = processMoveLeft(deltaFloat, didWalk);
		}
		if (movement != Movement.LEFT) {
			didWalk = processMoveRight(deltaFloat, containerWidth, didWalk);
		}
		
		// didnt walk, stating still.
		if (!didWalk && !stoodStillOnLastUpdate) {
			stoodStillOnLastUpdate = true;
			Logger.getInstance().log("Moved to position " + player.getCenterX(), 
					PriorityLevels.LOW, PLAYER);
			if (mainGame.isLanMultiplayer()) {
				if (mainGame.isHost() && player.getPlayerNumber() == 0) {
					mainGame.getHost().playerStoppedMoving(player.getX(), 
							player.getY(), player.getPlayerNumber());
				} else if (mainGame.isClient() && player.getPlayerNumber() == 1) {
					mainGame.getClient().playerStoppedMoving(player.getX(), 
							player.getY(), player.getPlayerNumber());
				}
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
		if (mainGame.isLanMultiplayer() && player.isOthersPlayer() && movingRight) {
			isMovingRight = true;
		}
		if (((gameState.getSavedInput().isKeyDown(player.getMoveRightKey()) 
				&& !gameState.getSavedInput().isKeyDown(player.getMoveLeftKey())
				&& player.getMaxX() < (containerWidth - gameState.getRightWall().getWidth()))
				|| isMovingRight) && (player.isFreeToRoam()
				|| (player.getCenterX() > intersectingGate.getRectangle().getCenterX()))) {
			player.setX(player.getX() + mainGame.getPlayerSpeed() * deltaFloat);
			this.movement = Movement.RIGHT;
			didWalk = true;
			if (mainGame.isLanMultiplayer() && stoodStillOnLastUpdate 
					&& mainGame.isHost() && player.getPlayerNumber() == 0) {
				mainGame.getHost().playerStartedMoving(player.getX(), 
						player.getY(), player.getPlayerNumber(), "RIGHT");
			} else if (mainGame.isLanMultiplayer() && stoodStillOnLastUpdate
					&& mainGame.isClient() && player.getPlayerNumber() == 1) {
				mainGame.getClient().playerStartedMoving(player.getX(), 
						player.getY(), player.getPlayerNumber(), "RIGHT");
			}
			if (!lastLogMove.equals("right")) {
				Logger.getInstance().log("Moving right from position " + player.getCenterX(),
						PriorityLevels.VERYLOW, PLAYER);
				lastLogMove = "right";
			}
			stoodStillOnLastUpdate = false;
		} return didWalk;
	}

	/**
	 * Process a movement to the left.
	 * @param deltaFloat the time in seconds since the last frame
	 * @param didWalk if the player did actually walk
	 * @return a boolean to check if the player walked.
	 */
	private boolean processMoveLeft(float deltaFloat, boolean didWalk) {
		boolean isMovingLeft = false;
		if (mainGame.isLanMultiplayer() && player.isOthersPlayer() && movingLeft) {
			isMovingLeft = true;
		}
		if (((gameState.getSavedInput().isKeyDown(player.getMoveLeftKey()) 
				&& !gameState.getSavedInput().isKeyDown(player.getMoveRightKey())
				&& player.getX() > gameState.getLeftWall().getWidth() && !movingRight) 
				|| isMovingLeft) && (player.isFreeToRoam() 
				|| (player.getCenterX() < intersectingGate.getRectangle().getCenterX()))) {
			player.setX(player.getX() - mainGame.getPlayerSpeed() * deltaFloat);
			this.movement = Movement.LEFT;
			didWalk = true;
			if (mainGame.isLanMultiplayer() && stoodStillOnLastUpdate) {
				if (mainGame.isHost() && player.getPlayerNumber() == 0) {
					mainGame.getHost().playerStartedMoving(player.getX(), 
							player.getY(), player.getPlayerNumber(), "LEFT");
				} else if (mainGame.isClient() && player.getPlayerNumber() == 1) {
					mainGame.getClient().playerStartedMoving(player.getX(), 
							player.getY(), player.getPlayerNumber(), "LEFT");
				}
			}
			if (!lastLogMove.equals("left")) {
				Logger.getInstance().log("Moving left from position " + player.getCenterX(),
						PriorityLevels.VERYLOW, PLAYER);
				lastLogMove = "left";
			}
			stoodStillOnLastUpdate = false;
		} return didWalk;
	}

	/**
	 * @return the intersectingGate
	 */
	public Gate getIntersectingGate() {
		return intersectingGate;
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
}
