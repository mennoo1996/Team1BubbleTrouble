import static org.junit.Assert.*;
import gui.MainGame;
import gui.SettingsState;

import org.junit.Before;
import org.junit.Test;

public class SettingsStateTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetID() {
		MainGame mg = new MainGame("Test");
		SettingsState s = new SettingsState(mg);
		assertEquals(3, s.getID());
	}

	@Test
	public void testSettingsState() {
		MainGame mg = new MainGame("Test");
		SettingsState s = new SettingsState(mg);
		s.setMg(mg);
		assertEquals(mg, s.getMg());
	}

	

}
