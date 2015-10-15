package logic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyFloat;
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
import org.mockito.Mockito;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;

import powerups.Powerup;
import powerups.SpeedPowerup;

public class PlayerTest {
	
	MainGame mg;
	Image i;
	Image i2;
	Image i3;
	Image i4;
	Player p;
	SpriteSheet s;
	SpriteSheet s2;
	GameState gs;

	GameStateCirclesHelper ch = mock(GameStateCirclesHelper.class);
	GameStateItemsHelper ih = mock(GameStateItemsHelper.class);
	GameStateInterfaceHelper ifh = mock(GameStateInterfaceHelper.class);
	GameStatePlayerHelper ph = mock(GameStatePlayerHelper.class);
	GameStateLogicHelper lh = mock(GameStateLogicHelper.class);
	GameStatePauseHelper pah = mock(GameStatePauseHelper.class);

	@Before
	public void setUp() throws Exception {
		Logger.getInstance().setLoggingOn(false);
		i = mock(Image.class);
		i2 = mock(Image.class);
		i3 = mock(Image.class);
		i4 = mock(Image.class);
		mg = new MainGame("maingame");
		s = mock(SpriteSheet.class);
		gs = mock(GameState.class);
		ch = mock(GameStateCirclesHelper.class);
		ih = mock(GameStateItemsHelper.class);
		ifh = mock(GameStateInterfaceHelper.class);
		when(gs.getItemsHelper()).thenReturn(ih);
		when(gs.getCirclesHelper()).thenReturn(ch);
		when(gs.getInterfaceHelper()).thenReturn(ifh);
		when(gs.getPlayerHelper()).thenReturn(ph);
		when(gs.getLogicHelper()).thenReturn(lh);
		when(gs.getPauseHelper()).thenReturn(pah);
	}

