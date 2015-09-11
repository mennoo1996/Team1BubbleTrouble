import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gui.GameState;
import gui.MainGame;

import java.util.ArrayList;

import logic.BouncingCircle;
import logic.Player;
import logic.PlayerList;
import logic.Weapon;
import logic.WeaponList;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;


@RunWith(MockitoJUnitRunner.class)
public class WeaponListTest {

	MainGame mg;
	GameState gs;
	Weapon w;
	WeaponList wl;
	Image img;

	@Before
	public void setUp() throws Exception {
		mg = new MainGame("TestGame");
		gs = new GameState(mg);
		
		mg = mock(MainGame.class);

		w = new Weapon(1, 2, 3, 4);
		wl = new WeaponList(w,mg,gs, true);
		wl.add(w);

		Player player = mock(Player.class);
		PlayerList pl = new PlayerList(player, mg, gs);
		pl.add(player);
		when(mg.getPlayerList()).thenReturn(pl);
		when(player.isShot()).thenReturn(true);
		
		img = mock(Image.class);
		wl.setLaserbeamimage(img);
		wl.setLasertipimage(img);
	}
	
	@Test
	public void testConstructor() {
		ExpectedException exception = ExpectedException.none();
		exception.expect(RuntimeException.class);
		assertNotNull(wl);
	}
	
	@Test
	public void testIntersectWeaponsWithCircle1() {
		mg.setMultiplayer(false);
		gs.setShotList(new ArrayList<BouncingCircle>());
		BouncingCircle circle = new BouncingCircle(1,2,3,4,5,6);
		
		wl.intersectWeaponsWithCircle(circle);
		assertFalse(w.isVisible());
	}
	
	@Test
	public void testIntersectWeaponsWithCircle2() {
		when(mg.isMultiplayer()).thenReturn(true);
		gs.setShotList(new ArrayList<BouncingCircle>());
		BouncingCircle circle = new BouncingCircle(1,2,3,4,5,6);
		
		wl.intersectWeaponsWithCircle(circle);
		assertFalse(w.isVisible());
	}
	
	@Test
	public void testDrawWeapons1() {
		Graphics g = mock(Graphics.class);
		wl.drawWeapons(g);
		Mockito.doNothing().when(g).drawImage(img, -19, -12);
	}
	
	@Test
	public void testDrawWeapons2() {
		Graphics g = mock(Graphics.class);
		when(mg.isMultiplayer()).thenReturn(true);
		wl.drawWeapons(g);
		Mockito.doNothing().when(g).drawImage(img, -19, -12);
	}
	
	@Test
	public void testSetWeapon() {
		Weapon w2 = new Weapon(1, 2, 3, 4);
		wl.setWeapon(0, w2);
		assertEquals(w2, wl.getWeaponList().get(0));
	}
	
	@Test
	public void testSetWeaponList() {
		Weapon w2 = new Weapon(1, 2, 3, 4);
		ArrayList<Weapon> wl2 = new ArrayList<Weapon>();
		wl2.add(w2);
		
		wl.setWeaponList(wl2);
		assertEquals(wl2, wl.getWeaponList());
	}
	
	@Test
	public void testGetLaserBeamImage() {
		assertEquals(img, wl.getLaserbeamimage());
	}
	
	@Test
	public void testGetLaserTipImage() {
		assertEquals(img, wl.getLasertipimage());
	}
}
