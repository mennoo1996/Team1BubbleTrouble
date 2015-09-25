import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.anyString;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.Image;

import gui.GameState;
import gui.MainGame;
import lan.Host;
import logic.BouncingCircle;
import logic.Coin;
import logic.FloatingScore;
import logic.Logger;
import logic.Player;
import logic.PlayerList;
import logic.Powerup;
import logic.WeaponList;

public class HostTest {
	MainGame mg;
	GameState gs;
	Host h;

	@Before
	public void setUp() throws Exception {
		mg = mock(MainGame.class);
		gs = mock(GameState.class);
	}

	@Test
	public void testHost() {
		h = new Host(0, mg, gs);
		assertEquals(0, h.getPortNumber());
	}
	
	@Test
	public void testReadClientInputs2() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs2.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		h.setReader(reader);
		PlayerList pl = mock(PlayerList.class);
		ArrayList<Player> plist = mock(ArrayList.class);
		when(mg.getPlayerList()).thenReturn(pl);
		when(pl.getPlayers()).thenReturn(plist);
		Image i1 = mock(Image.class);
		Image i2 = mock(Image.class);

		Image i3 = mock(Image.class);

		Image i4 = mock(Image.class);
		Player p = new Player(1, 2, 3, 4, i1, i2, i3, i4, mg);
		when(plist.get(0)).thenReturn(p);

