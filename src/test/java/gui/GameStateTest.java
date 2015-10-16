package gui;
import static org.junit.Assert.assertEquals;
import guigame.GameState;
import guimenu.MainGame;
import logic.MyRectangle;

import org.junit.Test;


public class GameStateTest {

	@Test
	public void testGetID() {
		MainGame mg = new MainGame(null);
		GameState gamestate = new GameState(mg);
		assertEquals(mg.getGameState(), gamestate.getID());
	}

	@Test
	public void testGameState() {
		MainGame mg = new MainGame(null);
		GameState gamestate = new GameState(mg);
		assertEquals(gamestate.getmainGame(),mg);
	}
	
	@Test
	public void testGetMainGame() {
		MainGame mg = new MainGame(null);
		GameState gamestate = new GameState(mg);
		assertEquals(mg,gamestate.getmainGame());
	}

}
