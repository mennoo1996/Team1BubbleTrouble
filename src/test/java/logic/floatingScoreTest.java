package logic;
import static org.junit.Assert.*;
import logic.BouncingCircle;
import logic.Coin;
import logic.FloatingScore;

import org.junit.Before;
import org.junit.Test;

import powerups.Powerup;


public class floatingScoreTest {

	BouncingCircle c;
	Powerup p;
	Coin co;
	FloatingScore s;
	
	@Before
	public void setup() {
		c = new BouncingCircle(100, 100, 20, 10, 10, 1);
		p = new Powerup(100, 100, Powerup.PowerupType.INSTANT);
		co = new Coin(100, 100, true);
	}
	
	@Test
	public void updateTest_c() {
		s = new FloatingScore(c);
		s.update(0.1f, 10);
		assertFalse(s.getY() == c.getCenterY());
	}
	
	@Test
	public void updateTest_c2() {
		s = new FloatingScore("dada",0,0,100,1000);
		s.update(0.1f, 10);
		assertFalse(s.getY() == c.getCenterY());
	}
	
	@Test
	public void updateTest_c3() {
		s = new FloatingScore("dada",0,0);
		s.update(0.1f, 10);
		assertFalse(s.getY() == c.getCenterY());
	}
	
	@Test
	public void updateTest_p() {
		s = new FloatingScore(p);
		s.update(0.1f, 10);
		assertFalse(s.getY() == p.getCenterY());
	}
	
	@Test
	public void updateTest_co() {
		s = new FloatingScore(co);
		s.update(0.1f, 10);
		assertFalse(s.getY() == co.getCenterY());
	}
	
	@Test
	public void isDeadTest_c() {
		s = new FloatingScore(c);
		s.update(0.1f, 1000);
		assertTrue(s.isDead());
	}
	
	@Test
	public void isDeadTest_Negative_c() {
		s = new FloatingScore(c);
		assertFalse(s.isDead());
	}
	
	@Test
	public void getScoreTest_c() {
		s = new FloatingScore(c);
		String t = Integer.toString(c.getScore());
		assertTrue(t.equals(s.getScore()));
	}
	
	@Test
	public void getOpacityTest_c() {
		s = new FloatingScore(c);
		s.update(0.1f, 500);
		assertTrue(s.getOpacity() == 0.5f);
	}

	@Test
	public void getXTest_c() {
		s = new FloatingScore(c);
		assertTrue(s.getX() == c.getCenterX());
	}
	
	@Test
	public void getYTest_c() {
		s = new FloatingScore(c);
		assertTrue(s.getY() == c.getCenterY());
	}
	
	@Test
	public void cloneTest() throws CloneNotSupportedException {
		s = new FloatingScore(c);
		FloatingScore z = s.clone();
		assertTrue(s.getX() == z.getX() && s.getY() == z.getY()
				&& s.getScore().equals(z.getScore()));
	}
	
	@Test
	public void toStringTest() {
		s = new FloatingScore(c);
		assertEquals(s.toString(), "FLOATINGSCORE " + c.getCenterX() + " " + c.getCenterY() + " " + c.getScore() + " ");
	}
	
}