		h.readClientInputs();
		
		
		
	}



	@Test
	public void testReadClientInputs3() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs3.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		h.setReader(reader);
		PlayerList pl = mock(PlayerList.class);
		ArrayList<Player> plist = mock(ArrayList.class);
		when(mg.getPlayerList()).thenReturn(pl);
		when(pl.getPlayers()).thenReturn(plist);
		Image i1 = mock(Image.class);
		Image i2 = mock(Image.class);

		Image i3 = mock(Image.class);

		Image i4 = mock(Image.class);
		Player p = new Player(1, 2, 3, 4, i1, i2, i3, i4, mg);
		when(plist.get(1)).thenReturn(p);

		h.readClientInputs();
		
		
		
	}

	

	@Test
	public void testReadClientInputs4() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs4.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		h.setReader(reader);
		PlayerList pl = mock(PlayerList.class);
		ArrayList<Player> plist = mock(ArrayList.class);
		when(mg.getPlayerList()).thenReturn(pl);
		when(pl.getPlayers()).thenReturn(plist);
		Image i1 = mock(Image.class);
		Image i2 = mock(Image.class);

		Image i3 = mock(Image.class);

		Image i4 = mock(Image.class);
		Player p = new Player(1, 2, 3, 4, i1, i2, i3, i4, mg);
		when(plist.get(1)).thenReturn(p);
		when(plist.get(0)).thenReturn(p);

		h.readClientInputs();
		
		
		
	}
	
	@Test
	public void testReadClientInputs5() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs5.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		h.setReader(reader);
		PlayerList pl = mock(PlayerList.class);
		ArrayList<Player> plist = mock(ArrayList.class);
		when(mg.getPlayerList()).thenReturn(pl);
		when(pl.getPlayers()).thenReturn(plist);
		Image i1 = mock(Image.class);
		Image i2 = mock(Image.class);

		Image i3 = mock(Image.class);

		Image i4 = mock(Image.class);
		Player p = new Player(1, 2, 3, 4, i1, i2, i3, i4, mg);
		when(plist.get(1)).thenReturn(p);
		when(plist.get(0)).thenReturn(p);

		h.readClientInputs();
		
		
		
	}
	
	@Test
	public void testReadClientInputs6() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs6.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		h.setReader(reader);
		PlayerList pl = mock(PlayerList.class);
		ArrayList<Player> plist = mock(ArrayList.class);
		when(mg.getPlayerList()).thenReturn(pl);
		when(pl.getPlayers()).thenReturn(plist);
		Image i1 = mock(Image.class);
		Image i2 = mock(Image.class);

		Image i3 = mock(Image.class);

		Image i4 = mock(Image.class);
		Player p = new Player(1, 2, 3, 4, i1, i2, i3, i4, mg);
		when(plist.get(1)).thenReturn(p);
		when(plist.get(0)).thenReturn(p);

		h.readClientInputs();
		
		
		
	}

	

	@Test
	public void testReadClientInputs7() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs7.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		h.setReader(reader);
		PlayerList pl = mock(PlayerList.class);
		ArrayList<Player> plist = mock(ArrayList.class);
		when(mg.getPlayerList()).thenReturn(pl);
		when(pl.getPlayers()).thenReturn(plist);
		Image i1 = mock(Image.class);
		Image i2 = mock(Image.class);

		Image i3 = mock(Image.class);

		Image i4 = mock(Image.class);
		Player p = new Player(1, 2, 3, 4, i1, i2, i3, i4, mg);
		when(plist.get(0)).thenReturn(p);

		h.readClientInputs();
		
		
		
	}

	

	@Test
	public void testReadClientInputs8() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs8.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		h.setReader(reader);
		PlayerList pl = mock(PlayerList.class);
		ArrayList<Player> plist = mock(ArrayList.class);
		when(mg.getPlayerList()).thenReturn(pl);
		when(pl.getPlayers()).thenReturn(plist);
		Image i1 = mock(Image.class);
		Image i2 = mock(Image.class);

		Image i3 = mock(Image.class);

		Image i4 = mock(Image.class);
		Player p = new Player(1, 2, 3, 4, i1, i2, i3, i4, mg);
		when(plist.get(0)).thenReturn(p);

		h.readClientInputs();
		
		
		
	}

	

	@Test
	public void testReadClientInputs9() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs9.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		h.setReader(reader);
		PlayerList pl = mock(PlayerList.class);
		ArrayList<Player> plist = mock(ArrayList.class);
		when(mg.getPlayerList()).thenReturn(pl);
		when(pl.getPlayers()).thenReturn(plist);
		Image i1 = mock(Image.class);
		Image i2 = mock(Image.class);

		Image i3 = mock(Image.class);

		Image i4 = mock(Image.class);
		Player p = new Player(1, 2, 3, 4, i1, i2, i3, i4, mg);
		when(plist.get(0)).thenReturn(p);

		h.readClientInputs();
		
		
		
	}

	

	@Test
	public void testReadClientInputs10() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs10.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		h.setReader(reader);
		PlayerList pl = mock(PlayerList.class);
		ArrayList<Player> plist = mock(ArrayList.class);
		when(mg.getPlayerList()).thenReturn(pl);
		when(pl.getPlayers()).thenReturn(plist);
		Image i1 = mock(Image.class);
		Image i2 = mock(Image.class);

		Image i3 = mock(Image.class);

		Image i4 = mock(Image.class);
		Player p = new Player(1, 2, 3, 4, i1, i2, i3, i4, mg);
		when(plist.get(0)).thenReturn(p);

		h.readClientInputs();
		
		
		
	}

	

	@Test
	public void testReadClientInputs11() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs11.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		h.setReader(reader);
		PlayerList pl = mock(PlayerList.class);
		ArrayList<Player> plist = mock(ArrayList.class);
		when(mg.getPlayerList()).thenReturn(pl);
		when(pl.getPlayers()).thenReturn(plist);
		Image i1 = mock(Image.class);
		Image i2 = mock(Image.class);

		Image i3 = mock(Image.class);

		Image i4 = mock(Image.class);
		Player p = new Player(1, 2, 3, 4, i1, i2, i3, i4, mg);
		when(plist.get(100)).thenReturn(p);
		
		WeaponList wl = mock(WeaponList.class);
		when(wl.getWeaponList()).thenReturn(new ArrayList<logic.Weapon>());
		when(gs.getWeaponList()).thenReturn(wl);
		
		h.readClientInputs();
		
		
		
	}
	
	@Test
	public void testReadClientInputs12() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs12.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		h.setReader(reader);
		PlayerList pl = mock(PlayerList.class);
		ArrayList<Player> plist = mock(ArrayList.class);
		when(mg.getPlayerList()).thenReturn(pl);
		when(pl.getPlayers()).thenReturn(plist);
		Image i1 = mock(Image.class);
		Image i2 = mock(Image.class);

		Image i3 = mock(Image.class);

		Image i4 = mock(Image.class);
		Player p = new Player(1, 2, 3, 4, i1, i2, i3, i4, mg);
		when(plist.get(100)).thenReturn(p);
		
		WeaponList wl = mock(WeaponList.class);
		when(wl.getWeaponList()).thenReturn(new ArrayList<logic.Weapon>());
		when(gs.getWeaponList()).thenReturn(wl);
		
		h.readClientInputs();
		
		
		
	}

	

	@Test
	public void testReadClientInputs13() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs13.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		h.setReader(reader);
		PlayerList pl = mock(PlayerList.class);
		ArrayList<Player> plist = mock(ArrayList.class);
		when(mg.getPlayerList()).thenReturn(pl);
		when(pl.getPlayers()).thenReturn(plist);
		Image i1 = mock(Image.class);
		Image i2 = mock(Image.class);

		Image i3 = mock(Image.class);

		Image i4 = mock(Image.class);
		Player p = new Player(1, 2, 3, 4, i1, i2, i3, i4, mg);
		when(plist.get(0)).thenReturn(p);

		h.readClientInputs();
		
		
		
	}

	

	@Test
	public void testReadClientInputs14() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs14.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		h.setReader(reader);
		PlayerList pl = mock(PlayerList.class);
		ArrayList<Player> plist = mock(ArrayList.class);
		when(mg.getPlayerList()).thenReturn(pl);
		when(pl.getPlayers()).thenReturn(plist);
		Image i1 = mock(Image.class);
		Image i2 = mock(Image.class);

		Image i3 = mock(Image.class);

		Image i4 = mock(Image.class);
		Player p = new Player(1, 2, 3, 4, i1, i2, i3, i4, mg);
		when(plist.get(0)).thenReturn(p);

		h.readClientInputs();
		
		
		
	}

	

	

	

	


	


	@Test
	public void testSetLogger() {
		h = new Host(100, mg, gs);
		Logger l = new Logger(true);
		h.setLogger(l);
		assertEquals(h.getLogger(), l);
	}

	@Test
	public void testClientConnected() {
		h = new Host(100, mg, gs);
		assertFalse(h.clientConnected());
	}

	@Test
	public void testMiscellaneous() {
		h = new Host(1, mg, gs);
		h.updateHostDead();
		h.sendFloatingScore(new FloatingScore(new Coin(1, 2, true)));
		h.updatePlayerLocation(0, 1, 1);
		h.updatePlayerName(0, "MENNO");
		h.playerStartedMoving(1, 1, 1, "LEFT");
		h.playerStoppedMoving(1, 1, 1);
		h.updateLaser(0, 1, 1, 1, 1, true);
		h.laserDone(0);
		h.updateCircles(new ArrayList<BouncingCircle>());
		h.updatePowerupsAdd(new Powerup(1, 1, Powerup.PowerupType.SHIELD));
		h.updatePowerupsDictate(new Powerup(1, 1, Powerup.PowerupType.SHIELD));
		h.updateCoinsAdd(new Coin(1, 1, true));
		h.updateCoinsDictate(new Coin(1, 1, true));
		h.updateLevelStarted();
		h.updateCountinStarted();
		h.updatePauseStarted();
		h.updatePauseStopped();
		h.updateLives(5);
		assertFalse(h.clientConnected());
	}



}
