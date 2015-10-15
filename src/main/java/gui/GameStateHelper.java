package gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Abstract GameStateHelper class used to relieve GameState of most of its responsibilities.
 * @author Mark
 */
public abstract class GameStateHelper {
	
	/**
	 * Generally called by GameState on Enter().
	 */
	public abstract void enter();
	
	/**
	 * Generally called by GameState on Exit().
	 */
	public abstract void exit();
	
	/**
	 * Generally called by GameState on Update().
	 * @param container is the container most objects are updated in.
	 * @param sbg the StateBasedGame required.
	 * @param deltaFloat shows how much time elapsed since the last frame.
	 */
	public abstract void update(GameContainer container, StateBasedGame sbg, float deltaFloat);
	
	/**
	 * Generally called by GameState on Render().
	 * @param graphics the context we draw in
	 * @param container the container most objects are located in
	 */
	public abstract void render(Graphics graphics, GameContainer container);
	
}
