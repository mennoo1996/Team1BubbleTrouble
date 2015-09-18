package logic;
import gui.GameState;
import gui.MainGame;
import gui.RND;

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
	private MainGame mg;
	private GameState gs;
	
	private boolean processCollisions = true;
	
	private static final int SPRITE_SHEET_THREE = 3;
	private static final int SPRITE_SHEET_FOUR = 4;
	private static final float MOVEMENT_COUNTER_FACTOR = 0.5f;
	private static final int PLAYER_DRAW_X_DEVIATION = 30;
	private static final int PLAYER_DRAW_Y_DEVIATION = 23;
	private static final int PLAYER_NAME_X_DEVIATION = 40;
	private static final int PLAYER_NAME_Y_DEVIATION = 100;
	private static final int SHIELD_DRAW_X_DEVIATION = 43;
	
	/**
	 * The constructor of playerlist.
	 * @param player1	- the first player of the list
	 * @param mg		- the maingame
	 * @param gs		- the gamestate
	 */
	public PlayerList(Player player1, MainGame mg, GameState gs) {
		super();
		playerList = new ArrayList<Player>();
		playerList.add(player1);
		this.mg = mg;
		this.gs = gs;
	}
	
	/**
	 * Update all players.
	 * @param deltaFloat	- time since last frame
	 * @param containerHeight - the height of the container
	 * @param containerWidth - the width of the container
	 */
	public void updatePlayers(float deltaFloat, float containerHeight, float containerWidth) {
		playerList.get(0).update(deltaFloat, containerHeight, containerWidth, false);
		if (mg.isMultiplayer()) {
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
				playerDeath(mg);
			}
			
			if (mg.isMultiplayer() && playerList.get(1).getRectangle().intersects(circle)
					&& !playerList.get(1).hasShield()) {
				//LIVES FUNCTIONALITY
				playerDeath(mg);
			}
		}
	}
	
	/**
	 * Draw all players.
	 * @param graphics	- the graphics to draw with
	 */
	public void drawPlayers(Graphics graphics) {
		drawPlayer(playerList.get(0), graphics);
		if (mg.isMultiplayer()) {
			RND.text(graphics, playerList.get(0).getX() - PLAYER_NAME_X_DEVIATION,
					playerList.get(0).getCenterY() - PLAYER_NAME_Y_DEVIATION, "#PLAYER_1");
			drawPlayer(playerList.get(1), graphics);
			RND.text(graphics, playerList.get(1).getX() - PLAYER_NAME_X_DEVIATION,
					playerList.get(1).getCenterY() - PLAYER_NAME_Y_DEVIATION, "#PLAYER_2");
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
			Image imageN = new Image("resources/images_Player/" + imageStringN);
			Image imageA = new Image("resources/images_Player/" + imageStringA);
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
			Image imageN = new Image("resources/images_Player/" + imageStringN);
			Image imageA = new Image("resources/images_Player/" + imageStringA);
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
		if (player.getMovement() == Player.Movement.RIGHT) {
			drawPlayerMoveRight(player, graphics);
		} else if (player.getMovement() == Player.Movement.LEFT) {
			drawPlayerMoveLeft(player, graphics);
		} else {
			drawPlayerNoMovement(player, graphics);
		}
		if (player.hasShield()) {
			RND.drawColor(graphics, player.getShieldImageN(), player.getShieldImageA(), 
					player.getX() - SHIELD_DRAW_X_DEVIATION, 
					player.getY() - SHIELD_DRAW_X_DEVIATION, mg.getColor());
		}
		player.setMovement(Player.Movement.NO_MOVEMENT);
	}

	private void drawPlayerNoMovement(Player player, Graphics graphics) {
		player.resetMovementCounter();
		RND.drawColor(graphics, player.getSpritesheetN().getSprite(2, 0),
				player.getSpritesheetA().getSprite(2, 0),
				player.getX() - PLAYER_DRAW_X_DEVIATION,
				player.getY() - PLAYER_DRAW_Y_DEVIATION, mg.getColor());
	}

	private void drawPlayerMoveLeft(Player player, Graphics graphics) {
		player.incrementMovementCounter();
		int sp = 1;
		if (player.getMovementCounter() > player.getMovementCounter_Max()
                * MOVEMENT_COUNTER_FACTOR) {
            sp = 0;
        }
		RND.drawColor(graphics, player.getSpritesheetN().getSprite(sp, 0),
				player.getSpritesheetA().getSprite(sp, 0),
				player.getX() - PLAYER_DRAW_X_DEVIATION,
				player.getY() - PLAYER_DRAW_Y_DEVIATION, mg.getColor());
	}

	private void drawPlayerMoveRight(Player player, Graphics graphics) {
		player.incrementMovementCounter();
		int sp = SPRITE_SHEET_THREE;
		//System.out.println(player.getMovementCounter_Max() * MOVEMENT_COUNTER_FACTOR);
		if (player.getMovementCounter() > player.getMovementCounter_Max()
                * MOVEMENT_COUNTER_FACTOR) {
            sp = SPRITE_SHEET_FOUR;
        }
		RND.drawColor(graphics, player.getSpritesheetN().getSprite(sp, 0),
				player.getSpritesheetA().getSprite(sp, 0),
				player.getX() - PLAYER_DRAW_X_DEVIATION,
				player.getY() - PLAYER_DRAW_Y_DEVIATION, mg.getColor());
	}

	/**
	 * Player death.
	 * @param sbg The stateBasedGame that uses this state.
	 */
	public void playerDeath(StateBasedGame sbg) {
		System.out.println("Playerdeath");
		mg.decreaselifeCount();
		if (mg.getLifeCount() <= 0) {
			mg.setScore(mg.getScore() + gs.getScore());
			mg.setSwitchState(mg.getGameOverState());
			//sbg.enterState(mg.getGameOverState());
		} else {
			//sbg.enterState(mg.getGameState());
			processCollisions = false;
			mg.setSwitchState(mg.getGameState());
			//mg.getPlayerList().
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
	public MainGame getMg() {
		return mg;
	}

	/**
	 * get the gamestate.
	 * @return	- the gamestate
	 */
	public GameState getGs() {
		return gs;
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

}
