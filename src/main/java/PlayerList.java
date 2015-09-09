import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;


public class PlayerList {
	
	private ArrayList<Player> playerList;
	private MainGame mg;
	private GameState gs;
	
	private static final int SPRITE_SHEET_THREE = 3;
	private static final int SPRITE_SHEET_FOUR = 4;
	private static final float MOVEMENT_COUNTER_FACTOR = 0.5f;
	private static final int PLAYER_DRAW_X_DEVIATION = 30;
	private static final int PLAYER_DRAW_Y_DEVIATION = 23;
	
	/**
	 * 
	 */
	public PlayerList(Player player1, MainGame mg, GameState gs) {
		super();
		playerList = new ArrayList<Player>();
		playerList.add(player1);
		this.mg = mg;
		this.gs = gs;
	}
	
	public void updatePlayers(float deltaFloat){
		playerList.get(0).update(deltaFloat);
		if(mg.isMultiplayer()) {
			playerList.get(1).update(deltaFloat);	
		}
	}
	
	public void add(Player player) {
		if(playerList.size() < 2) {
			playerList.add(player);
		}
	}
	
	public void intersectPlayersWithCircle(BouncingCircle circle) {
		if (playerList.get(0).getRectangle().intersects(circle) && !playerList.get(0).hasShield()) {
			//LIVES FUNCTIONALITY
			playerDeath(mg);
		}
		
		if (mg.isMultiplayer() && playerList.get(1).getRectangle().intersects(circle)&& !playerList.get(1).hasShield()) {
			//LIVES FUNCTIONALITY
			playerDeath(mg);
		}
	}
	
	public void drawPlayers(GameContainer container, Graphics graphics) {
		drawPlayer(playerList.get(0), container, graphics);
		if(mg.isMultiplayer()) {
			drawPlayer(playerList.get(1), container, graphics);
		}
	}
	
	private void drawPlayer(Player player, GameContainer container, Graphics graphics) {
		if (player.getMovement() == 2) {
			player.incrementMovementCounter();
			int sp = SPRITE_SHEET_THREE;
			if (player.getMovementCounter() > player.getMovementCounter_Max() 
					* MOVEMENT_COUNTER_FACTOR) {
				sp = SPRITE_SHEET_FOUR;
			}
			graphics.drawImage(player.getSpritesheet().getSprite(sp, 0), player.getX() 
					- PLAYER_DRAW_X_DEVIATION, player.getY() - PLAYER_DRAW_Y_DEVIATION);
		} else if (player.getMovement() == 1) {
			player.incrementMovementCounter();
			int sp = 1;
			if (player.getMovementCounter() > player.getMovementCounter_Max()
					* MOVEMENT_COUNTER_FACTOR) {
				sp = 0;
			}
			graphics.drawImage(player.getSpritesheet().getSprite(sp, 0), player.getX()
					- PLAYER_DRAW_X_DEVIATION, player.getY() - PLAYER_DRAW_Y_DEVIATION);
		} else {
			player.resetMovementCounter();
			graphics.drawImage(player.getSpritesheet().getSprite(2, 0), player.getX()
					- PLAYER_DRAW_X_DEVIATION, player.getY() - PLAYER_DRAW_Y_DEVIATION);
		}
		player.setMovement(0);
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
			sbg.enterState(mg.getGameOverState());
		} else {
			sbg.enterState(mg.getGameState());
		}
	}
	

	
	/**
	 * @return the playerList
	 */
	public ArrayList<Player> getPlayerList() {
		return playerList;
	}
	

	/**
	 * @param playerList the playerList to set
	 */
	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

}
