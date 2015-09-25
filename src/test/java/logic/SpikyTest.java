package logic;
import static org.junit.Assert.assertNotNull;
import logic.Spiky;

import org.junit.Test;
import org.newdawn.slick.geom.Rectangle;


public class SpikyTest {

	@Test
	public void testConstructor() {
		Spiky s = new Spiky(1,1,1,1);
		assertNotNull(s);
	}
	
	@Test
	public void testUpdate1() {
		Rectangle ceiling = new Rectangle(1,1,1,1);
		Rectangle floor = new Rectangle (1,1,1,1);
		
		Spiky s = new Spiky(1,1,1,1);
		s.update(ceiling, floor, 1);
	}
	
	@Test
	public void testUpdate2() {
		Rectangle ceiling = new Rectangle(1,1,1,1);
		Rectangle floor = new Rectangle (1,1,1,1);
	
		Spiky s = new Spiky(1,1,1,1);
		s.setVisible(false);
		s.update(ceiling, floor, 1);
	}

}
