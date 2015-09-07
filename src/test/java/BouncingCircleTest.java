import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.GameContainer;

public class BouncingCircleTest {
	
	BouncingCircle c;
	MainGame mg;
	GameState gs;
	GameContainer gc;

	@Before
	public void setUp() throws Exception {
		mg = new MainGame("TestGame");
		gs = new GameState(mg);
		gc = mg.getContainer();
	}
	
	@Test
	public void testBouncingCircleCenterX() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(1, c.getCenterX(), 0);
	}
	
	@Test
	public void testBouncingCircleCenterY() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(2, c.getCenterY(), 0);
	}
	
	@Test
	public void testBouncingCircleRadius() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(3, c.getRadius(), 0);
	}
	
	@Test
	public void testBouncingCircleXSpeed() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(4, c.getxSpeed(), 0);
	}
	
	@Test
	public void testBouncingCircleYSpeed() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(5, c.getySpeed(), 0);
	}
	
	@Test
	public void testBouncingCircleGravity() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertEquals(6, c.getGravity(), 0);
	}
	
	@Test
	public void testBouncingCircleDone() {
		c = new BouncingCircle(1, 2, 3, 4, 5, 6);
		assertFalse(c.isDone());
	}
	
	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetX() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetX() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetY() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetY() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMaxX() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMaxY() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMinX() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMinY() {
		fail("Not yet implemented");
	}

	

	

	@Test
	public void testGetSplittedCircles() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetScore() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsDone() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDone() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCircle() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetxSpeed() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetxSpeed() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetySpeed() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetySpeed() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetGravity() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetGravity() {
		fail("Not yet implemented");
	}

}
