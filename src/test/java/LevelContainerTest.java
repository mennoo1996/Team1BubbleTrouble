import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;


public class LevelContainerTest {
	private MainGame mg = new MainGame("maingame");

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
		Level level = new Level(2, new ArrayList<BouncingCircle>(), new ArrayList<Gate>());
		ArrayList<Level> first = new ArrayList<Level>();
		first.add(level);
		welp.add(level);
		assertEquals(first,welp.getLevels());
	}

	@Test
	public void testGetLevel() {
		LevelContainer welp = new LevelContainer(mg);
		Level level = new Level(2, new ArrayList<BouncingCircle>(), new ArrayList<Gate>());
		welp.add(level);
		assertEquals(welp.getLevel(0),level);
	}

	@Test
	public void testSize() {
		LevelContainer welp = new LevelContainer(mg);
		assertEquals(welp.size(),0);
	}

}
