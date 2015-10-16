package lan;
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

import logic.CircleList;
import logic.Coin;
import logic.Logger;
import logic.Player;
import logic.PlayerList;
import logic.Weapon;
import logic.WeaponList;

import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import powerups.Powerup;

public class ClientTest {
	Client c;
	MainGame mg;
	GameState gs;
	ClientMessageReader cr;
	
	GameStateCirclesHelper ch = mock(GameStateCirclesHelper.class);
	GameStateItemsHelper ih = mock(GameStateItemsHelper.class);
	GameStateInterfaceHelper ifh = mock(GameStateInterfaceHelper.class);
	GameStatePlayerHelper ph = mock(GameStatePlayerHelper.class);
	GameStateLogicHelper lh = mock(GameStateLogicHelper.class);
	GameStatePauseHelper pah = mock(GameStatePauseHelper.class);

	@Before
	public void setUp() throws Exception {
		mg = mock(MainGame.class);
		gs = mock(GameState.class);
		c = new Client("a", 1, mg, gs);
		cr = new ClientMessageReader(c, mg, gs);
		when(gs.getItemsHelper()).thenReturn(ih);
		when(gs.getCirclesHelper()).thenReturn(ch);
		when(gs.getInterfaceHelper()).thenReturn(ifh);
		when(gs.getPlayerHelper()).thenReturn(ph);
		when(gs.getLogicHelper()).thenReturn(lh);
		when(gs.getPauseHelper()).thenReturn(pah);
	}
	