	@Test
	public void testPlayerX() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(1, p.getX(), 0);
	}
	
	@Test
	public void testPlayerY() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(2, p.getY(), 0);
	}
	
	@Test
	public void testPlayerWidth() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(3, p.getWidth(), 0);
	}
	
	@Test
	public void testPlayerHeight() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(4, p.getHeight(), 0);
	}
	
	@Test
	public void testPlayerGetImageN() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(i, p.getImageN());
	}
	
	@Test
	public void testPlayerGetImageA() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(i2, p.getImageA());
	}
	
	@Test
	public void testPlayerGetShieldImageN() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(i3, p.getShieldImageN());
	}
	
	@Test
	public void testPlayerGetShieldImageA() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(i4, p.getShieldImageA());
	}
	
	@Test
	public void testPlayerSetImage() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		Image j = mock(Image.class);
		Image h = mock(Image.class);
		p.setImage(j, h);
		assertEquals(j, p.getImageN());
		assertEquals(h, p.getImageA());
	}

	@Test
	public void testGetRectangle() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(new MyRectangle(1, 2, 3, 4), p.getRectangle());
	}

	@Test
	public void testGetCenterX() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(2.5, p.getCenterX(), 0);
	}
	
	@Test
	public void testGetCenterXNoConstantValue() {
		p = new Player (3, 6, -8, 3, i, i2, i3, i4, mg);
		assertEquals(-1, p.getCenterX(), 0);
	}

	@Test
	public void testGetCenterY() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(4, p.getCenterY(), 0);
	}
	
	@Test
	public void testGetCenterYNoConstantValue() {
		p = new Player (2, 7, 1, 5, i, i2, i3, i4, mg);
		assertEquals(9.5, p.getCenterY(), 0);
	}

	@Test
	public void testGetMaxX() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(4, p.getMaxX(), 0);
	}
	
	@Test
	public void testGetMaxXNoConstantValue() {
		p = new Player(3, -4, 6 ,2, i, i2, i3, i4, mg);
		assertEquals(9, p.getMaxX(), 0);
	}
	

	@Test
	public void testGetMaxY() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(6, p.getMaxY(), 0);
	}
	
	@Test
	public void testGetMaxYNoConstantValue() {
		p = new Player(4, -9, 3, 6, i, i2, i3, i4, mg);
		assertEquals(-3, p.getMaxY(), 0);
	}

	@Test
	public void testGetX() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(1, p.getX(), 0);
	}
	
	@Test
	public void testGetXNoConstantValue() {
		p = new Player(3, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(3, p.getX(), 0);
	}

	@Test
	public void testSetX() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setX(4);
		assertEquals(4, p.getX(), 0);
	}
	
	@Test
	public void testSetXNoConstantValue() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setX(6);
		assertEquals(6, p.getX(), 0);
	}

	@Test
	public void testGetY() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(2, p.getY(), 0);
	}

	@Test
	public void testGetYNoConstantValue() {
		p = new Player(1, 6, 3, 4, i, i2, i3, i4, mg);
		assertEquals(6, p.getY(), 0);
	}
	@Test
	public void testSetY() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setY(5);
		assertEquals(5, p.getY(), 0);
	}
	
	@Test
	public void testSetYNoConstantValue() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setY(9);
		assertEquals(9, p.getY(), 0);
	}

	@Test
	public void testGetWidth() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(3, p.getWidth(), 0);
	}
	
	@Test
	public void testGetWidthNoConstantValue() {
		p = new Player(1, 2, 6, 4, i, i2, i3, i4, mg);
		assertEquals(6, p.getWidth(), 0);
	}

	@Test
	public void testSetWidth() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setWidth(7);
		assertEquals(7, p.getWidth(), 0);
	}
	
	@Test
	public void testSetWidthNoConstantValue() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setWidth(-4);
		assertEquals(-4, p.getWidth(), 0);
	}

	@Test
	public void testGetHeight() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(4,  p.getHeight(), 0);
	}
	
	@Test
	public void testGetHeightNoConstantValue() {
		p = new Player(1, 2, 3, 7, i, i2, i3, i4, mg);
		assertEquals(7, p.getHeight(), 0);
	}

	@Test
	public void testSetHeight() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setHeight(6);
		assertEquals(6, p.getHeight(), 0);
	}
	
	@Test
	public void testSetHeightNoConstantValue() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setHeight(18);
		assertEquals(18, p.getHeight(), 0);
	}
	
	@Test
	public void testSpriteSheet() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setSpritesheet(s, s2);
		assertEquals(s, p.getSpritesheetN());
		
	}
	
	@Test
	public void testSpriteSheet2() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setSpritesheet(s, s2);
		assertEquals(s2, p.getSpritesheetA());
		
	}
	
	@Test
	public void testMovementCounter() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.resetMovementCounter();
		p.incrementMovementCounter();
		assertEquals(1, p.getMovementCounter());
	}
	
	@Test
	public void testMovementCounter2() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setMovementCounter_Max(0);
		p.resetMovementCounter();
		p.incrementMovementCounter();
		assertEquals(0, p.getMovementCounter());
	}
	
	@Test
	public void testMovementCounterMax() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setMovementCounter_Max(5);
		assertEquals(5, p.getMovementCounter_Max());
	}
	
	@Test
	public void testUpdate1() {
		GameState gs = mock(GameState.class);
		Gate gate = new Gate(1,1,1,1);
		ArrayList<Gate> gl = new ArrayList<Gate>();
		gl.add(gate);
		Powerup pow = new Powerup(100,100,Powerup.PowerupType.SHIELD);
		ArrayList<Powerup> pl = new ArrayList<Powerup>();
		pl.add(pow);
		BouncingCircle circle = new BouncingCircle(1,2,3,4,5,6);
		FloatingScore fs = new FloatingScore(circle);
		ArrayList<FloatingScore> fsl = new ArrayList<FloatingScore>();
		fsl.add(fs);
		Weapon w = new Weapon(1,1,1,1);
		WeaponList wl = new WeaponList(w, mg, gs, true);
		mg = mock(MainGame.class);
		when(mg.getGameState()).thenReturn(1);
		when(mg.getState(1)).thenReturn(gs);
		
		when(ch.getGateList()).thenReturn(gl);
		when(ih.getDroppedPowerups()).thenReturn(pl);
		when(ifh.getFloatingScores()).thenReturn(fsl);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		MyRectangle ceiling = new MyRectangle(1,1,1,1);
		when(gs.getFloor()).thenReturn(floor);
		when(gs.getCeiling()).thenReturn(ceiling);
		when(ph.getWeaponList()).thenReturn(wl);
		when(gs.getLeftWall()).thenReturn(floor);
		when(gs.getRightWall()).thenReturn(floor);
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		//p.update(1, 1000, 1600, true);
		assertEquals(1,p.getX(),0);
		assertEquals(2,p.getY(),0);
	}
	

	
	@Test
	public void testSetPlayerName() {
		Player p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setPlayerName("Menno");
		assertEquals("Menno", p.getPlayerName());
	}

	@Test
	public void testGetPlayerName() {
		Player p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setPlayerName("Bart");
		assertEquals("Bart", p.getPlayerName());
	}
	
	@Test
	public void testControls() {
		Player p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setControlsForPlayer1();
		p.setControlsForPlayer2();
		p.setControlsDisabled();
		assertEquals(Integer.parseInt("5"), 5);
	}
	
	
	
	@Test
	public void testHasShieldFalse() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertFalse(p.hasShield());
	}
	
	@Test
	public void testHasShieldTrue() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.addPowerup(Powerup.PowerupType.SHIELD);
		assertTrue(p.hasShield());
	}

	@Test
	public void testRestPlayerLocation1() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.resetPlayerLocation(0);
		assertEquals(720,p.getX(),0);
		assertEquals(705,p.getY(),0);
	}
	
	@Test
	public void testRestPlayerLocation2() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.resetPlayerLocation(1);
		assertEquals(420,p.getX(),0);
		assertEquals(705,p.getY(),0);
	}
	
	@Test
	public void testGetAndSetGS() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		GameState gs2 = mock(GameState.class);
		p.setgameState(gs2);
		assertEquals(gs2, p.getgameState());
	}
	
	@Test
	public void testGetMoveLeftKey() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(Input.KEY_LEFT, p.getMoveLeftKey());
	}
	
	@Test
	public void testSetMoveLeftKey() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setMoveLeftKey(Input.KEY_0);
		assertEquals(Input.KEY_0, p.getMoveLeftKey());
	}
	
	@Test
	public void testGetMoveRightKey() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(203, p.getMoveLeftKey());
	}
	
	@Test
	public void testSetMoveRightKey() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setMoveRightKey(Input.KEY_0);
		assertEquals(Input.KEY_0, p.getMoveRightKey());
	}
	
	@Test
	public void testGetShootKey() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(Input.KEY_SPACE, p.getShootKey());
	}
	
	@Test
	public void testSetShootKey() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setShootKey(Input.KEY_0);
		assertEquals(Input.KEY_0, p.getShootKey());
	}
	
	@Test
	public void testRespawn() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.respawn();
		assertEquals(1,p.getX(),0);
		assertEquals(2,p.getY(),0);
	}
	
	@Test
	public void testIsShot() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertFalse(p.isShot());
	}
	
	@Test
	public void testSetShot() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setShot(true);
		assertTrue(p.isShot());
	}
	
	@Test
	public void testSetAndGetPlayerNumber() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setPlayerNumber(5);
		assertEquals(5, p.getPlayerNumber());
	}
	
	@Test
	public void testShieldTimeRemaining1() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.addPowerup(Powerup.PowerupType.SHIELD);
		assertTrue(p.shieldTimeRemaining() > 0);
	}
	
	@Test
	public void testShieldTimeRemaining2() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		assertEquals(0, p.shieldTimeRemaining(), 0);
	}
	
	@Test
	public void testIsOtherPlayers1() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setPlayerNumber(0);
		assertTrue(p.isOthersPlayer());
	}
	
	@Test
	public void testIsOtherPlayers2() {
		MainGame mg = mock(MainGame.class);
		when(mg.isHost()).thenReturn(true);
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setPlayerNumber(1);
		assertTrue(p.isOthersPlayer());
	}
	
	@Test
	public void testAddPowerup1() {
		ArrayList<SpeedPowerup> plist = new ArrayList<SpeedPowerup>();
		when(ih.getSpeedPowerupList()).thenReturn(plist);
		MainGame mg = mock(MainGame.class);
		when(mg.getState(1)).thenReturn(gs);
		when(mg.getGameState()).thenReturn(1);
		CircleList cl = mock(CircleList.class);
		Mockito.doNothing().when(cl).setAllMultipliers(anyFloat());
		when(ch.getCircleList()).thenReturn(cl);
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		gs.getItemsHelper().getSpeedPowerupList();
		assertEquals(0, plist.size());
		p.addPowerup(Powerup.PowerupType.SLOW);
		assertEquals(1, plist.size());
	}
	
	@Test
	public void testAddPowerup2() {
		ArrayList<SpeedPowerup> plist = new ArrayList<SpeedPowerup>();
		when(gs.getItemsHelper().getSpeedPowerupList()).thenReturn(plist);
		MainGame mg = mock(MainGame.class);
		when(mg.getState(1)).thenReturn(gs);
		when(mg.getGameState()).thenReturn(1);
		CircleList cl = mock(CircleList.class);
		Mockito.doNothing().when(cl).setAllMultipliers(anyFloat());
		when(gs.getCirclesHelper().getCircleList()).thenReturn(cl);
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		gs.getItemsHelper().getSpeedPowerupList();
		assertEquals(0, plist.size());
		p.addPowerup(Powerup.PowerupType.FREEZE);
		assertEquals(1, plist.size());
	}
	
	@Test
	public void testAddPowerup3() {
		ArrayList<SpeedPowerup> plist = new ArrayList<SpeedPowerup>();
		when(gs.getItemsHelper().getSpeedPowerupList()).thenReturn(plist);
		MainGame mg = mock(MainGame.class);
		when(mg.getState(1)).thenReturn(gs);
		when(mg.getGameState()).thenReturn(1);
		CircleList cl = mock(CircleList.class);
		Mockito.doNothing().when(cl).setAllMultipliers(anyFloat());
		when(gs.getCirclesHelper().getCircleList()).thenReturn(cl);
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		gs.getItemsHelper().getSpeedPowerupList();
		assertEquals(0, plist.size());
		p.addPowerup(Powerup.PowerupType.FAST);
		assertEquals(1, plist.size());
	}
	
	@Test
	public void testAddPowerup4() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		mg.setLifeCount(3);
		p.addPowerup(Powerup.PowerupType.HEALTH);
		assertEquals(4, mg.getLifeCount());
	}
}
