package gui;
import static org.junit.Assert.*;
import gui.GameState;
import gui.MainGame;

import java.util.ArrayList;

import logic.Gate;
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
	public void testGetMainGame() {
		MainGame mg = new MainGame(null);
		GameState gamestate = new GameState(mg);
		assertEquals(mg,gamestate.getmainGame());
	}

}
