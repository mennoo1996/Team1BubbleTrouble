package logic;
import static org.junit.Assert.*;
import logic.Coin;
import logic.MyRectangle;

import org.junit.Test;


public class CoinTest {

	private final static int COIN_CHEAP = 200;
    private final static int COIN_EXPENSIVE = 400;
    private final static float COIN_WIDTH = 20;
    private final static float COIN_HEIGHT = 20;
	
    @Test
    public void updateTest() {
    	Coin c = new Coin(100,500,false);
    	MyRectangle rect = new MyRectangle(0,100,300,10);
    	c.update(rect, 1000, 1.0f);
    	c.update(rect, 10, 1.0f);
    	assertTrue(c.getX() == 100);
    }
    
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
	
	@Test
	public void testToString() {
		Coin c = new Coin(100, 200, false);
		assertEquals("COIN 100.0 200.0 false ", c.toString());
	}
	
	@Test
	public void testUpdate() {
		Coin c = new Coin(100, 200, false);
		c.update(new MyRectangle(1, 2, 3, 4), 1, 1600);
		assertEquals(100, c.getX(), 0);
	}
	
	@Test
	public void testGetXId() {
		Coin c = new Coin(100, 200, false);
		c.setxId(600);
		assertEquals(600, c.getxId(), 0);
	}
	

	@Test
	public void testSetXId() {
		Coin c = new Coin(100, 200, false);
		c.setxId(600);
		assertEquals(600, c.getxId(), 0);
	}
	
	@Test
	public void testGetYId() {
		Coin c = new Coin(100, 200, false);
		c.setyId(600);
		assertEquals(600, c.getyId(), 0);
	}
	

	@Test
	public void testSetYId() {
		Coin c = new Coin(100, 200, false);
		c.setyId(600);
		assertEquals(600, c.getyId(), 0);
	}
	
}
