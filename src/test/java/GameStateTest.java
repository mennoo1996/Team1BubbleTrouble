import static org.junit.Assert.*;

import java.util.ArrayList;

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
		assertEquals(gamestate.getMg(),mg);
	}

	@Test
	public void testGetGateList() {
		MainGame mg = new MainGame(null);
		GameState gamestate = new GameState(mg);
		assertEquals(gamestate.getGateList(),null);
	}
	
	@Test
	public void testSetCeiling() {
		MainGame mg = new MainGame(null);
		GameState gamestate = new GameState(mg);
		MyRectangle a = new MyRectangle(3f, 4f, 200f, 100f);
		gamestate.setCeiling(a);
		
		assertEquals(a,gamestate.getCeiling());
	}

	@Test
	public void testSetFloor() {
		MainGame mg = new MainGame(null);
		GameState gamestate = new GameState(mg);
		MyRectangle a = new MyRectangle(3f, 4f, 200f, 100f);
		gamestate.setFloor(a);
		
		assertEquals(a,gamestate.getFloor());
	}

	@Test
	public void testSetLeftWall() {
		MainGame mg = new MainGame(null);
		GameState gamestate = new GameState(mg);
		MyRectangle a = new MyRectangle(3f, 4f, 200f, 100f);
		gamestate.setLeftWall(a);
		
		assertEquals(a,gamestate.getLeftWall());
	}

	@Test
	public void testSetRightWall() {
		MainGame mg = new MainGame(null);
		GameState gamestate = new GameState(mg);
		MyRectangle a = new MyRectangle(3f, 4f, 210f, 100f);
		gamestate.setRightWall(a);
		
		assertEquals(a,gamestate.getRightWall());
	}

	@Test
	public void testSetGateList() {
		MainGame mg = new MainGame(null);
		GameState gamestate = new GameState(mg);
		ArrayList<Gate> gatelist = new ArrayList<Gate>();
		gamestate.setGateList(gatelist);
		
		assertEquals(gatelist,gamestate.getGateList());
	}
	
	@Test
	public void testGetMainGame() {
		MainGame mg = new MainGame(null);
		GameState gamestate = new GameState(mg);
		assertEquals(mg,gamestate.getMg());
	}
	
	@Test
	public void testSetMainGame() {
		MainGame mg = new MainGame(null);
		MainGame mo = new MainGame("hi");
		GameState gamestate = new GameState(mg);
		gamestate.setMg(mo);
		assertEquals(mo,gamestate.getMg());
	}

}
