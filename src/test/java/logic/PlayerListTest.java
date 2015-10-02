package logic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

import gui.GameState;
import gui.MainGame;
import lan.Host;
import logic.BouncingCircle;
import logic.Logger;
import logic.Player;
import logic.PlayerList;

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
	public void testUpdatePlayers2() {
		Player p = mock(Player.class);
		Mockito.doNothing().when(p).update(1,  1000, 1600,  false);
		mg = mock(MainGame.class);
		when(mg.isMultiplayer()).thenReturn(false);
		when(mg.isLanMultiplayer()).thenReturn(false);
		PlayerList pl = new PlayerList(p, mg, gs);
		pl.add(p);
		pl.updatePlayers(1, 1000, 1600);
		verify(p, times(1)).update(1, 1000, 1600, false);
	}
	
	@Test
	public void testUpdatePlayers3() {
		Player p = mock(Player.class);
		Mockito.doNothing().when(p).update(1,  1000, 1600,  false);
		mg = mock(MainGame.class);
		when(mg.isMultiplayer()).thenReturn(false);
		when(mg.isLanMultiplayer()).thenReturn(true);
		PlayerList pl = new PlayerList(p, mg, gs);
		pl.add(p);
		pl.updatePlayers(1, 1000, 1600);
		verify(p, times(2)).update(1, 1000, 1600, false);
	}
	
	@Test
	public void testAdd2() {
		Player p1 = mock(Player.class);
		Player p2 = mock(Player.class);
		Player p3 = mock(Player.class);
		mg = mock(MainGame.class);
		gs = mock(GameState.class);
		PlayerList pl = new PlayerList(p, mg, gs);
		pl.add(p1);
		pl.add(p2);
		pl.add(p3);
		assertEquals(2, pl.getPlayers().size());
	}
	
	@Test
	public void testPlayerDeath() {
		StateBasedGame sbg = mock(StateBasedGame.class);
		p = mock(Player.class);
		mg = mock(MainGame.class);
		gs = mock(GameState.class);
		PlayerList pl = new PlayerList(p, mg, gs);
		pl.playerDeath(sbg);
		
	}
	
	@Test
	public void testPlayerDeath2() {
		StateBasedGame sbg = mock(StateBasedGame.class);
		p = mock(Player.class);
		mg = mock(MainGame.class);
		gs = mock(GameState.class);
		
		PlayerList pl = new PlayerList(p, mg, gs);
		pl.setDied(true);
		pl.playerDeath(sbg);
		
	}
	
	@Test
	public void testPlayerDeath3() {
		StateBasedGame sbg = mock(StateBasedGame.class);
		p = mock(Player.class);
		mg = mock(MainGame.class);
		gs = mock(GameState.class);
		
		PlayerList pl = new PlayerList(p, mg, gs);
		pl.setDied(false);
		when(mg.isLanMultiplayer()).thenReturn(true);
		pl.playerDeath(sbg);
		
	}
	
	@Test
	public void testPlayerDeath4() {
		StateBasedGame sbg = mock(StateBasedGame.class);
		p = mock(Player.class);
		mg = mock(MainGame.class);
		gs = mock(GameState.class);
		
		PlayerList pl = new PlayerList(p, mg, gs);
		pl.setDied(false);
		when(mg.isLanMultiplayer()).thenReturn(false);
		when(mg.isMultiplayer()).thenReturn(true);
		pl.playerDeath(sbg);
		
	}
	
	@Test
	public void testPlayerDeath5() {
		StateBasedGame sbg = mock(StateBasedGame.class);
		p = mock(Player.class);
		mg = mock(MainGame.class);
		gs = mock(GameState.class);
		
		PlayerList pl = new PlayerList(p, mg, gs);
		pl.setDied(false);
		when(mg.isLanMultiplayer()).thenReturn(true);
		when(mg.isMultiplayer()).thenReturn(true);
		Host h = mock(Host.class);
		when(mg.getLifeCount()).thenReturn(1);
		when(mg.isHost()).thenReturn(true);
		when(mg.getHost()).thenReturn(h);
		pl.playerDeath(sbg);
	}
	
	@Test
	public void testIsDied() {
		p = mock(Player.class);
		mg = mock(MainGame.class);
		gs = mock(GameState.class);
		PlayerList pl = new PlayerList(p, mg, gs);
		pl.setDied(true);
		assertTrue(pl.isDied());
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
		
		assertEquals(mg, a.getMainGame());
	}
	
	@Test
	public void testPlayerList3() throws SlickException {
		p = new Player(5, 6, 8, 9, i, i, i, i, mg);
		PlayerList a = new PlayerList(p, mg, gs);
		
		assertEquals(gs, a.getGameState());
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
		assertEquals(mg, a.getMainGame());
	}
	
	@Test
	public void testGetSg() {
		p = new Player(5, 6, 8, 9, i, i, i, i, mg);
		PlayerList a = new PlayerList(p, mg, gs);
		assertEquals(gs, a.getGameState());
	}
	
	@Test
	public void testSetAndGetProcessCollisions() {
		p = new Player(5, 6, 8, 9, i, i, i, i, mg);
		PlayerList a = new PlayerList(p, mg, gs);
		assertTrue(a.getProcessCollisions());
		a.setProcessCollisions(false);
		assertFalse(a.getProcessCollisions());
	}
	
}
