package gui;
import static org.junit.Assert.*;
import gui.MainGame;
import gui.MenuSettingsState;

import org.junit.Before;
import org.junit.Test;

public class SettingsStateTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetID() {
		MainGame mg = new MainGame("Test");
		MenuSettingsState s = new MenuSettingsState(mg);
		assertEquals(3, s.getID());
	}

	@Test
	public void testSettingsState() {
		MainGame mg = new MainGame("Test");
		MenuSettingsState s = new MenuSettingsState(mg);
		s.setmainGame(mg);
		assertEquals(mg, s.getmainGame());
	}

	

}
