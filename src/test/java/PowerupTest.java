import static org.junit.Assert.*;

import org.junit.Test;


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
	
}