	@Test
	public void testReadServerCommands() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands1.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands2() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands2.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands2_2() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands2_2.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		ArrayList<logic.Weapon> test = new ArrayList<logic.Weapon>();
		test.add(new Weapon(0, 0, 1, 1));
		when(wl.getWeaponList()).thenReturn(test);
		when(gs.getPlayerHelper().getWeaponList()).thenReturn(wl);

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands3() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands3.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands4() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands4.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands4_2() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands4_2.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands5() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands5.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands6() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands6.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands7() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands7.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands8() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands8.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}
	
	
	@Test
	public void testReadServerCommands9() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands9.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		
		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands10() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands10.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}
	
	
	@Test
	public void testReadServerCommands11() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands11.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		
		//ArrayList<BouncingCircle> clist = new ArrayList<BouncingCircle>();
		CircleList clmock = mock(CircleList.class);
		when(gs.getCirclesHelper().getCircleList()).thenReturn(clmock);

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands12() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands12.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands12_2() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands12_2.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}

	@Test
	public void testReadServerCommands12_3() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands12_3.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands12_4() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands12_4.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands12_5() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands12_5.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		
		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands12_6() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands12_6.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands12_7() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands12_7.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands12_8() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands12_8.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		
		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands13() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands13.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		
		WeaponList wl = mock(WeaponList.class);
		when(wl.getWeaponList()).thenReturn(new ArrayList<logic.Weapon>());
		when(gs.getPlayerHelper().getWeaponList()).thenReturn(wl);

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands13_2() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands13_2.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		
		WeaponList wl = mock(WeaponList.class);
		when(wl.getWeaponList()).thenReturn(new ArrayList<logic.Weapon>());
		when(gs.getPlayerHelper().getWeaponList()).thenReturn(wl);

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands13_3() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands13_3.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		
		WeaponList wl = mock(WeaponList.class);
		when(wl.getWeaponList()).thenReturn(new ArrayList<logic.Weapon>());
		when(gs.getPlayerHelper().getWeaponList()).thenReturn(wl);

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands13_7() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands13_7.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		
		WeaponList wl = mock(WeaponList.class);
		when(wl.getWeaponList()).thenReturn(new ArrayList<logic.Weapon>());
		when(gs.getPlayerHelper().getWeaponList()).thenReturn(wl);

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands13_8() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands13_8.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		
		WeaponList wl = mock(WeaponList.class);
		when(wl.getWeaponList()).thenReturn(new ArrayList<logic.Weapon>());
		when(gs.getPlayerHelper().getWeaponList()).thenReturn(wl);

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands14() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands14.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}

	@Test
	public void testReadServerCommands14_2() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands14_2.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}

	@Test
	public void testReadServerCommands14_3() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands14_3.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}

	@Test
	public void testReadServerCommands14_4() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands14_4.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}

	@Test
	public void testReadServerCommands14_5() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands14_5.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}

	@Test
	public void testReadServerCommands14_6() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands14_6.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}

	@Test
	public void testReadServerCommands14_7() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands14_7.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}

	@Test
	public void testReadServerCommands14_8() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands14_8.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands15() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands15.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands16() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands16.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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


		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands17() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands17.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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


		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands18() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands18.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		
		WeaponList wl = mock(WeaponList.class);
		when(wl.getWeaponList()).thenReturn(new ArrayList<logic.Weapon>());
		when(gs.getPlayerHelper().getWeaponList()).thenReturn(wl);


		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands19() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands19.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);

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

		WeaponList wl = mock(WeaponList.class);
		when(wl.getWeaponList()).thenReturn(new ArrayList<logic.Weapon>());
		when(gs.getPlayerHelper().getWeaponList()).thenReturn(wl);


		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands20() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands20.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		
		WeaponList wl = mock(WeaponList.class);
		when(wl.getWeaponList()).thenReturn(new ArrayList<logic.Weapon>());
		when(gs.getPlayerHelper().getWeaponList()).thenReturn(wl);
		

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands21() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands21.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		
		WeaponList wl = mock(WeaponList.class);
		when(wl.getWeaponList()).thenReturn(new ArrayList<logic.Weapon>());
		when(gs.getPlayerHelper().getWeaponList()).thenReturn(wl);
	

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands22() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands22.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		
		WeaponList wl = mock(WeaponList.class);
		when(wl.getWeaponList()).thenReturn(new ArrayList<logic.Weapon>());
		when(gs.getPlayerHelper().getWeaponList()).thenReturn(wl);
		

		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands23() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands23.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		
		WeaponList wl = mock(WeaponList.class);
		when(wl.getWeaponList()).thenReturn(new ArrayList<logic.Weapon>());
		when(gs.getPlayerHelper().getWeaponList()).thenReturn(wl);
		

		cr.readServerCommands(reader);
	}

	@Test
	public void testReadServerCommands24() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands24.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		
		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands25() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands25.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		
		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands26() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands26.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		
		cr.readServerCommands(reader);
	}
	
	@Test
	public void testReadServerCommands27() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("resources/testing/testReadServerCommands27.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.setReader(reader);
		
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
		
		cr.readServerCommands(reader);
	}
	
	
	
	@Test
	public void testMiscellaneous() {
//		h.sendFloatingScore(new FloatingScore(new Coin(1, 2, true)));
//		h.updatePlayerLocation(0, 1, 1);
//		h.updatePlayerName(0, "MENNO");
//		h.playerStartedMoving(1, 1, 1, "LEFT");
//		h.playerStoppedMoving(1, 1, 1);
//		h.updateLaser(0, 1, 1, 1, 1, true);
//		h.laserDone(0);
//		h.updateCircles(new ArrayList<BouncingCircle>());
//		h.updatePowerupsAdd(new Powerup(1, 1, Powerup.PowerupType.SHIELD));
//		h.updatePowerupsDictate(new Powerup(1, 1, Powerup.PowerupType.SHIELD));
//		h.updateCoinsAdd(new Coin(1, 1, true));
//		h.updateCoinsDictate(new Coin(1, 1, true));
//		h.updateLevelStarted();
//		h.updateCountinStarted();
//		h.updatePauseStarted();
//		h.updatePauseStopped();
//		h.updateLives(5);
		Coin coin = new Coin(0, 0, false);
		Powerup powerup = new Powerup(0, 0, Powerup.PowerupType.FAST);
		c.pleaCoin(coin);
		c.pleaPowerup(powerup);
		c.updatePowerupsAdd(powerup);
	}

}
