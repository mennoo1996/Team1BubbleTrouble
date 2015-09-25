import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gui.GameState;
import logic.MyRectangle;
import logic.Powerup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PowerupTest {
	
	private static final float POWERUP_WIDTH = 40;
    private static final float POWERUP_HEIGHT = 40;
	
	@Test
	public void getRectangleTest() {
		Powerup p = new Powerup(100,100,Powerup.PowerupType.INSTANT);
		MyRectangle rect = new MyRectangle(100,100, POWERUP_WIDTH, POWERUP_HEIGHT);
		assertEquals(p.getRectangle(), rect);
	}
	
	@Test
	public void getXTest() {
		Powerup p = new Powerup(100,100,Powerup.PowerupType.INSTANT);
		assertTrue(p.getX() == 100);
	}
	
	@Test
	public void getYTest() {
		Powerup p = new Powerup(100,100,Powerup.PowerupType.INSTANT);
		assertTrue(p.getY() == 100f);
	}
	
	@Test
	public void getCenterXTest() {
		Powerup p = new Powerup(100,100,Powerup.PowerupType.INSTANT);
		assertTrue(p.getCenterX() == 100 + POWERUP_WIDTH / 2);
	}
	
	@Test
	public void getCenterYTest() {
		Powerup p = new Powerup(100,100,Powerup.PowerupType.INSTANT);
		assertTrue(p.getCenterY() == 100 + POWERUP_HEIGHT / 2);
	}

	@Test
	public void getTypeTestINSTANT() {
		Powerup p = new Powerup(100,100,Powerup.PowerupType.INSTANT);
		assertTrue(p.getType() == Powerup.PowerupType.INSTANT);
	}
	
	@Test
	public void getTypeTestSPIKY() {
		Powerup p = new Powerup(100,100,Powerup.PowerupType.SPIKY);
		assertTrue(p.getType() == Powerup.PowerupType.SPIKY);
	}
	
	@Test
	public void getTypeTestSHIELD() {
		Powerup p = new Powerup(100,100,Powerup.PowerupType.SHIELD);
		assertTrue(p.getType() == Powerup.PowerupType.SHIELD);
	}
	
	@Test
	public void testUpdate1() {
		Powerup p = new Powerup(100,100,Powerup.PowerupType.SHIELD);
		GameState gs = mock(GameState.class);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		when(gs.isPaused()).thenReturn(false);
		when(gs.getFloor()).thenReturn(floor);
		p.update(gs, 100, 100);
	}
	
	@Test
	public void testUpdate2() {
		Powerup p = new Powerup(100,100,Powerup.PowerupType.SHIELD);
		GameState gs = mock(GameState.class);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		when(gs.isPaused()).thenReturn(false);
		when(gs.getFloor()).thenReturn(floor);
		p.update(gs, 1000, 100);
	}
	
	@Test
	public void testUpdate3() {
		Powerup p = new Powerup(100,100,Powerup.PowerupType.SHIELD);
		GameState gs = mock(GameState.class);
		MyRectangle floor = new MyRectangle(1,1,1,1);
		when(gs.isPaused()).thenReturn(true);
		when(gs.getFloor()).thenReturn(floor);
		p.update(gs, 1000, 100);
	}
	
	@Test
	public void testRemovePowerup() {
		Powerup p = new Powerup(100,100,Powerup.PowerupType.SHIELD);
		assertFalse(p.removePowerup());
	}
	
	@Test
	public void testToString() {
		Powerup p = new Powerup(100, 200, Powerup.PowerupType.SHIELD);
		assertEquals("POWERUP 100.0 200.0 SHIELD ", p.toString());
	}
	
	@Test
	public void testSetxId() {
		Powerup p = new Powerup(100, 200, Powerup.PowerupType.SHIELD);
		p.setxId(600);
		assertEquals(600, p.getxId(), 0);
	}
	
	@Test
	public void testGetxId() {
		Powerup p = new Powerup(100, 200, Powerup.PowerupType.SHIELD);
		p.setxId(600);
		assertEquals(600, p.getxId(), 0);
	}
	
	@Test
	public void testSetyId() {
		Powerup p = new Powerup(100, 200, Powerup.PowerupType.SHIELD);
		p.setyId(600);
		assertEquals(600, p.getyId(), 0);
	}
	
	@Test
	public void testGetyId() {
		Powerup p = new Powerup(100, 200, Powerup.PowerupType.SHIELD);
		p.setyId(600);
		assertEquals(600, p.getyId(), 0);
	}
	
	@Test
	public void testRemovePowerup2() {
		Powerup p = new Powerup(100, 200, Powerup.PowerupType.SHIELD);
		p.setTimeRemaining(-50);
		assertTrue(p.removePowerup());
	}
}
