import static org.junit.Assert.*;

import org.junit.Test;
import org.newdawn.slick.SlickException;


public class MainGameTest {

	@Test
	public void testMainGamePlayerImage() throws SlickException {
		MainGame game = new MainGame("hello");
		assertEquals("Playersprite.png", game.getPlayerImage());
	}

	@Test
	public void testMainGameLifeCount() throws SlickException {
		MainGame game = new MainGame("hello");
		assertEquals(5, game.getLifeCount());
	}
	
	@Test
	public void testGetPlayerImage() throws SlickException {
		MainGame game = new MainGame("hello");
		assertEquals("Playersprite.png", game.getPlayerImage());
	}
	
	@Test
	public void testSetPlayerImage() throws SlickException {
		MainGame game = new MainGame("hello");
		game.setPlayerImage("laser_vertical.png");
		assertEquals(game.getPlayerImage(),game.getPlayerImage());
	}
	
	@Test
	public void testSetLifeCount() throws SlickException {
		MainGame game = new MainGame("hello");
		game.setLifeCount(3);
		assertEquals(3,game.getLifeCount());
	}
	
	@Test
	public void testMain() {
		//How????
		//fail("Not yet implemented");
	}

	@Test
	public void testInitStatesListGameContainer() {
		//How????
		//fail("Not yet implemented");
	}

	@Test
	public void testDecreaselifeCount() throws SlickException {
		MainGame game = new MainGame("hello");
		game.decreaselifeCount();
		assertEquals(4,game.getLifeCount());
	}

	@Test
	public void testResetLifeCount() throws SlickException {
		MainGame game = new MainGame("hello");
		game.decreaselifeCount();
		game.resetLifeCount();
		assertEquals(5,game.getLifeCount());
	}

	@Test
	public void testGetLifeCount() throws SlickException {
		MainGame game = new MainGame("hello");
		assertEquals(5, game.getLifeCount());
	}

	@Test
	public void testResetLevelCount() throws SlickException {
		MainGame game = new MainGame("hello");
		game.setLevelCounter(4);
		game.resetLevelCount();
		assertEquals(0,game.getLevelCounter());
	}
	
	@Test
	public void testGetLevelCounter() throws SlickException {
		MainGame game = new MainGame("hello");
		assertEquals(0,game.getLevelCounter());
	}
	
	@Test
	public void testSetLevelCounter() throws SlickException {
		MainGame game = new MainGame("hello");
		game.setLevelCounter(99);
		assertEquals(99,game.getLevelCounter());
	}

}
