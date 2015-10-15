package logic;
import guigame.GameState;
import guimenu.MainGame;
import guimenu.RND;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A list of players.
 * @author Bart
 *
 */
public class PlayerList {
	
	private ArrayList<Player> playerList;
	private MainGame mainGame;
	private GameState gameState;
	private boolean died;
	
	private boolean processCollisions;
	
	private static final int SPRITE_SHEET_THREE = 3;
	private static final int SPRITE_SHEET_FOUR = 4;
	private static final float MOVEMENT_COUNTER_FACTOR = 0.5f;
	private static final int PLAYER_DRAW_X_DEVIATION = 30;
	private static final int PLAYER_DRAW_Y_DEVIATION = 23;
	private static final int PLAYER_NAME_X_DEVIATION = 40;
	private static final int PLAYER_NAME_Y_DEVIATION = 100;
	private static final int SHIELD_DRAW_X_DEVIATION = 43;
	
	private static final String PLAYER_IMAGES = "resources/images_Player/";
	
	private Logger logger = Logger.getInstance();
	
	/**
	 * The constructor of playerlist.
	 * @param player1	- the first player of the list
	 * @param mainGame		- the maingame
	 * @param gameState		- the gamestate
	 */
	public PlayerList(Player player1, MainGame mainGame, GameState gameState) {
		playerList = new ArrayList<Player>();
		playerList.add(player1);
		this.mainGame = mainGame;
		this.gameState = gameState;
		processCollisions = true;
		this.died = false;
	} 
	
	/**
	 * Update all players.
	 * @param deltaFloat	- time since last frame
	 * @param containerHeight - the height of the container
	 * @param containerWidth - the width of the container
	 */
	public void updatePlayers(float deltaFloat, float containerHeight, float containerWidth) {
		playerList.get(0).update(deltaFloat, containerHeight, containerWidth, false);
		if (mainGame.isMultiplayer() || mainGame.isLanMultiplayer()) {
			playerList.get(1).update(deltaFloat, containerHeight, containerWidth, false);	
		}	
	}
	
	/**
	 * Add a player to the list.
	 * @param player	- the player to add
	 */
	public void add(Player player) {
		if (playerList.size() < 2) {
			playerList.add(player);
		}
		logger.log("player added", Logger.PriorityLevels.MEDIUM, "players");
	}
	
	/**
	 * Inserct all players with a circle.
	 * @param circle	- the circle to intersect with
	 */
	public void intersectPlayersWithCircle(BouncingCircle circle) {
		if (processCollisions) {
			if (playerList.get(0).getRectangle().intersects(circle) 
					&& !playerList.get(0).hasShield()) {
				//LIVES FUNCTIONALITY
				if (!mainGame.isLanMultiplayer()) {
					playerDeath(mainGame);
				} else if (mainGame.isHost()) {
					mainGame.getHost().updateDead();
					playerDeath(mainGame);
				}
			}
			
			if ((mainGame.isMultiplayer() || mainGame.isLanMultiplayer()) 
					&& playerList.get(1).getRectangle().intersects(circle)
					&& !playerList.get(1).hasShield()) {
				//LIVES FUNCTIONALITY
				if (!mainGame.isLanMultiplayer()) {
					playerDeath(mainGame);
				} else if (mainGame.isClient()) {
					mainGame.getClient().updateDead();
					playerDeath(mainGame);
				}
			}
		}
	}
	
	/**
	 * Draw all players.
	 * @param graphics	- the graphics to draw with
	 */
	public void drawPlayers(Graphics graphics) {
		drawPlayer(playerList.get(0), graphics);
		if (mainGame.isMultiplayer() || mainGame.isLanMultiplayer()) {
			RND.getInstance().text(graphics, 
					playerList.get(0).getX() - PLAYER_NAME_X_DEVIATION,
					playerList.get(0).getCenterY() - PLAYER_NAME_Y_DEVIATION, 
					"#" + playerList.get(0).getPlayerName());
			drawPlayer(playerList.get(1), graphics);
			RND.getInstance().text(graphics, 
					playerList.get(1).getX() - PLAYER_NAME_X_DEVIATION,
					playerList.get(1).getCenterY() - PLAYER_NAME_Y_DEVIATION, 
					"#" + playerList.get(1).getPlayerName());
		}
	}
	
	/**
	 * Set shot variable of all players.
	 * @param shot	- the value to set
	 */
	public void setAllPlayersShot(boolean shot) {
		for (Player player : playerList) {
			player.setShot(shot);
		}
	}
	
