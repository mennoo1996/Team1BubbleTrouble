import static org.junit.Assert.*;
import gui.MainGame;
import gui.StartState;

import org.junit.Before;
import org.junit.Test;

public class StartStateTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetID() {
		MainGame mg = new MainGame("hello");
		StartState s = new StartState(mg);
		assertEquals(0, s.getID());
	}

	@Test
	public void testStartState() {
		MainGame mg = new MainGame("hello");
		StartState s = new StartState(mg);
		assertEquals(s.getMainGame(), mg);
	}

}
