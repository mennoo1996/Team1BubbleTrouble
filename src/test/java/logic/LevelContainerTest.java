package logic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import guimenu.MainGame;

import java.util.ArrayList;

import levels.Level;
import levels.Level1;

import org.junit.Test;


public class LevelContainerTest {
	private MainGame mg = mock(MainGame.class);

	@Test
	public void testLevelContainer() {
		LevelContainer welp = new LevelContainer(mg);
		assertEquals(welp.getLevels(), new ArrayList<Level>());
	}

	@Test
	public void testGetLevels() {
		LevelContainer welp = new LevelContainer(mg);
		assertEquals(welp.getLevels(), new ArrayList<Level>());
	}
	
	@Test
	public void testAdd() {
		LevelContainer welp = new LevelContainer(mg);
		Level level = new Level1(mg, false);
		ArrayList<Level> first = new ArrayList<Level>();
		first.add(level);
		welp.add(level);
		assertEquals(first,welp.getLevels());
	}

	@Test
	public void testGetLevel() {
		LevelContainer welp = new LevelContainer(mg);
		Level level = new Level1(mg, false);
		welp.add(level);
		assertEquals(welp.getLevel(0),level);
	}

	@Test
	public void testSize() {
		LevelContainer welp = new LevelContainer(mg);
		assertEquals(welp.size(),0);
	}

	// If anyone knows how to make the maingame have a container that isn't null be my guest
//	@Test
//	public void testInitialize1() throws SlickException {
//		LevelContainer welp = new LevelContainer(mg);
//		welp.initialize();
//		assertEquals(welp.getLevels().size(), 10);
//	}
	
	@Test
	public void testSetLevels() {
		ArrayList<Level> result = new ArrayList<Level>();
		result.add(new Level1(mg, false));
		LevelContainer welp = new LevelContainer(mg);
		welp.setLevels(result);
		assertEquals(welp.getLevels(),result);
	}
	
	@Test
	public void testInitialize() {
		Level.setTesting(true);
	LevelContainer lc = new LevelContainer(mg);
	
		
		//Mockito.when(logger.log("Levels are initialized", PriorityLevels.HIGH.getValue(), "LevelContainer").then;
		lc.initialize();
		assertEquals(10, lc.size());
		LevelContainer.setTesting(false);
	}
	
	@Test
	public void testIsTesting() {
		assertFalse(LevelContainer.isTesting());
	}
		
	
}
