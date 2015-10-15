package guigame;

import guimenu.MainGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import logic.BouncingCircle;
import logic.CircleList;
import logic.FloatingScore;
import logic.Gate;
import logic.Logger;
import logic.Logger.PriorityLevels;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * GameState Helper class for managing all circles. This is done to prevent GameState 
 * from holding too much responsibility and/or knowledge. The class should only
 * be used in conjunction with GameState.
 * @author Mark
 */
public class GameStateCirclesHelper extends GameStateHelper {

	private MainGame mainGame;
	private GameState parentState;
	private CircleList circleList; // object holding all circles
	private ArrayList<BouncingCircle> shotList; // list of all shot circles
	private int lastCircleUpdate;
	
	private static final int MINIMUM_SPLIT_RADIUS = 20;
	private static final int POWERUP_CHANCE = 20;
	private static final int COIN_CHANCE = 30;
	private static final int CIRCLES_UPDATE_RATE = 100; // rate is in frames
	
	/**
	 * Constructor for a GameStateCirclesHelper object.
	 * @param app the Main Game this class draws data from.
	 * @param state the GameState parent.
	 */
	public GameStateCirclesHelper(MainGame app, GameState state) {
		this.mainGame = app;
		this.parentState = state;
		try {
			Gate.loadImages();
			CircleList.loadImages();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void enter() {
		lastCircleUpdate = 0;
		circleList = new CircleList(parentState.getLevelContainer().getLevel(
				mainGame.getLevelCounter()).getCircles());
		shotList = new ArrayList<BouncingCircle>(); 
	}
	
	@Override
	public void exit() {

	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, float deltaFloat) {
		if (mainGame.isHost()) {
			lastCircleUpdate++;
			if (lastCircleUpdate >= CIRCLES_UPDATE_RATE) {
				lastCircleUpdate = 0;
				mainGame.getHost().updateCircles(circleList.getCircles());
				parentState.getGateHelper().update(container, sbg, deltaFloat);
			}
		}
		processCircles(container, deltaFloat);
		parentState.getGateHelper().updateGateExistence(deltaFloat);
	}
	
	
	@Override
	public void render(Graphics graphics, GameContainer container) {
		circleList.drawCircles(graphics, mainGame.getColor());
	}
	
	/**
	 * Process updating of circles.
	 * @param container the GameContainer circles are contained in.
	 * @param deltaFloat time used for the last frame.
	 */
	private void processCircles(GameContainer container, float deltaFloat) {
		ArrayList<BouncingCircle> ceilingList = new ArrayList<BouncingCircle>();
		updateActiveCircles(container, deltaFloat, ceilingList);
		circleList.getCircles().removeAll(ceilingList);
		updateShotCircles();
	}
	
	/**
	 * Update the circles that are still in the game, after all the other process methods.
	 * @param container the GameContainer you are playing in
	 * @param deltaFloat the time ins seconds since the last frame
	 * @param ceilingList the circles that have hit the ceiling
	 */
	private void updateActiveCircles(GameContainer container,
			float deltaFloat, ArrayList<BouncingCircle> ceilingList) {
		synchronized (circleList.getCircles()) {
			for (Iterator<BouncingCircle> iterator = 
					circleList.getCircles().iterator(); iterator.hasNext();) {
				BouncingCircle circle = iterator.next();
				circle.update(parentState, container.getHeight(), container.getWidth(), deltaFloat);

				mainGame.getPlayerList().intersectPlayersWithCircle(circle);

				parentState.getPlayerHelper().getWeaponList().intersectWeaponsWithCircle(circle);

				if (circle.isHitCeiling()) {
					ceilingList.add(circle);
				}
			}
		}
	}
	
	/**
	 * Update the circles that have been shot.
	 */
	private void updateShotCircles() {
		for (BouncingCircle circle : shotList) {
            if (!circle.isDone()) { // if the circle hasn't been handled

            	FloatingScore floatingScore = new FloatingScore(circle);
            	parentState.getInterfaceHelper().getFloatingScores().add(floatingScore);
            	//Send to client
            	if (mainGame.isLanMultiplayer() && mainGame.isHost()) {
            		mainGame.getHost().sendFloatingScore(floatingScore);
            	}
            	updateShotCirles2(circle, false);
			}
        }
	}

	/**
	 * Process the effects of a shooting a circle.
	 * @param circle the circle shot
	 * @param fromPeer indicates if the split command came from a peer
	 */
	public void updateShotCirles2(BouncingCircle circle, boolean fromPeer) {
		if (circleList.getCircles().contains(circle)) {
            circleList.getCircles().remove(circle);
            circle.setDone(true);
            parentState.getLogicHelper().addToScore(circle.getScore());
        } // if the ball has a radius of 20, split it u
        ArrayList<BouncingCircle> splits = new ArrayList<BouncingCircle>();
        if (circle.getRadius() >= MINIMUM_SPLIT_RADIUS) {
        	splits = circle.getSplittedCircles(mainGame, parentState);
            circleList.getCircles().addAll(splits);
			checkItem(circle);
        } else {
        	Logger.getInstance().log(
					"Circle with radius 10 shot, no new balls entered the game",
        			PriorityLevels.MEDIUM,
					"BouncingCircles");
        } // if it was part of the gate reqs, add to new gate reqs
		processUnlockCirclesGates(circle, splits);
		
		if (mainGame.isHost() && !fromPeer) {
			//mainGame.getHost().updateCircles(getCircleList().getCircles());
			mainGame.getHost().splittedCircle(circle);
		} else if (mainGame.isClient() && !fromPeer) {
			mainGame.getClient().splittedCircle(circle);
		}
	}
	
	
	
	/**
	 * Process the circles in the requirements lists of the gates.
	 * @param circle the circle you are processing
	 * @param splits the list of circles that have been split
	 */
	private void processUnlockCirclesGates(BouncingCircle circle,
										   ArrayList<BouncingCircle> splits) {
		for (Gate gate : parentState.getGateHelper().getGateList()) {
            if (gate.getUnlockCircles().contains(circle)) {
                gate.getUnlockCircles().remove(circle);
            }
            if (circle.getRadius() >= MINIMUM_SPLIT_RADIUS) {
                gate.addToRequirements(splits);
            }
        }
	}
	
	/**
	 * Check if an item should be dropped.
	 * @param circle the circle that could drop this item
	 */
	private void checkItem(BouncingCircle circle) {
		if (!mainGame.isLanMultiplayer() || mainGame.isHost()) {
			// 5% of the time
			final int total = 100;
			int randInt = new Random().nextInt(total) + 1;
			if (randInt <= POWERUP_CHANCE) {
				parentState.getItemsHelper().dropPowerup(circle);
			}
			else if (randInt <= POWERUP_CHANCE + COIN_CHANCE) {
				parentState.getItemsHelper().dropCoin(circle);
			}
		}
	}
	
	/**
	 * @return the shotList that shot circles are stored in.
	 */
	public ArrayList<BouncingCircle> getShotList() {
		return shotList;
	}
	
	/**
	 * set the shotlist.
	 * @param shotlist the shotlist to set
	 */
	public void setShotList(ArrayList<BouncingCircle> shotlist) {
		this.shotList = shotlist;
	}
	
	/**
	 * @return the circlelist object that circles are stored in.
	 */
	public CircleList getCircleList() {
		return circleList;
	}
	
	/**
	 * set the circlelist.
	 * @param circlelist the circlelist to set
	 */
	public void setCircleList(CircleList circlelist) {
		this.circleList = circlelist;
	}
	
}
