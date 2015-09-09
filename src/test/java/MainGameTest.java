import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class MainGameTest {

	@Test
	public void testMainGamePlayerImage() throws SlickException {
		MainGame game = new MainGame("hello");
		assertEquals("Playersprite.png", game.getPlayerImageString());
	}

	@Test
	public void testMainGameLifeCount() throws SlickException {
		MainGame game = new MainGame("hello");
		assertEquals(5, game.getLifeCount());
	}
	
	@Test
	public void testGetPlayerImage() throws SlickException {
		MainGame game = new MainGame("hello");
		assertEquals("Playersprite.png", game.getPlayerImageString());
	}
	
	@Test
	public void testSetPlayerImage() throws SlickException {
		MainGame game = new MainGame("hello");
		game.setPlayerImageString("laser_vertical.png");
		assertEquals(game.getPlayerImageString(),game.getPlayerImageString());
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
	
	@Test
	public void testSetGravity() {
		MainGame game = new MainGame("hello");
		game.setGravity(10);
		assertEquals(10, game.getGravity(), 0);
	}
	
	@Test
	public void testSetStartingSpeed() {
		MainGame game = new MainGame("hello");
		game.setStartingSpeed(8);
		assertEquals(8, game.getStartingSpeed(), 0);
	}
	
	@Test
	public void testSpeedStep() {
		MainGame game = new MainGame("hello");
		game.setSpeedStep(8);
		assertEquals(8, game.getSpeedStep(), 0);
	}
	
	@Test
	public void testPlayerSpeed() {
		MainGame game = new MainGame("hello");
		game.setPlayerSpeed(10);
		assertEquals(10, game.getPlayerSpeed(), 0);
	}
	
	@Test
	public void testLaserWidth() {
		MainGame game = new MainGame("hello");
		game.setLaserWidth(5);
		assertEquals(5, game.getLaserWidth(), 0);
	}
	
	@Test
	public void testLaserSpeed() {
		MainGame game = new MainGame("hello");
		game.setLaserSpeed(8);
		assertEquals(8, game.getLaserSpeed(), 0);
	}
	
	@Test
	public void testXRes() {
		MainGame.setxRes(7);
		assertEquals(7, MainGame.getxRes(), 0);
	}
	
	@Test
	public void testYRes() {
		MainGame.setyRes(9);
		assertEquals(9, MainGame.getyRes(), 0);
	}
	
	@Test
	public void testBackGroundImage() {
		Image i = mock(Image.class);
		MainGame game = new MainGame("hello");
		game.setBackgroundImage(i);
		assertEquals(i, game.getBackgroundImage());
	}
	
	@Test
	public void testForeGroundImage() {
		Image i = mock(Image.class);
		MainGame game = new MainGame("hello");
		game.setForeGroundImage(i);
		assertEquals(i, game.getForeGroundImage());
	}
	
	@Test
	public void testTerminalImage() {
		Image i = mock(Image.class);
		MainGame game = new MainGame("hello");
		game.setTerminalImage(i);
		assertEquals(i, game.getTerminalImage());
	}
	
	@Test
	public void testLaserHorizontalImage() {
		Image i = mock(Image.class);
		MainGame game = new MainGame("hello");
		game.setLaserHorizontalImage(i);
		assertEquals(i, game.getLaserHorizontalImage());
	}
	
	@Test
	public void testLaserVerticalImage() {
		Image i = mock(Image.class);
		MainGame game = new MainGame("hello");
		game.setLaserVerticalImage(i);
		assertEquals(i, game.getLaserVerticalImage());
	}
	
	@Test
	public void testScore() {
		MainGame game = new MainGame("hello");
		game.setScore(90);
		assertEquals(90, game.getScore());
	}
	
	@Test
	public void testHighscoresFile() {
		MainGame game = new MainGame("hello");
		game.setHighscoresFile("hi");
		assertEquals("hi", game.getHighscoresFile());
	}
	
	@Test
	public void testHighScores() {
		HighScores h = new HighScores();
		MainGame game = new MainGame("hello");
		game.setHighscores(h);
		assertEquals(h, game.getHighscores());
	}
	
	@Test
	public void testDefaultXRes() {
		assertEquals(1600, MainGame.getDefaultXRes());
	}
	
	@Test
	public void testDefaultYRes() {
		assertEquals(1000, MainGame.getDefaultYRes());
	}
	
	@Test
	public void testLives() {
		assertEquals(5, MainGame.getLives());
	}
	
	@Test
	public void testStartState() {
		MainGame game = new MainGame("hello");
		assertEquals(0, game.getStartState());
	}
	
	@Test
	public void testGetGameOverState() {
		MainGame game = new MainGame("hello");
		assertEquals(2, game.getGameOverState());
	}
	
	@Test
	public void testSettingsState() {
		MainGame game = new MainGame("hello");
		assertEquals(4, game.getSettingsState());
	}
	
	@Test
	public void testGameState() {
		MainGame game = new MainGame("hello");
		assertEquals(1, game.getGameState());
	}
	
	@Test
	public void testWonState() {
		MainGame game = new MainGame("hello");
		assertEquals(3, game.getWonState());
	}
	
	@Test
	public void testTargetFrameRate() {
		assertEquals(60, MainGame.getTargetFramerate());
	}
	
	@Test
	public void testDefaultGravity() {
		assertEquals(500, MainGame.getDefaultGravity(), 0);
	}
	
	@Test
	public void testGetDefaultStartingSpeed() {
		assertEquals(200, MainGame.getDefaultStartingSpeed(), 0);
	}
	
	@Test
	public void testGetDefaultSpeedStep() {
		assertEquals(0.5, MainGame.getDefaultSpeedStep(),0);
		
	}
	
	@Test
	public void testGetDefaultPlayerSpeed() {
		assertEquals(400, MainGame.getDefaultPlayerSpeed(), 0);
	}
	
	@Test
	public void testGetDefaultLaserWidth() {
		assertEquals(3, MainGame.getDefaultLaserWidth(), 0);
	}
	
	@Test
	public void testGetDefaultLaserSpeed() {
		assertEquals(1000, MainGame.getDefaultLaserSpeed(), 0);
	}

}
