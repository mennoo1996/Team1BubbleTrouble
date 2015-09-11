import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.newdawn.slick.geom.Rectangle;


public class SpikeyTest {
	
	@Test
	public void testConstructor() {
		Spiky spiky = new Spiky(1,1,1,1);
		assertNotNull(spiky);
	}

	@Test
	public void testUpdate() {
		Rectangle ceiling = new Rectangle(100,100,100,100);
		Rectangle floor = new Rectangle(100,100,100,100);
		
		Spiky spiky = new Spiky(1,1,1,1);
		spiky.setVisible(true);
		spiky.update(1, ceiling, floor);
		
		assertTrue(spiky.isVisible());
		assertEquals(90.0, spiky.getY(), 0);
		assertEquals(7.0, spiky.getHeight(), 0);
	}
	
	@Test
	public void testUpdateVisibleFalse() {
		Rectangle ceiling = new Rectangle(100,100,100,100);
		Rectangle floor = new Rectangle(100,100,100,100);
		
		Spiky spiky = new Spiky(1,1,1,1);
		spiky.setVisible(false);
		spiky.update(1, ceiling, floor);
		
		assertFalse(spiky.isVisible());
	}
}
