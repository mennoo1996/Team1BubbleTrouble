package logic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import logic.MyRectangle;
import logic.Weapon;
import gui.GameState;
import gui.MainGame;

import org.junit.Before;
import org.junit.Test;

public class WeaponTest {
	
	Weapon l;
	MainGame mg;
	GameState gs;

	@Before
	public void setUp() throws Exception {
		l = new Weapon(1, 2, 3, 4);
		mg = new MainGame("TestGame");
		gs = new GameState(mg);
	}

	@Test
	public void testLaserX() {
		assertEquals(-1, l.getX(), 0);
	}
	
	@Test
	public void testLaserY() {
		assertEquals(2, l.getY(), 0);
	}
	
	@Test
	public void testLaserWidth() {
		assertEquals(4, l.getWidth(), 0);
	}
	
	@Test
	public void testLaserHeight() {
		assertEquals(0, l.getHeight(), 0);
	}
	
	@Test
	public void testLaserLaserSpeed() {
		assertEquals(3, l.getLaserSpeed(), 0);
	}
	
	@Test
	public void testLaserVisible() {
		assertTrue(l.isVisible());
	}

	@Test
	public void testGetRectangle() {
		MyRectangle r = l.getRectangle();
		assertEquals(new MyRectangle(-1, 2, 4, 0), r);
	}
	
	@Test
	public void testGetRectangleNoConstantValue() {
		Weapon l2 = new Weapon(4, -2, 8, 4);
		MyRectangle r = l2.getRectangle();
		assertEquals(new MyRectangle(2, -2, 4, 0), r);
	}

	@Test
	public void testUpdateY() {
		gs.setCeiling(new MyRectangle(0, 0, 0, 4));
		l.update(gs.getCeiling(), gs.getFloor(), 0.5f);
		assertEquals(0.5f, l.getY(), 0);
		
	}
	
	@Test
	public void testUpdateYNoConstantValue() {
		gs.setCeiling(new MyRectangle(0, 0, 0, 4));
		l.update(gs.getCeiling(), gs.getFloor(), 1);
		assertEquals(-1, l.getY(), 0);
	}
	
	@Test
	public void testUpdateHeight() {
		gs.setCeiling(new MyRectangle(0, 0, 0, 4));
		l.update(gs.getCeiling(), gs.getFloor(), 0.5f);
		assertEquals(1.5, l.getHeight(), 0);
	}
	
	@Test
	public void testUpdateHeightNoConstantValue() {
		gs.setCeiling(new MyRectangle(0, 0, 0 ,4));
		l.update(gs.getCeiling(), gs.getFloor(), 1);
		assertEquals(3, l.getHeight(), 0);
	}
	
	@Test
	public void testUpdateVisible(){
		gs.setCeiling(new MyRectangle(0, 0, 0, 1));
		l.update(gs.getCeiling(), gs.getFloor(), 0.5f);
		assertFalse(l.isVisible());
	}
	

	@Test
	public void testGetX() {
		assertEquals(-1, l.getX(), 0);
	}
	
	@Test
	public void testGetXNoConstantValue() {
		l.setX(2);
		assertEquals(2, l.getX(), 0);
	}

	@Test
	public void testSetX() {
		l.setX(2);
		assertEquals(2, l.getX(), 0);
	}
	
	@Test
	public void testSetXNoConstantValue() {
		l.setX(4);
		assertEquals(4, l.getX(), 0);
	}

	@Test
	public void testGetY() {
		assertEquals(2, l.getY(), 0);
	}
	
	@Test
	public void testGetYNoConstantValue() {
		l.setY(4);
		assertEquals(4, l.getY(), 0);
	}

	@Test
	public void testSetY() {
		l.setY(9);
		assertEquals(9, l.getY(), 0);
	}
	
	@Test
	public void testSetYNoConstantValue() {
		l.setY(7);
		assertEquals(7, l.getY(), 0);
	}

	@Test
	public void testGetWidth() {
		assertEquals(4, l.getWidth(), 0);
	}
	
	@Test
	public void testGetWidthNoConstantValue() {
		l.setWidth(5);
		assertEquals(5, l.getWidth(), 0);
	}

	@Test
	public void testSetWidth() {
		l.setWidth(5);
		assertEquals(5, l.getWidth(), 0);
	}
	
	@Test
	public void testSetWidthNoConstantValue() {
		l.setWidth(8);
		assertEquals(8, l.getWidth(), 0);
	}

	@Test
	public void testGetHeight() {
		assertEquals(0, l.getHeight(), 0);
	}
	
	@Test
	public void testGetHeightNoConstantValue() {
		l.setHeight(2);
		assertEquals(2, l.getHeight(), 0);
	}

	@Test
	public void testSetHeight() {
		l.setHeight(3);
		assertEquals(3, l.getHeight(), 0);
	}
	
	@Test
	public void testSetHeightNoConstantValue() {
		l.setHeight(6);
		assertEquals(6, l.getHeight(), 0);
	}

	@Test
	public void testIsVisible() {
		assertTrue(l.isVisible());
	}
	
	@Test
	public void testIsVisibleNoConstantValue() {
		l.setVisible(false);
		assertFalse(l.isVisible());
	}

	@Test
	public void testSetVisible() {
		l.setVisible(false);
		assertFalse(l.isVisible());
	}
	
	@Test
	public void testSetVisibleNoConstantValue() {
		l.setVisible(true);
		assertTrue(l.isVisible());
	}

	@Test
	public void testGetLaserSpeed() {
		assertEquals(3, l.getLaserSpeed(), 0);
	}
	
	@Test
	public void testGetLaserSpeedNoConstantValue() {
		l.setLaserSpeed(5);
		assertEquals(5, l.getLaserSpeed(), 0);
	}

	@Test
	public void testSetLaserSpeed() {
		l.setLaserSpeed(6);
		assertEquals(6, l.getLaserSpeed(), 0);
	}
	
	@Test
	public void testSetLaserSpeedNoConstantValue() {
		l.setLaserSpeed(7);
		assertEquals(7, l.getLaserSpeed(), 0);
	}

}
