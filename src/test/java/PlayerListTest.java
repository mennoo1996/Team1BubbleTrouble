import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class PlayerListTest {

	MainGame mg;
	Image i;
	Image i2;
	Player p;
	Player p2;
	SpriteSheet s;
	GameState gs;

	@Before
	public void setUp() throws Exception {
		i = mock(Image.class);
		i2 = mock(Image.class);
		mg = new MainGame("maingame");
		s = mock(SpriteSheet.class);
		gs = mock(GameState.class);
	}
	
	@Test
	public void testPlayerList1() throws SlickException {
		p = new Player(5, 6, 8, 9, i, i, mg);
		PlayerList a = new PlayerList(p, mg, gs);
		ArrayList<Player> playerlist = new ArrayList<Player>();
		playerlist.add(p);
		
		assertEquals(playerlist, a.getPlayerList());
	}
	
	@Test
	public void testPlayerList2() throws SlickException {
		p = new Player(5, 6, 8, 9, i, i, mg);
		PlayerList a = new PlayerList(p, mg, gs);
		
		assertEquals(mg, a.getMg());
	}
	
	@Test
	public void testPlayerList3() throws SlickException {
		p = new Player(5, 6, 8, 9, i, i, mg);
		PlayerList a = new PlayerList(p, mg, gs);
		
		assertEquals(gs, a.getGs());
	}

	@Test
	public void testAdd() {
		p = new Player(5, 6, 8, 9, i, i, mg);
		p2 = new Player(5, 6, 8, 9, i, i, mg);
		PlayerList a = new PlayerList(p, mg, gs);
		a.add(p2);
		assertTrue(a.getPlayerList().contains(p));
		assertTrue(a.getPlayerList().contains(p2));
	}

	@Test
	public void testSetAllPlayersShot() {
		p = new Player(5, 6, 8, 9, i, i, mg);
		PlayerList a = new PlayerList(p, mg, gs);
		a.setAllPlayersShot(true);
		assertTrue(a.getPlayerList().get(0).isShot());
	}

	@Test
	public void testGetPlayerList() {
		p = new Player(5, 6, 8, 9, i, i, mg);
		PlayerList a = new PlayerList(p, mg, gs);
		ArrayList<Player> playerlist = new ArrayList<Player>();
		playerlist.add(p);
		
		assertEquals(playerlist, a.getPlayerList());
	}

	@Test
	public void testSetPlayerList() {
		p = new Player(5, 6, 8, 9, i, i, mg);
		p2 = new Player(5, 6, 8, 9, i, i, mg);
		
		PlayerList a = new PlayerList(p, mg, gs);
		ArrayList<Player> playerlist = new ArrayList<Player>();
		playerlist.add(p2);
		a.setPlayerList(playerlist);
		
		assertTrue(playerlist.contains(p2));
		assertFalse(playerlist.contains(p));
	}

	@Test
	public void testGetMg() {
		p = new Player(5, 6, 8, 9, i, i, mg);
		PlayerList a = new PlayerList(p, mg, gs);
		assertEquals(mg, a.getMg());
	}
	
	@Test
	public void testGetSg() {
		p = new Player(5, 6, 8, 9, i, i, mg);
		PlayerList a = new PlayerList(p, mg, gs);
		assertEquals(gs, a.getGs());
	}
	
}
