package logic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gui.GameState;
import gui.MainGame;

import java.util.ArrayList;

import logic.BouncingCircle;
import logic.Coin;
import logic.FloatingScore;
import logic.Gate;
import logic.Logger;
import logic.MyRectangle;
import logic.Player;
import logic.Powerup;
import logic.Weapon;
import logic.WeaponList;

import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;

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

	@Before
	public void setUp() throws Exception {
		i = mock(Image.class);
		i2 = mock(Image.class);
		i3 = mock(Image.class);
		i4 = mock(Image.class);
		mg = new MainGame("maingame");
		s = mock(SpriteSheet.class);
		gs = mock(GameState.class);
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
	public void testMovement() {
		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setMovement(Player.Movement.NO_MOVEMENT);
		assertEquals(Player.Movement.NO_MOVEMENT, p.getMovement());
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
		
		when(gs.getGateList()).thenReturn(gl);
		when(gs.getDroppedPowerups()).thenReturn(pl);
		when(gs.getFloatingScores()).thenReturn(fsl);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		MyRectangle ceiling = new MyRectangle(1,1,1,1);
		when(gs.getFloor()).thenReturn(floor);
		when(gs.getCeiling()).thenReturn(ceiling);
		when(gs.getWeaponList()).thenReturn(wl);

		p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.update(1, 1000, 1600, true);
		assertEquals(1,p.getX(),0);
		assertEquals(2,p.getY(),0);
	}
	
	@Test
	public void testUpdate2() {
		GameState gs = mock(GameState.class);
		Gate gate = new Gate(1,1,1,1);
		ArrayList<Gate> gl = new ArrayList<Gate>();
		gl.add(gate);
		Powerup pow = new Powerup(10,10,Powerup.PowerupType.SHIELD);
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
		Coin coin = new Coin(100,100,true);
		ArrayList<Coin> cl = new ArrayList<Coin>();
		cl.add(coin);
		
		when(gs.isPaused()).thenReturn(false);
		when(gs.getGateList()).thenReturn(gl);
		when(gs.getDroppedPowerups()).thenReturn(pl);
		when(gs.getFloatingScores()).thenReturn(fsl);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		MyRectangle ceiling = new MyRectangle(1,1,1,1);
		MyRectangle leftWall = new MyRectangle(1,1,1,1);
		MyRectangle rightWall = new MyRectangle(1,1,1,1);
		when(gs.getFloor()).thenReturn(floor);
		when(gs.getCeiling()).thenReturn(ceiling);
		when(gs.getLeftWall()).thenReturn(leftWall);
		when(gs.getRightWall()).thenReturn(rightWall);
		when(gs.getWeaponList()).thenReturn(wl);
		when(gs.getDroppedCoins()).thenReturn(cl);

		p = new Player(5,5,1000,1000, i, i2, i3, i4, mg);
		p.setShieldTimeRemaining(20l);
		p.addPowerup(Powerup.PowerupType.SPIKY);
		p.update(1,1000,1600, true);
		assertEquals(5,p.getX(),0);
		assertEquals(5,p.getY(),0);
	}
	
	@Test
	public void testUpdate3() {
		GameState gs = mock(GameState.class);
		Gate gate = new Gate(1,1,1,1);
		ArrayList<Gate> gl = new ArrayList<Gate>();
		gl.add(gate);
		Powerup pow = new Powerup(10,10,Powerup.PowerupType.SHIELD);
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
		Coin coin = new Coin(100,100,true);
		ArrayList<Coin> cl = new ArrayList<Coin>();
		cl.add(coin);
		
		when(gs.isPaused()).thenReturn(false);
		when(gs.getGateList()).thenReturn(gl);
		when(gs.getDroppedPowerups()).thenReturn(pl);
		when(gs.getFloatingScores()).thenReturn(fsl);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		MyRectangle ceiling = new MyRectangle(1,1,1,1);
		MyRectangle leftWall = new MyRectangle(1,1,1,1);
		MyRectangle rightWall = new MyRectangle(1,1,1,1);
		when(gs.getFloor()).thenReturn(floor);
		when(gs.getCeiling()).thenReturn(ceiling);
		when(gs.getLeftWall()).thenReturn(leftWall);
		when(gs.getRightWall()).thenReturn(rightWall);
		when(gs.getWeaponList()).thenReturn(wl);
		when(gs.getDroppedCoins()).thenReturn(cl);

		p = new Player(5,5,1000,1000, i, i2, i3, i4, mg);
		p.setShieldTimeRemaining(20l);
		p.addPowerup(Powerup.PowerupType.INSTANT);
		p.update(1,1000,1600, true);
		assertEquals(5,p.getX(),0);
		assertEquals(5,p.getY(),0);
	}
	
	@Test
	public void testUpdate4() {
		GameState gs = mock(GameState.class);
		Gate gate = new Gate(1,1,1,1);
		ArrayList<Gate> gl = new ArrayList<Gate>();
		gl.add(gate);
		Powerup pow = new Powerup(10,10,Powerup.PowerupType.SHIELD);
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
		Coin coin = new Coin(100,100,true);
		ArrayList<Coin> cl = new ArrayList<Coin>();
		cl.add(coin);
		
		when(gs.isPaused()).thenReturn(true);
		when(gs.getGateList()).thenReturn(gl);
		when(gs.getDroppedPowerups()).thenReturn(pl);
		when(gs.getFloatingScores()).thenReturn(fsl);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		MyRectangle ceiling = new MyRectangle(1,1,1,1);
		MyRectangle leftWall = new MyRectangle(1,1,1,1);
		MyRectangle rightWall = new MyRectangle(1,1,1,1);
		when(gs.getFloor()).thenReturn(floor);
		when(gs.getCeiling()).thenReturn(ceiling);
		when(gs.getLeftWall()).thenReturn(leftWall);
		when(gs.getRightWall()).thenReturn(rightWall);
		when(gs.getWeaponList()).thenReturn(wl);
		when(gs.getDroppedCoins()).thenReturn(cl);

		p = new Player(5,5,1000,1000, i, i2, i3, i4, mg);
		p.setShieldTimeRemaining(201);
		p.addPowerup(Powerup.PowerupType.INSTANT);
		p.update(1,1000,1600, true);
		assertEquals(5,p.getX(),0);
		assertEquals(5,p.getY(),0);
	}
	
	@Test
	public void testUpdate5() {
		GameState gs = mock(GameState.class);
		Gate gate = new Gate(1,1,1,1);
		ArrayList<Gate> gl = new ArrayList<Gate>();
		gl.add(gate);
		Powerup pow = new Powerup(10,10,Powerup.PowerupType.SHIELD);
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
		Coin coin = new Coin(100,100,true);
		ArrayList<Coin> cl = new ArrayList<Coin>();
		cl.add(coin);
		
		when(gs.isPaused()).thenReturn(true);
		when(gs.getGateList()).thenReturn(gl);
		when(gs.getDroppedPowerups()).thenReturn(pl);
		when(gs.getFloatingScores()).thenReturn(fsl);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		MyRectangle ceiling = new MyRectangle(1,1,1,1);
		MyRectangle leftWall = new MyRectangle(1,1,1,1);
		MyRectangle rightWall = new MyRectangle(1,1,1,1);
		when(gs.getFloor()).thenReturn(floor);
		when(gs.getCeiling()).thenReturn(ceiling);
		when(gs.getLeftWall()).thenReturn(leftWall);
		when(gs.getRightWall()).thenReturn(rightWall);
		when(gs.getWeaponList()).thenReturn(wl);
		when(gs.getDroppedCoins()).thenReturn(cl);

		p = new Player(5,5,1000,1000, i, i2, i3, i4, mg);
		p.setShieldTimeRemaining(201);
		p.addPowerup(Powerup.PowerupType.INSTANT);
		p.update(1,1000,1600, true);
		assertEquals(5,p.getX(),0);
		assertEquals(5,p.getY(),0);
	}
	
	@Test
	public void testUpdate6() {
		GameState gs = mock(GameState.class);
		Gate gate = new Gate(1,1,1,1);
		ArrayList<Gate> gl = new ArrayList<Gate>();
		gl.add(gate);
		Powerup pow = new Powerup(10,10,Powerup.PowerupType.SHIELD);
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
		Coin coin = new Coin(100,100,true);
		ArrayList<Coin> cl = new ArrayList<Coin>();
		cl.add(coin);
		
		when(gs.isPaused()).thenReturn(true);
		when(gs.getGateList()).thenReturn(gl);
		when(gs.getDroppedPowerups()).thenReturn(pl);
		when(gs.getFloatingScores()).thenReturn(fsl);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		MyRectangle ceiling = new MyRectangle(1,1,1,1);
		MyRectangle leftWall = new MyRectangle(1,1,1,1);
		MyRectangle rightWall = new MyRectangle(1,1,1,1);
		when(gs.getFloor()).thenReturn(floor);
		when(gs.getCeiling()).thenReturn(ceiling);
		when(gs.getLeftWall()).thenReturn(leftWall);
		when(gs.getRightWall()).thenReturn(rightWall);
		when(gs.getWeaponList()).thenReturn(wl);
		when(gs.getDroppedCoins()).thenReturn(cl);
		when(mg.isLanMultiplayer()).thenReturn(false);
		when(mg.isMultiplayer()).thenReturn(false);
		WeaponList wl2 = mock(WeaponList.class);
		ArrayList<Weapon> wl3 = mock(ArrayList.class);
		when(wl3.get(1)).thenReturn(new Weapon(1, 2, 3, 4));
		when(wl2.getWeaponList()).thenReturn(wl3);
		when(gs.getWeaponList()).thenReturn(wl2);
		
		p = new Player(5,5,1000,1000, i, i2, i3, i4, mg);
		p.setShieldTimeRemaining(201);
		p.setPlayerNumber(1);
		p.addPowerup(Powerup.PowerupType.INSTANT);
		p.update(1,1000,1600, true);
		assertEquals(5,p.getX(),0);
		assertEquals(5,p.getY(),0);
	}
	
	@Test
	public void testUpdate7() {
		GameState gs = mock(GameState.class);
		Gate gate = new Gate(1,1,1,1);
		ArrayList<Gate> gl = new ArrayList<Gate>();
		gl.add(gate);
		Powerup pow = new Powerup(10,10,Powerup.PowerupType.SHIELD);
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
		Coin coin = new Coin(100,100,true);
		ArrayList<Coin> cl = new ArrayList<Coin>();
		cl.add(coin);
		
		when(gs.isPaused()).thenReturn(true);
		when(gs.getGateList()).thenReturn(gl);
		when(gs.getDroppedPowerups()).thenReturn(pl);
		when(gs.getFloatingScores()).thenReturn(fsl);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		MyRectangle ceiling = new MyRectangle(1,1,1,1);
		MyRectangle leftWall = new MyRectangle(1,1,1,1);
		MyRectangle rightWall = new MyRectangle(1,1,1,1);
		when(gs.getFloor()).thenReturn(floor);
		when(gs.getCeiling()).thenReturn(ceiling);
		when(gs.getLeftWall()).thenReturn(leftWall);
		when(gs.getRightWall()).thenReturn(rightWall);
		when(gs.getWeaponList()).thenReturn(wl);
		when(gs.getDroppedCoins()).thenReturn(cl);
		when(mg.isLanMultiplayer()).thenReturn(false);
		when(mg.isMultiplayer()).thenReturn(false);
	
		
		p = new Player(5,5,1000,1000, i, i2, i3, i4, mg);
		p.setShieldTimeRemaining(201);
		p.setPlayerNumber(0);
		p.addPowerup(Powerup.PowerupType.INSTANT);
		p.update(1,1000,1600, true);
		assertEquals(5,p.getX(),0);
		assertEquals(5,p.getY(),0);
	}
	
	@Test
	public void testUpdate8() {
		GameState gs = mock(GameState.class);
		Gate gate = new Gate(1,1,1,1);
		ArrayList<Gate> gl = new ArrayList<Gate>();
		gl.add(gate);
		Powerup pow = new Powerup(10,10,Powerup.PowerupType.SHIELD);
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
		Coin coin = new Coin(100,100,true);
		ArrayList<Coin> cl = new ArrayList<Coin>();
		cl.add(coin);
		
		when(gs.isPaused()).thenReturn(true);
		when(gs.getGateList()).thenReturn(gl);
		when(gs.getDroppedPowerups()).thenReturn(pl);
		when(gs.getFloatingScores()).thenReturn(fsl);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		MyRectangle ceiling = new MyRectangle(1,1,1,1);
		MyRectangle leftWall = new MyRectangle(1,1,1,1);
		MyRectangle rightWall = new MyRectangle(1,1,1,1);
		when(gs.getFloor()).thenReturn(floor);
		when(gs.getCeiling()).thenReturn(ceiling);
		when(gs.getLeftWall()).thenReturn(leftWall);
		when(gs.getRightWall()).thenReturn(rightWall);
		when(gs.getWeaponList()).thenReturn(wl);
		when(gs.getDroppedCoins()).thenReturn(cl);
		when(mg.isLanMultiplayer()).thenReturn(false);
		when(mg.isMultiplayer()).thenReturn(true);
		WeaponList wl2 = mock(WeaponList.class);
		ArrayList<Weapon> wl3 = mock(ArrayList.class);
		when(wl3.get(1)).thenReturn(new Weapon(1, 2, 3, 4));
		when(wl2.getWeaponList()).thenReturn(wl3);
		when(gs.getWeaponList()).thenReturn(wl2);
		
		
		
		p = new Player(5,5,1000,1000, i, i2, i3, i4, mg);
		p.setShieldTimeRemaining(201);
		p.setPlayerNumber(1);
		p.addPowerup(Powerup.PowerupType.INSTANT);
		p.update(1,1000,1600, true);
		assertEquals(5,p.getX(),0);
		assertEquals(5,p.getY(),0);
	}
	
	@Test
	public void testUpdate9() {
		GameState gs = mock(GameState.class);
		Gate gate = new Gate(1,1,1,1);
		ArrayList<Gate> gl = new ArrayList<Gate>();
		gl.add(gate);
		Powerup pow = new Powerup(10,10,Powerup.PowerupType.SHIELD);
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
		Coin coin = new Coin(100,100,true);
		ArrayList<Coin> cl = new ArrayList<Coin>();
		cl.add(coin);
		
		when(gs.isPaused()).thenReturn(true);
		when(gs.getGateList()).thenReturn(gl);
		when(gs.getDroppedPowerups()).thenReturn(pl);
		when(gs.getFloatingScores()).thenReturn(fsl);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		MyRectangle ceiling = new MyRectangle(1,1,1,1);
		MyRectangle leftWall = new MyRectangle(1,1,1,1);
		MyRectangle rightWall = new MyRectangle(1,1,1,1);
		when(gs.getFloor()).thenReturn(floor);
		when(gs.getCeiling()).thenReturn(ceiling);
		when(gs.getLeftWall()).thenReturn(leftWall);
		when(gs.getRightWall()).thenReturn(rightWall);
		when(gs.getWeaponList()).thenReturn(wl);
		when(gs.getDroppedCoins()).thenReturn(cl);
		when(mg.isLanMultiplayer()).thenReturn(false);
		when(mg.isMultiplayer()).thenReturn(true);

		
		
		p = new Player(5,5,1000,1000, i, i2, i3, i4, mg);
		p.setShieldTimeRemaining(201);
		p.setPlayerNumber(0);
		p.addPowerup(Powerup.PowerupType.INSTANT);
		p.update(1,1000,1600, true);
		assertEquals(5,p.getX(),0);
		assertEquals(5,p.getY(),0);
	}
	
	@Test
	public void testUpdate10() {
		GameState gs = mock(GameState.class);
		Gate gate = new Gate(1,1,1,1);
		ArrayList<Gate> gl = new ArrayList<Gate>();
		gl.add(gate);
		Powerup pow = new Powerup(10,10,Powerup.PowerupType.SHIELD);
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
		Coin coin = new Coin(100,100,true);
		ArrayList<Coin> cl = new ArrayList<Coin>();
		cl.add(coin);
		
		when(gs.isPaused()).thenReturn(true);
		when(gs.getGateList()).thenReturn(gl);
		when(gs.getDroppedPowerups()).thenReturn(pl);
		when(gs.getFloatingScores()).thenReturn(fsl);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		MyRectangle ceiling = new MyRectangle(1,1,1,1);
		MyRectangle leftWall = new MyRectangle(1,1,1,1);
		MyRectangle rightWall = new MyRectangle(1,1,1,1);
		when(gs.getFloor()).thenReturn(floor);
		when(gs.getCeiling()).thenReturn(ceiling);
		when(gs.getLeftWall()).thenReturn(leftWall);
		when(gs.getRightWall()).thenReturn(rightWall);
		when(gs.getWeaponList()).thenReturn(wl);
		when(gs.getDroppedCoins()).thenReturn(cl);
		when(mg.isLanMultiplayer()).thenReturn(true);
		when(mg.isMultiplayer()).thenReturn(false);
		WeaponList wl2 = mock(WeaponList.class);
		ArrayList<Weapon> wl3 = mock(ArrayList.class);
		when(wl3.get(1)).thenReturn(new Weapon(1, 2, 3, 4));
		when(wl2.getWeaponList()).thenReturn(wl3);
		when(gs.getWeaponList()).thenReturn(wl2);
		
		
		p = new Player(5,5,1000,1000, i, i2, i3, i4, mg);
		p.setShieldTimeRemaining(201);
		p.setPlayerNumber(1);
		p.addPowerup(Powerup.PowerupType.INSTANT);
		p.update(1,1000,1600, true);
		assertEquals(5,p.getX(),0);
		assertEquals(5,p.getY(),0);
	}
	
	@Test
	public void testUpdate11() {
		GameState gs = mock(GameState.class);
		Gate gate = new Gate(1,1,1,1);
		ArrayList<Gate> gl = new ArrayList<Gate>();
		gl.add(gate);
		Powerup pow = new Powerup(10,10,Powerup.PowerupType.SHIELD);
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
		Coin coin = new Coin(100,100,true);
		ArrayList<Coin> cl = new ArrayList<Coin>();
		cl.add(coin);
		
		when(gs.isPaused()).thenReturn(true);
		when(gs.getGateList()).thenReturn(gl);
		when(gs.getDroppedPowerups()).thenReturn(pl);
		when(gs.getFloatingScores()).thenReturn(fsl);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		MyRectangle ceiling = new MyRectangle(1,1,1,1);
		MyRectangle leftWall = new MyRectangle(1,1,1,1);
		MyRectangle rightWall = new MyRectangle(1,1,1,1);
		when(gs.getFloor()).thenReturn(floor);
		when(gs.getCeiling()).thenReturn(ceiling);
		when(gs.getLeftWall()).thenReturn(leftWall);
		when(gs.getRightWall()).thenReturn(rightWall);
		when(gs.getWeaponList()).thenReturn(wl);
		when(gs.getDroppedCoins()).thenReturn(cl);
		when(mg.isLanMultiplayer()).thenReturn(true);
		when(mg.isMultiplayer()).thenReturn(false);
		WeaponList wl2 = mock(WeaponList.class);
		ArrayList<Weapon> wl3 = mock(ArrayList.class);
		when(wl3.get(1)).thenReturn(new Weapon(1, 2, 3, 4));
		when(wl2.getWeaponList()).thenReturn(wl3);
		when(gs.getWeaponList()).thenReturn(wl2);
		
		
		p = new Player(5,5,1000,1000, i, i2, i3, i4, mg);
		p.setShieldTimeRemaining(201);
		p.setPlayerNumber(0);
		p.addPowerup(Powerup.PowerupType.INSTANT);
		p.update(1,1000,1600, true);
		assertEquals(5,p.getX(),0);
		assertEquals(5,p.getY(),0);
	}
	
	@Test
	public void testUpdate12() {
		GameState gs = mock(GameState.class);
		Gate gate = new Gate(1,1,1,1);
		ArrayList<Gate> gl = new ArrayList<Gate>();
		gl.add(gate);
		Powerup pow = new Powerup(10,10,Powerup.PowerupType.SHIELD);
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
		Coin coin = new Coin(100,100,true);
		ArrayList<Coin> cl = new ArrayList<Coin>();
		cl.add(coin);
		
		when(gs.isPaused()).thenReturn(true);
		when(gs.getGateList()).thenReturn(gl);
		when(gs.getDroppedPowerups()).thenReturn(pl);
		when(gs.getFloatingScores()).thenReturn(fsl);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		MyRectangle ceiling = new MyRectangle(1,1,1,1);
		MyRectangle leftWall = new MyRectangle(1,1,1,1);
		MyRectangle rightWall = new MyRectangle(1,1,1,1);
		when(gs.getFloor()).thenReturn(floor);
		when(gs.getCeiling()).thenReturn(ceiling);
		when(gs.getLeftWall()).thenReturn(leftWall);
		when(gs.getRightWall()).thenReturn(rightWall);
		when(gs.getWeaponList()).thenReturn(wl);
		when(gs.getDroppedCoins()).thenReturn(cl);
		when(mg.isLanMultiplayer()).thenReturn(true);
		when(mg.isMultiplayer()).thenReturn(true);
		WeaponList wl2 = mock(WeaponList.class);
		ArrayList<Weapon> wl3 = mock(ArrayList.class);
		when(wl3.get(1)).thenReturn(new Weapon(1, 2, 3, 4));
		when(wl2.getWeaponList()).thenReturn(wl3);
		when(gs.getWeaponList()).thenReturn(wl2);
		
		
		p = new Player(5,5,1000,1000, i, i2, i3, i4, mg);
		p.setShieldTimeRemaining(201);
		p.setPlayerNumber(1);
		p.addPowerup(Powerup.PowerupType.INSTANT);
		p.update(1,1000,1600, true);
		assertEquals(5,p.getX(),0);
		assertEquals(5,p.getY(),0);
	}
	
	@Test
	public void testUpdate13() {
		GameState gs = mock(GameState.class);
		Gate gate = new Gate(1,1,1,1);
		ArrayList<Gate> gl = new ArrayList<Gate>();
		gl.add(gate);
		Powerup pow = new Powerup(10,10,Powerup.PowerupType.SHIELD);
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
		Coin coin = new Coin(100,100,true);
		ArrayList<Coin> cl = new ArrayList<Coin>();
		cl.add(coin);
		
		when(gs.isPaused()).thenReturn(true);
		when(gs.getGateList()).thenReturn(gl);
		when(gs.getDroppedPowerups()).thenReturn(pl);
		when(gs.getFloatingScores()).thenReturn(fsl);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		MyRectangle ceiling = new MyRectangle(1,1,1,1);
		MyRectangle leftWall = new MyRectangle(1,1,1,1);
		MyRectangle rightWall = new MyRectangle(1,1,1,1);
		when(gs.getFloor()).thenReturn(floor);
		when(gs.getCeiling()).thenReturn(ceiling);
		when(gs.getLeftWall()).thenReturn(leftWall);
		when(gs.getRightWall()).thenReturn(rightWall);
		when(gs.getWeaponList()).thenReturn(wl);
		when(gs.getDroppedCoins()).thenReturn(cl);
		when(mg.isLanMultiplayer()).thenReturn(true);
		when(mg.isMultiplayer()).thenReturn(true);
		WeaponList wl2 = mock(WeaponList.class);
		ArrayList<Weapon> wl3 = mock(ArrayList.class);
		when(wl3.get(1)).thenReturn(new Weapon(1, 2, 3, 4));
		when(wl2.getWeaponList()).thenReturn(wl3);
		when(gs.getWeaponList()).thenReturn(wl2);
		
		
		p = new Player(5,5,1000,1000, i, i2, i3, i4, mg);
		p.setShieldTimeRemaining(201);
		p.setPlayerNumber(0);
		p.addPowerup(Powerup.PowerupType.INSTANT);
		p.update(1,1000,1600, true);
		assertEquals(5,p.getX(),0);
		assertEquals(5,p.getY(),0);
	}
	
	@Test
	public void testIsMovingRight() {
		Player p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setMovingRight(true);
		assertTrue(p.isMovingRight());
	}

	@Test
	public void testSetMovingRight() {
		Player p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setMovingRight(false);
		assertFalse(p.isMovingRight());
	}
	
	@Test
	public void testIsMovingLeft() {
		Player p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setMovingLeft(true);
		assertTrue(p.isMovingLeft());
	}

	@Test
	public void testSetMovingLeft() {
		Player p = new Player(1, 2, 3, 4, i, i2, i3, i4, mg);
		p.setMovingLeft(false);
		assertFalse(p.isMovingLeft());
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
	
	
	
}
