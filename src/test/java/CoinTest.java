import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class CoinTest {

	private final static int COIN_CHEAP = 200;
    private final static int COIN_EXPENSIVE = 400;
    private final static float COIN_WIDTH = 20;
    private final static float COIN_HEIGHT = 20;
	
	@Test
	public void getPointsLargeTest() {
		Coin c = new Coin(100, 100, true);
		assertTrue(c.getPoints() == COIN_EXPENSIVE);
	}

	@Test
	public void getPointsSmallTest() {
		Coin c = new Coin(100, 100, false);
		assertTrue(c.getPoints() == COIN_CHEAP);
	}
	
	@Test
	public void getXTest() {
		Coin c = new Coin(100, 200, false);
		assertTrue(c.getX() == 100);
	}
	
	@Test
	public void getYTest() {
		Coin c = new Coin(100, 200, false);
		assertTrue(c.getY() == 200);
	}
	
	@Test
	public void getCenterXTest() {
		Coin c = new Coin(100, 200, false);
		assertTrue(c.getCenterX() == (100 + COIN_WIDTH / 2));
	}
	
	@Test
	public void getCenterYTest() {
		Coin c = new Coin(100, 200, false);
		assertTrue(c.getCenterY() == (200 + COIN_HEIGHT / 2));
	}
	
	@Test
	public void getRectangleTest() {
		Coin c = new Coin(100,100,false);
		MyRectangle rect = new MyRectangle(100,100, COIN_WIDTH, COIN_HEIGHT);
		assertEquals(c.getRectangle(), rect);
	}
	
}
