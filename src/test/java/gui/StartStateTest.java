package gui;
import static org.junit.Assert.*;
import gui.MainGame;
import gui.MenuMainState;

import org.junit.Before;
import org.junit.Test;

public class StartStateTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetID() {
		MainGame mg = new MainGame("hello");
		MenuMainState s = new MenuMainState(mg);
		assertEquals(0, s.getID());
	}

	@Test
	public void testStartState() {
		MainGame mg = new MainGame("hello");
		MenuMainState s = new MenuMainState(mg);
		assertEquals(s.getMainGame(), mg);
	}

}
