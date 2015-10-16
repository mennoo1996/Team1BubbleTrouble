package logic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;


@RunWith(MockitoJUnitRunner.class)
public class WeaponListTest {

	MainGame mg;
	GameState gs;
	Weapon w;
	WeaponList wl;
	Image img;
	Image imgA;
	Color col;

	GameStateCirclesHelper ch;
	GameStateItemsHelper ih;
	GameStateInterfaceHelper ifh;
	GameStatePlayerHelper ph;
	GameStateLogicHelper lh;
	GameStatePauseHelper pah;
	
	@Before
	public void setUp() throws Exception {
		mg = new MainGame("TestGame");
		gs = mock(GameState.class);
		
		mg = mock(MainGame.class);


		w = new Weapon(1, 2, 3, 4);
		wl = new WeaponList(w,mg,gs, true);
		wl.add(w);

		col = new Color(0.5f, 0.5f, 0.5f);
		Player player = mock(Player.class);
		PlayerList pl = new PlayerList(player, mg, gs);
		pl.add(player);
		when(mg.getPlayerList()).thenReturn(pl);
		when(mg.getColor()).thenReturn(col);
		when(player.isShot()).thenReturn(true);
		
		img = mock(Image.class);
		imgA = mock(Image.class);
		Texture texture = mock(Texture.class);
		when(img.getTexture()).thenReturn(texture);
		when(imgA.getTexture()).thenReturn(texture);
		wl.setLaserbeamimage(img, imgA);
		wl.setLasertipimage(img, imgA);

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
	public void testConstructor() {
		ExpectedException exception = ExpectedException.none();
		exception.expect(RuntimeException.class);
		assertNotNull(wl);
	}
	
	@Test
	public void testIntersectWeaponsWithCircle1() {
		mg.setMultiplayer(false);
		gs.getCirclesHelper().setShotList(new ArrayList<BouncingCircle>());
		BouncingCircle circle = new BouncingCircle(1,2,3,4,5,6, 0);
		
		wl.intersectWeaponsWithCircle(circle);
		assertFalse(w.isVisible());
	}
	
	@Test
	public void testIntersectWeaponsWithCircle2() {
		when(mg.isMultiplayer()).thenReturn(true);
		gs.getCirclesHelper().setShotList(new ArrayList<BouncingCircle>());
		BouncingCircle circle = new BouncingCircle(1,2,3,4,5,6, 0);
		
		wl.intersectWeaponsWithCircle(circle);
		assertFalse(w.isVisible());
	}
	
	@Test
	public void testDrawWeapons1() {
		Graphics g = mock(Graphics.class);
		wl.drawWeapons(g);
		Mockito.doNothing().when(g).drawImage(img, -19, -12, col);
		Mockito.doNothing().when(g).drawImage(imgA, -19, -12, col);
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
		assertEquals(img, wl.getLaserbeamimageN());
	}
	
	@Test
	public void testGetLaserTipImage() {
		assertEquals(img, wl.getLasertipimageN());
	}
	
	@Test
	public void testGetLaserBeamImageA() {
		assertEquals(imgA, wl.getLaserbeamimageA());
	}
	
	@Test
	public void testGetLaserTipImageA() {
		assertEquals(imgA, wl.getLasertipimageA());
	}
}
