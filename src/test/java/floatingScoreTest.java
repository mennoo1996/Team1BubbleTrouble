import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class floatingScoreTest {

	BouncingCircle c;
	floatingScore s;
	
	@Before
	public void setup() {
		c = new BouncingCircle(100, 100, 20, 10, 10, 1);
	}
	
	@Test
	public void updateTest() {
		s = new floatingScore(c);
		s.update(0.1f, 10);
		assertFalse(s.getY() == c.getCenterY());
	}
	
	@Test
	public void isDeadTest() {
		s = new floatingScore(c);
		s.update(0.1f, 1000);
		assertTrue(s.isDead());
	}
	
	@Test
	public void isDeadTest_Negative() {
		s = new floatingScore(c);
		assertFalse(s.isDead());
	}
	
	@Test
	public void getScoreTest() {
		s = new floatingScore(c);
		assertEquals(50, s.getScore());
	}
	
	@Test
	public void getOpacityTest() {
		s = new floatingScore(c);
		s.update(0.1f, 500);
		assertTrue(s.getOpacity() == 0.5f);
	}

	@Test
	public void getXTest() {
		s = new floatingScore(c);
		assertTrue(s.getX() == c.getCenterX());
	}
	
	@Test
	public void getYTest() {
		s = new floatingScore(c);
		assertTrue(s.getY() == c.getCenterY());
	}
	
}
