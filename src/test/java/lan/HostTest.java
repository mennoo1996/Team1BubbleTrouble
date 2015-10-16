package lan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import guigame.GameState;
import guigame.GameStateCirclesHelper;
import guigame.GameStateInterfaceHelper;
import guigame.GameStateItemsHelper;
import guigame.GameStateLogicHelper;
import guigame.GameStatePauseHelper;
import guigame.GameStatePlayerHelper;
import guimenu.MainGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import logic.BouncingCircle;
import logic.Coin;
import logic.FloatingScore;
import logic.WeaponList;

import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.Image;

import player.Player;
import player.PlayerList;
import powerups.Powerup;
import powerups.Powerup.PowerupType;

public class HostTest {
	MainGame mg;
	GameState gs;
	Host h;

	GameStateCirclesHelper ch;
	GameStateItemsHelper ih;
	GameStateInterfaceHelper ifh;
	GameStatePlayerHelper ph;
	GameStateLogicHelper lh;
	GameStatePauseHelper pah;
	
	@Before
	public void setUp() throws Exception {
		mg = mock(MainGame.class);
		gs = mock(GameState.class);

		ch = mock(GameStateCirclesHelper.class);
		ih = mock(GameStateItemsHelper.class);
		ifh = mock(GameStateInterfaceHelper.class);
		ph = mock(GameStatePlayerHelper.class);
		lh = mock(GameStateLogicHelper.class);
		pah = mock(GameStatePauseHelper.class);
		when(gs.getItemsHelper()).thenReturn(ih);
		when(gs.getCirclesHelper()).thenReturn(ch);
		when(gs.getInterfaceHelper()).thenReturn(ifh);
		when(gs.getPlayerHelper()).thenReturn(ph);
		when(gs.getLogicHelper()).thenReturn(lh);
		when(gs.getPauseHelper()).thenReturn(pah);
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
		when(gs.getPlayerHelper().getWeaponList()).thenReturn(wl);
		
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
	public void testReadClientInputs15() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs15.txt"));
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
	public void testReadClientInputs16() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs16.txt"));
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
	public void testReadClientInputs17() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs17.txt"));
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
	public void testReadClientInputs17_2() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs17_2.txt"));
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
	public void testReadClientInputs17_3() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs17_3.txt"));
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
	public void testReadClientInputs17_4() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs17_4.txt"));
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
	public void testReadClientInputs17_5() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs17_5.txt"));
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
	public void testReadClientInputs17_6() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs17_6.txt"));
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
	public void testReadClientInputs17_7() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs17_7.txt"));
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
	public void testReadClientInputs17_8() {
		h = new Host(0, mg, gs);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadClientInputs17_8.txt"));
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
	public void testClientConnected() {
		h = new Host(100, mg, gs);
		assertFalse(h.clientConnected());
	}

	@Test
	public void testMiscellaneous() {
		h = new Host(1, mg, gs);
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
		Coin coin = new Coin(0,0,false);
		Powerup powerup = new Powerup(0,0,PowerupType.SHIELD);
		ArrayList<BouncingCircle> cir = new ArrayList<BouncingCircle>();
		h.updateRequiredForGateList(cir, 0);
		h.updatePowerupsGrant(powerup);
		h.updateCoinsGrant(coin);
		h.clientConnected();
		h.updateRestart();
		h.updateDead();
		h.splittedCircle(new BouncingCircle(0, 0, 200, 100, 100, 1, 0));
		h.getReader();
		h.setPortNumber(4000);
		assertFalse(h.clientConnected());
	}



}