	/**
	 * set images of given player.
	 * @param playerNumber given player
	 * @param imageStringN normal image
	 * @param imageStringA additive image
	 */
	public void setPlayerImage(int playerNumber, String imageStringN, String imageStringA) {
		try {
			Image imageN = new Image(PLAYER_IMAGES + imageStringN);
			Image imageA = new Image(PLAYER_IMAGES + imageStringA);
			playerList.get(playerNumber).setImage(imageN, imageA);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * set images of players.
	 * @param playerNumber number of player.
	 * @param imageStringN string of normal image
	 * @param imageStringA string of additive image
	 */
	public void setPlayerImages(int playerNumber, String imageStringN, String imageStringA) {
		try {
			Image imageN = new Image(PLAYER_IMAGES + imageStringN);
			Image imageA = new Image(PLAYER_IMAGES + imageStringA);
			playerList.get(playerNumber).setImage(imageN, imageA);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Draws player.
	 * @param player to draw
	 * @param graphics context
	 */
	private void drawPlayer(Player player, Graphics graphics) {
		if (player.getMovement() == PlayerMovementHelper.Movement.RIGHT) {
			drawPlayerMoveRight(player, graphics);
		} else if (player.getMovement() == PlayerMovementHelper.Movement.LEFT) {
			drawPlayerMoveLeft(player, graphics);
		} else {
			drawPlayerNoMovement(player, graphics);
		}
		if (player.hasShield()) {
			RND.getInstance().drawColor(new RenderOptions(graphics, player.getShieldImageN(), 
					player.getShieldImageA(),
					player.getX() - SHIELD_DRAW_X_DEVIATION,
					player.getY() - SHIELD_DRAW_X_DEVIATION, mainGame.getColor()));
		}
		player.setMovement(PlayerMovementHelper.Movement.NO_MOVEMENT);
	}

	/**
	 * Draw the player after no movement.
	 * @param player the Player to draw
	 * @param graphics the Graphics object to draw things on screen
	 */
	private void drawPlayerNoMovement(Player player, Graphics graphics) {
		player.resetMovementCounter();
		RND.getInstance().drawColor(new RenderOptions(graphics, 
				player.getSpritesheetN().getSprite(2, 0),
				player.getSpritesheetA().getSprite(2, 0),
				player.getX() - PLAYER_DRAW_X_DEVIATION,
				player.getY() - PLAYER_DRAW_Y_DEVIATION, mainGame.getColor()));
	}

	/**
	 * Draw the player after a movement to the left.
	 * @param player the Player to draw
	 * @param graphics the Graphics object to draw things on screen
	 */
	private void drawPlayerMoveLeft(Player player, Graphics graphics) {
		player.incrementMovementCounter();
		int sp = 1;
		if (player.getMovementCounter() > player.getMovementCounter_Max()
                * MOVEMENT_COUNTER_FACTOR) {
            sp = 0;
        }
		RND.getInstance().drawColor(new RenderOptions(graphics, 
				player.getSpritesheetN().getSprite(sp, 0),
				player.getSpritesheetA().getSprite(sp, 0),
				player.getX() - PLAYER_DRAW_X_DEVIATION,
				player.getY() - PLAYER_DRAW_Y_DEVIATION, mainGame.getColor()));
	}

	/**
	 * Draw the player after a movement to the right.
	 * @param player the Player to draw
	 * @param graphics the Graphics object to draw things on screen
	 */
	private void drawPlayerMoveRight(Player player, Graphics graphics) {
		player.incrementMovementCounter();
		int sp = SPRITE_SHEET_THREE;
		if (player.getMovementCounter() > player.getMovementCounter_Max()
                * MOVEMENT_COUNTER_FACTOR) {
            sp = SPRITE_SHEET_FOUR;
        }
		RND.getInstance().drawColor(new RenderOptions(graphics, 
				player.getSpritesheetN().getSprite(sp, 0), 
				player.getSpritesheetA().getSprite(sp, 0),
				player.getX() - PLAYER_DRAW_X_DEVIATION,
				player.getY() - PLAYER_DRAW_Y_DEVIATION, mainGame.getColor()));
	}

	/**
	 * Player death.
	 * @param sbg The stateBasedGame that uses this state.
	 */
	public void playerDeath(StateBasedGame sbg) {
		if (!died) {
			logger.log("Player died, reducing lives", Logger.PriorityLevels.MEDIUM,
					"players");
			died = true;
			mainGame.decreaselifeCount();
			
			if (mainGame.isHost()) {
				mainGame.getHost().updateLives(mainGame.getLifeCount());
			}
			
			if (mainGame.getLifeCount() <= 0) {
				mainGame.setScore(mainGame.getScore() + gameState.getLogicHelper().getScore());
				mainGame.setSwitchState(mainGame.getGameOverState());
				logger.log("Player lives reached 0, game over",
						Logger.PriorityLevels.HIGH, "players");
			} else {
				processCollisions = false;
				mainGame.setSwitchState(mainGame.getGameState());
			}
		}
	}
	
	/**
	 * @return the playerList
	 */
	public ArrayList<Player> getPlayers() {
		return playerList;
	}
	

	/**
	 * @param playerList the playerList to set
	 */
	public void setPlayers(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

	/**
	 * Get the maingame.
	 * @return	- the maingame
	 */
	public MainGame getMainGame() {
		return mainGame;
	}

	/**
	 * get the gamestate.
	 * @return	- the gamestate
	 */
	public GameState getGameState() {
		return gameState;
	}
	
	/**
	 * Set whether the game should process collisions between players and spheres.
	 * @param set the boolean to set.
	 */
	public void setProcessCollisions(boolean set) {
		processCollisions = set;
	}
	
	/**
	 * @return returns whether collision for players and spheres is processed.
	 */
	public boolean getProcessCollisions() {
		return processCollisions;
	}

	/**
	 * @return the died
	 */
	public boolean isDied() {
		return died;
	}

	/**
	 * @param died the died to set
	 */
	public void setDied(boolean died) {
		this.died = died;
	}

	
}
