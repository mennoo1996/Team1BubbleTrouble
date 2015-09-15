import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gui.GameState;
import gui.MainGame;

import java.util.ArrayList;

import logic.BouncingCircle;
import logic.Player;
import logic.PlayerList;

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
	
//	@Test
//	public void testDrawPlayer1() {
//		MainGame mg = mock(MainGame.class);
//		when(mg.isMultiplayer()).thenReturn(true);
//		Graphics gr = mock(Graphics.class);
//		//RND rnd = mock(RND.class);
//		//AngelCodeFont acf = mock(AngelCodeFont.class);
//		//when(mg.getDosFont()).thenReturn(acf);
//		//Mockito.doNothing().when(acf).drawString(1, 1, "#PLAYER_1");
//		//Mockito.doNothing().when(rnd).text();
//		Player p = new Player(0,0,20,20,i,i,i,i,mg);
//		PlayerList pl = new PlayerList(p, mg, gs);
//		SpriteSheet ss = mock(SpriteSheet.class);
//		when(ss.getSprite(any(int.class), any(int.class))).thenReturn(null);
//		Mockito.doNothing().when(gr).drawImage(any(Image.class), any(float.class), any(float.class));
//		p.setSpritesheet(ss, ss);
//		pl.add(p);
//		pl.drawPlayers(gr);
//	}
//	
//	@Test
//	public void testDrawPlayer2() {
//		MainGame mg = mock(MainGame.class);
//		when(mg.isMultiplayer()).thenReturn(true);
//		Graphics gr = mock(Graphics.class);
//		AngelCodeFont acf = mock(AngelCodeFont.class);
//		when(mg.getDosFont()).thenReturn(acf);
//		Mockito.doNothing().when(acf).drawString(1, 1, "#PLAYER_1");
//		Player p = new Player(0,0,20,20,i,i,mg);
//		SpriteSheet ss = mock(SpriteSheet.class);
//		when(ss.getSprite(any(int.class), any(int.class))).thenReturn(null);
//		Mockito.doNothing().when(gr).drawImage(any(Image.class), any(float.class), any(float.class));
//		p.setSpritesheet(ss);
//		p.setMovement(2);
//		PlayerList pl = new PlayerList(p, mg, gs);
//		pl.add(p);
//		pl.drawPlayers(gr);
//	}
//	
//	@Test
//	public void testDrawPlayer3() {
//		MainGame mg = mock(MainGame.class);
//		when(mg.isMultiplayer()).thenReturn(true);
//		Graphics gr = mock(Graphics.class);
//		AngelCodeFont acf = mock(AngelCodeFont.class);
//		when(mg.getDosFont()).thenReturn(acf);
//		Mockito.doNothing().when(acf).drawString(1, 1, "#PLAYER_1");
//		Player p = new Player(0,0,20,20,i,i,mg);
//		PlayerList pl = new PlayerList(p, mg, gs);
//		SpriteSheet ss = mock(SpriteSheet.class);
//		when(ss.getSprite(any(int.class), any(int.class))).thenReturn(null);
//		Mockito.doNothing().when(gr).drawImage(any(Image.class), any(float.class), any(float.class));
//		p.setSpritesheet(ss);
//		p.setMovement(1);
//		p.addPowerup(Powerup.PowerupType.SHIELD);
//		pl.add(p);
//		pl.drawPlayers(gr);
//	}
	
	@Test
	public void testUpdatePlayers() {
		Player p = mock(Player.class);
		Mockito.doNothing().when(p).update(1, 1000, 1600, false);
		mg = mock(MainGame.class);
		when(mg.isMultiplayer()).thenReturn(true);
		PlayerList pl = new PlayerList(p, mg, gs);
		pl.add(p);
		pl.updatePlayers(1, 1000, 1600);
		verify(p, times(2)).update(1, 1000, 1600, false);
	}
	
	@Test 
	public void testIntersectPlayersWithCircle1() {
		MainGame mg = mock(MainGame.class);
		Mockito.doNothing().when(mg).enterState(1);
		when(mg.getLifeCount()).thenReturn(1);
		when(mg.isMultiplayer()).thenReturn(true);
		Player p = new Player(0,0,20,20,i,i,i,i,mg);
		PlayerList pl = new PlayerList(p, mg, gs);
		pl.add(p);
		
		BouncingCircle circle = new BouncingCircle(1,1,1,1,10,1);
		pl.intersectPlayersWithCircle(circle);
	}
	
	@Test 
	public void testIntersectPlayersWithCircle2() {
		MainGame mg = mock(MainGame.class);
		Mockito.doNothing().when(mg).enterState(1);
		when(mg.getLifeCount()).thenReturn(0);
		when(mg.isMultiplayer()).thenReturn(true);
		Player p = new Player(0,0,20,20,i,i,i,i,mg);
		PlayerList pl = new PlayerList(p, mg, gs);
		pl.add(p);
		
		BouncingCircle circle = new BouncingCircle(1,1,1,1,10,1);
		pl.intersectPlayersWithCircle(circle);
	}
	
	@Test
	public void testPlayerList1() throws SlickException {
		p = new Player(5, 6, 8, 9, i, i, i, i, mg);
		PlayerList a = new PlayerList(p, mg, gs);
		ArrayList<Player> playerlist = new ArrayList<Player>();
		playerlist.add(p);
		
		assertEquals(playerlist, a.getPlayers());
	}
	
	@Test
	public void testPlayerList2() throws SlickException {
		p = new Player(5, 6, 8, 9, i, i, i, i, mg);
		PlayerList a = new PlayerList(p, mg, gs);
		
		assertEquals(mg, a.getMg());
	}
	
	@Test
	public void testPlayerList3() throws SlickException {
		p = new Player(5, 6, 8, 9, i, i, i, i, mg);
		PlayerList a = new PlayerList(p, mg, gs);
		
		assertEquals(gs, a.getGs());
	}

	@Test
	public void testAdd() {
		p = new Player(5, 6, 8, 9, i, i, i, i, mg);
		p2 = new Player(5, 6, 8, 9, i, i, i, i, mg);
		PlayerList a = new PlayerList(p, mg, gs);
		a.add(p2);
		assertTrue(a.getPlayers().contains(p));
		assertTrue(a.getPlayers().contains(p2));
	}

	@Test
	public void testSetAllPlayersShot() {
		p = new Player(5, 6, 8, 9, i, i, i, i,  mg);
		PlayerList a = new PlayerList(p, mg, gs);
		a.setAllPlayersShot(true);
		assertTrue(a.getPlayers().get(0).isShot());
	}

	@Test
	public void testGetPlayerList() {
		p = new Player(5, 6, 8, 9, i, i, i, i, mg);
		PlayerList a = new PlayerList(p, mg, gs);
		ArrayList<Player> playerlist = new ArrayList<Player>();
		playerlist.add(p);
		
		assertEquals(playerlist, a.getPlayers());
	}

	@Test
	public void testSetPlayerList() {
		p = new Player(5, 6, 8, 9, i, i, i, i, mg);
		p2 = new Player(5, 6, 8, 9, i, i, i, i, mg);
		
		PlayerList a = new PlayerList(p, mg, gs);
		ArrayList<Player> playerlist = new ArrayList<Player>();
		playerlist.add(p2);
		a.setPlayers(playerlist);
		
		assertTrue(playerlist.contains(p2));
		assertFalse(playerlist.contains(p));
	}

	@Test
	public void testGetMg() {
		p = new Player(5, 6, 8, 9, i, i, i, i, mg);
		PlayerList a = new PlayerList(p, mg, gs);
		assertEquals(mg, a.getMg());
	}
	
	@Test
	public void testGetSg() {
		p = new Player(5, 6, 8, 9, i, i, i, i, mg);
		PlayerList a = new PlayerList(p, mg, gs);
		assertEquals(gs, a.getGs());
	}
	
}
