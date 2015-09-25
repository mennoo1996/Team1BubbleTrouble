import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.Image;

import gui.GameState;
import gui.MainGame;
import lan.Client;
import logic.BouncingCircle;
import logic.CircleList;
import logic.Logger;
import logic.Player;
import logic.PlayerList;
import logic.WeaponList;

public class ClientTest {
	Client c;
	MainGame mg;
	GameState gs;
	
	

	@Before
	public void setUp() throws Exception {
		mg = mock(MainGame.class);
		gs = mock(GameState.class);
		c = new Client("a", 1, mg, gs);
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
		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		
		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		
		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		
		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		
		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		
		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		
		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		
		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		
		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		
		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		
		ArrayList<BouncingCircle> clist = new ArrayList<BouncingCircle>();
		CircleList clmock = mock(CircleList.class);
		when(gs.getCircleList()).thenReturn(clmock);
		
		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		when(mg.getLogger()).thenReturn(new Logger(true));
		
		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		when(mg.getLogger()).thenReturn(new Logger(true));

		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		when(mg.getLogger()).thenReturn(new Logger(true));

		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		when(mg.getLogger()).thenReturn(new Logger(true));

		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		when(mg.getLogger()).thenReturn(new Logger(true));

		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		when(mg.getLogger()).thenReturn(new Logger(true));

		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		when(mg.getLogger()).thenReturn(new Logger(true));

		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		when(mg.getLogger()).thenReturn(new Logger(true));

		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		when(mg.getLogger()).thenReturn(new Logger(true));

		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		when(mg.getLogger()).thenReturn(new Logger(true));

		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		when(mg.getLogger()).thenReturn(new Logger(true));

		c.readServerCommands();
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
		when(gs.getWeaponList()).thenReturn(wl);
		when(mg.getLogger()).thenReturn(new Logger(true));

		c.readServerCommands();
	}

	

}
