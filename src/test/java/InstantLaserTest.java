import static org.junit.Assert.*;

import org.junit.Test;


public class InstantLaserTest {

	@Test
	public void testInstantLaserX() {
		InstantLaser a = new InstantLaser(400,100,200);
		assertEquals(300, a.getX(),0);
	}
	
	@Test
	public void testInstantLaserY() {
		InstantLaser a = new InstantLaser(100,100,200);
		assertEquals(100, a.getY(),0);
	}
	
	@Test
	public void testInstantLaserWidth() {
		InstantLaser a = new InstantLaser(100,100,200);
		assertEquals(200, a.getWidth(),0);
	}

}
