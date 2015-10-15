package guigame;

import guimenu.MainGame;
import guimenu.RND;

import java.util.ArrayList;
import java.util.Random;

import logic.BouncingCircle;
import logic.Coin;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import powerups.Powerup;
import powerups.SpeedPowerup;

/**
 * GameState Helper class for dealing with pickups/coins etc, to help GameState carry less load.
 * @author Mark
 */
public class GameStateItemsHelper extends GameStateHelper {
	
	private MainGame mainGame;
	private GameState parentState;
	
	private ArrayList<Powerup> droppedPowerups = new ArrayList<>();
	private ArrayList<Coin> droppedCoins = new ArrayList<>();
	private ArrayList<SpeedPowerup> speedPowerupList = new ArrayList<SpeedPowerup>();
	
	private Random random;
	
	/**
	 * Constructor for creating a GameSaateItemsHelper object.
	 * @param app the mainGame we are in.
	 * @param state the parent GameState
	 */
	public GameStateItemsHelper(MainGame app, GameState state) {
		mainGame = app;
		parentState = state;
		try {
			Coin.loadImages();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void enter() {
		droppedPowerups = new ArrayList<>();
		droppedCoins = new ArrayList<>();
		random = new Random();
	}

	@Override
	public void exit() {
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, float deltaFloat) {
		processSpeedPowerups(deltaFloat);
		processCoins(container, deltaFloat);
	}

	/**
	 * Process all speedPowerups.
	 * @param deltaFloat time since last update
	 */
	private void processSpeedPowerups(float deltaFloat) {
		ArrayList<SpeedPowerup> doneList = new ArrayList<SpeedPowerup>();
		synchronized (speedPowerupList) {
			for (SpeedPowerup speedPowerup : speedPowerupList) {
				speedPowerup.update(deltaFloat, 
						((GameState) mainGame.getState(mainGame.getGameState())).
						getCirclesHelper().getCircleList());
				if (speedPowerup.isDone()) {
					doneList.add(speedPowerup);
				}
			}
		}
		for (SpeedPowerup speedPowerup : doneList) {
			speedPowerupList.remove(speedPowerup);
		}
	}
	
	/**
	 * Process the coins that are currently in the game.
	 * @param container the GameContainer we are playing in
	 * @param deltafloat the time in seconds since the last frame
	 */
	private void processCoins(GameContainer container, float deltafloat) {
		for (Coin coin : droppedCoins) {
			coin.update(parentState.getFloor(), deltafloat, container.getHeight());
		}
	}
	
	/**
	 * Drop a coin.
	 * @param circle the circle that should drop the coin.
	 */
	public void dropCoin(BouncingCircle circle) {
		boolean bigMoney = random.nextBoolean();
		Coin someCoin = new Coin(circle.getCenterX(), circle.getCenterY(), bigMoney);
		synchronized (droppedCoins) {
			droppedCoins.add(someCoin);
		}
		if (mainGame.isLanMultiplayer()) {
			mainGame.getHost().updateCoinsAdd(someCoin);
		}
	}

	/**
	 * Drop a powerup.
	 * @param circle the circle that should drop the powerup
	 */
	public void dropPowerup(BouncingCircle circle) {
		// Get a random powerup
		Powerup.PowerupType newPowerup = Powerup.PowerupType.values()[new Random()
				.nextInt(Powerup.PowerupType.values().length)];
		Powerup somePowerup = new Powerup(circle.getCenterX(), circle.getCenterY(), newPowerup);
		//somePowerup = new Powerup(circle.getCenterX(), 
		//circle.getCenterY(), Powerup.PowerupType.RANDOM);
		droppedPowerups.add(somePowerup);
		if (mainGame.isLanMultiplayer()) {
			mainGame.getHost().updatePowerupsAdd(somePowerup);
		}
	}
	
	@Override
	public void render(Graphics graphics, GameContainer container) {
		drawPowerups(graphics);
		drawCoins(graphics);
	}
	
	/**
	 * Draw the powerups.
	 * @param graphics the Graphics object to draw things on screen
	 */
	private void drawPowerups(Graphics graphics) {
		synchronized (droppedPowerups) {
			for (Powerup pow : droppedPowerups) {
				RND.getInstance().drawPowerup(graphics, pow);
			}
		}
	}

	/**
	 * Draw the coins.
	 * @param graphics the Graphics object to draw things on screen
	 */
	private void drawCoins(Graphics graphics) {
		graphics.setColor(Color.blue);
		synchronized (droppedCoins) {
			for (Coin coin : droppedCoins) {
				coin.draw(graphics, mainGame);
			}	
		}
		graphics.setColor(Color.white);
	}

	/**
	 * @return the droppedPowerups
	 */
	public ArrayList<Powerup> getDroppedPowerups() {
		return droppedPowerups;
	}

	/**
	 * @param droppedPowerups the droppedPowerups to set
	 */
	public void setDroppedPowerups(ArrayList<Powerup> droppedPowerups) {
		this.droppedPowerups = droppedPowerups;
	}

	/**
	 * @return the droppedCoins
	 */
	public ArrayList<Coin> getDroppedCoins() {
		return droppedCoins;
	}

	/**
	 * @param droppedCoins the droppedCoins to set
	 */
	public void setDroppedCoins(ArrayList<Coin> droppedCoins) {
		this.droppedCoins = droppedCoins;
	}

	/**
	 * @return the speedPowerupList
	 */
	public ArrayList<SpeedPowerup> getSpeedPowerupList() {
		return speedPowerupList;
	}

	/**
	 * @param speedPowerupList the speedPowerupList to set
	 */
	public void setSpeedPowerupList(ArrayList<SpeedPowerup> speedPowerupList) {
		this.speedPowerupList = speedPowerupList;
	}

}
