package gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import logic.Player;

/**
 * GameState Helper class for managing all players. This is done to prevent
 * GameState from having too much responsibility and/or knowledge. The class
 * should only be used in conjunction with GameState.
 * @author Mark
 */
public class GameStatePlayerHelper extends GameStateHelper {

	private MainGame mainGame;
	
	/**
	 * Constructor for GameStatePlayerHelper object.
	 * @param app the Main Game this object is fetching data from.
	 */
	public GameStatePlayerHelper(MainGame app) {
		this.mainGame = app;
	}
	
	@Override
	public void enter() {
		mainGame.getPlayerList().setAllPlayersShot(false);
		mainGame.getPlayerList().getPlayers().forEach(Player::respawn);
	}
	
	@Override
	public void exit() {
		mainGame.getPlayerList().getPlayers().forEach(Player::respawn);
		mainGame.getPlayerList().setProcessCollisions(true);
	}
	
	@Override
	public void update(GameContainer container, float deltaFloat) {
		mainGame.getPlayerList().updatePlayers(deltaFloat,
				container.getHeight(),
				container.getWidth());
	}

	@Override
	public void render(Graphics graphics, GameContainer container) {
		mainGame.getPlayerList().drawPlayers(graphics);
	}
	
}
