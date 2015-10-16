package guigame;

import java.util.ArrayList;

import logic.Gate;
import guimenu.MainGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * GameState Helper class for managing all gates. This is done to prevent GameState 
 * from holding too much responsibility and/or knowledge. The class should only
 * be used in conjunction with GameState.
 * @author Mark
 */
public class GameStateGateHelper extends GameStateHelper {

	private MainGame mainGame;
	private GameState parentState;
	
	private ArrayList<Gate> gateList;
	
	/**
	 * Constructor for a GameStateGateHelper object.
	 * @param app the Main Game this class draws data from.
	 * @param state the GameState parent.
	 */
	public GameStateGateHelper(MainGame app, GameState state) {
		mainGame = app;
		parentState = state;
	}
	
	@Override
	public void enter() {
		gateList = parentState.getLevelsHelper().getLevelContainer().getLevel(
				mainGame.getLevelCounter()).getGates();
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg,
			float deltaFloat) {
		for (int i = 0; i < gateList.size(); i++) {
			mainGame.getHost().updateRequiredForGateList(
					gateList.get(i).getUnlockCircles(), i);
		}
	}
	
	/**
	 * Update the existence of the gates. Remove them if the required balls have disappeared.
	 * @param deltaFloat the time in seconds since the last frame.
	 */
	public void updateGateExistence(float deltaFloat) {
		ArrayList<Gate> tempGateList = new ArrayList<Gate>();
		synchronized (gateList) {
			for (Gate gate : gateList) {
				if (gate.getUnlockCircles().isEmpty()) {
					tempGateList.add(gate);
					gate.setFading(true);
				}
			}
		}
		for (Gate gate : tempGateList) {
			if (gateList.contains(gate) && gate.isDone()) {
				gateList.remove(gate);
			} else if (gateList.contains(gate) && gate.isFading()) {
				gate.update(deltaFloat);
			}
		}
	}
	
	
	
	@Override
	public void render(Graphics graphics, GameContainer container) {
		synchronized (gateList) {
			for (Gate gate : gateList) {
				gate.draw(graphics, mainGame, parentState, container);
			}
		}
	}
	
	/**
	 * @return the list containing all gates.
	 */
	public ArrayList<Gate> getGateList() {
		return gateList;
	}
	
	/**
	 * set the gatelist.
	 * @param gatelist the gatelist to set
	 */
	public void setGateList(ArrayList<Gate> gatelist) {
		this.gateList = gatelist;
	}
	
}
