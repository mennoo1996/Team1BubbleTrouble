package guigame;

import guimenu.MainGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import logic.Player;
import logic.Weapon;
import logic.WeaponList;

/**
 * GameState Helper class for managing all players. This is done to prevent
 * GameState from having too much responsibility and/or knowledge. The class
 * should only be used in conjunction with GameState.
 * @author Mark
 */
public class GameStatePlayerHelper extends GameStateHelper {

	private MainGame mainGame;
	private GameState parentState;
	private WeaponList weaponList;
	
	/**
	 * Constructor for GameStatePlayerHelper object.
	 * @param app the Main Game this class draws data from.
	 * @param state the GameState parent.
	 */
	public GameStatePlayerHelper(MainGame app, GameState state) {
		this.mainGame = app;
		this.parentState = state;
		Weapon weapon1 = null;
		Weapon weapon2 = null;
		weaponList = new WeaponList(weapon1, mainGame, parentState, false);
		weaponList.add(weapon2);
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
	public void update(GameContainer container, StateBasedGame sbg, float deltaFloat) {
		mainGame.getPlayerList().updatePlayers(deltaFloat,
				container.getHeight(),
				container.getWidth());
	}

	@Override
	public void render(Graphics graphics, GameContainer container) {
		weaponList.drawWeapons(graphics);
		mainGame.getPlayerList().drawPlayers(graphics);
	}
	
	/**
	 * @return the weaponList holding all player weapons.
	 */
	public WeaponList getWeaponList() {
		return weaponList;
	}
	
}
